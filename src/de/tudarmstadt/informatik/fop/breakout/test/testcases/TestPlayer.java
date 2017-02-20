package de.tudarmstadt.informatik.fop.breakout.test.testcases;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.tudarmstadt.informatik.fop.breakout.test.adapter.Adapter;



public class TestPlayer {

	private Adapter adapter;
	
	@Before
	public void init(){
		adapter= new Adapter();	
		adapter.initializeGame();
	}
	
	@Test
	public void testSetAndGetHitsLeft() {
		this.adapter.setLives(6);
		assertEquals("setAndGet Lives shoulb be 6", 6, this.adapter.getLivesLeft(),0);		
	}
	
	@Test
	public void testaddLives(){
		this.adapter.addLives(2);
		assertEquals("after addLives the value should be 5", 5, this.adapter.getLivesLeft(),0);
	}
	
	@Test
	public void testHasLivesLeft(){
		assertTrue("block should have hits left", this.adapter.hasLivesLeft());
		this.adapter.setLives(0);
		assertFalse("block should have no hits left", this.adapter.hasLivesLeft());
	}

}
