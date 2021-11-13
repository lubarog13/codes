#include <iostream>
#include <random>

#include<cstdlib>

class sudoku
{
private:
    int table[9][9] = {1, 2, 3, 4, 5, 6, 7, 8, 9,
             4, 5, 6, 7, 8, 9, 1, 2, 3,
             7, 8, 9, 1, 2, 4, 4, 5, 6,
             2, 3, 4, 5, 6, 7, 8, 9, 1,
             5, 6, 7, 8, 9, 1, 2, 3, 4,
             8, 9, 1, 2, 3, 4, 5, 6, 7,
             3, 4, 5, 6, 7, 8, 9, 1, 2,
             6, 7, 8, 9, 1, 2, 3, 4, 5,
             9, 1, 2, 3, 4, 5, 6, 7, 8};
    char play_matrix[9][9] = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
                              ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
                              ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
                              ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
                              ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
                              ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
                              ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
                              ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
                           ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',};
public:
    sudoku(/* args */);
    ~sudoku();
    void generate();
    void transposing();
    void swap_rows(int a, int b);
    void swap_columns(int a, int b);
    void swap_area_rows(int a, int b);
    void swap_area_columns(int a, int b);
    void show();
    void show_first();
    void input();
    
};

sudoku::sudoku(/* args */)
{
    generate();

}

sudoku::~sudoku()
{
}
void sudoku::generate(){
    int row1, row2, col1, col2;
    std::random_device random_device; 
    std::mt19937 generator(random_device()); 

    std::uniform_int_distribution<> distribution(1, 5); 
    std::uniform_int_distribution<> distribution1(1, 3);
    std::uniform_int_distribution<> distribution_while(0, 50);
    std::uniform_int_distribution<> distribution3(0, 2);
    std::uniform_int_distribution<> distribution2(0, 2);

    int d_w = distribution_while(generator);
    int i=0;
    while (i!=d_w){
    int x = distribution(generator);
    int f_r_c = distribution1(generator);
    int l_r_c = distribution1(generator);
    switch (x)
    {
    case 1:
        transposing();

        break;
    case 2:
        swap_area_rows(f_r_c, l_r_c);
        break;
    case 3:
        swap_area_columns(f_r_c, l_r_c);

        break;
    case 4:
         row1 = distribution3(generator) * 3;
         row2 = distribution2(generator);
         swap_rows(row1, row1+row2);
        
         break;
    case 5:
         col1 = distribution3(generator) * 3;
         col2 = distribution2(generator);
         
         swap_columns(col1, col2+col1);
        
         break;
    default:
        break;
    }
    i++;
    }
}
void sudoku::show_first(){
    std::random_device random_device; 
    std::mt19937 generator(random_device()); 
    int x;
    int k=1;
    int j;
    std::uniform_int_distribution<> distribution(0, 8);
    std::uniform_int_distribution<> distribution1(1, 8);
    for (int i=0;i<9;i++){
        x = distribution(generator);
        while (k!=x)
        {
            j = distribution1(generator);
            play_matrix[i][j] = char(table[i][j] + '0');
            k++;
        }
        k=0;
    }
    show();
}
void sudoku::show(){
    std::cout<<"Play matrix"<<std::endl;
    for (int i=0; i<9; i++){
            std::cout<<"-------------------"<<std::endl;
    for(int t=0;t<9;t++){
            std::cout<<"|"<<play_matrix[i][t];
        }
        std::cout<<"|"<<std::endl;
    }
}
void sudoku::transposing(){
    int buf_table[9][9];
    for (int i =0; i<9;i++){
        for(int j=0; j<9; j++){
            buf_table[i][j] = table[j][i];
        }
    }
    for (int i =0; i<9;i++){
        for(int j=0; j<9; j++){
            table[i][j] = buf_table[i][j];
        }
    }
}
void sudoku::swap_area_rows(int a, int b){
    int buf_table[3][9];
    int f1_r, l1_r, f2_r, l2_r;
    switch (a)
    {
    case 1:
        f1_r = 0;
        l1_r = 2;
        break;
    case 2:
        f1_r = 3;
        l1_r = 5;
        break;
    case 3:
        f1_r = 6;
        l1_r = 8;
        break;
    default:
        break;
    }
    switch (b)
    {
    case 1:
        f2_r = 0;
        l2_r = 2;
        break;
    case 2:
        f2_r = 3;
        l2_r = 5;
        break;
    case 3:
        f2_r = 6;
        l2_r = 8;
        break;
    default:
        break;
    }
    int k=0;
    for (int i=f1_r; i<l1_r+1; i++){
        for(int j=0; j<9; j++){
            buf_table[k][j] = table[i][j];
        }
        k++;
    }
    k = f2_r;
    for (int i=f1_r; i<l1_r+1; i++){
        for(int j=0; j<9; j++){
            table[i][j] = table[k][j];
        }
        k++;
    }
    k = 0;
    for (int i=f2_r; i<l2_r+1; i++){
        for(int j=0; j<9; j++){
            table[i][j] = buf_table[k][j];
        }
        k++;
    }
}
void sudoku::swap_area_columns(int a, int b){
    int buf_table[9][3];
    int f1_c, l1_c, f2_c, l2_c;
    switch (a)
    {
    case 1:
        f1_c = 0;
        l1_c = 2;
        break;
    case 2:
        f1_c = 3;
        l1_c = 5;
        break;
    case 3:
        f1_c = 6;
        l1_c = 8;
        break;
    default:
        break;
    }
    switch (b)
    {
    case 1:
        f2_c = 0;
        l2_c = 2;
        break;
    case 2:
        f2_c = 3;
        l2_c = 5;
        break;
    case 3:
        f2_c = 6;
        l2_c = 8;
        break;
    default:
        break;
    }
    int k=0;
    for (int i=0; i<9; i++){
        for(int j=f1_c; j<l1_c+1; j++){
            buf_table[i][k] = table[i][j];
            k++;
        }
        k=0;
    }
    k = f2_c;
    for (int i=0; i<9; i++){
        for(int j=f1_c; j<l1_c+1; j++){
            table[i][j] = table[i][k];
            k++;
        }
        k=f2_c;
    }
    k = 0;
    for (int i=0; i<9; i++){
        for(int j=f2_c; j<l2_c+1; j++){
            table[i][j] = buf_table[i][k];
            k++;
        }
        k=0;
    }
}
void sudoku::swap_rows(int a, int b){
    int buf[9];
    for (int i= 0; i<9; i++){
        buf[i]=table[a][i];
    }
    for (int i= 0; i<9; i++){
        table[a][i]=table[b][i];
    }
    for (int i= 0; i<9; i++){
        table[b][i]=buf[i];
    }
}
void sudoku::swap_columns(int a, int b){
    int buf[9];
    for (int i= 0; i<9; i++){
        buf[i]=table[i][a];
    }
    for (int i= 0; i<9; i++){
        table[i][a]=table[i][b];
    }
    for (int i= 0; i<9; i++){
        table[i][b]=buf[i];
    }
}
void sudoku::input(){
    std::cout<<"Enter the position by two numbers"<<std::endl;
    int x=0, y=0;
    char count=0;
    while(x<1 || x>9){
    std::cout<<"Input row"<<std::endl;
    std::cin>>x;
    }
    while(y<1 || y>9){
    std::cout<<"Input column"<<std::endl;
    std::cin>>y;
    }
    while(count<'1' || count>'9'){
    std::cout<<"Input number by 1 to 9"<<std::endl;
    std::cin>>count;
    }
    play_matrix[x-1][y-1] = count;
    show();
}

int main(){
    sudoku s;
    s.show_first();
    s.input();
}