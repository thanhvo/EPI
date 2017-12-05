import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskExecutionWebServer {
	private static final int NTHREADS = 100;
	private static final int SERVERPORT = 8080;
	private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);
	
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(SERVERPORT);
		try (
				Socket clientSocket = serverSocket.accept();     
				PrintWriter out =
						new PrintWriter(clientSocket.getOutputStream(), true);                   
				BufferedReader in = new BufferedReader(
		                new InputStreamReader(clientSocket.getInputStream()));
		) {
		        String inputLine;
		        while ((inputLine = in.readLine()) != null) {
		        	out.println(inputLine);
		        }
		} catch (IOException e) {
				System.out.println("Exception caught when trying to listen on port "
		                + SERVERPORT + " or listening for a connection");
				System.out.println(e.getMessage());
		}
		while (true) {
			final Socket connection = serverSocket.accept();
			Runnable task = new Runnable() {
				public void run() {
					Worker.handleRequest(connection);
				}
			};
			exec.execute(task);			
		}
	}
}
