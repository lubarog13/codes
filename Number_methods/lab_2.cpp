#include <iostream>
#include <cmath>
const int n=3;

int lab_2() {
    double C[n][n+1];
    std::cout<<"Enter system: "<<std::endl;
    for(int i=0;i<n;i++){
        for (int (j) = 0; (j) < n; ++(j)) {
            std::cin>>C[i][j];
        }
    }
    C[1][1] = ((-1)*C[1][1] + 8)/8;
    C[2][2] = ((-1)*C[2][2] + 8)/8;
        for(int j=0; j<n; j++){
            if(0==j) C[0][0]= ((-1)*C[0][j] + 10)/10;
            else C[0][j] = (-1)*C[0][j]/10;
            C[1][j]  = (-1)*C[1][j]/8;
            C[2][j]  = (-1)*C[2][j]/18;
        }
    std::cout<<"Enter b"<<std::endl;
    for(int i=0;i<n;i++){
        std::cin>>C[i][n];
    }
    C[0][n]/=10;
    C[1][n]/=8;
    C[2][n]/=8;
    for(int i=0;i<n;i++){
        for (int (j) = 0; (j) < n+1; ++(j)) {
            std::cout<<C[i][j]<<" ";
        }
        std::cout<<std::endl;
    }
    double x0[n] ={0, 0, 0};
    double x[n] ={0, 0, 0};
    double k=0, e=0.001;
    float sum, max=0;
    for(int i=0; i<n; i++){
        sum=0;
        for (int j = 0; j < n; j++) {
            sum+=fabs(C[j][i]);
        }
        if(sum>max) max=sum;
    }
    if(max>1) std::cout << "Matrix doesn't well" << std::endl;
    else{
        for(int i=0; i<n;i++){
            x[i]=C[i][n];
        }
        while((max/(1-max))*fabs(x[0]-x0[0])>e){
            k++;
            for(int i=0;i<n;i++){
                x0[i]=x[i];
                x[i]=0;
                int m=0;
                for (int j=0; j<n; j++){
                    if(j<m) x[i]+=C[i][j] * x[m];
                    else x[i] += C[i][j] * x0[j];
                    m++;
                }
                x[i]+=C[i][n];
            }
        }
    }
    std::cout<<"X answer: ";
    for (int i = 0; i < n; ++i) {
        std::cout<<x[i]<<" ";
    }

    std::cout<<std::endl<<"Iterations: "<<k;
    return 0;
}
