package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author anoopboyal
 */
/**
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E>
{
  /**
   * Default number of elements that may be stored in each node.
   */
  private static final int DEFAULT_NODESIZE = 4;
  
  /**
   * Number of elements that can be stored in each node.
   */
  private final int nodeSize;
  
  /**
   * Dummy node for head.  It should be private but set to public here only  
   * for grading purpose.  In practice, you should always make the head of a 
   * linked list a private instance variable.  
   */
  public Node head;
  
  /**
   * Dummy node for tail.
   */
  private Node tail;
  
  /**
   * Number of elements in the list.
   */
  private int size;


  /**
   * position of item from parameter
   */
  private int pos;
  /**
   * array of data of items
   */
  private E[] data;

  /**
   * Constructs an empty list with the default node size.
   */
  public StoutList()
  {
    this(DEFAULT_NODESIZE);
  }

  /**
   * Constructs an empty list with the given node size.
   * @param nodeSize number of elements that may be stored in each node, must be 
   *   an even number
   */
  public StoutList(int nodeSize)
  {
    if (nodeSize <= 0 || nodeSize % 2 != 0) throw new IllegalArgumentException();
    
    // dummy nodes
    head = new Node();
    tail = new Node();
    head.next = tail;
    tail.previous = head;
    this.nodeSize = nodeSize;
  }
  
  /**
   * Constructor for grading only.  Fully implemented. 
   * @param head
   * @param tail
   * @param nodeSize
   * @param size
   */
  public StoutList(Node head, Node tail, int nodeSize, int size)
  {
	  this.head = head; 
	  this.tail = tail; 
	  this.nodeSize = nodeSize; 
	  this.size = size; 
  }

  @Override
  public int size()
  {
    return size; 
  }
  
  @Override
  public boolean add(E item)
  {
	  // Check if the 'item' is null, and if so, throw a NullPointerException.
	  if (item == null) throw new NullPointerException("Item cannot be null.");
		

		Node newNode = new Node();
		newNode.addItem(item);

		if (size == 0) {
		    // If the 'size' is 0, make new node and link it
		    head.next = newNode;
		    newNode.previous = head;
		    newNode.next = tail;
		    tail.previous = newNode;
		} else {	// If the list is not empty, find the last node.
		    
		    Node lastNode = tail.previous;
		    if (lastNode.count < nodeSize)  lastNode.addItem(item); // If the last node has room for more items, add 'item' to it.
		    else {
		        // link it to the previous last node and 'tail'.
		        lastNode.next = newNode;
		        newNode.previous = lastNode;
		        newNode.next = tail;
		        tail.previous = newNode;
		    }
		}
		size++;
		return true;

  }

  @Override
  public void add(int pos, E item)
  {
	   if (pos < 0 || pos > size) throw new IndexOutOfBoundsException("Position is out of bounds.");  // if pos is not possible
	   if (item == null) throw new NullPointerException(); // if item is null
	   // if size is 0 or if the pos is the size then just add the item.
	   if (size == 0 || pos == size) add(item);
	   
	   NodeInfo nodeInfo = find(pos);
	   Node currentNode = nodeInfo.node;
	   int offset = nodeInfo.offset;
	   
	   // If the insertion point is at the beginning of the current node.
	   if (offset == 0) {
		   if (currentNode.previous.count < nodeSize && currentNode.previous != head) {
			   currentNode.previous.addItem(item);
			   size++;
	           return;
		   } else if (currentNode == tail) {
			   add(item);
			   size++;
			   return;
		   }
	   }
	   
	   if (currentNode.count < nodeSize) currentNode.addItem(offset, item); 
	   else {		// If the current node is full, split it and create a new node
		   Node newNode = new Node();
		   int half = nodeSize / 2;
		   int count = 0;
		   			// Move half of the elements from the current node to the new node.\
		   while (count < half) {
			   newNode.addItem(currentNode.data[half]);
			   currentNode.removeItem(half);
			   count++;
		   }

		   Node frontNode = currentNode.next;
	       // link the nodes
		   newNode.next = frontNode;
		   newNode.previous = currentNode;
		   currentNode.next = newNode;
		   frontNode.previous = newNode;
		   
		   if (offset <= half) {
			   currentNode.addItem(offset, item);
		   }
		   if (offset > half) {
			   newNode.addItem((offset - half), item);
		   }
	   }
	   size++; // increment size
  }

  @Override
  public E remove(int pos)
  {
	  if (pos < 0 || pos > size) throw new IndexOutOfBoundsException("Position is out of bounds."); // same thing
	  NodeInfo nodeInfo = find(pos);
	  Node currentNode = nodeInfo.node;
	  int offset = nodeInfo.offset;
	  E removedValue = currentNode.data[offset];

	  if (currentNode.next == tail && currentNode.count == 1) {
	      // Special case: The current node is the tail and has only one item.
	      // Remove the node from the list.
	      currentNode.previous.next = currentNode.next;
	      currentNode.next.previous = currentNode.previous;
	      currentNode = null;
	  } else {
		  
	      if (currentNode.next == tail || currentNode.count > nodeSize / 2) {
	          // Normal case or the current node has more than half of the maximum capacity.
	          // Simply remove the item from the current node.
	          currentNode.removeItem(offset);
	      } else {
	          // The current node has less than or equal to half of the maximum capacity,
	          // which requires redistributing items with the next node.

	          // Remove the item from the current node.
	          currentNode.removeItem(offset);
	          Node newNode = currentNode.next;
	          if (newNode.count > nodeSize / 2) {
	              // Transfer an item from the next node to the current node.
	              currentNode.addItem(newNode.data[0]);
	              newNode.removeItem(0);
	          } else if (newNode.count <= nodeSize / 2) {
	              // Merge the current node with the next node.
	              for (int i = 0; i < newNode.count; i++) {
	                  currentNode.addItem(newNode.data[i]);
	              }
	              currentNode.next = newNode.next;
	              newNode.next.previous = currentNode;
	              newNode = null;
	          }
	      }
	  }
	  // Decrease the size of the list since the item has been removed.
	  size--;
	  return removedValue; 
  }

  /**
   * Sort all elements in the stout list in the NON-DECREASING order. You may do the following. 
   * Traverse the list and copy its elements into an array, deleting every visited node along 
   * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting 
   * efficiency is not a concern for this project.)  Finally, copy all elements from the array 
   * back to the stout list, creating new nodes for storage. After sorting, all nodes but 
   * (possibly) the last one must be full of elements.  
   *  
   * Comparator<E> must have been implemented for calling insertionSort().    
   */
  public void sort()
  {	  
	  
	  E[] data = collectData(); // store the data
	  // Create a custom comparator for comparing elements.
	  Comparator<E> comparator = new Comparator<E>() {
	      @Override
	      public int compare(E o1, E o2) {
	          return o1.compareTo(o2);
	      }
	  };

	  // Sort the array using insertionSort with the comparator.
	  insertionSort(data, comparator);

	  // Clear the size of the list.
	  size = 0;

	  // Rebuild the list with the sorted elements.
	  for (int i = 0; i < data.length; i++) {
	      add(data[i]);
	  }
  }
  
  /**
   * Sort all elements in the stout list in the NON-INCREASING order. Call the bubbleSort()
   * method.  After sorting, all but (possibly) the last nodes must be filled with elements.  
   *  
   * Comparable<? super E> must be implemented for calling bubbleSort(). 
   */
  public void sortReverse() 
  {
	  // collect the data and bubble sort
	  E[] data = collectData();
	  bubbleSort(data);
	  size = 0; // reset
	  for (int i = 0; i < data.length; i++) {
	      add(data[i]);
	  }

  }
  
  @Override
  public Iterator<E> iterator() {
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator() {
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator(int index) {
    return new StoutListIterator(index);
  }
  
  /**
   * Returns a string representation of this list showing
   * the internal structure of the nodes.
   */
  public String toStringInternal() {
    return toStringInternal(null);
  }

  /**
   * Returns a string representation of this list showing the internal
   * structure of the nodes and the position of the iterator.
   *
   * @param iter
   *            an iterator for this list
   */
  public String toStringInternal(ListIterator<E> iter) 
  {
      int count = 0;
      int position = -1;
      if (iter != null) {
          position = iter.nextIndex();
      }

      StringBuilder sb = new StringBuilder();
      sb.append('[');
      Node current = head.next;
      while (current != tail) {
          sb.append('(');
          E data = current.data[0];
          if (data == null) {
              sb.append("-");
          } else {
              if (position == count) {
                  sb.append("| ");
                  position = -1;
              }
              sb.append(data.toString());
              ++count;
          }

          for (int i = 1; i < nodeSize; ++i) {
             sb.append(", ");
              data = current.data[i];
              if (data == null) {
                  sb.append("-");
              } else {
                  if (position == count) {
                      sb.append("| ");
                      position = -1;
                  }
                  sb.append(data.toString());
                  ++count;

                  // iterator at end
                  if (position == size && count == size) {
                      sb.append(" |");
                      position = -1;
                  }
             }
          }
          sb.append(')');
          current = current.next;
          if (current != tail)
              sb.append(", ");
      }
      sb.append("]");
      return sb.toString();
  }


  /**
   * Node type for this list.  Each node holds a maximum
   * of nodeSize elements in an array.  Empty slots
   * are null.
   */
  private class Node
  {
    /**
     * Array of actual data elements.
     */
    // Unchecked warning unavoidable.
    public E[] data = (E[]) new Comparable[nodeSize];
    
    /**
     * Link to next node.
     */
    public Node next;
    
    /**
     * Link to previous node;
     */
    public Node previous;
    
    /**
     * Index of the next available offset in this node, also 
     * equal to the number of elements in this node.
     */
    public int count;

    /**
     * Adds an item to this node at the first available offset.
     * Precondition: count < nodeSize
     * @param item element to be added
     */
    void addItem(E item) {
        if (count >= nodeSize)
        {
          return;
        }
        data[count++] = item;
	}
    /**
     * Adds an item to this node at the indicated offset, shifting
     * elements to the right as necessary.
     * 
     * Precondition: count < nodeSize
     * @param offset array index at which to put the new element
     * @param item element to be added
     */
    void addItem(int offset, E item) {
        if (count >= nodeSize)
        {
      	  return;
        }
        for (int i = count - 1; i >= offset; --i)
        {
          data[i + 1] = data[i];
        }
        ++count;
        data[offset] = item;
    }

    /**
     * Deletes an element from this node at the indicated offset, 
     * shifting elements left as necessary.
     * Precondition: 0 <= offset < count
     * @param offset
     */
    void removeItem(int offset) {
        E item = data[offset];
        for (int i = offset + 1; i < nodeSize; ++i)
        {
          data[i - 1] = data[i];
        }
        data[count - 1] = null;
        --count;
      }    
  }
 
  private class StoutListIterator implements ListIterator<E>
  {
	/**
	 * the current position
	 */
	private int position;
	/**
	 * counter for iterator methods
	 */
	private int counter;

    /**
     * Default constructor 
     */
    public StoutListIterator() {
		position = 0;
		counter = -1;
		collectData();
    }

    /**
     * Constructor finds node at a given position.
     * @param pos
     */
    public StoutListIterator(int pos) {
		position = pos;
		counter = -1;
		collectData();
    }

    @Override
    public boolean hasNext() {
    	return position < size;
    }

    @Override
    public E next() {
    	if (!hasNext()) throw new NoSuchElementException();
    	counter = 1;
		return data[position++];
    }

    @Override
    public void remove() {
    	if (counter == 1) {
    	    // If counter is 1, remove the element at the previous position.
    	    StoutList.this.remove(position - 1); 

    	    // Collect data (not shown in the provided code).
    	    collectData();

    	    // Reset counter and adjust the position.
    	    counter = -1;
    	    position--;

    	    // Ensure position is not negative.
    	    if (position < 0)
    	        position = 0;
    	} else if (counter == 0) {
    	    // If counter is 0, remove the element at the current position.
    	    StoutList.this.remove(position);

    	    // Collect data (not shown in the provided code).
    	    collectData();

    	    // Reset counter.
    	    counter = -1;
    	}
    }

	@Override
	public boolean hasPrevious() {
		return position > 0;
	}

	@Override
	public E previous() {
		if (!hasPrevious()) throw new NoSuchElementException();
		counter = 0;
		position--;
		return data[position];
	}

	@Override
	public int nextIndex() {
		return position + 1;
	}

	@Override
	public int previousIndex() {
		return position - 1;
	}

	@Override
	public void set(E e) {

		if (counter == 0) {
		    // If counter is 0, update the element at the current position.
		    NodeInfo nodeInfo = find(position);
		    nodeInfo.node.data[nodeInfo.offset] = e;
		    
		    // Also update the corresponding element in the data array.
		    data[position] = e;
		    
		} else if (counter == 1) {
		    // If counter is 1, update the element at the previous position.
		    NodeInfo nodeInfo = find(position - 1);
		    nodeInfo.node.data[nodeInfo.offset] = e;
		    
		    // Also update the corresponding element in the data array.
		    data[position - 1] = e;
		} 
	}

	@Override
	public void add(E e) {
		if (e == null)  throw new NullPointerException();
		
		// Add the element at the specified position in the StoutList.
		StoutList.this.add(position, e);

		// Increment the position.
		position++;

		// Reset the counter and collect data (not shown in the provided code).
		counter = -1;
		collectData();
	}
  }
  
  

  /**
   * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order. 
   * @param arr   array storing elements from the list 
   * @param comp  comparator used in sorting 
   */
  private void insertionSort(E[] arr, Comparator<? super E> comp)
  {
	  	int len = arr.length;
		for (int i = 1; i < len; i++) {
			E key = arr[i];
			int j = i - 1;
			
			while (j >= 0 && comp.compare(arr[j], key) > 0) {
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = key;
		}
  }
  
  /**
   * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a 
   * description of bubble sort please refer to Section 6.1 in the project description. 
   * You must use the compareTo() method from an implementation of the Comparable 
   * interface by the class E or ? super E. 
   * @param arr  array holding elements from the list
   */
  private void bubbleSort(E[] arr)
  {
	    int len = arr.length;
	    for (int i = 0; i < len - 1; i++) {
	        for (int j = 0; j < len - i - 1; j++) {
	            if (arr[j].compareTo(arr[j + 1]) < 0) { // Sorting in non-increasing order
	                E temp = arr[j];
	                arr[j] = arr[j + 1];
	                arr[j + 1] = temp;
	            }
	        }
	    }
  }
  
	/**
	 * returns the node and offset for the given logical index
	 */
	private class NodeInfo {
		public Node node;
		public int offset;

		public NodeInfo(Node node, int offset) {
			this.node = node;
			this.offset = offset;
		}
	}


	/**
	 * help finds a item at the parameter
	 * @param pos
	 * @return
	 */
	private NodeInfo find(int pos) {
		// Initialize a temporary node and set the current position to 0.
		Node temp = head.next;
		int currPos = 0;

		// Iterate through the linked list.
		while (temp != tail) {
		    if (currPos + temp.count <= pos) {
		        // If the current position plus the count of the current node is less than or equal to the target position (pos),
		        // update the current position, move to the next node, and continue searching
		        currPos += temp.count;
		        temp = temp.next;
		        continue;
		    }
		    // If we reach this point we have found the node where the target position is located
		    // Create a NodeInfo object to represent the node and offset within the node
		    NodeInfo nodeInfo = new NodeInfo(temp, pos - currPos);
		    return nodeInfo;
		}

		// If the loop finishes without finding the target position, return null (position is out of bounds)
		return null;
  }

	
	/**
	 * Collects the data from all nodes and items
	 * @return data
	 */
	private E[] collectData() {
		// Create an array to store the elements.
		data = (E[]) new Comparable[size];

		int tempIndex = 0;
		Node temp = head.next;

		// Iterate through the nodes in the linked list.
		while (temp != tail) {
		    for (int i = 0; i < temp.count; i++) {
		        // Copy the elements from each node into the array.
		        data[tempIndex] = temp.data[i];
		        tempIndex++;
		    }
		    temp = temp.next;
		}

		// Return the array containing the elements from the StoutList.
		return data;
	}
}