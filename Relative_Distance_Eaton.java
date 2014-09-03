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
		System.out.println("CSV titles: " + title);
		
		
		//2nd, create a current_Chromatin object, to compare each chromatin to be read;
		Chromatin currChr = new Chromatin();
		
		//3rd, readin the second line, which include information about first chromatin and bind site;
		//then, split the string according to ',';
		
		String Firstline = CSV_reader.nextLine();
		String[] splitLine = Firstline.split(",");
		
		//4th, assign name and absDistance to current Chromatin;
		currChr.name = splitLine[1];
		long absDis = Integer.parseInt(splitLine[2]);
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
				
				Long nextAbsDis = Long.parseLong(split[2]);
				if(split[3].equals("-")) nextAbsDis = -nextAbsDis;
				ChrArrayList.get(lastIndex).Position.add(nextAbsDis);
			
			} else { 
				
				Long nextAbsDis = Long.parseLong(split[2]);
				if(split[3].equals("-")) nextAbsDis = -nextAbsDis;
				
				nextChr.Position.add(nextAbsDis);
				
				ChrArrayList.add(nextChr);
				currChr = nextChr;
			
			}//end if-else conditions;
						
			
		}//end while loop;
		
		//CSV_reader.close();
		
		int index = ChrArrayList.size();
		System.out.println("There are " + index + " chromatins.");
		
		for(int i=0; i<index; i++){
			int postions = ChrArrayList.get(i).Position.size();
			System.out.print(" " + ChrArrayList.get(i).name +", " + postions +": ");
			
			for(int j=0; j<postions; j++){
				System.out.print(" " + ChrArrayList.get(i).Position.get(j)+" ");
			}
			
			System.out.println();
		}
		
		/**************
		 * At this point, each chromatin and it's absolute position has been added to ChrArrayList list.
		 * next, we will compare each of the chromatin's absolute positions to the reads in file: 
		 * orc_chip_seq_dm265_data.csv
		 * 
		 * Then, we will calculate all relative distances for each orc_chip read:
		 * 
		 **************/
		
		
		
		//7th, create a file to write all outputs:
		File output_file = new File("orc_output_small.csv");
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));
		
		//write the titles for each column;
		output.write("name,chr,pos,strand,rela_positions" +"\n");
		
		
		//8th, create another reader to read_in data from orc_chip_seq_dm265_data.csv;
		Scanner ORC_reader = new Scanner (new File("orc_chip_seq_dm265_data.csv"));
		
		//read_in the first line, titles of each column;
		String orc_title = ORC_reader.nextLine();
		System.out.println("ORC titles: " + orc_title);
		
		
		while(ORC_reader.hasNextLine()){
			
			String line = ORC_reader.nextLine();
			String[] split = line.split(",");
			
			//split every element in the line between ',' then print out the array; 
			//printArray(split);
			
			//from absolute distance and strand#, calculate relative distance;
			int chr_number = Integer.parseInt(split[0]);
			
			/************
			 * Just found that there are Chromatin_17 reads in the orc_chip_seq_dm256_data.csv file;
			 * So, here I choose to close the output writer, then nothing will be written into
			 * the output.csv file;
			 */
		//	if(chr_number > 16 ) output.close();
			
			//get the chromation object from ChrArrayList, according to chr_number; 
			index = chr_number -1;
			
			ArrayList<Long> curr_relat_positions = new ArrayList<Long>(ChrArrayList.get(index).Position);
			
			//get absolute position of each read:
			long read_position = Integer.parseInt(split[1]);
			
			
			//System.out.print( chr_number +"  " + read_position +"  " + split[2] + " ");
	
			
			//write Chromatin number and absolute position into orc_output.csv data file;
			/*****
			output.write(chr_number +",");
			output.write(read_position +",");
			output.write(split[2] +",");
			*/
			
			for(int i=0; i<curr_relat_positions.size(); i++){
				
				long relat_position = (read_position - curr_relat_positions.get(i));
				
			//	System.out.print(", " + relat_position);
				if(relat_position>=-1000 && relat_position<=1000)
					output.write(relat_position +" ");
				
			}//end for i<curr_relat_positions.size()
			
		//	output.write("\n");
			
		//	System.out.println();
			
			//write name, chri, relative distance to output.csv;
						
			
		}//end while(CSV_reader has next line loop;
		
			
		//close output and readers;
		output.close();
		CSV_reader.close();
		ORC_reader.close();
		
		
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
 * Each Cromatin object will hole a name String and one ArrayList;
 * The arrayList will store all absolute distance readed from eaton_acs_filter_rDNA_match_oridb_acs_238_sites.csv
 * 
 * In later work (program section, we will use the readed positions from orc_chip_seq_dm265_data.csv minus
 * each of the absolute distance in the arrayList;
 * 
 * ** Before doing the relative_distance calculation, check if the Chromatin.name matches the chromatin readed.
 * 
 * @author Jeff
 *
 */
class Chromatin{
	
	String name;
	ArrayList<Long> Position = new ArrayList<Long>();
	
}//end of Chromatin class;

