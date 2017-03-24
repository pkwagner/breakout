package events;

import models.KeyBinding;

import eea.engine.event.Event;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

/**
 * In comparison to the KeyPressedEvent of EEA this event checks for keydown events using the KeyBinding
 * enum. Then it's easier to change keybinding and starts listening based on the new key without unregister and
 * register the key event.
 *
 * @see eea.engine.event.basicevents.KeyPressedEvent
 */
public class KeyPressedEvent extends Event {

    private final KeyBinding keyBinding;

    public KeyPressedEvent(KeyBinding keyBinding) {
        super("KeyPressedEvent");

        this.keyBinding = keyBinding;
    }

    @Override
    protected boolean performAction(GameContainer gc, StateBasedGame sb, int delta) {
        return gc.getInput().isKeyPressed(keyBinding.getCurrentBinding());
    }
}
