package hw4;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
/**
 * @author anoopboyal
 */
import java.util.Stack;
import java.util.stream.Collectors;

public class MsgTree {
	public char payloadChar;
	public MsgTree left;
	public MsgTree right;
	/*Can use a static char idx to the tree string for recursive
	solution, but it is not strictly necessary*/
	private static int staticCharIdx = 0;
	//Constructor building the tree from a string
	/**
	 * the binary code
	 */
	private static String binCode;

	/**
	 * Constructor building the tree from a string
	 * @param encodingString string pulled from data file
	 */
	public MsgTree(String encodingString) {
		
		this.payloadChar = encodingString.charAt(staticCharIdx);
		staticCharIdx = staticCharIdx + 1;
		this.left = new MsgTree(encodingString.charAt(staticCharIdx));
		
		if (this.left.payloadChar == '^') { // new tree
			this.left = new MsgTree(encodingString);
		}

		staticCharIdx = staticCharIdx + 1;
		this.right = new MsgTree(encodingString.charAt(staticCharIdx));
		if (this.right.payloadChar == '^') { // new tree
			this.right = new MsgTree(encodingString);
		}
	}

	public MsgTree(char payloadChar) {
		this.payloadChar = payloadChar;
	}

	/**
	 * method to print characters and their binary codes
	 * @param root
	 * @param code
	 */
	public static void printCodes(MsgTree root, String encodingString) {
		System.out.println("character code");
		System.out.println("-------------------------");
		
		
		for (char i : encodingString.toCharArray()) { // goes through the letters and gets the binary codes
			findPath(root, i, binCode = "");
			System.out.println(i + "       " + binCode);
		}
	}
	
	/**
	 * gets the path for the letter
	 * @param root
	 * @param x
	 * @param path
	 * @return
	 */
	private static boolean findPath(MsgTree root, char x, String path) {
		if (root == null) return false;
		if (root.payloadChar == x) binCode = path;
		return findPath(root.left, x, path + "0") || findPath(root.right, x, path + "1");
	}

	/**
	 * to decode the message, It will print the decoded message to the console
	 * @param codes
	 * @param msg
	 */
	public void decode(MsgTree codes, String msg) {


		MsgTree tree = codes;
		char curChar;
		int curCharIndex = 0;
		String decodedMsg = "";

		while (curCharIndex < msg.length()) {
			curChar = msg.charAt(curCharIndex);
			if (curChar == '0') {
				if (tree.left == null) {
					decodedMsg += tree.payloadChar;
					tree = codes;
				} else {
					tree = tree.left;
					curCharIndex = curCharIndex + 1;
				}
			} else if (curChar == '1') {
				if (tree.right == null) {
					decodedMsg += tree.payloadChar;
					tree = codes;
				} else {
					tree = tree.right;
					curCharIndex = curCharIndex + 1;
				}
			}
			
			if (curCharIndex == msg.length() - 1 ) {
				if (curChar == '0') {
					decodedMsg += tree.left.payloadChar;
					curCharIndex = curCharIndex + 1;
				}
				
				else {
					decodedMsg += tree.right.payloadChar;
					curCharIndex = curCharIndex + 1;
				}
			}
		}
		System.out.println(decodedMsg);
	}

	/**
	 * extra credit for the statistics
	 */
	public static void extraCred(String encodingString, String binaryCode) {
		System.out.println("STATISTICS:");
		System.out.println("Avg bits/char: " + encodingString.length() / binaryCode.length());
		System.out.println("Total characters: " + binaryCode.length());
		System.out.printf("Space savings: \t%.1f%%", (1 - binaryCode.length() / (double) encodingString.length()) * 100);
	}


	
	public static void main(String[] args) throws IOException {
		System.out.println("Please enter filename to decode:");	
		Scanner scnr = new Scanner(System.in); 
		String file = scnr.nextLine();
		scnr.close();
		String data = new String(Files.readAllBytes(Paths.get(file))).trim();
		int end = data.lastIndexOf('\n');	
		String message = data.substring(0, end);
		String binaryCode = data.substring(end).trim(); 

		Set<Character> chars = new HashSet<>();
		for (char x : message.toCharArray()) {
			if (x != '^') {
				chars.add(x);
			}
		}
		String letters = chars.stream().map(String::valueOf).collect(Collectors.joining());
		MsgTree root = new MsgTree(message);
		
		MsgTree.printCodes(root, letters);
		root.decode(root, binaryCode);
		extraCred(binaryCode,message);
		
	}
}
