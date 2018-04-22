/**
 * 
 */
package packhacks2018.binary_battle.model.character;

import java.awt.Color;

import packhacks2018.binary_battle.Direction;
import packhacks2018.binary_battle.model.action.Action;
import packhacks2018.binary_battle.model.action.Action.ActionType;
import packhacks2018.binary_battle.model.context.Location;
import packhacks2018.binary_battle.util.SimpleIntBinaryTree;

/**
 * Superclass for each individual character.
 * @author Andrew Wock
 *
 */
public abstract class BinaryCharacter {
	
	public enum CharacterType {
		RED,
		BLUE,
		GREEN,
		YELLOW
	}
	
	private Location current;
	
	private CharacterType type;
	
	private Action[] moveSet;
	
	private static final int MOVE_COUNT = 4;
	
	private int movesThisTurn;
	
	private boolean alive;
	
	private boolean invincible;
	/** The binary tree used for attacks. */
	private SimpleIntBinaryTree tree;
	
	private static final int LIFE_COUNT = 3;
	
	private int lives; 

	private Location respawnLocation;
	
	public BinaryCharacter(CharacterType type, Location location) {
		this.type = type;
		this.current  = location;
		setRespawnLocation(location);
		alive = true;
		moveSet = new Action[MOVE_COUNT];
		lives = LIFE_COUNT;
		movesThisTurn = 0;
		tree = new SimpleIntBinaryTree();
	}
	
	public CharacterType getType() {
		return type;
	}
	
	public void setAttack(Direction direction) {
		if (movesThisTurn >= MOVE_COUNT) {
			throw new IllegalArgumentException();
		}
		moveSet[movesThisTurn] = new Action(ActionType.FIRE, direction);
		movesThisTurn++;
	}
	
	public void setMove(Direction direction) {
		if (movesThisTurn >= MOVE_COUNT) {
			throw new IllegalArgumentException();
		}
		moveSet[movesThisTurn] = new Action(ActionType.MOVE, direction);
		movesThisTurn++;
	}
	
	public void setStay() {
		if (movesThisTurn >= MOVE_COUNT) {
			throw new IllegalArgumentException();
		}
		moveSet[movesThisTurn] = new Action(ActionType.STAY, Direction.DOWN);
		movesThisTurn++;
	}
	
	public Action[] executeTurn() {
		for (int i = 0; i < moveSet.length; i++) {
			if (moveSet[i] == null) {
				setStay();
			}
		}
		return moveSet;
	}
	
	public Action[] getMoveSet() {
		return moveSet;
	}
	
	public void clearMoveSet() {
		movesThisTurn = 0;
		moveSet = new Action[MOVE_COUNT];
	}
	
	public int getLives() {
		return lives;
	}
	
	public void collect(int toCollect) {
		tree.add(toCollect);
	}
	
	public SimpleIntBinaryTree getTree() {
		return tree;
	}

	/**
	 * GUI methods, diff for each subclass.
	 * @return The stuff GUI needs.
	 */
	public abstract Color getColor();
	public abstract String getUpSprite();
	public abstract String getRightSprite();
	public abstract String getDownSprite();
	public abstract String getLeftSprite();
	public abstract String getRestSprite();

	public boolean getAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public boolean invincible() {
		return invincible;
	}
	
	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}
	
	public void setLocation(Location current) {
		if (this.current != null) {
			this.current.leaveCharacter();
		}
		this.current = current;
		if (current != null) {
			current.enterCharacter(this);
		}
	}
	
	public Location getLocation() {
		return current;
	}
	
	public Location getRespawnLocation() {
		return respawnLocation;
	}
	
	public void setRespawnLocation(Location respawnLocation) {
		this.respawnLocation = respawnLocation;
	}

	public void setLives(int i) {
		lives = i;
		
	}

}


