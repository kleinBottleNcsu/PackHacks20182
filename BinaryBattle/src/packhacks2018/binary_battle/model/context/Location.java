package packhacks2018.binary_battle.model.context;

import packhacks2018.binary_battle.model.character.BinaryCharacter;

/**
 * 
 * @author Andrew Wock
 *
 */
public class Location {
	private int xPos;
	
	private int yPos;
	
	private Integer number;
	
	private BinaryCharacter current;
	
	private LocationOrientation orientation;
	
	private static final String BG_MID1 = "images/BGBase.png";
	
	private static final String BG_MID2 = "images/BGGrass1.png";
	
	private static final String BG_MID3 = "images/BGGrass2.png";
	
	private static final String BG_TOP = "images/BGGrassTop.png";
	
	private static final String BG_RIGHT = "images/BGGrassRight.png";
	
	private static final String BG_BOTTOM = "images/BGGrassBottom.png";
	
	private static final String BG_LEFT = "images/BGGrassLeft.png";
	
	private static final String BG_UR = "images/BGGrassCNRUR.png";
	
	private static final String BG_UL = "images/BGGrassCNRUL.png";
	
	private static final String BG_LR = "images/BGGrassCNRDR.png";
	
	private static final String BG_LL = "images/BGGrassCNRDL.png";
	
	private String paintFile;
	
	public enum LocationOrientation {
		MID,
		TOP_WALL,
		RIGHT_WALL,
		BOTTOM_WALL,
		LEFT_WALL,
		TL_CORNER,
		TR_CORNER,
		BL_CORNER,
		BR_CORNER
	}
	
	public Location(int xPos, int yPos, LocationOrientation orientation) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.orientation = orientation;
		switch (orientation) {
		/*case BL_CORNER:
			paintFile = BG_LL;
			break;
		case BOTTOM_WALL:
			paintFile = BG_BOTTOM;
			break;
		case BR_CORNER:
			paintFile = BG_LR;
			break;
		case LEFT_WALL:
			paintFile = BG_LEFT;
			break;*/
		default:
			int chance = (int)(Math.random()*15.0);
			if (chance == 0) {
				paintFile = BG_MID2;
				break;
			}
			if (chance == 1) {
				paintFile = BG_MID3;
				break;
			}
			paintFile = BG_MID1;
			break;
		/*case RIGHT_WALL:
			paintFile = BG_RIGHT;
			break;
		case TL_CORNER:
			paintFile = BG_UL;
			break;
		case TOP_WALL:
			paintFile = BG_TOP;
			break;
		case TR_CORNER:
			paintFile = BG_UR;
			break;*/
		}
		number = null;
	}
	
	public String getPaintFile() {
		return paintFile;
	}
	
	public boolean hasNumber() {
		return number != null;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public void enterCharacter(BinaryCharacter c) {
		current = c;
	}
	
	public BinaryCharacter leaveCharacter() {
		BinaryCharacter temp = current;
		current = null;
		return temp;
	}
	
	public boolean hasCharacter() {
		return current != null;
	}
	
	public int xPos() {
		return xPos;
	}
	
	public int yPos() {
		return yPos;
	}
	
	public BinaryCharacter getOccupant() {
		return current;
	}
	
	public LocationOrientation getOrientation() {
		return orientation;
	}
	
}
