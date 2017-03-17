package de.tudarmstadt.informatik.fop.breakout.actions.gui;

import de.tudarmstadt.informatik.fop.breakout.actions.MouseInsideAction;
import de.tudarmstadt.informatik.fop.breakout.models.gui.Checkbox;

import eea.engine.component.Component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Generic clickbox action. It will trigger if someone clicks inside the checkbox component and therefore toggles
 * the value and calls the onToggle method for subclasses listening to that.
 */
public abstract class CheckBoxClickAction extends MouseInsideAction {

    private final Logger logger = LogManager.getLogger();

    @Override
    public void onUpdate(GameContainer gc, StateBasedGame stateBasedGame, int delta, Component event) {
        Checkbox ownerEntity = (Checkbox) event.getOwnerEntity();

        //toggle the value
        boolean newVal = !ownerEntity.isEnabled();
        ownerEntity.setEnabled(newVal);

        logger.debug("Clicked checkbox to {} on {}", newVal, ownerEntity.getID());
        onToggle(newVal);
    }

    /**
     * Called if the user toggles the checkbox and either enables or disables it.
     *
     * @param newVal the value that was set due this event
     */
    public abstract void onToggle(boolean newVal);
}
