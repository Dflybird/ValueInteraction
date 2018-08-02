package com.gq;

import java.util.Arrays;

public class Main {
    enum action{
        up, left, down, right
    }
    private static float[][] U = new float[][]{
            {0, 0, 0, 1},
            {0, 0, 0, -1},
            {0, 0, 0, 0}};

    public static void main(String[] args) {
        float theta;
        float R = -0.04f;
        float[][] pa = new float[][]{{0.1f}, {0.8f}, {0.1f}};
        int k = 0;
        float[][] temp = new float[3][4];

        do {
            for(int i = 0;i < U.length;i++){
                temp[i] = U[i].clone();
            }
            for (int i = 2; i >= 0; i--)
            {
                for (int j = 0; j <= 3; j++){
                    if (!((i ==0 && j==3)||(i ==1 && j==3)||(i==1&&j==1))){
                        float[][] actionArr = multiMatrix(actionArr(i, j), 4, 3, pa, 3, 1);
                        U[i][j] = R + Max(actionArr);
                    }
                }
            }
            theta = MaxNorm(temp, U, 3,4);
            printArr(U, 3, 4);
        }while (theta > 0.01f);

    }

    /**
     * 最大值范数
     */
    private static float MaxNorm(float[][] arr1, float[][] arr2, int row, int column){
        float theta = 0f;
        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++)
                theta = Math.max(theta, Math.abs(arr1[i][j] - arr2[i][j]));
        return theta;
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

    /**
     * 当前row行column列点进行所有动作后的状态点
     */
    private static float[][] actionArr(int row, int column){
        float[][] a = new float[4][3];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 3; j++){
//                System.out.println(i+"----"+j);
                a[i][j] = getValidU(row, column, action.values()[((i+j)+3)%4]);
            }
        }
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

    //TODO 给出row行column列经过动作a后的状态点
    private static float getValidU(int row, int column, action a){
        if (row == 0 && column == 3)
            return U[0][3];
        if (row == 1 && column == 3)
            return U[1][3];
        switch (a){
            case up:
                if (row == 0)
                    return U[0][column];
                if (row == 2 && column == 1)
                    return U[2][1];
                return U[row-1][column];
            case left:
                if (column == 0)
                    return U[row][0];
                if (row == 1 && column == 2)
                    return U[1][2];
                return U[row][column-1];
            case down:
                if (row == 2)
                    return U[2][column];
                if (row == 0 && column == 1)
                    return U[0][1];
                return U[row+1][column];
            case right:
                if (column == 3)
                    return U[row][3];
                if (row == 1 && column == 0)
                    return U[1][0];
                return U[row][column+1];
        }
        return 0;
    }

}