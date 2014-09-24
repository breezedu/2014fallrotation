package rotation2014fall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**********************
 * now we would like the average signal over early and late regions 
 * (the regions that replicate early in S phase vs. the regions that replicate late.  
 * This data is from our Genome Research modENCODE companion paper: 
 * http://www.ncbi.nlm.nih.gov/pubmed/24985913).  
 * I have 2 bed files that give the regions corresponding to the early and late regions, 
 * they are in this directory (you can load them directly into the UCSC genome browser to view).  
 * http://alchemy.duhs.duke.edu/~jab112/dros_wiggle_tracks/repliseq/Kc-EarlyDomains.bed 
 * http://alchemy.duhs.duke.edu/~jab112/dros_wiggle_tracks/repliseq/Kc-LateDomains.bed
 * 
 * So for each of these regions, obtain the average ChIP signal over each region 
 * (so you'll get 245 early signal regions, corresponding to the average signal on 2L 
 * from 59001 to 300000 as your first value, 2L from 366001 to 630000 as your second value, 
 * and 166 late signal regions).  
 * Then you can create side-by-side boxplots to compare the signal in each of these regions.
 * 
 * @author Jeff
 *
 */

public class Chromatin_signal_over_earlyandlate_regions {

	public static void main(String[] args) throws IOException{
		
		System.out.println("This is Chromatin_single_over_earlyandlate_regions class.");
		

		/********************************************************************************************/
		//1st, in this stage, we just check Chr2L one chromatin; 
		//read_in datas from Kc-EarlyDomains.bed and Kc-LateDomains.bed files;
		//every line in these documents is a ChrRegion object, put them into two ArrayList<> lists;
		ArrayList<ChrRegion> Chr_early = new ArrayList<ChrRegion>();
		ArrayList<ChrRegion> Chr_late = new ArrayList<ChrRegion>();
		
		Scanner read_Chr_early = new Scanner(new File("D:/2014FallRotation/data/Kc-EarlyDomains.bed"));
		Scanner read_Chr_late = new Scanner(new File("D:/2014FallRotation/data/Kc-LateDomains.bed"));
		
		String firstLine = read_Chr_early.nextLine();
		System.out.println("First line of Chr2L_early: " + firstLine);
		
		//read_in data from Kc-EarlyDomains.bed and build Chr2L_early arrayList;
		while(read_Chr_early.hasNextLine()){
			
			String name = read_Chr_early.next();
			long start = read_Chr_early.nextLong();
			long end = read_Chr_early.nextLong();
			
			//build a new ChrRegion object;
			ChrRegion currChrRegion = new ChrRegion();
			currChrRegion.name = name;
			currChrRegion.start = start;
			currChrRegion.end = end;
			
			Chr_early.add(currChrRegion);
			
		}//end while(read_Chr2L_early.hasNextLine()) loop;
		
		int early_size = Chr_early.size();
		System.out.println("There are " + early_size + " chromation regions in early phase.\n");
		
		
		//read_in data from Kc-LateDomains.bed and build Chr2L_early arrayList;
		firstLine = read_Chr_late.nextLine();
		System.out.println("First line of Chr2L_late: " + firstLine);
		
		while(read_Chr_late.hasNextLine()){
			String line = read_Chr_late.nextLine();
			String[] splitLine = line.split("\t");
			
			String name = splitLine[0];
			long start = Long.parseLong(splitLine[1]);
			long end = Long.parseLong(splitLine[2]);
			
			//build a new ChrRegion object;
			ChrRegion currChrRegion = new ChrRegion();
			currChrRegion.name = name;
			currChrRegion.start = start;
			currChrRegion.end = end;
				
			Chr_late.add(currChrRegion);
					
		}//end while(read_Chr2L_early.hasNextLine()) loop;
		
		//at this point, all Chr2L_early and Chr2L_late chromatin informations have been added to arrayLists;
		int late_size = Chr_late.size();
		System.out.println("There are " + late_size + " chromation regions in late phase.\n");
		
		

		/********************************************************************************************/
		//2nd, read_in chromatin2L reads from BED_DM433_2L_output_t_922.txt document;
		//compare the read_in read with Chr2L_early and Chr2L_late data in two ArrayLists; 
		//if we get a match (like Chr2L_early.at(3), update Chr2L_early.at(3).read++;
		Scanner Chr_reads = new Scanner(new File("D:/2014FallRotation/data/BED_DM433_allChrs_output_924.txt"));
		firstLine = Chr_reads.nextLine();
		System.out.println("The first line of BED_DM433_2L_output_t_922.txt is: " + firstLine);
		
		
		while(Chr_reads.hasNextLine()){
			
			String currLine = Chr_reads.nextLine();
			String[] splitLine = currLine.split("\t");
			String name = splitLine[0];
			
			long start = Long.parseLong(splitLine[1]);
			
			for(int i=0; i<early_size; i++){
				
				if(name.equals(Chr_early.get(i).name) && start >= Chr_early.get(i).start && start <= Chr_early.get(i).end){
					
					Chr_early.get(i).read++;
					i = early_size;
				}
			}//end for i<early_size loop;
			
			
			for(int j=0; j<late_size; j++){
				
				if(name.equals(Chr_late.get(j).name) && start >= Chr_late.get(j).start && start <= Chr_late.get(j).end){
					
					Chr_late.get(j).read++;
					j = late_size;
				}
			}
			
		}//end while(Chr2L_reads.hasNextLine()) loop;
		
		

		/********************************************************************************************/
		//3rd, printout the updated Chr_early and Chr_late arrayLists:
		System.out.println("Printout the chrAll early regions:");
		for(int i=0; i<early_size; i++){
			
			ChrRegion currCR = Chr_early.get(i);
			//if(currCR.name.equals("chr2L"))
			System.out.println(currCR.name + ", " + currCR.start +", " + currCR.end +", " + currCR.read +". ");
		}
		
		System.out.println();
		
		System.out.println("Printout the chrALL late regions:");
		for(int i=0; i<late_size; i++){
			
			ChrRegion currCR = Chr_late.get(i);
			//if(currCR.name.equals("chr2L"))
			System.out.println(currCR.name + ", " + currCR.start +", " + currCR.end +", " + currCR.read +". ");
		}
		
		
		/********************************************************************************************/
		//4th, write the Chr_early and Chr_late arrayLists data into two txt documents;
		
		//early phase
		File output_Chr_early = new File("D:/2014FallRotation/data/allChrs_early_output_924.txt");
		BufferedWriter output_early = new BufferedWriter(new FileWriter(output_Chr_early));
		
		//chr_name  start  end  read
		output_early.write("chr_name" + "\t" + "start_pos" + "\t" + "end_pos" +"\t" + "read_count" +"\n");
		
		for(int i=0; i<early_size; i++){
			
			ChrRegion currChr = Chr_early.get(i);
			output_early.write(currChr.name + "\t" + currChr.start + "\t" + currChr.end +"\t" + currChr.read +"\n");
		
		}
		
		//late_phase
		File output_Chr_late = new File("D:/2014FallRotation/data/allChrs_late_output_924.txt");
		BufferedWriter output_late = new BufferedWriter(new FileWriter(output_Chr_late));
		
		//chr_name  start  end  read
		output_late.write("chr_name" + "\t" + "start_pos" + "\t" + "end_pos" +"\t" + "read_count" +"\n");
		
		for(int i=0; i<late_size; i++){
			
			ChrRegion currChr = Chr_late.get(i);
			output_late.write(currChr.name + "\t" + currChr.start + "\t" + currChr.end +"\t" + currChr.read +"\n");
		
		}		
		
		
		

		/********************************************************************************************/
		//5th, close() Scanners
		read_Chr_early.close();
		read_Chr_late.close();
		Chr_reads.close();
		
		//close() output_writters
		output_early.close();
		output_late.close();
		
	}//end of main()
	
}//end of everything in Chromatin_single_over_earlyandlate_regions class

