package events;

import models.KeyBinding;

import eea.engine.event.Event;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

/**
 * In comparison to the KeyDownEvent of EEA this event checks for keydown events using the KeyBinding
 * enum. Then it's easier to change keybinding and starts listening based on the new key without unregister and
 * register the key event.
 *
 * @see eea.engine.event.basicevents.KeyDownEvent
 */
public class KeyDownEvent extends Event {

    private final KeyBinding keyBinding;

    public KeyDownEvent(KeyBinding keyBinding) {
        super("KeyDownEvent");

        this.keyBinding = keyBinding;
    }

    @Override
    protected boolean performAction(GameContainer gc, StateBasedGame sb, int delta) {
        return gc.getInput().isKeyDown(keyBinding.getCurrentBinding());
    }
}
