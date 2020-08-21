package sample.pojo;

import sample.exceptions.NotFoundItemException;

public class PriorityQueue<T> {

    private final int DEFAULT_CAPACITY = 20;

    // Массив
    private QueueItem[] arr = new QueueItem[DEFAULT_CAPACITY];

    // Вместимость
    private int capacity = DEFAULT_CAPACITY;

    // Реальный размер
    private int size = 0;

    /** Конструкторы **/

    public PriorityQueue(QueueItem<T>[] arr) {
        size = arr.length;
        capacity = arr.length * 2;

        this.arr = new QueueItem[capacity];
        System.arraycopy(arr, 0, this.arr, 0, size);

        for (int i = size / 2; i >= 0; i--) {
            heapify(i);
        }
    }

    public PriorityQueue(int capacity) {
        this.capacity = capacity;
        arr = new QueueItem[capacity];
    }

    public PriorityQueue() {
    }


    /* Свойства */

    public void setCapacity(int capacity) {
        if (capacity <= size) {
            return;
        }
        this.capacity = capacity;
        QueueItem[] newArr = new QueueItem[capacity];
        if (size >= 0) {
            System.arraycopy(arr, 0, newArr, 0, size);
        }
        this.arr = newArr;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() { return size; }

    public QueueItem[] getArray() { return arr; }

    /** Методы **/

    private int getLeftIndex(int index) {
        return 2 * index + 1;
    }

    private int getRightIndex(int index) {
        return 2 * index + 2;
    }

    private int getParentIndex(int index) {
        return (index - 1) >>> 1;
    }

    // Восстановление кучи
    private void heapify(int iCur) {
        int iLeft;
        int iRight;
        int iMin;

        while (true) {
            iLeft = getLeftIndex(iCur);
            iRight = getRightIndex(iCur);
            iMin = iCur;

            if (iLeft < size && arr[iLeft].getPriority() < arr[iMin].getPriority()) {
                iMin = iLeft;
            }

            if (iRight < size && arr[iRight].getPriority() < arr[iMin].getPriority()) {
                iMin = iRight;
            }

            if (iMin == iCur) {
                break;
            }

            QueueItem temp = arr[iCur];
            arr[iCur] = arr[iMin];
            arr[iMin] = temp;
            iCur = iMin;
        }
    }

    // Добавление элемента
    public void insert(QueueItem<T> item) {
        if (capacity == size) {
            setCapacity(size * 2 + 1);
        }

        int iCur = size;
        arr[iCur] = item;
        int iParent = getParentIndex(iCur);

        while (iCur > 0 && arr[iParent].getPriority() > arr[iCur].getPriority()) {
            QueueItem temp = arr[iCur];
            arr[iCur] = arr[iParent];
            arr[iParent] = temp;

            iCur = iParent;
            iParent = getParentIndex(iCur);
        }

        size++;
    }

    // Извлечение минимального
    public QueueItem extractMin() throws NotFoundItemException {
        if (size == 0) {
            throw new NotFoundItemException();
        }
        QueueItem min = arr[0];
        arr[0] = arr[size - 1];
        size--;
        heapify(0);
        return min;
    }

    // Извлечение минимального
    public QueueItem findMin() throws NotFoundItemException {
        if (size == 0) {
            throw new NotFoundItemException();
        }
        return arr[0];
    }
}