package rotation2014fall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**********************
 * Create a big table for each sub-type:
 * Chr ,Start, Counts
 * 2L, 1, xyz
 * 2L, 1000, xyz
 * .
 * .
 * .
 * 2L, 23000000, xyz
 * 2R, 1, xyz
 * 2R, 1000, xyz,
 * .
 * .
 * .
 * 2R, 24000000, xyz
 * 3L....
 *
 * @author Jeff
 *
 */

public class Bin2Table_subtype_1010 {
	
	
	/*************
	 * Main()
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		
		System.out.println("This is Bin2Table script.");
		
		
		//1st, read-in data from D:\2014FallRotation\data\yulong\1010;
		String routine = "D:/2014FallRotation/data/yulong/1010/";		
		
		//readin the title of Bin document:
		System.out.print("Please input the document to deal with: [DM456, DM457....DM463] \n document = ");
		
		Scanner input = new Scanner(System.in);
		
		String doc_name = input.next();
		
		
		//array of chromosomes:
		String[] chr_name = {"2L", "2R", "3L", "3R", "4", "X"};
		
		
		//2nd, create an output_writer to write Bin document;
		File output_file = new File(routine + "/Bin2Table/" + doc_name +"_Bin2Table_allChrs.txt");
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));
						
		output.write("chr" + "\t" + "start_pos" + "\t" + "counts" + "\n");
				
		
		//3rd, for each chromosome, write_in the big table;
		for(int i=0; i<chr_name.length; i++){	
			
			run(routine, doc_name, chr_name[i], output);
			
		}//end for i<chr_name.length loop;
		
		
		
		//4th, close output writter;
		output.close();
		input.close();
		
	}//end of main();

	
	
	/*****************
	 * run Bin2Table for each sub-type (WT/pUC/SetYL/SetRC;
	 * 1st, build a hashMap for all bin-reads; record the upper-bound of each chromosome (2L, 2R, 3L, 3R, 4, and X);
	 * 
	 * @param routine
	 * @param doc_name
	 * @param chr_name
	 * @param output
	 * @throws IOException
	 */
	private static void run(String routine, String doc_name, String chr_name, BufferedWriter output) throws IOException {
		// TODO each bin span 1000 base-pairs, here we transfer each bin into each 'step';
		// each window span 10,000 base-pairs (10 bins)
		
		
		Scanner read_in = new Scanner(new File(routine +"/BED2Bin/" + doc_name + "_BED2Bin_" + chr_name +"_1000.txt"));
		
		String first_line = read_in.nextLine();
		System.out.println("Line 106: FirstLine: " + first_line +"\n");
		
		//create a HashMap, put bin-reads (or step-reads) into the HashMap;
		HashMap<Long, Integer> binHash = new HashMap<Long, Integer>();
		long upper_bound = 0;
		
		while(read_in.hasNextLine()){
			
			String curr_line = read_in.nextLine();
			
			//split the line into chr_name, start, end;
			String[] line_split = curr_line.split("\t");
			
			//when there are more than 3 items in that line, update the bin HashMap
			if(line_split.length > 2){
				
				//for each bin, there are 1000bps;
				long bin_index = Integer.parseInt(line_split[1])/1000;
				int count = Integer.parseInt(line_split[3]);
				
				binHash.put(bin_index, count);
				
				//update upper_bound;
				if(bin_index > upper_bound) upper_bound = bin_index;
				
			}//end if line_split.length > 2 condition;
			
		}//end while loop;
		
		
		
		//after Building the HashMap, write_into Bin2Table.txt document;
		/*************
		 * check each step from 0 to the upper_bound;
		 * calculate all 10 steps following current step, sum up the counts;
		 * write-into output-txt document the current step index and total counts;
		 */
		long index = 0;
		
		while(index <= upper_bound){
			
			int count = 0;
			
			//the window is within 10*current index; 
			//which makes the window 10,000bps in this case;
			for(long j=index; j<index+10; j++){
				
				if(binHash.containsKey(j)){
					count += binHash.get(j);
				}
			}
			
			output.write(chr_name +"\t" + index + "\t" + count + "\n");
			
			index++;
		}//end while index <= upper_bound loop;
		
		System.out.println("The output document could be found in:" +routine + "/Bin2Table/" + doc_name +"_Bin2Table_allChrs.txt" );
		
		
		//close read_in scanner;
		read_in.close();
		
		
	}//end run() method;
	
	
}//end of everything in Bin2Table_subtype_1010 class;
