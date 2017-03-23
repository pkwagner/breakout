package de.tudarmstadt.informatik.fop.breakout.models;

import de.tudarmstadt.informatik.fop.breakout.interfaces.IHighscoreEntry;

import java.util.Objects;

/**
 * Represents a immutable highscore entry
 */
public class HighScoreEntry implements IHighscoreEntry, Comparable<IHighscoreEntry> {

    private final String playerName;
    private final int blocksDestroyed;
    private final float elapsedTime;

    /**
     * Creates a new highscore entry
     *
     * @param playerName      the player name that should be displayed
     * @param blocksDestroyed amount of blocks destroyed
     * @param elapsedTime     amount of elapsed time since the starting of the level
     * @throws IllegalArgumentException if blocksDestroyed or elapsed time is negative
     * @throws NullPointerException     if playername is negative
     */
    public HighScoreEntry(String playerName, int blocksDestroyed, float elapsedTime)
            throws IllegalArgumentException, NullPointerException {
        Objects.requireNonNull(playerName, "Playername cannot be null");
        if (blocksDestroyed < 0 || elapsedTime < 0) {
            throw new IllegalArgumentException("destroyed blocks or elapsed time is negative");
        }

        this.playerName = playerName;
        this.blocksDestroyed = blocksDestroyed;
        //keep only three decimal places for better serialization
        this.elapsedTime = Math.round(elapsedTime * 1_000) / 1_000F;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public int getNumberOfDestroyedBlocks() {
        return blocksDestroyed;
    }

    @Override
    public float getElapsedTime() {
        return elapsedTime;
    }

    @Override
    public double getPoints() {
        float points = (float) (Math.pow(blocksDestroyed, 2) * (4 * blocksDestroyed / elapsedTime));
        //round it with only decimal places
        return Math.round(points * 100) / 100D;
    }

    @Override
    public int compareTo(IHighscoreEntry other) {
        //reverse order - highest entry comes first
        return Double.compare(other.getPoints(), getPoints());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        HighScoreEntry otherEntry = (HighScoreEntry) other;
        return blocksDestroyed == otherEntry.blocksDestroyed
                && Float.compare(otherEntry.elapsedTime, elapsedTime) == 0
                && Objects.equals(playerName, otherEntry.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, blocksDestroyed, elapsedTime);
    }

    @Override
    public String toString() {
        return "HighScoreEntry{" +
                "playerName='" + playerName +
                ", blocksDestroyed=" + blocksDestroyed +
                ", elapsedTime=" + elapsedTime +
                '}';
    }
}
