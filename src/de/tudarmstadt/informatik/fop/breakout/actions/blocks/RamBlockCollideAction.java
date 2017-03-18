package de.tudarmstadt.informatik.fop.breakout.actions.blocks;

import de.tudarmstadt.informatik.fop.breakout.models.BallModel;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.AbstractBlockModel;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;

public class RamBlockCollideAction extends AbstractBlockCollideAction {

	public RamBlockCollideAction(AbstractBlockModel blockModel, BallModel ballModel, Breakout breakout) {
		super(blockModel, ballModel, breakout);
	}

}
