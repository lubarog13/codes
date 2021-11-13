import math

import numpy as np

# класс подсчета интеграла
class Integral(object):
    # конструктор принимает на вход пределы интегрирования, функцию или таблицу xi
    def __init__(self, a, b, func=None, x_data=None, y_data=None):
        self.a = a
        self.b = b
        if func is not None:
            self.func = func
        else:
            self.func = self.newton_polynomial
        self.x_ = x_data
        self.y_ = y_data

    # коэффиценты полинома ньютона
    def _poly_newton_coefficient(self, x, y):

        m = len(x)

        x = np.copy(x)
        a = np.copy(y)
        for k in range(1, m):
            a[k:m] = (a[k:m] - a[k - 1]) / (x[k:m] - x[k - 1])
        return a

    # функция полинома ньютона
    def newton_polynomial(self, x):

        a = self._poly_newton_coefficient(self.x_, self.y_)
        n = len(self.x_) - 1  # Degree of polynomial
        p = a[n]

        for k in range(1, n + 1):
            p = a[n - k] + (x - self.x_[n - k]) * p

        return p

    # правило прямоугольников
    def rectangle_rule(self, n):
        h = abs(self.a - self.b) / n
        sum = 0
        for i in range(0, n+1):
            sum += h * self.func(self.a + h * i)
        return sum

    # правило трапеций
    def trapezoid_rule(self, n):
        h = abs(self.a - self.b) / n
        sum = 0
        for i in range(0, n+1):
            sum += h * (self.func(self.a + h * i) + self.func(self.a + h * (i + 1))) * 0.5
        return sum

    # правило Симпсона
    def simpsone_rule(self, n):
        h = abs(self.a - self.b) / n
        sum = self.func(self.a) + self.func(self.b)
        for i in range(2, n, 2):
            sum += 2 * self.func(self.a + i * h)
        for i in range(1, n, 2):
            sum += 4 * self.func(self.a + i * h)
        sum *= h / 3
        return sum

    # определение количества шагов методом Рунге
    def runge_rule(self, rule, n0, o, e):
        I1 = 0
        I2 = 100 * e
        while o * (abs(I2 - I1)) > e:
            I2 = rule(n0)
            I1 = rule(2 * n0)
            n0 *= 2
        return I1, I2
def func(x):
    return x * math.acos(x)

x = [0.079, 0.637, 1.345, 2.095, 2.782]
y = [-4.308, -0.739, 1.697, 4.208, 6.203]
a = x[0]
b = x[len(x) - 1]
e = 0.001
n0 = 10
integral = Integral(-0.5, 0.5, func)
print(integral.runge_rule(integral.rectangle_rule, n0, float(1 / 3), e))
print("\n")
print(integral.runge_rule(integral.trapezoid_rule, n0, float(1 / 3), e))
print("\n")
print(integral.runge_rule(integral.simpsone_rule, n0, float(1 / 15), e))
print("\n")
newton_integral = Integral(a, b, None, x, y)
print(newton_integral.runge_rule(newton_integral.rectangle_rule, n0, float(1 / 3), e))
print("\n")
print(newton_integral.runge_rule(newton_integral.trapezoid_rule, n0, float(1 / 3), e))
print("\n")
print(newton_integral.runge_rule(newton_integral.simpsone_rule, n0, float(1 / 15), e))
print("\n")
# По проверке на онлайн-калькуляторе метод симпсона - самый точный