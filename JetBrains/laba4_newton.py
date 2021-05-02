import numpy as np
x_ = [0.079, 0.637, 1.345, 2.095, 2.782]
y_ = [-4.308, -0.739, 1.697, 4.208, 6.203]
def func(x):
    return newton_polynomial(x_, y_, x)
def _poly_newton_coefficient(x, y):
    """
    x: list or np array contanining x data points
    y: list or np array contanining y data points
    """

    m = len(x)

    x = np.copy(x)
    a = np.copy(y)
    for k in range(1, m):
        a[k:m] = (a[k:m] - a[k - 1])/(x[k:m] - x[k - 1])
    return a

def newton_polynomial(x_data, y_data, x):
    """
    x_data: data points at x
    y_data: data points at y
    x: evaluation point(s)
    """
    a = _poly_newton_coefficient(x_data, y_data)
    n = len(x_data) - 1  # Degree of polynomial
    p = a[n]

    for k in range(1, n + 1):
        p = a[n - k] + (x - x_data[n - k])*p

    return p
def rectangle_rule(a, b, n):
    h = abs(a-b)/n
    sum=0
    for i in range(0, n):
        sum+=h*(a+h*i)
    return sum
def trapezoid_rule(a, b, n):
    h = abs(a - b) / n
    sum = 0
    for i in range(0, n):
        sum += h * (func(a + h * i)+func(a+h*(i+1))) * 0.5
    return sum
def simpsone_rule(a, b, n):
    h = abs(a - b) / n
    sum = func(a) + func(b)
    for i in range(2, n, 2):
        sum+=2*func(a+i*h)
    for i in range(1, n, 2):
        sum += 4 * func(a + i * h)
    sum*=h/3
    return sum
def runge_rule(a, b, rule, n0, o, e):
    I1=0
    I2=100*e
    while o*(I2-I1) > e:
        I2=rule(a, b, n0)
        I1 =rule(a, b, 2*n0)
        n0*=2
    return I1, I2
a=x_[0]
b=x_[len(x_)-1]
e=0.001
n0=10
print(runge_rule(a, b, rectangle_rule, n0, float(1/3), e))
print("\n")
print(runge_rule(a, b, trapezoid_rule, n0, float(1/3), e))
print("\n")
print(runge_rule(a, b,simpsone_rule, n0, float(1/15), e))
print("\n")