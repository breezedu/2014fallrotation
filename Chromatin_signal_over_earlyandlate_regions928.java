package rotation2014fall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

public class Chromatin_signal_over_earlyandlate_regions928 {

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
		
		
		//read_in data from Kc-EarlyDomains.bed and build Chr2L_early arrayList;
		//call read_chr_earlyandlate() method to read-in early and late chromatin regions;
		Chr_early = read_chr_earlyandlate(read_Chr_early);
		Chr_late = read_chr_earlyandlate(read_Chr_late);
		


		
		/********************************************************************************************/
		//2nd, read_in chromatin2L reads from BED_DM433_2L_output_t_922.txt document;
		//compare the read_in read with Chr2L_early and Chr2L_late data in two ArrayLists; 
		//if we get a match (like Chr2L_early.at(3), update Chr2L_early.at(3).read++;
		Scanner Chr_reads = new Scanner(new File("D:/2014FallRotation/data/BED_yulong_allChrs_output_926.txt"));
		String firstLine = Chr_reads.nextLine();
		System.out.println("The first line of BED_yulong_2L_output_t_922.txt is: " + firstLine);
		
		
		/*****************************************************
		 * create a hashMap here for all read regions;
		 * take the first early read region: chr2L	59001	300000 for example,
		 * in the HashMap we are going to build, every index between [59001-300000] 
		 * would be taken as a key, all these 240999 keys will map to one value: 
		 * the position index that chr2L locates in the ArrayList;
		 * 
		 * Just one HashMap would be OK, for early_phase, store the value as negative; 
		 * for late_phase, store the value as positive. 
		 * 
		 * For each read we got from the BED document, go check it's value in the HashMap, 
		 * then get the index in O(1) running time, then just let the ChrRegion.read++;
		 *  
		 */
		// HashMap<Long, Integer> phaseHash = buildPhaseHash(Chr_early, Chr_late);
		
		while(Chr_reads.hasNextLine()){
			
			String currLine = Chr_reads.nextLine();
			//System.out.println(currLine +". ");
			
			String[] splitLine = currLine.split("\t");
			String name = splitLine[0];
			
			long start = Long.parseLong(splitLine[1]);
			
			
			
			//binary-search-tree:
			//binarySearch(Chr_early, 0, Chr_early.size(), name, start);
			//binarySearch(Chr_late, 0, Chr_late.size(), name, start);
			
			
			/********/
			// bruchforce search:
			
			int early_size = Chr_early.size();
			int late_size = Chr_late.size();
			
			for(int i=0; i<early_size; i++){
				
				if(name.equals(Chr_early.get(i).name) && start >= Chr_early.get(i).start && start <= Chr_early.get(i).end){
					
					Chr_early.get(i).read++;
					i = early_size;         //jump out of early loop;
				}
			}//end for i<early_size loop;
			
			
			for(int j=0; j<late_size; j++){
				
				if(name.equals(Chr_late.get(j).name) && start >= Chr_late.get(j).start && start <= Chr_late.get(j).end){
					
					Chr_late.get(j).read++;
					j = late_size;
				}
			}//end for j<late_size loop;
			/**/
			
		}//end while(Chr2L_reads.hasNextLine()) loop;
		
		
		
		
		/********************************************************************************************/
		//3rd, printout the updated Chr_early and Chr_late arrayLists:
		System.out.println("Printout the chrAll early and late regions:");
		
		printArrayList(Chr_early);
		printArrayList(Chr_late);
		
		
		
		
		/********************************************************************************************/
		//4th, write the Chr_early and Chr_late arrayLists data into two txt documents;
		//call writeIntoDoc() method to write every object in the Chr_early ArrayList or Chr_late ArrayList into a txt document;
		//early phase
		File output_Chr_early = new File("D:/2014FallRotation/data/Yulong_allChrs_early_output_928.txt");
		BufferedWriter output_early = new BufferedWriter(new FileWriter(output_Chr_early));
		
		writeIntoDoc(output_early, Chr_early);
		
		
		
		//late_phase
		File output_Chr_late = new File("D:/2014FallRotation/data/Yulong_allChrs_late_output_928.txt");
		BufferedWriter output_late = new BufferedWriter(new FileWriter(output_Chr_late));
		
		writeIntoDoc(output_late, Chr_late);
		
		
		

		/********************************************************************************************/
		//5th, close() Scanners
		read_Chr_early.close();
		read_Chr_late.close();
		Chr_reads.close();
		
