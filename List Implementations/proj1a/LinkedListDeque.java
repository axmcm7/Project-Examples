/** A LinkedListDeque abstraction
 * @author Alex McMurry
 */
public class LinkedListDeque<T> {
    /**
     * Nested Node class to create a Node in a deque
     */
    private class Node {
        private Node previous;
        private T item;
        private Node next;

        private Node(Node prev, T gen, Node nxt) {
            previous = prev;
            item = gen;
            next = nxt;
        }

        private Node() {
            previous = null;
            item = null;
            next = null;
        }
    }

    /**
     * Instance variables which each instance possesses
     */
    private Node sentinel;
    private int size;

    /**
     * creates an empty linked list deque
     */
    public LinkedListDeque() {
        sentinel = new Node();
        sentinel.next = sentinel;
        sentinel.previous = sentinel;
        size = 0;
    }

    /**
     * Adds an item of type T to the front of the deque.
     * >CAN'T USE ANY LOOPS OR RECURSION
     * >MUST TAKE CONSTANT TIME
     */
    public void addFirst(T item) {
        if (size == 0) {
            sentinel.next = new Node(sentinel, item, sentinel);
            sentinel.previous = sentinel.next;
        } else {
            sentinel.next = new Node(sentinel, item, sentinel.next);
            sentinel.next.next.previous = sentinel.next;

        }
        size++;
    }

    /**
     * Adds an item of type T to the back of the deque
     * >CAN'T USE ANY LOOPS OR RECURSION
     * >MUST TAKE CONSTANT TIME
     */
    public void addLast(T item) {
        sentinel.previous = new Node(sentinel.previous,
                item, sentinel);
        sentinel.previous.previous.next = sentinel.previous;
        size++;
    }

    /**
     * Returns true if deque is empty, false otherwise
     */
    public boolean isEmpty() {
        if (sentinel.next == sentinel || sentinel.previous == sentinel) {
            return true;
        }
        return false;
    }

    /**
     * Returns the number of items in the deque
     * MUST TAKE CONSTANT TIME --> SIZE CACHE
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     */
    public void printDeque() {
        if (isEmpty()) {
            System.out.println("_");
        }
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     * >CAN'T USE ANY LOOPS OR RECURSION
     * >MUST TAKE CONSTANT TIME
     */
    public T removeFirst() {
        T removed = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.previous = sentinel;
        if (size > 0) {
            size--;
        }
        return removed;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     * >CAN'T USE ANY LOOPS OR RECURSION
     * >MUST TAKE CONSTANT TIME
     */
    public T removeLast() {
        T removed = sentinel.previous.item;
        sentinel.previous.previous.next = sentinel;
        sentinel.previous = sentinel.previous.previous;
        if (size > 0) {
            size--;
        }
        return removed;
    }

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists, returns null.
     * Must not alter the deque!
     * MUST USE ITERATION
     */
    public T get(int index) {
        if (isEmpty()) {
            return null;
        }
        LinkedListDeque<T> temp = new LinkedListDeque<>();
        Node front = this.sentinel.next;
        for (int i = 0; i < index + 1; i++) {
            temp.addFirst(front.item);
            front = front.next;
        }
        return temp.sentinel.next.item;
    }

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists, returns null.
     * MUST USE RECURSION
     */
    public T getRecursive(int index) {
        int numRecurses = 0;
        return recursiveHelper(index, numRecurses);
    }

    private T recursiveHelper(int index, int numRecurses) {
        if (index == 0) {
            T returned = sentinel.next.item;
            for (int i = 0; i < numRecurses; i++) {
                sentinel = sentinel.previous;
            }
            return returned;
        } else if (isEmpty()) {
            return null;
        } else {
            sentinel = sentinel.next;
            return recursiveHelper(index - 1, numRecurses + 1);
        }
    }

}
