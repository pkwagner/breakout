package de.tudarmstadt.informatik.fop.breakout.controllers;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.AbstractBlockModel;

import eea.engine.component.Component;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class BlockController extends Component {

    private final GameParameters.BlockType blockType;

    public BlockController(String componentID, GameParameters.BlockType blockType) {
        super(componentID);

        this.blockType = blockType;
    }

    @Override
    public AbstractBlockModel getOwnerEntity() {
        return (AbstractBlockModel) super.getOwnerEntity();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        // TODO Do sth.
    }
}
