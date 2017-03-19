package de.tudarmstadt.informatik.fop.breakout.actions;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.BallController;
import de.tudarmstadt.informatik.fop.breakout.models.ClockModel;
import de.tudarmstadt.informatik.fop.breakout.models.PlayerModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameoverState;
import de.tudarmstadt.informatik.fop.breakout.models.BallModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.StateBasedEntityManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

public class BallLeaveScreenAction implements Action {

    private final BallController ballController;
    private final StateBasedEntityManager entityManager = StateBasedEntityManager.getInstance();

    public BallLeaveScreenAction(BallController ballController) {
        this.ballController = ballController;
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i, Component component) {
        BallModel ball = (BallModel) component.getOwnerEntity();
        GameplayState gameplayState = (GameplayState) stateBasedGame.getState(GameParameters.GAMEPLAY_STATE);

        // Check if there is more than one remaining ball
        ArrayList<BallModel> allBalls = gameplayState.getBalls();
        if (allBalls.size() > 1) {
            // Remove ball completely
            gameplayState.removeEntity(ball);
            allBalls.remove(ball);
        } else {
            // Pause game, reset ball and reactivate 'press key' screen
            gameContainer.setPaused(true);
            ballController.reset(gameContainer);
            entityManager.getEntity(GameParameters.GAMEPLAY_STATE, GameParameters.GAMESTART_ENTITY_ID).setVisible(true);

            // Decrease health points & check game end
            // NOTICE: Only valid for 1 player
            ArrayList<PlayerModel> allPlayers = gameplayState.getPlayers();

            if (allPlayers.size() == 1) {
                PlayerModel player = allPlayers.get(0);

                player.hit();
                if (player.isDead()) {
                    float time = ((ClockModel) entityManager.getEntity(GameParameters.GAMEPLAY_STATE, GameParameters.STOP_WATCH_ID)).getSeconds();

                    GameoverState gameoverState = (GameoverState) stateBasedGame.getState(GameParameters.GAMEOVER_STATE);
                    gameoverState.load(player, time);
                    stateBasedGame.enterState(GameParameters.GAMEOVER_STATE);
                }
            }
        }
    }
}
