package edu.iastate.cs228.hw1;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;
/**
 * @author Anoop Boyal
 *
 * The ISPBusiness class performs simulation over a grid 
 * plain with cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {
	
	private static int profit = 0;


	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		
		// iterates the grid and updates it by switching the cases by what needs change
		
		
		Town townUpdate = new Town(tOld.getLength(), tOld.getWidth());
		for (int i = 0; i < tOld.getWidth(); i++) {
			for (int j = 0; j < tOld.getWidth(); j++) {
				townUpdate.grid[i][j] = tOld.grid[i][j].next(townUpdate); 
			}
		}
		return townUpdate;
	}
	
	/**
	 * Returns the profit for the current state in the town grid.
	 * @param town
	 * @return
	 */
	public static int getProfit(Town town) { // finds casuals and adds 1 to profit count
		for (int i = 0; i < town.getLength(); i++) {
			for (int j = 0; j < town.getWidth(); j++) {
				if (town.grid[i][j].who() == State.CASUAL) {
					profit += 1;
				}
			}
		}
		return profit;
	}
	

	/**
	 *  Main method. Interact with the user and ask if user wants to specify elements of grid
	 *  via an input file (option: 1) or wants to generate it randomly (option: 2).
	 *  
	 *  Depending on the user choice, create the Town object using respective constructor and
	 *  if user choice is to populate it randomly, then populate the grid here.
	 *  
	 *  Finally: For 12 billing cycle calculate the profit and update town object (for each cycle).
	 *  Print the final profit in terms of %. You should print the profit percentage
	 *  with two digits after the decimal point:  Example if profit is 35.5600004, your output
	 *  should be:
	 *
	 *	35.56%
	 *  
	 * Note that this method does not throw any exception, so you need to handle all the exceptions
	 * in it.
	 * 
	 * @param args
	 * 
	 */
	public static void main(String []args) {
		
		// Gets User input to either load file or generate 
        Scanner scnr = new Scanner(System.in); 
        System.out.println("Enter 1 to load a file or 2; random grid with seed ");
        int option = scnr.nextInt(); 
        int length, width, seed;
        
        // if User loads file this option scans file then loads it
        Town town;
        if (option == 1) {
        	
            Scanner scnr2 = new Scanner(System.in);
            System.out.println("Enter filename without (.txt)");
            String filename = scnr2.next() + ".txt";
            
            try { 
                town = new Town(filename);
                
            } catch (FileNotFoundException e) {
            	System.out.println("File cannot be found");
                scnr.close();
                scnr2.close();
                return;
            }
        // if User decides to generate this will make a random grid and can be used with a seed
        } else if(option == 2) {
        	
            Scanner scnr3 = new Scanner(System.in);
            System.out.println("What is length, width, and seed? (Separate with a space): ");
            length = scnr3.nextInt();
            width = scnr3.nextInt();
            seed = scnr3.nextInt();
            town = new Town(length, width);
            town.randomInit(seed);
            scnr3.close();
            
        } else { // if user does not type 1 or 2
        	
            System.out.println("Invalid Input");
            return;
        }
 

        int totalProfit = 0;
        
        // generates the simulation for 12 iterations and calculates how much of the 
        // potential profit actually earned
        
        for (int i = 1; i < 13; i++) {  
            profit = 0;
            profit = getProfit(town);
            totalProfit += profit;
            System.out.println(town);
            System.out.println("Profit: " + profit);
            System.out.println("Iteration: " + i);
            town =  updatePlain(town);
            
        }
        
   		int potentialProfit = town.getLength() * town.getWidth() * 12;
  		double profitPercentage = (totalProfit * 100.00) / potentialProfit;
  		DecimalFormat df = new DecimalFormat("0.00");
  	    String formattedNumber = df.format(profitPercentage);
        System.out.println("Profit %: " + formattedNumber); 
        
	}
}
