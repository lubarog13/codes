#include <iostream>
using namespace std;
 
// For searching in all 8 direction
int x[] = { -1, -1, -1,  0, 0,  1, 1, 1 };
int y[] = { -1,  0,  1, -1, 1, -1, 0, 1 };
int notSearch[100][2];
int countNS = 0;

bool canSearch (int row, int col){
    for (int i=0; i<countNS; i++){
        if(notSearch[i][0] == row && notSearch[i][1]== col){
            return false;
        }
    }
    return true;
}
bool seachOnElemnt(char *grid, int row, int col, char k, int C) {
    if (*(grid+row*C+col) == k && canSearch(row, col)){
        notSearch[countNS][0] = row;
        notSearch[countNS][1] = col; 
        countNS++;
        return true;
    }
    return false;
}
// This function searches in
// all 8-direction from point
// (row, col) in grid[][]
bool searchIn8 (char *grid, int row, int col, char k, int R, int C){
    int izn_col=col, izn_row=row;
    if (row<R){
    row+=1;
    if (seachOnElemnt(grid, row, col, k, C)) return true;
    
    if(col<C){
        col+=1;
        if (seachOnElemnt(grid, row, col, k, C)) return true;
        if(col>0){
            col-=2;
            if (seachOnElemnt(grid, row, col, k, C)) return true;
        }
    } else if (col>0){
        col-=1;
        if (seachOnElemnt(grid, row, col, k, C)) return true;
    }
    row=izn_row;
    col=izn_col;
    }
    if(col<C){
        col+=1;
        if (seachOnElemnt(grid, row, col, k, C)) return true;
        if(col>0){
            col-=2;
            if (seachOnElemnt(grid, row, col, k, C)) return true;
        }
    } else if (col>0){
        col-=1;
        if (seachOnElemnt(grid, row, col, k, C)) return true;
    }
    col=izn_col;
    if(row> 0){
        row-=1;
    if (seachOnElemnt(grid, row, col, k, C)) return true;
    
    if(col<C){
        col+=1;
        if (seachOnElemnt(grid, row, col, k, C)) return true;
        if(col>0){
            col-=2;
            if (seachOnElemnt(grid, row, col, k, C)) return true;
        }
    } else if (col>0){
        col-=1;
        if (seachOnElemnt(grid, row, col, k, C)) return true;
    }
    }
    return false;
}

void clearArr(){
    for(int i=0; i<countNS; i++){
        notSearch[i][0]=0;
        notSearch[i][1]=0;
    }
    countNS = 0;
}

bool seach(char *grid, int row, int col, string word, int R, int C){
    if (*(grid+row*C+col) != word[0])
        return false;
 
    int len = word.length();
    notSearch[0][0] = row;
    notSearch[0][1] = col;
    countNS = 1;
    for(int i=1; i<len; i++){
        if(!searchIn8(grid, row, col, word[i], R, C)){
            clearArr();
            return false;
        } else {
            col=notSearch[countNS-1][1];
            row=notSearch[countNS-1][0];
        }
    }
    clearArr();
    return true;
}
 
// Searches given word in a given
// matrix in all 8 directions
void patternSearch(char *grid, string word,
                  int R, int C)
{
    // Consider every point as starting
    // point and search given word
    for (int row = 0; row < R; row++)
        for (int col = 0; col < C; col++)
            if (seach(grid, row, col, word, R-1, C-1))
                cout << "pattern found at "
                     << row << ", "
                     << col << endl;
}
 
// Driver program
int main()
{
      int R = 3, C = 14;
    char grid[3][14] = {"GEEKSFORGEEKS",
                        "GEEKSQUIZGEEK",
                        "IDEQAPRACTICE"};
 
    patternSearch((char *)grid, "GEEKS", R, C);
    cout << endl;
    patternSearch((char *)grid, "EEE", R, C);
    cout << endl;
    patternSearch((char *)grid, "IDE", R, C);
    return 0;
}