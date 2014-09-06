package rotation2014fall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/*********
 * try to create the output file that extracts the end coordinates of the reads 
 * from the alignment file, alignment file: my_bowtie_output.txt
 * 
 * output file: DM454_bowtie_output_coordinates.csv or txt they are the same;
 * 
 * @author Jeff
 *
 */
public class Extract_End_Coordinates {
	
	public static void main(String[] args) throws IOException{
		

		//1st, read_in data line by line from my_bowtie_output.txt document
		Scanner CoorReader = new Scanner(new File("D:/2014FallRotation/data/my_bowtie_output.txt"));
		
		File output_file = new File("D:/2014FallRotation/data/DM454_bowtie_output_coordinates.txt");
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));
		
		output.write("chromosome,read_start_coordinate,read_end_coordinate,read_width" + "\n");
		
		while(CoorReader.hasNextLine()){
			
			//Read_In two line together;
			String linePositive = CoorReader.nextLine();
			String lineNegitative = CoorReader.nextLine();
			//	System.out.println(line);
			
			
			//create 2 Chromo objects, one with start 
			Chromo start_Chro = CalCoordinate(linePositive);
			Chromo end_Chro = CalCoordinate(lineNegitative);
			long range = end_Chro.Position - start_Chro.Position; //we do not need to +1 here;
			
			output.write(start_Chro.Chromosome +"," +start_Chro.Position + "," + end_Chro.Position + "," + range + "  \n");
			
		}//end while() loop;
		
		
		//close the Scanner;
		CoorReader.close();
		output.close();
		
	}//end main()

	
	/*********
	 * From each line, split the name part which is seperated with a single space " ";
	 * the second part include Strand, Chromosome and Position information;
	 * 
	 * @param line
	 * @return
	 */
	private static Chromo CalCoordinate(String line) {
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
		
		//calculate the Position;
		if(pivot == 1) Pos = (Pos+1);
		else Pos = Pos + 25 -1 +1;
		
		//Create a Chromosome object, when return the Chromosome object, the chromosome and the position
		//could be returned at the same time.
		Chromo currChro = new Chromo();
		currChro.Chromosome = Chromosome;
		currChro.Position = Pos;
		
		return currChro;
	}//end of CalCoordinate() method;
	
	
}//end of everything in Extrct_End_Coordinates class;


/*****
 * Just a simple object to record the Chromosome name and relative position;
 * @author Jeff
 *
 */
class Chromo{
	
	String Chromosome;
	long Position;
	
}//end of Chromo class;
