
public class ServiceResponse {
	private String[] wordList;
	
	public ServiceResponse() {
		wordList = null;
	}
	
	public void encodeIntoResponse(String[] wordList) {
		this.wordList = wordList;
	}
	
	public void print() {
		if (wordList == null) {
			System.out.println("The word is in the dictionary. No suggestion.");
		} else {
			System.out.print("Suggesitons: ");
			for (String word: wordList) {
				System.out.print(word + " ");
			}
			System.out.println();
		}
	}
}