		//close() output_writters
		output_early.close();
		output_late.close();
		
	}//end of main()
	
	
	

	
	/*********
	 * Binary-search-tree, to check if a chromatin could be located 
	 * in a certain chromitin region in either early or late arrayList;
	 * @param chr_late
	 * @param p
	 * @param q
	 * @param name
	 * @param start
	 */
	private static void binarySearch(ArrayList<ChrRegion> chr_array, int p, int q, String name, long start) {
		// TODO Auto-generated method stub
		
		if(p>=chr_array.size() || p>q){
			
			//do nothing; 
			
		} else {
			
			int median = (p+q)/2;
			ChrRegion currChr = chr_array.get(median);
			
			if(currChr.name.equals(name) && start >= currChr.start && start <= currChr.end) {

				chr_array.get(median).read++;
			
			} else if(start <currChr.start) {
				
				binarySearch(chr_array, p, median-1, name, start);
			
			} else if(start > currChr.end){
				
				binarySearch(chr_array, median+1, q, name, start);
			}
			
			
			
		}//end of else (p<=q) conditoin;
		
		
	}//end of binarySearch() method;
	
	






	/*******
	private static HashMap<Long, Integer> buildPhaseHash( ArrayList<ChrRegion> chr_early, ArrayList<ChrRegion> chr_late) {
		// TODO Auto-generated method stub
		HashMap<Long, Integer> phaseHash = new HashMap<Long, Integer>();
		
		int early_size = chr_early.size();
		int late_size = chr_late.size();
		
		ChrRegion currChr = null;
		
		
		//first put all ChrRegions in early_phase into hashMap;
		for(int i=0; i<early_size; i++){
			
			int value = -1 * i;					//for early_phase, the value is negivative;
			currChr = chr_early.get(i);
			for(long j= currChr.start; j<=currChr.end; j++){
				
				phaseHash.put(j, value);
			}
			
		}//end for i<early_size loop;
		
		
		//now add late_phase Chr_regions into hashMap;
		for(int i=0; i<late_size; i++){
			
			currChr = chr_late.get(i);
			
			for(long j = currChr.start; j<=currChr.end; j++){
				
				phaseHash.put(j,  i);
			}
			
		}//end for i<late_size loop;
		
		
		
		return phaseHash;
		
	}//end of buildPhaseHash() method;
	*/






	private static void writeIntoDoc(BufferedWriter output_early, ArrayList<ChrRegion> chr_arraylist) throws IOException {
		// TODO Auto-generated method stub
		
		//chr_name  start  end  read.
		//we could assign title for these columns in R, so here we do not need to add title line;
		//output_early.write("chr_name" + "\t" + "start_pos" + "\t" + "end_pos" +"\t" + "read_count" +"\n");
		
		int size = chr_arraylist.size();
				
		for(int i=0; i<size; i++){
					
			ChrRegion currChr = chr_arraylist.get(i);
			output_early.write(currChr.name + "\t" + currChr.start + "\t" + currChr.end +"\t" + currChr.read +"\n");
				
		}//end for i<size loop;
		
	}//end writeIntoDoc() method;



	/******************
	 * Printout every element in the Region-ArrayList;
	 * @param chr_array
	 */
	private static void printArrayList(ArrayList<ChrRegion> chr_array) {
		// TODO Auto-generated method stub
		int size = chr_array.size();
		for(int i=0; i<size; i++){
			
			ChrRegion currCR = chr_array.get(i);
			//if(currCR.name.equals("chr2L"))
			System.out.println("Line 172:  " + currCR.name + ", " + currCR.start +", " + currCR.end +", " + currCR.read +". ");
		}
		
		System.out.println();
		
	}//end printArrayList() method;
	
	
	
	
	
	/*************
	 * build Chr_early_Arraylist and Chr_late_ArrayList;
	 * 
	 * @param read_Chr
	 * @return
	 */
	private static ArrayList<ChrRegion> read_chr_earlyandlate( Scanner read_Chr) {
		// TODO Auto-generated method stub
		ArrayList<ChrRegion> ChrArrayList = new ArrayList<ChrRegion>();
		
		
		//printout the first line, the title line;
		String firstLine = read_Chr.nextLine();
		System.out.println("First line:" + firstLine);
		
			

		while(read_Chr.hasNextLine()){
						
			String line = read_Chr.nextLine();
			String[] splitLine = line.split("\t");
			
			String name = splitLine[0];
			long start = Long.parseLong(splitLine[1]);
			long end = Long.parseLong(splitLine[2]);
			
			//build a new ChrRegion object;
			ChrRegion currChrRegion = new ChrRegion();
			currChrRegion.name = name;
			currChrRegion.start = start;
			currChrRegion.end = end;
			
			ChrArrayList.add(currChrRegion);
			
		}//end while(read_Chr2L_early.hasNextLine()) loop;
		
		int size = ChrArrayList.size();
		System.out.println("There are " + size + " chromation regions in this phase.\n");
		
		
		return ChrArrayList;
		
	}//end of read_chr_earlyandlate() method;
	
	
	
}//end of everything in Chromatin_single_over_earlyandlate_regions class

