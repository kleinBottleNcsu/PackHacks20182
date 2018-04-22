package packhacks2018.binary_battle.model.character;

import java.awt.Color;

import packhacks2018.binary_battle.model.context.Location;

public class GreenCharacter extends BinaryCharacter {
	
	private static final String REST_SPRITE = "images/ManFront.png";
	
	private static final String FRONT_SPRITE = "images/ManAtFront.png";
	
	private static final String RIGHT_SPRITE = "images/ManAtRight.png";
	
	private static final String BACK_SPRITE = "images/ManAtBack.png";
	
	private static final String LEFT_SPRITE = "images/ManAtLeft.png";
	
	private static final Color COLOR = new Color(0, 155, 0);
	
	public GreenCharacter(CharacterType type, Location location) {
		super(type, location);
	}

	@Override
	public Color getColor() {
		return COLOR;
	}

	@Override
	public String getUpSprite() {
		return BACK_SPRITE;
	}

	@Override
	public String getRightSprite() {
		return RIGHT_SPRITE;
	}

	@Override
	public String getDownSprite() {
		return FRONT_SPRITE;
	}

	@Override
	public String getLeftSprite() {
		return LEFT_SPRITE;
	}

	@Override
	public String getRestSprite() {
		return REST_SPRITE;
	}

}
