#define REQ_KEY 0x4C384241 // ключ для общей очереди LAB8
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

extern int shared_qid, local_qid;
extern int file_read; // 1 — файл уже прочитан
extern long program_time; // время запроса программы
extern RequestMsg deferred[MAX_DEFERRED];
extern int deferred_count;

long now_ms();
void send_reply(int prog_num, RequestMsg req);
void *listener(void *arg); // arg — номер программы (intptr_t)

