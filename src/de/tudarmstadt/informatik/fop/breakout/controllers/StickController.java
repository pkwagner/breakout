package de.tudarmstadt.informatik.fop.breakout.controllers;

import de.tudarmstadt.informatik.fop.breakout.models.StickModel;
import eea.engine.component.Component;
import org.newdawn.slick.GameContainer;
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

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {

    }
}
