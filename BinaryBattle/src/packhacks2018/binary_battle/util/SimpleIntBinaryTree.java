package packhacks2018.binary_battle.util;

public class SimpleIntBinaryTree {
	
	private int size;
	
	private IntNode root;
	
	public SimpleIntBinaryTree() {
		size = 0;
		root = null;
	}
	
	public int size() {
		return size;
	}
	
	public void add(int data) {
		if (root == null) {
			root = new IntNode(data);
			size++;
		} else {
			add(data, root);
		}
	}
	
	private void add(int data, IntNode current) {
		
		if (data > current.data()) {
			if (current.right() == null) {
				size++;
				current.setRight(new IntNode(data));
			} else {
				add(data, current.right());
			}
		} else if (data < current.data()) {
			if (current.left() == null) {
				size++;
				current.setLeft(new IntNode(data));
			} else {
				add(data, current.left());
			}
		} else {
			throw new IllegalArgumentException("Tree cannot contain duplicate data.");
		}
		
	}
	
	public void clear() {
		size = 0;
		root = null;
	}
	
	/**
	 * Make sure that null case is handled.
	 * @return The root.
	 */
	public IntNode getRoot() {
		return root;
	}
	
	
	
}
