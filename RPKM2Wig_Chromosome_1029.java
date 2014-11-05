package rotation2014fall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/********************
 *  Create a wiggle track for the ratio plots that can be loaded into the ucsc genome browse
 *  
 *  The first line of the wiggle file has to be:
 *  track type=wiggle_0 name=<track_label> description=<center_label>
 *  where <track_label> and <center_label> are your own descriptions
 *  Then, each chromosome contains a header line as follows:
 *  VariableStep chrom=chr<chr_name>
 *  where <chr_name> is 2L, 3L, 3R, 3L, or X (we can ignore the 4th for now)
 *  Then each data point in that file is the position, followed by a tab, followed by the data point.  
 *  So in the case of your 10,000 bp bins, I'd say use the midpoint of the bin for your position, 
 *  so you'd have (5,000 followed by 15,000 followed by 25,000 etc...)
 *  When you start a new chr, all you need to do is enter a new "variableStep chrom=chr<chr_name>" line.  
 *  
 *  
 *  See the "test.wig" as an example below:
 *  
 *  track type=wiggle_0 name=Test description="My test"
 *  variableStep chrom=chr2L
 *  45000	4.1589
 *  55000	8.3176
 *  65000	11.7833
 *  75000	15.816
 *  variableStep chrom=chr2R
 *  5000	7.7268
 *  15000	9.3274
 *  25000	10.928
 *  35000	10.762
 *  45000	12.5838
 *  
 * @author Jeff
 *
 */
public class RPKM2Wig_Chromosome_1029 {
	
	
	public static void main(String[] args) throws IOException{
		
		//ask user to input two documents, and the window range;
		Scanner input = new Scanner(System.in);
		
		System.out.print("Please input doc_one: [DM457, DM458, DM459 etc] \n doc_one: " );
		String doc_one = input.next();
		System.out.print("Please input doc_two: [DM456, DM460, or DM435]\n doc_two: " );
		String doc_two = input.next();
		
		
		String routine = "D:/2014FallRotation/data/yulong/";
		String date = "1105";
		String document = doc_one + "OVER" + doc_two;
		
		System.out.print("Please input the window range:[10,000 or 50,000] \n window = ");
		int window = input.nextInt();
		
		String[] chromosome = {"2L", "2R", "3L", "3R", "X"};
		
		//create an output writer:
		File output_file = new File(routine +date+"/RPKM2Wiggle/" + document +"_wiggle_" + window +".txt");
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));
		
		//First line of wiggle format document: track type=wiggle_0 name=Test description="My test"
		String firstLine = "track type=wiggle_0 name=436over435_" + window+ " description=\"" +window+ "_test\"";
		output.write(firstLine + "\n");
		
		System.out.println();
		
		
		//for each chromosome (2L, 2R, 3L, 3R, and X), write the ratio to wiggle document;
		for(int i=0; i<chromosome.length; i++){
			
			String addLine = "variableStep chrom=chr" + chromosome[i];
			System.out.println(addLine);
			
			output.write(addLine + "\n");
			//output.write(4500 +"\t" + 0.543 + "\n");
			
			writeChromosomeWiggle(chromosome[i], window, doc_one, doc_two, output);
			
		}
		
		System.out.println("document could be found:" +routine +date+"/RPKM2Wiggle/" + document +"_wiggle_" + window +".txt");
		
		
		//close output writer and input scanner;
		output.close();
		input.close();
		
	}//end main();
	
	
	
	private static void writeChromosomeWiggle(String chromosome, int window, String doc_one, String doc_two, BufferedWriter output) throws IOException {
		// TODO Auto-generated method stub
		//the routine is: D:\2014FallRotation\data\yulong\1010\Bin2RPKM
		//the two data-documents were DM436_Bin2RPKM_ +chromosome +_10000R.txt
		
		String routine = "D:/2014FallRotation/data/yulong/1010/Bin2RPKM/";
		String doc1 = doc_one + "_Bin2RPKM_" + chromosome + "_" + window +"R.txt";
		String doc2 = doc_two + "_Bin2RPKM_" + chromosome + "_" + window +"R.txt";
		
		//create 2 scanners to read RPKM values from two documents;
		Scanner rpkmReader1 = new Scanner(new File(routine + doc1));
		Scanner rpkmReader2 = new Scanner(new File(routine + doc2));
		
		
		while(rpkmReader1.hasNextLine() && rpkmReader2.hasNextLine()){
			
			String[] dm436 = rpkmReader1.nextLine().split("\t");
			String[] dm435 = rpkmReader2.nextLine().split("\t");
			
			int index = Integer.parseInt(dm436[1]);
			
			//make the middle, when index = 1, the region is from 1001 to 2000; so, 1500 is the middle index;
			index = index*1000 + 500;
			
			double read1 = Double.parseDouble(dm436[2]) +1;
			double read2 = Double.parseDouble(dm435[2]) +1;
			
			double ratio = (Math.log(read1) - Math.log(read2)) / Math.log(2);
			
			output.write(index + "\t" + ratio + "\n");
			
		}//end while loop;
		
		
		//close scanners
		rpkmReader1.close();
		rpkmReader2.close();
		
	}//end of writeChromosomeWiggle() method;
	
	
}//end of RPKM2Wig_Chromosome_1029 class
