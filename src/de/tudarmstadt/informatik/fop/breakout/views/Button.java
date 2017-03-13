package de.tudarmstadt.informatik.fop.breakout.views;

import org.lwjgl.util.vector.Vector2f;

import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.*;

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
        font = new TrueTypeFont(new Font("Arial",1,50),true);

        fontOffset = new Vector2f(60,15);
    }

    @Override
    public void render(GUIContext guiContext, Graphics graphics) {
        super.render(guiContext,graphics);
        graphics.setFont(font);
        graphics.drawString(label,getX()+fontOffset.getX(),getY()+fontOffset.getY());
    }
}
