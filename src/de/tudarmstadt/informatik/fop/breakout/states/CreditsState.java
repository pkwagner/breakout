package de.tudarmstadt.informatik.fop.breakout.states;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.views.CreditsRenderComponent;

import eea.engine.entity.Entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Showing the all developers
 */
public class CreditsState extends AbstractMenuState {

    public CreditsState(int id) throws SlickException {
        super(id, new Image(GameParameters.CREDITS_BACKGROUND_IMAGE));
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        super.init(container, game);
        if (isTesting()) {
            return;
        }

        String contentId = GameParameters.CREDITS_CONTENT_ID;
        Entity titleEntity = new Entity(contentId);
        titleEntity.setPosition(new Vector2f(container.getWidth() / 2, GameParameters.CREDITS_DEVS_START_Y));
        titleEntity.addComponent(new CreditsRenderComponent(contentId + GameParameters.EXT_VIEW));
        addEntity(titleEntity);
    }
}
