package de.tudarmstadt.informatik.fop.breakout.test.testcases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.tudarmstadt.informatik.fop.breakout.test.adapter.AdapterExtended;


public class TestHighscore {
	 
	AdapterExtended adapter;
	
	@Before
	public void setUp() {
		adapter = new AdapterExtended();
		adapter.resetHighscore();
	}
		
	@Test
	public void testCreateHighscoreEntry() {
		
		assertEquals("Highscore count should be zero after reset", 0, adapter.getHighscoreCount());
		
		adapter.addHighscore("PlayerA", 42, 123);
		
		assertEquals("Highscore count should be one after adding an entry", 1, adapter.getHighscoreCount());
		assertEquals("The playername of the highscore entry is incorrect", "PlayerA", adapter.getNameAtHighscorePosition(0));
		assertEquals("The amount of destroyed Block of the highscore entry is incorrect", 42, adapter.getNumberOfDesBlocksAtHighscorePosition(0));
		assertEquals("The time elapsed of the highscore entry is incorrect", 123, adapter.getTimeElapsedAtHighscorePosition(0));	
	}
	
	@Test
	public final void testMaximumHighscoreCount() {
		
		assertEquals("Highscore count should be zero after reset", 0, adapter.getHighscoreCount());
		
		adapter.addHighscore("PlayerOne", 2, 123);
		assertEquals("Highscore count should be 1", 1, adapter.getHighscoreCount());
		adapter.addHighscore("PlayerTwo", 10, 123);
		assertEquals("Highscore count should be 2", 2, adapter.getHighscoreCount());
		adapter.addHighscore("PlayerThree", 100, 123);
		assertEquals("Highscore count should be 4", 3, adapter.getHighscoreCount());
		adapter.addHighscore("PlayerFour", 54, 123);
		assertEquals("Highscore count should be 4", 4, adapter.getHighscoreCount());
		adapter.addHighscore("PlayerFive", 11, 123);
		assertEquals("Highscore count should be 5", 5, adapter.getHighscoreCount());
		adapter.addHighscore("PlayerSix", 340, 123);
		assertEquals("Highscore count should be 6", 6, adapter.getHighscoreCount());
		adapter.addHighscore("PlayerSeven", 15, 123);
		assertEquals("Highscore count should be 7", 7, adapter.getHighscoreCount());
		adapter.addHighscore("PlayerEight", 132, 123);
		assertEquals("Highscore count should be 8", 8, adapter.getHighscoreCount());
		adapter.addHighscore("PlayerNine", 9, 123);
		assertEquals("Highscore count should be 8", 9, adapter.getHighscoreCount());
		adapter.addHighscore("PlayerTen", 3, 123);
		assertEquals("Highscore count should be 9", 10, adapter.getHighscoreCount());
		
		adapter.addHighscore("PlayerEleven", 2, 123);
		adapter.addHighscore("PlayerTwelve", 2, 123);
		adapter.addHighscore("PlayerThirteen", 2, 123);
		adapter.addHighscore("PlayerFourteen", 2, 123);
		assertEquals("Only a maximum of 10 highscores should be saved", 10, adapter.getHighscoreCount());
	}
	
	@Test
	public final void testSortHighscoresByNumberOfDestroyedBlock() {
		assertEquals("Highscore count should be zero after reset", 0, adapter.getHighscoreCount());
	
		adapter.addHighscore("Player6", 103, 123);
		adapter.addHighscore("Player1", 1, 123);
		adapter.addHighscore("Player3", 42, 123);
		adapter.addHighscore("Player5", 99, 123);
		adapter.addHighscore("Player2", 12, 123);
		adapter.addHighscore("Player4", 60, 123);
		
		assertEquals("Highscore count should be 6", 6, adapter.getHighscoreCount());
		
		assertEquals("Player1", adapter.getNameAtHighscorePosition(5));
		assertEquals(1, adapter.getNumberOfDesBlocksAtHighscorePosition(5));
		assertEquals("Player2", adapter.getNameAtHighscorePosition(4));
		assertEquals(12, adapter.getNumberOfDesBlocksAtHighscorePosition(4));
		assertEquals("Player3", adapter.getNameAtHighscorePosition(3));
		assertEquals(42, adapter.getNumberOfDesBlocksAtHighscorePosition(3));
		assertEquals("Player4", adapter.getNameAtHighscorePosition(2));
		assertEquals(60, adapter.getNumberOfDesBlocksAtHighscorePosition(2));
		assertEquals("Player5", adapter.getNameAtHighscorePosition(1));
		assertEquals(99, adapter.getNumberOfDesBlocksAtHighscorePosition(1));
		assertEquals("Player6", adapter.getNameAtHighscorePosition(0));
		assertEquals(103, adapter.getNumberOfDesBlocksAtHighscorePosition(0));
		
		for (int i = 0; i < 6; i++) {
			assertEquals("The amount of time passed is incorrect", 123, adapter.getTimeElapsedAtHighscorePosition(i));
		}
	}
	
	@Test
	public final void testSortHighscoresByTimeElapsed() {
		assertEquals("Highscore count should be zero after reset", 0, adapter.getHighscoreCount());
	
		
		adapter.addHighscore("Player4", 42, 2045);
		adapter.addHighscore("Player1", 42, 1337);
		adapter.addHighscore("Player3", 42, 1879);
		adapter.addHighscore("Player2", 42, 1512);
		
		
		assertEquals("Highscore count should be 4", 4, adapter.getHighscoreCount());
		
		assertEquals("Player1", adapter.getNameAtHighscorePosition(0));
		assertEquals(1337, adapter.getTimeElapsedAtHighscorePosition(0));
		assertEquals("Player2", adapter.getNameAtHighscorePosition(1));
		assertEquals(1512, adapter.getTimeElapsedAtHighscorePosition(1));
		assertEquals("Player3", adapter.getNameAtHighscorePosition(2));
		assertEquals(1879, adapter.getTimeElapsedAtHighscorePosition(2));
		assertEquals("Player4", adapter.getNameAtHighscorePosition(3));
		assertEquals(2045, adapter.getTimeElapsedAtHighscorePosition(3));
		
		for (int i = 0; i < 4; i++) {
			assertEquals("The Number of destroyed blocks is incorrect", 42, adapter.getNumberOfDesBlocksAtHighscorePosition(i));
		}
	}
	
	
	@Test
	public final void testNullAccess() {
		assertEquals("Highscore count should be zero after reset", 0, adapter.getHighscoreCount());
		
		adapter.addHighscore("PlayerOne", 2, 3);
		
		assertNull("Accessing a non existent position should resturn null", adapter.getNameAtHighscorePosition(-1));
		assertNull("Accessing a non existent position should resturn null", adapter.getNameAtHighscorePosition(1));
		
		assertEquals("Accessing a non existent position should resturn null", -1, adapter.getNumberOfDesBlocksAtHighscorePosition(-1));
		assertEquals("Accessing a non existent position should resturn null", -1, adapter.getNumberOfDesBlocksAtHighscorePosition(1));
		
		assertEquals("Accessing a non existent position should resturn null", -1, adapter.getTimeElapsedAtHighscorePosition(-1));
		assertEquals("Accessing a non existent position should resturn null", -1, adapter.getTimeElapsedAtHighscorePosition(1));
		
		assertEquals("PlayerOne", adapter.getNameAtHighscorePosition(0));
		assertEquals(2, adapter.getNumberOfDesBlocksAtHighscorePosition(0));
		assertEquals(3, adapter.getTimeElapsedAtHighscorePosition(0));
		
	}
	
}
