package rotation2014fall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

	public static void main(String[] args) throws IOException{
		
		System.out.println("Readin two wig files, compare their log2 ratios.");
		
		//1st, input parameters:
		//create 1 scanners to input two document names: DM432 and DM431 // DM434 and DM433;
		String routine = "D:/2014FallRotation/data/yulong/1114/";
		
		Scanner input = new Scanner(System.in);
		
		System.out.print("Please input the document one: [DM432] \n Doc_One: ");
		String doc_name1 = input.next();
		
		System.out.print("Please input the document two: [DM431] \n Doc_Two: ");
		String doc_name2 = input.next();
		
		System.out.print("Please indicate the size of window: [10,000, 50,000] \n Window: " );
		int window = 10000; // input.nextInt();
		
		System.out.print("Please indicate the size of BIN: [1000, 2000] \n Bin: ");
		int bin = 1000; //input.nextInt();
		
		
		
		//2nd, scan data from documents:
		//create 2 scanners to readin data from two documents just inputed
		Scanner readIn1 = new Scanner(new File(routine + doc_name1 +".wig"));
		Scanner readIn2 = new Scanner(new File(routine + doc_name2 +".wig"));
		
		
		
		//3rd, create two ArrayList of reads in  Bin-Window format:
		//
		
		ArrayList<Double> oneList = createBinList(readIn1, bin, window);
		ArrayList<Double> twoList = createBinList(readIn2, bin, window);		
		
		
		
		//4th, calculateLog2Ratio;
		calculateLog2Ratio(oneList, twoList, routine, doc_name1, doc_name2);
		
		
		//close Scanners
		input.close();
		readIn1.close();
		readIn2.close();
		
	}//end main();
	
	

	private static void calculateLog2Ratio(ArrayList<Double> oneList, ArrayList<Double> twoList, String routine, String doc_name1, String doc_name2) throws IOException {
		// TODO Auto-generated method stub
		
		String doc_name = doc_name1 + "over" + doc_name2+".wig";
		
		File output_file = new File(routine + doc_name);
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));	
		
		String subtitle = doc_name1 + "Over" + doc_name2;
		
		String firstLine = "track type=wiggle_0 name=" +subtitle +" description=\"" + subtitle + "\"";
		String secondLine = "variableStep chrom=chr2L";
		output.write(firstLine + "\n");
		output.write(secondLine + "\n");
		
		double log2 = Math.log(2);
		int size = oneList.size();
		
		for(int i=0; i<size; i++){
			
			/***
			double log2ratio = 0;
			
			for(int j=i; j<i+500; j++){
				double value1 = oneList.get(j)+1;
				double value2 = twoList.get(j)+1;
				
				log2ratio += (Math.log(value1) - Math.log(value2)) / log2;

			}//end for j loop;
			
			*/
			
			double value1 = oneList.get(i)+1;
			double value2 = twoList.get(i)+1;
			
			double log2ratio = (Math.log(value1) - Math.log(value2)) / log2;
			
			
			output.write((i*250+1) + "\t" + log2ratio + "\n");
			
		}//end for i loop;
		
		
		System.out.println("document could be found: " + routine + doc_name);
		output.close();
		
	}//



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
