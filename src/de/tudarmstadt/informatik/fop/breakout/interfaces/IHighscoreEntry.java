package de.tudarmstadt.informatik.fop.breakout.interfaces;

public interface IHighscoreEntry {
    String getPlayerName();

    /**
     * Returns the number of destroyed blocks
     *
     * @return number of destroyed blocks
     */
    int getNumberOfDestroyedBlocks();

    /**
     * Returns elapsed time
     *
     * @return elapsed time
     */
    float getElapsedTime();

    /**
     * Returns the calculated points
     *
     * @return calculated points
     */
    double getPoints();
}
