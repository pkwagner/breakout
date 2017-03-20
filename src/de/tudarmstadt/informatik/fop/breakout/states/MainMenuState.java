package de.tudarmstadt.informatik.fop.breakout.states;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

import de.tudarmstadt.informatik.fop.breakout.views.gui.Button;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;

public class MainMenuState extends BasicGameState {

    private final int id;

    private Button buttonStart, buttonHighscore, buttonCredits, buttonSettings;
    private Image background;

    public MainMenuState(int id) {
        this.id = id;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        background = new Image(GameParameters.MENU_BACKGROUND_IMAGE);

        Image buttonImage		= new Image(GameParameters.ENTRY_IMAGE).getScaledCopy(GameParameters.ENTRY_SCALE_FACTOR);
        Image buttonDownImage 	= new Image(GameParameters.ENTRY_DOWN_IMAGE).getScaledCopy(GameParameters.ENTRY_SCALE_FACTOR);
        Image buttonOverImage 	= new Image(GameParameters.ENTRY_OVER_IMAGE).getScaledCopy(GameParameters.ENTRY_SCALE_FACTOR);

        int x = (GameParameters.WINDOW_WIDTH - buttonImage.getWidth()) / 2;
        
        buttonStart = new Button(gameContainer,buttonImage,x,GameParameters.MAIN_MENU_ENTRY_Y,"START GAME");    
        buttonStart.setMouseOverImage(buttonOverImage);
        buttonStart.setMouseDownImage(buttonDownImage);
        
        buttonStart.addListener(source -> {
            stateBasedGame.enterState(GameParameters.GAMEPLAY_STATE);
            gameContainer.setPaused(true);
        });

        buttonHighscore = new Button(gameContainer,buttonImage,x,GameParameters.MAIN_MENU_ENTRY_Y + GameParameters.MAIN_MENU_ENTRY_DISTANCE,"HIGHSCORES");
        buttonHighscore.setMouseOverImage(buttonOverImage);
        buttonHighscore.setMouseDownImage(buttonDownImage);
        
        buttonHighscore.addListener(source -> stateBasedGame.enterState(GameParameters.HIGHSCORE_STATE));


        buttonCredits = new Button(gameContainer,buttonImage,x,GameParameters.MAIN_MENU_ENTRY_Y+ GameParameters.MAIN_MENU_ENTRY_DISTANCE*2,"CREDITS");
        buttonCredits.setMouseOverImage(buttonOverImage);
        buttonCredits.setMouseDownImage(buttonDownImage);
        buttonCredits.addListener(source -> stateBasedGame.enterState(GameParameters.CREDITS_STATE));
        
        buttonSettings = new Button(gameContainer,buttonImage,x,GameParameters.MAIN_MENU_ENTRY_Y+ GameParameters.MAIN_MENU_ENTRY_DISTANCE*3,"SETTINGS");
        buttonSettings.setMouseOverImage(buttonOverImage);
        buttonSettings.setMouseDownImage(buttonDownImage);
        buttonSettings.addListener(source -> stateBasedGame.enterState(GameParameters.SETTINGS_STATE));
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background, 0, 0);
        buttonStart.render(gameContainer, graphics);
        buttonHighscore.render(gameContainer, graphics);
        buttonCredits.render(gameContainer, graphics);
        buttonSettings.render(gameContainer, graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {

    }

    @Override
    public int getID() {
        return id;
    }
}
