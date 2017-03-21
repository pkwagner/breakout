package de.tudarmstadt.informatik.fop.breakout.actions.items;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.PlayerModel;
import de.tudarmstadt.informatik.fop.breakout.models.StickModel;

import eea.engine.entity.StateBasedEntityManager;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Makes the stick bigger on item pickup
 */
public class BiggerItemAction extends AbstractItemAction {

    private StickModel stickModel;

    @Override
    protected void init(StateBasedGame stateBasedGame, PlayerModel catchingPlayer) {
        stickModel = (StickModel) StateBasedEntityManager.getInstance().getEntity(GameParameters.GAMEPLAY_STATE, catchingPlayer.isSecondPlayer() ? GameParameters.STICK_ID_PLAYER2 : GameParameters.STICK_ID);
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
