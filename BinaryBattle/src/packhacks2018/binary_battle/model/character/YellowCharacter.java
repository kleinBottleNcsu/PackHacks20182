package packhacks2018.binary_battle.model.character;

import java.awt.Color;

import packhacks2018.binary_battle.model.context.Location;

public class YellowCharacter extends BinaryCharacter {

	private static final String REST_SPRITE = "images/AlienFront.png";
	
	private static final String FRONT_SPRITE = "images/AlienAtFront.png";
	
	private static final String RIGHT_SPRITE = "images/AlienAtRight.png";
	
	private static final String BACK_SPRITE = "images/AlienAtBack.png";
	
	private static final String LEFT_SPRITE = "images/AlienAtLeft.png";
	
	private static final Color COLOR = new Color(255, 0, 255);
	
	public YellowCharacter(CharacterType type, Location location) {
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
