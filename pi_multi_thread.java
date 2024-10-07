package fr.esisar;

public class pi_multi_thread {

	public static Double piSeriesMulti(Integer N, Integer nThreads) throws InterruptedException
	{
		// TODO: verify if I need a check for the cases with too many threads or with a single one
		
		Integer nTerms = (int) Math.floor((1.0*N)/nThreads);		
		
		SeriesCalculator arrOfCalculators[] = new SeriesCalculator[nThreads];
		SeriesCalculator.setBuffer(0.0);
		
		for(Integer i = 0; i < (nThreads - 1); i++)
		{
			arrOfCalculators[i] = new SeriesCalculator(i*nTerms + 1, (i+1)*nTerms + 1);
		}
		
		arrOfCalculators[nThreads - 1] = new SeriesCalculator(nTerms * (nThreads - 1) + 1, N + 1);
		
		for (Integer i = 0; i < nThreads; i++)
		{
			arrOfCalculators[i].start();
		}
		
		for (Integer i = 0; i < nThreads; i++)
		{
			arrOfCalculators[i].join();
		}
		
		Double buf = SeriesCalculator.getBuffer();
		Double pi = Math.sqrt(6*buf);
		
		return pi;
	}
	
	  public static void main(String[] args) throws InterruptedException 
	  {
		  Integer N =  2000000000;
		  Integer nThreads = 32;
		  
		  long start = System.currentTimeMillis();
		  Double pi = piSeriesMulti(N,nThreads);
		  long stop = System.currentTimeMillis();
		  System.out.println("Elapsed Time = "+(stop-start)+" ms");
		  
		  System.out.println("Approximation of pi = " + pi + " ; with N = " + N);
	  }
	
}
