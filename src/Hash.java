import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;



public class Hash {
	

	public static void main(String[] args) 
			throws FileNotFoundException, UnsupportedEncodingException {
		// Initialize scanner for array size user input
		Scanner scan = new Scanner(System.in);
		System.out.println("What is the size of your array? (100 vs. 200)");
        int size = scan.nextInt();
        scan.close();
        
		ArrayList<String> input = readWords("/Users/laurenelisegardiner/Desktop/input.txt");
        
        // Initial hash tables
        Hashtable<Integer, String> defaultHash = defaultHash(input, size);
        Hashtable<Integer, String> customHash = customHash(input, size);
        Hashtable<Integer, String> creativeHash = creativeHash(input, size);
        	
        // Write out hash tables
		write("output1.txt", defaultHash, size);
		write("output2.txt", customHash, size);
		write("output3.txt", creativeHash, size);
		
		// Measure performance for each hash
		performance(size, defaultHash, customHash, creativeHash);
		
	}
	
	public static ArrayList<String> readWords(String filename) 
		throws FileNotFoundException
	{
		// Initialize scanner and empty array list for words
		ArrayList<String> words = new ArrayList<String>();
		Scanner inp = new Scanner(new File(filename));
		// Read in each word from input
		while(inp.hasNext()){
			words.add(inp.next());
		}
		inp.close();
		return words;
	}
	
	// Hashes value with the hashCode and scales by user inputed size
	public static Hashtable<Integer, String> defaultHash(ArrayList<String> file, int size) 
		{
		// Initialize hash table
		   Hashtable<Integer, String> result = new Hashtable<Integer, String>();
		   for(String value: file) {
			   // If there is not a value at the hash, add it to that line
				if(result.get(Math.abs(value.hashCode() % size)) == null) {
					result.put(Math.abs(value.hashCode() % size), value);
				}
				// If there is a value at the hash, add it to existing list at that hash
				else {
					result.put(Math.abs(value.hashCode() % size), result.get(Math.abs(value.hashCode() % size)) + ", " + value);
				}
			}
		return result;
		}
	
	
	// Hashes value based on the sum of the ascii values of each character
	public static Hashtable<Integer, String> customHash(ArrayList<String> file, int size) 
	{
	// Initialize hash table
	   Hashtable<Integer, String> result = new Hashtable<Integer, String>();
	   for(String value: file) {
		   int asciiSum = 0;
		   // Calculate the sum of ascii values of each character that make up a word
		   for(int j = 0; j < value.length(); j++) {
				asciiSum = asciiSum + (int) value.charAt(j);
				}
		   // If there is not a value at the hash, add it to that line
		   if(result.get(Math.abs(asciiSum % size)) == null) {
				result.put(Math.abs(asciiSum % size), value);
			}
		   // If there is a value at the hash, add it to existing list at that hash
		   else {
				result.put(Math.abs(asciiSum % size), result.get(Math.abs(asciiSum % size)) + ", " + value);
			}
	   	}
	return result;
	}
	
	// Hashes value based with creativeHash that uses ascii value and prime numbers
	public static Hashtable<Integer, String> creativeHash(ArrayList<String> file, int size) 
	{
	   Hashtable<Integer, String> result = new Hashtable<Integer, String>();
	   	for(String value: file) {
	   		// Calculate sum of the ascii values of each character with seed 7 and multiply sum by 31
	   		int asciiWord = 7; 
	   		for(int j = 0; j < value.length(); j++) {
	   			asciiWord = 31 * asciiWord + (int) value.charAt(j);
			}
	   		// If there is not a value at the hash, add it to that line
			if(result.get(Math.abs(asciiWord % size)) == null) {
				result.put(Math.abs(asciiWord % size), value);
			}
			// If there is a value at the hash, add it to existing list at that hash
			else {
				result.put(Math.abs(asciiWord % size), result.get(Math.abs(asciiWord % size)) + ", " + value);
			}
	   	}
	return result;
	}
	
	// Writes hash table to file
	public static void write(String filename, Hashtable<Integer, String> hash, int size) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter(filename, "UTF-8");
		for(int i = 0; i < size; i++) {
			// If there are no values at a hash, print "empty line"
			if(hash.get(i) == null) {
				writer.println(i+1 + ". EMPTY LINE...\n");
			}
			// Print values at a hash
			else {
				writer.println(i+1 + ". " + hash.get(i) + "\n");
			}
		}
		writer.close();
	}
	
	// Measure performance of each hash by determining occupancy (% of hashes that have values)
	public static void performance(int size, Hashtable<Integer, String> defaultHash, Hashtable<Integer, String> customHash, 
			Hashtable<Integer, String> creativeHash) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter("performance.txt", "UTF-8");
		writer.println("Percent Occupancy");
		writer.println("Java Default Hash: " +  (float) defaultHash.size()/size * 100 + "%");
		writer.println("ASCII Sum: " + (float) customHash.size()/size * 100 + "%");
		writer.println("Custom Hash: " + (float) creativeHash.size()/size * 100 + "%");
		writer.close();
	}
}


