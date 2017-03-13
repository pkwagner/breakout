package de.tudarmstadt.informatik.fop.breakout.controllers;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.blocks.AbstractBlockController;
import de.tudarmstadt.informatik.fop.breakout.controllers.blocks.SimpleBlockController;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.AbstractBlockModel;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.SimpleBlock;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import de.tudarmstadt.informatik.fop.breakout.views.blocks.AbstractBlockRenderComponent;
import de.tudarmstadt.informatik.fop.breakout.views.blocks.SimpleBlockRenderComponent;
import eea.engine.component.render.ImageRenderComponent;
import exceptions.InvalidMapFileException;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class MapController {

	private static final Logger logger = LogManager.getLogger();
	
	private static final String FILE_PATH	= System.getProperty("user.dir") + "/maps/";
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
        loadMap(1); //load default map
    }

    public void loadMap(int mapId) {
    	
    	ArrayList<String> rawMap;
    	
    	try{
    		rawMap = loadMapFromFile(FILE_PATH + FILE_PREFIX + mapId + FILE_EXT);
    		logger.info("Map with Id " + mapId +" succesfully loaded");
    	}catch(InvalidMapFileException e){
    		rawMap = new ArrayList<String>();
    		logger.error("Map with Id " + mapId + " is invalid. The following error occured: " + e);
    	} catch (IOException e) {
    		rawMap = new ArrayList<String>();
    		logger.error("Map with Id " + mapId + " failed to load. The following error occured: " + e);
		}
        
        createModels(rawMap);

        //assign views to blocks
        map.stream().forEach(block -> block.addComponent(createView(block)));

        //assign controller to blocks
        map.stream().forEach(block -> block.addComponent(createController(block)));

		//add block to game
		map.stream().forEach(gameplayState::addEntity);
    }
    
    
    /**
     * Loads a map-file, checks it for validity and converts it into a list of strings
     * 
     * @param path path to the map-file
     * @return List of Blocks in their String representation
     * @throws InvalidMapFileException if the map is invalid
     * @throws IOException 
     */
    private ArrayList<String> loadMapFromFile(String path) throws InvalidMapFileException, IOException{
    	
    	List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8); //read file into string list
    	
    	if(lines.size() != GameParameters.MAP_ROWS) throw new InvalidMapFileException("Invalid row count. The read row count was: " + lines.size()); //check row count
    	
    	ArrayList<String> output = new ArrayList<String>();
    	
    	int index = 1;
    	for(String line : lines){
    		List<String> lineBuffer		= new LinkedList<String>();
    		StringReader stringReader	= new StringReader(line);
    		String stringBuffer 		= "";
    		
    		try{
	    		for(int i = 0; ++i <= line.length();){ //interpret single lines of the map file
	    			int readChar = stringReader.read();
	    			
	    			if(readChar == ','){
	    				lineBuffer.add(stringBuffer);
	    				stringBuffer = "";
	    			}else{
	    				stringBuffer += (char)readChar;
	    			}
	    		}
	    		
	    		lineBuffer.add(stringBuffer); //at end of line there is no comma
	    		
    		}catch(IOException e){ //the StringReader can throw IOExceptions
    			throw e;
    		}finally{
    			stringReader.close();
    		}
    		
    		if(lineBuffer.size() == GameParameters.MAP_COLUMNS){ //check column count
    			logger.debug("line "+ index + " interpreted " +lineBuffer);
    			output = (ArrayList<String>) Stream.concat(output.stream(), lineBuffer.stream()).collect(Collectors.toList());
    		}else{
    			logger.debug("line "+ index + " raw " +line);
    			logger.debug("line "+ index + " interpreted " +lineBuffer);
    			throw new InvalidMapFileException("Invalid column count at row: " + index + " The read column count was: " + lineBuffer.size());
    		}  
    		
    		index++;
    	}
    	
    	return output;
    }
    
    /**
     * Creates the Models of Blocks, adds them to the "map"-Collection and positions them
     * 
     * @param rawMap list of Blocks in their string representation
     */
    private void createModels(ArrayList<String> rawMap){
    	
    	int index = 0;			  //index to keep track of the Blocks
    	int positioningIndex = 0; //index to keep track of the positioning of the blocks
    	
    	for(String blockRep : rawMap){	//blockRep stands for the String representation of a Block
    		if(blockRep.equals("0")){
    			
    		}else if(isInteger(blockRep)){
    			map.add(new SimpleBlock(GameParameters.BLOCK_ID + index,Integer.parseInt(blockRep)));
    			
    			int row		= (positioningIndex - (positioningIndex % GameParameters.MAP_COLUMNS)) / GameParameters.MAP_COLUMNS;
    			int column 	= positioningIndex - row * GameParameters.MAP_COLUMNS;
    			
    			float columnWidth	= gameContainer.getWidth() /(float) GameParameters.MAP_COLUMNS;       //map.get(index).getSize().getX();
    			float rowHeight		= gameContainer.getHeight() * 0.5F /(float) GameParameters.MAP_ROWS;  //map.get(index).getSize().getY();
    			
    			int x = (int) (columnWidth	* column	+ columnWidth/2);
    			int y = (int) (rowHeight	* row		+ rowHeight/2);
    			
    			map.get(index).setPosition(new Vector2f(x,y));
    			index++;
    		}else{
    			//TODO: implement behavior for non-simpleBlocks
    			int row		= (positioningIndex - (positioningIndex % GameParameters.MAP_COLUMNS)) / GameParameters.MAP_COLUMNS;
    			int column 	= positioningIndex - row * GameParameters.MAP_COLUMNS;
    			logger.error("Unknown Block type '" + blockRep + "'  at row " + (row +1) + " column " + (column +1));
    		}    		
    		positioningIndex++;
    	}
    }
    
    
    /**
     *  Creates the controller for a given block
     *  
     * @param block the block for which a controller shall be created
     * @return
     */
    private AbstractBlockController createController(AbstractBlockModel block){
    	
    	switch(block.getType()){
    		case SIMPLE:	return new SimpleBlockController(block.getID() + "Controller"); //TODO: wolln wir das so machen?
    		default: 		logger.error("Some error occured during the creation of the controller for block: " + block.getID());
    						return null;
    	}
    } 
    
    /**
     * Creates the view for a given block
     * 
     * @param block the block for which a view shall be created
     * @return
     * @throws SlickException
     */
    private AbstractBlockRenderComponent createView(AbstractBlockModel block) { //TODO: an dieser Stelle warte ich auf den lieben paul, das hier is alles provisorisch
		try {
			switch (block.getType()) {
				case SIMPLE:
					return new SimpleBlockRenderComponent(((SimpleBlock) block).getMaxHits());
				default:
					logger.error("Some error occured during the creation of the view of the block: " + block.getID());
					return new SimpleBlockRenderComponent(1);
			}
		} catch (SlickException e) {
			logger.error("The following error occured during the creation of block " + block.getID() + ": " + e);
			return null;
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
    	   	@SuppressWarnings("unused")
			int i = Integer.parseInt(str);  
    	  }  
    	  catch(NumberFormatException nfe)  
    	  {  
    	    return false;  
    	  }  
    	  return true;  
    }
}
