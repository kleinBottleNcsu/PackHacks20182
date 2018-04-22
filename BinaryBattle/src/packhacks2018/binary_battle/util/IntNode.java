/**
 * 
 */
package packhacks2018.binary_battle.util;

/**
 * @author Andrew Wock
 *
 */
public class IntNode {
	private IntNode left;
	private IntNode right;
	private int data;
	
	public IntNode(int data) {
		this(data, null, null);
	}
	
	public IntNode(int data, IntNode left, IntNode right) {
		this.left = left;
		this.right = right;
		this.data = data;
	}
	
	public IntNode left() {
		return left;
	}
	
	public IntNode right() {
		return right;
	}
	
	public int data() {
		return data;
	}
	
	public void setLeft(IntNode left) {
		this.left = left;
	}
	
	public void setRight(IntNode right) {
		this.right = right;
	}
	
	public boolean hasLeft() {
		return left != null;
	}
	
	public boolean hasRight() {
		return right != null;
	}
	
}
