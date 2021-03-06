import numpy as np
import scipy.interpolate
import matplotlib.pyplot as plt
def one_diff(x, y):
    a = np.copy(x[1:len(x)-1])
    for k in range(1, len(x)-1):
        a[k-1] = (y[k+1]-y[k-1])/(x[k+1]-x[k-1])
    return a
def double_diff(x, y):
    a = one_diff(x, y)
    a_new = one_diff(x[1:len(x)-1], a)
    return a_new
def newton_end_sub(x, y):
    """
        x: list or np array contanining x data points
        y: list or np array contanining y data points
        """

    m = len(x)

    x = np.copy(x)
    a = np.copy(y)
    print("Таблица конечных разностей: ")
    print(x)
    print(y)
    for k in range(1, m):
        a[k:m] = (a[k:m] - a[k - 1])
        print(a[k:m])
def _poly_newton_coefficient(x, y):
    """
    x: list or np array contanining x data points
    y: list or np array contanining y data points
    """

    m = len(x)

    x = np.copy(x)
    a = np.copy(y)
    print("Таблица разделенных разностей: ")
    print(x)
    print(y)
    for k in range(1, m):
        a[k:m] = (a[k:m] - a[k - 1])/(x[k:m] - x[k - 1])
        print(a[k:m])
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
x = [0.079, 0.637, 1.345, 2.095, 2.782]
y = [-4.308, -0.739, 1.697, 4.208, 6.203]
x_parabola = np.array(x) # array for x
y_parabola = np.array(y) # array for y
newton_end_sub(x, y)
p = newton_polynomial(x, y, 1)
print("Значение функции в точке: " + p.__str__())
plt.figure()
u = plt.plot(x_parabola,y_parabola,'ro') # plot the points
t = np.linspace(0, 1, len(x_parabola)) # parameter t to parametrize x and y
pxLagrange = scipy.interpolate.lagrange(t, x_parabola) # X(T)
pyLagrange = scipy.interpolate.lagrange(t, y_parabola)
xdSpline = scipy.interpolate.UnivariateSpline(x, y, w=None,  bbox = [None, None] , k=4, ext=0, check_finite=False)
n = 100
ts = np.linspace(t[0],t[-1],n)
x_data = [1.25, 1.27, 1.29, 1.31, 1.33, 1.35, 1.37, 1.39, 1.41]
y_data = np.array(x_data)
for k in range (0, len(x)):
    y_data[k] = newton_polynomial(x, y, x_data[k])
print("Производные: ")
print(one_diff(x_data, y_data))
print(double_diff(x_data, y_data))
xLagrange = pxLagrange(ts) # lagrange x coordinates
yLagrange = pyLagrange(ts) # lagrange y coordinates
plt.plot(xLagrange, yLagrange,'b-',label = "Polynomial")
plt.plot(x, y, 'o-.r', label="second", mec='r')
ts = np.linspace(0,3,n)
plt.plot(ts, xdSpline(ts), ',-.g', label="third")
plt.plot(ts, newton_polynomial(x, y, ts), 'b-',label = "Newton")
plt.legend()
plt.grid(True)
plt.show()
