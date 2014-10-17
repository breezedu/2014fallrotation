package rotation2014fall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**********************
 * Create a Bin2RPKM class
 * Since some sub-type has more total reads than others
 * it is not easy to compare two sub-types directly trough their Bin reads;
 * So we are trying to use RPKM (reads per kb of sequence, per million mapped reads: RPKM)
 * 
 * RPKM = Reads / ((Window/width) * (total_read/1e+6));
 * 
 * The write the RPKM value into (take DM456, Chromosome 2L, Window=10000 for example) DM456_Bin2RPKM_2L_10000.txt;
 *
 * @author Jeff
 *
 */

public class Bin2RPKM_chromosome {
	
	
	/*************
	 * Main()
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		
		System.out.println("This is Bin2Table script.");
		
		
		//1st, read-in Bin_data from D:\2014FallRotation\data\yulong\1010;
		String routine = "D:/2014FallRotation/data/yulong/1010/";		
		
		//readin the title of Bin document:
		Scanner input = new Scanner(System.in);
		
		System.out.print("Please input the document to deal with: [DM456, DM457....DM463] \n document = ");		
		String doc_name = input.next();
		
		System.out.print("Please input the window width: \n window = ");
		int window = input.nextInt();
		
		/*
		System.out.print("Please input the chromosome: [2L, 2R, 3L, 3R, 4 or X] \n chr_name = ");
		String chr_name = input.next();
		*/
		
		//array of chromosomes:
		String[] chr_name = {"2L", "2R", "3L", "3R", "4", "X"};
				
		
		//3rd, for each chromosome, write_in the big table;
		
		for(int i=0; i< chr_name.length; i++){
			
			run_Bin2RPKM(routine, doc_name, chr_name[i], window);		
		
		}//end for i<chr_name.length loop;
		
		
		//4th, close output writter;
		
		input.close();
		
	}//end of main();

	
	
	/*****************
	 * run Bin2Table for each sub-type (WT/pUC/SetYL/SetRC;
	 * 1st, build a hashMap for all bin-reads; record the upper-bound of each chromosome (2L, 2R, 3L, 3R, 4, and X);
	 * 
	 * @param routine
	 * @param doc_name
	 * @param chr_name
	 * @param window 
	 * @param output
	 * @throws IOException
	 */
	private static void run_Bin2RPKM(String routine, String doc_name, String chr_name, int window) throws IOException {
		// TODO each bin span 1000 base-pairs, here we transfer each bin into each 'step';
		
		//1st, create an output_writer to write Bin document;
		File output_file = new File(routine + "/Bin2RPKM/" + doc_name +"_Bin2RPKM_" + chr_name + "_" + window +"R.txt");
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));
								
		//For R plot data, don't write te first line;
		//output.write("chr" + "\t" + "start_pos" + "\t" + "counts" + "\n");
		
		
		
		// each window span window/width base-pairs (10 bins)
				
		int width = 1000;
		
		Scanner read_in = new Scanner(new File(routine +"/BED2Bin/" + doc_name + "_BED2Bin_" + chr_name +"_1000.txt"));
		
		String first_line = read_in.nextLine();
		System.out.println("Line 106: FirstLine: " + first_line +"\n");
		
		//create a HashMap, put bin-reads (or step-reads) into the HashMap;
		HashMap<Long, Integer> binHash = new HashMap<Long, Integer>();
		long upper_bound = 0;
		long total_read = 0;
		
		while(read_in.hasNextLine()){
			
			String curr_line = read_in.nextLine();
			
			//split the line into chr_name, start, end;
			String[] line_split = curr_line.split("\t");
			
			//when there are more than 3 items in that line, update the bin HashMap
			if(line_split.length > 2){
				
				//for each bin, there are 1000bps;
				long bin_index = Integer.parseInt(line_split[1])/1000;
				int count = Integer.parseInt(line_split[3]);
				
				binHash.put(bin_index, count);	//put count into hash_table;
				
				total_read += count;			//update total_read;
				
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
			for(long j=index; j<index+window/width; j++){
				
				if(binHash.containsKey(j)){
					count += binHash.get(j);
				}
				
			}//end for j<index+10 loop;
			
			double rpkm = count/((window/width) * (total_read/1e+6)); 
			
			output.write(chr_name +"\t" + index + "\t" + rpkm + "\n");
			
			index++;
		}//end while index <= upper_bound loop;
		
		System.out.println("The output document could be found in:" +routine + "/Bin2RPKM/" + doc_name +"_Bin2RPKM_" + chr_name +"_" + window +".txt" );
		
		
		//close read_in scanner;
		read_in.close();
		output.close();
		
		
	}//end run() method;
	
	
}//end of everything in Bin2Table_subtype_1010 class;
