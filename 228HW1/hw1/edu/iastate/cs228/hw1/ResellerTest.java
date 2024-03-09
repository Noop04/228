package edu.iastate.cs228.hw1;
/**
 * 
 * @author anoopboyal
 *
 */
public class ResellerTest {
	// test to see if reseller leaves
	public static void resellerTest1() {
		Town town = new Town(3,3);
		town.grid[0][0] = new Reseller(town, 0,0 );
		town.grid[0][1] = new Casual(town, 0,1 );
		town.grid[0][2] = new Casual(town, 0,2 );
		town.grid[1][0] = new Casual(town, 1,0 );
		town.grid[2][0] = new Casual(town, 2,0 );
		town.grid[2][2] = new Casual(town, 2,2 );
		town.grid[1][1] = new Casual(town, 1,1 );
		town.grid[1][2] = new Casual(town, 1,2 );
		town.grid[2][1] = new Casual(town, 2,1 );
		System.out.println(town);
		town = ISPBusiness.updatePlain(town);
		System.out.println(town);
	}
	// test to see if reseller leaves
	public static void resellerTest2() {
		Town town = new Town(3,3);
		town.grid[0][0] = new Reseller(town, 0,0 );
		town.grid[0][1] = new Empty(town, 0,1 );
		town.grid[0][2] = new Casual(town, 0,2 );
		town.grid[1][0] = new Empty(town, 1,0 );
		town.grid[2][0] = new Casual(town, 2,0 );
		town.grid[2][2] = new Casual(town, 2,2 );
		town.grid[1][1] = new Empty(town, 1,1 );
		town.grid[1][2] = new Casual(town, 1,2 );
		town.grid[2][1] = new Casual(town, 2,1 );
		System.out.println(town);
		town = ISPBusiness.updatePlain(town);
		System.out.println(town);
	}
	public static void main(String[] args) {
		resellerTest1();
		resellerTest2();
	}
}
