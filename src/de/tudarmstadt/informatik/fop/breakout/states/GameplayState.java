package de.tudarmstadt.informatik.fop.breakout.states;

import de.tudarmstadt.informatik.fop.breakout.controllers.BallController;
import de.tudarmstadt.informatik.fop.breakout.controllers.MapController;
import de.tudarmstadt.informatik.fop.breakout.controllers.StickController;
import de.tudarmstadt.informatik.fop.breakout.models.BallModel;
import de.tudarmstadt.informatik.fop.breakout.models.StickModel;
import de.tudarmstadt.informatik.fop.breakout.views.BallRenderComponent;
import de.tudarmstadt.informatik.fop.breakout.views.StickRenderComponent;

import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameplayState extends BasicGameState {

    private final StateBasedEntityManager entityManager = StateBasedEntityManager.getInstance();
    private final int stateId;

    public GameplayState(int id) {
        this.stateId = id;
    }

    @Override
    public int getID() {
        return stateId;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        StickModel stickModel = new StickModel("stickModel");
        stickModel.addComponent(new StickController("stick_controller"));
        stickModel.addComponent(new StickRenderComponent("stick_view"));

        BallModel ballModel = new BallModel("ball");
        ballModel.addComponent(new BallController("ball_controller"));
        ballModel.addComponent(new BallRenderComponent("ball_view"));

        addEntity(stickModel);
        addEntity(ballModel);

        MapController mapController = new MapController(this);
        mapController.loadMap();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta)
            throws SlickException {
        entityManager.updateEntities(gameContainer, stateBasedGame, delta);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
            throws SlickException {
        entityManager.renderEntities(gameContainer, stateBasedGame, graphics);
    }

    public void addEntity(Entity entity) {
        entityManager.addEntity(stateId, entity);
    }
}
