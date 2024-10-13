package fr.esisar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFileTimeMeasurement
{
    public static void main(String[] args) throws Exception
    {
        CopyFileTimeMeasurement copyFile = new CopyFileTimeMeasurement();
        copyFile.execute();
    }


    /**
     * 
     */
    private void execute() throws IOException
    {
    	
    	
    	long start = System.currentTimeMillis();

        System.out.println("Starting \"CopyFile\"...");
        
        
        String inputPath = "/home/userir/file3.txt";
        String outputPath = "/home/userir/file4.txt";
        
        FileInputStream fis = new FileInputStream(inputPath);
        FileOutputStream fos = new FileOutputStream(outputPath);

        System.out.println("Copying \"" + inputPath + "\" to \"" + outputPath + "\"...");
        
        byte[] buf = new byte[1_000_000];

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
        
        System.out.println("Finished \"CopyFile\"");
        
    	long stop = System.currentTimeMillis();
    	System.out.println("Elapsed Time = "+(stop-start)+" ms");


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
