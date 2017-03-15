package de.tudarmstadt.informatik.fop.breakout.controllers;

import de.tudarmstadt.informatik.fop.breakout.actions.BallCollideAction;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.BallModel;

import eea.engine.component.Component;

import eea.engine.event.basicevents.CollisionEvent;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class BallController extends Component {

    public BallController(String componentID) {
        super(componentID);
    }

    @Override
    public BallModel getOwnerEntity() {
        return (BallModel) super.getOwnerEntity();
    }

    public void init(StateBasedGame game) {
        BallModel ball = getOwnerEntity();

        GameContainer container = game.getContainer();
        int gameHeight = container.getHeight();
        int gameWidth = container.getWidth();

        ball.setPosition(new Vector2f(gameWidth / 2, gameHeight / 2 + 20));

        ball.setVelocity(new Vector2f(1, -1).scale(GameParameters.INITIAL_BALL_SPEED));

        CollisionEvent collisionEvent = new CollisionEvent();
        collisionEvent.addAction(new BallCollideAction(ball));

        ball.addComponent(collisionEvent);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        BallModel ball = getOwnerEntity();

        Vector2f oldPosition = ball.getPosition();
        Vector2f newPosition = oldPosition.add(ball.getVelocity().copy().scale(delta));
        ball.setPosition(newPosition);
    }
}
