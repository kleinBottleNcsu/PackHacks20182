package packhacks2018.binary_battle.model.context;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

import packhacks2018.binary_battle.Direction;
import packhacks2018.binary_battle.model.character.BinaryCharacter;
import packhacks2018.binary_battle.model.character.BinaryCharacter.CharacterType;

public class BinaryBattleFieldTest {

	@Test
	public void testAddCharacter() {
		BinaryBattleField bbf = BinaryBattleField.getInstance();
		bbf.clearCharacters();
		bbf.addCharacter();
		assertEquals(CharacterType.RED, bbf.getGrid()[0][0].getOccupant().getType());
		bbf.addCharacter();
		assertEquals(CharacterType.BLUE, bbf.getGrid()[bbf.xSize() - 1][0].getOccupant().getType());
		bbf.addCharacter();
		assertEquals(CharacterType.GREEN, bbf.getGrid()[bbf.xSize() - 1][bbf.ySize() - 1].getOccupant().getType());
		bbf.addCharacter();
		assertEquals(CharacterType.YELLOW, bbf.getGrid()[0][bbf.ySize() - 1].getOccupant().getType());
		try {
			bbf.addCharacter();
			fail("No exception after adding four characters.");
		} catch(IllegalArgumentException e) {
			assertEquals(CharacterType.RED, bbf.getGrid()[0][0].getOccupant().getType());
			assertEquals(CharacterType.BLUE, bbf.getGrid()[bbf.xSize() - 1][0].getOccupant().getType());
			assertEquals(CharacterType.GREEN, bbf.getGrid()[bbf.xSize() - 1][bbf.ySize() - 1].getOccupant().getType());
			assertEquals(CharacterType.YELLOW, bbf.getGrid()[0][bbf.ySize() - 1].getOccupant().getType());
		}
	}

	@Test
	public void testExecuteTurn() {
		BinaryBattleField bbf = BinaryBattleField.getInstance();
		bbf.clearCharacters();
		bbf.reset(4);
		BinaryCharacter c = bbf.getCharacters()[0];
		c.setMove(Direction.RIGHT);
		c.setMove(Direction.DOWN);
		bbf.executeTurn();
		assertEquals(bbf.getCharacters()[1], bbf.getCurrentCharacter());
		assertEquals(bbf.getCharacters()[0], bbf.getGrid()[1][1].getOccupant());
		assertEquals(bbf.getCharacters()[3], bbf.getGrid()[0][bbf.ySize() - 1].getOccupant());
		c = bbf.getCharacters()[1];
		for (int i = 0; i < 30; i++) {
			c.collect(i);
		}
		c.setAttack(Direction.LEFT);
		c.setMove(Direction.DOWN);
		bbf.executeTurn();
		assertNull(bbf.getGrid()[0][bbf.ySize() - 1].getOccupant());
		assertFalse(bbf.getCharacters()[3].getAlive());
		assertEquals(2, bbf.getCharacters()[3].getLives());
		assertEquals(c, bbf.getGrid()[bbf.xSize() - 1][1].getOccupant());
		assertEquals(bbf.getCharacters()[2], bbf.getCurrentCharacter());
		bbf.executeTurn();
		assertEquals(bbf.getCharacters()[2], bbf.getGrid()[bbf.xSize() - 1][bbf.ySize() - 1].getOccupant());
		assertEquals(bbf.getCharacters()[3], bbf.getCurrentCharacter());
		bbf.executeTurn();
		assertTrue(bbf.getCharacters()[3].getAlive());
		assertEquals(bbf.getCharacters()[0], bbf.getCurrentCharacter());
		bbf.executeTurn();
		assertEquals(bbf.getCharacters()[0], bbf.getGrid()[1][1].getOccupant());
		assertEquals(bbf.getCharacters()[1], bbf.getCurrentCharacter());
		bbf.executeTurn();
		assertEquals(bbf.getCharacters()[2], bbf.getCurrentCharacter());
		bbf.executeTurn();
		assertEquals(bbf.getCharacters()[3], bbf.getCurrentCharacter());
		
	}
	
	@Test
	public void testReset() {
		BinaryBattleField bbf = BinaryBattleField.getInstance();
		bbf.clearCharacters();
		bbf.reset(4);
		assertEquals(bbf.getCharacters()[0], bbf.getCurrentCharacter());
		assertEquals(CharacterType.RED, bbf.getGrid()[0][0].getOccupant().getType());
		assertEquals(CharacterType.BLUE, bbf.getGrid()[bbf.xSize() - 1][0].getOccupant().getType());
		assertEquals(CharacterType.GREEN, bbf.getGrid()[bbf.xSize() - 1][bbf.ySize() - 1].getOccupant().getType());
		assertEquals(CharacterType.YELLOW, bbf.getGrid()[0][bbf.ySize() - 1].getOccupant().getType());
		bbf.reset(2);
		assertEquals(bbf.getCharacters()[0], bbf.getCurrentCharacter());
		assertEquals(CharacterType.RED, bbf.getGrid()[0][0].getOccupant().getType());
		assertEquals(CharacterType.BLUE, bbf.getGrid()[bbf.xSize() - 1][0].getOccupant().getType());
		assertNull(bbf.getGrid()[bbf.xSize() - 1][bbf.ySize() - 1].getOccupant());
		assertNull(bbf.getGrid()[0][bbf.ySize() - 1].getOccupant());
	}

