package rotation2014fall;

import java.io.File;
import java.io.FileNotFoundException;
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

	public static void main(String[] args) throws FileNotFoundException{
		
		System.out.println("This is Chromatin_single_over_earlyandlate_regions class.");
		
		//1st, in this stage, we just check Chr2L one chromatin; 
		//read_in datas from Kc-EarlyDomains.bed and Kc-LateDomains.bed files;
		//every line in these documents is a ChrRegion object, put them into two ArrayList<> lists;
		ArrayList<ChrRegion> Chr2L_early = new ArrayList<ChrRegion>();
		ArrayList<ChrRegion> Chr2L_late = new ArrayList<ChrRegion>();
		
		Scanner read_Chr2L_early = new Scanner(new File("D:/2014FallRotation/data/Kc-EarlyDomains.bed"));
		Scanner read_Chr2L_late = new Scanner(new File("D:/2014FallRotation/data/Kc-LateDomains.bed"));
		
		String firstLine = read_Chr2L_early.nextLine();
		System.out.println("First line of Chr2L_early: " + firstLine);
		
		//read_in data from Kc-EarlyDomains.bed and build Chr2L_early arrayList;
		while(read_Chr2L_early.hasNextLine()){
			
			String name = read_Chr2L_early.next();
			long start = read_Chr2L_early.nextLong();
			long end = read_Chr2L_early.nextLong();
			
			//build a new ChrRegion object;
			ChrRegion currChrRegion = new ChrRegion();
			currChrRegion.name = name;
			currChrRegion.start = start;
			currChrRegion.end = end;
			
			Chr2L_early.add(currChrRegion);
			
		}//end while(read_Chr2L_early.hasNextLine()) loop;
		
		int early_size = Chr2L_early.size();
		System.out.println("There are " + early_size + " chromation regions in early phase.\n");
		
		
		//read_in data from Kc-LateDomains.bed and build Chr2L_early arrayList;
		firstLine = read_Chr2L_late.nextLine();
		System.out.println("First line of Chr2L_late: " + firstLine);
		
		while(read_Chr2L_late.hasNextLine()){
			String line = read_Chr2L_late.nextLine();
			String[] splitLine = line.split("\t");
			
			String name = splitLine[0];
			long start = Long.parseLong(splitLine[1]);
			long end = Long.parseLong(splitLine[2]);
			
			//build a new ChrRegion object;
			ChrRegion currChrRegion = new ChrRegion();
			currChrRegion.name = name;
			currChrRegion.start = start;
			currChrRegion.end = end;
				
			Chr2L_late.add(currChrRegion);
					
		}//end while(read_Chr2L_early.hasNextLine()) loop;
		
		//at this point, all Chr2L_early and Chr2L_late chromatin informations have been added to arrayLists;
		int late_size = Chr2L_late.size();
		System.out.println("There are " + late_size + " chromation regions in late phase.\n");
		
		
		
		//2nd, read_in chromatin2L reads from BED_DM433_2L_output_t_922.txt document;
		//compare the read_in read with Chr2L_early and Chr2L_late data in two ArrayLists; 
		//if we get a match (like Chr2L_early.at(3), update Chr2L_early.at(3).read++;
		Scanner Chr2L_reads = new Scanner(new File("D:/2014FallRotation/data/BED_DM433_2L_output_t_922.txt"));
		firstLine = Chr2L_reads.nextLine();
		System.out.println("The first line of BED_DM433_2L_output_t_922.txt is: " + firstLine);
		
		
		while(Chr2L_reads.hasNextLine()){
			
			String currLine = Chr2L_reads.nextLine();
			String[] splitLine = currLine.split("\t");
			String name = splitLine[0];
			
			long start = Long.parseLong(splitLine[1]);
			
			for(int i=0; i<early_size; i++){
				
				if(name.equals(Chr2L_early.get(i).name) && start >= Chr2L_early.get(i).start && start <= Chr2L_early.get(i).end){
					
					Chr2L_early.get(i).read++;
					i = early_size;
				}
			}//end for i<early_size loop;
			
			
			for(int j=0; j<late_size; j++){
				
				if(name.equals(Chr2L_late.get(j).name) && start >= Chr2L_late.get(j).start && start <= Chr2L_late.get(j).end){
					
					Chr2L_late.get(j).read++;
					j = late_size;
				}
			}
			
		}//end while(Chr2L_reads.hasNextLine()) loop;
		
		System.out.println("Printout the chr2L early regions:");
		for(int i=0; i<early_size; i++){
			
			ChrRegion currCR = Chr2L_early.get(i);
			if(currCR.name.equals("chr2L"))
				System.out.println(currCR.name + ", " + currCR.start +", " + currCR.end +", " + currCR.read +". ");
		}
		
		System.out.println();
		
		System.out.println("Printout the chr2L late regions:");
		for(int i=0; i<late_size; i++){
			
			ChrRegion currCR = Chr2L_late.get(i);
			//if(currCR.name.equals("chr2L"))
				System.out.println(currCR.name + ", " + currCR.start +", " + currCR.end +", " + currCR.read +". ");
		}
		
		//close() Scanners
		read_Chr2L_early.close();
		read_Chr2L_late.close();
		Chr2L_reads.close();
		
	}//end of main()
	
}//end of everything in Chromatin_single_over_earlyandlate_regions class


/*********
 * Create a Chromatin region object, for Chr2L first;
 * Each ChrRegion was read from the Kc-EarlyDomains.bed or Kc-LateDomains.bed files;
 * Initially, the ChrRegion.read of each ChrRegion is '0'; Store all reads into Chr2L_early arrayList
 * and Chr2L_late arrayList.
 * 
 * Everytime we scan a read from BED_DM433_2L_output_t_922.txt document, we will compare the read_in
 * data with the Chr2L_early and Chr2L_Late arrayLists, if the read_in chromatin's start value matches any
 * ChrRegion in Chr2L_early or Chr2L_late arrayLists, update the ChrRegion.read++;
 * 
 * @author Jeff
 *
 */
class ChrRegion{
	
	String name = "";
	long start = 0;
	long end = 0;
	int read = 0;
}