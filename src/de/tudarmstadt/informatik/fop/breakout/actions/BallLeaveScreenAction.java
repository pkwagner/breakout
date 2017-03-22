package de.tudarmstadt.informatik.fop.breakout.actions;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.BallController;
import de.tudarmstadt.informatik.fop.breakout.models.BallModel;
import de.tudarmstadt.informatik.fop.breakout.models.ClockModel;
import de.tudarmstadt.informatik.fop.breakout.models.PlayerModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameoverState;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.StateBasedEntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Arrays;
import java.util.List;

public class BallLeaveScreenAction implements Action {

    private final Logger logger = LogManager.getLogger();

    private final BallController ballController;
    private final StateBasedEntityManager entityManager = StateBasedEntityManager.getInstance();

    public BallLeaveScreenAction(BallController ballController) {
        this.ballController = ballController;
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i, Component component) {
        BallModel ball = (BallModel) component.getOwnerEntity();
        GameplayState gameplayState = (GameplayState) stateBasedGame.getState(GameParameters.GAMEPLAY_STATE);

        PlayerModel allPlayers[] = gameplayState.getPlayers();
        List<BallModel> allBalls = gameplayState.getBalls();
        boolean multiplayer = (allPlayers.length == 2);

        if (!multiplayer) {
            // SINGLEPLAYER

            // Check if there is more than one remaining ball
            if (allBalls.size() > 1) {
                // Remove ball completely
                gameplayState.removeEntity(ball);
                allBalls.remove(ball);
            } else {
                // Pause game, reset stick&ball and reactivate 'press key' screen
                gameContainer.setPaused(true);
                ballController.reset(gameContainer, stateBasedGame);
                resetStick(allPlayers[0]);

                entityManager.getEntity(GameParameters.GAMEPLAY_STATE, GameParameters.GAMESTART_ENTITY_ID).setVisible(true);

                // Decrease health points
                allPlayers[0].hit();
            }
        } else {
            // Remove ball completely
            gameplayState.removeEntity(ball);
            allBalls.remove(ball);

            // Check if player1 or player2 failed keeping the ball
            if (ball.getPosition().getY() < (gameContainer.getHeight() / 2))
                allPlayers[1].hit();
            else
                allPlayers[0].hit();

            // Add balls for each player if there's no ball remaining and reset sticks
            if (allBalls.size() == 0) {
                // Pause game
                gameContainer.setPaused(true);

                try {
                    gameplayState.addBall(stateBasedGame, allPlayers[0]);
                    gameplayState.addBall(stateBasedGame, allPlayers[1]);
                } catch (SlickException e) {
                    logger.error("Some error occurred while loading new balls in multiplayer: {}", e);
                }

                resetStick(allPlayers[0]);
                resetStick(allPlayers[1]);

                // Set 'press key to continue' screen
                entityManager.getEntity(GameParameters.GAMEPLAY_STATE, GameParameters.GAMESTART_ENTITY_ID).setVisible(true);
            }
        }

        // Check if all players are dead (game end)
        if(Arrays.stream(allPlayers).allMatch(playerModel -> playerModel.isDead())){
            float time = ((ClockModel) entityManager.getEntity(GameParameters.GAMEPLAY_STATE, GameParameters.STOP_WATCH_ID)).getSeconds();

            GameoverState gameoverState = (GameoverState) stateBasedGame.getState(GameParameters.GAMEOVER_STATE);
            if(allPlayers.length==1)
                gameoverState.load(allPlayers[0],time);
            else
                gameoverState.load(allPlayers,time);

            stateBasedGame.enterState(GameParameters.GAMEOVER_STATE);
        }
    }

    private void resetStick(PlayerModel player) {
        if (player.getStickController() != null)
            player.getStickController().reset();
    }
}
