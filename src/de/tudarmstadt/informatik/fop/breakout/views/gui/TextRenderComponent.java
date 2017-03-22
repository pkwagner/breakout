package de.tudarmstadt.informatik.fop.breakout.views.gui;

import eea.engine.component.RenderComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by martin on 19.03.17.
 */
public class TextRenderComponent extends RenderComponent{

    private TrueTypeFont font;
    private String text = "Text";

    private static final Logger logger = LogManager.getLogger();


    public TextRenderComponent(String id,TrueTypeFont font){
        super(id);
        this.font = font;
    }

    @Override
    public Vector2f getSize() {
        return new Vector2f(font.getWidth(text),font.getHeight(text));
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        Vector2f position = getOwnerEntity().getPosition();

        graphics.setFont(font);
        graphics.drawString(text,position.x,position.y);
        graphics.resetFont();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {

    }

    public void setText(String text){
        this.text = text;
    }
}
