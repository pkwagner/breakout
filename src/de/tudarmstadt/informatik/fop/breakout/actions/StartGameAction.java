package de.tudarmstadt.informatik.fop.breakout.actions;

import eea.engine.action.Action;
import eea.engine.component.Component;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Starts the game on key press
 */
public class StartGameAction implements Action {

    private boolean started;

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta, Component component) {
        //abort if the game was already started
        if (started) {
            return;
        }

        //start the game and hide the "how to start it" text
        gameContainer.setPaused(false);
        component.getOwnerEntity().setVisible(false);
    }
}
