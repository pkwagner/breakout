package de.tudarmstadt.informatik.fop.breakout.views;

import de.tudarmstadt.informatik.fop.breakout.models.ClockModel;
import de.tudarmstadt.informatik.fop.breakout.util.Utility;

import eea.engine.component.RenderComponent;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class ClockRenderComponent extends RenderComponent {

    public ClockRenderComponent(String id) {
        super(id);
    }

    @Override
    public ClockModel getOwnerEntity() {
        return (ClockModel) super.getOwnerEntity();
    }

    @Override
    public Vector2f getSize() {
        return getOwnerEntity().getSize();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        ClockModel clock = getOwnerEntity();
        Shape shape = clock.getShape();

        Font font = graphics.getFont();

        String time = Float.toString(Utility.round(clock.getSeconds(), 2));
        int textWidth = font.getWidth(time);
        graphics.drawString(time, shape.getMaxX() - textWidth, clock.getPosition().getY());

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        //ignore
    }
}
