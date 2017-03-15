package de.tudarmstadt.informatik.fop.breakout.actions.items;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.StickModel;

import eea.engine.entity.StateBasedEntityManager;

import org.newdawn.slick.geom.Vector2f;

/**
 * Makes the stick bigger on item pickup
 */
public class BiggerItemAction extends AbstractItemAction {

    private StickModel stickModel;

    public BiggerItemAction() {
        super();

        stickModel = (StickModel) StateBasedEntityManager.getInstance().getEntity(GameParameters.GAMEPLAY_STATE, GameParameters.STICK_ID);
    }

    @Override
    public void onEnable() {
        Vector2f oldSize = stickModel.getSize();
        stickModel.setWidth(oldSize.getX() * GameParameters.ITEM_BIGGER_CHANGE_VALUE);
    }

    @Override
    public void onDisable() {

    }
}
