import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int n = 0;


    // construct an empty randomized queue
    /* public RandomizedQueue(int capacity) {
        items = (Item[]) new Object[capacity];
    } */

    public RandomizedQueue() {
        int INITIAL_CAPACITY = 16;
        items = (Item[]) new Object[INITIAL_CAPACITY];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        checkIfItemIsNull(item);
        if (n == items.length) resize(2 * items.length);
        items[n++] = item;

    }

    // remove and return a random item
    public Item dequeue() {
        isEmptyEx();
        int randomIndex = StdRandom.uniform(0, n);
        Item item = items[randomIndex];
        items[randomIndex] = items[--n];
        items[n] = null;
        if (n > 0 && n == items.length / 4) resize(items.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        isEmptyEx();
        int randomIndex = StdRandom.uniform(0, n);
        return items[randomIndex];
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RQIterator();
    }


    private class RQIterator implements Iterator<Item> {
        private final int[] randomArray = StdRandom.permutation(n);
        private int i = n;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("no more items to return");
            return items[randomArray[--i]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("This Deque does not support remove");
        }
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
            copy[i] = items[i];
        items = copy;
    }

    private void checkIfItemIsNull(Item item) {
        if (item == null) throw new IllegalArgumentException("item = null");
    }

    private void isEmptyEx() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        System.out.println(rq.isEmpty());
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);
        rq.enqueue(7);
        System.out.println("size = " + rq.size());
        System.out.println("1 sample " + rq.sample());
        System.out.println("2 sample " + rq.sample());
        System.out.println("3 sample " + rq.sample());
        System.out.println("4 sample " + rq.sample());

        System.out.println("1 deq " + rq.dequeue());
        System.out.println("2 deq " + rq.dequeue());

        for (int i = 0; i < 3; i++) {
            System.out.println("________________________");
            for (int w : rq) {
                System.out.println(w);
            }
            System.out.println("________________________");
        }
        System.out.println(rq.isEmpty());

    }

}
