package de.tudarmstadt.informatik.fop.breakout.models.gui;

import eea.engine.entity.Entity;

/**
 * A checkbox model that could be turned off and on.
 *
 * @see de.tudarmstadt.informatik.fop.breakout.views.gui.CheckboxRenderComponent
 * @see de.tudarmstadt.informatik.fop.breakout.actions.gui.CheckBoxClickAction
 */
public class Checkbox extends Entity {

    private boolean enabled;

    public Checkbox(String entityID, boolean def) {
        super(entityID);

        this.enabled = def;
    }

    /**
     * Get the checkbox stats
     *
     * @return true if the checkbox is activated
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set whether the checkbox is enabled
     *
     * @param enabled if the checkbox is enabled or not
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
