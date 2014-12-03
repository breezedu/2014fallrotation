package rotation2014fall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/***********************
 * Assess the overlap between the late regions and your H2av peaks.
 * 
 * LATE REGIONS
 * Let's use a log2 ratio cutoff of 1, so that if DM436 is more than 2-fold greater than DM435, 
 * we'll call it an H2av "peak domain".  
 * Once you get the coordinate of each genomic region that has a log2 of at least 1, 
 * check if it overlaps with any of the regions in the "late" domains (attached).  
 * Ideally, the H2av peak domains should be highly enriched (maybe between 70-80%?) in late domains; 
 * otherwise, we probably need to alter the cutoff a little bit.  
 * 
 * @author Jeff
 *
 */
public class OverlapRepTimeAndLateregion_1203 {
	
	public static void main(String[] args) throws IOException{
		
		//let me do it in a super naive way first: bruce-force!
		
		//1st, create a late_region ArrayList to store all LateRegion objects;
		/****************************************************************************************
		 * Create a late_region list of each chromosome;
		 * for each chromosome, create a region list, then put all these lists into a total list* 
		 * in order of chromosomes: 2L, 2R, 3L, 3R, and X;
		 */
		ArrayList<ArrayList<lateRegion>> regionList = createRegionList();
		
		
		for(int i=0; i<regionList.size(); i++){
			System.out.println("regionList[" +i +"] = " + regionList.get(i).get(0).getChromosome() +",  " + regionList.get(i).size());
		}
		
		
		
		//2nd, create a ratioLog2 ArrayList to store all ratioLog2 objects;
		/*****************************************************************************************
		 * readIn chromosome distribution data from wiggle document:
		 * create ratioLog2 object according to the data read;
		 * put each ratioLog2 into corresponding arrayList;
		 * put all arrayList into total ArrayList;
		 */
		ArrayList<ArrayList<posTime>> ratioList = creatRatioArrayList();
		
		for(int i=0; i<ratioList.size(); i++){
			
			if(ratioList.get(i).size() != 0)
			System.out.println("ratioList[" +i + "] = " + ratioList.get(i).get(0).getChromosome() +",  " + ratioList.get(i).size() );
		}
		
		
		
		//3rd, check Overlap of each ratioLog2 object with Late-regions;
		//traverse through all ratioLog2 objects, if the ratioLog2.getRatio()>=1, check the overlap with late_domain;
		Scanner input = new Scanner(System.in);
		
		System.out.print("Please input the cutoff value: [1.0, 0.5, 0.25, 0.05 etc] \n cutoff = ");
		double cutoff = input.nextDouble();
		
		checkOverlap(ratioList, regionList, cutoff);
		
				
		
		//4th, statistic regions with H2Avs:
		
		statLateRegions(regionList);
		
		
		
		//5th, Stretch each Regions, make them span [0--100], then merge all reads of H2Avs into one array
		//output these merger reads into a txt document;
		//plot the array later;
		stretchLateRegions(ratioList, regionList, cutoff);
		
		
		//close input object:
		input.close();
		//end
		
	}//end main();
	
	
	
	/***************************
	 * Stretch each Regions, make them span [0--100], then merge all reads of H2Avs into one array
	 * output these merger reads into a txt document;
	 * plot the array later;
	 * @param ratioList
	 * @param regionList
	 * @param cutoff 
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	private static void stretchLateRegions(ArrayList<ArrayList<posTime>> ratioList,ArrayList<ArrayList<lateRegion>> regionList, double cutoff) throws IOException{
		// TODO Auto-generated method stub
		
		/******
		//1st, ask user to input cutoff value:
		System.out.print("Please input the cutoff [1.0, 0.5, 0.25] \n cuttoff = ");
		
		BufferedReader inp = new BufferedReader (new InputStreamReader(System.in));
		int cutoff = Integer.parseInt(inp.readLine());
		
		System.out.println("Cutoff is " + cutoff);
		*/
		System.out.println("stretch late Regions.");
		
		String routine = "D:/2014FallRotation/data/yulong/1203/";
		String doc_name = "StretchRegionWithTime_cutoff" + cutoff +".txt";
		
