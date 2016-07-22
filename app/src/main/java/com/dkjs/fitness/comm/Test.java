package com.dkjs.fitness.comm;

/**
 * Created by administrator on 16/7/20.
 */
public class Test {

    public static void main(String[] args){

    }

    public void printTwoDem(int num, int layerCount, int baseNum, int[][] arr){
        if(layerCount == 0){
            arr = new int[num][num];
        }
        if(num == 1){
            arr[layerCount][layerCount] = baseNum + 1;
            for(int i=0; i<num; i++){
                for(int j=0; j<num; j++){
                    System.out.print(arr[i][j] + " ");
                }
                System.out.println();
            }
            return;
        }
        if(num == 2){
            arr[layerCount][layerCount] = baseNum + 1;
            arr[layerCount][layerCount + 1] = baseNum + 2;
            arr[layerCount+1][layerCount+1] = baseNum + 3;
            arr[layerCount+1][layerCount] = baseNum + 4;
            for(int i=0; i<num; i++){
                for(int j=0; j<num; j++){
                    System.out.print(arr[i][j] + " ");
                }
                System.out.println();
            }
            return;
        }

        


    }
}
