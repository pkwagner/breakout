package de.tudarmstadt.informatik.fop.breakout.controllers;

import de.tudarmstadt.informatik.fop.breakout.exceptions.IllegalHighscoreFormat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class HighScoreControllerTest {

    @Test(expected = IllegalHighscoreFormat.class)
    public void testIllegalFormat() throws Exception {
        HighScoreController controller = new HighScoreController();

        List<String> testLst = new ArrayList<>();
        testLst.add("wrong");
        controller.load(testLst);
    }

    @Test
    public void testLoading() throws Exception {
        HighScoreController controller = new HighScoreController();

        List<String> testLst = new ArrayList<>();
        testLst.add("test:123:123.0");
        testLst.add("test:123:123.345");
        testLst.add("test:123:123.62");
        controller.load(testLst);
    }
}