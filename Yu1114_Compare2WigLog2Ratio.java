package rotation2014fall;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/***********************
 * Read-in two wig documents: D:\2014FallRotation\data\yulong\1114\DM432.wig or DM431.wig;
 * 
 * 
 * @author Jeff
 *
 */
public class Yu1114_Compare2WigLog2Ratio {

	public static void main(String[] args) throws FileNotFoundException{
		
		System.out.println("Readin two wig files, compare their log2 ratios.");
		
		//1st, input parameters:
		//create 1 scanners to input two document names: DM432 and DM431 // DM434 and DM433;
		String routine = "D:/2014FallRotation/data/yulong/1114/";
		
		Scanner input = new Scanner(System.in);
		
		System.out.print("Please input the document one: [DM432.wig] \n Doc_One: ");
		String doc_name1 = input.next();
		
		System.out.print("Please input the document two: [DM431.wig] \n Doc_Two: ");
		String doc_name2 = input.next();
		
		System.out.print("Please indicate the size of window: [10,000, 50,000] \n Window: " );
		int window = 10000; // input.nextInt();
		
		System.out.print("Please indicate the size of BIN: [1000, 2000] \n Bin: ");
		int bin = 1000; //input.nextInt();
		
		
		
		//2nd, scan data from documents:
		//create 2 scanners to readin data from two documents just inputed
		Scanner readIn1 = new Scanner(new File(routine + doc_name1));
		Scanner readIn2 = new Scanner(new File(routine + doc_name2));
		
		
		
		//3rd, create two ArrayList of reads in  Bin-Window format:
		//
		
		ArrayList<Double> oneList = createBinList(readIn1, bin, window);
		ArrayList<Double> twoList = createBinList(readIn2, bin, window);		
		
		
		
		//4th, calculateLog2Ratio;
		calculateLog2Ratio(oneList, twoList);
		
		
		//close Scanners
		input.close();
		readIn1.close();
		readIn2.close();
		
	}//end main();
	
	

	private static void calculateLog2Ratio(ArrayList<Double> oneList, ArrayList<Double> twoList) {
		// TODO Auto-generated method stub
		
		
		
	}



	private static ArrayList<Double> createBinList(Scanner readIn, int bin, int window) {
		// TODO Auto-generated method stub
		//the first 5 lines are just information about the wig file
		for(int i=0; i<3; i++){
			String line = readIn.nextLine();
			System.out.println(line);
		}
		
		ArrayList<Double> readList = new ArrayList<Double>();
		
		while(readIn.hasNextLine()){
			String line = readIn.nextLine();
			
			if(line.length() < 20){
				double value = Double.parseDouble(line);
				readList.add(value);
			}
		}
		
		System.out.println("The size of this arrayList: " + readList.size() +"\n");
		
		return readList;
	}
	
}//end of Yu1114_Compare2WigLog2Ratio class
