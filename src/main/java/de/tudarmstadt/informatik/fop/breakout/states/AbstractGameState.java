package de.tudarmstadt.informatik.fop.breakout.states;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;

import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Shared implementation of all de.tudarmstadt.informatik.fop.breakout.states to make the actual implementation cleaner with less duplicates.
 */
public abstract class AbstractGameState extends BasicGameState {

    protected final StateBasedEntityManager entityManager = StateBasedEntityManager.getInstance();

    protected final int stateId;
    private final Renderable background;

    /**
     * Creates a new basic game state that holds the common implementation for background image and state id.
     *
     * @param stateId   unique state id
     * @param imagePath relative path to the background image
     * @throws SlickException if the image cannot be loaded
     */
    public AbstractGameState(int stateId, String imagePath) throws SlickException {
        this(stateId, imagePath, false);
    }

    /**
     * Creates a new basic game state that holds the common implementation for background image and state id.
     *
     * @param stateId   unique state id
     * @param imagePath relative path to the background image or spritesheet
     * @param animation if the background image should be animated
     * @throws SlickException if the image cannot be loaded
     */
    public AbstractGameState(int stateId, String imagePath, boolean animation) throws SlickException {
        this.stateId = stateId;

        /*if (isTesting()) {
            background = (x, y) -> {
            };
        } else */if (animation) {
            this.background = new Image(imagePath);
        } else {
            SpriteSheet sprites = new SpriteSheet(imagePath, GameParameters.WINDOW_WIDTH, GameParameters.WINDOW_HEIGHT);
            this.background = new Animation(sprites, 70);
        }
    }


    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        //draw the background first in order to let others overdraw it
        background.draw(0, 0);

        entityManager.renderEntities(container, game, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        entityManager.updateEntities(container, game, delta);
    }

    /**
     * Checks this instance is only running for unit tests and therefore no entities should be added to prevent
     * OpenGL de.tudarmstadt.informatik.fop.breakout.exceptions.
     *
     * @return if we are in unit testing mode
     */
    protected boolean isTesting() {
        return Breakout.getDebug();
    }

    /**
     * Adds the given entity to this state.
     *
     * @param entity the entity that should be added
     */
    public void addEntity(Entity entity) {
        entityManager.addEntity(stateId, entity);
    }

    /**
     * Removes the given entity from this state
     *
     * @param entity the entity that should be removed
     */
    public void removeEntity(Entity entity) {
        entityManager.removeEntity(stateId, entity);
    }

    protected void getEntity(String id) {
        entityManager.getEntity(stateId, id);
    }

    /**
     * Removes all entities of this state.
     */
    public void clearEntities() {
        entityManager.clearEntitiesFromState(stateId);
    }

    @Override
    public int getID() {
        return stateId;
    }
}
