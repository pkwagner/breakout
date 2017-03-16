package de.tudarmstadt.informatik.fop.breakout.controllers.blocks;

import de.tudarmstadt.informatik.fop.breakout.interfaces.IHitable;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.AbstractBlockModel;

import de.tudarmstadt.informatik.fop.breakout.views.blocks.AbstractBlockRenderComponent;
import de.tudarmstadt.informatik.fop.breakout.views.blocks.RamBlockRenderComponent;
import de.tudarmstadt.informatik.fop.breakout.views.blocks.SimpleBlockRenderComponent;
import eea.engine.component.Component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public abstract class AbstractBlockController extends Component implements IHitable {

    private static final Logger logger = LogManager.getLogger();

    public AbstractBlockController(String componentID) {
        super(componentID);
    }

    @Override
    public AbstractBlockModel getOwnerEntity() {
        return (AbstractBlockModel) super.getOwnerEntity();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {

    }
    
    public void init(StateBasedGame game){
    	
    }

    /**
     * Creates the view for a given block
     *
     * @param block the block for which a view shall be created
     * @return
     * @throws SlickException
     */
    public static AbstractBlockRenderComponent createBlockView(AbstractBlockModel block) {
        try {
            switch (block.getType()) {
                case SIMPLE:	return new SimpleBlockRenderComponent(block.getHitsLeft());
                case RAM:		return new RamBlockRenderComponent();
                default:
                    logger.error("Some error occured during the creation of the view of the block: " + block.getID());
                    return new SimpleBlockRenderComponent(1);
            }
        } catch (SlickException e) {
            logger.error("The following error occured during the creation of block " + block.getID() + ": " + e);
            return null;
        }
    }
}
