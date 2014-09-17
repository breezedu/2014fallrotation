package rotation2014fall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/*************
 * the next step is to now smooth the data, 
 * since it's a little difficult to visualize (and later analyze) data
 * when you just have the raw reads.  
 * The easiest method to do this is to "bin" the data.  
 * Essentially we create windows of a various width, 
 * and see how many reads fall within that "bin".  
 * The score for that particular bin is then the number of reads within that bin.
 * 
 * 
 * 
 * @author Jeff
 *
 */
public class Bin_Chrom_hash {
	
	public static void main(String[] args) throws IOException{
		
		System.out.println("This is Binning program, transfer a BED formate data into Binning format.");
		
		//1st, creat a hashmap:
		HashMap<Long, Integer> binHash = new HashMap<Long, Integer>();
		
		//keep a rocord of max_bin range;
		long max_bin = 1000;
		System.out.println("The initial max_bin is:" + max_bin);
		
		//2nd, creat a read_in scanner to read data from BED_DM433_2L_t_916.txt
		//alse, create a output-writter to store all output data:
		Scanner bedReader = new Scanner(new File("D:/2014FallRotation/data/BED_DM433_2L_output_t_916.txt"));
		
		File output_file = new File("D:/2014FallRotation/data/Bin_DM433_output.txt");
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));
		
		
		//get the first line, put it into the output.txt document;
		String firstLine = "track type=bedGraph name=\"Chr2L\" description=\"Chr2L\" visibility=\"full\" color=0,0,0";
		output.write(firstLine+"\n");
		
		firstLine = bedReader.nextLine();
		
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
			
			long position = start%1000;
			
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
			System.out.println("chr2L " + start +", " + end + ", " + binHash.get(i) + "; \n");
			if(binHash.containsKey(i))
					output.write("chr2L" + "\t" + start + "\t" + end + "\t" + binHash.get(i) + "\n");
		}
		
		//close the reader and output writer;
		bedReader.close();
		output.close();
		
	}//end of main();

}//end of Bin_Chrom_hash class;
