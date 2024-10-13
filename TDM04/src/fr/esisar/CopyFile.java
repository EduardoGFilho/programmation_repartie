package fr.esisar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFile
{
    public static void main(String[] args) throws Exception
    {
        CopyFile copyFile = new CopyFile();
        copyFile.execute();
    }


    /**
     * 
     */
    private void execute() throws IOException
    {

        System.out.println("Starting \"CopyFileTimeMeasurement\"...");
        
        
        String inputPath = "/home/userir/file3.txt";
        String outputPath = "/home/userir/file4.txt";
        
        //FileInputStream fis = new FileInputStream(inputPath);
        //FileOutputStream fos = new FileOutputStream(outputPath);
        
        FileOutputStream fosRecordedData = new FileOutputStream("/home/userir/ElapsedTimes.txt");
        
        Integer[] bufSizes = {10,100,1000,10_000,1_000_000};
        //Integer[] bufSizes = {100,1000,10_000,1_000_000};

        Long[] timeSpent = new Long[bufSizes.length];
        

        for (int i = 0; i < bufSizes.length; i++)
        {
        	// Always overwrite
            FileInputStream fis = new FileInputStream(inputPath);
        	FileOutputStream fos = new FileOutputStream(outputPath);
        	
            System.out.println("Copying \"" + inputPath + "\" to \"" + outputPath + "\"; bufSize =" + bufSizes[i] + "...");

        	long start = System.currentTimeMillis();
        	
            byte[] buf = new byte[bufSizes[i]];

            int len = fis.read(buf);
            
            while(len!=-1)
            {
            	fos.write(buf,0,len);
                //displayBufContent(buf,len);
                len = fis.read(buf);
                //System.out.println(len);
                
                
            }
            
            fis.close();
            fos.close();
            
            //System.out.println("Finished \"CopyFile\"");
            
            // Measure time and record to a file
        	long stop = System.currentTimeMillis();
        	timeSpent[i] = stop-start;
        	
        	fosRecordedData.write(new String(bufSizes[i]+ ":"+ (stop-start) + "; ").getBytes());
        	
        	System.out.println("Elapsed Time = "+(stop-start)+" ms");
        	
        	
        }	
        
        
        //fis.close();
        fosRecordedData.close();
        
        
        


    }
    
    private void displayBufContent(byte[] buf, int len) 
    {
        System.out.println("len="+len);
        for (int i = 0; i < len; i++) 
        {
            System.out.println("Caractère lu : "+buf[i]);
        }

    }
}
