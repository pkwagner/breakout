package de.tudarmstadt.informatik.fop.breakout.models;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import eea.engine.entity.Entity;
import org.newdawn.slick.geom.Vector2f;

public class PlayerModel extends Entity {

    private int blockCounter = 0, score = 0, initialHitPoints, remainingHitPoints;
    private String displayName;
    private boolean dead = false;

    // NOTICE: 'rank' defines the player id
    public PlayerModel(String entityId, int rank, int initialHitPoints) {
        super(entityId);

        this.initialHitPoints = initialHitPoints;
        this.remainingHitPoints = initialHitPoints;

        // Calculate position based on offset
        setPosition(new Vector2f(0, rank * GameParameters.PLAYER_VIEW_SCORE_DISTANCE_Y).add(GameParameters.PLAYER_VIEW_SCORE_OFFSET));
        this.displayName = "Player" + rank;
    }

    public int getBlockCounter() {
        return blockCounter;
    }

    public int getScore() {
        return score;
    }

    public int getRemainingHitPoints() {
        return remainingHitPoints;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isDead() {
        return dead;
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
        if (--remainingHitPoints <= 0) {
            dead = true;
            remainingHitPoints = 0;
        }
    }

    public void reset() {
        this.blockCounter = 0;
        this.score = 0;
        this.remainingHitPoints = initialHitPoints;
        this.dead = false;
    }

    public void heal(int hitPoints) {
        this.remainingHitPoints += hitPoints;
    }
}
