#include <sys/msg.h>
#include <sys/time.h>

#include <cstdint>

#include "lab_8.h"

int shared_qid, local_qid;
int file_read = 0;
long program_time = 0;

RequestMsg deferred[MAX_DEFERRED];
int deferred_count = 0;

// текущее время в миллисекундах
long now_ms()
{
	timeval tv{};
	gettimeofday(&tv, nullptr);
	return tv.tv_sec * 1000L + tv.tv_usec / 1000L;
}

void send_reply(int prog_num, RequestMsg req)
{
	ReplyMsg rep{};
	rep.mtype = 1;
	rep.from = prog_num;
	rep.reply_time = now_ms();
	msgsnd(req.reply_qid, &rep, sizeof(ReplyMsg) - sizeof(long), 0);
}

// Поток: принимает запросы из общей очереди
void *listener(void *arg)
{
	int prog_num = static_cast<int>(reinterpret_cast<intptr_t>(arg));

	while (true) {
		RequestMsg req{};
		if (msgrcv(shared_qid, &req, sizeof(RequestMsg) - sizeof(long),
		           prog_num, 0) == -1) {
			continue;
		}

		// Уже прочитали файл — отвечаем сразу
		if (file_read) {
			send_reply(prog_num, req);
			continue;
		}

		// Ещё не читали: отвечаем, только если их запрос раньше нашего
		// (или мы ещё сами не запрашивали)
		if (program_time == 0 || req.request_time < program_time ||
		    (req.request_time == program_time && req.from < prog_num)) {
			send_reply(prog_num, req);
		} else if (deferred_count < MAX_DEFERRED) {
			deferred[deferred_count++] = req; // ответим после чтения
		}
	}
	return nullptr;
}
