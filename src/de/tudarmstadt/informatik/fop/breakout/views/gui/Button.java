package de.tudarmstadt.informatik.fop.breakout.views.gui;

import org.lwjgl.util.vector.Vector2f;

import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.*;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

import java.awt.Font;


/**
 * Created by martin on 28.02.2017.
 */
public class Button extends MouseOverArea{

    private String label;
    private TrueTypeFont font;

    private Vector2f fontOffset;

    public Button(GUIContext guiContext, Image image, int x, int y, String buttonLabel) throws SlickException{
        super(guiContext,image,x,y);
        label = buttonLabel;
        font = new TrueTypeFont(new Font("Poplar",Font.PLAIN,35),true);
        
        int xOffset = (int) (image.getWidth() - font.getWidth(label))/2;
        int yOffset = (int) (image.getHeight() - font.getHeight(label))/2;
        
        fontOffset = new Vector2f(xOffset,yOffset + GameParameters.ENTRY_Y_OFFSET);
    }

    @Override
    public void render(GUIContext guiContext, Graphics g) {
        super.render(guiContext,g);
        g.setFont(font);
        g.setColor(new Color(0));
        g.drawString(label,getX()+fontOffset.getX(),getY()+fontOffset.getY());
        g.resetFont();
        g.setColor(new Color(255,255,255));
    }
}
