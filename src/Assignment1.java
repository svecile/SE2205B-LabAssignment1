import java.io.*;
import java.util.Scanner;

public class Assignment1 {

    private int matrix[][];

//    // Private strassen algorithm to return multiplied matrix
//    // @params are 2x2 matrices
//    // @return 2x2 matrix of strassen-computed matrices
//    private int[][] strassenCompute(int[][] a, int[][] b) {
//
//        return new int[5][6];
//    }
    private int nextPowerOfTwo(int n) {
        int log2 = (int) Math.ceil(Math.log(n) / Math.log(2));
        return (int) Math.pow(2, log2);
    }

    /** Function to multiply matrices **/
    public int[][] denseMatrixMult(int[][] A, int[][] B, int size)
    {
        int n = size;
        int[][] R = new int[n][n];
        // initialization
        if (n == 1)
            R[0][0] = A[0][0] * B[0][0];
        else
        {
            int[][] A11 = new int[n/2][n/2];
            int[][] A12 = new int[n/2][n/2];
            int[][] A21 = new int[n/2][n/2];
            int[][] A22 = new int[n/2][n/2];
            int[][] B11 = new int[n/2][n/2];
            int[][] B12 = new int[n/2][n/2];
            int[][] B21 = new int[n/2][n/2];
            int[][] B22 = new int[n/2][n/2];

            // Split matrix A into 4
            split(A, A11, 0 , 0, n/2);
            split(A, A12, 0 , n/2, n/2);
            split(A, A21, n/2, 0, n/2);
            split(A, A22, n/2, n/2, n/2);
            // Split matrix B into 4
            split(B, B11, 0 , 0, n/2);
            split(B, B12, 0 , n/2, n/2);
            split(B, B21, n/2, 0, n/2);
            split(B, B22, n/2, n/2, n/2);

            /**
             M1 = (A11 + A22)(B11 + B22)
             M2 = (A21 + A22) B11
             M3 = A11 (B12 - B22)
             M4 = A22 (B21 - B11)
             M5 = (A11 + A12) B22
             M6 = (A21 - A11) (B11 + B12)
             M7 = (A12 - A22) (B21 + B22)
             **/
            int[][] m1_0 = sum(A11, A22,0,0,0,0,n/2);
            int[][] m1_1 = sum(B11, B22,0,0,0,0,n/2);
            int [][] M1 = denseMatrixMult(m1_0, m1_1, n/2);

            int[][] m2_0 = sum(A21, A22,0,0,0,0,n/2);
            int [][] M2 = denseMatrixMult(m2_0, B11, n/2);

            int[][] m3_0 = sub(B12, B22,0,0,0,0,n/2);
            int [][] M3 = denseMatrixMult(A11, m3_0, n/2);

            int[][] m4_0 = sub(B21, B11,0,0,0,0,n/2);
            int [][] M4 = denseMatrixMult(A22, m4_0, n/2);

            int[][] m5_0 = sum(A11, A12,0,0,0,0,n/2);
            int [][] M5 = denseMatrixMult(m5_0, B22, n/2);

            int[][] m6_0 = sub(A21, A11,0,0,0,0,n/2);
            int[][] m6_1 = sum(B11, B12,0,0,0,0,n/2);
            int [][] M6 = denseMatrixMult(m6_0, m6_1, n/2);

            int[][] m7_0 = sub(A12, A22,0,0,0,0,n/2);
            int[][] m7_1 = sum(B21, B22,0,0,0,0,n/2);
            int [][] M7 = denseMatrixMult(m7_0, m7_1, n/2);

            //C11 = M1 + M4 - M5 + M7
            int [][] C11 = sum(sub(sum(M1, M4,0,0,0,0,n/2), M5,0,0,0,0,n/2), M7,0,0,0,0,n/2);
            //C12 = M3 + M5
            int [][] C12 = sum(M3, M5,0,0,0,0,n/2);
            //C21 = M2 + M4
            int [][] C21 = sum(M2, M4,0,0,0,0,n/2);
            //C22 = M1 - M2 + M3 + M6
            int [][] C22 = sum(sub(sum(M1, M3,0,0,0,0,n/2), M2,0,0,0,0,n/2), M6,0,0,0,0,n/2);

            // Join matrices
            join(C11, R, 0 , 0, n/2);
            join(C12, R, 0 , n/2, n/2);
            join(C21, R, n/2, 0, n/2);
            join(C22, R, n/2, n/2, n/2);
        }
        return R;
    }
    // Split parent matrix into child matrices
    public void split(int[][] P, int[][] C, int childBoundX, int childBoundY, int size)
    {
        for(int i = 0, j = childBoundX; i < size; i++, j++)
            for(int k = 0, l = childBoundY; k < size; k++, l++)
                C[i][k] = P[j][l];
    }
    // Join child matrices into one big parent matrix
    public void join(int[][] C, int[][] P, int childBoundX, int childBoundY, int size)
    {
        for(int i = 0, j = childBoundX; i < size; i++, j++)
            for(int k = 0, l = childBoundY; k < size; k++, l++)
                P[j][l] = C[i][k];
    }

    public int[][] sum(int[][] A, int[][] B, int x1, int y1, int x2, int y2, int n) {

        int[][] sum = new int[n][n];
        // sums columns together then rows
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