package rotation2014fall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/*****
 * after Relative Distance Orc, all relative distance are available;
 * transfer all the relative distance to (x, y) pairs;
 * 
 * create an array a[2001]: 
 * a[0] -> relative_distance at -1000;
 * a[2000] -> relative_distance at +1000;
 * 
 * @author Jeff
 *
 */
public class GetXandY {
	
	public static void main(String[] args) throws IOException{
		
		System.out.println("Read data from orc_output.csv.");
		
		//1st, read relative distance data from orc_output.csv file;
		Scanner ORC_reader = new Scanner(new File("orc_output.csv"));
		
		//2nd, create an array of relate_distance a[2001];
		int[] a = new int[2001];
		
		String title = ORC_reader.nextLine();
		System.out.println(" " + title +": ");
		
		while(ORC_reader.hasNextLine()){
			
			String line = ORC_reader.nextLine();
			String[] split = line.split(",");
			int len = split.length;
			
			int relative_distance = Integer.parseInt(split[len-1]);
			
			int index = relative_distance + 1000;
			
			if(index>=0 && index<2001)
				a[index]++;
			
		}//end while ORC_reader.hasNextLine() loop;
		
		
		ORC_reader.close();
		
		
		//3rd, printout the array:
		printArray(a);
		
		
		//4th, save all data to a new file;
		File output_file = new File("orc_output.txt");
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));
		
		for(int i=0; i<2001; i++){
			output.write(a[i] + "\n");
		}
		
		//output.write("\n");
		output.close();
		
	}//end of main();

	private static void printArray(int[] a) {
		// TODO printout an array;
		
		int len = a.length;
		
		for(int i=0; i<len; i++){
						
			if(a[i]>99) {
				System.out.print(" ");
				
			} else if(a[i]>9) {
				System.out.print("  ");
				
			} else {
				System.out.print("   ");
			}
			
			System.out.print( a[i] );
			

			if(i%20 == 0) System.out.println("   i=" + i +". ");
			
		}//end for i<len loop;
		
	}//end printArray() method;

}//end of ee
