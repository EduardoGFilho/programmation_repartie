package fr.esisar;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


// Wait for connection, partition file, send through TCP

public class ServerTCPFileTransferChoice
{

    public static void main(String[] args) throws Exception
    {
        ServerTCPFileTransferChoice serverTCP = new ServerTCPFileTransferChoice();
        serverTCP.execute();

    }


    private void execute() throws Exception
    {
    	
    	// I hate windows why can't this be simpler??
    	// Path to file to be sent over TCP
        //File inputPath = new File(System.getProperty("user.home"),"Documents/highs_certificate_english.pdf");
        Integer bufSize = 10_000;
        int port = 4251;

        //
        System.out.println("Initializing Server ...");

        // The server declares itself to the transport layer
        ServerSocket socketListen = new ServerSocket();
        socketListen.bind(new InetSocketAddress(port));
        
//        // Open file to be read
//        FileInputStream fis = new FileInputStream(inputPath);


        // Waiting for the connection of a client
        System.out.println("Waiting for the connection of a client ...");
        Socket socketConnection = socketListen.accept();

        // Display of the port and ip of the client
        System.out.println("A client is connected");
        System.out.println("IP:"+socketConnection.getInetAddress());
        System.out.println("Port:"+socketConnection.getPort());
         
        byte[] bufR = new byte[2048];
      	InputStream is = socketConnection.getInputStream();
        int lenBufR = is.read(bufR);
        
        // This could lead to an error if the buffer is empty...too bad
        String message = new String(bufR, 0 , lenBufR);
        
    	File inputPath = new File(message);
    	FileInputStream fis = new FileInputStream(inputPath);
        
        
        byte[] buf = new byte[bufSize];
        int len = fis.read(buf);

    	System.out.println("Sending \"" + inputPath + "\" through TCP ...");

        while(len!=-1)
        {
        	
			OutputStream os = socketConnection.getOutputStream();
			os.write(buf,0,len);

            len = fis.read(buf);
            
        }
        System.out.println("End of transfer.");
        fis.close();

        // Closing of the connection socket
        socketConnection.close();


        // Stopping of server
        socketListen.close();
        System.out.println("Stopping of server.");
    }

}
