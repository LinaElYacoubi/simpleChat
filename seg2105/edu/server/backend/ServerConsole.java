package edu.seg2105.edu.server.backend;

import java.util.Scanner;

import common.ChatIF;



/**
 * Class responsible of displaying message on the Server Console
 * */
 
public class ServerConsole implements ChatIF {

	
	
	/**
	 * The port the server listens to
	 */
	
	final public static int DEFAULT_PORT= 5555;
	
	//Instance variables
	Echoserver server;
	Scanner fromConsole;
	
	
	public ServerConsole(int port) {
		server= new EchoSever(port, this);
		
		fromConsole= new Scanner (System.in);
		
	}
	
	@Override
	public void display(String message) {
		// TODO Auto-generated method stub
		
		
	}

	
}
