package packhacks2018.binary_battle.model.character;

import java.awt.Color;

import packhacks2018.binary_battle.model.context.Location;

public class BlueCharacter extends BinaryCharacter {
	
	private static final String REST_SPRITE = "images/NekoFront.png";
	
	private static final String FRONT_SPRITE = "images/NekoAtFront.png";
	
	private static final String RIGHT_SPRITE = "images/NekoAtRight.png";
	
	private static final String BACK_SPRITE = "images/NekoAtBack.png";
	
	private static final String LEFT_SPRITE = "images/NekoAtLeft.png";
	
	private static final Color COLOR = new Color(100, 100, 255);
	
	public BlueCharacter(CharacterType type, Location location) {
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
