import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Random;

public class Worker {
	private static String getRandomString() {
		String SALTCHARS = "abcdefghijklmnopqrstuvwxyz";
		final int STRING_MAX_LENGTH = 20;
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        int length = (int) (Math.random() * STRING_MAX_LENGTH) + 1;
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
	}
	
	public static void handleRequest(Socket connection) {
		try (
	            PrintWriter out =
	                new PrintWriter(connection.getOutputStream(), true);
	            BufferedReader in =
	                new BufferedReader(
	                    new InputStreamReader(connection.getInputStream()));	            
	     ) {
	            String userInput;
	            for (int i = 0; i < 100; i++) {
	            	userInput = getRandomString();
	                out.println(userInput);
	                System.out.println("echo: " + in.readLine());
	            }
	     } catch (IOException e) {
	            System.err.println("Couldn't get I/O for the connection.");
	            System.exit(1);
	     }
	}
}
