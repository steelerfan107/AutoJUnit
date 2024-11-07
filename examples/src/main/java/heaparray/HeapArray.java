package heaparray;

public class HeapArray {

    private static final int MAX_SIZE = 20;

    /*
     * Builders ---------------------------
     */
    public HeapArray() {
        array = new int[MAX_SIZE];
        for (int i=0;i<MAX_SIZE;i++) {
            array[i] = -1;
        }
        size = 0;
    }

    public void add(int elem) {
        if (elem < 0)
            throw new IllegalArgumentException("The element must be a positive number");

        if (size<MAX_SIZE) {
            array[size] = elem;
            size++;

            bubbleUp();
        }

    }

    /**
     * Performs the "bubble up" operation to place a newly inserted element
     * (i.e. the element that is at the size index) in its correct place so
     * that the heap maintains the max-heap order property.
     */
    private void bubbleUp() {
        int index = this.size-1;

        while (hasParent(index)
            && (parent(index) < array[index])) {
            // parent/child are out of order; swap them
            swap(index, parentIndex(index));
            index = parentIndex(index);
        }
    }

    private boolean hasParent(int i) {
        return i >= 1;
    }

    private int parent(int i) {
        return array[parentIndex(i)];
    }

    private int parentIndex(int i) {
        return (i - 1) / 2;
    }

    private void swap(int index1, int index2) {
        int tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
    }
    /*
     * -----------------------------------------
     */

    private int size;

    private int array[];

    public String toString() {
        String s = "";
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                s = s + array[i] + " ";
            }
            s += ", size = " + size + ", array.length = " + array.length;
        }
        return s;
    }


    public boolean repOK_Complete() {
        if (!repOK_arraynull())
            return false;

        if (!repOK_size())
            return false;

        if (!repOK_allelems())
            return false;

        if (!repOK_remaining())
            return false;
        return true;

    }

    public boolean repOK_arraynull() {
        if (array == null)
            return false;
        return true;
    }

    public boolean repOK_size() {
        if (array != null) {
            if (size < 0 || size > array.length)
                return false;
        }
        return true;
    }

    public boolean repOK_allelems() {
        if (array != null) {
            for (int i = 0; i < size; i++) {
                int elem_i = array[i];
                if (elem_i == -1)
                    return false;

                if (i > 0) {
                    int elem_parent = array[(i - 1) / 2];
                    if (elem_i > elem_parent)
                        return false;
                }
            }
        }
        return true;
    }

    public boolean repOK_remaining() {
        if (array != null) {
            for (int i = size; i < array.length; i++)
                if (array[i] != -1)
                    return false;
        }
        return true;
    }

    public boolean repOK_arraynull_size() {
        if (!repOK_arraynull())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_arraynull_allelems() {
        if (!repOK_arraynull())
            return false;
        if (!repOK_allelems())
            return false;
        return true;
    }

    public boolean repOK_arraynull_remaining() {
        if (!repOK_arraynull())
            return false;
        if (!repOK_remaining())
            return false;
        return true;
    }

    public boolean repOK_size_allelems() {
        if (!repOK_size())
            return false;
        if (!repOK_allelems())
            return false;
        return true;
    }

    public boolean repOK_size_remaining() {
        if (!repOK_size())
            return false;
        if (!repOK_remaining())
            return false;
        return true;
    }

    public boolean repOK_allelems_remaining() {
        if (!repOK_allelems())
            return false;
        if (!repOK_remaining())
            return false;
        return true;
    }

    public boolean repOK_arraynull_size_allelems() {
        if (!repOK_arraynull())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_allelems())
            return false;
        return true;
    }

    public boolean repOK_arraynull_size_remaining() {
        if (!repOK_arraynull())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_remaining())
            return false;
        return true;
    }

    public boolean repOK_arraynull_allelems_remaining() {
        if (!repOK_arraynull())
            return false;
        if (!repOK_allelems())
            return false;
        if (!repOK_remaining())
            return false;
        return true;
    }

    public boolean repOK_size_allelems_remaining() {
        if (!repOK_size())
            return false;
        if (!repOK_allelems())
            return false;
        if (!repOK_remaining())
            return false;
        return true;
    }

}
