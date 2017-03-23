package de.tudarmstadt.informatik.fop.breakout.actions.game.items;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.game.BallModel;
import de.tudarmstadt.informatik.fop.breakout.models.game.PlayerModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class AdditionalBallItemAction extends AbstractItemAction {

    private final Logger logger = LogManager.getLogger();

    private StateBasedGame stateBasedGame;
    private PlayerModel catchingPlayer;

    @Override
    protected void init(StateBasedGame stateBasedGame, PlayerModel catchingPlayer) {
        this.stateBasedGame = stateBasedGame;
        this.catchingPlayer = catchingPlayer;
    }

    @Override
    public void onEnable() {
        GameplayState gameplayState = ((GameplayState) stateBasedGame.getState(GameParameters.GAMEPLAY_STATE));

        // Get primary ball to set contrary position
        BallModel primaryBall = gameplayState.getBalls().get(0);

        try {
            // Spawn new ball contrary to the primary one
            BallModel newBall = gameplayState.addBall(stateBasedGame, catchingPlayer);
            newBall.setPosition(primaryBall.getPosition().copy());
            newBall.setVelocity(new Vector2f(primaryBall.getVelocity().getX(), -primaryBall.getVelocity().getY()));
        } catch (SlickException e) {
            logger.error("Some error occurred while adding an additional ball to game: " + e);
        }
    }
}
