package de.tudarmstadt.informatik.fop.breakout.controllers;

import de.tudarmstadt.informatik.fop.breakout.models.BallModel;

import eea.engine.component.Component;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class BallController extends Component {

    public BallController(String componentID) {
        super(componentID);
    }

    @Override
    public BallModel getOwnerEntity() {
        return (BallModel) super.getOwnerEntity();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {

    }
}
