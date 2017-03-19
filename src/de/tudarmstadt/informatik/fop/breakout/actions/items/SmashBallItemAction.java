package de.tudarmstadt.informatik.fop.breakout.actions.items;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.BallModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import eea.engine.entity.StateBasedEntityManager;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

public class SmashBallItemAction extends AbstractItemAction {

    private ArrayList<BallModel> allBalls;

    @Override
    protected void init(StateBasedGame stateBasedGame) {
        allBalls = ((GameplayState) stateBasedGame.getState(GameParameters.GAMEPLAY_STATE)).getBalls();
    }

    @Override
    public void onEnable() {
        allBalls.forEach(ball -> ball.setSmashMode(true));
    }

    @Override
    public void onDisable() {
        allBalls.forEach(ball -> ball.setSmashMode(false));
    }
}
