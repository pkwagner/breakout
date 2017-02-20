package de.tudarmstadt.informatik.fop.breakout.views;

import eea.engine.component.RenderComponent;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class BallRenderComponent extends RenderComponent {

    public BallRenderComponent(String id) {
        super(id);
    }

    @Override
    public Vector2f getSize() {
        return null;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {

    }
}
