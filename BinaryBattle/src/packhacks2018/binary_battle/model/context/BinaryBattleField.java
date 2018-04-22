package packhacks2018.binary_battle.model.context;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import packhacks2018.binary_battle.Direction;
import packhacks2018.binary_battle.model.action.Action;
import packhacks2018.binary_battle.model.character.BinaryCharacter;
import packhacks2018.binary_battle.model.character.BinaryCharacter.CharacterType;
import packhacks2018.binary_battle.model.character.BlueCharacter;
import packhacks2018.binary_battle.model.character.GreenCharacter;
import packhacks2018.binary_battle.model.character.RedCharacter;
import packhacks2018.binary_battle.model.character.YellowCharacter;
import packhacks2018.binary_battle.model.context.Location.LocationOrientation;
import packhacks2018.binary_battle.util.IntNode;
import packhacks2018.binary_battle.util.SimpleIntBinaryTree;

public class BinaryBattleField {
	private Location[][] field;
	
	private static final int sizeX = 30;
	
	private static final int sizeY = 30;
	
	private ArrayList<Integer> availableNumbers;
	
	private static final int RED = 0;
	
	private static final int BLUE = 1;
	
	private static final int GREEN = 2;
	
	private static final int YELLOW = 3;
	
	private static final int NUMBER_OF_CHARACTERS = 4;
	
	private BinaryCharacter[] characters = new BinaryCharacter[NUMBER_OF_CHARACTERS];
	
	private int numberOfCharacters;
	
	Queue<BinaryCharacter> turnOrder;
	
	private Queue<Location> recursiveAssist;
	
	BinaryCharacter currentCharacter;
	
	private static BinaryBattleField instance = new BinaryBattleField();
	
	public static BinaryBattleField getInstance() {
		return instance;
	}
	
	private BinaryBattleField() {
		turnOrder = new LinkedList<BinaryCharacter>();
		numberOfCharacters = 0;
		field = new Location[sizeY][sizeX];
		
		for (int c = 0; c < sizeX; c++) {
			for (int r = 0; r < sizeY; r++) {
				if (c == 0) {
					if (r == 0) {
						field[c][r] = new Location(c, r, LocationOrientation.TL_CORNER);
					} else if (r == sizeY - 1) {
						field[c][r] = new Location(c, r, LocationOrientation.TR_CORNER);
					} else {
						field[c][r] = new Location(c, r, LocationOrientation.LEFT_WALL);
					}
				} else if (c == sizeX - 1) {
					if (r == 0) {
						field[c][r] = new Location(c, r, LocationOrientation.BL_CORNER);
					} else if (r == sizeY - 1) {
						field[c][r] = new Location(c, r, LocationOrientation.BR_CORNER);
					} else {
						field[c][r] = new Location(c, r, LocationOrientation.RIGHT_WALL);
					}
				} else {
					if (r == 0) {
						field[c][r] = new Location(c, r, LocationOrientation.TOP_WALL);
					} else if (r == sizeY - 1) {
						field[c][r] = new Location(c, r, LocationOrientation.BOTTOM_WALL);
					} else {
						field[c][r] = new Location(c, r, LocationOrientation.MID);
					}
				}
			}
		}
		
		availableNumbers = new ArrayList<>(sizeY*sizeX);
		for (int i = 0; i < sizeY*sizeX; i++) {
			availableNumbers.add(i);
		}
	}
	
	public void addCharacter() {
		switch(numberOfCharacters) {
		case RED:
			BinaryCharacter red = new RedCharacter(CharacterType.RED, field[0][0]);
			characters[RED] = red;
			field[0][0].enterCharacter(red);
			turnOrder.add(red);
			break;
		case BLUE:
			BinaryCharacter blue = new BlueCharacter(CharacterType.BLUE, field[sizeX - 1][0]);
			characters[BLUE] = blue;
			field[sizeX - 1][0].enterCharacter(blue);
			turnOrder.add(blue);
			//characters[BLUE] = new BlueCharacter(CharacterType.BLUE, field[sizeX][0]);
			break;
		case GREEN:
			BinaryCharacter green = new GreenCharacter(CharacterType.GREEN, field[sizeX - 1][sizeY - 1]);
			characters[GREEN] = green;
			field[sizeX - 1][sizeY - 1].enterCharacter(green);
			turnOrder.add(green);
			break;
		case YELLOW:
			BinaryCharacter yellow = new YellowCharacter(CharacterType.YELLOW, field[0][sizeY - 1]);
			characters[YELLOW] = yellow;
			field[0][sizeY - 1].enterCharacter(yellow);
			turnOrder.add(yellow);
			break;
		default:
			throw new IllegalArgumentException("Invalid number of characters.");
		}
		numberOfCharacters++;
	}
	
