package edu.iastate.cs228.hw1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;



/**
 *  @author Anoop Boyal
 *
 */
public class Town {
	private int length, width;  //Row and col (first and second indices)
	public TownCell[][] grid;
	
	/**
	 * Constructor to be used when user wants to generate grid randomly, with the given seed.
	 * This constructor does not populate each cell of the grid (but should assign a 2D array to it).
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		grid = new TownCell[length][width];
		this.length = length;
		this.width = width;
		
	}
	
	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of catching it.
	 * Ensure that you close any resources (like file or scanner) which is opened in this function.
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException { 
        
		try { 
		// find file scan then iterate through the grid and transfer the info to make the same grid
        File file = new File(inputFileName);
        Scanner scnr = new Scanner(file); 
        int length = scnr.nextInt();
        int width = scnr.nextInt();
        grid = new TownCell[length][width];
     
  
        for (int i = 0; i < length; i++) {
            String[] row = scnr.nextLine().split(" ");
            for (int j = 0; j < row.length; j++) {
            	if (row[i] == "C") {
            		grid[i][j] = new Casual(this, i, j);
            		
            	} else if (row[i] == "E") {
            		grid[i][j] = new Empty(this, i, j);
            		
            	} else if (row[i] == "O") {
            		grid[i][j] = new Outage(this, i, j);
            		
            	} else if (row[i] == "R") {
            		grid[i][j] = new Reseller(this, i, j);
            		
            	} else if (row[i] == "S") {
            		grid[i][j] = new Streamer(this, i, j);
            		}
            	}
        	}
        
        scnr.close();
        
		} catch (FileNotFoundException e ) {
			return;
		}
	}
        
    
	
	/**
	 * Returns width of the grid.
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns length of the grid.
	 * @return
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following class object:
	 * Casual, Empty, Outage, Reseller OR Streamer
	 */
	public void randomInit(int seed) { 
		
		// randomly assign the grid with random number each number is a cell type which then gets places into the cell
		Random rand = new Random(seed);
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				int randInt = rand.nextInt(5);
				
				if (randInt == 0) {
					grid[i][j] = new Reseller(this, i, j);
					
				} else if (randInt == 1) {
					grid[i][j] = new Empty(this, i, j);
					
				} else if (randInt == 2) {
					grid[i][j] = new Casual(this, i, j);
					
				} else if (randInt == 3) {
					grid[i][j] = new Outage(this, i, j);
					
				} else if (randInt == 4) {
					grid[i][j] = new Streamer(this, i, j);	
				}
				
				
				
				
			}
		}
	}
	
	/**
	 * Output the town grid. For each square, output the first letter of the cell type.
	 * Each letter should be separated either by a single space or a tab.
	 * And each row should be in a new line. There should not be any extra line between 
	 * the rows.
	 */
	@Override
	public String toString() { 
		String s = "";
		
		for (int i = 0; i < length; i++) {
			s += "\n";
			
			for (int j = 0; j < width; j++) {
			
				if (grid[i][j].who() == State.CASUAL) {
					s += "C ";
					
				} else if (grid[i][j].who() == State.EMPTY) {
					s += "E ";
					
				} else if (grid[i][j].who() == State.RESELLER) {
					s += "R ";
					
				} else if (grid[i][j].who() == State.OUTAGE) {
					s += "O ";
					
				} else if (grid[i][j].who() == State.STREAMER) {
					s += "S ";	
				}
			}
		}
		return s;
	}
}

