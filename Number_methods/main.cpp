#include <iostream>
#include <cmath>
const int n=4;
float searchX(float c[], float x[]){
    float newX =0;
    for (int i=0; i<n; i++){
        newX +=c[i]* x[i];
    }
    newX+=c[n];
    return newX;
}

int main() {
    float C[n][n+1] = {0.22, (-0.14), 0.06, (-0.16), 0,
                   0.12, 0, 0.32, (-0.18), 0,
                   0.08, (-0.12), 0.23, 0.32, 0,
                   0.25, 0.21, 0.19, 0, 0};
    float b[n]={1.27, -0.78, -0.58, 1.51};
    float x0[n] ={0, 0, 0, 0};
    float x[n] ={0, 0, 0, 0};
    int k=0, e=0.001;
    float sum, max=0;
    for(int i=0; i<n; i++){
        sum=0;
        for (int j = 0; j < n; j++) {
            sum+=fabs(C[i][j]);
        }
        if(sum>max) max=sum;
    }
    if(max>1) std::cout << "Matrix doesn't well" << std::endl;
    else{
        for(int i=0; i<n;i++){
            x[i]=b[i];
        }
        for(int i=0; i<n;i++){
            C[i][n]=b[i];
        }
        while(max/(1-max)*fabs(x[0]-x0[0])>e){
            k++;
            for(int i=0;i<n;i++){
                x0[i]=x[i];
                x[i]=0;
                for (int j=0; j<n; j++){
                    x[i] += C[i][j] * x0[j];
                }
                x[i]+=C[i][n];
            }
        }
    }
    for (int i = 0; i < n; ++i) {
        std::cout<<x[i]<<" ";
    }
    std::cout<<std::endl<<k;
    return 0;
}
