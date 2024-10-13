package fr.esisar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


//Wait for connect to server, fetch packages, put them together

public class ClientTCPFileTransferBetter
{

    public static void main(String[] args) throws Exception
    {
    	ClientTCPFileTransferBetter clientTCP = new ClientTCPFileTransferBetter();
        clientTCP.execute();                
    }

    private void execute() throws IOException
    {
    	// Output and input files
        File inputPath = new File(System.getProperty("user.home"),"Documents/test.txt");
        File outputPath = new File(System.getProperty("user.home"),"Documents/testR2.txt");
        Integer bufSize = 10_000;
        int port = 4251;
    	
        System.out.println("Initializing Client ...");

        // Creation of the socket
        Socket socket = new Socket();

        // Connection to the server
        InetSocketAddress adrDest = new InetSocketAddress("127.0.0.1", port);
        socket.connect(adrDest);        

        // Sending the path we want
        byte[] bufE = inputPath.getAbsolutePath().getBytes();
        OutputStream os = socket.getOutputStream();
        os.write(bufE);
        System.out.println("Requested to copy \"" + inputPath + "\"");

        // Wait for the size of the file
        byte[] bufR = new byte[bufSize];
        InputStream is = socket.getInputStream();
        int lenBufR = is.read(bufR);
        
        // Decode size
        String sizeStr = new String(bufR).substring(0, lenBufR);
        String sizeSubStr = sizeStr.substring(sizeStr.indexOf("size:") + 5, sizeStr.indexOf(";"));
        Long sizeLong = Long.parseLong(sizeSubStr);
        
        FileOutputStream fos = new FileOutputStream(outputPath);

    	System.out.println("Receiving \"" + inputPath + "\" through TCP ...");
    	
    	Long dataTransfered = (long) 0;
    	Float percent = (float) 0.0;
    	
    	// Begin reading actual file
    	lenBufR = is.read(bufR);
        if (lenBufR!=-1)
        {
        	dataTransfered += lenBufR;
            fos.write(bufR,0,lenBufR);
            
            percent = 100 * ((float) dataTransfered)/sizeLong;
            System.out.println(String.format("%.02f", percent) + "% Done...");
        }
        
        System.out.println("End of transfer.");
        fos.close();

        // Stopping of the socket
        socket.close();
        System.out.println("Stopping of client .");
    }
}