	//	String doc_timeing = "timingOverlap.txt";
		
		
		int[] regionCount = new int[101];
		double[] regionReads = new double[101];
		for(int i=0; i<101; i++){
			regionReads[i] = 0;
			regionCount[i] = 1; 	//to avoid 0 counts, when calculating average regionReads[i]/regionCount[i].
		}
		
		for(int i=0; i<ratioList.size(); i++){
			
			for( int j=0; j<ratioList.get(i).size(); j++){
				
				posTime currPostTime = ratioList.get(i).get(j);
				
				if(currPostTime.getTime() >= cutoff){
					
					stretchSingleRegion(currPostTime, regionReads, regionCount, regionList);
				}
				
			}//end for j loop;
		}//end for i loop;
		
		
		//output regionReads[] array into a txt document;
		
		
		File output_file = new File(routine + doc_name);
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));		
		
		for(int i=0; i<100; i++){
		
			output.write(i + "\t" + regionReads[i]/regionCount[i] +"\n");
		}//end for i<100 loop;
		
		
		//close output
		System.out.println("The output document could be found: " + routine + doc_name);
		output.close();
		
	}//end of stretchLateRegions() method;


	private static void stretchSingleRegion(posTime posTime, double[] regionReads, int[] regionCount, ArrayList<ArrayList<lateRegion>> regionList) {
		// TODO Auto-generated method stub
		
		int stretchPoint = 0;
		
		switch(posTime.getChromosome()){
		
		case "chr2L": stretchPoint = stretchSingleRatioSingleArrayList(posTime, regionList.get(0)); break;
		case "chr2R": stretchPoint = stretchSingleRatioSingleArrayList(posTime, regionList.get(1)); break;
		case "chr3L": stretchPoint = stretchSingleRatioSingleArrayList(posTime, regionList.get(2)); break;
		case "chr3R": stretchPoint = stretchSingleRatioSingleArrayList(posTime, regionList.get(3)); break;
		case "chrX":  stretchPoint = stretchSingleRatioSingleArrayList(posTime, regionList.get(4)); break;
				
		}
		
		if(stretchPoint>-1){
		
			regionReads[stretchPoint] = regionReads[stretchPoint] + posTime.getTime();
			regionCount[stretchPoint] = regionCount[stretchPoint] + 1;
		
		}//end if condition;	
		
	}//end stretchSingleRegion() method;



	private static int stretchSingleRatioSingleArrayList(posTime ratio, ArrayList<lateRegion> lateRegionChr) {
		// TODO Auto-generated method stub
		
		int regionPoint = -1;
		int size = lateRegionChr.size();
		
		for(int i=0; i<size; i++){
			
			lateRegion currRegion = lateRegionChr.get(i);
			
			if(ratio.getPos() >= currRegion.getStart() && ratio.getPos()<= currRegion.getEnd()) {
				
				regionPoint = 100 * (ratio.getPos() - currRegion.getStart()) / (currRegion.getEnd() - currRegion.getStart());
			}
			
		}//end for i loop;		
		
		return regionPoint;
	}//end stretchSingleRatioSingleArrayList() method;



	/**********************
	 * Statistic late region with H2Av vs Late region without H2Av;
	 * @param regionList
	 * @throws IOException 
	 */
	private static void statLateRegions(ArrayList<ArrayList<lateRegion>> regionList) throws IOException {
		// TODO Auto-generated method stub
		String routine = "D:/2014FallRotation/data/yulong/1029/RPKM2Wiggle/";
		
		//ask user to input doc_name, to mark cutoff value;
	//	Scanner input2 = new Scanner(System.in);
	//	System.out.print("Please input the document name of output doc:[LateRegionWithH2Av_cutoff05.txt] \n doc_name: ");
		
	//	String doc_name = input2.next();
		
		String doc_name = "LateRegionWithH2Av_cutoff0.05.txt";
		
		File output_file = new File(routine + doc_name);
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));		
		
		String firstLine = "track name=\"Late-WH2Av_05\" description=\"Late-withH2Av_05\" visibility=1 color=102,0,106";
		
		output.write(firstLine + "\n");
		
		int ChrNum = regionList.size();
		int totalLateRegions = 0;
		int totalLateRegionsWH2Av = 0;
		
		for(int i=0; i<ChrNum; i++){			
						
			int size = regionList.get(i).size();
			
			int regionWithH2Av = 0;
			int LateRegions = size;
			
			for(int j=0; j<size; j++){
				
				lateRegion currRegion = regionList.get(i).get(j); 
				
				if(currRegion.getH2Avs()) {
					
					regionWithH2Av++;
					output.write(currRegion.getChromosome() +"\t" + currRegion.getStart() + "\t" + currRegion.getEnd() +"\n");
				}
				
			}//end inner for j<size loop;		
			
			
			System.out.println(regionList.get(i).get(0).getChromosome() + " Late Regions with H2Av: " + regionWithH2Av +". Total late regions " + LateRegions );
			
			totalLateRegions += LateRegions;
			totalLateRegionsWH2Av += regionWithH2Av;
			
		}//end for i<ChrNum loop;
		
		System.out.println("In total, LateRegions are: " + totalLateRegions +"; LateRegions with H2Avs are: " + totalLateRegionsWH2Av +".");
		
		
		//close output writter;
		output.close();
	//	input2.close();
		
	}//end of statLateRegions()


	/**********************
	 * Check the overlap of ratios greater than 1 to all late-domain chromosomes;
	 * will call checkSingleRatio() method to check if one single ratio has overlap with any late-domain;
	 * Ask user to input the cutoff value: Log2Ratio = 1, 0.5, 0.25, 0.05 etc...
	 * @param ratioList
	 * @param regionList
	 * @throws IOException 
	 */
	private static void checkOverlap(ArrayList<ArrayList<posTime>> ratioList, ArrayList<ArrayList<lateRegion>> regionList, double cutoff) throws IOException {
		// TODO Auto-generated method stub
		
		String routine = "D:/2014FallRotation/data/yulong/1117/";
		String doc_name = "timeOverlap.txt";

		File output_file = new File(routine + doc_name);
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));	
		
		String subtitle = "timeOverlap";
		
		String firstLine = "track type=wiggle_0 name=" +subtitle +" description=\"" + subtitle + "\"";
		String secondLine = "variableStep chrom=";
		
		output.write(firstLine + "\n");
		
		System.out.println("Check Overlap.");
		System.out.println("ratioList: " + ratioList.size() +", " + ratioList.get(0).size());
		
		int greaterThanCut = 0;
		int overLapCount =0;
		
		for(int i=0; i<ratioList.size(); i++){
			
			String chromosome = ratioList.get(i).get(0).getChromosome();
			output.write(secondLine + chromosome +"\n");
			
			for(int j=0; j<ratioList.get(i).size(); j++){
				
				posTime currRatio = ratioList.get(i).get(j);
				
				//Here is the Log2Ratio difference:
				if(currRatio.getTime() >= cutoff){
					
					greaterThanCut++;
					
					if(checkSingleRatio(currRatio, regionList) == 1){
						
						overLapCount ++;
						output.write(currRatio.getPos() +"\t" + currRatio.getTime() + "\n");
					}
					
					overLapCount += checkSingleRatio(currRatio, regionList);
					
				}//end if-condition;
				
			}//end for j<ratioList.get(i).size() loop;
			
		}//end for i<ratioList.size() loop;
		
		System.out.println("There are " + greaterThanCut + " ratios greater than cutoff.");
		System.out.println("The count of ratios overlap with late-domains is: " + overLapCount);
		
		output.close();
		
	}//end of checkOverlap() method;


	/*******************************
	 * check if a single ratio has overlap with any late-domain region;
	 * will call checkSingleRatioSingleArrayList() method to check if that ratio object has overlap
	 * with a specific Chromosome-late-domain arrayList;
	 *  
	 * @param Ratio
	 * @param regionList
	 * @return
	 */
	private static int checkSingleRatio(posTime postime, ArrayList<ArrayList<lateRegion>> regionList) {
		// TODO Auto-generated method stub
		int check = 0;
		
		switch(postime.getChromosome()){
		
		case "chr2L": check = checkSingleRatioSingleArrayList(postime, regionList.get(0)); break;
		case "chr2R": check = checkSingleRatioSingleArrayList(postime, regionList.get(1)); break;
		case "chr3L": check = checkSingleRatioSingleArrayList(postime, regionList.get(2)); break;
		case "chr3R": check = checkSingleRatioSingleArrayList(postime, regionList.get(3)); break;
		case "chrX":  check = checkSingleRatioSingleArrayList(postime, regionList.get(4)); break;
		
		
		}
		
		return check;
	
	}//end of checkSingleRatio() method;


	/**********************************
	 * check if a single ratio has overlap with a specific chromosome's late-domain region
	 * if YES, update that lateRegion object's H2Avs parameter from False to True;
	 * 			make check = 1;
	 * @param ratio
	 * @param lateRegionChr
	 * @return
	 */
	private static int checkSingleRatioSingleArrayList(posTime ratio,	ArrayList<lateRegion> lateRegionChr) {
		// TODO Auto-generated method stub
		
		int check = 0;		
		int size = lateRegionChr.size();
		
		for(int i=0; i<size; i++){
			
			if(ratio.getPos() >= lateRegionChr.get(i).getStart() && ratio.getPos()<= lateRegionChr.get(i).getEnd()){
				
				check = 1;
				lateRegionChr.get(i).setH2Avs(true);
				
			}

		}//end for i<size loop;
		
		
		return check;
		
	}//end checkSingleRatioSingleArrayList() method;


	/*********************************************
	 * create ratioArrayList to store all ratio arrayLists for each chromosome;
	 * 
	 * @return ratioList
	 * @throws FileNotFoundException
	 */
	private static ArrayList<ArrayList<posTime>> creatRatioArrayList() throws FileNotFoundException {
		// TODO create a ratioList and return it
		ArrayList<ArrayList<posTime>> ratioList = new ArrayList<ArrayList<posTime>>();
		
		ArrayList<posTime> posTime_2L = new ArrayList<posTime>();
		ArrayList<posTime> posTime_2R = new ArrayList<posTime>();		
		ArrayList<posTime> posTime_3L = new ArrayList<posTime>();
		ArrayList<posTime> posTime_3R = new ArrayList<posTime>();
		ArrayList<posTime> posTime_X = new ArrayList<posTime>();
		
		
		//create a scanner to readIn ratio data from D:\2014FallRotation\data\yulong\1114\
		//document: DM436OVERDM435_wiggle_10000.txt
		String routine = "D:/2014FallRotation/data/yulong/1114/";
		String doc_name = "kc_replication_timing.csv";
		
		Scanner readIn = new Scanner(new File(routine + doc_name));
		
		String firstLine = readIn.nextLine();
		System.out.println("First line of ratioLog2: " + firstLine);
		
	//	String chrName = "";
		while(readIn.hasNextLine()){
			
			String currLine = readIn.nextLine();
							
			String[] str = currLine.split(",");
				
			int pos = Integer.parseInt(str[1]);
			String chrName = "chr" + str[0]; 		//	System.out.println(" " + str[0] +"\t" + str[1] +"\t" + str[2]);
			
			double time = 0;
			
			if(!str[2].equals("NA")) time = Double.parseDouble(str[2]);
				
			posTime newRatio = new posTime(chrName, pos, time);
				
			switch(newRatio.getChromosome()){
				
			case "chr2L": posTime_2L.add(newRatio); break;
			case "chr2R": posTime_2R.add(newRatio); break;
			case "chr3L": posTime_3L.add(newRatio); break;
			case "chr3R": posTime_3R.add(newRatio); break;
			case "chrX":  posTime_X.add(newRatio);  break;
				
			}//end switch;
				
						
		}//end while loop;
		
		//add all ratio_chromosome lists to the ratioList ArrayList;
		ratioList.add(posTime_2L);
		ratioList.add(posTime_2R);
		ratioList.add(posTime_3L);
		ratioList.add(posTime_3R);
		ratioList.add(posTime_X);
		
		//close readIn scanner
		readIn.close();
		
		return ratioList;
	
	}//end of creatRatioArrayList() method;



	/******************
	 * read_in late-region data from D:\2014FallRotation\data\yulong\1029\RPKM2Wiggle;
	 * @return regionList
	 * @throws FileNotFoundException 
	 */
	private static ArrayList<ArrayList<lateRegion>> createRegionList() throws FileNotFoundException {
		// TODO create an ArrayList for each chromosome, put all these lists into a total ArrayList
		
		//1st, initialize arrayLists;
		ArrayList<ArrayList<lateRegion>> regionList = new ArrayList<ArrayList<lateRegion>>();
		
		ArrayList<lateRegion> regionList_2L = new ArrayList<lateRegion>();
		ArrayList<lateRegion> regionList_2R = new ArrayList<lateRegion>();
		ArrayList<lateRegion> regionList_3L = new ArrayList<lateRegion>();
		ArrayList<lateRegion> regionList_3R = new ArrayList<lateRegion>();
		ArrayList<lateRegion> regionList_X = new ArrayList<lateRegion>();
		
		
		//2nd, create a scanner to read_in data from D:\2014FallRotation\data\yulong\1029\RPKM2Wiggle
		String routine = "D:/2014FallRotation/data/yulong/1029/RPKM2Wiggle/";
		String doc_name = "Kc-LateDomains.BED";
		Scanner readIn = new Scanner(new File(routine + doc_name));
		
		String firstLine = readIn.nextLine();
		System.out.println("1st line of Kc-LateDomains: " + firstLine);
		
		while(readIn.hasNextLine()){
			
			String currLine = readIn.nextLine();
			String[] chromosome = currLine.split("\t");
			
			String name = chromosome[0];
			int start = Integer.parseInt(chromosome[1]);
			int end = Integer.parseInt(chromosome[2]);
			
			lateRegion newLateRegion = new lateRegion(name, start, end);
			
			switch(name){
			
			case "chr2L": regionList_2L.add(newLateRegion); break;
			case "chr2R": regionList_2R.add(newLateRegion); break;
			case "chr3L": regionList_3L.add(newLateRegion); break;
			case "chr3R": regionList_3R.add(newLateRegion); break;
			case "chrX":  regionList_X.add(newLateRegion);  break;
			
			}//end switch-case loop;
			
		}//end while loop;
		
		
		//close readIn scanner
		readIn.close();
		
		
		//add all regionLists to the total regionList ArrayList;
		regionList.add(regionList_2L);		
		regionList.add(regionList_2R);
		regionList.add(regionList_3L);
		regionList.add(regionList_3R);		
		regionList.add(regionList_X);
		
		
		//return the regionList
		return regionList;
		
	}//end createRegionList() method;

}//end of OverlapH2avAndLaterregion class;



