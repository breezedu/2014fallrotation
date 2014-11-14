package rotation2014fall;

public class ratioLog2 {
	

	private String chromosome;
	private int pos;
	private double ratio;
	
	public ratioLog2(String chromosome, int pos, double ratio){
		super();
		this.chromosome = chromosome;
		this.pos = pos;
		this.ratio = ratio;
		
	}
	
	public void setChromosome(String chromosome){
		this.chromosome = chromosome;
	}
	
	public String getChromosome(){
		return this.chromosome;
	}
	
	public void setPos(int pos){
		this.pos = pos;
	}
	
	public int getPos(){
		return this.pos;
	}
	
	public void setRatio(double ratio){
		this.ratio = ratio;
	}
	
	public double getRatio(){
		return this.ratio;
	}


}
