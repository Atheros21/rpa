package core;

public class Reteta {
	private String name;
	private String description;
	private int cost;

	public Reteta(String name, String description, int cost) {
		this.name = name;
		this.description = description;
		this.cost = cost;
	}
	
	public String GetName()
	{
		return name;
	}
	
	public String GetDescription()
	{
		return description;
	}
	
	public int GetCost()
	{
		return cost;
	}
}
