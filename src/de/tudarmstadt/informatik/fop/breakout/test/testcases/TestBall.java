package de.tudarmstadt.informatik.fop.breakout.test.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters.BorderType;
import de.tudarmstadt.informatik.fop.breakout.factories.BorderFactory;
import de.tudarmstadt.informatik.fop.breakout.test.adapter.Adapter;
import eea.engine.entity.Entity;

public class TestBall {

  private Adapter adapter;

  @Before
  public void init() {
    adapter = new Adapter();
    adapter.initializeGame();
  }

  @Test
  public void testSetAndGetSpeed() {
    adapter.setSpeed(2);
    assertEquals("speed should be 2", 2, adapter.getSpeed(), 0);
  }

  @Test
  public void testColidesWithTopBorder() {
    adapter.changeToGameplayState();
    Entity topBorder = new BorderFactory(BorderType.TOP).createEntity();
    float borderY = topBorder.getShape().getCenterY() + topBorder.getSize().getY() * 0.5f;

    // angle lower 90 degrees
    adapter.setRotation(45);
    // ball do not collide
    adapter.setPosition(new Vector2f(200, borderY + adapter.getSize().getY() / 2 + 1));
    assertFalse("no collision at top Border with angle 45 and wrong position", adapter.collides(topBorder));
    // ball do collide
    adapter.setPosition(new Vector2f(200, borderY + adapter.getSize().getY() / 2));
    assertTrue("collision at top Border with angle 45 and right position", adapter.collides(topBorder));

    // angle higher than 90 degrees and lower or equal to 270 degrees
    adapter.setRotation(90);
    assertFalse("no collision at top border with angle 90", adapter.collides(topBorder));
    adapter.setRotation(270);
    assertFalse("no collision at top border with angle 270", adapter.collides(topBorder));

    // angle between 271 and 360 within 269 and 360
    adapter.setRotation(271);
    // ball do not collide
    adapter.setPosition(new Vector2f(200, borderY + adapter.getSize().getY() / 2 + 1));
    assertFalse("no collision at top border with angle 271 and wrong position", adapter.collides(topBorder));
    // ball do collide
    adapter.setPosition(new Vector2f(200, borderY + adapter.getSize().getY() / 2));
    assertTrue("collision at top border with angle 271 and right position", adapter.collides(topBorder));

    adapter.setRotation(360);
    // ball do not collide
    adapter.setPosition(new Vector2f(200, borderY + adapter.getSize().getY() / 2 + 1));
    assertFalse("no collision at top border with angle 360 and wrong position", adapter.collides(topBorder));
    // ball do collide
    adapter.setPosition(new Vector2f(200, borderY + adapter.getSize().getY() / 2));
    assertTrue("collision at top border with angle 360 and right position", adapter.collides(topBorder));

  }

  @Test
  public void testColidesWithRightBorder() {
    Entity rightBorder = new BorderFactory(BorderType.RIGHT).createEntity();
    float borderX = rightBorder.getShape().getCenterX() - rightBorder.getSize().getX() * 0.5f;
    // angle not between 179 and 1
    adapter.setRotation(180);
    assertFalse("no collision at right border with angle 180", adapter.collides(rightBorder));
    adapter.setRotation(0);
    assertFalse("no collision at right border with angle 0", adapter.collides(rightBorder));

    // angle between 179 and 1
    adapter.setRotation(179);
    // ball do not collide
    adapter.setPosition(new Vector2f(borderX - adapter.getSize().getX() / 2 - 1, 200));
    assertFalse("no collision at right border with angle 179 and wrong position", adapter.collides(rightBorder));
    // ball do collide
    adapter.setPosition(new Vector2f(borderX - adapter.getSize().getX() / 2 + 1, 200));
    assertTrue("collision at right border with angle 179 and right position", adapter.collides(rightBorder));

    // angle between 179 and 1
    adapter.setRotation(1);
    // ball do not collide
    adapter.setPosition(new Vector2f(borderX - adapter.getSize().getX() / 2 - 1, 200));
    assertFalse("no collision at right border with angle 1 and wrong position", adapter.collides(rightBorder));
    // ball do collide
    adapter.setPosition(new Vector2f(borderX - adapter.getSize().getX() / 2, 200));
    assertTrue("collision at right border with angle 1 and right position", adapter.collides(rightBorder));

  }

