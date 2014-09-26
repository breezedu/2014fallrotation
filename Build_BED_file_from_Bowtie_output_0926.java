package rotation2014fall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
 * @author Jeff
 *
 */
public class Build_BED_file_from_Bowtie_output_0926 {
	
	public static void main(String[] args) throws IOException{
				
		System.out.println("Build BED format file from Bowtie output file.");
		String chromosome = "2L";
		
		//1st, read-in data from Bowtie output file in the alchemy.duhs.duke.edu directory:
		// gd44/public_html/yulong_output_926.txt
		//url: http://alchemy.duhs.duke.edu/~gd44/yulong_output_926.txt
		
		URL url = new URL("http://alchemy.duhs.duke.edu/~gd44/yulong_output_926.txt");
		InputStream in = url.openStream();
		
		Scanner BowtieReader = new Scanner( in ); //new File("D:/2014FallRotation/data/yulong_output_926.txt"));
		Scanner input = new Scanner(System.in);
		System.out.print("Please input the chromosome you want to extract: \n chromosome = ");
		chromosome = input.next();
		
		
		String Fstline = BowtieReader.nextLine();
		System.out.println("1: " + Fstline);
		
		File output_file = new File("D:/2014FallRotation/data/BED_yulong_926_" + chromosome + "_output.txt");
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));
		
		//track name="Kc-Early" description="Kc-Early" visibility=1 color=0,100,0
		output.write("track name= chr" +chromosome + " description=" + "Chr" + chromosome + " visibility=1 color=255,0,0" +"\n");
				
		
		//2nd, read the bowtie output data line by line;
		while(BowtieReader.hasNextLine()){
			
			String Line = BowtieReader.nextLine();
			Chromosome currChromosome = GetChromosomeInfo(Line);
			
		//	System.out.println(" " + currChromosome.Chromo_name +" ;");
		//Check if the Chromosome's name is equal to "2L", if yes, save related info into output file;	
			if(currChromosome.Chromo_name.equals(chromosome)){
				output.write("chr"+chromosome +"\t" + currChromosome.Position +"\t" + (currChromosome.Position+49) +"\n");
			}
			
		}//end of while() loop;
		
		input.close();
		BowtieReader.close();
		output.close();
		
		
		System.out.println("Done!!");
		
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
