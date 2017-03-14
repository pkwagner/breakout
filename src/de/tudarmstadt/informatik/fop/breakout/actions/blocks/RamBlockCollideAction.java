package de.tudarmstadt.informatik.fop.breakout.actions.blocks;

import de.tudarmstadt.informatik.fop.breakout.models.BallModel;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.AbstractBlockModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;

public class RamBlockCollideAction extends AbstractBlockCollideAction {

	public RamBlockCollideAction(AbstractBlockModel blockModel, BallModel ballModel, GameplayState gameplayState) {
		super(blockModel, ballModel, gameplayState);
	}

}
