
public class StringToIntegerTransformer implements Transformer<String, Integer>{
	public Integer transform(String input) {
		return Integer.valueOf(input);
	}	
}
