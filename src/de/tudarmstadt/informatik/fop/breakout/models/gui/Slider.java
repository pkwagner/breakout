package de.tudarmstadt.informatik.fop.breakout.models.gui;

import eea.engine.entity.Entity;

/**
 * A round circle that can be tragged on a line. The circle represents the percentual value in relation to the total
 * width of this component.
 *
 * @see de.tudarmstadt.informatik.fop.breakout.views.gui.SliderRenderComponent
 * @see de.tudarmstadt.informatik.fop.breakout.actions.gui.SliderChangeAction
 */
public class Slider extends Entity {

    private float value;

    public Slider(String entityID, float def) {
        super(entityID);

        this.value = def;
    }

    /**
     * Get the percent value of the ball
     *
     * @return value between 0.0f (0%) and 1.0f (100%)
     */
    public float getValue() {
        return value;
    }

    /**
     * Sets the percent value of the circle
     *
     * @param value value between 0.0f (0%) and 1.0f (100%)
     */
    public void setValue(float value) {
        this.value = value;
    }
}
