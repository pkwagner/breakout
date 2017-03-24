package events;

import eea.engine.event.Event;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This event is called if hold your main mouse button down (including holding it).
 */
public class MousePressedEvent extends Event {

    public MousePressedEvent() {
        super("MousePressedEvent");
    }

    @Override
    protected boolean performAction(GameContainer gc, StateBasedGame sb, int delta) {
        return gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
    }
}
