package de.tudarmstadt.informatik.fop.breakout.controllers.game.blocks;

import de.tudarmstadt.informatik.fop.breakout.models.game.blocks.AbstractBlockModel;

import eea.engine.component.Component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class SimpleBlockController extends Component {

    private static final Logger logger = LogManager.getLogger();

    public SimpleBlockController(String componentID) {
        super(componentID);
    }

    @Override
    public AbstractBlockModel getOwnerEntity() {
        return (AbstractBlockModel) super.getOwnerEntity();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
    }

    public void init(StateBasedGame game) {

    }
}
