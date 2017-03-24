package de.tudarmstadt.informatik.fop.breakout.views.game;

import de.tudarmstadt.informatik.fop.breakout.models.game.PlayerModel;

import eea.engine.component.RenderComponent;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class PlayerStatsView extends RenderComponent {

    private PlayerModel player;
    private final boolean showPlayerName;

    public PlayerStatsView(String id, boolean showPlayerName) {
        super(id);

        this.showPlayerName = showPlayerName;
    }

    @Override
    public Vector2f getSize() {
        return new Vector2f(200, 20);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        String scoreStats = (showPlayerName ? player.getDisplayName() + " | " : "") + "Blocks: " + player.getBlockCounter();
        String healthStats = "Leben: " + player.getRemainingHealthpoints();
        String stats = scoreStats + " // " + healthStats;
        graphics.drawString(stats, player.getPosition().getX(), player.getPosition().getY());
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {

    }

    public void init() {
        player = (PlayerModel) getOwnerEntity();
    }
}
