
public class EncodingBinaryTree {
	public double prob;
	public Symbol s;
	public EncodingBinaryTree left, right;
	public EncodingBinaryTree(double prob, Symbol s) {
		this.prob = prob;
		this.s = s;
		left = null;
		right = null;
	}
	
	public EncodingBinaryTree(double prob, Symbol s, EncodingBinaryTree left, EncodingBinaryTree right) {
		this.prob = prob;
		this.s = s;
		this.left = left;
		this.right = right;
	}
}
