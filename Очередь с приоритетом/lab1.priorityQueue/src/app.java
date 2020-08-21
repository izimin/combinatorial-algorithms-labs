import sample.exceptions.NotFoundItemException;
import sample.pojo.QueueItem;

import sample.pojo.PriorityQueue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class app {
    public static void main(String[] args) throws NotFoundItemException {
        Random random = new Random();

        final int n = 1000000;
        PriorityQueue<Integer> priorityQueueInteger = new PriorityQueue<>(n+1);

        // Вставка
        long totalTime = 0;
        for (int i = 0; i < n; i++) {
            int priority = 1 + random.nextInt(n - 1);
            QueueItem<Integer> structItem = new QueueItem<>(priority, 10);
            long start = System.nanoTime();
            priorityQueueInteger.insert(structItem);
            totalTime += (System.nanoTime() - start);
        }
        System.out.println(String.format("Среднее время одной операции вставки за %d вставок: %g сек.", n, (totalTime / 1e+9 / n)));

        // Извлечение
        totalTime = 0;
        for (int i = 0; i < n; i++) {
            long start = System.nanoTime();
            priorityQueueInteger.extractMin();
            totalTime += (System.nanoTime() - start);
        }
        System.out.println(String.format("Среднее время одной операции извлечения за %d извлечений: %f сек.", n, (totalTime / 1e+9 / n)));

        aLotOfData();
    }

    public static void aLotOfData() throws NotFoundItemException {
        final int n = 10000000;
        Random random = new Random();

        QueueItem[] arr = new QueueItem[n];
        for (int i = 0; i < n; i++) {
            arr[i] = new QueueItem<>(1 + random.nextInt(n - 1), '1');
        }

        long start = System.nanoTime();
        PriorityQueue characterPriorityQueue = new PriorityQueue<>(arr);
        System.out.println(String.format("Создание очереди из массива %d элементов: %f сек.", n, (System.nanoTime()-start)/1e+9));


        QueueItem<Character> structItemMin = new QueueItem<>(0, '0');
        QueueItem<Character> structItemMax = new QueueItem<>(n+1, '0');
        start = System.nanoTime();
        characterPriorityQueue.insert(structItemMin);
        System.out.println(String.format("Худишй случай вставки в очередь в %d элементами: %f сек.", n, (System.nanoTime()-start)/1e+9));
        start = System.nanoTime();
        characterPriorityQueue.insert(structItemMax);
        System.out.println(String.format("Лучший случай вставки в очередь в %d элементами: %f сек.", n, (System.nanoTime()-start)/1e+9));

        start = System.nanoTime();
        characterPriorityQueue.extractMin();
        System.out.println(String.format("Извлечение из очереди с %d элементами: %f сек.", n, (System.nanoTime()-start)/1e+9));
    }
}
