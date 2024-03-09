package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;

/**
 *  
 * @author Anoop Boyal
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if needed
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		super.algorithm = "mergesort";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(points);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
	    if (pts.length <= 1) {
	        return; // Base case already sorted or empty array
	    }

	    // Divide the array into two halves
	    int mid = pts.length / 2;
	    Point[] leftHalf = new Point[mid];
	    for (int i = 0; i < mid; i++) {
	    	leftHalf[i] = new Point(pts[i]);
	    }
		Point[] rightHalf = new Point[pts.length - mid];
	    for (int i = 0; i < pts.length - mid; i++) {
	    	rightHalf[i] = new Point(pts[i]);
	    }
		
	    // Recursively sort the two halves
	    mergeSortRec(leftHalf);
	    mergeSortRec(rightHalf);

	    // Merge the sorted halves back into pts
	    merge(leftHalf, rightHalf, pts);
	}

	private void merge(Point[] left, Point[] right, Point[] pts) {
	    int leftSize = left.length;
	    int rightSize = right.length;
	    int i = 0, j = 0, k = 0;

	    // Merge the left and right subarrays back into pts
	    while (i < leftSize && j < rightSize) {
	        if (left[i].compareTo(right[j]) <= 0) {
	            pts[k++] = left[i++];
	        } else {
	            pts[k++] = right[j++];
	        }
	    }
	    while (i < leftSize) {
	        pts[k++] = left[i++];
	    }

	    while (j < rightSize) {
	        pts[k++] = right[j++];
	    }
	}



}
