package de.tudarmstadt.informatik.fop.breakout.views.blocks;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

public class RamBlockRenderComponent extends AbstractBlockRenderComponent {

	public RamBlockRenderComponent() throws SlickException {
		super(new Image(GameParameters.RAM_BLOCK_IMAGE));
	}

}
