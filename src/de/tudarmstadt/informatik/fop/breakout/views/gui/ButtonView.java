package de.tudarmstadt.informatik.fop.breakout.views.gui;

import org.lwjgl.util.vector.Vector2f;

import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.*;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

import java.awt.Font;


/**
 *
 */
public class ButtonView extends MouseOverArea {

    private String label;
    private TrueTypeFont font;

    private Vector2f fontOffset;

    private boolean visible = true;

    public ButtonView(GUIContext guiContext, Image image, int x, int y, String buttonLabel) {
        super(guiContext, image, x, y);

        label = buttonLabel;
        font = new TrueTypeFont(new Font("Poplar", Font.PLAIN, 35), true);

        int xOffset = (image.getWidth() - font.getWidth(label)) / 2;
        int yOffset = (image.getHeight() - font.getHeight(label)) / 2;

        if (xOffset<0)xOffset=0;
        if (yOffset<0)yOffset=0;

        fontOffset = new Vector2f(xOffset, yOffset + GameParameters.ENTRY_Y_OFFSET);
    }

    @Override
    public void render(GUIContext guiContext, Graphics g) {
        if(visible) {
            super.render(guiContext, g);

            g.setFont(font);
            g.setColor(new Color(0));
            g.drawString(label, getX() + fontOffset.getX(), getY() + fontOffset.getY());
            g.resetFont();
            g.setColor(new Color(255, 255, 255));
        }
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }
}
