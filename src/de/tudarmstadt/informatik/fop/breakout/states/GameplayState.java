package de.tudarmstadt.informatik.fop.breakout.states;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.BallController;
import de.tudarmstadt.informatik.fop.breakout.controllers.MapController;
import de.tudarmstadt.informatik.fop.breakout.controllers.StickController;
import de.tudarmstadt.informatik.fop.breakout.factories.BorderFactory;
import de.tudarmstadt.informatik.fop.breakout.models.BallModel;
import de.tudarmstadt.informatik.fop.breakout.models.StickModel;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import de.tudarmstadt.informatik.fop.breakout.views.BallRenderComponent;
import de.tudarmstadt.informatik.fop.breakout.views.StickRenderComponent;

import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameplayState extends BasicGameState {

    private final StateBasedEntityManager entityManager = StateBasedEntityManager.getInstance();
    private final int stateId;
    private Image background;

    public GameplayState(int id) {
        this.stateId = id;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        if (Breakout.getDebug()) {
            return;
        }

        background = new Image(GameParameters.BACKGROUND_IMAGE);

        StickModel stickModel = new StickModel(GameParameters.STICK_ID);
        StickController stickController = new StickController("stickController");
        stickModel.addComponent(stickController);
        stickModel.addComponent(new StickRenderComponent());

        BallModel ballModel = new BallModel(GameParameters.BALL_ID);
        BallController ballController = new BallController("ball_controller");
        ballModel.addComponent(ballController);
        ballModel.addComponent(new BallRenderComponent());

        //borders
        Entity leftBorder = new BorderFactory(GameParameters.BorderType.LEFT).createEntity();
        Entity rightBorder = new BorderFactory(GameParameters.BorderType.RIGHT).createEntity();
        Entity topBorder = new BorderFactory(GameParameters.BorderType.TOP).createEntity();

        ballController.init(stateBasedGame);
        stickController.init(stateBasedGame);

        addEntity(stickModel);
        addEntity(ballModel);
        addEntity(leftBorder);
        addEntity(rightBorder);
        addEntity(topBorder);

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
        graphics.drawImage(background,0,0);
        entityManager.renderEntities(gameContainer, stateBasedGame, graphics);
    }

    public void addEntity(Entity entity) {
        entityManager.addEntity(stateId, entity);
    }

    @Override
    public int getID() {
        return stateId;
    }
}
