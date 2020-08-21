import java.io.BufferedReader;
import java.io.FileReader;

public class Maze {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        String[] nk = reader.readLine().split(" ");

        int N = Integer.parseInt(nk[0]);
        int K = Integer.parseInt(nk[1]);

        char[][] m = new char[N][N];

        for (int i = 0; i < N; i++) {
            m[i] = reader.readLine().toCharArray();
        }

        int[][] counts = new int[N+2][N+2];
        counts[1][1] = 1;

        for (int k = 1; k <= K; k++) {
            int[][] tempCounts = clone(counts);
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (m[i - 1][j - 1] == '0') {
                       counts[i][j] = tempCounts[i][j - 1]
                                    + tempCounts[i][j + 1]
                                    + tempCounts[i - 1][j]
                                    + tempCounts[i + 1][j];
                    }
                }
            }
        }

        System.out.println(counts[N][N]);
    }

    private static int[][] clone(int[][] arr) {
        int[][] newArr = arr.clone();
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i].clone();
        }
        return newArr;
    }
}