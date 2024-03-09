package edu.iastate.cs228.hw1;


/**
 * 
 * @author anoopboyal
 *
 */
public class TownCellTest {
	private static int CASUAL;
	private static int EMPTY;
	private static int OUTAGE;
	private static int RESELLER;
	private static int STREAMER;
	public static void townCellTest() { // test if census counts the people
		Town town = new Town(3,3);
		town.grid[0][0] = new Streamer(town, 0,0 );
		town.grid[0][1] = new Streamer(town, 0,1 );
		town.grid[0][2] = new Outage(town, 0,2 );
		town.grid[1][0] = new Empty(town, 1,0 );
		town.grid[2][0] = new Casual(town, 2,0 );
		town.grid[2][2] = new Casual(town, 2,2 );
		town.grid[1][1] = new Casual(town, 1,1 );
		town.grid[1][2] = new Casual(town, 1,2 );
		town.grid[2][1] = new Reseller(town, 2,1 );
		for (int i = 0; i <= 2; i++) {
	        for (int j = 0; j <= 2; j++) {
				State who = town.grid[i][j].who();
				if (who == State.CASUAL) {
					CASUAL += 1;
						
				} else if (who == State.EMPTY) {
					EMPTY += 1;		
					
				} else if (who == State.OUTAGE) {
					OUTAGE += 1;
					
				} else if (who == State.RESELLER) {
					RESELLER += 1;
					
				} else if (who == State.STREAMER) {
					STREAMER += 1;
						
				}
	        }
		System.out.println(town);
		System.out.println("Casual: " + CASUAL);
		System.out.println("Empty: " + EMPTY);
		System.out.println("Outage: " + OUTAGE);
		System.out.println("Reseller: " + RESELLER);
		System.out.println("Streamer: " + STREAMER);

	    
	    
		}
	}
	// test if streamer leaves

	
	public static void main(String []args) {
		townCellTest();

	}
}
