package de.tudarmstadt.informatik.fop.breakout.controllers;

import de.tudarmstadt.informatik.fop.breakout.actions.StickMoveAction;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.events.KeyDownEvent;
import de.tudarmstadt.informatik.fop.breakout.models.KeyBinding;
import de.tudarmstadt.informatik.fop.breakout.models.StickModel;

import eea.engine.component.Component;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class StickController extends Component {

    private Vector2f initialPos;

    public StickController(String componentID) {
        super(componentID);
    }

    @Override
    public StickModel getOwnerEntity() {
        return (StickModel) super.getOwnerEntity();
    }

    public void setOwnerEntity(StickModel owningEntity) {
        super.setOwnerEntity(owningEntity);
    }

    public void init(StateBasedGame game, int initialPosX) {
        KeyDownEvent leftEvent = new KeyDownEvent(KeyBinding.LEFT_MOVE);
        leftEvent.addAction(new StickMoveAction(GameParameters.Direction.LEFT, getOwnerEntity()));

        KeyDownEvent rightEvent = new KeyDownEvent(KeyBinding.RIGHT_MOVE);
        rightEvent.addAction(new StickMoveAction(GameParameters.Direction.RIGHT, getOwnerEntity()));

        getOwnerEntity().addComponent(leftEvent);
        getOwnerEntity().addComponent(rightEvent);

        //set default position
        StickModel stick = getOwnerEntity();

        GameContainer container = game.getContainer();

        Vector2f stickSize = stick.getSize();
        initialPos = new Vector2f(initialPosX, container.getHeight() - stickSize.getY() / 2);

        reset();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {

    }

    public void reset() {
        StickModel stick = getOwnerEntity();
        stick.setPosition(initialPos.copy());
    }
}
