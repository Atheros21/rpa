package core;

public class Reteta {
	private String name;
	private String description;
	private int cost;
	private int time;
	public Reteta(String name, String description, int cost,int time) {
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.time = time;
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
	
	public int GetTime()
	{
		return time;
	}
	
	@Override
	public String toString()
	{
		return "Meniu:"+GetName()+"("+GetDescription()+"), Pret:"+GetCost()+", Timp:"+GetTime();
	}
}
