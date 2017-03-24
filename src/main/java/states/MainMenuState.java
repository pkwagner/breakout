package states;

import constants.GameParameters;
import views.gui.ButtonView;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.state.StateBasedGame;

import java.util.HashSet;
import java.util.Set;

/**
 * Showing the main submenu where can switch to sub menus or starting a new game.
 */
public class MainMenuState extends AbstractGameState {

    private Set<AbstractComponent> buttons = new HashSet<>();

    public MainMenuState(int id) throws SlickException {
        super(id, GameParameters.MENU_BACKGROUND_IMAGE);
    }

    @Override
    public void init(GameContainer gc, StateBasedGame stateBasedGame) throws SlickException {
        if (isTesting()) {
            return;
        }

        Image buttonImage = new Image(GameParameters.ENTRY_IMAGE).getScaledCopy(GameParameters.ENTRY_SCALE_FACTOR);
        Image buttonDownImage = new Image(GameParameters.ENTRY_DOWN_IMAGE).getScaledCopy(GameParameters.ENTRY_SCALE_FACTOR);
        Image buttonOverImage = new Image(GameParameters.ENTRY_OVER_IMAGE).getScaledCopy(GameParameters.ENTRY_SCALE_FACTOR);

        int posY = GameParameters.MAIN_MENU_ENTRY_Y;

        ButtonView startButton = addMenuEntry(gc, buttonImage, posY, "START GAME");
        addListeners(startButton, stateBasedGame, buttonDownImage, buttonOverImage, GameParameters.GAMEPLAY_STATE);
        startButton.addListener((abstractComponent -> {
            ((GameplayState) stateBasedGame.getState(GameParameters.GAMEPLAY_STATE)).setMultiplayer(false);
        }));

        posY += GameParameters.MAIN_MENU_ENTRY_DISTANCE;
        ButtonView multiplayerButton = addMenuEntry(gc, buttonImage, posY, "MULTIPLAYER");
        addListeners(multiplayerButton, stateBasedGame, buttonDownImage, buttonOverImage, GameParameters.GAMEPLAY_STATE);
        multiplayerButton.addListener((abstractComponent -> {
            ((GameplayState) stateBasedGame.getState(GameParameters.GAMEPLAY_STATE)).setMultiplayer(true);
        }));

        posY += GameParameters.MAIN_MENU_ENTRY_DISTANCE;
        ButtonView highScoreButton = addMenuEntry(gc, buttonImage, posY, "HIGHSCORES");
        addListeners(highScoreButton, stateBasedGame, buttonDownImage, buttonOverImage, GameParameters.HIGHSCORE_STATE);

        posY += GameParameters.MAIN_MENU_ENTRY_DISTANCE;
        ButtonView creditsButton = addMenuEntry(gc, buttonImage, posY, "CREDITS");
        addListeners(creditsButton, stateBasedGame, buttonDownImage, buttonOverImage, GameParameters.CREDITS_STATE);

        posY += GameParameters.MAIN_MENU_ENTRY_DISTANCE;
        ButtonView settingsButton = addMenuEntry(gc, buttonImage, posY, "SETTINGS");
        addListeners(settingsButton, stateBasedGame, buttonDownImage, buttonOverImage, GameParameters.SETTINGS_STATE);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
            throws SlickException {
        super.render(gameContainer, stateBasedGame, graphics);

        for (AbstractComponent button : buttons) {
            button.render(gameContainer, graphics);
        }
    }

    /**
     * Creates a new button and adds it to this state.
     *
     * @param container   screen container
     * @param buttonImage background image of this button
     * @param posY        start y position
     * @param text        button label
     * @return the created button
     */
    private ButtonView addMenuEntry(GameContainer container, Image buttonImage, int posY, String text) {
        int posX = (GameParameters.WINDOW_WIDTH - buttonImage.getWidth()) / 2;
        ButtonView button = new ButtonView(container, buttonImage, posX, posY, text);
        buttons.add(button);
        return button;
    }

    /**
     * Add the change state, mouse over and mouse down listeners.
     *
     * @param button    where to add the listeners
     * @param game      the game instance
     * @param mouseDown which image should be displayed if the user pressed on the button but didn't released it
     * @param mouseOver which image should be displayed if the user hovers over the button
     * @param nextState to which state should we switch if the player clicked on the button
     */
    private void addListeners(ButtonView button, StateBasedGame game, Image mouseDown, Image mouseOver, int nextState) {
        button.setMouseDownImage(mouseDown);
        button.setMouseOverImage(mouseOver);
        button.addListener(source -> game.enterState(nextState));
    }
}
