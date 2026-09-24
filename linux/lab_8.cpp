#include <cstdio>
#include <cstdlib>
#include <ctime>

#include <pthread.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/time.h>
#include <unistd.h>

#ifndef PROG_NUM // Номер программы
#error "Скомпилируйте с -DPROG_NUM=1 (или 2, или 3)"
#endif

#define REQ_KEY 0x4C384241  //  ключ для общей очереди LAB8
#define MAX_DEFERRED 16 // максимальное количество отложенных запросов

struct RequestMsg {
	long mtype; // кому
	int from;
	int reply_qid;
	long request_time;
};

struct ReplyMsg {
	long mtype;
	int from;
	long reply_time;
};

int shared_qid, local_qid;
int file_read = 0; // 1 — файл уже прочитан
long program_time = 0; // время запроса программы

RequestMsg deferred[MAX_DEFERRED];
int deferred_count = 0;

// текущее время в миллисекундах
long now_ms()
{
	timeval tv{};
	gettimeofday(&tv, nullptr);
	return tv.tv_sec * 1000L + tv.tv_usec / 1000L;
}

void send_reply(RequestMsg req)
{
	ReplyMsg rep{};
	rep.mtype = 1;
	rep.from = PROG_NUM;
	rep.reply_time = now_ms();
	msgsnd(req.reply_qid, &rep, sizeof(ReplyMsg) - sizeof(long), 0);
}

// Поток: принимает запросы из общей очереди
void *listener(void *)
{
	while (true) {
		RequestMsg req{};
		if (msgrcv(shared_qid, &req, sizeof(RequestMsg) - sizeof(long),
		           PROG_NUM, 0) == -1) {
			continue;
		}

		// Уже прочитали файл — отвечаем сразу
		if (file_read) {
			send_reply(req);
			continue;
		}

		// Ещё не читали: отвечаем, только если их запрос раньше нашего
		// (или мы ещё сами не запрашивали)
		if (program_time == 0 || req.request_time < program_time ||
		    (req.request_time == program_time && req.from < PROG_NUM)) {
			send_reply(req);
		} else if (deferred_count < MAX_DEFERRED) {
			deferred[deferred_count++] = req; // ответим после чтения
		}
	}
	return nullptr;
}

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
	pthread_create(&tid, nullptr, listener, nullptr);
	pthread_detach(tid);

	printf("Программа %d запущена\n", PROG_NUM);
	printf("Ожидание запуска остальных программ...\n");
	fflush(stdout);
	sleep(3);

	program_time = now_ms();
	printf("Программа %d: время запроса = %ld\n", PROG_NUM, program_time);
	fflush(stdout);

	// Запросы двум другим программам
	for (int dest = 1; dest <= 3; dest++) {
		if (dest == PROG_NUM) {
			continue;
		}
		RequestMsg req{};
		req.mtype = dest;
		req.from = PROG_NUM;
		req.reply_qid = local_qid;
		req.request_time = program_time;
        // отправка запроса в общую очередь
		msgsnd(shared_qid, &req, sizeof(RequestMsg) - sizeof(long), 0);
	}

	// Ждём два ответа
	long t1 = 0, t2 = 0;
	int from1 = 0, from2 = 0;
	for (int i = 0; i < 2; i++) {
		ReplyMsg rep{};
        // получение ответа из локальной очереди
		msgrcv(local_qid, &rep, sizeof(ReplyMsg) - sizeof(long), 1, 0);
		if (i == 0) {
			from1 = rep.from;
			t1 = rep.reply_time;
		} else {
			from2 = rep.from;
			t2 = rep.reply_time;
		}
	}

	printf("\n===== Программа %d =====\n", PROG_NUM);
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
		send_reply(deferred[i]); // отложенные ответы
	}
	deferred_count = 0;

	printf("\nПрограмма %d: готово. Ожидание \n", PROG_NUM);
	fflush(stdout);
	sleep(5);

    // удаление локальной очереди
	msgctl(local_qid, IPC_RMID, nullptr);
	return 0;
}
