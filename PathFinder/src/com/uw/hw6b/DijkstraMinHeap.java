package com.uw.hw6b;

import com.uw.hw6b.DijkstraHeapNode.DistanceUpdateListener;

//Modified by Group 6 
/**
 * A binary minheap of DijkstraHeapNode objects.
 * 
 * @author Donald Chinn
 * @version September 19, 2003
 */
public class DijkstraMinHeap implements DistanceUpdateListener{
    
    /* the heap is organized using the implicit array implementation.
     * Array index 0 is not used
     */
    private DijkstraHeapNode[] elements;
    private int size;       // index of last element in the heap
    
    // Constructor
    public DijkstraMinHeap() {
        int initialCapacity = 10;
        
        this.elements = new DijkstraHeapNode[initialCapacity + 1];
        this.elements[0] = null;
        this.size = 0;
    }
    
    
    /**
     * Constructor
     * @param capacity  number of active elements the heap can contain
     */    
    public DijkstraMinHeap(int capacity) {
        this.elements = new DijkstraHeapNode[capacity + 1];
        this.elements[0] = null;
        this.size = 0;
    }
    
    
    /**
     * Given an array of Comparables, return a binary heap of those
     * elements.
     * @param data  an array of data (no particular order)
     * @return  a binary heap of the given data
     */
    public static DijkstraMinHeap buildHeap(DijkstraHeapNode[] data) {
        DijkstraMinHeap newHeap = new DijkstraMinHeap(data.length);
        for (int i = 0; i < data.length; i++) {
            newHeap.elements[i+1] = data[i];
            newHeap.elements[i+1].setHeapIndex(i + 1);
            newHeap.elements[i+1].registerDistanceUpdateListener(newHeap);
        }
        newHeap.size = data.length;
        for (int i = newHeap.size / 2; i > 0; i--) {
            newHeap.percolateDown(i);
        }
        return newHeap;
    }

    /**
     * Determine whether the heap is empty.
     * @return  true if the heap is empty; false otherwise
     */
    public boolean isEmpty() {
        return (size < 1);
    }
    
    /**
     * Insert an object into the heap.
     * @param key   a key
     */
    public void insert(DijkstraHeapNode key) {

        if (size >= elements.length - 1) {
            // not enough room -- create a new array and copy
            // the elements of the old array to the new
        	DijkstraHeapNode[] newElements = new DijkstraHeapNode[2*size];
            for (int i = 0; i < elements.length; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
        
        size++;
        elements[size] = key;
        elements[size].setHeapIndex(size);
        elements[size].registerDistanceUpdateListener(this);
        percolateUp(size);
    }
    
    
    /**
     * Remove the object with minimum key from the heap.
     * @return  the object with minimum key of the heap
     */
    public DijkstraHeapNode deleteMin() throws EmptyHeapException {
        if (!isEmpty()) {
        	DijkstraHeapNode returnValue = elements[1];
            elements[1] = elements[size];
            elements[1].setHeapIndex(1);
            size--;
            percolateDown(1);
            returnValue.setHeapIndex(0);
            returnValue.unregisterDistanceUpdateListener();
            return returnValue;
            
        } else {
            throw new EmptyHeapException();
        }
    }
    
    
    /**
     * Given an index in the heap array, percolate that key up the heap.
     * @param index     an index into the heap array
     */
    private void percolateUp(int index) {
    	DijkstraHeapNode temp = elements[index];  // keep track of the item to be moved
        while (index > 1) {
            if (temp.compareTo(elements[index/2]) < 0) {
                elements[index] = elements[index/2];
                elements[index].setHeapIndex(index);
                index = index / 2;
            } else {
                break;
            }
        }
        elements[index] = temp;
        elements[index].setHeapIndex(index);
    }
    
    
    /**
     * Given an index in the heap array, percolate that key down the heap.
     * @param index     an index into the heap array
     */
    private void percolateDown(int index) {
        int child;
        DijkstraHeapNode temp = elements[index];
        
        while (2*index <= size) {
            child = 2 * index;
            if ((child != size) &&
                (elements[child + 1].compareTo(elements[child]) < 0)) {
                child++;
            }
            // ASSERT: at this point, elements[child] is the smaller of
            // the two children
            if (elements[child].compareTo(temp) < 0) {
                elements[index] = elements[child];
                elements[index].setHeapIndex(index);
                index = child;
            } else {
                break;
            }
        }
        elements[index] = temp;
        elements[index].setHeapIndex(index);    
    }


	@Override
	public void onDistanceUpdate(int index) {
		this.percolateUp(index);
	}
}
