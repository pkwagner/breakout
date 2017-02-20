package de.tudarmstadt.informatik.fop.breakout.test.testcases;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.tudarmstadt.informatik.fop.breakout.interfaces.IHitable;
import de.tudarmstadt.informatik.fop.breakout.test.adapter.Adapter;

public class TestBlock {

  // Leave as is - declares a "Block" entity implementing the IHitable interface
    private IHitable block;
    private Adapter adapter;

    @Before
    public void init(){
	adapter = new Adapter();
	adapter.initializeGame();
	block = adapter.createBlockInstance("block", 3);
    }
	
    @Test
    public void testSetAndGetHitsLeft() {
	block.setHitsLeft(6);
	assertEquals("setAndGet Hits left should be 6", 6, block.getHitsLeft(),0);		
    }
    
    @Test
    public void testAddHitsLeft(){
	block.addHitsLeft(2);
	assertEquals("after abbHitsLeft the value should be 5", 5, block.getHitsLeft(),0);
    }
    
    @Test
    public void testHasHitsLeft(){
	assertTrue("block should have hits left", block.hasHitsLeft());
	block.setHitsLeft(0);
	assertFalse("block should have no hits left", block.hasHitsLeft());
    }
}
