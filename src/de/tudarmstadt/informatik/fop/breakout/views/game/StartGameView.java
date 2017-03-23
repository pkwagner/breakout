package de.tudarmstadt.informatik.fop.breakout.views.game;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.KeyBinding;

import eea.engine.component.RenderComponent;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Show a little piece of text on how to start the game. This text hides if the game was started.
 */
public class StartGameView extends RenderComponent {

    private static final String FORMAT = "Press %s to start the game";

    public StartGameView() {
        super(GameParameters.GAMESTART_ENTITY_ID + GameParameters.EXT_VIEW);
    }

    @Override
    public Vector2f getSize() {
        return getOwnerEntity().getSize();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        String text = String.format(FORMAT, KeyBinding.START_GAME.getKeyName());

        int textWidth = graphics.getFont().getWidth(text);

        Vector2f position = getOwnerEntity().getPosition();
        graphics.drawString(text, position.getX() - textWidth / 2, position.getY());
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        //ignore
    }
}
