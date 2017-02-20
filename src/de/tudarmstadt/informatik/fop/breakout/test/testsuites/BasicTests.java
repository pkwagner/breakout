package de.tudarmstadt.informatik.fop.breakout.test.testsuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.tudarmstadt.informatik.fop.breakout.test.testcases.TestBall;
import de.tudarmstadt.informatik.fop.breakout.test.testcases.TestBlock;
import de.tudarmstadt.informatik.fop.breakout.test.testcases.TestKeyBoardInput;
import de.tudarmstadt.informatik.fop.breakout.test.testcases.TestPlayer;



@RunWith(Suite.class)
@SuiteClasses({ TestBall.class, TestBlock.class, TestPlayer.class, TestKeyBoardInput.class})
public class BasicTests {

}
