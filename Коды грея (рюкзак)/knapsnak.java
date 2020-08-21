import java.util.Scanner;
import java.util.stream.IntStream;

public class Knapsack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        final int n = scanner.nextInt(), M = scanner.nextInt();
        final int n1 = n + 1;

        double[] masses = new double[n1], costs = new double[n1];
        for (int i = 0; i < n; i++) {
            masses[i + 1] = scanner.nextDouble();
        }
        for (int i = 0; i < n; i++) {
            costs[i + 1] = scanner.nextInt();
        }

        double curM = 0, maxM = 0, curCost = 0, maxCost = 0;

        boolean[] mask = new boolean[n1], bestMask = new boolean[n1];

        int[] b = IntStream.rangeClosed(1, n1).toArray();

        int i = b[0], mult;

        while (i != n1) {
            mask[i] = !mask[i];

            mult = mask[i] ? 1 : -1;
            curM += masses[i] * mult;
            curCost += costs[i] * mult;

            if (curM <= M && curCost > maxCost) {
                maxM = curM;
                maxCost = curCost;
                bestMask = mask.clone();
            }

            b[0] = 1;
            b[i - 1] = b[i];
            b[i] = i + 1;

            i = b[0];
        }

        for (int j = 1; j < n1; j++) {
            System.out.print(bestMask[j] ? (j + " ") : "");
        }
        System.out.println(String.format("\nMax cost: %.1f\nWeight: %.1f", maxCost, maxM));
    }
}