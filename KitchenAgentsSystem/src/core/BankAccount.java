package core;

public class BankAccount {
	private int id;
	private int pin;
	private int value;

	public BankAccount() {
	}

	public BankAccount(int id, int pin, int value) {
		this.id = id;
		this.pin = pin;
		this.value = value;
	}
	
	//Properties
	public int GetId()
	{
		return id;
	}
	
	public int GetPin()
	{
		return pin;
	}
	
	public int GetValue()
	{
		return value;
	}
	
	public void SetValue(int targetValue)
	{
		if(targetValue<0)
		{
			value = 0;
			return;
		}
		value = targetValue;	
	}
	
	@Override
	public String toString()
	{
		return String.format("id:%d,pin:%d,value:%d", id,pin,value);
	}
}