	public Location[][] getGrid() {
		return field;
	}
	
	public int xSize() {
		return sizeX;
	}
	
	public int ySize() {
		return sizeY;
	}
	
	public void clearCharacters() {
		characters = new BinaryCharacter[NUMBER_OF_CHARACTERS];
		turnOrder = new LinkedList<>();
		numberOfCharacters = 0;
		field = new Location[sizeY][sizeX];
		for (int c = 0; c < sizeX; c++) {
			for (int r = 0; r < sizeY; r++) {
				if (c == 0) {
					if (r == 0) {
						field[c][r] = new Location(c, r, LocationOrientation.TL_CORNER);
					} else if (r == sizeY) {
						field[c][r] = new Location(c, r, LocationOrientation.TR_CORNER);
					} else {
						field[c][r] = new Location(c, r, LocationOrientation.TOP_WALL);
					}
				} else if (c == sizeX) {
					if (r == 0) {
						field[c][r] = new Location(c, r, LocationOrientation.BL_CORNER);
					} else if (r == sizeY) {
						field[c][r] = new Location(c, r, LocationOrientation.BR_CORNER);
					} else {
						field[c][r] = new Location(c, r, LocationOrientation.BOTTOM_WALL);
					}
				} else {
					if (r == 0) {
						field[c][r] = new Location(c, r, LocationOrientation.LEFT_WALL);
					} else if (r == sizeY) {
						field[c][r] = new Location(c, r, LocationOrientation.RIGHT_WALL);
					} else {
						field[c][r] = new Location(c, r, LocationOrientation.MID);
					}
				}
			}
		}
	}
	
