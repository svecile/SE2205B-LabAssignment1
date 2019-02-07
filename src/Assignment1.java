import java.io.*;
import java.util.Scanner;

public class Assignment1 {

    private int matrix[][];

    public int[][] denseMatrixMult(int[][] A, int[][] B, int size) {
        return A;
    }

    public int[][] sum(int[][] A, int[][] B, int x1, int y1, int x2, int y2, int n) {

        int[][] sum = new int[n][n];

        for (int i = 0; i < n; i++) {

            int yA = y1, yB = y2;

            for (int j = 0; j < n; j++) {
                sum[i][j] = A[x1][yA] + B[x2][yB];
                yA++;
                yB++;
            }
            x1++;
            x2++;
        }
        return sum;
    }

    public int[][] sub(int[][] A, int[][] B, int x1, int y1, int x2, int y2, int n) {

        int[][] sub = new int[n][n];

        for (int i = 0; i < n; i++) {

            int yA = y1, yB = y2;

            for (int j = 0; j < n; j++) {
                sub[i][j] = A[x1][yA] - B[x2][yB];
                yA++;
                yB++;
            }
            x1++;
            x2++;
        }
        return sub;
    }


    public int[][] initMatrix(int n) {
        return matrix = new int[n][n];
    }

    public void printMatrix(int n, int[][] A) {

        String s = "";

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {
                s += A[i][j] + " ";
            }

            System.out.println(s);
            s = "";
        }
    }

    public int[][] readMatrix(String filename, int n) throws Exception {

        int temp[][] = new int[n][n];
        int i = 0, j = 0;
        Scanner reader = new Scanner(new File(filename));

        while (reader.hasNext()) {
            //j counts columns and when it reaches the last column it resets and i increments to a new row
            if (j == n) {
                j = 0;
                i++;
            }
            temp[i][j] = reader.nextInt();
            j++;
        }
        return temp;
    }
}