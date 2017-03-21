package de.tudarmstadt.informatik.fop.breakout.actions;

import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Event listener on toggling the pause state. This action will pause the game (update and rendering) and will
 * toggle the visibility of pause image and back button image.
 */
public class PauseToggleAction implements Action {

    private final Logger logger = LogManager.getLogger();

    private Set<Entity> entities = new HashSet<>();

    public PauseToggleAction(Entity... entities) {
        this.entities.addAll(Arrays.asList(entities));
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta, Component component) {
        GameplayState gameplayState = (GameplayState) stateBasedGame.getState(GameParameters.GAMEPLAY_STATE);

        // Check if the current pause state is caused by the player
        if (gameplayState.isManuallyPaused() == gameContainer.isPaused()) {
            boolean newPauseState = !gameContainer.isPaused();
            logger.info("Toggling pause state to {}", newPauseState);

            gameContainer.setPaused(newPauseState);
            //hide or show the pause image/back button depending on the pause state
            for (Entity entity : entities) {
                entity.setVisible(newPauseState);
            }

            gameplayState.setManuallyPaused(newPauseState);
        } else {
            logger.debug("Doesn't pause/resume game because this toggle wasn't set manually");
        }
    }
}
