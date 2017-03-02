package de.tudarmstadt.informatik.fop.breakout.controllers;

import de.tudarmstadt.informatik.fop.breakout.models.blocks.AbstractBlockModel;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.SimpleBlock;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import de.tudarmstadt.informatik.fop.breakout.views.BlockRenderComponent;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class MapController {

    private static final String FILE_PREFIX = "level";
    private static final String FILE_EXT = ".map";

    private final List<AbstractBlockModel> map = new ArrayList<>();

    private final GameplayState gameplayState;
    private final GameContainer gameContainer;

    public MapController(GameplayState gameplayState, GameContainer gameContainer) {
        this.gameplayState = gameplayState;
        this.gameContainer = gameContainer;
    }

    public void loadMap() {
        loadMap(1);
    }

    public void loadMap(int mapId) {
        //todo: loadFile()

        //create specialized models
        //todo:

        //assign views to blocks
        //todo:

        //assign controller to blocks
        //todo:

        // NOTICE: Comment out to remove example map
        int mapWidth = 10;
        int mapHeight = 10;

        for (int i = 0; i < 10; i = i+1) {
            for (int j = 0; j <10; j = j+2)
                map.add(new SimpleBlock("TEST",i,j));
        }

        //add block to game
        map.forEach(block -> {
            try {
                Vector2f blockSize = calculateBlockSize(mapWidth, mapHeight, gameContainer.getWidth(), gameContainer.getHeight());
                block.setSize(blockSize);
                block.setPosition(calculateBlockPosition(block.getX(), block.getY(), blockSize));
                block.addComponent(new BlockRenderComponent(block.getType(), blockSize));
                block.addComponent(new BlockController(block.getID() + "_controller", block.getType()));

                gameplayState.addEntity(block);
            } catch (SlickException e) {
                e.printStackTrace();
            }
        });
    }

    private List<String> loadFile(String fileName) {
        //prefix + id + ext
        //todo:
        return null;
    }

    private Vector2f calculateBlockPosition(float x, float y, Vector2f blockSize) {
        return new Vector2f(
                (x + 0.5f) * blockSize.getX(),
                (y + 0.5f) * blockSize.getY());
    }

    private Vector2f calculateBlockSize(int mapWidth, int mapHeight, int windowWidth, int windowHeight) {
        return new Vector2f(windowWidth / mapWidth, windowHeight / mapHeight);
    }
}
