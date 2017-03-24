package de.tudarmstadt.informatik.fop.breakout.interfaces;

/**
 * Interface for degradable Entities
 *
 * @author Tobias Otterbein, Benedikt Wartusch
 */
public interface IHitable {

    /**
     * Sets a number of necessary hits for degrading an entity
     *
     * @param value number of necessary hits
     */
    void setHitsLeft(int value);

    /**
     * Returns the number of necessary hits for degrading an entity
     *
     * @return number of hits
     */
    int getHitsLeft();

    /**
     * Adds a number of necessary hits for degrading an entity
     *
     * @param value number of hits added
     */
    void addHitsLeft(int value);


    /**
     * Returns whether the entity has hits left
     *
     * @return true, if entity has hits left, else false
     */
    boolean hasHitsLeft();

}

