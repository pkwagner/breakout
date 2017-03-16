package de.tudarmstadt.informatik.fop.breakout.controllers;

import de.tudarmstadt.informatik.fop.breakout.actions.items.AbstractItemAction;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.ItemModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import eea.engine.component.Component;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class ItemTimer extends Component {

    private double time, clock = 0;
    private final AbstractItemAction itemAction;

    public ItemTimer(String componentID, double time, AbstractItemAction itemAction) {
        super(componentID);

        this.time = time;
        this.itemAction = itemAction;
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        // Increase clock counter
        clock += delta/1000D;

        // Check if timer is over
        if (clock >= time) {
            // Remove timer (this component)
            this.getOwnerEntity().removeComponent(this);

            // Trigger onDisable
            itemAction.onDisable();
        }
    }
}
