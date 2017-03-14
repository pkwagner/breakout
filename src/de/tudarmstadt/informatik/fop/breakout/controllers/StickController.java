package de.tudarmstadt.informatik.fop.breakout.controllers;

import de.tudarmstadt.informatik.fop.breakout.actions.StickMoveAction;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.StickModel;

import eea.engine.component.Component;
import eea.engine.event.basicevents.KeyDownEvent;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class StickController extends Component {

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

    public void init(StateBasedGame game) {
        KeyDownEvent leftEvent = new KeyDownEvent(Input.KEY_LEFT);
        leftEvent.addAction(new StickMoveAction(GameParameters.Direction.LEFT, getOwnerEntity()));

        KeyDownEvent rightEvent = new KeyDownEvent(Input.KEY_RIGHT);
        rightEvent.addAction(new StickMoveAction(GameParameters.Direction.RIGHT, getOwnerEntity()));

        getOwnerEntity().addComponent(leftEvent);
        getOwnerEntity().addComponent(rightEvent);

        //set default position
        StickModel stick = getOwnerEntity();

        GameContainer container = game.getContainer();

        Vector2f stickSize = stick.getSize();
        Vector2f initialPos = new Vector2f(container.getWidth() / 2
                , container.getHeight() - stickSize.getY() / 2);
        stick.setPosition(initialPos);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {

    }
}
