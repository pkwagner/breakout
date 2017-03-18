package de.tudarmstadt.informatik.fop.breakout.views;

import de.tudarmstadt.informatik.fop.breakout.models.PlayerModel;
import eea.engine.component.RenderComponent;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class PlayerStatsRenderComponent extends RenderComponent {

    private PlayerModel player;
    private final boolean showPlayerName;

    public PlayerStatsRenderComponent(String id, boolean showPlayerName) {
        super(id);

        this.showPlayerName = showPlayerName;
    }

    @Override
    public Vector2f getSize() {
        // TODO
        return new Vector2f(0, 0);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        String scoreStats = (showPlayerName ? player.getDisplayName() + " | " : "") + "Score: " + player.getScore();
        String healthStats = "Leben: " + player.getRemainingHitPoints();
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