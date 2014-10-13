package rotation2014fall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/*************
 * From fastq to UCSC genomic browser then calculating the ratio between two sub-types:
 * step 1: from fastq to bowtie_output;
 * step 2: from bowtie_output to BED documents; 				[Bowtie2BED_***.java]
 * step 3: from BED to Bin documents;           				[BED2Bin_***.java]
 * step 4: submit the Bin**.txt documents to UCSC genomic browser;
 * step 5: calculate the ratio of reads between two sub-types;	[RatioOfChromatinReads.java]
 * * * 
 * since it's a little difficult to visualize (and later analyze) data
 * when you just have the raw reads.  
 * The easiest method to do this is to "bin" the data.  
 * Essentially we create windows of a various width, 
 * and see how many reads fall within that "bin".  
 * The score for that particular bin is then the number of reads within that bin.
 * 
 * exa:
 * read-in data from D;/2014Fallrotation/data/yulong/DM456_bowtie2BED_2L_1003.txt
 * 
 * @author Jeff
 *
 */
public class BED2Bin_Chromosome {
	
	
	//create a BED2Bin object:
	static BED2Bin_Chromosome bed2bin = new BED2Bin_Chromosome();
	
	
	/**********
	 * Main();
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		
		System.out.println("This is BED to Bin class.");
		
		
		//call the bed2bin.run() method; to create BED2Bin output txt document;
		bed2bin.run();	
		
		
	}//end main();
	
	
	
	/***********************************************************************
	 * 
	 * @param window: 		this is the width of Chromatin region;
	 * @param date:			the date also means the sub-folder; 
	 * @param document: 	the title of BED document;
	 * @param chromatin:	Chromatin name; 2L, 2R, 3L, 3R or X;
	 * @param routine: 		the routine where the BED documents are stored;
	 * @throws IOException: IOexception throws; 
	 */
	public void run() throws IOException{
		
		System.out.println("This is Binning program, transfer a BED formate data into Binning format.");
		
		
		/*********************************************************************************************/
		//create a read_input scanner to read parameters for this program;
		//read-in directory routine, chromatin name, document name and operation date;
		Scanner readInput = new Scanner(System.in);
				
		System.out.print("Please input the routine of BED.txt document: [D:/2014FallRotation/data/yulong/] \n directory = ");
		String routine = readInput.next();
				
		System.out.print("Please input the chromatin you want to bin: [2L, 2R, 3L, 3R or X]. \n chromatin = ");
		String chromatin = readInput.next();
				
				
		System.out.print("Please input the document name to bin: [DM456-----DM463] \n document = ");
		String document = readInput.next();
		
				
		System.out.print("Please input the date of this operation: [1003] \n date = ");
		String date = readInput.next();
		
		
		System.out.print("Please input the window[STEP] of base_pair range: [1000, 5000 or 10000 etc] \n window = ");
		int window = readInput.nextInt();
		
		
		//1st, create a hashMap, to store all reads in one reads-window exp:[1-10000], [1001-20000]------
		HashMap<Long, Integer> binHash = new HashMap<Long, Integer>();
		
		
		//2nd, create a scanner to read_in data from BED_DM456_2L_t_916.txt 
		Scanner bedReader = new Scanner(new File(routine + date +"/"  + document + "_bowtie2BED_" + chromatin +".txt"));
		
		
		//3rd, create an output_writer to write Bin document;
		File output_file = new File(routine + date +"/" + document +"_BED2Bin_" + chromatin +"_" + window +".txt");
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));
		
		
		//get the first line, put it into the output.txt document;
		String firstLine = "track type=bedGraph name=" +document +"_Chr" + chromatin +"\t" + "description=" +"Chr_" + chromatin +"\t" +"visibility=\"full\" color=0,0,0";
		output.write(firstLine+"\n");
		
		firstLine = bedReader.nextLine();
		
		
		//keep a record of max_bin range;
		long max_bin = 1000;
		System.out.println("The initial max_bin is:" + max_bin);
				
		
		/*********************************************************************************************/
		//4th, read_in data line by line: split the line with "\t", then we could get 3 strings;
		//the 1st split[] is chr2L, follow with start and end reads;
		//here we just use the start read as the bin parameter; 
		while(bedReader.hasNextLine()){
			
			String line = bedReader.nextLine();
			String[] split = line.split("\t");
			
			
			//just use the start read as the bin_parameter;
			long start = Long.parseLong(split[1]);
			
			//String chr_name = split[0];
			//long end = Long.parseLong(split[2]);			
			//System.out.println("name:" + chr_name +", start:" +start +", end:" + end +".");
			
			//long position = start/1000;
			long position = start/window;
			
			//update max_bin;
			if(position > max_bin) max_bin = position;
			
			//check the hashmap;
			if(!binHash.containsKey(position)){
				
				binHash.put(position, 1);
			
			} else {
				
				int new_value = binHash.get(position) + 1;
				binHash.put(position, new_value);
			}
			
		}//end while loop;
		
		
		/******************
		 * traverse from (1,1000) till (max_bin*1000, max_bin*1000-1);
		 * check the binHash map, get total bin reads for each range:
		 * 		
		 */
		for(long i = 1; i<=max_bin; i++){
			
			long end = i*window;
			long start = end-window+1;

			if(binHash.containsKey(i)){
				
					output.write("chr"+ chromatin + "\t" + start + "\t" + end + "\t" + binHash.get(i) + "\n");
					System.out.println("chr" + chromatin +" " + start +", " + end + ", " + binHash.get(i) + ";");
		
			} //end if condition;
		}//end for i<=max_bin loop;
		
		
		//close the reader and output writer;

		//close the scanner;
		readInput.close();
		bedReader.close();
		output.close();
		
		
	}//end of run();

	
}//end of BED2Bin_Chromatin_1006 class;
