package edu.seg2105.edu.server.backend;


import edu.seg2105.client.common.ChatIF;

import java.util.Scanner;

public class ServerConsole implements ChatIF{

	// Class variables
	/**
	 * Default port the server listens to
	 */
	
	final public static int DEFAULT_PORT= 5555;
	
	
	//Instance variables 
	EchoServer server;
	Scanner fromConsole;
	
	
	//Constructor
	public ServerConsole(int port) {
		server= new EchoServer(port,this);
		
		fromConsole= new Scanner (System.in);
	}
	
	@Override
	public void display(String message) {
		// TODO Auto-generated method stub
		
		System.out.println(">" + message);
		
	}
	
	/**
	   * This method waits for input from the console.  Once it is 
	   * received, it sends it to the client's message handler.
	   */
	
	
	public void accept() {
		
		try {
			String message;
			while (true) {
				message = fromConsole.nextLine();
				server.handleMessagefromServerUI(message);
			}
		}
		
		catch (Exception e) {
			
			System.out.println("Error while trying to read from Sever Console!");
		}
	}
	
	/**
	   * This method is responsible for the creation of the Client UI.
	   *
	   * @param args[0] The port number to listen to. 5555 as as DEFAULT value 
	   *
	   */
	
	public static void main (String[] args) {
		
		int port= 0; //port the server listens to
		
		try {
			port= Integer.parseInt(args[0]);
		}
		
		catch (Throwable e) {
			port= 5555;
		}
		
		ServerConsole sv= new ServerConsole(port);
		try {
			sv.server.listen();
		}
		catch (Exception e) {
			System.out.println("ERROR - Could not listen for clients !");
		}
		sv.accept();
	}

	
}
