package edu.miracosta.cs113;

// Java program to implement Max Heap
public class MaxHeap {
    private int[] Heap;
    private int size;
    private int maxsize;
  
    // Constructor to initialize an
    // empty max heap with given maximum
    // capacity.
    public MaxHeap(int maxsize)
    {
        this.maxsize = maxsize;
        this.size = 0;
        Heap = new int[this.maxsize + 1];
        Heap[0] = Integer.MAX_VALUE;
    }
  
    // Returns position of parent
    private int parent(int pos) { return pos / 2; }
  
    // Below two functions return left and
    // right children.
    private int leftChild(int pos) { return (2 * pos); }
    private int rightChild(int pos)
    {
        return (2 * pos) + 1;
    }
  
    // Returns true of given node is leaf
    private boolean isLeaf(int pos)
    {
        if (pos > (size / 2) && pos <= size) {
            return true;
        }
        return false;
    }
  
    private void swap(int fpos, int spos)
    {
        int tmp;
        tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }
  
    // A recursive function to max heapify the given
    // subtree. This function assumes that the left and
    // right subtrees are already heapified, we only need
    // to fix the root.
    private void maxHeapify(int pos)
    {
        if (isLeaf(pos))
            return;
  
        if (Heap[pos] < Heap[leftChild(pos)]
            || Heap[pos] < Heap[rightChild(pos)]) {
  
            if (Heap[leftChild(pos)]
                > Heap[rightChild(pos)]) {
                swap(pos, leftChild(pos));
                maxHeapify(leftChild(pos));
            }
            else {
                swap(pos, rightChild(pos));
                maxHeapify(rightChild(pos));
            }
        }
    }
  
    // Inserts a new element to max heap
    public void insert(int element)
    {
        Heap[++size] = element;
  
        // Traverse up and fix violated property
        int current = size;
        while (Heap[current] > Heap[parent(current)]) {
            swap(current, parent(current));
            current = parent(current);
        }
    }
  
    public void print()
    {
        for (int i = 1; i <= size / 2; i++) {
            System.out.print(
                " PARENT : " + Heap[i]
                + " LEFT CHILD : " + Heap[2 * i]
                + " RIGHT CHILD :" + Heap[2 * i + 1]);
            System.out.println();
        }
    }
    
    public String toString()
    {
    	String mhs = "";
    	for(int i = 1; i <= size / 2; i++)
    	{
    		mhs += Heap[i];
    	}
    	return mhs;
    }
  
    // Remove an element from max heap
    public int extractMax()
    {
        int popped = Heap[1];
        Heap[1] = Heap[size--];
        maxHeapify(1);
        return popped;
    }
  
    public static void main(String[] arg)
    {
    	// 20, 50, 90, 75, 32, 23, 77, 1, 3, 6 
        System.out.println("The Max Heap is ");
        MaxHeap maxHeap = new MaxHeap(15);
        maxHeap.insert(20);
        // System.out.println("The max val is " + maxHeap.extractMax());
        maxHeap.insert(50);
        // System.out.println("The max val is " + maxHeap.extractMax());
        maxHeap.insert(90);
        // System.out.println("The max val is " + maxHeap.extractMax());
        maxHeap.insert(75);
        // System.out.println("The max val is " + maxHeap.extractMax());
        maxHeap.insert(32);
        // System.out.println("The max val is " + maxHeap.extractMax());
        maxHeap.insert(33);
        // System.out.println("The max val is " + maxHeap.extractMax());
        maxHeap.insert(77);
        // System.out.println("The max val is " + maxHeap.extractMax());
        maxHeap.insert(1);
        // System.out.println("The max val is " + maxHeap.extractMax());
        maxHeap.insert(3);
        // System.out.println("The max val is " + maxHeap.extractMax());
        maxHeap.insert(6);
        
  
        /*
        maxHeap.print();
        System.out.println("The max val is "
                           + maxHeap.extractMax());
                           */
        System.out.print(maxHeap.toString());
    }
}