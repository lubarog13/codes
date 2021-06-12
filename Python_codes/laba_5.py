import math
import matplotlib.pyplot as plt
import numpy as np
def func(x, y):
    d = 2*x*pow(y,2)+y
    return d
def func_right(x):
    y=-1*math.exp(x)/(-1*math.exp(-1)+2*math.exp(x)*(x-1))
    return y
def runge_meth_in_dot(x0, y0, h):
    f1=func(x0, y0)
    f2=func(x0+h/2, y0+h*0.5*f1)
    f3=func(x0+h/2, y0+0.5*h*f2)
    f4 = func(x0+h, y0+h*0.5*f3)
    y = y0+h/6*(f1+2*f2+2*f3+f4)
    return y
def runge_meth(a,b,n,y0):
    h=(float)(b-a)/n
    ypoints=[]
    xpoints=np.arange(a, b, h)
    y=y0
    for x in xpoints:
        ypoints.append(y)
        y = runge_meth_in_dot(x, y, h)
    return ypoints
def meth_eiler(a, b, n, y0):
    h = (float) (b - a) / n
    ypoints = []
    xpoints = np.arange(a, b, h)
    y = y0
    for x in xpoints:
        ypoints.append(y)
        y = y+h*func(x, y)
    return ypoints
def search_n(a, b, y0, e):
    h0 = math.pow(e, 0.25)
    print(h0)
    y1 = runge_meth_in_dot(a, y0, h0)
    y_1 = runge_meth_in_dot(a, y0, 2 * h0)
    y2 = runge_meth_in_dot(a + h0, y1, h0)
    print("N = ")
    if abs(y2- y_1)<e:
        while abs(y2-y_1)<e:
            h0=2*h0
            y1 = runge_meth_in_dot(a, y0, h0)
            y_1 = runge_meth_in_dot(a, y0, 2 * h0)
            y2 = runge_meth_in_dot(a + h0, y1, h0)
        n = (b - a) / h0
        if (n % 2 != 0):
            n = n + pribl(a, b, n, e, y0)
        else:
            n=2*n
        print(n)
    elif abs(y2- y_1)>e:
        while abs(y2-y_1)>e:
            h0 = h0 / 2
            print(h0)
            y1 = runge_meth_in_dot(a, y0, h0)
            y_1= runge_meth_in_dot(a, y0, 2*h0)
            y2 = runge_meth_in_dot(a + h0, y1, h0)
            print(abs(y2-y_1))
        n = (b - a) / h0
        if(n % 2 != 0):
            n=n+pribl(a, b, n, e, y0)
        print(n)
    else:
        n = (b - a) / h0
        if (n % 2 != 0):
            n = n + pribl(a, b, n, e, y0)
        print(n)
    return n
def pribl(a, b, n, e, y0):
    h0=(float) (a-b)/(n+1)
    y1 = runge_meth_in_dot(a, y0, h0)
    y_1=runge_meth_in_dot(a, y0, 2*h0)
    if(abs(runge_meth_in_dot(a + h0, y1, h0) - y_1)<e):
        return 1
    else:
        return -1
a=-1
b=0.6
y0=0.2
e=0.0001
n = search_n(a, b, y0, e)
h=0.01
n=(float)(b-a)/h
xpoints=np.arange(a, b, h)
ypoints = runge_meth(a, b, n, y0)
print("yi in Runge-Kutt: ")
print(ypoints)
plt.figure()
plt.plot(xpoints, ypoints,'b-',label = "Метод Рунге-Кутта")
y_points = meth_eiler(a, b, n, y0)
print("yi in Eiler: ")
print(ypoints)
plt.plot(xpoints, y_points,',-.g',label = "Метод Эйлера")
max=0
for i in range(0, len(y_points)):
    if(abs(y_points[i]-ypoints[i])>max):
        max=abs(y_points[i]-ypoints[i])
print("MAX delta: ")
print(max)
ypoints_=[]
for x in xpoints:
    ypoints_.append(func_right(x))
print("Right solution:")
print(ypoints_)
plt.plot(xpoints, ypoints_, ',-.r', label="Точное решение")
max=0
for i in range(0, len(y_points)):
    if(abs(ypoints[i]-ypoints_[i])>max):
        max=abs(ypoints[i]-ypoints_[i])
print("MAX delta: ")
print(max)
plt.legend()
plt.grid(True)
plt.show()