package packhacks2018.binary_battle.model.character;

import static org.junit.Assert.*;

import org.junit.Test;

import packhacks2018.binary_battle.Direction;
import packhacks2018.binary_battle.model.action.Action;
import packhacks2018.binary_battle.model.action.Action.ActionType;
import packhacks2018.binary_battle.model.character.BinaryCharacter.CharacterType;
import packhacks2018.binary_battle.model.context.Location;
import packhacks2018.binary_battle.model.context.Location.LocationOrientation;

public class BinaryCharacterTest {


	@Test
	public void testExecuteTurn() {
		BinaryCharacter red = new RedCharacter(CharacterType.RED, new Location(0, 0, LocationOrientation.MID));
		assertEquals(new Action(ActionType.STAY, Direction.RIGHT), red.executeTurn()[0]);
		assertEquals(new Action(ActionType.STAY, Direction.RIGHT), red.executeTurn()[1]);
		red.clearMoveSet();
		red.setMove(Direction.UP);
		assertEquals(new Action(ActionType.MOVE, Direction.UP), red.executeTurn()[0]);
		red.clearMoveSet();
		red.setMove(Direction.UP);
		red.setAttack(Direction.LEFT);
		Action[] test = red.executeTurn();
		assertEquals(new Action(ActionType.MOVE, Direction.UP), test[0]);
		assertEquals(new Action(ActionType.FIRE, Direction.LEFT), test[1]);
		red.clearMoveSet();
		red.setStay();
		red.setStay();
		test = red.executeTurn();
		assertEquals(new Action(ActionType.STAY, Direction.RIGHT), test[0]);
		assertEquals(new Action(ActionType.STAY, Direction.LEFT), test[0]);
		try {
			red.setStay();
			fail();
		} catch(IllegalArgumentException e) {
			assertEquals(new Action(ActionType.STAY, Direction.RIGHT), red.executeTurn()[0]);
			assertEquals(new Action(ActionType.STAY, Direction.LEFT), red.executeTurn()[0]);
		}
	}
	
	@Test
	public void testCollect() {
		BinaryCharacter red = new RedCharacter(CharacterType.RED, new Location(0, 0, LocationOrientation.MID));
		red.collect(4);
		red.collect(3);
		assertEquals(4, red.getTree().getRoot().data());
		assertEquals(3, red.getTree().getRoot().left().data());
	}

}
