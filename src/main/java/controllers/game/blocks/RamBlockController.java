package controllers.game.blocks;

import constants.GameParameters;
import models.Direction;
import models.game.blocks.RamBlock;
import util.Utility;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class RamBlockController extends SimpleBlockController {

    private RamBlock blockModel;
    private Vector2f positionA, positionB; //the positions between which the block oscillates
    private Direction direction;
    private int length;

    public RamBlockController(String componentID, Direction direction, int length) {
        super(componentID);
        this.direction = direction;
        this.length = length;
    }

    @Override
    public RamBlock getOwnerEntity() {
        return (RamBlock) super.getOwnerEntity();
    }

    @Override
    public void init(StateBasedGame game) {
        blockModel = getOwnerEntity();
        positionA = blockModel.getPosition().copy();

        switch (direction) {
            case UP:
                positionB = positionA.copy().add(new Vector2f(0, -(length * GameParameters.BLOCK_HEIGHT)));
                break;
            case DOWN:
                positionB = positionA.copy().add(new Vector2f(0, length * GameParameters.BLOCK_HEIGHT));
                break;
            case LEFT:
                positionB = positionA.copy().add(new Vector2f(-(length * GameParameters.BLOCK_WIDTH), 0));
                break;
            case RIGHT:
                positionB = positionA.copy().add(new Vector2f(length * GameParameters.BLOCK_WIDTH, 0));
                break;
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        blockModel.setPosition(new Vector2f(Utility.map(blockModel.getRamPosition(), 0, 1, positionA.getX(), positionB.getX()), Utility.map(blockModel.getRamPosition(), 0, 1, positionA.getY(), positionB.getY())));
    }
}
