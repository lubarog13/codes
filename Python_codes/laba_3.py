import numpy as np
import cProfile
import scipy.interpolate
import matplotlib.pyplot as plt
def newton_end_sub(x, y):
    """
        x: list or np array contanining x data points
        y: list or np array contanining y data points
        """

    m = len(x)

    x = np.copy(x)
    a = np.copy(y)
    for k in range(1, m):
        a[k:m] = (a[k:m] - a[k - 1])

    return a
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
n = int(input("Input n"))
x=[0]*n
y=[0]*n
print("Input x\n")
for i in range(0,n):
    x[i]  = float(input())
print("input y\n")
for i in range(0, n):
    y[i]  = float(input())
x_parabola = np.array(x) # array for x
y_parabola = np.array(y) # array for y
plt.figure()
u = plt.plot(x_parabola,y_parabola,'ro') # plot the points
t = np.linspace(0, 1, len(x_parabola)) # parameter t to parametrize x and y
pxLagrange = scipy.interpolate.lagrange(t, x_parabola) # X(T)
pyLagrange = scipy.interpolate.lagrange(t, y_parabola) # Y(T)
n = 100
ts = np.linspace(t[0],t[-1],n)
xLagrange = pxLagrange(ts) # lagrange x coordinates
yLagrange = pyLagrange(ts) # lagrange y coordinates
plt.plot(xLagrange, yLagrange,'b-',label = "Polynomial")
plt.show()