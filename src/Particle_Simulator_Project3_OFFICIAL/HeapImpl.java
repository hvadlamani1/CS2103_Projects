public class HeapImpl<T extends Comparable<? super T>> implements Heap<T> {
    private static final int INITIAL_CAPACITY = 128;
    private T[] _storage;
    private int _numElements;

    @SuppressWarnings("unchecked")
    public HeapImpl() {
        _storage = (T[]) new Comparable[INITIAL_CAPACITY];
        _numElements = 0;
    }

    /**
     * returns the parent node
     * @param pos
     * @return the parent node of the current node
     */
    private int parent(int pos) {
        return (pos - 1) / 2;
    }

    /**
     * returns the left child
     * @param pos
     * @return left child from the curent node
     */
    private int leftChild(int pos) {
        return (2 * pos) + 1;
    }

    /**
     * returns the right child
     * @param pos
     * @return right child from the current node
     */
    private int rightChild(int pos) {
        return (2 * pos) + 2;
    }

    /**
     * swaps the values of the two specified positions
     * @param fpos
     * @param spos
     */
    private void swap(int fpos, int spos) {
        T temp;
        temp = _storage[fpos];
        _storage[fpos] = _storage[spos];
        _storage[spos] = temp;
    }

    /**
     * restores the array into a heap array
     * @param pos
     */
    private void convertToHeapUp(int pos) {
        while (pos > 0 && _storage[pos].compareTo(_storage[parent(pos)]) > 0) {
            swap(pos, parent(pos));
            pos = parent(pos);
        }
    }

    /**
     * restores the array into a heap array
     * @param pos
     */
    private void convertToHeapDown(int pos) {
        int maxChildIndex;
        while (leftChild(pos) < _numElements) {
            maxChildIndex = maxChild(pos);

            if (_storage[pos].compareTo(_storage[maxChildIndex]) < 0) {
                swap(pos, maxChildIndex);
                pos = maxChildIndex;
            } else {
                break;
            }
        }
    }

    /**
     * 
     * @param pos
     * @return the right or left child depending on which one is the highest
     */
    private int maxChild(int pos) {
        int left = leftChild(pos);
        int right = rightChild(pos);

        if (right >= _numElements || _storage[left].compareTo(_storage[right]) > 0) {
            return left;
        } else {
            return right;
        }
    }

    /**
     * adds the current value to the heap array
     * @param data
     */
    public void add(T data) {
        _numElements++;
        if (_numElements >= _storage.length) {
            resize(2 * _storage.length);
        }
        int currentSize = _numElements - 1;
        _storage[currentSize] = data;
        convertToHeapUp(currentSize);
    }

    /**
     * removes the first node from the heap array
     */
    public T removeFirst() {
        if (_numElements == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        T root = _storage[0];
        _storage[0] = _storage[_numElements - 1];
        decreaseSize();
        convertToHeapDown(0);

        return root;
    }

    /**
     * resizes the heap array to account for more space
     * @param capacity
     */
    private void resize(int capacity) {
        T[] temp = (T[]) new Comparable[capacity];
        for (int i = 0; i < _numElements; i++) {
            temp[i] = _storage[i];
        }
        _storage = temp;
    }

    /**
     * decreases the size of the heap array by one
     */
    private void decreaseSize() {
        _numElements--;
    }

    /**
     * gives the size of the heap
     @return integer size of the heap
     */
    public int size() {
        return _numElements;
    }

    /**
     * finds and returns the object at the specefied index
     * @param i: the index of the node that you want to access
     * @return the object at the specefied index
     */
    public T get(int i) {
        if (i < 0 || i >= _numElements) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return _storage[i];
    }
}
