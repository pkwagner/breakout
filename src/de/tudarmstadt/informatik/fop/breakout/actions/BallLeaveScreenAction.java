package de.tudarmstadt.informatik.fop.breakout.actions;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.BallController;
import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.StateBasedEntityManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class BallLeaveScreenAction implements Action {

    private final BallController ballController;
    private final StateBasedEntityManager entityManager = StateBasedEntityManager.getInstance();

    public BallLeaveScreenAction(BallController ballController) {
        this.ballController = ballController;
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i, Component component) {
        // Pause game, reset ball and reactivate 'press key' screen
        gameContainer.setPaused(true);
        ballController.reset();
        entityManager.getEntity(GameParameters.GAMEPLAY_STATE, GameParameters.GAMESTART_ENTITY_ID).setVisible(true);

        // TODO Decrease remaining health points
    }
}
