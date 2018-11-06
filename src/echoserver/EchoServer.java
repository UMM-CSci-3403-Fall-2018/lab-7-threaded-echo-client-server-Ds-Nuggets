package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.*;

public class EchoServer {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	private void start() throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
		while (true) {
			//Connecting the serverSocket to the client socket
			Socket socket = serverSocket.accept();
			//Creates a thread with a clientHandler Runnable
			Thread t = new Thread(new ClientHandler(socket));
			//Starts Thread
			t.start();
		}
	}
	//ClientHandler implements the Runnable because it will be implemented in a Thread
	public class ClientHandler implements Runnable{
		Socket s;
		//Constructor
		public ClientHandler(Socket s){
			this.s = s;
		}
		//Recieves input from the client and echos them back. Also Shuts down the client so that it doesnt run indefinitely
		public void run(){
			try{
				InputStream inputStream = s.getInputStream();
				OutputStream outputStream = s.getOutputStream();
				int b;
				while((b = inputStream.read()) != -1){
					outputStream.write(b);
				}
				s.shutdownOutput();
			} catch (IOException e){
				System.err.println("Reached maximum amount of threads.");
			}
		}
	}
}
