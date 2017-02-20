package de.tudarmstadt.informatik.fop.breakout.test.testcases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.tudarmstadt.informatik.fop.breakout.test.adapter.Adapter;


public class TestMapParser {

	private Adapter adapter;

	@Before
	public void init() {
		adapter = new Adapter();
		adapter.initializeGame();
	}

	@Test
	public void testFirstRow() {
		for (int i = 0; i <= 15; i++) {
			int hitsLeft = adapter.getHitsLeft("block" + i + "_0");
			assertEquals("Block should have 1 hit left", 1, hitsLeft);
			assertTrue("Block should have hits left",
					adapter.hasHitsLeft("block" + i + "_0"));
		}
	}

	@Test
	public void testSomeBlocks() {
		assertEquals("Block should have 2 hit left", 2,
				adapter.getHitsLeft("block1_1"));
		assertTrue("Block should have hits left",
				adapter.hasHitsLeft("block1_1"));

		assertEquals("Block should have 3 hit left", 3,
				adapter.getHitsLeft("block2_4"));
		assertTrue("Block should have hits left",
				adapter.hasHitsLeft("block2_4"));

		assertEquals("Block should have 4 hit left", 4,
				adapter.getHitsLeft("block7_5"));
		assertTrue("Block should have hits left",
				adapter.hasHitsLeft("block7_5"));
	}

	@Test(expected = NullPointerException.class)
	public void testBlockNotExists() {
		adapter.getHitsLeft("block2_2");
	}

}
