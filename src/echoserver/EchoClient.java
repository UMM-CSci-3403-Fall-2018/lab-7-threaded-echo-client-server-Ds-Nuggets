package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.lang.*;


public class EchoClient {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient();

		client.start();

	}


	private void start() throws IOException {
		// Assigns the Socket to the localhost and portnumber address.
		Socket socket = new Socket("localhost", PORT_NUMBER);
		//Creates a thread with a KeyboardReader Runnable
		Thread firstthead = new Thread(new KeyboardReader(socket));
		//Creates a thread with a ScreenWriter Runnable
    Thread secondthread = new Thread(new ScreenWriter(socket));
		//Starts First Thread
		firstthead.start();
		//Starts Second Thread
		secondthread.start();
	}


	//KeyboardReader implements the Runnable because it will be implemented in a Thread
	public class KeyboardReader implements Runnable {
	    Socket s;

	    public KeyboardReader(Socket s){
	        this.s = s;
        }

		public void run(){
		    int readByte;
				try {
                    OutputStream socketOutputStream = s.getOutputStream();
                    while ((readByte = System.in.read()) != -1) {
                        socketOutputStream.write(readByte);
                    }
										s.shutdownOutput();
                } catch (IOException e) {
									System.err.println("Reached maximum amount of threads.");
									System.exit(1);
                }
		}
	}


	//ScreenWriter implements the Runnable because it will be implemented in a Thread
	public class ScreenWriter implements Runnable {
        Socket s;

        public ScreenWriter(Socket s) {
            this.s = s;
        }

        public void run() {
            int readByte;
            try {
                InputStream socketInputStream = s.getInputStream();
                while ((readByte = socketInputStream.read()) != -1) {
                    System.out.write(readByte);
                }
            } catch (IOException e) {
							System.err.println("Reached maximum amount of threads.");
            } finally {
                System.out.flush();
            }
        }
    }

}
