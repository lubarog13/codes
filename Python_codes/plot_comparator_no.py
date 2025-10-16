import matplotlib.pyplot as plt
import numpy as np

# Параметры гистерезиса
U_VX1_K = 5.016  # Пороговое значение для перехода с высокого на низкий уровень
U_VX2_K = 4.896 # Пороговое значение для перехода с низкого на высокий уровень
U_OP = (U_VX1_K + U_VX2_K) / 2  # Опорное напряжение (среднее значение)
U_G = U_VX1_K - U_VX2_K  # Ширина гистерезиса

# Выходные уровни
U_OUT_HIGH = 15.063
U_OUT_LOW = -15.063

# Создание фигуры и осей
fig, ax = plt.subplots(figsize=(10, 8))

# Построение петли гистерезиса
# Верхняя горизонтальная линия (высокий уровень)
x_upper = np.linspace(U_VX2_K - 2, U_VX1_K, 100)
y_upper = np.full_like(x_upper, U_OUT_HIGH)

# Вертикальная линия вниз (переход с высокого на низкий)
x_down = np.full(50, U_VX1_K)
y_down = np.linspace(U_OUT_HIGH, U_OUT_LOW, 50)

# Нижняя горизонтальная линия (низкий уровень)
x_lower = np.linspace(U_VX1_K, U_VX1_K + 2, 100)
y_lower = np.full_like(x_lower, U_OUT_LOW)

# Вертикальная линия вверх (переход с низкого на высокий)
x_up = np.full(50, U_VX2_K)
y_up = np.linspace(U_OUT_LOW, U_OUT_HIGH, 50)

# Объединение всех сегментов
x_hysteresis = np.concatenate([x_upper, x_down, x_lower, x_up])
y_hysteresis = np.concatenate([y_upper, y_down, y_lower, y_up])

# Построение графика
ax.plot(x_hysteresis, y_hysteresis, 'k-', linewidth=3, label='Гистерезис')

# Добавление стрелок для указания направления
# Стрелка на верхней горизонтальной линии
ax.annotate('', xy=(U_VX1_K - 0.1, U_OUT_HIGH), xytext=(U_VX1_K - 0.3, U_OUT_HIGH),
            arrowprops=dict(arrowstyle='->', color='black', lw=2))

# Стрелка на вертикальной линии вниз
ax.annotate('', xy=(U_VX1_K, U_OUT_LOW + 2), xytext=(U_VX1_K, U_OUT_HIGH - 2),
            arrowprops=dict(arrowstyle='->', color='black', lw=2))

# Стрелка на нижней горизонтальной линии
ax.annotate('', xy=(U_VX2_K + 0.1, U_OUT_LOW), xytext=(U_VX2_K + 0.3, U_OUT_LOW),
            arrowprops=dict(arrowstyle='->', color='black', lw=2))

# Стрелка на вертикальной линии вверх
ax.annotate('', xy=(U_VX2_K, U_OUT_HIGH - 2), xytext=(U_VX2_K, U_OUT_LOW + 2),
            arrowprops=dict(arrowstyle='->', color='black', lw=2))

# Отметка точек A и B
ax.plot(U_VX2_K - 2, U_OUT_HIGH, 'ko', markersize=8, label='Точка A')
ax.plot(U_VX1_K + 2, U_OUT_LOW, 'ko', markersize=8, label='Точка B')

# Добавление подписей
ax.text(U_VX2_K - 2.2, U_OUT_HIGH + 1, 'A', fontsize=14, fontweight='bold')
ax.text(U_VX1_K + 2.2, U_OUT_LOW - 1, 'B', fontsize=14, fontweight='bold')

# Подписи пороговых значений
ax.axvline(x=U_VX1_K, color='red', linestyle='--', alpha=0.7)
ax.axvline(x=U_VX2_K, color='red', linestyle='--', alpha=0.7)
ax.axvline(x=U_OP, color='blue', linestyle='--', alpha=0.7)

ax.text(U_VX1_K - 0.5, U_OUT_LOW - 3, 'U_ВХ1.К', fontsize=12, ha='center', color='red')
ax.text(U_VX2_K + 0.5, U_OUT_LOW - 3, 'U_ВХ2.К', fontsize=12, ha='center', color='red')
ax.text(U_OP, U_OUT_LOW - 3, 'U_ОП', fontsize=12, ha='center', color='blue')

# Стрелка для ширины гистерезиса
ax.annotate('', xy=(U_VX1_K, U_OUT_LOW - 1.5), xytext=(U_VX2_K, U_OUT_LOW - 1.5),
            arrowprops=dict(arrowstyle='<->', color='green', lw=2))
ax.text(U_OP, U_OUT_LOW - 2.5, 'U_Г', fontsize=12, ha='center', color='green', fontweight='bold')

# Настройка осей
ax.set_xlim(U_VX2_K - 3, U_VX1_K + 3)
ax.set_ylim(U_OUT_LOW - 4, U_OUT_HIGH + 4)
ax.set_xlabel('Входное напряжение', fontsize=14)
ax.set_ylabel('Выходное напряжение', fontsize=14)
ax.set_title('График гистерезиса (безгистерезисный компаратор)', fontsize=16, fontweight='bold')

# Добавление сетки
ax.grid(True, alpha=0.3)

# Добавление легенды
ax.legend(loc='upper right')

# Настройка внешнего вида
ax.spines['top'].set_visible(False)
ax.spines['right'].set_visible(False)
ax.spines['left'].set_linewidth(2)
ax.spines['bottom'].set_linewidth(2)

plt.tight_layout()
plt.show()

# Вывод информации о параметрах
print(f"Пороговое значение U_ВХ1.К: {U_VX1_K} В")
print(f"Пороговое значение U_ВХ2.К: {U_VX2_K} В")
print(f"Опорное напряжение U_ОП: {U_OP:.3f} В")
print(f"Ширина гистерезиса U_Г: {U_G:.3f} В")
print(f"Высокий выходной уровень: {U_OUT_HIGH} В")
print(f"Низкий выходной уровень: {U_OUT_LOW} В")