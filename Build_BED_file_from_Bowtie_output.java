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
 * @author Jeff
 *
 */
public class Build_BED_file_from_Bowtie_output {
	
	public static void main(String[] args) throws IOException{
				
		System.out.println("Build BED format file from Bowtie output file.");
		
		//1st, read-in data from Bowtie output file: D:/2014FallRotation/data/bowtie_output_DM433.txt
		Scanner BowtieReader = new Scanner(new File("D:/2014FallRotation/data/bowtie_output_DM433.txt"));
		
		String Fstline = BowtieReader.nextLine();
		System.out.println("1: " + Fstline);
		
		File output_file = new File("D:/2014FallRotation/data/BED_DM433_2L_output_t_916.txt");
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));
		
		//track name="Kc-Early" description="Kc-Early" visibility=1 color=0,100,0
		output.write("track name= chr2L" + " description=" + "Chr2L" + " visibility=1 color=255,0,0" +"\n");
				
		
		//2nd, read the bowtie output data line by line;
		while(BowtieReader.hasNextLine()){
			
			String Line = BowtieReader.nextLine();
			Chromosome currChromosome = GetChromosomeInfo(Line);
			
		//	System.out.println(" " + currChromosome.Chromo_name +" ;");
		//Check if the Chromosome's name is equal to "2L", if yes, save related info into output file;	
			if(currChromosome.Chromo_name.equals("2L")){
				output.write("chr2L" +"\t" + currChromosome.Position +"\t" + (currChromosome.Position+49) +"\n");
			}
			
		}//end of while() loop;
		
		BowtieReader.close();
		output.close();
		
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
		
		line = splitLine[1];
		
		int pivot = 0; //to record whether it's a + or - strand;
		String Chromosome = "";
		String Position = "";
		
		int i=0;
		
		//till charAt(i) reach '+' or '-';
		while(line.charAt(i)!='+' && line.charAt(i)!='-')
			i++;
		
		if(line.charAt(i) == '+') pivot = 1;
		else pivot = -1;
		
		int j=i+1;
		//till charAt(j) reach next number;
		while(line.charAt(j)<48 || line.charAt(j)>57) j++; 	//ASCII code between 48-57 are numbers 0-9;
		
		//get the first number after +/-; this is the Chromosome ##
		while(line.charAt(j)>47 && line.charAt(j)<58) {
			Chromosome += line.charAt(j);
			j++;
		}
		
		//add another letter to Chromosome name, because here we have 2R, 2L different chromosomes;
		Chromosome += line.charAt(j);	
		
		
		//till charAt(j) reach next number, which will be the position;
		while(line.charAt(j)<48 || line.charAt(j)>57) j++; 	
			
		//record the position, here the position is String, when calculating the relative position
		//we have to transfer String into Long;
		while(line.charAt(j)>47 && line.charAt(j)<58) {
			Position += line.charAt(j);
			j++;
		}
		
	//	System.out.println("Strand: " + pivot +",  Chromosome: " + Chromosome + ",  Position: " + Position +". ");
		
		//transfer String into Long
		long Pos = Long.parseLong(Position);
		
		
		//calculate the Start Position;
		if(pivot == 1) Pos = (Pos+1);
		else Pos = Pos - 50 + 1;
		
		//Create a Chromosome object, when return the Chromosome object, the chromosome and the position
		//could be returned at the same time.
		Chromosome currChro = new Chromosome();
		currChro.Chromo_name = Chromosome;
		currChro.Position = Pos;
		
		return currChro;
	}//end of CalCoordinate() method;
	
	
	
}//end of everything in Build_BED_file_from_Bowtie_output;
