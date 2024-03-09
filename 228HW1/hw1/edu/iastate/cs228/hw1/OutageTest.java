package edu.iastate.cs228.hw1;
/**
 * 
 * @author anoopboyal
 *
 */
public class OutageTest {
	//test if outage turns empty
	public static void outageTest1() {
		Town town = new Town(3,3);
		town.grid[0][0] = new Outage(town, 0,0 );
		town.grid[0][1] = new Outage(town, 0,1 );
		town.grid[0][2] = new Outage(town, 0,2 );
		town.grid[1][0] = new Outage(town, 1,0 );
		town.grid[2][0] = new Outage(town, 2,0 );
		town.grid[2][2] = new Outage(town, 2,2 );
		town.grid[1][1] = new Outage(town, 1,1 );
		town.grid[1][2] = new Outage(town, 1,2 );
		town.grid[2][1] = new Outage(town, 2,1 );
		System.out.println(town);
		town = ISPBusiness.updatePlain(town);
		System.out.println(town);
	}

	
	public static void main(String []args) {
		outageTest1();
	}
}
