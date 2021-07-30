package Model;

//Class for send information to efficiency tables
public class EfficiencyType {
	private String name;
	private double efficiency;
	
	public EfficiencyType(String name, double efficiency) {
		this.name = name;
		this.efficiency = efficiency;
	}

	public String getName() {
		return name;
	}

	public double getEfficiency() {
		return efficiency;
	}

	
	
}
