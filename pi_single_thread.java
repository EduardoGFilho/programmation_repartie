package fr.esisar;

public class pi_single_thread {

	public static Double piSeries(Integer N)
	{
		Double buffer = 0.0;
		
		for(int i = 1; i<N; i++)
		{
			Double tmp = (double) i;
			buffer += 1.0/(tmp*tmp);
		}
		
		Double pi = Math.sqrt(buffer*6);
		return pi;
	}
	
	  public static void main(String[] args) 
	  {
		  Integer N =  2_000_000_000;
		  
		  long start = System.currentTimeMillis();
		  Double pi = piSeries(N);
		  long stop = System.currentTimeMillis();
		  System.out.println("Elapsed Time = "+(stop-start)+" ms");
		  
		  System.out.println("Approximation of pi = " + pi + " ; with N = " + N);
	  }
	
}
