package de.tudarmstadt.informatik.fop.breakout.models;

import de.tudarmstadt.informatik.fop.breakout.interfaces.IHighscoreEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HighScoreEntryTest {

    @Test
    public void testRounding() throws Exception {
        HighScoreEntry testEntry = new HighScoreEntry("Test", 123, 123.456789F);

        assertEquals(123.457F, testEntry.getElapsedTime(), 0F);
        Double points = testEntry.getPoints();
        assertTrue(points.toString().split("\\.")[1].length() <= 3);
    }

    @Test(expected = NullPointerException.class)
    public void testNullName() throws Exception {
        new HighScoreEntry(null, 123, 123);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeBlocks() throws Exception {
        new HighScoreEntry("Test", -123, 123);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeTime() throws Exception {
        new HighScoreEntry("Test", 123, -123);
    }

    @Test
    public void testPointsBlocks() throws Exception {
        HighScoreEntry first = new HighScoreEntry("First", 600, 3);
        HighScoreEntry second = new HighScoreEntry("Second", 500, 3);

        Assert.assertTrue(first.getPoints() > second.getPoints());
    }

    @Test
    public void testPointsTime() throws Exception {
        HighScoreEntry first = new HighScoreEntry("First", 500, 60);
        HighScoreEntry second = new HighScoreEntry("Second", 500, 100);

        Assert.assertTrue(first.getPoints() > second.getPoints());
    }

    @Test
    public void testSortingOrder() throws Exception {
        List<IHighscoreEntry> entries = new ArrayList<>();

        HighScoreEntry first = new HighScoreEntry("First", 600, 3);
        HighScoreEntry second = new HighScoreEntry("Second", 500, 4);
        HighScoreEntry third = new HighScoreEntry("Third", 400, 5);

        entries.add(second);
        entries.add(first);
        entries.add(third);

        //check that the entry with the highest points comes first
        List<IHighscoreEntry> sortedList = entries.stream().sorted().collect(Collectors.toList());
        assertEquals(first, sortedList.get(0));
        assertEquals(second, sortedList.get(1));
        assertEquals(third, sortedList.get(2));
    }
}