/*************************
 * An object of log2 ratio, DM436/DM435;
 * for each ratioLog2 object, there's a chromosome name: 2L, 2R, 3L, 3R, and X;
 * and the pos, the position of each 'read'
 * the ratio, which is the log2 ratio of DM436 over DM435;
 * 
 * @author Jeff
 *
 *
class posTime{
	
	private String chromosome;
	private int pos;
	private double time;
	
	public posTime(String chromosome, int pos, double time){
		super();
		this.chromosome = chromosome;
		this.pos = pos;
		this.time = time;
		
	}
	
	public void setChromosome(String chromosome){
		this.chromosome = chromosome;
	}
	
	public String getChromosome(){
		return this.chromosome;
	}
	
	public void setPos(int pos){
		this.pos = pos;
	}
	
	public int getPos(){
		return this.pos;
	}
	
	public void setTime(double time){
		this.time = time;
	}
	
	public double getTime(){
		return this.time;
	}
	
}//end of ratioLog2 class;


*************************
 * An object of lateRegion class;
 * This object means a 'late' region for one chromosome;
 * each object has a chromosome, a start and an end parameters;
 * the chomosome describe the chromosome it marks;
 * the start and the end, are two boundaries of one region. (the region belongs to 'late' phase); 
 * @author Jeff
 *
 *
class lateRegion{
	
	private String chromosome;
	private int start;
	private int end;
	private boolean H2Avs;
	
	public lateRegion(String chromosome, int start, int end){
		super();
		
		this.chromosome = chromosome;
		this.start = start;
		this.end = end;
		this.H2Avs = false;
		
	}
	
	
	public void setChromosome(String chromosome){
		this.chromosome = chromosome;
	}
	
	public String getChromosome(){
		return this.chromosome;
	}
	
	public void setStart(int start){
		this.start = start;
	}
	
	public int getStart(){
		return this.start;
	}
	
	public void setEnd(int end){
		this.end = end;
	}
	
	public int getEnd(){
		return this.end;
	}
	
	public void setH2Avs(boolean H2Avs){
		this.H2Avs = H2Avs;
	}
	
	public boolean getH2Avs(){
		return this.H2Avs;
	}
	
	
}//end of lateRegion class;

*/
