package rotation2014fall;

public class STA11_homework8 {
	
	public static void main(String[] args){
		
		double[] data = {0.95, 0.85, 0.92, 0.95, 0.93, 0.86, 1.00, 0.92, 0.85, 0.81,
						 0.78, 0.93, 0.93, 1.05, 0.93, 1.06, 1.06, 0.96, 0.81, 0.96 };
		
		int len = data.length;
		
		double total = 0;
		
		for(int i=0; i<len; i++){
			total += data[i];
		}
		
		System.out.println("There are " + len +" elements. \n" + "total = " + total + ".");
		
		double average = total / len;
		
		System.out.println("average = " + average);
		
		
		//calculate Deta_prime = Sum(Xi - Average)^2 / (n-1) then squre
		
		double Sn_Square = 0;
		
		for(int i=0; i<len; i++){
			
			Sn_Square += (data[i] - average)*(data[i] - average);
		}
		
		double Sigma_prime = Math.sqrt( Sn_Square/(len-1) );
		
		double constant = 2.093; //TextBook, P860;
		
		System.out.println("Sn-Square = " + Sn_Square +", Sigma_prime = " + Sigma_prime +", c/n^0.5 = " + constant/Math.sqrt(20));
		
		double A = average - constant * Sigma_prime /Math.sqrt(20);
		double B = average + constant * Sigma_prime /Math.sqrt(20);
		
		System.out.println("A = " + A +", B = " + B +". ");
		
	}

}
