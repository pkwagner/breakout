package de.tudarmstadt.informatik.fop.breakout.views.gui;

import de.tudarmstadt.informatik.fop.breakout.events.MouseClickedEvent;
import de.tudarmstadt.informatik.fop.breakout.events.MousePressedEvent;
import eea.engine.entity.Entity;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * TextField that can be set invisible
 */
public class TextField extends org.newdawn.slick.gui.TextField{

    private boolean visible = true;

    public TextField(GUIContext container, Font font, int x, int y, int width, int height) {
        super(container, font, x, y, width, height);
    }

    public TextField(GUIContext container, Font font, int x, int y, int width, int height,ComponentListener componentListener) {
        super(container, font, x, y, width, height,componentListener);
    }

    @Override
    public void render(GUIContext container, Graphics graphics){
        if(visible)
        super.render(container,graphics);
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }
}
