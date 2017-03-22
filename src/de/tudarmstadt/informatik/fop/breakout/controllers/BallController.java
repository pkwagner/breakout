package de.tudarmstadt.informatik.fop.breakout.controllers;

import de.tudarmstadt.informatik.fop.breakout.actions.BallCollideAction;
import de.tudarmstadt.informatik.fop.breakout.actions.BallLeaveScreenAction;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.BallModel;

import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import eea.engine.component.Component;

import eea.engine.event.basicevents.CollisionEvent;
import eea.engine.event.basicevents.LeavingScreenEvent;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class BallController extends Component {

    private boolean secondPlayer;

    public BallController(String componentID) {
        super(componentID);
    }

    @Override
    public BallModel getOwnerEntity() {
        return (BallModel) super.getOwnerEntity();
    }

    public void init(GameContainer gameContainer, StateBasedGame game, boolean secondPlayer) {
        this.secondPlayer = secondPlayer;

        BallModel ball = getOwnerEntity();

        // Reset position & velocity
        reset(gameContainer, game);

        CollisionEvent collisionEvent = new CollisionEvent();
        collisionEvent.addAction(new BallCollideAction(ball));

        // Add leave screen event
        LeavingScreenEvent leavingScreenEvent = new LeavingScreenEvent();
        leavingScreenEvent.addAction(new BallLeaveScreenAction(this));

        ball.addComponent(collisionEvent);
        ball.addComponent(leavingScreenEvent);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        GameplayState gameplayState = (GameplayState) stateBasedGame.getState(GameParameters.GAMEPLAY_STATE);
        BallModel ball = getOwnerEntity();

        // Add gravity to ball velocity (only singleplayer)
        if (!gameplayState.isMultiplayer()) {
            double pixelPerMeter = gameContainer.getHeight() / GameParameters.MAP_REAL_HEIGHT;
            double gravityVelocity = GameParameters.MAP_GRAVITY * pixelPerMeter * (delta / 1000D);
            ball.getVelocity().add(new Vector2f(0, (float) gravityVelocity));
        }

        // Update ball position
        Vector2f oldPosition = ball.getPosition();
        Vector2f newPosition = oldPosition.add(ball.getVelocity().copy().scale(gameplayState.getGameSpeedFactor()).scale(delta));
        ball.setPosition(newPosition);
    }

    public void reset(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        BallModel ball = getOwnerEntity();
        GameplayState gameplayState = (GameplayState) stateBasedGame.getState(GameParameters.GAMEPLAY_STATE);

        // Spawn position depends on the question if this player is the second one
        ball.setPosition(new Vector2f(gameContainer.getWidth() / 2, (secondPlayer ? GameParameters.BALL_INITIAL_POS_Y_PLAYER2 : GameParameters.BALL_INITIAL_POS_Y)));
        ball.setVelocity(new Vector2f(1, (secondPlayer ? 1 : -1)).scale((gameplayState.isMultiplayer() ? GameParameters.INITIAL_BALL_SPEED_MULTIPLAYER : GameParameters.INITIAL_BALL_SPEED)));
    }
}
