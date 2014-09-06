package rotation2014fall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/*****
 * after Relative Distance Orc, all relative distance are available;
 * transfer all the relative distance to (x, y) pairs;
 * save the coordinates pairs into orc_write_output_small.txt;
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
		Scanner ORC_output_reader = new Scanner(new File("orc_output_small.csv"));
		
		//2nd, create an array of relate_distance a[2001];
		int[] a = new int[2001];
		
		String title = ORC_output_reader.nextLine();
		System.out.println(" " + title +": ");
		
		while(ORC_output_reader.hasNextInt()){
			
				int relative_distance = ORC_output_reader.nextInt();
				
				int index = relative_distance + 1000;
				
				//only save [-1000, +1000]
				//at first, I tried to save all relative distance to the data_file, 
				//then the file size was over 600M
				//so later I just put numbers between -1000 and +1000 into the data_file;
				if(index>=0 && index<2001)
					a[index]++;
		
			
		}//end while ORC_reader.hasNextLine() loop;
		
		
		ORC_output_reader.close();
		
		
		//3rd, printout the array:
		printArray(a);
		
		
		//4th, save all data to a new file;
		File output_file = new File("orc_write_output_small.txt");
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));
		
		output.write("Relative position, Total ChiP Signal" +"\n");
		
		for(int i=0; i<2001; i++){
			
			output.write(i-1000 + ",");
			output.write(a[i] + "\n");
		}
		
	//	output.write("\n");
		
		output.close();
		
	}//end of main();

	/*********
	 * Printout an array, line by line;
	 * @param a
	 */
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
