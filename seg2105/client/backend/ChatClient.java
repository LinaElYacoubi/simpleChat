// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package edu.seg2105.client.backend;

import ocsf.client.*;

import java.io.*;

import edu.seg2105.client.common.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
    
    
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    try
    {
      if (message.startsWith("#")) {
    	  clientCommands(message);
      }
      else {
    	  sendToServer(message);
      }
    }
    
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  
  
  
  private void clientCommands(String command) {
	  
	  if(command.equals("#quit")) {
		  clientUI.display("Client will quit !");;
		  quit();
		  
	  }
	  
	  else if (command.equals("#logoff")) {
		  clientUI.display("Client will log off !");
		  try {
			  if (!this.isConnected()) {
				  clientUI.display("Client already logged off !");
				  
			  }
			  
			  else {
				  closeConnection();
				  
			  }
			  
			  
		  }
		  
		  catch(IOException e){
			  clientUI.display("Error closing connection");
		  }
		  
	  }
	  
	  else if (command.equals("#sethost")) {
		  clientUI.display("Client is currently setting host name");
		  
			  if (this.isConnected()) {
				  clientUI.display("Cannot change host name because the Clietn is currently connected !");
				  
			  }
			  
			  else {
				  String hostName= "";
				  
				  for (int i=9; i<command.length();i++) { // i=9 because #setHost plus space make it 9
					  hostName+=command.charAt(i);
				  }
				  
				  setHost(hostName);
				  clientUI.display("Host name has been set up to"+ hostName);
			  }
			  
		  
		 
	  }
	  
	  else if (command.equals("#setport")) {
		  clientUI.display("Client is currently setting a port !");
		  
		  if(this.isConnected()) {
			  clientUI.display("Cannot set up a port because the Client is currently logged in ");
		  }
		  
		  else {
			  String portNumber=""; // i=9 because #setPort plus space make it 9
			  
			  for (int i=0;i<command.length();i++) {
				  portNumber+= command.charAt(i);
			  }
			  
			  try{
				  setPort(Integer.parseInt(portNumber));
				  clientUI.display("Port number has been set up to "+ portNumber);

			  }
			  catch(NumberFormatException e) {
				  clientUI.display("Invalid number");
			  }
			  
			  
			  
			  
			  
			  
		  }
		  
	  }
	  
	  else if (command.equals("#login")) {
		  clientUI.display("Client will log in");
		  try {
			  
			  if(this.isConnected()) {
				  clientUI.display("Client is already logged in !");
				  
			  }
			  
			  else {
				  openConnection();
			  }
		}
		  
		  catch(IOException e) {
			  clientUI.display("Error opening connection !");
		  }
		  
	  }
	  
	  else if (command.equals("#gethost")) {
		  clientUI.display("Host number is " + getHost());
		
	  }
	  
	  else if (command.equals("#getport")) {
		  clientUI.display("The port number is "+ getPort());
		  
	  }
	  
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  
  

	/**
	 * Hook method called each time an exception is thrown by the client's
	 * thread that is waiting for messages from the server. The method may be
	 * overridden by subclasses.
	 * 
	 * @param exception
	 *            the exception raised.
	 */
  
  @Override
  protected void connectionException(Exception exception) {
	  clientUI.display("The server has shut down !");
	  System.exit(0);
  }
  
  
  /**
	 * Hook method called after the connection has been closed. The default
	 * implementation does nothing. The method may be overriden by subclasses to
	 * perform special processing such as cleaning up and terminating, or
	 * attempting to reconnect.
	 */
  @Override
  protected void connectionClosed() {
	  clientUI.display("Connection Closed to the Server.");
	  
  }
}
//End of ChatClient class
