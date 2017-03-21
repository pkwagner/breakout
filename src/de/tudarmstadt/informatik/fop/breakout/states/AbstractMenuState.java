package de.tudarmstadt.informatik.fop.breakout.states;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.gui.BackButton;
import de.tudarmstadt.informatik.fop.breakout.views.MenuTitleRenderComponent;

import eea.engine.entity.Entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Handles common code for every state in menu like settings
 */
public abstract class AbstractMenuState extends AbstractGameState {

    AbstractMenuState(int stateId, Renderable background) {
        super(stateId, background);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        if (isTesting()) {
            return;
        }

        //every state has button to move back to the main menu
        addEntity(new BackButton());
    }
}
