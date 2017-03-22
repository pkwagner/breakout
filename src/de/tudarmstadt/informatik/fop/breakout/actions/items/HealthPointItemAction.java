package de.tudarmstadt.informatik.fop.breakout.actions.items;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.PlayerModel;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Gives the player who picks up this item an additional health point
 */
public class HealthPointItemAction extends AbstractItemAction {

    private PlayerModel player;

    @Override
    protected void init(StateBasedGame stateBasedGame, PlayerModel catchingPlayer) {
        this.player = catchingPlayer;
    }

    @Override
    public void onEnable() {
        player.heal(GameParameters.ITEM_HP_HEALTHPOINTS);
    }

    @Override
    public void onDisable() {
        // Hey, Captain Obvious here. You don't see anything cause it's not a temporary item. Obviously.
    }
}
