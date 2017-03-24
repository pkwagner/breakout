package states.submenu;

import models.gui.BackButton;

import states.AbstractGameState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Handles common code for every state in submenu like settings
 */
public abstract class AbstractMenuState extends AbstractGameState {

    public AbstractMenuState(int stateId, String imagePath) throws SlickException {
        super(stateId, imagePath);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        if (isTesting()) {
            return;
        }

        //every state has button to move back to the main submenu
        addEntity(new BackButton());
    }
}
