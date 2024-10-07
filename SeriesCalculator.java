package fr.esisar;

public class SeriesCalculator extends Thread {
	
	Integer first;
	Integer last;
	
	static Double buffer;
	
	public SeriesCalculator(Integer first, Integer last)
	{
		this.first =  first;
		this.last = last;
	}
	
	public void run()
	{
		
		for (int i = first; i<last; i++)
		{
			Double tmp = (double) i;
			buffer += 1.0/(tmp*tmp);
		}
	}
	
	public static void setBuffer(Double buf)
	{
		SeriesCalculator.buffer = buf;
	}
	public static Double getBuffer()
	{
		return SeriesCalculator.buffer;
	}

}
