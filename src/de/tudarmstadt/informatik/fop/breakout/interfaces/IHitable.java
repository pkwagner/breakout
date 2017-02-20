package de.tudarmstadt.informatik.fop.breakout.interfaces;

/**
 * Interface for degradable Entities 
 * @author Tobias Otterbein, Benedikt Wartusch
 *
 */
public interface IHitable{
	
	/**
	 * Sets a number of necessary hits for degrading an entity
	 * @param value number of necessary hits
	 */
	public void setHitsLeft(int value);
	
	/**
	 * Returns the number of necessary hits for degrading an entity
	 * @return number of hits
	 */
	public int getHitsLeft();
	
	/**
	 * Adds a number of necessary hits for degrading an entity
	 * 
	 * @param value
	 *            number of hits added
	 */
	public void addHitsLeft(int value);
	
	
	/**
	 * Returns whether the entity has hits left
	 * 
	 * @return true, if entity has hits left, else false
	 */
	public boolean hasHitsLeft();

}

