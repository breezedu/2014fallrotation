package rotation2014fall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**************************
 * Calculate the ratio of reads in the same region between two sub-types 
 * step 1: from fastq to bowtie_output;
 * step 2: from bowtie_output to BED documents;
 * step 3: from BED to Bin documents;
 * step 4: submit the Bin documents to UCSC genomic browser;
 * step 5: calculate the ratio of reads between two sub-types;
 * 
 * EXP:
 * we have DM456_BED2Bin_2L_103.txt and DM457_BED2Bin_2L_103.txt documents;
 * for each documents, there are 4 colums:
 * 		chr2L start_position end_position, reads
 * 		chr2L 19681001, 	19682000, 		7;
 * 		chr2L 19682001, 	19683000, 		8;
 * 		chr2L 19683001, 	19684000, 		3;
 * 
 * like for chr2L in region [19681001-19682000], we could get 2 reads from DM456 and DM457 documents;
 * Calculate log(reads1/reads2)/log(2); this is the ratio we want;
 * calculate ratios for each region;
 * 
 *  Plot the figure of ratio<-->region;
 *  
 * 
 * @author Jeff
 *
 */

public class RatioOfChromatinReads {

	public static void main(String[] args) throws IOException{
		
		System.out.println("This is a script to calculate the Ratio of Chromatin Reads between two sub-types.");
		
		
		
		/************************************************************************************************/
		//1st, create 2 scanners to read_in data from 2 BED2Bin.txt documents;
		//also, create a scanner(system.in) to input the name of two documents: [DM456, DM457,,,, DM463];
		Scanner input = new Scanner(System.in);
		
		System.out.print("Please input the first document's name:[DM456, DM457, --- DM463] \n doc_name1 = ");
		String doc_name1 = input.next();
		
		System.out.print("Please input the second document's name:[DM456, DM457, --- DM463] \n doc_name2 = ");
		String doc_name2 = input.next();
		
		System.out.print("Please input the Chromatin:[2L, 2R, 3L, 3R or X] \n chromatin = ");
		String chromatin = input.next();
		
		
		//scanners for reading in data from file;
		Scanner read_bin1 = new Scanner(new File("D:/2014FallRotation/data/yulong/" +doc_name1 +"_BED2Bin_"+ chromatin +"_103.txt"));
		
		Scanner read_bin2 = new Scanner(new File("D:/2014FallRotation/data/yulong/" +doc_name2 +"_BED2Bin_2L_103.txt"));
		
				
		
		/***************************************************************************************************/
		//2nd create two hash-table for each chromatin region reads;
		//because there are empty gaps, so we make every region+1;
				
		HashMap<Long, Integer> doc1_hash = buildReadsHash(read_bin1, doc_name1);
		HashMap<Long, Integer> doc2_hash = buildReadsHash(read_bin2, doc_name2);
		
		
		//create an output writer to write the ratio_log and the region into a txt document;
		File output_file = new File("D:/2014FallRotation/data/yulong/" + doc_name1 +"OVER" + doc_name2 +"_ratio_" + chromatin +"_103.txt");
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));
		
		
		//compare reads between two hash-tables;
		double base = Math.log(2);
		System.out.println("base: " + base);
		
		long read_region = 1;
		
		
		while(doc1_hash.containsKey(read_region) && doc2_hash.containsKey(read_region)){
			
			double d1 = doc1_hash.get(read_region);
			double d2 = doc2_hash.get(read_region);
			
			double ratio_log = (Math.log(d1) - Math.log(d2) ) / base;
			
			//write the chromatin name, the ratio_log and the read_region into the txt document;
			output.write(chromatin +"\t" + ratio_log + "\t" + read_region + "\n");
			
			//check next read_region;
			read_region++;
		}//end while both hash-tables contain the key;
		
		
		//close all scanners;
		input.close();
		read_bin1.close();
		read_bin2.close();
		
		//close the output writer;
		output.close();
		System.out.println("End :)");
		
	}//end of main();

	private static HashMap<Long, Integer> buildReadsHash(Scanner read_bin, String doc_name) {
		// TODO Build a hash-table for each read region, base on the Bin document;
		
		HashMap<Long, Integer> readHash = new HashMap<Long, Integer>();
		long upper_bound = 0;
		
		String first_line = read_bin.nextLine();
		System.out.println("First line of file " + doc_name +" is: " + first_line);
		
		while(read_bin.hasNextLine()){
			
			String line = read_bin.nextLine();
			
			String[] line_split = line.split("\t");
			
			long start_pos = Long.parseLong( line_split[1] )/1000;
			int read = Integer.parseInt( line_split[3] );
			
			//update the upper_bound, if we get a greater one;
			if(start_pos > upper_bound) upper_bound = start_pos;
			
			//put the start_pos and the read into hash-table;
			readHash.put(start_pos, read);
			
		}//end wile read_bin.hasNextLine() loop;
		
		
		//make every read region+1;
		
		for(long i=1; i<=upper_bound; i++){
			
			if(readHash.containsKey(i)){
				
				int value = readHash.get(i);
				readHash.put(i, value);
			
			} else {
				
				readHash.put(i, 1);
				
			}//end if-else condition;
	
		}//end for i<=upper_bound loop;
		
		
		return readHash;
		
	}//end of buildReadsHash() method;
	
	
}//end of RationOfChromatinReads class
