import java.util.List;

public class HyperMetro<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    HyperMetro() {
        this.size = 0;
    }

    public void addFirst(T value) {
        if (this.size == 0) {
            this.head = new Node(value, null, null);
            this.tail = this.head;
        } else {
            Node<T> temp = this.head;
            this.head = new Node(value, null, temp);
            temp.setPrev(this.head);
        }
        this.size++;
    }

    public void addLast(T value) {
        if (this.size == 0) {
            this.head = new Node(value, null, null);
            this.tail = this.head;
        } else {
            Node<T> temp = this.tail;
            this.tail = new Node(value, temp, null);
            temp.setNext(this.tail);
        }
        this.size++;
    }

    public void removeLast() {
        if (this.size == 0) {
            return;
        } else if (this.size == 1) {
            this.head = null;
            this.tail = null;
        } else {
            this.tail = this.tail.getPrev();
            this.tail.setNext(null);
        }
        this.size--;
    }

    public void removeFirst() {
        if (this.size == 0) {
            return;
        } else if (this.size == 1) {
            this.head = null;
            this.tail = null;
        } else {
            this.head = this.head.getNext();
            this.head.setPrev(null);
        }
        this.size--;
    }

    public void print() {
        Node<T> pointer = this.head;
        while (pointer != null) {
            System.out.printf("%s ", pointer.getValue().toString());
            pointer = pointer.getNext();
        }
    }

    public T getHeadValue() {
        return this.head.getValue();
    }

    public T getTailValue() {
        return this.tail.getValue();
    }

    public void reverse() {
        Node<T> pointer = this.head;

        while (pointer != null) {
            Node<T> temp = pointer.getPrev();
            pointer.setPrev(pointer.getNext());
            pointer.setNext(temp);
            pointer = pointer.getPrev();
        }

        Node<T> temp = this.head;
        this.head = this.tail;
        this.tail = temp;

    }


}

class Node<T> {
    private T value;
    private List<String> transfer;
    private Node<T> prev;
    private Node<T> next;

    Node(T value, Node prev, Node next) {
        this.value = value;
        this.prev = prev;
        this.next = next;
    }

    public void setTransfer(List<String> transfer) {
        this.transfer = transfer;
    }

    public List<String> getTransfer() {
        return transfer;
    }

    public T getValue() {
        return this.value;
    }

    public Node<T> getPrev() {
        return this.prev;
    }

    public Node<T> getNext() {
        return this.next;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}