package de.tudarmstadt.informatik.fop.breakout.actions.items;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.StickModel;

import eea.engine.entity.StateBasedEntityManager;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Makes the stick smaller on item pickup
 */
public class SmallerItemAction extends AbstractItemAction {

    private StickModel stickModel;

    @Override
    protected void init(StateBasedGame stateBasedGame) {
        stickModel = (StickModel) StateBasedEntityManager.getInstance().getEntity(GameParameters.GAMEPLAY_STATE, GameParameters.STICK_ID);
    }

    @Override
    public void onEnable() {
        Vector2f oldSize = stickModel.getSize();
        int newWidth = (int) (oldSize.getX() * GameParameters.ITEM_SMALLER_CHANGE_VALUE);
        if(newWidth < GameParameters.STICK_MIN_WIDTH) newWidth = (int) GameParameters.STICK_MIN_WIDTH;
        if(newWidth > GameParameters.WINDOW_WIDTH) newWidth = GameParameters.WINDOW_WIDTH;
        stickModel.setSize(new Vector2f(newWidth, oldSize.getY()));
    }

    @Override
    public void onDisable() {

    }
}
