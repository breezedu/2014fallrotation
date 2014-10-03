package rotation2014fall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/*********************
 * BED file: http://genome.ucsc.edu/FAQ/FAQformat.html#format1
 * 
 * For example, the first read in your output is on the negative strand, 
 * with chrom 3R and position 13533406.  So your output would be:
 * chr3R (tab) 13533357 (tab) 13533406 (new-line)
 *  * Start with chr2L for now.  
 * 
 * Note that the chr names for UCSC have to have the "chr" before the chr name, 
 * so just make sure to include that.
 * 
 * From fastq to UCSC genomic browser then calculating the ratio between two sub-types:
 * step 1: from fastq to bowtie_output;
 * step 2: from bowtie_output to BED documents;
 * step 3: from BED to Bin documents;
 * step 4: submit the Bin documents to UCSC genomic browser;
 * step 5: calculate the ratio of reads between two sub-types;
 * 
 * This is the step 2 from bowtie_output to BED documents;
 * 
 * @author Jeff
 *
 */
public class Bowtie2BED_transfer_1003 {
	
	public static void main(String[] args) throws IOException{
				
		System.out.println("Build BED format file from Bowtie output file. \n");
				
		//1st, read-in data from Bowtie output file in the alchemy.duhs.duke.edu directory:
		
		System.out.println("Modele 1: input Routine and file name for scanner.");
		
		
		/***************************************************************************************************
		 * We could try either to load data from URL or from local directory:
		 *************************************************************************************************** 
		// gd44/public_html/yulong_output_926.txt
		//url: http://alchemy.duhs.duke.edu/~gd44/yulong_output_926.txt
		
		URL url = new URL("http://alchemy.duhs.duke.edu/~gd44/yulong_output_926.txt");
		InputStream in = url.openStream();
		
		Scanner BowtieReader = new Scanner( in ); //new File("D:/2014FallRotation/data/yulong_output_926.txt"));
		*
		*
		*************************************************************************************************************/
		
		
		//input the chromatin name: chr2L, chr2R, chr3L, chr3R and chrX.
		System.out.print("Please input the chromosome you want to extract: [2L, 2R, 3L, 3R or X] \n chromosome = ");
		
		Scanner input = new Scanner(System.in);
		String chromosome = input.next();
		
		
		//input the document name of bowtie_output document: DM456, DM457 ----- DM463;
		System.out.print("Please input the routine of bowtie_output.txt document: [D:/2014FallRotation/data/yulong/] \n directory = ");
		String routine = input.next();
		
		System.out.print("Please input the document name of bowtie_output.txt: [DM456---DM363] \n document name = ");
		String document = input.next();
		
		System.out.print("Please input the date of this operation: [1003] \n date = ");
		String date = input.next();
		System.out.println();
		
		/******************************************************************************************/
		//2nd, read the first line of bowtie_output data line by line;
		//create an output_writer for bowtie2BED document;
		
		System.out.println("Module 2: read-in bowtie_output and create bowtie2BED writer.");
		
		//scan read in bowtie_output file;
		Scanner BowtieReader = new Scanner(new File(routine+ document +"_bowtie_output.txt"));
		
		
		String Fstline = BowtieReader.nextLine();
		System.out.println("First line of bowtie_output: " + Fstline);
		
		File output_file = new File(routine + document +"_bowtie2BED_" + chromosome +"_"+ date +".txt");
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));
		
		//track name="Kc-Early" description="Kc-Early" visibility=1 color=0,100,0
		output.write("track name= chr" +chromosome + " description=" + chromosome + " visibility=1 color=255,0,0" +"\n");
		
		System.out.println();
		
		
		/******************************************************************************************/
		//3rd, read the bowtie output data line by line;
		
		System.out.println("Module 3: read-in bowtie_output data line by line.");
		System.out.println("Create Chromosome objects base on the data read in from bowtie_output.");
		System.out.println("Write Chromosome information into bowtie2BED document.");
		
		while(BowtieReader.hasNextLine()){
			
			String Line = BowtieReader.nextLine();
			Chromosome currChromosome = GetChromosomeInfo(Line);
			
		//	System.out.println(" " + currChromosome.Chromo_name +" ;");
		//Check if the Chromosome's name is equal to "2L", if yes, save related info into output file;	
			if(currChromosome.Chromo_name.equals(chromosome)){
				
				output.write("chr"+chromosome +"\t" + currChromosome.Position +"\t" + (currChromosome.Position+49) +"\n");
			}
			
		}//end of while() loop;
		
		System.out.println();
		
		/******************************************************************************************/
		//4th, close all readers scanners and outputers.
		System.out.println("Module 4: close all scanners inputers and output writers.");
		input.close();
		BowtieReader.close();
		output.close();
		
		
		System.out.println("Done!!");
		System.out.println("The output file could be find at: ");
		System.out.println(routine + document +"_bowtie2BED_" + chromosome +"_"+ date +".txt");
		
		
	}//end main();
	
	
	/*********
	 * From each line, split the name part which is seperated with a single space " ";
	 * the second part include Strand, Chromosome and Position information;
	 * 
	 * @param line
	 * @return
	 */
	private static Chromosome GetChromosomeInfo(String line) {
		// TODO Auto-generated method stub
		
		
		String[] splitLine = line.split(" ");
		
		line = splitLine[1]; 	//this will elimite the first name string;
		
		String[] infoLine = line.split("\t"); //split the second part of the original line;
		
		
		String chroName = infoLine[2];
		long Position = Long.parseLong(infoLine[3]);
		//int seqLength = infoLine[5].length();
		
		
		//according to the strand position (+/-), calculate the chromatin position:
		if(infoLine[1].equals("+")) Position += 1;
		else Position = Position -50 +1;
		
		//Create a Chromosome object, when return the Chromosome object, the chromosome and the position
		//could be returned at the same time.
		Chromosome currChro = new Chromosome();
		currChro.Chromo_name = chroName;
		currChro.Position = Position;
		
		
		//use a println line to check if the infoLine was split correctly;
		//System.out.println("Strand: " + infoLine[1] +",  Chromosome: " + infoLine[2] + ",  Position: " + infoLine[3] +" -" + infoLine[4] + ". ");
				
		
		return currChro;
	}//end of CalCoordinate() method;
	
	
	
}//end of everything in Build_BED_file_from_Bowtie_output;
