#include <cerrno>
#include <csignal>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <cmath>
#include <ctime> // Для работы с временем
#include <iostream>
#include <sstream>
#include <string>

#include <sys/wait.h> // Для waitpid
#include <sys/time.h> // Для setitimer
#include <unistd.h>

// Структура для передачи результатов работы дочернего процесса через pipe
struct ChildResult {
	double duration;
};

// Функция для получения текущей даты и времени
std::string current_datetime()
{
	std::time_t now = std::time(nullptr);
	std::tm local_time{};
	localtime_r(&now, &local_time);

	char buffer[32];
	std::strftime(buffer, sizeof(buffer), "%d.%m.%Y %H:%M:%S", &local_time);
	return buffer;
}

// Функция для вычисления прошедшего времени в секундах между двумя timespec
double elapsed_seconds(const timespec &start, const timespec &finish)
{
	return static_cast<double>(finish.tv_sec - start.tv_sec) +
	       static_cast<double>(finish.tv_nsec - start.tv_nsec) / 1'000'000'000.0;
}

// Логика выполнения дочернего процесса
void run_child(int launch, int result_write_fd)
{
	timespec child_start{};
	clock_gettime(CLOCK_MONOTONIC, &child_start);
	std::cout << "Запуск " << launch << ": PID дочернего процесса = " << getpid()
	          << ", время старта = " << current_datetime() << std::endl;

	timespec child_finish{};
	clock_gettime(CLOCK_MONOTONIC, &child_finish); 
	ChildResult result{elapsed_seconds(child_start, child_finish)}; // Вычисление времени работы дочернего процесса
	if (write(result_write_fd, &result, sizeof(result)) != sizeof(result)) { // Запись результатов в канал
		std::perror("write");
		close(result_write_fd); // Закрытие канала для записи в дочернем процессе
		_exit(1);
	}
	close(result_write_fd); // Закрытие канала для записи в дочернем процессе
	_exit(0);
}

// Функция для выполнения таймера
bool on_timer_tick(int launch, const timespec &program_start)
{
	int result_pipe[2];
	if (pipe(result_pipe) == -1) {
		std::perror("pipe");
		return false;
	}

	pid_t child_pid = fork();
	if (child_pid == -1) {
		std::perror("fork");
		close(result_pipe[0]);
		close(result_pipe[1]);
		return false;
	}

	if (child_pid == 0) {
		close(result_pipe[0]); // Закрытие канала для чтения в дочернем процессе
		run_child(launch, result_pipe[1]);
	}

	close(result_pipe[1]); // Закрытие канала для записи в дочернем процессе
	int status = 0;
	if (waitpid(child_pid, &status, 0) == -1) {
		std::perror("waitpid");
		close(result_pipe[0]);
		return false;
	}

	ChildResult child_result{}; 
	ssize_t bytes_read = read(result_pipe[0], &child_result, sizeof(child_result)); // Чтение результатов из канала
	close(result_pipe[0]);
	if (bytes_read != sizeof(child_result) || !WIFEXITED(status) ||
	    WEXITSTATUS(status) != 0) {
		std::cerr << "Дочерний процесс завершился с ошибкой.\n";
		return false;
	}

	timespec program_now{};
	clock_gettime(CLOCK_MONOTONIC, &program_now);
	std::cout << "Дочерний процесс " << child_pid << " завершён. ";
	std::cout << "Время работы дочернего процесса: " << child_result.duration << " с, ";
	std::cout << "Общее время работы программы: "
	          << elapsed_seconds(program_start, program_now) << " с\n";
	return true;
}

int main(int argc, char *argv[])
{
	if (argc != 3) {
		std::cerr << "Использование: " << argv[0]
		          << " период_в_секундах количество_запусков\n";
		return 1;
	}

	double period = 0;
	int launch_count = 0;
	try {
		std::size_t period_end = 0;
		period = std::stod(argv[1], &period_end);
		if (period_end != std::strlen(argv[1])) {
			throw std::invalid_argument("лишние символы");
		}
		launch_count = std::stoi(argv[2]);
	} catch (const std::invalid_argument &) {
		std::cerr << "Период должен быть положительным числом. Количество запусков должно быть положительным целым числом.\n";
		return 1;
	} catch (const std::out_of_range &) {
		std::cerr << "Период и количество запусков слишком большие.\n";
		return 1;
	}
	if (period <= 0 || launch_count <= 0) {
		std::cerr << "Период и количество запусков должны быть положительными числами.\n";
		return 1;
	}

	time_t whole_seconds = static_cast<time_t>(period);
	suseconds_t microseconds = static_cast<suseconds_t>(
		std::llround((period - static_cast<double>(whole_seconds)) * 1'000'000.0));
	if (microseconds >= 1'000'000) {
		++whole_seconds;
		microseconds -= 1'000'000;
	}
	if (whole_seconds == 0 && microseconds == 0) {
		std::cerr << "Период слишком мал: минимум 1 микросекунда.\n";
		return 1;
	}

	// Перехват сигнала SIGTSTP (Ctrl+Z)
	if (std::signal(SIGTSTP, SIG_IGN) == SIG_ERR) {
		std::perror("signal");
		return 1;
	}

	timespec program_start{};
	clock_gettime(CLOCK_MONOTONIC, &program_start);

	// Блокируем SIGALRM и ждём его через sigwait (без обработчика и флага)
	sigset_t blocked_signals;
	sigemptyset(&blocked_signals);
	sigaddset(&blocked_signals, SIGALRM);
	if (sigprocmask(SIG_BLOCK, &blocked_signals, nullptr) == -1) {
		std::perror("sigprocmask");
		return 1;
	}

	// Настройка периодического таймера с использованием setitimer
	struct itimerval timer{};
	timer.it_value.tv_sec = whole_seconds;
	timer.it_value.tv_usec = microseconds;
	timer.it_interval.tv_sec = whole_seconds;
	timer.it_interval.tv_usec = microseconds;
	if (setitimer(ITIMER_REAL, &timer, nullptr) == -1) {
		std::perror("setitimer");
		return 1;
	}

	// Периодический таймер будит основной поток; по каждому тику — вызов on_timer_tick.
	for (int launch = 1; launch <= launch_count; ++launch) {
		int sig = 0;
		if (sigwait(&blocked_signals, &sig) != 0) {
			std::perror("sigwait");
			return 1;
		}
		if (!on_timer_tick(launch, program_start)) {
			return 1;
		}
	}

	// Останавливаем таймер после нужного количества запусков.
	struct itimerval stopped_timer{};
	setitimer(ITIMER_REAL, &stopped_timer, nullptr);

	return 0;
}
