package events;

import eea.engine.event.Event;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This event triggers if the user clicked (pressed and released the main mouse button shortly) somewhere on the state.
 *
 * The MouseClickedEvent in EEA only triggers one event, because the {@link Input#isMousePressed(int)} will remove the
 * saved MouseClickedCall once it was called. This means no multiple MouseClickedEvents can exist in a single state.
 *
 * They all have to be connected to one Event, but events can only be assigned to a single component. So this class
 * was created where we can create from each other independent MouseClickedEvents.
 *
 * @see eea.engine.event.basicevents.MouseClickedEvent
 */
public class MouseClickedEvent extends Event {

    private static final int MOUSE_TOLERANCE = 5;

    private boolean wasDownBefore;
    private int startedClickX;
    private int startedClickY;

    public MouseClickedEvent() {
        super("MouseClickedEvent");
    }

    @Override
    protected boolean performAction(GameContainer gc, StateBasedGame sb, int delta) {
        boolean isDown = gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
        int mouseX = gc.getInput().getMouseX();
        int mouseY = gc.getInput().getMouseY();

        //the user released the mouse button
        if (wasDownBefore && !isDown
                //check if it's not a mouse drag
                //the user released the mouse button at the same position he/she started to click
                && Math.abs(mouseX - startedClickX) < MOUSE_TOLERANCE
                && Math.abs(mouseY - startedClickY) < MOUSE_TOLERANCE) {
            wasDownBefore = isDown;
            return true;
        }

        //user started to pressed the mouse button down
        if (!wasDownBefore && isDown) {
            startedClickX = mouseX;
            startedClickY = mouseY;
        }

        wasDownBefore = isDown;
        return false;
    }
}
