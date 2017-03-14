package de.tudarmstadt.informatik.fop.breakout.models;

import de.tudarmstadt.informatik.fop.breakout.actions.BackButtonAction;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.basicevents.MouseClickedEvent;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

/**
 * Button for switching to the main menu
 */
public class BackButton extends Entity {

    public BackButton() throws SlickException {
        super("back_button");

        setPosition(new Vector2f(50, 50));
        setSize(new Vector2f(64, 64));

        addComponent(new ImageRenderComponent(new Image("/images/back-button.png")));

        MouseClickedEvent clickedEvent = new MouseClickedEvent();
        clickedEvent.addAction(new BackButtonAction());
        addComponent(clickedEvent);
    }
}
