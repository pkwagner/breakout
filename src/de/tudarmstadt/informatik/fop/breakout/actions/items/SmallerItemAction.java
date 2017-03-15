package de.tudarmstadt.informatik.fop.breakout.actions.items;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.StickModel;

import eea.engine.entity.StateBasedEntityManager;
import org.newdawn.slick.geom.Vector2f;


/**
 * Makes the stick smaller on item pickup
 */
public class SmallerItemAction extends AbstractItemAction {

    private StickModel stickModel;

    public SmallerItemAction() {
        super();

        stickModel = (StickModel) StateBasedEntityManager.getInstance().getEntity(GameParameters.GAMEPLAY_STATE, GameParameters.STICK_ID);
    }

    @Override
    public void onEnable() {
        Vector2f oldSize = stickModel.getSize();
        stickModel.setSize(new Vector2f(oldSize.getX() * GameParameters.ITEM_SMALLER_CHANGE_VALUE, oldSize.getY()));
    }

    @Override
    public void onDisable() {

    }
}