	@Test
	public void testKillAndRevive() {
		BinaryBattleField bbf = BinaryBattleField.getInstance();
		bbf.clearCharacters();
		bbf.reset(4);
		bbf.getGrid()[1][1].enterCharacter(bbf.getGrid()[0][0].leaveCharacter());
		bbf.kill(bbf.getGrid()[1][1].getOccupant());
		assertFalse(bbf.getCharacters()[0].getAlive());
		bbf.revive(bbf.getCharacters()[0]);
		assertTrue(bbf.getCharacters()[0].getAlive());
		assertEquals(bbf.getCharacters()[0], bbf.getGrid()[0][0].getOccupant());
	}
	
	@Test
	public void testGetTreeHit() {
		BinaryBattleField bbf = BinaryBattleField.getInstance();
		bbf.clearCharacters();
		bbf.reset(4);
		Queue<Location> targets = bbf.getTreeHit(Direction.UP);
		assertTrue(targets.isEmpty());
		bbf.getGrid()[0][0].leaveCharacter().setLocation(bbf.getGrid()[15][15]);
		BinaryCharacter c = bbf.getCharacters()[0];
		assertEquals(15, c.getLocation().xPos());
		assertEquals(15, c.getLocation().yPos());
		c.collect(5);
		c.collect(1);
		c.collect(3);
		c.collect(4);
		c.collect(2);
		c.collect(6);
		c.collect(7);
		c.collect(8);
		c.collect(9); //should spit out in order of 1 - 10
		targets = bbf.getTreeHit(Direction.UP);
		Location target = targets.remove();
		assertEquals(16, target.xPos());
		assertEquals(14, target.yPos());
		target = targets.remove();
		assertEquals(16, target.xPos());
		assertEquals(12, target.yPos());
		target = targets.remove();
		assertEquals(15, target.xPos());
		assertEquals(13, target.yPos());
		target = targets.remove();
		assertEquals(14, target.xPos());
		assertEquals(12, target.yPos());
		target = targets.remove();
		assertEquals(15, target.xPos()); //make  sure that you can't hit yourself
		assertEquals(15, target.yPos());
		target = targets.remove();
		assertEquals(14, target.xPos());
		assertEquals(14, target.yPos());
		target = targets.remove();
		assertEquals(13, target.xPos());
		assertEquals(13, target.yPos());
		target = targets.remove();
		assertEquals(12, target.xPos());
		assertEquals(12, target.yPos());
		target = targets.remove();
		assertEquals(11, target.xPos());
		assertEquals(11, target.yPos());
		targets = bbf.getTreeHit(Direction.LEFT);
		target = targets.remove();
		assertEquals(14, target.xPos());
		assertEquals(14, target.yPos());
		target = targets.remove();
		assertEquals(12, target.xPos());
		assertEquals(14, target.yPos());
		target = targets.remove();
		assertEquals(13, target.xPos());
		assertEquals(15, target.yPos());
		target = targets.remove();
		assertEquals(12, target.xPos());
		assertEquals(16, target.yPos());
		target = targets.remove();
		assertEquals(15, target.xPos()); //make  sure that you can't hit yourself
		assertEquals(15, target.yPos());
		target = targets.remove();
		assertEquals(14, target.xPos());
		assertEquals(16, target.yPos());
		target = targets.remove();
		assertEquals(13, target.xPos());
		assertEquals(17, target.yPos());
		target = targets.remove();
		assertEquals(12, target.xPos());
		assertEquals(18, target.yPos());
		target = targets.remove();
		assertEquals(11, target.xPos());
		assertEquals(19, target.yPos());
		targets = bbf.getTreeHit(Direction.DOWN);
		target = targets.remove();
		assertEquals(14, target.xPos());
		assertEquals(16, target.yPos());
		target = targets.remove();
		assertEquals(14, target.xPos());
		assertEquals(18, target.yPos());
		target = targets.remove();
		assertEquals(15, target.xPos());
		assertEquals(17, target.yPos());
		target = targets.remove();
		assertEquals(16, target.xPos());
		assertEquals(18, target.yPos());
		target = targets.remove();
		assertEquals(15, target.xPos()); //make  sure that you can't hit yourself
		assertEquals(15, target.yPos());
		target = targets.remove();
		assertEquals(16, target.xPos());
		assertEquals(16, target.yPos());
		target = targets.remove();
		assertEquals(17, target.xPos());
		assertEquals(17, target.yPos());
		target = targets.remove();
		assertEquals(18, target.xPos());
		assertEquals(18, target.yPos());
		target = targets.remove();
		assertEquals(19, target.xPos());
		assertEquals(19, target.yPos());
		targets = bbf.getTreeHit(Direction.RIGHT);
		target = targets.remove();
		assertEquals(16, target.xPos());
		assertEquals(16, target.yPos());
		target = targets.remove();
		assertEquals(18, target.xPos());
		assertEquals(16, target.yPos());
		target = targets.remove();
		assertEquals(17, target.xPos());
		assertEquals(15, target.yPos());
		target = targets.remove();
		assertEquals(18, target.xPos());
		assertEquals(14, target.yPos());
		target = targets.remove();
		assertEquals(15, target.xPos()); //make  sure that you can't hit yourself
		assertEquals(15, target.yPos());
		target = targets.remove();
		assertEquals(16, target.xPos());
		assertEquals(14, target.yPos());
		target = targets.remove();
		assertEquals(17, target.xPos());
		assertEquals(13, target.yPos());
		target = targets.remove();
		assertEquals(18, target.xPos());
		assertEquals(12, target.yPos());
		target = targets.remove();
		assertEquals(19, target.xPos());
		assertEquals(11, target.yPos());
	}

}
