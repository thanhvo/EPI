import java.util.Stack;

public class StackUtil {
	public static int evaluate_RPN(String expr) throws Exception{
		if (expr == null)
			return 0;
		Stack<Integer> stack = new Stack<Integer>();
		boolean neg = false;
		String value = "";
		for (int i = 0; i < expr.length(); i++) {
			char c = expr.charAt(i);
			if(c >= '0' && c <= '9')
				value += c;
			else if (c == ',') {
				if (value.isEmpty())
					throw new Exception("Invalid Input");
				int val = Integer.valueOf(value); 
				if (neg)
					stack.push(-val);
				else 
					stack.push(val);
				value = "";
				neg = false;
			} 
			
			else if (c == '-' || c == '+' || c == '*' || c =='/') {
				if (!value.isEmpty()) {
					throw new Exception("Invalid input");
				}
				/* Operation signs */
				else if (i == expr.length() -1 || expr.charAt(i+1) == ',') {
					if (i < expr.length() -1)
						i++;
					if (stack.isEmpty())
						throw new Exception("Invalid Input");
					int val1 = stack.pop().intValue();
					if (stack.isEmpty())
						throw new Exception("Invalid Input");
					int val2 = stack.pop().intValue();
					switch(c) {
					case '-':
						stack.push(val2 - val1);
						break;
					case '+':
						stack.push(val1 + val2);
						break;
					case '*':
						stack.push(val1 * val2);
						break;
					case '/':
						stack.push(val1/val2);
						break;
					}
				}			
				/* Negative sign */
				else if (c == '-' ) {
					/* Double negative signs */
					if (neg)
						throw new Exception("Invalid Input");
					neg = true;
				}								
			}
			else 
				throw new Exception("Invalid Input");

		}
		if (!value.isEmpty() || neg)
			throw new Exception("Invalid Input");
		if(stack.isEmpty())
			throw new Exception("Invalid Input");
		int ret = stack.pop().intValue();
		if (!stack.isEmpty())
			throw new Exception("Invalid Input");
		return ret;
	}
}
