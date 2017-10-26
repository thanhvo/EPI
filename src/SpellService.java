import java.util.Arrays;

public class SpellService extends SpellCheckService{
	static String wLast = null;
	static String[] closestToLastWord = null;
	
	public static void service(ServiceRequest req, ServiceResponse resp) {
		String w = req.extractWordToCheckFromRequest();
		String[] result = null;
		synchronized (SpellService.class) {
			if (w.equals(wLast)) {
				result = Arrays.copyOf(closestToLastWord, closestToLastWord.length);
			}			
		}
		if (result == null) {
			result = closestInDictionary(w);
			synchronized(SpellService.class) {
				wLast = w;
				closestToLastWord = result;
			}
		}
		resp.encodeIntoResponse(result);
	}
}
