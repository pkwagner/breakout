package de.tudarmstadt.informatik.fop.breakout.factories;

import org.newdawn.slick.geom.Vector2f;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import eea.engine.entity.Entity;
import eea.engine.interfaces.IEntityFactory;

/**
 * Factory for creating Borders of the field. Borders are not visible and not
 * passable entities for holding the ball in the field.
 * 
 * @author Tobias Otterbein, Benedikt Wartusch
 * 
 */
public class BorderFactory implements IEntityFactory, GameParameters {

	private BorderType type;

	/**
	 * Factory Constructor
	 * 
	 * @param type
	 *            determines the type of a created border (TOP, LEFT or RIGHT)
	 */
	public BorderFactory(BorderType type) {
		this.type = type;
	}

	@Override
	public Entity createEntity() {

		Entity border;
		Vector2f size;
		Vector2f position;

		switch (type) {

		case TOP:
			border = new Entity(TOP_BORDER_ID);
			position = new Vector2f(WINDOW_WIDTH / 2, 0);
			size = new Vector2f(WINDOW_WIDTH, BORDER_WIDTH);
			break;
		case LEFT:
			border = new Entity(LEFT_BORDER_ID);
			position = new Vector2f(0, WINDOW_HEIGHT / 2);
			size = new Vector2f(BORDER_WIDTH, WINDOW_HEIGHT);
			break;
		case RIGHT:
			border = new Entity(RIGHT_BORDER_ID);
			position = new Vector2f(WINDOW_WIDTH, WINDOW_HEIGHT / 2);
			size = new Vector2f(BORDER_WIDTH, WINDOW_HEIGHT);
			break;
		default:
			return null;
		}

		border.setPosition(position);
		border.setSize(size);
		border.setVisible(false);
		border.setPassable(false);

		return border;
	}

}
