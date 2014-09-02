package rotation2014fall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/***********************
 * the csv file: eaton_acs_filter_rDNA_match_oridb_acs_238_sites.csv 
 * This is a CSV file listing 238 replication initiation sites in yeast 
 * by chromosome, position, and strand.  
 * The score is the "motif score", which in this case is how good the yeast genomic sequence 
 * starting at the indicated position and on the forward/reverse strand matches the ACS motif 
 * (relative to background sequence)
 * 
 * 
 * 
 * @author Jeff Du
 *
 */

public class Relative_Distance_Orc {

	
	public static void main(String[] args) throws IOException{
		
		//1st, read the csv data from eaton_acs_filter_rDNA_match_oridb_acs_238_sites.csv file:
		
		System.out.println("Read eaton_acs_filter_rDNA_match_oridb_acs_238_sites.csv data.");
		
		Scanner CSV_reader = new Scanner(new File("orc_chip_seq_dm265_data.csv"));
		
		String title = CSV_reader.nextLine();
		System.out.println("Title: " + title);
		
		
		//2nd, create a file to write all outputs:
		File output_file = new File("orc_output.csv");
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));
		
		//write the titles for each column;
		output.write("chr,pos,strand,ori_one,ori_two,ori_three,ori_four,ori_five,ori_six" +"\n");
		
		//766	31002	42044	70433	124528	159952
		int[] ori_pos = {-766, 31002, 42044, -70433, -124528, 159952};
		
		while(CSV_reader.hasNextLine()){
			
			String line = CSV_reader.nextLine();
			String[] split = line.split(",");
			
			//split every element in the line between ',' then print out the array; 
			//printArray(split);
			
			//from each absolute distance and strand#, calculate relative distance;
			int chr_number = Integer.parseInt(split[0]);
			int absolute_position = Integer.parseInt(split[1]);
			
			int relative_distance = 0;
			
			//for Chromatin One, there are 6 sites:
			output.write(chr_number +",");
			output.write(absolute_position +",");
			output.write(split[2] +",");
			
			for(int i=0; i<6; i++){
				
				// if(split[2].equals("-")) relative_distance = (absolute_position - ori_pos[i])*(-1);
				 relative_distance = ori_pos[i] - absolute_position;
				
				output.write(relative_distance +",");
			}


			output.write("\n");
			
		}//end while(CSV_reader has next line loop;
		
			
		//close both output and reader;
		output.close();
		CSV_reader.close();
		
		System.out.println("Done!");
	}//end of main();

	/*********
	 * Print out every element in an array;
	 * @param str
	 *
	private static void printArray(String[] str) {
		// TODO Printout every string in an array;
		
		int len = str.length;
		for(int i=0; i<len; i++){
			System.out.print("   " + str[i]);
		}
		
		System.out.println();
	}//end of printArray() method;
	*/
	
}

