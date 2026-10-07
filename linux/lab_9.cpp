#include <cstdio>

#include <sys/ipc.h>
#include <sys/shm.h>
#include <unistd.h>

#ifndef PROG_NUM // Номер программы
#error "Скомпилируйте с -DPROG_NUM=1 или -DPROG_NUM=2"
#endif

#define N 5
#define KEY1 0x395001 // область: программа 1 -> программа 2
#define KEY2 0x395002 // область: программа 2 -> программа 1

// Массив и общая переменная для синхронизации
struct Area {
	int ready; // 0 — данных нет, 1 — массив записан
	int data[N];
};

#if PROG_NUM == 1

int main()
{
	int arr[N] = {1, 2, 3, 4, 5};

	int id1 = shmget(KEY1, sizeof(Area), IPC_CREAT | 0666); // создаем область памяти для программы
	int id2 = shmget(KEY2, sizeof(Area), IPC_CREAT | 0666);
	if (id1 == -1 || id2 == -1) {
		perror("shmget");
		return 1;
	}

	Area *to_second = (Area *)shmat(id1, nullptr, 0); // присоединяем область памяти к адресному пространству 
	Area *to_first = (Area *)shmat(id2, nullptr, 0); 
	if (to_second == (Area *)-1 || to_first == (Area *)-1) {
		perror("shmat");
		return 1;
	}

	to_second->ready = 0;
	to_first->ready = 0;

	for (int i = 0; i < N; i++) {
		to_second->data[i] = arr[i]; // записываем массив в область памяти
	}
	to_second->ready = 1; // массив можно читать

	while (to_first->ready == 0) {
		usleep(1000);
	}

	printf("Программа 1 получила: ");
	for (int i = 0; i < N; i++) {
		printf("%d ", to_first->data[i]);
	}
	printf("\n");

	shmdt(to_second); // отсоединяем область памяти
	shmdt(to_first);
	shmctl(id1, IPC_RMID, nullptr); // удаляем область памяти
	shmctl(id2, IPC_RMID, nullptr);
	return 0;
}

#else

int main()
{
	int id1 = shmget(KEY1, sizeof(Area), IPC_CREAT | 0666);
	int id2 = shmget(KEY2, sizeof(Area), IPC_CREAT | 0666);
	if (id1 == -1 || id2 == -1) {
		perror("shmget");
		return 1;
	}

	Area *from_first = (Area *)shmat(id1, nullptr, 0); 
	Area *to_first = (Area *)shmat(id2, nullptr, 0); 
	if (from_first == (Area *)-1 || to_first == (Area *)-1) {
		perror("shmat");
		return 1;
	}

	while (from_first->ready == 0) {
		usleep(1000);
	}

	printf("Программа 2 получила: ");
	for (int i = 0; i < N; i++) {
		printf("%d ", from_first->data[i]);
		to_first->data[i] = from_first->data[i] - 1;
	}
	printf("\n");

	to_first->ready = 1; // ответ можно читать

	shmdt(from_first);
	shmdt(to_first);
	return 0;
}

#endif
