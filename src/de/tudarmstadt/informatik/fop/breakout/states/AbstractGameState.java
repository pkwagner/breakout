package de.tudarmstadt.informatik.fop.breakout.states;

import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;

import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Shared implementation of all states to make the actual implementation cleaner with less duplicates.
 */
public abstract class AbstractGameState extends BasicGameState {

    private final StateBasedEntityManager entityManager = StateBasedEntityManager.getInstance();

    private final int stateId;
    private final Renderable background;

    public AbstractGameState(int stateId, Renderable background) {
        this.stateId = stateId;
        this.background = background;
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
     * OpenGL exceptions.
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
