import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node first = null;
    private Node last = null;

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkIfItemIsNull(item);
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = null;
        first.previous = oldFirst;
        if (isEmpty()) {
            last = first;
        }
        else {
            oldFirst.next = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        checkIfItemIsNull(item);
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.previous = null;
        last.next = oldLast;
        if (isEmpty()) {
            first = last;
        }
        else {
            oldLast.previous = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        isEmptyEx();
        Item item = first.item;
        size--;
        if (isEmpty()) {
            last = null;
            first = null;
        }
        else {
            first = first.previous;
            first.next = null;
        }

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        isEmptyEx();
        Item item = last.item;
        size--;

        if (isEmpty()) {
            last = null;
            first = null;
        }
        else {
            last = last.next;
            last.previous = null;
        }

        return item;
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {

        return new DequeIterator();
    }

    private void checkIfItemIsNull(Item item) {
        if (item == null) throw new IllegalArgumentException("item = null");
    }

    private void isEmptyEx() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("no more items to return");
            Item item = current.item;
            current = current.previous;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("This Deque does not support remove");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<>();
        System.out.println(dq.isEmpty());
        dq.addFirst(1);
        dq.removeFirst();


        dq.addFirst(1);
        dq.addFirst(1);
        dq.addLast(335);
        System.out.println(dq.removeFirst());
        System.out.println(dq.removeLast());

        System.out.println("size = " + dq.size());
        System.out.println(dq.isEmpty());
        System.out.println("------------------------------------");
        for (int i : dq) {
            System.out.println(i);
        }
        System.out.println("------------------------------------");

        Iterator<Integer> i = dq.iterator();
        System.out.println(i.hasNext());

        //i.remove();
    }


}