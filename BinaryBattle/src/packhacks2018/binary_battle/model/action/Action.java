/**
 * 
 */
package packhacks2018.binary_battle.model.action;

import packhacks2018.binary_battle.Direction;

/**
 * @author Andrew Wock
 *
 */
public class Action {
	
	/**
	 * Determines the type of action.
	 * @author Andrew Wock
	 *
	 */
	public enum ActionType {
		STAY,
		MOVE,
		FIRE,
	}
	
	/** direction of the action, not used in still. */
	private Direction direction;
	/** Action type */
	private ActionType type;
	
	public Action(ActionType type, Direction direction) {
		this.direction = direction;
		this.type = type;
	}
	
	/** Returns the direction */
	public Direction getDirection() {
		return direction;
	}
	
	/** Returns the action type */
	public ActionType getActionType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direction == null || type == ActionType.STAY) ? 0 : direction.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Action other = (Action) obj;
		if (type != other.type)
			return false;
		if (type != ActionType.STAY && direction != other.direction)
			return false;
		
		return true;
	}
	
}
