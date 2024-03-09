package test;

import edu.iastate.cs228.hw3.StoutList;

public class TestClass {
	public static void main(String[] args) {
	StoutList list = new StoutList();

	list.add("A");
	list.add("B");
	list.add("C");
	list.add("D");
	list.add("C");
	list.add("D");
	list.add("E");
	list.remove(2);
	list.remove(2);
	System.out.println("(Figure 3) Starting list:\n" +list.toStringInternal());
	System.out.println();

	list.add("V");
	System.out.println("(Figure 4) After adding V:\n" +list.toStringInternal());
	System.out.println();

	list.add("W");
	System.out.println("(Figure 5) After adding W:\n" +list.toStringInternal());
	System.out.println();

	list.add(2, "X");
	System.out.println("(Figure 6) After adding X at 2:\n" +list.toStringInternal());
	System.out.println();

	list.add(2, "Y");
	System.out.println("(Figure 7) After adding Y at 2:\n" +list.toStringInternal());
	System.out.println();

	list.add(2, "Z");
	System.out.println("(Figure 8) After adding Z at 2:\n" +list.toStringInternal());
	System.out.println();

	list.remove(9);
	System.out.println("(Figure 10) After removing W at 9:\n" +list.toStringInternal());
	System.out.println();

	list.remove(3);
	System.out.println("(Figure 11) After removing Y at 3:\n" +list.toStringInternal());
	System.out.println();

	list.remove(3);
	System.out.println("(Figure 12) After removing X at 3:\n" +list.toStringInternal());
	System.out.println();

	list.remove(5);
	System.out.println("(Figure 13) After removing E at 5:\n" +list.toStringInternal());
	System.out.println();

	list.remove(3);
	System.out.println("(Figure 14) After removing C at 3:\n" +list.toStringInternal());
	System.out.println();
}
}
