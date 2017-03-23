package de.tudarmstadt.informatik.fop.breakout.models.game;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.game.StickController;
import eea.engine.entity.Entity;

public class PlayerModel extends Entity {

    private int blockCounter, score;
    private int remainingHealthpoints = GameParameters.PLAYER_DEFAULT_HEALTHPOINTS;
    private int initialHealthpoints = remainingHealthpoints;

    private String displayName;
    private final boolean secondPlayer;
    private StickController stickController;

    public PlayerModel(String entityId, boolean secondPlayer) {
        super(entityId);

        this.secondPlayer = secondPlayer;

        // Calculate position based on offset
        setPosition(secondPlayer ? GameParameters.PLAYER_VIEW_SCORE_OFFSET_PLAYER2 : GameParameters.PLAYER_VIEW_SCORE_OFFSET);
        this.displayName = (secondPlayer) ? "Player2" : "Player1";
    }

    public int getBlockCounter() {
        return blockCounter;
    }

    public int getRemainingHealthpoints() {
        return remainingHealthpoints;
    }

    public String getDisplayName() {
        return displayName;
    }

    public StickController getStickController() {
        return stickController;
    }

    public void setStickController(StickController stickController) {
        this.stickController = stickController;
    }

    public boolean isDead() {
        return remainingHealthpoints == 0;
    }

    public boolean isSecondPlayer() {
        return secondPlayer;
    }

    /**
     * Increases block counter by 1 and adds given points to score
     *
     * @param scorePoints the additional points that should be added to score
     */
    public void destroyBlock(int scorePoints) {
        blockCounter++;
        score += scorePoints;
    }

    /**
     * Decreases hit points counter by one
     */
    public void hit() {
        if (--remainingHealthpoints <= 0) {
            remainingHealthpoints = 0;
        }
    }

    public void reset() {
        this.blockCounter = 0;
        this.score = 0;
        this.remainingHealthpoints = initialHealthpoints;
    }

    public void addHealthpoints(int hitPoints) {
        this.remainingHealthpoints += hitPoints;
    }

    public void setRemainingHealthpoints(int remainingHealthpoints) {
        this.remainingHealthpoints = remainingHealthpoints;
    }
}
