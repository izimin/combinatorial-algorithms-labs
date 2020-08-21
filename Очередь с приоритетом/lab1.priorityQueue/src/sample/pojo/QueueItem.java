package sample.pojo;

public class QueueItem<T> {
    int priority;
    T value;

    public QueueItem(int priority, T value) {
        this.priority = priority;
        this.value = value;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}