package states.submenu;

import constants.GameParameters;
import views.CreditsStateView;
import eea.engine.entity.Entity;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Showing the all developers
 */
public class CreditsState extends AbstractMenuState {

    public CreditsState(int id) throws SlickException {
        super(id, GameParameters.CREDITS_BACKGROUND_IMAGE);
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
        titleEntity.addComponent(new CreditsStateView(contentId + GameParameters.EXT_VIEW));
        addEntity(titleEntity);
    }
}
