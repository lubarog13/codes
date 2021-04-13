#include <iostream>
#include <cmath>
const int n=/*4*/3;

int main() {
    /*double C[n][n+1] = {0.22, (-0.14), 0.06, (-0.16), 0,
                        0.12, 0, 0.32, (-0.18), 0,
                        0.08, (-0.12), 0.23, 0.32, 0,
                        0.25, 0.21, 0.19, 0, 0};
    double b[n]={1.27, -0.78, -0.58, 1.51};
    double x0[n] ={0, 0, 0, 0};
    double x[n] ={0, 0, 0, 0};
    double k=0, e=0.001;
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
        while((max/(1-max))*fabs(x[0]-x0[0])>e){
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
    std::cout<<"X answer: ";
    for (int i = 0; i < n; ++i) {
        std::cout<<x[i]<<" ";
    }

    std::cout<<std::endl<<"Iterations: "<<k;
    return 0;*/
    double C[n][n+1];
    std::cout<<"Enter system: "<<std::endl;
    for(int i=0;i<n;i++){
        for (int (j) = 0; (j) < n; ++(j)) {
            std::cin>>C[i][j];
        }
    }
    C[0][0] = ((-1)*C[0][0] + 10)/10;
    C[1][1] = ((-1)*C[1][1] + 10)/10;
    C[2][2] = ((-1)*C[2][2] + 10)/(10);
    for(int j=1; j<n; j++){
        C[0][j]  = (-1)*C[0][j]/10;
    }
    for(int i=0; i<n; i++){
        if (i!=1) C[1][i]  = (-1)*C[1][i]/10;
    }
    for(int i=0; i<n; i++){
        if (i!=2) C[2][i]  = (-1)*C[2][i]/(10);
    }
    std::cout<<"Enter b"<<std::endl;
    for(int i=0;i<n;i++){
        std::cin>>C[i][n];
    }
    /* float buf;
    for(int i=0;i<n;i++){
        buf = C[1][i];
        C[1][i] = C[2][i];
        C[2][i] = buf;
    }
    for(int i=0; i<n; i++){
        for(int j=0;j<n;j++){
            C[i][j]/=(-1)*C[i][i]
        }
        C[i][n]/=C[i][i];
        C[i][i]=0;
    } */
    C[0][n]/=10;
    C[1][n]/=10;
    C[2][n]/=(10);
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
    std::cout<<max<<std::endl;
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
                    //2
                   /*  if(j<m) x[i]+=C[i][j] * x[m];
                    else x[i] += C[i][j] * x0[j]; 
                    m++;
                    for (int i = 0; i < n; ++i) {
                        std::cout<<x[i]<<" ";
                    } */
                    //std::cout<<std::endl;
                    //1
                    x[i] += C[i][j] * x0[j];
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