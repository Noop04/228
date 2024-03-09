package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;
/**
 * 
 * @author anoopboyal
 *
 */
public class TownTest {
	//Test length and width
	public static void townTest() {
	Town town = new Town(3,1);

	System.out.println(town.getLength());
	System.out.println(town.getWidth());
	town.grid[0][0] = new Streamer(town, 0,0 );
	town.grid[1][0] = new Reseller(town, 0,1 );
	town.grid[2][0] = new Outage(town, 0,2 );
	System.out.print(town);
	
	
}
	public static void townTest1() throws FileNotFoundException {
	Town town = new Town("src1/hw1/edu.iastate.cs228.hw1/ISP4x4");
	System.out.println(town);
	
	
	
	
}
	public static void main(String[] args) throws FileNotFoundException {
		townTest();
	}
}
