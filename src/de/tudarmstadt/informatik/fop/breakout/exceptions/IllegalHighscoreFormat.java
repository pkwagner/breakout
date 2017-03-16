package de.tudarmstadt.informatik.fop.breakout.exceptions;

/**
 * This exception is thrown if the highscore file isn't correctly formatted. Maybe it happen because of an
 * older version of this game or because the user manually edited the file.
 */
public class IllegalHighscoreFormat extends Exception {

    public IllegalHighscoreFormat() {
        this("The highscore file is in a invalid format");
    }

    public IllegalHighscoreFormat(String message) {
        super(message);
    }
}
