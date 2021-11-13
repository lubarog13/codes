#include <iostream>
#include <iomanip>
#include <math.h>

using namespace std;

double f(double x) {
return pow(exp(x), (-0.5))-pow((x-0.8), 2) + 0.1;
}

double f1(double x) {
return exp(-0.5*x)*(0.5)-2*x+1.6;
}

int main()
{
double x;
double e = 0.001;
int a = -2;
int b = 2;
int c=(a + b) / 2;
int k = 0;

do {
c = x;
cout<<c<<endl;
x = c - f(x) / f1(x);
k++;
}
while (fabs(c - x) > e);

cout<< "x="<< x <<endl;
cout << "k= " << k << endl;
}