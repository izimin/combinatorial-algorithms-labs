import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

public class Knapsack2 {

    // Класс листа
    @Getter
    @AllArgsConstructor
    static class Leaf {
        double accW;
        double accC;
        int lvl;
        boolean[] mask;
        Double f;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        final int n = scanner.nextInt(), W = scanner.nextInt();

        List<Double> arrW = new ArrayList<>(),
                     arrC = new ArrayList<>(),
                     arrE = new ArrayList<>();

        List<Integer> indices = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            arrW.add(scanner.nextDouble());
            indices.add(i);
        }

        for (int i = 0; i < n; i++) {
            arrC.add(scanner.nextDouble());
            arrE.add(arrC.get(i) / arrW.get(i));
        }

        // Сортирует все массивы по первому (по индексам), нашел библиотечку в интернете
        Sorter.concurrentSort(arrE, arrW, arrC, arrE, indices);

        arrE.add(0.0);

        double accW, accC;

        boolean[] mask = new boolean[n];

        // Очередь с приоритетом, pool - извлекается максимальный
        PriorityQueue<Leaf> leafs = new PriorityQueue<>((leaf1, leaf2) ->
                leaf2.getF().compareTo(leaf1.getF()));

        Leaf maxL = new Leaf(0.0, 0.0, 0, mask, 0.0);

        while (maxL.getLvl() < n) {

            int lvl = maxL.getLvl() + 1;
            int index = maxL.getLvl();

            accW = maxL.getAccW();
            accC = maxL.getAccC();
            mask = maxL.getMask();

            // Не берем
            leafs.add(new Leaf(accW, accC, lvl, mask, calcF(accW, accC, W, arrE.get(index+1))));

            boolean[] maskTmp = mask.clone();

            maskTmp[index] = true;

            accW += arrW.get(index);
            accC += arrC.get(index);

            // Берем
            leafs.add(new Leaf(accW, accC, lvl, maskTmp, calcF(accW, accC, W, arrE.get(index+1))));

            maxL = leafs.poll();
        }

        mask = maxL.getMask();

        // Выводим результат
        for (int j = 0; j < n; j++) {
            System.out.print(mask[j] ? (indices.get(j) + 1 + " ") : "");
        }
        System.out.println(String.format("\nMax cost: %.1f\nWeight: %.1f", maxL.getAccC(), maxL.getAccW()));
    }

    private static double calcF(double accW, double accC, int W, double e) {
        return accW <= W ? accC + (W - accW) * e : -1;
    }
}