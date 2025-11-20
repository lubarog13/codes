#include <iostream>
#include <sys/ioctl.h>
#include <sys/soundcard.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <cmath>


//написать программу, которая обращается к устройству выдачи звука и генерирует звуковой сигнал заданой частоты.

const double FREQUENCY_DEFAULT = 440.0;  // Частота в Гц
const int SAMPLE_RATE_DEFAULT = 44100;  // Частота дискретизации
const int BITS_DEFAULT = 16;             // Разрядность
const int CHANNELS_DEFAULT = 1;          // Моно
const int DURATION_DEFAULT = 2;          // Длительность в секундах

// WAV header writer
void write_wav_header(FILE* file, int sample_rate, int channels, int bits_per_sample, int data_size) {
    fwrite("RIFF", 1, 4, file);
    uint32_t file_size = 36 + data_size;
    fwrite(&file_size, 4, 1, file);
    fwrite("WAVE", 1, 4, file);
    fwrite("fmt ", 1, 4, file);
    uint32_t fmt_size = 16;
    fwrite(&fmt_size, 4, 1, file);
    uint16_t audio_format = 1; // PCM
    fwrite(&audio_format, 2, 1, file);
    fwrite(&channels, 2, 1, file);
    fwrite(&sample_rate, 4, 1, file);
    uint32_t byte_rate = sample_rate * channels * (bits_per_sample / 8);
    fwrite(&byte_rate, 4, 1, file);
    uint16_t block_align = channels * (bits_per_sample / 8);
    fwrite(&block_align, 2, 1, file);
    fwrite(&bits_per_sample, 2, 1, file);
    fwrite("data", 1, 4, file);
    fwrite(&data_size, 4, 1, file);
}

int main(int argc, char** argv) {
    double frequency = FREQUENCY_DEFAULT;
    int sample_rate = SAMPLE_RATE_DEFAULT;
    int bits = BITS_DEFAULT;
    int channels = CHANNELS_DEFAULT;
    int duration = DURATION_DEFAULT;
    for (int i = 0; i < argc; i++) {
        if (strncmp(argv[i], "--frequency=", 12) == 0) {
            try {
                frequency = atof(argv[i] + 12);
            } catch (std::exception& e) {
                frequency = FREQUENCY_DEFAULT;
            }
        }
        if (strncmp(argv[i], "--sample-rate=", 14) == 0) {
            try {
                sample_rate = atoi(argv[i] + 14);
            } catch (std::exception& e) {
                sample_rate = SAMPLE_RATE_DEFAULT;
            }
        }
        if (strncmp(argv[i], "--bits=", 7) == 0) {
            try {
                bits = atoi(argv[i] + 7);
            } catch (std::exception& e) {
                bits = BITS_DEFAULT;
            }
        }
        if (strncmp(argv[i], "--channels=", 11) == 0) {
            try {
                channels = atoi(argv[i] + 11);
            } catch (std::exception& e) {
                channels = CHANNELS_DEFAULT;
            }
        }
        if (strncmp(argv[i], "--duration=", 10) == 0) {
            try {
                duration = atoi(argv[i] + 10);
            } catch (std::exception& e) {
                duration = DURATION_DEFAULT;
            }
        }
    }
    int fd = open("/dev/dsp", O_WRONLY);
    FILE* wav = NULL;
    bool use_device = (fd != -1);
    
    if (!use_device) {
        perror("open /dev/dsp");
        std::cerr << "Предупреждение: не удалось открыть /dev/dsp. Будет использован файл output.wav" << std::endl;
    }

    int speed = sample_rate;

    // Если устройство открыто, настраиваем его
    if (use_device) {
        // Устанавливаем формат (16-bit)
        int format = AFMT_S16_LE;
        if (ioctl(fd, SNDCTL_DSP_SETFMT, &format) == -1) {
            perror("ioctl SNDCTL_DSP_SETFMT");
            close(fd);
            // Пробуем записать в файл вместо устройства
            use_device = false;
        } else if (format != AFMT_S16_LE) {
            std::cerr << "Ошибка: устройство не поддерживает 16-bit формат. Используется файл." << std::endl;
            close(fd);
            use_device = false;
        } else {
            // Устанавливаем количество каналов (моно)
            int ch = channels;
            if (ioctl(fd, SNDCTL_DSP_CHANNELS, &ch) == -1) {
                perror("ioctl SNDCTL_DSP_CHANNELS");
                close(fd);
                use_device = false;
            }
            if (ch != channels) {
                std::cerr << "Предупреждение: установлено " << ch << " каналов вместо " << channels << std::endl;
            }

            // Устанавливаем частоту дискретизации
            if (ioctl(fd, SNDCTL_DSP_SPEED, &speed) == -1) {
                perror("ioctl SNDCTL_DSP_SPEED");
                close(fd);
                use_device = false;
            }
            if (speed != sample_rate) {
                std::cerr << "Предупреждение: установлена частота " << speed << " Гц вместо " << sample_rate << " Гц" << std::endl;
            }
        }
    }

    std::cout << "Генерация звукового сигнала частотой " << frequency << " Гц..." << std::endl;
    std::cout << "Частота дискретизации: " << speed << " Гц" << std::endl;
    std::cout << "Длительность: " << duration << " секунд" << std::endl;

    // Генерируем звук
    const int samples_count = speed * duration;
    const double two_pi = 2.0 * M_PI;
    const double phase_increment = two_pi * frequency / speed;

    int16_t* buffer = new int16_t[samples_count];
    
    // Генерируем синусоидальный сигнал
    for (int i = 0; i < samples_count; i++) {
        double sample = sin(phase_increment * i);
        // Масштабируем до диапазона int16_t (от -32768 до 32767)
        buffer[i] = (int16_t)(sample * 32767);
    }

    // Записываем данные
    bool write_success = false;
    
    if (use_device) {
        // Пробуем записать на устройство
        ssize_t written = write(fd, buffer, samples_count * sizeof(int16_t));
        if (written != -1) {
            std::cout << "Записано на устройство: " << written << " байт" << std::endl;
            write_success = true;
            close(fd);
        } else {
            perror("write to device");
            std::cerr << "Ошибка записи на устройство. Пробуем записать в файл..." << std::endl;
            close(fd);
            use_device = false;
        }
    }
    
    // Если не удалось записать на устройство, записываем в файл
    if (!write_success) {
            wav = fopen("output.wav", "wb");
            if (!wav) {
                perror("fopen output.wav");
                delete[] buffer;
                return 1;
            }
        
        // Записываем WAV заголовок
        int data_size = samples_count * sizeof(int16_t);
        write_wav_header(wav, speed, channels, bits, data_size);
        
        // Записываем аудиоданные
        size_t written = fwrite(buffer, sizeof(int16_t), samples_count, wav);
        if (written != samples_count) {
            perror("fwrite");
            fclose(wav);
            delete[] buffer;
            return 1;
        }
        std::cout << "Записано в файл output.wav: " << (written * sizeof(int16_t)) << " байт" << std::endl;
        fclose(wav);
    }

    // Очистка
    delete[] buffer;
    return 0;
}