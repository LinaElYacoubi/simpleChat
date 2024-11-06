package edu.seg2105.edu.server.backend;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import edu.seg2105.client.common.ChatIF;

import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF serverUI;
  
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
    System.out.println("Message received: " + msg + " from " + client);
    this.sendToAllClients(msg);
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = 5555; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
     
    }
  }
  
  
  
  
  /**
	 * Hook method called each time a new client connection is
	 * accepted. The default implementation does nothing.
	 * @param client the connection connected to the client.
	 */
  
  @Override
	protected void clientConnected(ConnectionToClient client) {
	  System.out.println("A client has just connected !");
  }

	/**
	 * Hook method called each time a client disconnects.
	 * The default implementation does nothing. The method
	 * may be overridden by subclasses but should remains synchronized.
	 *
	 * @param client the connection with the client.
	 */
	
	@Override
	synchronized protected void clientDisconnected(
		ConnectionToClient client) {
		System.out.println("A client has just disconnected !");
		
		
	}


	public void handleMessagefromServerUI(String message) {
		// TODO Auto-generated method stub
		if (message.startsWith("#")) {
			serverCommands(message);
		}
		
		else {
			sendToAllClients("SERVER MSG> "+ message);
			serverUI.display(message);
		}
	}
	
	private void serverCommands(String command) {
		// TODO Auto-generated method stub
		if(command.equals("#quit")) {
			serverUI.display("Server will shut down")
			quit();
		}
		
		else if(command.equals("#stop")) {
			serverUI.display("Sever will stop listening !");
				stopListening();
			
		}
		
		else if(command.equals("#close")) {
			serverUI.display("Sever will close !");
				try {
					close ();
				}
				catch (IOException e) {
					serverUI.display("Error closing server !");
				}
			
			
		}
		
		else if(command.startsWith("#setport")) {
			serverUI.display("Server will set the port number !");
			if (this.isListening()) {
				serverUI.display("Cannot set up the port because the server is already running !");
			}
			
			else {
				String portNumber="";
				for (int i=9; i<command.length();i++) {
					portNumber+=command.charAt(i);
				}
				try {
					setPort(Integer.parseInt(portNumber));
					serverUI.display("Port number has been set up to "+ portNumber);
						
				}
				catch(NumberFormatException nfe) {
					serverUI.display("Invalid number");
				}
			
	
		}
		}
		
		else if(command.equals("#start")) {
			serverUI.display("Server will start listening for clients !");
			if (isListening()) {
				serverUI.display("Server is already listening to clients !");
			}
			
			else {
				try {
					listen();
					 serverUI.display("Server started listening to clients !");
					
				}
				catch (IOException e) {
					serverUI.display("Could not listen to clients ! ");
				}
				 
			}
			
		}
		else if(command.equals("#getport")) {
			serverUI.display("Port number is "+ getPort());
			
		}
		
		else {
			  serverUI.display("Not a valid command !");
		  }
		
	

		
		
	}
	
	/**
	 * Terminates server
	 */


	private void quit() {
		// TODO Auto-generated method stub
		
		try {
			close();
		}
		catch(IOException e) {
			System.exit(0);
		}
		
	}
	
	/**
	 * 
	 */


	

	


	
}
//End of EchoServer class