	public void reset(int numChars) {
		clearCharacters();
		if (numChars > NUMBER_OF_CHARACTERS || numChars < 0) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < numChars; i++) {
			addCharacter();
		}
		for (int i = 0; i < sizeX*sizeY/10; i++) {
			newNumber();
		}
		currentCharacter = turnOrder.remove();
	}
	
	public void newNumber() {
		int xPos = (int)(Math.random()*(double)sizeX);
		int yPos = (int)(Math.random()*(double)sizeY);
		if (field[xPos][yPos].hasNumber()) {
			newNumber();
		} else {
			field[xPos][yPos].setNumber(availableNumbers.remove((int)(Math.random()*(double)availableNumbers.size())));
		}
	}
	
	public void executeTurn() {
		if (!currentCharacter.getAlive() && currentCharacter.getLives() > 0) {
			revive(currentCharacter);
			turnOrder.add(currentCharacter);
			clearMoveSet();
			currentCharacter = turnOrder.remove();
			return;
		}
		Action[] moveList = currentCharacter.executeTurn();
		for (int i = 0; i < moveList.length; i++) {
			switch(moveList[i].getActionType()) {
			case FIRE:
				fire(moveList[i].getDirection());
				break;
			case MOVE:
				move(moveList[i].getDirection());
				break;
			case STAY:
				stay();
				break;
			}
		}
		
		if (currentCharacter.invincible()) {
			currentCharacter.setInvincible(true);
		}
		
		turnOrder.add(currentCharacter);
		clearMoveSet();
		currentCharacter = turnOrder.remove();
	}
	
	private void move(Direction direction) {
		Location currentLocation = currentCharacter.getLocation();
		Location destination = null;
		switch (direction) {
		case DOWN:
			try {
				destination = field[currentLocation.xPos()][currentLocation.yPos() + 1];
			} catch(ArrayIndexOutOfBoundsException e) {
				return;
			}
			break;
		case LEFT:
			try {
				destination = field[currentLocation.xPos() - 1][currentLocation.yPos()];
			} catch(ArrayIndexOutOfBoundsException e) {
				return;
			}
			break;
		case RIGHT:
			try {
				destination = field[currentLocation.xPos() + 1][currentLocation.yPos()];
			} catch(ArrayIndexOutOfBoundsException e) {
				return;
			}
			break;
		case UP:
			try {
				destination = field[currentLocation.xPos()][currentLocation.yPos() - 1];
			} catch(ArrayIndexOutOfBoundsException e) {
				return;
			}
			break;
		}
		if (destination.hasCharacter()) {
			return;
		}
		
		currentCharacter.setLocation(destination);
		if (destination.hasNumber()) {
			currentCharacter.collect(destination.getNumber());
			destination.setNumber(null);
			newNumber();
		}
	}
	

	private void fire(Direction direction) {
		Queue<Location> targets = getTreeHit(direction);
		while(!targets.isEmpty()) {
			Location target = targets.remove();
			if (target.hasCharacter()) {
				if (target.getOccupant() != currentCharacter && !target.getOccupant().invincible()) {
					kill(target.getOccupant());
				}
			}
		}
	}
	
	public Queue<Location> getTreeHit(Direction direction) {
		SimpleIntBinaryTree tree = currentCharacter.getTree();
		recursiveAssist = new LinkedList<>();
		IntNode currentNode = tree.getRoot();
		switch(direction) {
		case DOWN:
			getTreeHitDown(currentNode, currentCharacter.getLocation().xPos(), currentCharacter.getLocation().yPos());
			return recursiveAssist;
		case LEFT:
			getTreeHitLeft(currentNode, currentCharacter.getLocation().xPos(), currentCharacter.getLocation().yPos());
			return recursiveAssist;
		case RIGHT:
			getTreeHitRight(currentNode, currentCharacter.getLocation().xPos(), currentCharacter.getLocation().yPos());
			return recursiveAssist;
		case UP:
			getTreeHitUp(currentNode, currentCharacter.getLocation().xPos(), currentCharacter.getLocation().yPos());
			return recursiveAssist;
		}
		return null; //technically unreachable
	}
	
	private void getTreeHitUp(IntNode node, int currentX, int currentY) {
		if (node != null) {
			getTreeHitUp(node.left(), currentX + 1, currentY - 1);
			try {
				recursiveAssist.add(field[currentX][currentY]);
			} catch(ArrayIndexOutOfBoundsException e) {
				//Do nothing, we just won't do anything with that.
			}
			getTreeHitUp(node.right(), currentX - 1, currentY - 1);
		}
	}
	
	private void getTreeHitRight(IntNode node, int currentX, int currentY) {
		if (node != null) {
			getTreeHitRight(node.left(), currentX + 1, currentY + 1);
			try {
				recursiveAssist.add(field[currentX][currentY]);
			} catch(ArrayIndexOutOfBoundsException e) {
				//Do nothing, we just won't do anything with that.
			}
			getTreeHitRight(node.right(), currentX + 1, currentY - 1);
		}
	}
	
	private void getTreeHitDown(IntNode node, int currentX, int currentY) {
		if (node != null) {
			getTreeHitDown(node.left(), currentX - 1, currentY + 1);
			try {
				recursiveAssist.add(field[currentX][currentY]);
			} catch(ArrayIndexOutOfBoundsException e) {
				//Do nothing, we just won't do anything with that.
			}
			getTreeHitDown(node.right(), currentX + 1, currentY + 1);
		}
	}
	
	private void getTreeHitLeft(IntNode node, int currentX, int currentY) {
		if (node != null) {
			getTreeHitLeft(node.left(), currentX - 1, currentY - 1);
			try {
				recursiveAssist.add(field[currentX][currentY]);
			} catch(ArrayIndexOutOfBoundsException e) {
				//Do nothing, we just won't do anything with that.
			}
			getTreeHitLeft(node.right(), currentX - 1, currentY + 1);
		}
	}
	
	private void stay() {
		//Lol literally do nothing.
	}
	
	public void setAttack(Direction direction) {
		currentCharacter.setAttack(direction);
	}
	
	public void setMove(Direction direction) {
		currentCharacter.setMove(direction);
	}
	
	public void clearMoveSet() {
		currentCharacter.clearMoveSet();
	}
	
	public BinaryCharacter[] getCharacters() {
		return characters;
	}
	
	/**
	 * Maybe not needed.
	 */
	public void checkAlive() {
		
	}
	
	public void kill(BinaryCharacter c) {
		c.setAlive(false);
		c.getLocation().leaveCharacter();
		c.setLives(c.getLives() - 1);
		c.setLocation(null);
	}
	
	public void revive(BinaryCharacter c) {
		c.setAlive(true);
		c.setLocation(c.getRespawnLocation());
		c.setInvincible(true);
	}
	
	public void collect(int number) {
		currentCharacter.collect(number);
	}
	
	public BinaryCharacter getCurrentCharacter() {
		return currentCharacter;
	}
	
	public void permanentKill(BinaryCharacter character) {
		
	}
	
	
}
