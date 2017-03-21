package de.tudarmstadt.informatik.fop.breakout.actions.items;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.PlayerModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import org.newdawn.slick.state.StateBasedGame;

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

    }
}
