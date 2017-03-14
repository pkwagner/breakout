package de.tudarmstadt.informatik.fop.breakout.views;

import eea.engine.component.RenderComponent;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Show a little piece of text on how to start the game. This text hides if the game was started.
 */
public class StartGameRenderComponent extends RenderComponent {

    private static final String TEXT = "Press spacebar to start the game";

    public StartGameRenderComponent() {
        super("start_game_view");
    }

    @Override
    public Vector2f getSize() {
        return getOwnerEntity().getSize();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        int textWidth = graphics.getFont().getWidth(TEXT);

        Vector2f position = getOwnerEntity().getPosition();
        graphics.drawString(TEXT, position.getX() - textWidth / 2, position.getY());
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        //ignore
    }
}
