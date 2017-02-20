package de.tudarmstadt.informatik.fop.breakout.interfaces;

public interface IHighscoreEntry {
  public String getPlayerName();

  /**
   * Returns the number of destroyed blocks
   * 
   * @return number of destroyed blocks
   */
  public int getNumberOfDestroyedBlocks();

  /**
   * Returns elapsed time
   * 
   * @return elapsed time
   */
  public float getElapsedTime();

  /**
   * Returns the calculated points
   * 
   * @return calculated points
   */
  public double getPoints();
}
