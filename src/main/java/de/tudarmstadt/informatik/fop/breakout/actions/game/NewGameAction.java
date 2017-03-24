package de.tudarmstadt.informatik.fop.breakout.actions.game;


import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 */
public class NewGameAction implements ComponentListener {

    private static final Logger logger = LogManager.getLogger();

    private GameplayState gameplayState;

    private StateBasedGame stateBasedGame;

    private GameContainer gameContainer;

    public NewGameAction(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        this.gameplayState = (GameplayState) stateBasedGame.getState(GameParameters.GAMEPLAY_STATE);
        this.stateBasedGame = stateBasedGame;
        this.gameContainer = gameContainer;
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        try {
            gameplayState.enter(gameContainer, stateBasedGame);
            stateBasedGame.enterState(GameParameters.GAMEPLAY_STATE);
        } catch (SlickException e) {
            logger.error("Unable to start game");
        }
    }
}
