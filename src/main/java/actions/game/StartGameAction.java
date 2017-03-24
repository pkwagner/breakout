package actions.game;

import eea.engine.action.Action;
import eea.engine.component.Component;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Starts the game on key press
 */
public class StartGameAction implements Action {

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta, Component component) {
        //start the game and hide the "how to start it" text
        gameContainer.setPaused(false);
        component.getOwnerEntity().setVisible(false);
    }
}
