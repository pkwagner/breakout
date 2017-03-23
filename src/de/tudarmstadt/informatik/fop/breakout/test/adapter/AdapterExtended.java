package de.tudarmstadt.informatik.fop.breakout.test.adapter;

import de.tudarmstadt.informatik.fop.breakout.interfaces.IHighscoreEntry;
import de.tudarmstadt.informatik.fop.breakout.controllers.HighScoreController;

import java.util.List;

public class AdapterExtended extends Adapter {

    private final HighScoreController scores = new HighScoreController();

    /**
     * Use this constructor to set up everything you need.
     */
    public AdapterExtended() {
        super();
    }

	/* *************************************************** 
     * ********************** Highscore ******************
	 * *************************************************** */

    /**
     * adds a new highscore entry for the player. Note: only the 10 best entries are kept.
     *
     * @param playerName        name of the player
     * @param numberOfDesBlocks number of destroyed blocks
     * @param elapsedTime       time since starting the map/level
     */
    public void addHighscore(String playerName, int numberOfDesBlocks, long elapsedTime) {
        scores.addEntry(playerName, numberOfDesBlocks, elapsedTime);
    }

    /**
     * resets (clears) the highscore list
     */
    public void resetHighscore() {
        scores.reset();
    }

    /**
     * returns all highscore entries as a list
     *
     * @return the list of all current highscore entries
     */
    public List<? extends IHighscoreEntry> getHighscores() {
        return scores.getHighscores();
    }

    /**
     * returns the number of entries in the highscore list
     *
     * @return returns the number of highscore entries - between 0 and 10
     */
    public int getHighscoreCount() {
        return getHighscores().size();
    }

    /**
     * returns the name of the player at the given position of the highscore table
     *
     * @param position the position in the list, should be inside
     *                 the interval [0, max(9, getHighscoreCount() - 1)]
     * @return the name of the player at the given position or null if the index is invalid
     * (negative, greater than 9 and/or greater than or equal to the entry count)
     */
    public String getNameAtHighscorePosition(int position) {
        return scores.getEntryAt(position)
                .map(IHighscoreEntry::getPlayerName)
                .orElseGet(() -> null);
    }

    /**
     * return the time since starting this level for the highscore entry at the given position
     *
     * @param position the position in the list, should be inside
     *                 the interval [0, max(9, getHighscoreCount() - 1)]
     * @return the time elapsed for the given highscore entry if this exists; otherwise -1
     */
    public int getTimeElapsedAtHighscorePosition(int position) {
        return scores.getEntryAt(position)
                .map(IHighscoreEntry::getElapsedTime)
                .map(Number::intValue)
                .orElseGet(() -> -1);
    }

    /**
     * return the number of blocks destroyed by highscore entry at the given position
     *
     * @param position the position in the list, should be inside
     *                 the interval [0, max(9, getHighscoreCount() - 1)]
     * @return the number of blocks destroyed for the given highscore entry if this exists; otherwise -1
     */
    public int getNumberOfDesBlocksAtHighscorePosition(int position) {
        return scores.getEntryAt(position)
                .map(IHighscoreEntry::getNumberOfDestroyedBlocks)
                .orElseGet(() -> -1);
    }
}
