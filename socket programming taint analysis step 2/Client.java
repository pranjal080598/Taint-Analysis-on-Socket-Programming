

// A Java program for a Client
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client
{
    // initialize socket and input output streams
    private Socket socket		 = null;
    private DataInputStream input = null;
    private DataOutputStream out	 = null;

    // constructor to put ip address and port
    public Client(String address, int port)
    {
        // establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            input = new DataInputStream(System.in);

            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }

        // string to read message from input
        String line = "";

        // keep reading until "Over" is input
        while (!line.equals("Over"))
        {
            try
            {
                line = input.readLine();
                out.writeUTF(line);
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }

        // close the connection
        try
        {
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static void main(String args[])
    {
      // regex to validate the port number
        String regex = "\\d+";
      // Create a Scanner object
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter port number to establish connection");
        // Read user input
        String port_number = myObj.next();
        // sanitize port number using regex
        // remove  this for output with Taint
        // if (port_number.matches(regex))
        // {
        //     int port_number_int = Integer.parseInt(port_number);
        //     Client client = new Client("127.0.0.1", port_number_int);
        // }

        // comment this code incase of sanitization
        int port_number_int = Integer.parseInt(port_number);
        Client client = new Client("127.0.0.1", port_number_int);
    }
}
