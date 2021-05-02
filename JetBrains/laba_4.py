import math
def func(x):
    return x*math.atan(x)
def _rectangle_rule(func, a, b, nseg, frac):
    """Обобщённое правило прямоугольников."""
    dx = 1.0 * (b - a) / nseg
    sum = 0.0
    xstart = a + frac * dx # 0 <= frac <= 1 задаёт долю смещения точки,
                           # в которой вычисляется функция,
                           # от левого края отрезка dx
    for i in range(nseg):
        sum += func(xstart + i * dx)

    return sum * dx

def left_rectangle_rule(func, a, b, nseg):
    """Правило левых прямоугольников"""
    return _rectangle_rule(func, a, b, nseg, 0.0)

def right_rectangle_rule(func, a, b, nseg):
    """Правило правых прямоугольников"""
    return _rectangle_rule(func, a, b, nseg, 1.0)

def midpoint_rectangle_rule(func, a, b, nseg):
    """Правило прямоугольников со средней точкой"""
    return _rectangle_rule(func, a, b, nseg, 0.5)
def trapezoid_rule(func, a, b, rtol = 0.01, nseg0 = 10):
    nseg = nseg0
    old_ans = 0.0
    dx = 1.0 * (b - a) / nseg
    ans = 0.5 * (func(a) + func(b))
    for i in range(1, nseg):
        ans += func(a + i * dx)

    ans *= dx
    err_est = max(1, abs(ans))
    while (err_est > abs(rtol * ans)):
        old_ans = ans
        ans = 0.5 * (ans + midpoint_rectangle_rule(func, a, b, nseg)) # новые точки для уточнения интеграла
                                                                      # добавляются ровно в середины предыдущих отрезков
        nseg *= 2
        err_est = abs(ans - old_ans)

    return ans