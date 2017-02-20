package de.tudarmstadt.informatik.fop.breakout.test.testsuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.tudarmstadt.informatik.fop.breakout.test.testcases.TestHighscore;
import de.tudarmstadt.informatik.fop.breakout.test.testcases.TestMapParser;


@RunWith(Suite.class)
@SuiteClasses({ TestMapParser.class, TestHighscore.class })
public class ExtendedTests {

}
