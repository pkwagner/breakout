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

    private final String menuTitle;

    public AbstractMenuState(int stateId, Renderable background, String menuTitle) {
        super(stateId, background);

        this.menuTitle = menuTitle;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        if (isTesting()) {
            return;
        }

        //every menu state has a title
        Entity titleEntity = new Entity(GameParameters.MENU_TITLE_ID);
        titleEntity.addComponent(new MenuTitleRenderComponent("title_view", menuTitle));
        addEntity(titleEntity);

        //every state has button to move back to the main menu
        addEntity(new BackButton());
    }
}
