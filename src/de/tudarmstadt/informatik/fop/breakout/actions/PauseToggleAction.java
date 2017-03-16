package de.tudarmstadt.informatik.fop.breakout.actions;

import eea.engine.action.Action;
import eea.engine.component.Component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Event listener on toggling the pause state. This action will pause the game (update and rendering) and will
 * toggle the visibility of pause image as entity.
 */
public class PauseToggleAction implements Action {

    private final Logger logger = LogManager.getLogger();

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta, Component component) {
        boolean newPauseState = !gameContainer.isPaused();
        logger.info("Toggling pause state to {}", newPauseState);

        gameContainer.setPaused(newPauseState);
        //hide the pause image again
        component.getOwnerEntity().setVisible(newPauseState);
    }
}
