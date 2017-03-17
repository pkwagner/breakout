package de.tudarmstadt.informatik.fop.breakout.states;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.PlayerModel;
import de.tudarmstadt.informatik.fop.breakout.views.gui.Button;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Font;

public class GameoverState extends BasicGameState {

    private Button menuButton, newGameButton;

    private int stateId;

    private Entity backgroundEntity;

    private double totalBlocks, blocks, totalScore, score, time;

    private static final Logger logger = LogManager.getLogger();
    private PlayerModel player;

    public GameoverState(int id) {
        stateId = id;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        addBackground(gameContainer, GameParameters.MENU_BACKGROUND_IMAGE);
        addButtons(gameContainer, stateBasedGame);
    }


    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        score = animateNumbers(score, totalScore, delta);
        blocks = animateNumbers(blocks, totalBlocks, delta);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
            throws SlickException {
        backgroundEntity.render(gameContainer, stateBasedGame, graphics);
        int frameHeight = gameContainer.getHeight();

        graphics.setFont(new TrueTypeFont(new java.awt.Font("Verdana", Font.PLAIN, 20), true));
        graphics.drawString("Score: " + Math.round(score) + " Time: " + time, 0, frameHeight / 4);

        menuButton.render(gameContainer, graphics);
        newGameButton.render(gameContainer, graphics);
    }

    public void load(PlayerModel player, float time) {
        this.totalScore = player.getScore();
        this.totalBlocks = player.getBlockCounter();
        this.time = time;

        // Reset scrolling animation
        score = 0;
        blocks = 0;

        logger.debug("Game over");
        logger.debug("Time: " + time + " score: " + totalScore + " destroyed blocks: " + totalBlocks);
    }

    private double animateNumbers(double f, double total, int delta) {
        double next = f + total * ((delta > 0) ? delta : 1) / 1000D;

        if (next > total) {
            return total;
        }
        return next;
    }

    private void addBackground(GameContainer gameContainer, String file) throws SlickException {
        backgroundEntity = new Entity(GameParameters.GAMEOVER_BACKGROUND_PATH);

        //center the image to be displayed on the complete window
        backgroundEntity.setPosition(new Vector2f(gameContainer.getWidth() / 2, gameContainer.getHeight() / 2));
        backgroundEntity.addComponent(new ImageRenderComponent(new Image(file)));
    }

    private void addButtons(GameContainer gameContainer, final StateBasedGame stateBasedGame) {
        int frameWidth = gameContainer.getWidth();
        int frameHeight = gameContainer.getHeight();

        try {
            Image buttonTex = new Image(GameParameters.ENTRY_IMAGE);
            Image buttonDownTex = new Image(GameParameters.ENTRY_DOWN_IMAGE);

            menuButton = new Button(gameContainer, buttonTex, 0, 0, "Main Menu");
            menuButton.addListener(component -> stateBasedGame.enterState(GameParameters.MAINMENU_STATE));
            menuButton.setLocation(frameWidth / 2 - menuButton.getWidth() / 2, frameHeight / 2);

            newGameButton = new Button(gameContainer, buttonTex, 0, 0, "New Game");
            newGameButton.addListener(component -> stateBasedGame.enterState(GameParameters.GAMEPLAY_STATE));
            newGameButton.setLocation(frameWidth / 2 - menuButton.getWidth() / 2, frameHeight / 2 + menuButton.getHeight() + 10);

            menuButton.setMouseDownImage(buttonDownTex);
            newGameButton.setMouseDownImage(buttonDownTex);
        } catch (SlickException e) {
            logger.error("Error loading buttons");
            e.printStackTrace();
        }
    }

    public int getID() {
        return stateId;
    }
}
