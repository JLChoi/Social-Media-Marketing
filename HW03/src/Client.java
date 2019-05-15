import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This is the client for the socket communication.
 * @author swapneel
 *
 */
public class Client {
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	
	/**
	 * This will connect to the server on the specified port
	 * @param servername
	 * @param port
	 * 
	 */
	public Client(String servername, int port) {
		try {
			socket = new Socket(servername, port);
			System.out.println("Connected to the server!!!");
			
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream(), true);
			askUser();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnections();
		}
	}
	
	/**
	 * This method gracefully closes all open connections.
	 */
	private void closeConnections() {
		try {
			out.close();
			in.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void askUser() {
		Scanner userInput = new Scanner(System.in);
		
		while(true) {
			System.out.println("Please enter a message to send to the server");
			String message = userInput.nextLine();
			
			out.println(message);
			String serverMessage = in.nextLine();
			System.out.println(serverMessage);
		}
	}

}
