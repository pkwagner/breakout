package de.tudarmstadt.informatik.fop.breakout.controllers.blocks;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.actions.StickMoveAction;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.BallModel;
import de.tudarmstadt.informatik.fop.breakout.models.StickModel;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.RamBlock;
import de.tudarmstadt.informatik.fop.breakout.util.Utility;

public class RamBlockController extends AbstractBlockController {

	private RamBlock blockModel;
	Vector2f positionA,positionB; //the positions between which the block oscillates
	GameParameters.Direction direction;
	int length;
	
	public RamBlockController(String componentID,GameParameters.Direction direction,int length) {
		super(componentID);
		this.direction = direction;
		this.length = length;
	}

	@Override
	public void setHitsLeft(int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getHitsLeft() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addHitsLeft(int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasHitsLeft() {
		// TODO Auto-generated method stub
		return false;
	}

    @Override
    public RamBlock getOwnerEntity() {
        return (RamBlock) super.getOwnerEntity();
    }

    public void setOwnerEntity(RamBlock owningEntity) {
        super.setOwnerEntity(owningEntity);
    }
	
    @Override
    public void init(StateBasedGame game) {
        blockModel = getOwnerEntity();
        positionA = blockModel.getPosition().copy();
        
        switch(direction){
        	case UP: positionB = positionA.copy().add(new Vector2f(0,-(length * GameParameters.BLOCK_HEIGHT)));
        		break;
        	case DOWN: positionB = positionA.copy().add(new Vector2f(0,length * GameParameters.BLOCK_HEIGHT));
    			break;
        	case LEFT: positionB = positionA.copy().add(new Vector2f(-(length * GameParameters.BLOCK_WIDTH),0));
    			break;
        	case RIGHT: positionB = positionA.copy().add(new Vector2f(length * GameParameters.BLOCK_WIDTH,0));
    			break;
        }
    }
	
    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
    	blockModel.setPosition(new Vector2f(Utility.map(blockModel.getRamPosition(),0,1,positionA.getX(),positionB.getX()) ,Utility.map(blockModel.getRamPosition(),0,1,positionA.getY(),positionB.getY())));
    }
}
