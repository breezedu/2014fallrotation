package rotation2014fall;

public class lateRegion {
	
	private String chromosome;
	private int start;
	private int end;
	private boolean H2Avs;
	
	public lateRegion(String chromosome, int start, int end){
		super();
		
		this.chromosome = chromosome;
		this.start = start;
		this.end = end;
		this.H2Avs = false;
		
	}
	
	
	public void setChromosome(String chromosome){
		this.chromosome = chromosome;
	}
	
	public String getChromosome(){
		return this.chromosome;
	}
	
	public void setStart(int start){
		this.start = start;
	}
	
	public int getStart(){
		return this.start;
	}
	
	public void setEnd(int end){
		this.end = end;
	}
	
	public int getEnd(){
		return this.end;
	}
	
	public void setH2Avs(boolean H2Avs){
		this.H2Avs = H2Avs;
	}
	
	public boolean getH2Avs(){
		return this.H2Avs;
	}
}
