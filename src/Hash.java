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
		
		Scanner scan = new Scanner(System.in);
		System.out.println("What is the size of your array? (100 vs. 200)");
        int size = scan.nextInt();
        scan.close();
        
		ArrayList<String> input = readWords("/Users/sarahgreenwood/Desktop/input.txt");
        
        
        Hashtable<Integer, String> defaultHash = defaultHash(input, size);
        Hashtable<Integer, String> customHash = customHash(input, size);
        Hashtable<Integer, String> creativeHash = creativeHash(input, size);

		write("output1.txt", defaultHash, size);
		write("output2.txt", customHash, size);
		write("output3.txt", creativeHash, size);
		
		performance(size, defaultHash, customHash, creativeHash);
		
	}
	
	public static ArrayList<String> readWords(String filename) 
		throws FileNotFoundException
	{
		ArrayList<String> words = new ArrayList<String>();
		Scanner inp = new Scanner(new File(filename));
		while(inp.hasNext()){
			words.add(inp.next());
		}
		inp.close();
		return words;
	}
	
	public static Hashtable<Integer, String> defaultHash(ArrayList<String> file, int size) 
		{
		   Hashtable<Integer, String> result = new Hashtable<Integer, String>();
		   for(String value: file) {
				if(result.get(Math.abs(value.hashCode() % size)) == null) {
					result.put(Math.abs(value.hashCode() % size), value);
				}
				else {
					result.put(Math.abs(value.hashCode() % size), result.get(Math.abs(value.hashCode() % size)) + ", " + value);
				}
			}
		return result;
		}
	
	public static Hashtable<Integer, String> customHash(ArrayList<String> file, int size) 
	{
	   Hashtable<Integer, String> result = new Hashtable<Integer, String>();
	   for(String value: file) {
		   int asciiSum = 0;
		   for(int j = 0; j < value.length(); j++) {
				asciiSum = asciiSum + (int) value.charAt(j);
				}
		   if(result.get(Math.abs(asciiSum % size)) == null) {
				result.put(Math.abs(asciiSum % size), value);
			}
		   else if(result.get(Math.abs(asciiSum % size)) != null) {
				result.put(Math.abs(asciiSum % size), result.get(Math.abs(asciiSum % size)) + ", " + value);
			}
	   	}
	return result;
	}
	
	public static Hashtable<Integer, String> creativeHash(ArrayList<String> file, int size) 
	{
	   Hashtable<Integer, String> result = new Hashtable<Integer, String>();
	   	for(String value: file) {
	   		int asciiWord = 7; 
	   		for(int j = 0; j < value.length(); j++) {
	   			asciiWord = 31 * asciiWord + (int) value.charAt(j);
			}
			if(result.get(Math.abs(asciiWord % size)) == null) {
				result.put(Math.abs(asciiWord % size), value);
			}
			else {
				result.put(Math.abs(asciiWord % size), result.get(Math.abs(asciiWord % size)) + ", " + value);
			}
	   	}
	return result;
	}
	
	public static void write(String filename, Hashtable<Integer, String> hash, int size) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter(filename, "UTF-8");
		for(int i = 0; i < size; i++) {
			if(hash.get(i) == null) {
				writer.println(i+1 + ". EMPTY LINE...\n");
			}
			else {
				writer.println(i+1 + ". " + hash.get(i) + "\n");
			}
		}
		writer.close();
	}
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


