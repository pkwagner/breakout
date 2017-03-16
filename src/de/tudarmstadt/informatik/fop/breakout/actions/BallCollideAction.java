package de.tudarmstadt.informatik.fop.breakout.actions;

import de.tudarmstadt.informatik.fop.breakout.actions.blocks.AbstractBlockCollideAction;
import de.tudarmstadt.informatik.fop.breakout.actions.blocks.RamBlockCollideAction;
import de.tudarmstadt.informatik.fop.breakout.actions.blocks.SimpleBlockCollideAction;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.BallModel;
import de.tudarmstadt.informatik.fop.breakout.models.SoundType;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.AbstractBlockModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.event.basicevents.CollisionEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class BallCollideAction implements Action {

    private final Logger logger = LogManager.getLogger();
    private final BallModel ballModel;

    public BallCollideAction(BallModel ballModel) {
        this.ballModel = ballModel;
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta, Component component) {
        CollisionEvent collisionEvent = (CollisionEvent) component;

        GameplayState state = (GameplayState) stateBasedGame.getState(GameParameters.GAMEPLAY_STATE);

        Entity collidedEntity = collisionEvent.getCollidedEntity();
        String collidedId = collidedEntity.getID();

        Vector2f velocity = ballModel.getVelocity();
        switch (collidedId) {
            case GameParameters.STICK_ID:
                state.getSoundController().playEffect(SoundType.STICK_HIT);
            case GameParameters.TOP_BORDER_ID:
                ballModel.setVelocity(new Vector2f(velocity.getX(), - velocity.getY()));
                break;
            case GameParameters.LEFT_BORDER_ID:
            case GameParameters.RIGHT_BORDER_ID:
                ballModel.setVelocity(new Vector2f(- velocity.getX(), velocity.getY()));
                break;
            default:
                // Check if the collided entity is really a block
                if (collidedEntity instanceof AbstractBlockModel) {
                    onBlockCollide(state, (AbstractBlockModel) collidedEntity, velocity);
                }
        }
    }

    private void onBlockCollide(GameplayState state, AbstractBlockModel collidedEntity, Vector2f velocity) {
        AbstractBlockModel block = collidedEntity;

        state.getSoundController().playEffect(SoundType.BLOCK_HIT);

        // TODO Try to replace this by a switch/case construction
        // Decide by block type which blockCollideAction to choose
        AbstractBlockCollideAction collideAction = null;
        switch(block.getType()){
            case SIMPLE:
                collideAction = new SimpleBlockCollideAction(block, ballModel, state);
                break;
            case RAM:
                collideAction = new RamBlockCollideAction(block, ballModel, state);
        }

        if (collideAction != null) {
            collideAction.onCollision();
            ballModel.setVelocity(collideAction.calculateCollisionVelocity(velocity));
        } else {
            // TODO Throw error concerning undefined block type
        }
    }
}
