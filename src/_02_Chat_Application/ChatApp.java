package _02_Chat_Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatApp {
	boolean working = true;
	public static void main(String[] args) {
			ChatApp app = new ChatApp();
			app.run();
	}
	
	public void run() {
		String setting = JOptionPane.showInputDialog("Are you a client or server (c or s)");
		if(setting.equalsIgnoreCase("c")) {
			client();
		}
		else {
			server();
		}
	}
	
	public void client() {
		System.out.println("Client");
		String ip = JOptionPane.showInputDialog("Server IP");
		int port = Integer.parseInt(JOptionPane.showInputDialog("Port #"));
		
		System.out.println("Connected!");
		
		try {
			Socket socket = new Socket(ip, port);
			
			Thread writer = new Thread(() -> write(socket, "Client"));
			
			Thread reader = new Thread(() -> recieve(socket));
			
			writer.start();
			reader.start();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void server() {
		try {
		System.out.println("Server");
		ServerSocket server = new ServerSocket(8080);
		
		System.out.println("Connecting...");
		
		Socket socket = server.accept();
		
		System.out.println("Connected");
		
		Thread writer = new Thread(() -> write(socket, "Server"));
			
		Thread reader = new Thread(() -> recieve(socket));
			
			writer.start();
			reader.start();
		while(working) {
			try {
				Thread.sleep(10);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		server.close();
		System.exit(0);
		
	} catch (IOException e) {
		e.printStackTrace();
	}
		
	}
	
	public void write(Socket socket, String thing) {
		while(working) {
			try {
				DataOutputStream output = new DataOutputStream(socket.getOutputStream());
				String message = JOptionPane.showInputDialog(thing + " message");
				output.writeUTF(message);
				System.out.println("You responded with " + message);
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
	}
	
	public void recieve(Socket socket) {
		while(working) {
			try {
				DataInputStream input = new DataInputStream(socket.getInputStream());
				String message = input.readUTF();
				System.out.println("They responded with " + message);
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
	}
	}

