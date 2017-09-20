
public class Symbol {
	public char c;
	public double prob;
	public String code;
	public Symbol(char c, double prob) {
		this.c = c;
		this.prob = prob;
		code = "";
	}
	public void print() {
		System.out.println(c + "->" + code);
	}
}
