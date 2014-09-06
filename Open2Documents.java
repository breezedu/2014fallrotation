package rotation2014fall;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Open2Documents {

	public static void main(String[] args) throws FileNotFoundException{
		
		System.out.println("Readin first Data title:");
		
		Scanner Reader = new Scanner(new File("test1.txt"));
		
		String line1 = Reader.nextLine();
		
		Reader.close();
		System.out.println(line1 +"\n");		
		System.out.println("Scanner Reader1 has been closed.");
		
		Scanner Reader2 = new Scanner(new File("test2.txt"));
		String line2 = Reader2.nextLine();
		
		Reader2.close();
		
		System.out.println( line2 );
		
	}
}
