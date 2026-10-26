#include <cstdio>
#include <cstdlib>

#include <pthread.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <unistd.h>

#include <cstdint>

#include "lab_8.h"

int main(int argc, char *argv[])
{
	if (argc != 2) {
		printf("Использование: %s файл\n", argv[0]);
		return 1;
	}

	// создание очередей
	shared_qid = msgget(REQ_KEY, IPC_CREAT | 0666);
	local_qid = msgget(IPC_PRIVATE, IPC_CREAT | 0666);

	// создание потока для приема запросов
	pthread_t tid;
	pthread_create(&tid, nullptr, listener,
	               reinterpret_cast<void *>(static_cast<intptr_t>(1)));
	pthread_detach(tid);

	printf("Программа 1 запущена\n");
	printf("Ожидание запуска остальных программ...\n");
	fflush(stdout);
	sleep(3);

	program_time = now_ms();
	printf("Программа 1: время запроса = %ld\n", program_time);
	fflush(stdout);

	// Запросы программам 2 и 3
	int dests[] = {2, 3};
	for (int i = 0; i < 2; i++) {
		RequestMsg req{};
		req.mtype = dests[i];
		req.from = 1;
		req.reply_qid = local_qid;
		req.request_time = program_time;
		msgsnd(shared_qid, &req, sizeof(RequestMsg) - sizeof(long), 0);
	}

	// Ждём два ответа
	long t1 = 0, t2 = 0;
	int from1 = 0, from2 = 0;
	for (int i = 0; i < 2; i++) {
		ReplyMsg rep{};
		msgrcv(local_qid, &rep, sizeof(ReplyMsg) - sizeof(long), 1, 0);
		if (i == 0) {
			from1 = rep.from;
			t1 = rep.reply_time;
		} else {
			from2 = rep.from;
			t2 = rep.reply_time;
		}
	}

	printf("\n===== Программа 1 =====\n");
	printf("Ответ от %d: время %ld\n", from1, t1);
	printf("Ответ от %d: время %ld\n", from2, t2);

	// чтение файла
	FILE *f = fopen(argv[1], "r");
	char buf[256];
	while (fgets(buf, sizeof(buf), f)) {
		fputs(buf, stdout);
	}
	fclose(f);

	file_read = 1;
	for (int i = 0; i < deferred_count; i++) {
		send_reply(1, deferred[i]); // отложенные ответы
	}
	deferred_count = 0;

	printf("\nПрограмма 1: готово. Ожидание \n");
	fflush(stdout);
	sleep(5);

	msgctl(local_qid, IPC_RMID, nullptr);

	
	msqid_ds info{};
	if (msgctl(shared_qid, IPC_STAT, &info) == -1) {
		return 0;
	}
	if (info.msg_qnum != 0) {
		printf("В общей очереди ещё %lu сообщений — удаление невозможно\n",
		       static_cast<unsigned long>(info.msg_qnum));
		return 1;
	}
	if (msgctl(shared_qid, IPC_RMID, nullptr) == -1) {
		perror("msgctl IPC_RMID");
		return 1;
	}
	printf("Общая очередь удалена\n");
	return 0;
}
