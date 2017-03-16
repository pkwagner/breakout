package de.tudarmstadt.informatik.fop.breakout.controllers.blocks;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.actions.StickMoveAction;
import de.tudarmstadt.informatik.fop.breakout.models.BallModel;
import de.tudarmstadt.informatik.fop.breakout.models.Direction;
import de.tudarmstadt.informatik.fop.breakout.models.StickModel;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.RamBlock;

public class RamBlockController extends AbstractBlockController {

	private RamBlock blockModel;
	Vector2f positionA,positionB; //the positions between which the block oscillates
	
	
	public RamBlockController(String componentID) {
		super(componentID);
		// TODO Auto-generated constructor stub
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
	
    public void init(StateBasedGame game) {
        RamBlock blockModel = getOwnerEntity();
                
    }
	
    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
    }
}
