package rotation2014fall;

import java.io.File;
import java.io.FileNotFoundException;
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
public class OverlapH2avAndLateregion {
	
	public static void main(String[] args) throws FileNotFoundException{
		
		//let me do it in a super naive way first: bruce-force!
		
		//1st;
		/****************************************************************************************
		 * Create a late_region list of each chromosome;
		 * for each chromosome, create a region list, then put all these lists into a total list* 
		 * in order of chromosomes: 2L, 2R, 3L, 3R, and X;
		 */
		ArrayList<ArrayList<lateRegion>> regionList = createRegionList();
		
		
		for(int i=0; i<regionList.size(); i++){
			System.out.println("regionList[" +i +"] = " + regionList.get(i).get(0).getChromosome() +",  " + regionList.get(i).size());
		}
		
		
		
		//2nd;
		/*****************************************************************************************
		 * readIn chromosome distribution data from wiggle document:
		 * create ratioLog2 object according to the data read;
		 * put each ratioLog2 into corresponding arrayList;
		 * put all arrayList into total ArrayList;
		 */
		ArrayList<ArrayList<ratioLog2>> ratioList = creatRatioArrayList();
		
		for(int i=0; i<ratioList.size(); i++){
			
			if(ratioList.get(i).size() != 0)
			System.out.println("ratioList[" +i + "] = " + ratioList.get(i).get(0).getChromosome() +",  " + ratioList.get(i).size() );
		}
		
		
		
		//3rd;
		//traverse through all ratioLog2 objects, if the ratioLog2.getRatio()>=1, check the overlap with late_domain;
		
		checkOverlap(ratioList, regionList);
		
		
		//end
		
	}//end main();
	
	
	/**********************
	 * Check the overlap of ratios greater than 1 to all late-domain chromosomes;
	 * will call checkSingleRatio() method to check if one single ratio has overlap with any late-domain;
	 * 
	 * @param ratioList
	 * @param regionList
	 */
	private static void checkOverlap(ArrayList<ArrayList<ratioLog2>> ratioList,	ArrayList<ArrayList<lateRegion>> regionList) {
		// TODO Auto-generated method stub
		int greaterThanOne = 0;
		int overLapCount =0;
		
		for(int i=0; i<ratioList.size(); i++){
			
			for(int j=0; j<ratioList.get(i).size(); j++){
				
				ratioLog2 currRatio = ratioList.get(i).get(j);
				
				if(currRatio.getRatio() >= 1){
					
					greaterThanOne++;
					
					overLapCount += checkSingleRatio(currRatio, regionList);
					
				}//end if-condition;
				
			}//end for j<ratioList.get(i).size() loop;
			
		}//end for i<ratioList.size() loop;
		
		System.out.println("There are " + greaterThanOne + " ratios greater than one.");
		System.out.println("The count of ratios overlap with late-domains is: " + overLapCount);
		
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
	private static int checkSingleRatio(ratioLog2 Ratio, ArrayList<ArrayList<lateRegion>> regionList) {
		// TODO Auto-generated method stub
		int check = 0;
		
		switch(Ratio.getChromosome()){
		
		case "chr2L": check = checkSingleRatioSingleArrayList(Ratio, regionList.get(0)); break;
		case "chr2R": check = checkSingleRatioSingleArrayList(Ratio, regionList.get(1)); break;
		case "chr3L": check = checkSingleRatioSingleArrayList(Ratio, regionList.get(2)); break;
		case "chr3R": check = checkSingleRatioSingleArrayList(Ratio, regionList.get(3)); break;
		case "chrX":  check = checkSingleRatioSingleArrayList(Ratio, regionList.get(4)); break;

		
		}
		
		return check;
	
	}//end of checkSingleRatio() method;


	/**********************************
	 * check if a single ratio has overlap with a specific chromosome's late-domain region
	 * @param ratio
	 * @param lateRegionChr
	 * @return
	 */
	private static int checkSingleRatioSingleArrayList(ratioLog2 ratio,	ArrayList<lateRegion> lateRegionChr) {
		// TODO Auto-generated method stub
		
		int check = 0;		
		int size = lateRegionChr.size();
		
		for(int i=0; i<size; i++){
			
			if(ratio.getPos() >= lateRegionChr.get(i).getStart() && ratio.getPos()<= lateRegionChr.get(i).getEnd())
				check = 1;
		}
		
		
		return check;
	}


	/*********************************************
	 * create ratioArrayList to store all ratio arrayLists for each chromosome;
	 * 
	 * @return ratioList
	 * @throws FileNotFoundException
	 */
	private static ArrayList<ArrayList<ratioLog2>> creatRatioArrayList() throws FileNotFoundException {
		// TODO create a ratioList and return it
		ArrayList<ArrayList<ratioLog2>> ratioList = new ArrayList<ArrayList<ratioLog2>>();
		
		ArrayList<ratioLog2> ratio_2L = new ArrayList<ratioLog2>();
		ArrayList<ratioLog2> ratio_2R = new ArrayList<ratioLog2>();		
		ArrayList<ratioLog2> ratio_3L = new ArrayList<ratioLog2>();
		ArrayList<ratioLog2> ratio_3R = new ArrayList<ratioLog2>();
		ArrayList<ratioLog2> ratio_X = new ArrayList<ratioLog2>();
		
		
		//create a scanner to readIn ratio data from D:\2014FallRotation\data\yulong\1029\RPKM2Wiggle
		//document: DM436OVERDM435_wiggle_10000.txt
		String routine = "D:/2014FallRotation/data/yulong/1029/RPKM2Wiggle/";
		String doc_name = "DM436OVERDM435_wiggle_10000.txt";
		
		Scanner readIn = new Scanner(new File(routine + doc_name));
		
		String firstLine = readIn.nextLine();
		System.out.println("First line of ratioLog2: " + firstLine);
		
		String chrName = "";
		while(readIn.hasNextLine()){
			
			String currLine = readIn.nextLine();
			if(currLine.charAt(0) == 'v'){
				String[] str = currLine.split("=");
				chrName = str[1];
			
			} else {
				
				String[] str = currLine.split("\t");
				
				int pos = Integer.parseInt(str[0]);
				double ratio = Double.parseDouble(str[1]);
				
				ratioLog2 newRatio = new ratioLog2(chrName, pos, ratio);
				
				switch(newRatio.getChromosome()){
				
				case "chr2L": ratio_2L.add(newRatio); break;
				case "chr2R": ratio_2R.add(newRatio); break;
				case "chr3L": ratio_3L.add(newRatio); break;
				case "chr3R": ratio_3R.add(newRatio); break;
				case "chrX":  ratio_X.add(newRatio);  break;
				
				}//end switch;
				
			}//end else loop;
						
		}//end while loop;
		
		//add all ratio_chromosome lists to the ratioList ArrayList;
		ratioList.add(ratio_2L);
		ratioList.add(ratio_2R);
		ratioList.add(ratio_3L);
		ratioList.add(ratio_3R);
		ratioList.add(ratio_X);
		
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
 */
class ratioLog2{
	
	private String chromosome;
	private int pos;
	private double ratio;
	
	public ratioLog2(String chromosome, int pos, double ratio){
		super();
		this.chromosome = chromosome;
		this.pos = pos;
		this.ratio = ratio;
		
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
	
	public void setRatio(double ratio){
		this.ratio = ratio;
	}
	
	public double getRatio(){
		return this.ratio;
	}
	
}//end of ratioLog2 class;


/*************************
 * An object of lateRegion class;
 * This object means a 'late' region for one chromosome;
 * each object has a chromosome, a start and an end parameters;
 * the chomosome describe the chromosome it marks;
 * the start and the end, are two boundaries of one region. (the region belongs to 'late' phase); 
 * @author Jeff
 *
 */
class lateRegion{
	
	private String chromosome;
	private int start;
	private int end;
	
	public lateRegion(String chromosome, int start, int end){
		super();
		
		this.chromosome = chromosome;
		this.start = start;
		this.end = end;
		
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
	
	
}//end of lateRegion class;
