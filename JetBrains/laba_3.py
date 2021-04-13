import numpy as np
import scipy.interpolate
import matplotlib.pyplot as plt

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