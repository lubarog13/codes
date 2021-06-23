#include <iostream>
#include <math.h>
/*var
    simple, i, j, c, sqrtI: longint;
begin
    for i := 35000000 to 40000000 do begin
        c:=i;
        while c mod 2 = 0 do begin
          c:=c div 2;
          end;
        sqrtI := round(sqrt(c));
        sqrtI:= round(sqrt(sqrtI));
        if sqrtI * sqrtI * sqrtI * sqrtI = c then begin
          simple:=1;
          for j:=3 to round(sqrt(sqrtI)) do begin
            if sqrtI mod j=0 then begin
              simple:=0;
              break;
              end;
            end;
          if simple=1 then writeln(i);
          end;
        end;
end.*/

int main(){
    long count=0, c, sqrtI, simple;
    for(long i=45000000; i<=50000000; i++){
        c=i;
        while(c%2==0){
            c= c/2;
        }
        if(c!=1){
        sqrtI = sqrt(c);
        sqrtI = sqrt(sqrtI);
        if (sqrtI*sqrtI*sqrtI*sqrtI==c){
            simple=1;
            for(int j=3; j<=sqrt(sqrtI); j++){
                if(sqrtI%j==0){
                    simple =0;
                    break;
                }
            }
            if(simple==1){
                std::cout<<i<<std::endl;
            }
        }}
    }
    return 0;
}