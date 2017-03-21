package de.tudarmstadt.informatik.fop.breakout.models.gui;

import de.tudarmstadt.informatik.fop.breakout.actions.gui.BackButtonAction;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.events.MouseClickedEvent;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

/**
 * ButtonView for switching to the main menu
 */
public class BackButton extends Entity {

    public BackButton() throws SlickException {
        super(GameParameters.ESCAPE_ID);

        setPosition(GameParameters.BACK_BUTTON_POSITION.copy());
        setSize(new Vector2f(64, 64));

        addComponent(new ImageRenderComponent(new Image("/images/back-button.png")));

        MouseClickedEvent clickedEvent = new MouseClickedEvent();
        clickedEvent.addAction(new BackButtonAction());
        addComponent(clickedEvent);
    }
}
