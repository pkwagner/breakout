package de.tudarmstadt.informatik.fop.breakout.states;

import de.tudarmstadt.informatik.fop.breakout.actions.gui.NewGameAction;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.HighScoreController;
import de.tudarmstadt.informatik.fop.breakout.models.HighScoreEntry;
import de.tudarmstadt.informatik.fop.breakout.models.PlayerModel;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import de.tudarmstadt.informatik.fop.breakout.views.gui.ButtonView;
import de.tudarmstadt.informatik.fop.breakout.views.gui.TextField;
import de.tudarmstadt.informatik.fop.breakout.views.gui.TextRenderComponent;
import eea.engine.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Font;


public class GameoverState extends AbstractGameState {

    private ButtonView menuButton, newGameButton, highscoreButton;

    private TextRenderComponent[] scoreText, blocksText,nameText,timeText;
    private TextField nameField;

    private int numPlayers;

    private double[] totalBlocks, blocks, totalScore, score;
    private float time;

    private static final Logger logger = LogManager.getLogger();

    private HighScoreController highScoreController;

    public GameoverState(int id) throws SlickException {
        super(id, GameParameters.GAMEOVER_BACKGROUND_IMAGE);
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        if (isTesting()) {
            return;
        }

        addGUI(gameContainer, stateBasedGame);
        highScoreController = ((Breakout) stateBasedGame).getHighScoreController();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        super.update(gameContainer, stateBasedGame, delta);

        for(int i = 0; i < numPlayers; i++) {
            score[i] = animateNumbers(score[i], totalScore[i], delta);
            blocks[i] = animateNumbers(blocks[i], totalBlocks[i], delta);
        }

        for(int i = 0;i < numPlayers ; i++) {
            scoreText[i].setText("Score: " + (int) score[i]);
            blocksText[i].setText("Blocks: " + (int) blocks[i]);
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
            throws SlickException {
        super.render(gameContainer, stateBasedGame, graphics);

        menuButton.render(gameContainer, graphics);
        newGameButton.render(gameContainer, graphics);

        highscoreButton.render(gameContainer, graphics);
        nameField.render(gameContainer, graphics);
    }

    public void load(PlayerModel player, float time) {
        load(new PlayerModel[]{player},time);
        nameText[0].getOwnerEntity().setVisible(false);

        int rank = highScoreController.getRank((float) totalScore[0]);
        // if the score is under the top 10 ask the player to enter his name
        if (rank < GameParameters.HIGHSCORE_MAX_ENTRIES)
            showNewHighscoreDialog(rank + 1);
        else hideNewHighscoreDialog();
    }

    public void load(PlayerModel[] players, float time) {
        clearEntities();
        numPlayers = players.length;

        totalScore = new double[numPlayers];
        totalBlocks = new double[numPlayers];
        score = new double[numPlayers];
        blocks = new double[numPlayers];

        for(int i = 0; i < numPlayers; i++) {
            this.totalScore[i] = new HighScoreEntry("", players[i].getBlockCounter(), time).getPoints();
            this.totalBlocks[i] = players[i].getBlockCounter();
        }
        this.time = time;

        hideNewHighscoreDialog();

        addStatsGUI(numPlayers);

        for(int i = 0; i < numPlayers; i++) {
            // reset scrolling animation
            score[i] = 0;
            blocks[i] = 0;

            timeText[i].setText("Time: " + (int) time + " sec.");
            nameText[i].setText(players[i].getDisplayName());
        }

        logger.debug("Game over");
        logger.debug("Time: " + time + " score: " + totalScore + " destroyed blocks: " + totalBlocks);
    }

    private double animateNumbers(double f, double total, int delta) {
        double next = f + total * ((delta > 0) ? delta : 1) / 100D;

        if (next > total) {
            return total;
        }
        return next;
    }

    private void addGUI(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        TrueTypeFont font = new TrueTypeFont(new Font("Poplar", Font.PLAIN, 35), true);

        int frameWidth = gameContainer.getWidth();
        int frameHeight = gameContainer.getHeight();

        int leftBorder = frameWidth * 1 / 3;
        int rightBorder = frameWidth * 5 / 8;
        int upperBorder =  frameHeight * 2 / 4 + 25;


        nameField = new TextField(gameContainer, font, leftBorder, upperBorder, frameWidth - leftBorder - (frameWidth - rightBorder), font.getHeight());
        nameField.setVisible(false);

        //------------
        //Add Buttons
        try {
            Image buttonTex = new Image(GameParameters.ENTRY_IMAGE);
            Image buttonDownTex = new Image(GameParameters.ENTRY_DOWN_IMAGE);
            Image buttonMouseOverTex = new Image(GameParameters.ENTRY_OVER_IMAGE);
            Image buttonTexSmall = new Image(GameParameters.ENTRY_IMAGE_SMALL);

            menuButton = new ButtonView(gameContainer, buttonTex, 0, 0, "Main Menu");
            menuButton.addListener(component -> stateBasedGame.enterState(GameParameters.MAINMENU_STATE));
            menuButton.setLocation(frameWidth / 2 - menuButton.getWidth() / 2, upperBorder + nameField.getHeight()+20);

            newGameButton = new ButtonView(gameContainer, buttonTex, 0, 0, "New Game");
            newGameButton.addListener(new NewGameAction(gameContainer, stateBasedGame));
            newGameButton.setLocation(frameWidth / 2 - menuButton.getWidth() / 2, menuButton.getY() + menuButton.getHeight() + 10);

            highscoreButton = new ButtonView(gameContainer, buttonTexSmall, 0, 0, "Save");
            highscoreButton.addListener(component -> addHighscore(stateBasedGame));
            highscoreButton.setLocation(rightBorder, upperBorder);
            highscoreButton.setVisible(false);

            menuButton.setMouseDownImage(buttonDownTex);
            menuButton.setMouseOverImage(buttonMouseOverTex);
            newGameButton.setMouseDownImage(buttonDownTex);
            newGameButton.setMouseOverImage(buttonMouseOverTex);
        } catch (SlickException e) {
            logger.error("Error loading buttons");
            e.printStackTrace();
        }
    }

    private void addStatsGUI(int numPlayers){

        TrueTypeFont font = new TrueTypeFont(new Font("Poplar", Font.PLAIN, 35), true);

        scoreText = new TextRenderComponent[numPlayers];
        blocksText = new TextRenderComponent[numPlayers];
        timeText = new TextRenderComponent[numPlayers];
        nameText = new TextRenderComponent[numPlayers];

        int leftBorder;
        int upperBorder =  GameParameters.WINDOW_HEIGHT * 1 / 4 + 10;
        int columnHeight = font.getHeight();
        int columnWidth = font.getWidth("Player1");


        for(int i = 0; i < numPlayers; i++) {
            leftBorder = (int) (GameParameters.WINDOW_WIDTH * (i*2+1)/(numPlayers*2F)-columnWidth/2F) - 20;
            nameText[i] = addTextEntity("player", font, leftBorder, upperBorder);
            scoreText[i] = addTextEntity("score", font, leftBorder, upperBorder + columnHeight);
            blocksText[i] = addTextEntity("blocks", font, leftBorder, (int) (columnHeight+scoreText[i].getOwnerEntity().getPosition().getY()));
            timeText[i] = addTextEntity("time",font,leftBorder, (int) (columnHeight+blocksText[i].getOwnerEntity().getPosition().getY()));
        }
    }

    private void addHighscore(StateBasedGame stateBasedGame) {
        highScoreController.addEntry(nameField.getText(), (int) totalBlocks[0], time);
        try {
            highScoreController.saveToFile();
        } catch (Exception e) {
            logger.error("There was an error while writing highscores to file: {}" + e);
        }

        stateBasedGame.enterState(GameParameters.HIGHSCORE_STATE);
    }

    private TextRenderComponent addTextEntity(String id, TrueTypeFont font, int x, int y) {
        Entity entity = new Entity(id + "_entity");
        TextRenderComponent renderComponent = new TextRenderComponent(id + "_view", font);
        entity.addComponent(renderComponent);
        entity.setPosition(new Vector2f(x, y));
        addEntity(entity);
        return renderComponent;
    }

    private void showNewHighscoreDialog(int rank) {
        highscoreButton.setVisible(true);
        nameField.setVisible(true);
    }

    private void hideNewHighscoreDialog() {
        highscoreButton.setVisible(false);
        nameField.setVisible(false);
    }
}
