import math
def func(x):
    return x*math.acos(x)
def rectangle_rule(a, b, n):
    h = abs(a-b)/n
    sum=0
    for i in range(0, n):
        sum+=h*func(a+h*i)
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
a=-0.5
b=0.5
e=0.001
n0=10
print(runge_rule(a, b, rectangle_rule, n0, float(1/3), e))
print("\n")
print(runge_rule(a, b, trapezoid_rule, n0, float(1/3), e))
print("\n")
print(runge_rule(a, b,simpsone_rule, n0, float(1/15), e))
print("\n")