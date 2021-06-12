# include <iostream>

int main (){
    long count=0, max=0;
    /*for (long i = 3361; i <= 9205; i++)
    {
        if((i%4==0 || i%5==0) && i%9!=0 && i%11!=0 && i%17!=0 && i%23!=0){
            count++;
            max=i;
        }
    }
    std::cout<<count<<max;*/
    for (long i=489421; i<=489440; i++){
        count=0;
        for (long j=1; j<=i; j++){
            if(i%j==0) count++;
        }
        if(count==4){
            for (long j=1; j<=i; j++){
            if(i%j==0) std::cout<<j<<" ";
            }
            std::cout<<std::endl;
        }
    }
    return 0;
}