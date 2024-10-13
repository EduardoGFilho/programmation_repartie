package fr.esisar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


// Wait for connection, partition file, send through TCP

public class ServerTCPFileTransfer
{

    public static void main(String[] args) throws Exception
    {
        ServerTCPFileTransfer serverTCP = new ServerTCPFileTransfer();
        serverTCP.execute();

    }


    private void execute() throws IOException
    {
    	
    	// I hate windows why can't this be simpler??
    	// Path to file to be sent over TCP
        File inputPath = new File(System.getProperty("user.home"),"Documents/highs_certificate_english.pdf");
        Integer bufSize = 10_000;
        int port = 4251;

        //
        System.out.println("Initializing Server ...");

        // The server declares itself to the transport layer
        ServerSocket socketListen = new ServerSocket();
        socketListen.bind(new InetSocketAddress(port));
        
        // Open file to be read
        FileInputStream fis = new FileInputStream(inputPath);


        // Waiting for the connection of a client
        System.out.println("Waiting for the connection of a client ...");
        Socket socketConnection = socketListen.accept();

        // Display of the port and ip of the client
        System.out.println("A client is connected");
        System.out.println("IP:"+socketConnection.getInetAddress());
        System.out.println("Port:"+socketConnection.getPort());
        
        
        byte[] buf = new byte[bufSize];
        int len = fis.read(buf);

    	System.out.println("Sending \"" + inputPath + "\" through TCP ...");

        while(len!=-1)
        {
        	// Send data trough TCP
        	
        	//fos.write(buf,0,len);
        	
			OutputStream os = socketConnection.getOutputStream();
			os.write(buf,0,len);
			//System.out.println("Message envoye = ok");
        	
            //displayBufContent(buf,len);
            len = fis.read(buf);
            //System.out.println(len);
            
        }
        System.out.println("End of transfer.");
        fis.close();

        // A client has connected, the server reads the message sent by the client (no guarantee of reading the entire message)
//        byte[] bufR = new byte[2048];
//        InputStream is = socketConnexion.getInputStream();
//        int maxIt = 100;
//        
//        System.out.println("Receive messages:");
//        // Read the buffer until the socket is closed, or we have tried too much
//        for (int i = 0; i < maxIt; i++)
//        {
//            int lenBufR = is.read(bufR);
//            if (lenBufR!=-1)
//            {
//                String message = new String(bufR, 0 , lenBufR);
//                System.out.println(message);
//            }
//            else
//            {
//            	break;
//            }
//            
//        	
//        }


//        // Emission d'un message en retour
//        byte[] bufE = new String("ok").getBytes();
//        OutputStream os = socketConnexion.getOutputStream();
//        os.write(bufE);
//        System.out.println("Message envoye = ok");

        // Closing of the connection socket
        socketConnection.close();


        // Stopping of server
        socketListen.close();
        System.out.println("Stopping of server.");
    }

}
