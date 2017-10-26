import java.util.Random;

public class SpellCheckClient extends Thread{
	private static final int REQUEST_NUM = 100;
	private static final int STRING_MAX_LENGTH = 20;
	
	private String getRandomString() {
		String SALTCHARS = "abcdefghijklmnopqrstuvwxyz";
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
	
	public void run() {
		for (int i = 0; i < REQUEST_NUM; i++) {
			String word = getRandomString();
			System.out.println("Request: " + word);
			ServiceRequest request = new ServiceRequest(word);
			ServiceResponse response = new ServiceResponse();
			SpellService.service(request, response);
			response.print();
		}
	}	
}