  @Test
  public void testColidesWithLeftBorder() {
    Entity leftBorder = new BorderFactory(BorderType.LEFT).createEntity();
    float borderX = leftBorder.getShape().getCenterX() + leftBorder.getSize().getX() * 0.5f;
    // angle not between 359 and 181
    adapter.setRotation(360);
    assertFalse("no collision at left border with angle 360", adapter.collides(leftBorder));
    adapter.setRotation(180);
    assertFalse("no collision at left border with angle 180", adapter.collides(leftBorder));

    // angle between 359 and 181
    adapter.setRotation(181);
    // ball do not collide
    adapter.setPosition(new Vector2f(borderX + adapter.getSize().getX() / 2 + 1, 200));
    assertFalse("no collision at right border with angle 181 and wrong position", adapter.collides(leftBorder));
    // ball do collide
    adapter.setPosition(new Vector2f(borderX + adapter.getSize().getX() / 2, 200));
    assertTrue("collision at right border with angle 181 and right position", adapter.collides(leftBorder));

    // angle between 359 and 181
    adapter.setRotation(359);
    // ball do not collide
    adapter.setPosition(new Vector2f(borderX + adapter.getSize().getX() / 2 + 1, 200));
    assertFalse("no collision at right border with angle 359 and wrong position", adapter.collides(leftBorder));
    // ball do collide
    adapter.setPosition(new Vector2f(borderX + adapter.getSize().getX() / 2, 200));
    assertTrue("collision at right border with angle 359 and right position", adapter.collides(leftBorder));
  }

  @Test
  public void testColidesWithStick() {
    // stick has collision offset of 10. to read in the exercise
    Entity stick = new Entity(GameParameters.STICK_ID);
    stick.setPosition(new Vector2f(GameParameters.WINDOW_WIDTH / 2, GameParameters.WINDOW_HEIGHT - 10));
    stick.setPassable(false);
    // angle not between 91 and 269
    adapter.setRotation(90);
    assertFalse("no collision with stick with angle 90", adapter.collides(stick));
    adapter.setRotation(270);
    assertFalse("no collision with stick with angle 270", adapter.collides(stick));

    // angle between 91 and 269
    adapter.setRotation(91);
    float x1Stick = stick.getShape().getCenterX() + stick.getSize().getX() * 0.5f;
    float x2Stick = stick.getShape().getCenterX() - stick.getSize().getX() * 0.5f;
    float y1Stick = stick.getShape().getCenterY() - stick.getSize().getY() * 0.5f - adapter.getSize().getY() / 2;

    // ball to high to colide with stick
    adapter.setPosition(new Vector2f(x1Stick + 10, y1Stick + adapter.getSize().getY() / 2 - 1));
    assertFalse("no collision with stick with angle 91, but the ball is to high", adapter.collides(stick));
    // ball not to high and hits the stick
    adapter.setPosition(new Vector2f(x1Stick + 10, y1Stick + adapter.getSize().getY() / 2));
    assertTrue("collision with stick with angle 91", adapter.collides(stick));
    // ball is right beside the stick
    adapter.setPosition(new Vector2f(x1Stick + 10 + 1, y1Stick + adapter.getSize().getY() / 2));
    assertFalse("no collision with stick with angle 91, ball is right beside the stick", adapter.collides(stick));
    // ball not to high and hits the stick
    adapter.setPosition(new Vector2f(x2Stick - 10, y1Stick + adapter.getSize().getY() / 2));
    assertTrue("collision with stick with angle 91", adapter.collides(stick));
    // ball is right beside the stick
    adapter.setPosition(new Vector2f(x1Stick - 10 - 1, y1Stick + adapter.getSize().getY() / 2));
    assertFalse("no collision with stick with angle 91, ball is left beside the stick", adapter.collides(stick));

    adapter.setRotation(269);
    // ball to high to colide with stick
    adapter.setPosition(new Vector2f(x1Stick + 10, y1Stick + adapter.getSize().getY() / 2 - 1));
    assertFalse("no collision with stick with angle 269, but the ball is to high", adapter.collides(stick));
    // ball not to high and hits the stick
    adapter.setPosition(new Vector2f(x1Stick + 10, y1Stick + adapter.getSize().getY() / 2));
    assertTrue("collision with stick with angle 269", adapter.collides(stick));
    // ball is right beside the stick
    adapter.setPosition(new Vector2f(x1Stick + 10 + 1, y1Stick + adapter.getSize().getY() / 2));
    assertFalse("no collision with stick with angle 269, ball is right beside the stick", adapter.collides(stick));
    // ball not to high and hits the stick
    adapter.setPosition(new Vector2f(x2Stick - 10 + 1, y1Stick + adapter.getSize().getY() / 2));
    assertTrue("collision with stick with angle 269", adapter.collides(stick));
    // ball is right beside the stick
    adapter.setPosition(new Vector2f(x1Stick - 10 - 1, y1Stick + adapter.getSize().getY() / 2));
    assertFalse("no collision with stick with angle 269, ball is left beside the stick", adapter.collides(stick));

  }

  @Test
  public void testColidesWithOthers() {
    Entity dummy = null;
    assertFalse("no collision with null", adapter.collides(dummy));

    dummy = adapter.createBallInstance("ball2");
    dummy.setPassable(false);
    dummy.setPassable(true);
    assertFalse("no collision with passable entities", adapter.collides(dummy));

  }

}
