package de.tudarmstadt.informatik.fop.breakout.actions.gui;

import de.tudarmstadt.informatik.fop.breakout.models.gui.Slider;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;

import eea.engine.component.Component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Trigger subclass listeners if the user dragged the slider-ball around the slider box.
 */
public abstract class SliderChangeAction extends MouseInsideAction {

    private final Logger logger = LogManager.getLogger();

    @Override
    public void onUpdate(GameContainer gc, StateBasedGame stateBasedGame, int delta, Component event) {
        Slider ownerEntity = (Slider) event.getOwnerEntity();

        float oldValue = ownerEntity.getValue();
        float newValue = calculateNewPercent(gc.getInput().getMouseX(), ownerEntity.getShape());

        //update only if something changed and not on every frame
        if (oldValue != newValue) {
            logger.debug("Clicked inside the slider: {} - new percent {}", ownerEntity.getID(), newValue);
            ownerEntity.setValue(newValue);
            onChange((Breakout) stateBasedGame, oldValue, newValue);
        }
    }

    /**
     * It calculates where the mouse is located in relation to the width of the slider component.
     * <p/>
     * Examples:
     *
     * <ul>
     *     <li>Right end of the slider => 100% (full width of the component)</li>
     *     <li>Left start of the slider => 0% (min x coordinate of the component)</li>
     * </ul>
     *
     * @param mouseX mouse x position
     * @param ownerShape the bounding box of the owning slider entity
     * @return the percent of the mouse x position in relation to the width
     */
    private float calculateNewPercent(int mouseX, Shape ownerShape) {
        float width = ownerShape.getWidth();
        float startX = mouseX - ownerShape.getMinX();

        float percent = 100 * startX / width;
        return percent / 100;
    }

    /**
     * This method is called if the user moves the slider around. This method is only called if the is actual
     * a change in comparison to the last frame.
     *
     * @param breakout game instance
     * @param oldValue the value from the last frame
     * @param newValue new value
     */
    protected abstract void onChange(Breakout breakout, float oldValue, float newValue);
}
