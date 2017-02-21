package de.tudarmstadt.informatik.fop.breakout.controllers;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.AbstractBlockModel;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.SimpleBlock;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import exceptions.InvalidMapFileException;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

public class MapController {

	private static final Logger logger = LogManager.getLogger();
	
	private static final String FILE_PATH	= "/maps/";
    private static final String FILE_PREFIX = "level";
    private static final String FILE_EXT 	= ".map";

    private final List<AbstractBlockModel> map = new ArrayList<>();

    private final GameplayState gameplayState;
    private final GameContainer gameContainer;
    
    public MapController(GameContainer gameContainer, GameplayState gameplayState) {
        this.gameplayState 	= gameplayState;
        this.gameContainer	= gameContainer;
    }

    public void loadMap() {
        loadMap(1);
    }

    public void loadMap(int mapId) {
    	
    	ArrayList<String> rawMap;
    	
    	try{
    		rawMap = loadMapFromFile(FILE_PATH + FILE_PREFIX + mapId + FILE_EXT);
    		logger.info("Map with Id: " + mapId +" succesfully loaded");
    	}catch(InvalidMapFileException e){
    		rawMap = new ArrayList<String>();
    		logger.error("Map with Id: " + mapId + " is invalid");
    	}
        
        createModels(rawMap);

        //assign views to blocks
        map.stream().forEach(gameplayState::addEntity);

        //assign controller to blocks
        map.stream().forEach(gameplayState::addEntity);

        //add block to game
        map.stream().forEach(gameplayState::addEntity);
    }
    
    /**
     * Loads a map-file, checks it for validity and converts it into a list of strings
     * 
     * @param FileName path to the map-file
     * @return List of Blocks in their String representation
     * @throws InvalidMapFileException if the map is invalid
     */
    private ArrayList<String> loadMapFromFile(String FileName) throws InvalidMapFileException{
    	if(1 == 2){
    		
    	}else{
    		throw new InvalidMapFileException();
    	}
    	return new ArrayList<String>();
    }
    
    /**
     * Creates the Models of Blocks, adds them to the "map"-Collection and positions them
     * 
     * @param rawMap list of Blocks in their string representation
     */
    private void createModels(ArrayList<String> rawMap){
    	
    	int index = 0;	//index to keep track of the Blocks
    	
    	for(String blockRep : rawMap){	//blockRep stands for the String representation of a Block
    		if(isInteger(blockRep)){
    			map.add(new SimpleBlock(GameParameters.BLOCK_ID + index,Integer.parseInt(blockRep)));
    			
    			int row		= (index - (index % GameParameters.MAP_COLUMNS)) / GameParameters.MAP_COLUMNS;
    			int column 	= index - row * GameParameters.MAP_COLUMNS;
    			
    			float columnWidth	= gameContainer.getWidth() /(float) GameParameters.MAP_COLUMNS;
    			float rowHeight		= map.get(index).getSize().getY();
    			
    			int x = (int) (columnWidth	* column	+ columnWidth/2);
    			int y = (int) (rowHeight	* row		+ rowHeight/2);
    			
    			map.get(index).setPosition(new Vector2f(x,y));
    		}else{
    			//TODO: implement behavior for non-simpleBlocks
    		}    		
    		index++;
    	}
    }
    
    /**
     * Checks whether a given String can be cast to an integer
     *
     * @param str the string to be checked
     * @return
     */
    private static boolean isInteger(String str){    	
    	  try  
    	  {  
    	    double i = Integer.parseInt(str);  
    	  }  
    	  catch(NumberFormatException nfe)  
    	  {  
    	    return false;  
    	  }  
    	  return true;  
    }
    
}
