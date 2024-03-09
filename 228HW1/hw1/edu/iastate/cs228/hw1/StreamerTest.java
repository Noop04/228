package edu.iastate.cs228.hw1;
/**
 * 
 * @author anoopboyal
 *
 */
public class StreamerTest {
	// test additional rule because that must happen first
	public static void streamerTest1() {
		Town town = new Town(3,3);
		town.grid[0][0] = new Streamer(town, 0,0 );
		town.grid[0][1] = new Reseller(town, 0,1 );
		town.grid[0][2] = new Outage(town, 0,2 );
		town.grid[1][0] = new Casual(town, 1,0 );
		town.grid[2][0] = new Outage(town, 2,0 );
		town.grid[2][2] = new Outage(town, 2,2 );
		town.grid[1][1] = new Reseller(town, 1,1 );
		town.grid[1][2] = new Outage(town, 1,2 );
		town.grid[2][1] = new Outage(town, 2,1 );
		System.out.println(town);
		town = ISPBusiness.updatePlain(town);
		System.out.println(town);
	}
	// test if streamer leaves
	public static void streamerTest2() {
		Town town = new Town(3,3);
		town.grid[0][0] = new Streamer(town, 0,0 );
		town.grid[0][1] = new Outage(town, 0,1 );
		town.grid[0][2] = new Casual(town, 0,2 );
		town.grid[1][0] = new Outage(town, 1,0 );
		town.grid[2][0] = new Casual(town, 2,0 );
		town.grid[2][2] = new Casual(town, 2,2 );
		town.grid[1][1] = new Casual(town, 1,1 );
		town.grid[1][2] = new Casual(town, 1,2 );
		town.grid[2][1] = new Casual(town, 2,1 );
		System.out.println(town);
		town = ISPBusiness.updatePlain(town);
		System.out.println(town);
	}
	
	public static void main(String []args) {
		streamerTest1();
		streamerTest2();
	}
}
