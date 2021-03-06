import java.util.*;

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
	
	public static<T extends Comparable> void moveRings(StackT<T> s1, StackT<T> s2, StackT<T> s3, int n) throws Exception {
		if (n <= 0)
			return;
		moveRings(s1,s3,s2,n-1);
		T item = s1.pop();
		s2.push(item);
		System.out.println("Move item " + item + " from stack " + s1.getID() + " to stack " + s2.getID());
		moveRings(s3,s2,s1, n-1);
	}
	
	public static<T extends Comparable> void moveRings(StackT<T> s1, StackT<T> s2, StackT<T> s3) throws Exception {
		moveRings(s1,s2,s3,s1.getSize());
	}	
	
	public static<T extends Comparable> Stack<Pair<Integer, T>> examine_buildings_with_sunset(String s, Transformer<String, T> transformer) {
		int idx = 0;
		T height;
		Scanner scanner = new Scanner(s);
		Stack<Pair<Integer, T>> buildings_with_sunset = new Stack<Pair<Integer, T>>();
		while (scanner.hasNext()) {
			height = transformer.transform(scanner.next());
			while(!buildings_with_sunset.isEmpty() && height.compareTo(buildings_with_sunset.peek().second) >= 0) {
				buildings_with_sunset.pop();
			}
			buildings_with_sunset.push(new Pair<Integer, T>(new Integer(idx++), height));
		}
		return buildings_with_sunset;
	}
	
	public static<T extends Comparable> void insert(Stack<T> s, T e ) {
		if (s.isEmpty() || s.peek().compareTo(e) <= 0) {
			s.push(e);
		} else {
			T f = s.pop();
			insert(s,e);
			s.push(f);
		}
	}
	
	public static<T extends Comparable> void sort(Stack<T> s) {
		if (!s.isEmpty()) {
			T e = s.pop();
			sort(s);
			insert(s,e);
		}
	}
	
	public static String simplifyPath(String path) throws Exception{
		Stack<String> stack = new Stack<String>();
		Scanner scanner = new Scanner(path);
		scanner.useDelimiter("/");
		while(scanner.hasNext()) {
			String dir = scanner.next();
			//System.out.println(dir);
			if (dir.equals("..")) {
				if (stack.isEmpty()) {
					throw new Exception("Invalid input");
				}
				stack.pop();
			}
			else if (!dir.equals(".") && !dir.isEmpty()) {
				stack.push(dir);
			}
		}
		String ret = "";
		while (!stack.isEmpty()) {
			if (ret.isEmpty()) {
				ret = stack.pop();
			} else {
				ret = stack.pop() + "/" + ret;
			}
		}
		ret = "/" + ret;
		return ret;
	}
}
