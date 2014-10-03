package rotation2014fall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/*************
 * the 1st step is to align fastq files with bowtie ==> bowtie_output.txt;
 * the 2nd step is to transfer bowtie_output into BED format ducumnet ==> bowtie2BED.txt;
 * the 3rd step is to smooth the data, 'bin' the data;
 * 
 * since it's a little difficult to visualize (and later analyze) data
 * when you just have the raw reads.  
 * The easiest method to do this is to "bin" the data.  
 * Essentially we create windows of a various width, 
 * and see how many reads fall within that "bin".  
 * The score for that particular bin is then the number of reads within that bin.
 * 
 * exa:
 * read-in data from D;/2014Fallrotation/data/yulong/DM456_bowtie2BED_2L_101.txt
 * 
 * @author Jeff
 *
 */
public class BED2Bin_Chromatin_103 {
	
	public static void main(String[] args) throws IOException{
		
		System.out.println("This is Binning program, transfer a BED formate data into Binning format.");
		
		//1st, creat a hashmap:
		HashMap<Long, Integer> binHash = new HashMap<Long, Integer>();
		
		//keep a rocord of max_bin range;
		long max_bin = 1000;
		System.out.println("The initial max_bin is:" + max_bin);
		
		
		/*********************************************************************************************/
		//2nd, create a read_in scanner to read data from BED_DM433_2L_t_916.txt
		//create 
		//also, create a output-writer to store all output data:
		
		//read-in chromatin name;
		System.out.print("Please input the chromatin you want to bin: [2L, 2R, 3L, 3R or X]. \n chrmitin = ");
		Scanner readInput = new Scanner(System.in);
		String chromatin = readInput.next();
		
		
		System.out.print("Please input the document name to bin: [DM456-----DM463] \n document = ");
		String document = readInput.next();
		
		Scanner bedReader = new Scanner(new File("D:/2014FallRotation/data/yulong/" +document + "_bowtie2BED_" + chromatin +"_101.txt"));
		
		
		
		File output_file = new File("D:/2014FallRotation/data/yulong/" + document +"_BED2Bin_" + chromatin +"_103.txt");
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));
		
		
		//get the first line, put it into the output.txt document;
		String firstLine = "track type=bedGraph name=" +document +"\"Chr2L\" description=\"Chr2L\" visibility=\"full\" color=0,0,0";
		output.write(firstLine+"\n");
		
		firstLine = bedReader.nextLine();

		
		
		/*********************************************************************************************/
		//3rd, read_in data line by line: split the line with "\t", then we could get 3 strings;
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
			
			long position = start/1000;
			
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
			
			long end = i*1000;
			long start = end-999;

			if(binHash.containsKey(i)){
					output.write("chr"+ chromatin + "\t" + start + "\t" + end + "\t" + binHash.get(i) + "\n");
					System.out.println("chr" + chromatin +" " + start +", " + end + ", " + binHash.get(i) + ";");
		
			} //end if condition;
		}//end for i<=max_bin loop;
		
		//close the reader and output writer;
		bedReader.close();
		output.close();
		readInput.close();
	}//end of main();

	
}//end of Bin_Chrom_hash class;
