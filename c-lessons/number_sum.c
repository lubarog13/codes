#include <stdio.h>

int main(void) {
    int a, b, c, k=0;
    char cont;

    do {
        printf("\nвведите целое a=");
        k=scanf("%d", &a);
        while(!(k)) {
            printf("\nвведите целое a=");
            fflush(stdin);
            k=scanf("%d", &a);
        }
         printf("\nвведите целое b=");
        k=scanf("%d", &b);
        while(!(k)) {
            printf("\nвведите целое b=");
            fflush(stdin);
            k=scanf("%d", &b);
        }
        c=a+b;
        printf("\nсумма = %d\n", c);
        do {
        printf("Хотите продолжать? (y/n)\n");
        k = scanf("%s", &cont);
        } while(!k);
    } while (!(cont=='n'||cont=='N'));
}
