package com.gq;

public class Main {
    enum action{
        up, left, down, right
    }
    private static float[][] U = new float[][]{
            {0, 0, 0, 1},
            {0, 0, 0, -1},
            {0, 0, 0, 0}};

    public static void main(String[] args) {
        float theta = 0f;
        float R = -0.04f;
        float[][] pa = new float[][]{{0.8f}, {0.1f}, {0.1f}};
        int i = 2;
        int j = 0;
        int k = 0;
        float temp;

        do {
            temp = U[i][j];
            if (!((i ==0 && j==3)||(i ==1 && j==3))){
                float[][] actionArr = multiMatrix(actionArr(i+1, j+1), 4, 3, pa, 3, 1);
                printArr(actionArr, 4, 1);
                U[i][j] = R + Max(actionArr);
            }
            if ((U[i][j] - temp) > theta)
                theta = U[i][j] - temp;
            i--;
            if (i < 0){
                i = 2;
                j++;
            }
            if (j > 3){
                j=0;
            }
//            printArr(U, 3, 4);
            k++;
        }while (k<5);
//        }while (theta > 0.01f || theta < 0.01f);

    }


    private static float Max(float[][] arr){
        float maximum = arr[0][0];
        for (float f[] : arr) {
            for (float item : f) {
                if (item > maximum)
                    maximum = item;
            }
        }
        return maximum;
    }

    private static void printArr(float[][] arr, int row, int column){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print(arr[i][j] + "\t\t");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    private static float[][] actionArr(int row, int column){
        float[][] a = new float[4][3];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 3; j++){
//                System.out.println(i+"----"+j);
                a[i][j] = getValidU(row, column, action.values()[(i+j)%4]);
            }
        }
        printArr(a, 4, 3);
        return a;
    }

    private static float[][] multiMatrix(float[][] arr1, int row1, int column1, float[][] arr2, int row2, int column2){
        if (column1 == row2){
            float[][] arr3 = new float[row1][column2];
            for (int i = 0; i<row1; i++)
                for (int j = 0; j < column2; j++)
                    arr3[i][j] = 0;
            for (int i = 0; i<row1; i++)
                for (int j = 0; j < column2; j++)
                    for (int k = 0; k < column1; k++){
//                        System.out.println(i+"---------"+j+"----------"+k);
                        arr3[i][j] += arr1[i][k] * arr2[k][j];
                    }
            return arr3;
        }
        return null;
    }

    //TODO 从1开始
    private static float getValidU(int row, int column, action a){
        switch (a){
            case down:
                if (row == 1 && column == 4)
                    return U[0][3];
                if (row == 3)
                    return U[row-1][column-1];
                if (row == 1 && column == 2)
                    return U[row - 1][column-1];
                return U[row][column-1];
            case up:
                if (row == 1 && column == 4)
                    return U[0][3];
                if (row == 1)
                    return U[row-1][column-1];
                if (row == 3 && column == 2)
                    return U[row-1][column-1];
                return U[row-2][column-1];
            case right:
                if (row == 1 && column == 4)
                    return U[0][3];
                if (column == 1)
                    return U[row-1][column-1];
                if (row == 2 && column == 3)
                    return U[row-1][column-1];
                return U[row-1][column-2];
            case left:
                if (column == 4)
                    return U[row-1][column-1];
                if (row == 2 && column == 1)
                    return U[row-1][column-1];
        }
        return 0;
    }

}