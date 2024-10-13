package fr.esisar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


//Wait for connect to server, fetch packages, put them together

public class ClientTCPFileTransfer
{

    public static void main(String[] args) throws Exception
    {
    	ClientTCPFileTransfer clientTCP = new ClientTCPFileTransfer();
        clientTCP.execute();                
    }

    private void execute() throws IOException
    {
    	// Output file to be received over TCP
        File outputPath = new File(System.getProperty("user.home"),"Documents/highs_certificate_english_copy.pdf");
        Integer bufSize = 10_000;
        int port = 4251;
    	
        System.out.println("Initializing Client ...");

        // Creation of the socket
        Socket socket = new Socket();

        // Connection to the server
        InetSocketAddress adrDest = new InetSocketAddress("127.0.0.1", port);
        socket.connect(adrDest);        

//        // Sending of request
//        byte[] bufE = new String("### HELLO TCP ###").getBytes();
//        OutputStream os = socket.getOutputStream();
//        os.write(bufE);
//        System.out.println("Message envoye");

        // Wait for the server to send in the data
        byte[] bufR = new byte[bufSize];
        InputStream is = socket.getInputStream();
        int lenBufR = is.read(bufR);
        
        FileOutputStream fos = new FileOutputStream(outputPath);

    	System.out.println("Receiving a file through TCP ...");
        if (lenBufR!=-1)
        {
        	
            fos.write(bufR,0,lenBufR);
        }
        
        System.out.println("End of transfer.");
        fos.close();

        // Stopping of the socket
        socket.close();
        System.out.println("Stopping of client .");
    }
}
