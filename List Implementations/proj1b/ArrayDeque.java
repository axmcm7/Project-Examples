public class ArrayDeque<T> implements Deque<T> {

    private T[] array; //the ArrayDeque
    private int size; //the number of items in ArrayDeque
    private int arrayLength; //the length of ArrayDeque (number of boxes)
    private int nextFirst; //the index at the next addFirst item will be inserted exactly
    private int nextLast; //the index at the next addLast item will be inserted exactly

    /**
     * no-arg constructor that creates an empty array deque of length 8
     */
    public ArrayDeque() {
        array = (T[]) new Object[8];
        size = 0;
        arrayLength = array.length;
        nextFirst = 3;
        nextLast = 4;
    }

    /**
     * an ArrayDeque Constructor used for resizing up or down
     */
    private ArrayDeque(int newLength, ArrayDeque<T> previousArray) {
        array = (T[]) new Object[newLength];
        size = 0;
        arrayLength = newLength;

        int first = previousArray.nextFirst + 1;
        for (int i = 0; i < previousArray.size; i++) {
            if (first < previousArray.arrayLength) {
                array[i] = previousArray.array[first];
                first++;
                size++;
            } else {
                first = 0;
                array[i] = previousArray.array[first];
                first++;
                size++;
            }
        }
        nextFirst = arrayLength - 1;
        nextLast = size;
    }

    /**
     * checks whether or not an array is full, prompting a resize
     */
    private boolean isFull() {
        if (size == arrayLength) {
            return true;
        }
        return false;
    }

    /**
     * checks whether or not an array of length >= 16 is below its usage factor
     */
    private boolean needsDownsize() {
        double usageFactor = (double) size / arrayLength;
        if (usageFactor < 0.25 && arrayLength >= 32) {
            return true;
        }
        return false;
    }

    /**
     * Adds an item of type T to the front of the deque
     * MUST TAKE CONSTANT TIME, EXCEPT DURING RESIZING OPERATIONS
     */
    @Override
    public void addFirst(T item) {
        if (isFull()) {
            upsize();
            array[nextFirst] = item;
            nextFirst--;
        } else if (nextFirst > 0) {
            array[nextFirst] = item;
            nextFirst--;
        } else {
            array[nextFirst] = item;
            nextFirst = arrayLength - 1;
        }
        size++;
    }

    /**
     * Adds an item of type T to the back of the deque
     * MUST TAKE CONSTANT TIME, EXCEPT DURING RESIZING OPERATIONS
     */
    @Override
    public void addLast(T item) {
        if (isFull()) {
            upsize();
            array[nextLast] = item;
            nextLast++;
        } else if (nextLast < arrayLength) {
            array[nextLast] = item;
            nextLast++;
        } else {
            nextLast = 0;
            array[nextLast] = item;
            nextLast++;
        }
        size++;
    }

    /**
     * returns true if deque is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * returns the number of items in the deque
     * MUST TAKE CONSTANT TIME
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * prints the items in the deque from first to last,
     * separated by a space
     */
    @Override
    public void printDeque() {
        ArrayDeque<T> temp = new ArrayDeque<>(arrayLength, this);
        for (int i = 0; i < temp.size; i++) {
            System.out.print(temp.array[i] + " ");
        }
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, return null
     * MUST TAKE CONSTANT TIME, EXCEPT DURING RESIZING OPERATIONS
     */
    @Override
    public T removeFirst() {
        T returned;
        if (isEmpty()) {
            returned = null;
        } else if (needsDownsize()) {
            downsize();
            if (nextFirst == arrayLength - 1) {
                nextFirst = -1;
                returned = array[nextFirst + 1];
                array[nextFirst + 1] = null;
            } else {
                returned = array[nextFirst + 1];
                array[nextFirst + 1] = null;
            }
        } else if (nextFirst < arrayLength - 1) {
            returned = array[nextFirst + 1];
            array[nextFirst + 1] = null;
        } else {
            nextFirst = -1;
            returned = array[nextFirst + 1];
            array[nextFirst + 1] = null;
        }
        if (size > 0) {
            size--;
            nextFirst++;
        }
        if (size == 0 && !isEmpty()) {
            nextFirst = (arrayLength / 2) - 1;
            nextLast = (arrayLength / 2);
        }
        return returned;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, return null
     * MUST TAKE CONSTANT TIME, EXCEPT DURING RESIZING OPERATIONS
     */
    @Override
    public T removeLast() {
        T returned = null;
        if (isEmpty()) {
            returned = null;
        }
        if (needsDownsize()) {
            downsize();
        }
        if (nextLast == 0) {
            nextLast = arrayLength;
            returned = array[nextLast - 1];
            array[nextLast - 1] = null;
        } else {
            returned = array[nextLast - 1];
            array[nextLast - 1] = null;
        }
        if (size > 0) {
            size--;
            nextLast--;
        }
        if (size == 0) {
            nextFirst = (arrayLength / 2) - 1;
            nextLast = (arrayLength / 2);
        }
        return returned;
    }

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth.
     * If no such item exists, returns null.
     * MUST NOT ALTER THE DEQUE
     * MUST TAKE CONSTANT TIME
     */
    @Override
    public T get(int index) {
        T returned;
        if (index < 0 || index > arrayLength || isEmpty()) {
            returned = null;
        } else {
            ArrayDeque<T> temp = new ArrayDeque<>(arrayLength, this);
            returned = temp.array[index];
        }
        return returned;
    }

    /**
     * when an array becomes full, creates a new, larger
     * array and copies over the items in correct order
     */
    private void upsize() {
        int newSize = arrayLength * 2;
        ArrayDeque<T> resized = new ArrayDeque<>(newSize, this);
        this.array = resized.array;
        this.nextFirst = resized.nextFirst;
        this.nextLast = resized.nextLast;
        this.arrayLength = resized.arrayLength;
    }

    /**
     * when an array falls below its usage factor, creates a new, smaller array
     * and copies over the items in correct order
     */
    private void downsize() {
        int newLength = arrayLength / 2;
        ArrayDeque<T> resized = new ArrayDeque<>(newLength, this);
        this.array = resized.array;
        this.nextFirst = resized.nextFirst;
        this.nextLast = resized.nextLast;
        this.arrayLength = resized.arrayLength;
    }

}
