package de.tudarmstadt.informatik.fop.breakout.states;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

import de.tudarmstadt.informatik.fop.breakout.views.Button;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;

public class MainMenuState extends BasicGameState {

    private final int id;

    private Button buttonStart, buttonHighscore, buttonCredits;
    private Image background;

    public MainMenuState(int id) {
        this.id = id;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        background = new Image(GameParameters.MENU_BACKGROUND_IMAGE);

        Image buttonTex = new Image(GameParameters.ENTRY_IMAGE);
        Image buttonDownTex = new Image(GameParameters.ENTRY_DOWN_IMAGE);

        buttonStart = new Button(gameContainer,buttonTex,40,150,"Start Game");
        buttonStart.setMouseOverImage(buttonDownTex);
        buttonStart.addListener(source -> {
            stateBasedGame.enterState(GameParameters.GAMEPLAY_STATE);
            gameContainer.setPaused(true);
        });

        buttonHighscore = new Button(gameContainer,buttonTex,40,300,"Highscores");
        buttonHighscore.setMouseOverImage(buttonDownTex);
        buttonHighscore.addListener(source -> stateBasedGame.enterState(GameParameters.HIGHSCORE_STATE));

        buttonCredits = new Button(gameContainer,buttonTex,40,450,"Credits");
        buttonCredits.setMouseOverImage(buttonDownTex);
        buttonCredits.addListener(source -> stateBasedGame.enterState(GameParameters.CREDITS_STATE));
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background,0,0);
        buttonStart.render(gameContainer,graphics);
        buttonHighscore.render(gameContainer,graphics);
        buttonCredits.render(gameContainer,graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {

    }

    @Override
    public int getID() {
        return id;
    }
}