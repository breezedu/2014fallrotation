package rotation2014fall;

import java.util.Scanner;

/*********
 * just a short script to calculate the combination;
 * 
 * @author Jeff
 *
 */
public class CombinationsCalculator {

	public static void main(String[] args){
		
		System.out.println("This is a script to calculate the Combinations.");
		
		//use scanner to read_in numbers:
		Scanner input = new Scanner(System.in);
		System.out.print("Please input the sample base:");
		int base = input.nextInt();
		
		System.out.print("Please input the number of partition:");
		int part = input.nextInt();
		
		input.close();
		
		if(part == 0) {
			
			System.out.println("There's only one probability, 1.");
		}
		
		long baseL = 1;
		long partL = 1;
		
		for(int i=1; i<=part; i++){
			partL = partL*i;
			baseL = baseL*(base-i+1);
		}
		
		float comb = baseL/partL;
		
		System.out.println("baseL=" + baseL +", partL=" + partL + ", Result: " + comb +".");
	}//end of main();
}
