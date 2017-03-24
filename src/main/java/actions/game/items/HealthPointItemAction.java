package actions.game.items;

import constants.GameParameters;
import models.game.PlayerModel;
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
        player.addHealthpoints(GameParameters.ITEM_HP_HEALTHPOINTS);
    }
}
