package packhacks2018.binary_battle.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class SimpleIntBinaryTreeTest {

	@Test
	public void testSimpleIntBinaryTree() {
		SimpleIntBinaryTree tree = new SimpleIntBinaryTree();
		assertEquals(0, tree.size());
		assertNull(tree.getRoot());
	}

	@Test
	public void testAdd() {
		SimpleIntBinaryTree tree = new SimpleIntBinaryTree();
		assertEquals(0, tree.size());
		assertNull(tree.getRoot());
		tree.add(4);
		assertEquals(4, tree.getRoot().data());
		assertFalse(tree.getRoot().hasLeft());
		assertFalse(tree.getRoot().hasRight());
		tree.add(3);
		assertEquals(4, tree.getRoot().data());
		assertTrue(tree.getRoot().hasLeft());
		assertEquals(3, tree.getRoot().left().data());
		tree.add(-1);
		assertEquals(4, tree.getRoot().data());
		assertTrue(tree.getRoot().hasLeft());
		assertEquals(3, tree.getRoot().left().data());
		assertTrue(tree.getRoot().left().hasLeft());
		assertEquals(-1, tree.getRoot().left().left().data());
		tree.add(6);
		assertEquals(4, tree.getRoot().data());
		assertTrue(tree.getRoot().hasLeft());
		assertEquals(3, tree.getRoot().left().data());
		assertTrue(tree.getRoot().left().hasLeft());
		assertEquals(-1, tree.getRoot().left().left().data());
		assertTrue(tree.getRoot().hasRight());
		assertEquals(6, tree.getRoot().right().data());
		tree.add(5);
		assertEquals(4, tree.getRoot().data());
		assertTrue(tree.getRoot().hasLeft());
		assertEquals(3, tree.getRoot().left().data());
		assertTrue(tree.getRoot().left().hasLeft());
		assertEquals(-1, tree.getRoot().left().left().data());
		assertTrue(tree.getRoot().hasRight());
		assertEquals(6, tree.getRoot().right().data());
		assertTrue(tree.getRoot().right().hasLeft());
		assertEquals(5, tree.getRoot().right().left().data());
		tree.add(9);
		assertEquals(4, tree.getRoot().data());
		assertTrue(tree.getRoot().hasLeft());
		assertEquals(3, tree.getRoot().left().data());
		assertTrue(tree.getRoot().left().hasLeft());
		assertEquals(-1, tree.getRoot().left().left().data());
		assertTrue(tree.getRoot().hasRight());
		assertEquals(6, tree.getRoot().right().data());
		assertTrue(tree.getRoot().right().hasLeft());
		assertEquals(5, tree.getRoot().right().left().data());
		assertTrue(tree.getRoot().right().hasRight());
		assertEquals(9, tree.getRoot().right().right().data());
		
		try {
			tree.add(9);
			fail("Shouldn't be able to add a duplicate.");
		} catch(IllegalArgumentException e) {
			assertEquals(4, tree.getRoot().data());
			assertTrue(tree.getRoot().hasLeft());
			assertEquals(3, tree.getRoot().left().data());
			assertTrue(tree.getRoot().left().hasLeft());
			assertEquals(-1, tree.getRoot().left().left().data());
			assertTrue(tree.getRoot().hasRight());
			assertEquals(6, tree.getRoot().right().data());
			assertTrue(tree.getRoot().right().hasLeft());
			assertEquals(5, tree.getRoot().right().left().data());
			assertTrue(tree.getRoot().right().hasRight());
			assertEquals(9, tree.getRoot().right().right().data());
		}
	}

	@Test
	public void testClear() {
		SimpleIntBinaryTree tree = new SimpleIntBinaryTree();
		tree.add(4);
		tree.add(5);
		tree.add(6);
		assertEquals(3, tree.size());
		tree.clear();
		assertEquals(0, tree.size());
		assertNull(tree.getRoot());
	}


}
