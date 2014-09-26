package rotation2014fall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**************
 * Combine all chromatins, chr2L, chr2R, chr3L, chr3R and chrX into one TXT document;
 * D:/2014Fallrotation/BED_DM433_***_output_t_922.txt documents;
 * BED_DM433_2L_output_t_922.txt
 * BED_DM433_2R_output_t_922.txt
 * BED_DM433_3L_output_t_922.txt
 * BED_DM433_3R_output_t_922.txt
 * BED_DM433_X_output_t_922.txt
 * 
 * @author Jeff
 *
 */

public class CombineChr2L2R3L3RandX926 {
	
	public static void main(String[] args) throws IOException{
		
		System.out.println("This script will combine all BED_DM433_***_output_t_922.txt documents together.");
		
		
		//1st, create Scanners;
		Scanner read_2L = new Scanner(new File("D:/2014FallRotation/data/BED_yulong_926_2L_output.txt"));
		Scanner read_2R = new Scanner(new File("D:/2014FallRotation/data/BED_yulong_926_2R_output.txt"));
		Scanner read_3L = new Scanner(new File("D:/2014FallRotation/data/BED_yulong_926_3L_output.txt"));
		Scanner read_3R = new Scanner(new File("D:/2014FallRotation/data/BED_yulong_926_3R_output.txt"));
		Scanner read_X = new Scanner(new File("D:/2014FallRotation/data/BED_yulong_926_X_output.txt"));
		
		
		//2nd, create an writter; 
		File output_file = new File("D:/2014FallRotation/data/BED_yulong_allChrs_output_926.txt");
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));
		
		//chr_name  start  end  read
		output.write("chr_name" + "\t" + "start_pos" + "\t" + "end_pos" +"\t" + "read_count" +"\n");
		
		
		
		//3rd, merge files;
		String firstLine = read_2L.nextLine();
		System.out.println("2L first: " + firstLine);
		
		//put everything about chr2L into chrAll document.
		while(read_2L.hasNextLine()){
			
			String currLine = read_2L.nextLine();
			output.write(currLine + "\n");
		}
		
		//about 2R
		firstLine = read_2R.nextLine();
		System.out.println("2R first: " + firstLine);
		while(read_2R.hasNextLine()){
			
			String currLine = read_2R.nextLine();
			output.write(currLine + "\n");
		}
		
		//about 3L
		firstLine = read_3L.nextLine();
		System.out.println("3L first: " + firstLine);
		while(read_3L.hasNextLine()){
			
			String currLine = read_3L.nextLine();
			output.write(currLine + "\n");
		}		
		
		
		//about 3R
		firstLine = read_3R.nextLine();
		System.out.println("3R first: " + firstLine);
		while(read_3R.hasNextLine()){
			
			String currLine = read_3R.nextLine();
			output.write(currLine + "\n");
		}		
		
		
		//about X
		firstLine = read_X.nextLine();
		System.out.println("X first: " + firstLine);
		while(read_X.hasNextLine()){
			
			String currLine = read_X.nextLine();
			output.write(currLine + "\n");
		}		
		
		System.out.println("OK, files merged!");
		
		//end, close all scanners:
		read_2L.close();
		read_2R.close();
		read_3L.close();
		read_3R.close();
		read_X.close();
		output.close();
		
	}//end of main();

}//end of everything in CombineChr2L2R3L3RandX class;
