import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Schedule {

    // Число задач
    private static int N;

    // Число исполнителей
    private static int M;

    @Getter
    @Setter
    // Класс задачи
    static class Task {
        // Наименование
        private String name;

        // Приоритет
        private Integer priority;

        // Список и кол-во предшествующих задач
        private List<Task> prev = new ArrayList<>();
        private Integer prevCnt = 0;

        // Список и количество последующих задач
        private List<Task> next = new ArrayList<>();
        private Integer nextCnt = 0;

        // Метка
        private List<Integer> mark = new ArrayList<>();

        // Конструктор
        Task(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return getName();
        }
    }

    public static void main(String[] args) throws IOException {

        // Список задач
        List<Task> tasks = new ArrayList<>();

        // Считываение
        inputData(tasks);

        /* ПЕРВЫЙ ЭТАП */

        // Берем только те задачи, у которых нет последующих
        List<Task> L = tasks.stream().filter(x -> x.getNextCnt() == 0).collect(Collectors.toList());
        tasks.clear();

        int p = 1;

        while (!L.isEmpty()) {

            Task t = L.get(0);
            L.remove(0);
            t.setPriority(p);
            tasks.add(0, t);

            for (Task tp : t.getPrev()){
                tp.getMark().add(0, p);
                tp.setNextCnt(tp.getNextCnt() - 1);

                if (tp.getNextCnt() == 0) {
                    L.add(getIndexForInsertByMark(L, tp.getMark()), tp);
                }
            }
            p++;
        }

        // Проверка, вем ли задачам назначены приоритеты (т.к формируется список заново, задач без приоритета в нем не будет)
        if (tasks.size() != N) {
            System.out.println("Указанный набор заданий не может быть выполнен!\n");
            return;
        }

        /* ВТОРОЙ ЭТАП */

        // Список задач для каждого исполнителя

        List<List<String>> tasksPerformers = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            ArrayList<String> list = new ArrayList<>();
            for (int j = 0; j < N; j++) {
                list.add(" ");
            }
            tasksPerformers.add(list);
        }

        // Берем только те задачи, у которых нет предыдущих
        L = tasks.stream().filter(x -> x.getPrevCnt() == 0).collect(Collectors.toList());

        int step = 0;
        while (!L.isEmpty()) {
            int i = 0;
            for (Task t : new ArrayList<>(L.subList(0, Math.min(M, L.size())))) {
                tasksPerformers.get(i).set(step, t.getName());
                L.remove(t);

                for (Task tn : t.getNext()) {
                    tn.setPrevCnt(tn.getPrevCnt() - 1);
                    if (tn.getPrevCnt() == 0) {
                        L.add(getIndexForInsertByPriority(L, tn.getPriority()), tn);
                    }
                }
                i++;
            }
            step++;
        }

        /* ОТВЕТ */
        outputData(tasks, tasksPerformers);
    }

    // Считывание
    private static void inputData(List<Task> tasks) throws IOException {
        // Массив индексов в списке задач по наименованию задачи
        Map<String, Integer> indices = new HashMap<>();

        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Ilya\\IdeaProjects\\work\\knapsack\\src\\main\\resources\\inp.txt"));
        String[] nm = reader.readLine().split(" ");

        N = Integer.parseInt(nm[0]);
        M = Integer.parseInt(nm[1]);
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tasksPair = line.split("-");
            String t1 = tasksPair[0], t2 = tasksPair[1];
            boolean isT1 = indices.containsKey(t1), isT2 = indices.containsKey(t2);

            Task task1 = isT1 ? tasks.get(indices.get(t1)) : new Task(t1);
            Task task2 = isT2 ? tasks.get(indices.get(t2)) : new Task(t2);

            task1.getNext().add(task2);
            task1.setNextCnt(task1.getNextCnt() + 1);

            task2.getPrev().add(task1);
            task2.setPrevCnt(task2.getPrevCnt() + 1);

            if (!isT1) {
                indices.put(t1, tasks.size());
                tasks.add(task1);
            }
            if (!isT2) {
                indices.put(t2, tasks.size());
                tasks.add(task2);
            }
        }
    }

    // Вывод ответа
    private static void outputData(List<Task> tasks, List<List<String>> tasksPerformers) {
        System.out.println("Список задач с приоритетами:\n");

        for (int i = 0; i < N; i++) {
             System.out.println(tasks.get(i).getName() + " " + tasks.get(i).getPriority());
        }

        System.out.println();

        System.out.println("Итоговое расписание:\n");

        int per = 1;
        for (List<String> str : tasksPerformers) {
            System.out.print("Исп. " + per + ": " + String.join("   ", str) + "\n");
            per++;
        }
    }

    // Поиск индекса для вставки с учетом приоритета
    private static int getIndexForInsertByPriority(List<Task> L, Integer p) {
        int index = L.size();

        while (index != 0 && L.get(index - 1).getPriority() < p) {
            index--;
        }

        return index;
    }

    // Сравнение двух марок
    private static int compareMark(List<Integer> mark1, List<Integer> mark2) {
        for (int i = 0; i < Math.min(mark1.size(), mark2.size()); i++) {
            if (mark1.get(i) > mark2.get(i)) {
                return 1;
            } else if (mark1.get(i) < mark2.get(i)) {
                return -1;
            }
        }
        return (Integer.compare(mark1.size(), mark2.size()));
    }

    // Поиск индекса для вставки с учетом метки
    private static int getIndexForInsertByMark(List<Task> L, List<Integer> mark) {
        int index = L.size();

        while (index != 0 && compareMark(L.get(index-1).getMark(), mark) > 0) {
                index--;
        }
        return index;
    }
}