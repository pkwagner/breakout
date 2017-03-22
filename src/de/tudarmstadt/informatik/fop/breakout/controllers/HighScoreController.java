package de.tudarmstadt.informatik.fop.breakout.controllers;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.exceptions.IllegalHighscoreFormat;
import de.tudarmstadt.informatik.fop.breakout.interfaces.IHighscoreEntry;
import de.tudarmstadt.informatik.fop.breakout.models.HighScoreEntry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controller for managing the list of highscore entries.
 */
public class HighScoreController {

    private final Logger logger = LogManager.getLogger();
    private final Pattern linePattern = Pattern.compile(GameParameters.HIGHSCORE_FILE_ENTRY_SCHEME);
    private List<IHighscoreEntry> entries = new ArrayList<>(GameParameters.HIGHSCORE_MAX_ENTRIES);

    /**
     * Loads the highscore file from disk into an internal representation in memory. If the file doesn't exist
     * at the moment it creates a new one.
     *
     * @throws IOException            if I/O occurred on reading file (i.e. FileNotFound, permission denied)
     * @throws IllegalHighscoreFormat if the lines are not correctly formatted
     */
    public void loadFromFile() throws IOException, IllegalHighscoreFormat {
        logger.info("Loading highscore file");

        Path highScoreFile = Paths.get(GameParameters.HIGHSCORE_FILE);
        if (Files.notExists(highScoreFile)) {
            Files.createFile(highScoreFile);
        }

        load(Files.readAllLines(highScoreFile));
    }

    /**
     * Loads the highscore based on a given list of lines
     *
     * @throws IOException            if I/O occurred on reading file (i.e. FileNotFound, permission denied)
     * @throws IllegalHighscoreFormat if the lines are not correctly formatted
     */
    protected void load(List<String> lines) throws IllegalHighscoreFormat {
        //read all lines and ignore empty lines for example at the end of the file
        Stream<String> lineStream = lines.stream().filter(line -> !line.isEmpty());
        if (!lineStream.allMatch(linePattern.asPredicate())) {
            throw new IllegalHighscoreFormat();
        }

        entries = lines.stream()
                .filter(line -> !line.isEmpty())
                .map(line -> {
                    String[] components = line.split(":");

                    String playerName = components[0];
                    int blocksDestroyed = Integer.parseInt(components[1]);
                    float elapsedTime = Float.parseFloat(components[2]);
                    return new HighScoreEntry(playerName, blocksDestroyed, elapsedTime);
                }).collect(Collectors.toList());
    }

    /**
     * Saves the currently in memory representation of the high scores to disk. It will replace or create a new
     * file if it doesn't exist before automatically.
     *
     * @throws IOException if I/O error occurred on writing/creating the file
     */
    public void saveToFile() throws IOException {
        logger.info("Saving highscore file");

        Files.write(Paths.get(GameParameters.HIGHSCORE_FILE), entries.stream()
                .map(entry -> entry.getPlayerName()
                        + ':' + entry.getNumberOfDestroyedBlocks()
                        + ':' + entry.getElapsedTime())
                .collect(Collectors.toList()));
    }

    /**
     * Creates a new highscore entry and inserts it at the correct position. The amount of entries will still be max
     * 10.
     *
     * @param playerName      display name of that player
     * @param blocksDestroyed amount of blocks destroyed
     * @param elapsedTime     time since starting of the level
     * @throws IllegalArgumentException if blocksDestroyed or elapsed time is negative
     * @throws NullPointerException     if playername is negative
     */
    public void addEntry(String playerName, int blocksDestroyed, float elapsedTime)
            throws IllegalArgumentException, NullPointerException {
        HighScoreEntry newEntry = new HighScoreEntry(playerName, blocksDestroyed, elapsedTime);
        entries.add(newEntry);

        entries = entries.stream()
                .sorted()
                .limit(GameParameters.HIGHSCORE_MAX_ENTRIES)
                .collect(Collectors.toList());

        logger.info("Added highscore entry - new state: {}", entries);
    }

    /**
     * Removes all highscore entries.
     * <p>
     * <b>Warning: It doesn't actually save it to disk</b>
     */
    public void reset() {
        entries.clear();
    }

    /**
     * Gets a list of current highscores. The entries in this list are sorted by their points. The player with the
     * highest points will be at the first position (index 0) and then the player with the second most points and
     * ongoing.
     * <p>
     * This list will only have maximum 10 entries. If there are less scores, the list will also be smaller.
     *
     * @return unmodifiable sorted list of immutable highscore entries
     */
    public List<IHighscoreEntry> getHighscores() {
        //this controller should only handle modification - this prevents modifications without the knowledge of this
        //management class.
        return Collections.unmodifiableList(entries);
    }

    /**
     * Get the highscore entry at certain position. If the list is smaller Optional.empty is returned.
     *
     * @param position the position index with the player with the highest points at the first index
     * @return an Optional of that entry or Optional.empty() if the list is smaller or the position index is invalid.
     */
    public Optional<IHighscoreEntry> getEntryAt(int position) {
        if (position < 0 || position >= entries.size()) {
            return Optional.empty();
        }

        return Optional.of(entries.get(position));
    }
}
