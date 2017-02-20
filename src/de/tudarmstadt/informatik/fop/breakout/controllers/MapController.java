package de.tudarmstadt.informatik.fop.breakout.controllers;

import de.tudarmstadt.informatik.fop.breakout.models.blocks.AbstractBlockModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;

import java.util.ArrayList;
import java.util.List;

public class MapController {

    private static final String FILE_PREFIX = "level";
    private static final String FILE_EXT = ".map";

    private final List<AbstractBlockModel> map = new ArrayList<>();

    private final GameplayState gameplayState;

    public MapController(GameplayState gameplayState) {
        this.gameplayState = gameplayState;
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

        //add block to game
        map.stream().forEach(gameplayState::addEntity);
    }

    private List<String> loadFile(String fileName) {
        //prefix + id + ext
        //todo:
        return null;
    }
}
