package edu.iastate.cs228.hw2;
/**
 * @author anoopboyal
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
		
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	
	{
		sortingAlgorithm = algo;
		points = new Point[pts.length];
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < pts.length; i ++) {
			points[i] = pts[i];
			
		}
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		sortingAlgorithm = algo;
        File f = new File(inputFileName);
        Scanner scnr = new Scanner(f);
        
        int numInts = 0;
        
        if (f.exists() == false) {
        	throw new FileNotFoundException();
        }
        try {
            while (scnr.hasNextInt()) {
            	scnr.nextInt();
                numInts++;
            }
        } finally {
        	scnr.close();
        }

        if (numInts % 2 == 1) {
            throw new InputMismatchException();
        }

        points = new Point[numInts / 2]; // Assuming each point consists of two integers
        scnr = new Scanner(f); // Reopen the file
        int index = 0;

        try {
            while (scnr.hasNextInt()) {
                int x = scnr.nextInt();
                int y = scnr.nextInt();
                points[index] = new Point(x, y);
                index++;
            }
        } finally {
        	scnr.close();
        }

        
    }
	    

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 * @return
	 */
	public void scan()
	{
		
		AbstractSorter aSorter; 
		
		// create an object to be referenced by aSorter according to sortingAlgorithm. for each of the two 
		// rounds of sorting, have aSorter do the following: 
		// 
		//     a) call setComparator() with an argument 0 or 1. 
		//
		//     b) call sort(). 		
		// 
		//     c) use a new Point object to store the coordinates of the medianCoordinatePoint
		//
		//     d) set the medianCoordinatePoint reference to the object with the correct coordinates.
		//
		//     e) sum up the times spent on the two sorting rounds and set the instance variable scanTime. 
		
	   


		if (sortingAlgorithm == Algorithm.SelectionSort) {
			aSorter = new SelectionSorter(this.points);
		} else if (sortingAlgorithm == Algorithm.InsertionSort) {
			aSorter = new InsertionSorter(this.points);
		} else if (sortingAlgorithm == Algorithm.MergeSort) {
			aSorter = new MergeSorter(this.points);
		} else {
			aSorter = new QuickSorter(this.points);
		}
		
		int x = 0, y = 0;	
		long start = System.nanoTime();
					
		for (int i = 0; i <= 1; i++) {
			aSorter.setComparator(i);
			if (i == 0 || i == 1) {
				aSorter.sort();
			}
			if (i == 0) {
				x = aSorter.getMedian().getX();
			}
			else if (i == 1) {
				y = aSorter.getMedian().getY();
				
				medianCoordinatePoint = new Point(x, y);

			}	
		}
		long end = System.nanoTime();
		scanTime = end - start;
  
		
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats() {
		return sortingAlgorithm + " " + points.length + " " + scanTime;
		
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		return "MCP: (" + medianCoordinatePoint.getX() + ", " + medianCoordinatePoint.getY() + ")";
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
	    Writer outputFileName = null;
		try (PrintWriter writer = new PrintWriter(outputFileName)) {
	        writer.println(this.toString());
	    }
		
	}

		
}
