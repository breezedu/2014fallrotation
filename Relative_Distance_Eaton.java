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
 * orc_chip_seq_dm265_data.csv
 * This is the raw sequencing data after alignment by the program Bowtie.  
 * Each line is a separate sequencing read alignment (giving the chromosome, 
 * position, and whether the read aligns to the forward or reverse strand).  
 * For now, ignore the strand information for each read.
 * 
 * 
 * We would like to make an aggregate plot of the ORC ChIP-seq signal 
 * around each of the 238 ORC binding sites.  
 * So each ORC binding site should be centered on its ACS, 
 * and then you should find the relative distance for every sequencing read from this ACS position. 
 * 
 * 
 * @author Jeff Du
 *
 */

public class Relative_Distance_Eaton {

	
	public static void main(String[] args) throws IOException{
		
		//1st, read the csv data from eaton_acs_filter_rDNA_match_oridb_acs_238_sites.csv file:
		
		System.out.println("Read eaton_acs_filter_rDNA_match_oridb_acs_238_sites.csv data.");
		
		Scanner CSV_reader = new Scanner(new File("eaton_acs_filter_rDNA_match_oridb_acs_238_sites.csv "));
		
		//create an arrayList to store all Chromatin objects;
		ArrayList<Chromatin> ChrArrayList = new ArrayList<Chromatin>();
		
		
		String title = CSV_reader.nextLine();
		System.out.println("Title: " + title);
		
		
		//2nd, create a current_Chromatin object, to compare each chromatin to be read;
		Chromatin currChr = new Chromatin();
		
		//3rd, readin the second line, which include information about first chromatin and bind site;
		//then, split the string according to ',';
		
		String Firstline = CSV_reader.nextLine();
		String[] splitLine = Firstline.split(",");
		
		//4th, assign name and absDistance to current Chromatin;
		currChr.name = splitLine[1];
		int absDis = Integer.parseInt(splitLine[2]);
		if(splitLine[3].equals("-")) absDis = -absDis;
		currChr.Position.add(absDis);
		
		//5th, add the first Chromatin, the currChr, object to the ChrArrayList.
		ChrArrayList.add(currChr);
		
		
		//6th, read each Chromatin binding site line by line;
		while(CSV_reader.hasNextLine()){
			
			int lastIndex = ChrArrayList.size()-1;
			
			String line = CSV_reader.nextLine();
			
			Chromatin nextChr = new Chromatin();
			String[] split = line.split(",");
			nextChr.name = split[1];
			
			//if current Chromatin and next Chromatin share the same name, 
			//the add next absolute position to the last Chr in the ChrArrayList's position. 
			
			if(nextChr.name.equals(currChr.name)){
				
				int nextAbsDis = Integer.parseInt(split[2]);
				if(split[3].equals("-")) nextAbsDis = -nextAbsDis;
				ChrArrayList.get(lastIndex).Position.add(nextAbsDis);
			
			} else { 
				
				int nextAbsDis = Integer.parseInt(split[2]);
				if(split[3].equals("-")) nextAbsDis = -nextAbsDis;
				
				nextChr.Position.add(nextAbsDis);
				
				ChrArrayList.add(nextChr);
				currChr = nextChr;
			
			}//end if-else conditions;
						
			
		}//end while loop;
		
		int index = ChrArrayList.size();
		System.out.println("There are " + index + " chromatins.");
		
		for(int i=0; i<index; i++){
			System.out.println(" " + ChrArrayList.get(i).name +", " +ChrArrayList.get(i).Position.size() +". ");
		}
		
		/**************
		 * At this point, each chromatin and it's absolute positions has been added to ChrArrayList list.
		 * next, we will compare each of the chromatin's absolute positions to the reads in file: 
		 * orc_chip_seq_dm265_data.csv
		 * 
		 * Then, we will calculate all relative distances for each orc_chip read:
		 * 
		 **************/
		
		
		
		//2nd, create a file to write all outputs:
		File output_file = new File("output.csv");
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));
		
		//write the titles for each column;
		output.write("name,chr,pos,strand,rela_pos,score" +"\n");
		
		while(CSV_reader.hasNextLine()){
			
			String line = CSV_reader.nextLine();
			String[] split = line.split(",");
			
			//split every element in the line between ',' then print out the array; 
			//printArray(split);
			
			//from absolute distance and strand#, calculate relative distance;
			int chr_number = Integer.parseInt(split[1]);
			int absolute_position = Integer.parseInt(split[2]);
			
			int relative_distance = 0;
			if(split[3].equals("-")) relative_distance = (absolute_position - chr_number)*(-1);
			else relative_distance = absolute_position - chr_number;
			
			double score = Double.parseDouble(split[4]);
			
			
			System.out.println("  " + split[0] + "  " + chr_number +"  " + absolute_position +"  " +
								split[3] + "  " + relative_distance +"  " + score +". ");
			
			
			//write name, chri, relative distance to output.csv;
			output.write(split[0] + ", ");
			output.write(chr_number +", ");
			output.write(absolute_position +", ");
			output.write(split[3] +", ");
			output.write(relative_distance +", " + score + "\n");
			
		}//end while(CSV_reader has next line loop;
		
			
		//close both output and reader;
		output.close();
		CSV_reader.close();
		
		
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


/********
 * Create Chromatin object for Relative Distance Eaton class;
 * 
 * @author Jeff
 *
 */
class Chromatin{
	
	String name;
	ArrayList<Integer> Position = new ArrayList<Integer>();
	
}//end of Chromatin class;

