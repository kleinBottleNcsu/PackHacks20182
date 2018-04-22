/**
 * 
 */
package packhacks2018.binary_battle.util;

import packhacks2018.binary_battle.Direction;

/**
 * @author Andrew Wock
 *
 */
public class DiagonallyLinkedField<E> {
	
	private int size;
	
	private ListNode topLeft;
	
	private ListNode topLeftChecker;
	
	public DiagonallyLinkedField() {
		topLeft = null;
		topLeftChecker = null;
		size = 0;
	}
	
	/**
	 * Add method, carelessly writes over present nodes if they are in the way.  No need not to for the moment.
	 * @param data
	 * @param checker
	 * @param diagonalRightIndex
	 * @param diagonalLeftIndex
	 */
	public void add(E data, boolean checker, int diagonalRightIndex, int diagonalLeftIndex) {
		if (diagonalRightIndex + diagonalLeftIndex < 0 || diagonalLeftIndex > diagonalRightIndex) {
			throw new IllegalArgumentException();
		}
		if (checker) {
			if (topLeftChecker == null) {
				if (diagonalRightIndex == 0 && diagonalLeftIndex == 0) {
					topLeftChecker = new ListNode(data);
				} else {
					throw new IllegalArgumentException();
				}
			} else {
				add(data, diagonalRightIndex, diagonalLeftIndex, topLeftChecker, 0, 0);
			}
		} else {
			if (topLeft == null) {
				if (diagonalRightIndex == 0 && diagonalLeftIndex == 0) {
					topLeft = new ListNode(data);
				} else {
					throw new IllegalArgumentException();
				}
			} else {
				add(data, diagonalRightIndex, diagonalLeftIndex, topLeft, 0, 0);
			}
		}
	}
	
	private void add(E data, int diagonalRightIndex, int diagonalLeftIndex, ListNode current, int rCurrent, int lCurrent) {
		try {
			if (rCurrent == diagonalRightIndex && lCurrent == diagonalLeftIndex) {
				current = null;
			} else if (rCurrent != diagonalRightIndex) {
				add(data, diagonalRightIndex, diagonalLeftIndex, current.bottomRight, rCurrent + 1, lCurrent);
			} else if (rCurrent != diagonalLeftIndex) {
				if (rCurrent < diagonalLeftIndex) {
					add(data, diagonalRightIndex, diagonalLeftIndex, current.bottomLeft, rCurrent, lCurrent + 1);
				} else {
					add(data, diagonalRightIndex, diagonalLeftIndex, current.topRight, rCurrent, lCurrent - 1);
				}
			}
			throw new IllegalArgumentException();
		} catch(NullPointerException e) {
			throw new IllegalArgumentException("Can only add to locations reachable by other nodes.");
		}
	}
	
	public void add(E data, int xPos, int yPos) {
		
	}
	
	public E remove(E data, int xPos, int yPos) {
		return null;
	}
	
	public E remove(boolean checker, int diagonalLeftIndex, int diagonalRightIndex) {
		return null;
	}
	
	public int size() {
		return size;
	}
	
	public E get(int xPos, int yPos) {
		return null;
	}
	
	public E get(boolean checker, int diagonalLeftIndex, int diagonalRightIndex) {
		if (diagonalRightIndex + diagonalLeftIndex < 0 || diagonalLeftIndex > diagonalRightIndex) {
			throw new IllegalArgumentException();
		}
		if (checker) {
			return get(diagonalRightIndex, diagonalLeftIndex, topLeftChecker, 0, 0);
		} else {
			return get(diagonalRightIndex, diagonalLeftIndex, topLeft, 0, 0);
		}
	}
	
	private E get(int diagonalLeftIndex, int diagonalRightIndex, ListNode current, int rCurrent, int lCurrent) {
		try {
			if (rCurrent == diagonalRightIndex && lCurrent == diagonalLeftIndex) {
				return current.data;
			} else if (rCurrent != diagonalRightIndex) {
				get(diagonalRightIndex, diagonalLeftIndex, current.bottomRight, rCurrent + 1, lCurrent);
			} else if (rCurrent != diagonalLeftIndex) {
				if (rCurrent < diagonalLeftIndex) {
					get(diagonalRightIndex, diagonalLeftIndex, current.bottomLeft, rCurrent, lCurrent + 1);
				} else {
					get(diagonalRightIndex, diagonalLeftIndex, current.topRight, rCurrent, lCurrent - 1);
				}
			}
			throw new IllegalArgumentException();
		} catch(NullPointerException e) {
			throw new IllegalArgumentException("Can only get at locations reachable by other nodes.");
		}
	}
	
	public E[] treeTransverse(SimpleIntBinaryTree tree, int xPos, int yPos, Direction direction) {
		

		
		
		return null;
	}
	
	private class ListNode {
		
		private ListNode topLeft;
		
		private ListNode topRight;
		
		private ListNode bottomLeft;
		
		private ListNode bottomRight;
		
		E data;
		
		public ListNode(E data) {
			this(data, null, null, null, null);
		}
		
		public ListNode(E data, ListNode uL, ListNode uR, ListNode bL, ListNode bR) {
			this.data = data;
			topLeft = uL;
			topRight = uR;
			bottomLeft = bL;
			bottomRight = bR;
		}
	}
	
}
