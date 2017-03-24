package models;

import views.gui.KeyBindingView;
import views.gui.WaitingKeybindingView;
import org.newdawn.slick.Input;

/**
 * All changeable KeyBindings to control the game.
 *
 * @see KeyBindingView
 * @see WaitingKeybindingView
 * @see actions.gui.KeyBindingClickAction
 */
public enum KeyBinding {

    /**
     * Pauses the game inside the gameplay state
     */
    PAUSE(Input.KEY_P),

    /**
     * Starts the game on a new game or if the player lost a life
     */
    START_GAME(Input.KEY_SPACE),

    /**
     * Moves the stick to the left side
     */
    LEFT_MOVE(Input.KEY_LEFT),

    /**
     * Moves the stick to the right side
     */
    RIGHT_MOVE(Input.KEY_RIGHT),

    /**
     * Moves the stick of player 2 to the right side (only for multiplayer)
     */
    LEFT_MOVE_PLAYER2(Input.KEY_A),

    /**
     * Moves the stick of player 2 to the right side (only for multiplayer)
     */
    RIGHT_MOVE_PLAYER2(Input.KEY_D);

    private int currentBinding;

    KeyBinding(int defaultBinding) {
        this.currentBinding = defaultBinding;
    }

    /**
     * Get the current key id for this type of KeyBinding.
     *
     * @return current key id
     */
    public int getCurrentBinding() {
        return currentBinding;
    }

    /**
     * Updates the key id for this type of KeyBinding.
     *
     * @param currentBinding the new key id
     */
    public void setCurrentBinding(int currentBinding) {
        this.currentBinding = currentBinding;
    }

    /**
     * Get the human readable representation for the current key binding. Example for the space button it's "SPACE"
     * or "5NUMPAD" for the number five on the numpad.
     *
     * @return human-readable string
     */
    public String getKeyName() {
        return Input.getKeyName(currentBinding);
    }
}
