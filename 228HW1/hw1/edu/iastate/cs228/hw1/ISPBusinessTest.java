package edu.iastate.cs228.hw1;
/**
 * 
 * @author anoopboyal
 *
 */
public class ISPBusinessTest {
	
	// test if profit and update plain works
	
	public static void businessTest() {

			Town town = new Town(2,2);
			town.grid[0][0] = new Casual(town, 0,0 );
			town.grid[0][1] = new Casual(town, 0,1 );
			town.grid[1][0] = new Casual(town, 1,0 );
			town.grid[1][1] = new Casual(town, 1,1 );
			System.out.println(town);
			System.out.println("Profit:" + ISPBusiness.getProfit(town));
			town = ISPBusiness.updatePlain(town);
			System.out.println(town);
	}
 	
	
	public static void main(String[] args) {
		businessTest();
		}
	}

