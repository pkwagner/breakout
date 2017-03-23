package de.tudarmstadt.informatik.fop.breakout.controllers.game;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.game.blocks.AbstractBlockController;
import de.tudarmstadt.informatik.fop.breakout.controllers.game.blocks.RamBlockController;
import de.tudarmstadt.informatik.fop.breakout.controllers.game.blocks.SimpleBlockController;
import de.tudarmstadt.informatik.fop.breakout.exceptions.InvalidMapFileException;
import de.tudarmstadt.informatik.fop.breakout.models.game.blocks.AbstractBlockModel;
import de.tudarmstadt.informatik.fop.breakout.models.game.blocks.RamBlock;
import de.tudarmstadt.informatik.fop.breakout.models.game.blocks.SimpleBlock;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import de.tudarmstadt.informatik.fop.breakout.util.Utility;
import de.tudarmstadt.informatik.fop.breakout.views.game.blocks.AbstractBlockView;
import de.tudarmstadt.informatik.fop.breakout.views.game.blocks.RamBlockView;
import de.tudarmstadt.informatik.fop.breakout.views.game.blocks.SimpleBlockView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapController {

	private static final Logger logger = LogManager.getLogger();

    private Map<String, AbstractBlockModel> map = new HashMap<>();

    private int mapId;
    private final boolean multiplayer;

    private final GameplayState gameplayState;
    private final GameContainer gameContainer;
    private final StateBasedGame stateBasedGame;

    public MapController(StateBasedGame stateBasedGame, GameplayState gameplayState, boolean multiplayer) {
        this.gameplayState 	= gameplayState;
        this.gameContainer	= stateBasedGame.getContainer();
        this.stateBasedGame = stateBasedGame;
        this.multiplayer = multiplayer;
    }

    public void loadMap(int mapId) {
        this.mapId = mapId;

    	ArrayList<String> rawMap;

    	try{
    		rawMap = loadMapFromFile(GameParameters.MAP_FILE_PATH + GameParameters.MAP_FILE_PREFIX + mapId + GameParameters.MAP_FILE_EXT);
    		logger.info("Map with Id " + mapId +" succesfully loaded");
    	}catch(InvalidMapFileException e){
    		rawMap = new ArrayList<>();
    		logger.error("Map with Id " + mapId + " is invalid. The following error occurred: " + e);
    	} catch (IOException e) {
    		rawMap = new ArrayList<>();
    		logger.error("Map with Id " + mapId + " failed to load. The following error occurred: " + e);
		}

        createModels(rawMap);

        //assign views to blocks
        map.values().forEach(block -> createView(block));

        //assign controller to blocks
        map.values().forEach(block -> {
            AbstractBlockController c = createController(block);
            block.addComponent(c);
            c.init(stateBasedGame);
        });

		//add block to game
		map.values().forEach(gameplayState::addEntity);
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

		float columnWidth	= gameContainer.getWidth() /(float) GameParameters.MAP_COLUMNS;       //map.get(index).getSize().getX();
		float rowHeight		= gameContainer.getHeight() * 0.5F /(float) GameParameters.MAP_ROWS;  //map.get(index).getSize().getY();
		int totalMapHeight = (int) rowHeight * GameParameters.MAP_ROWS;

    	for(String blockRep : rawMap){	//blockRep stands for the String representation of a Block

			int row		= (positioningIndex - (positioningIndex % GameParameters.MAP_COLUMNS)) / GameParameters.MAP_COLUMNS;
			int column 	= positioningIndex - row * GameParameters.MAP_COLUMNS;

			int x = (int) (columnWidth	* column	+ columnWidth/2);
			int y = (int) (rowHeight	* row		+ rowHeight/2);

			// Center map in multiplayer mode
			if (multiplayer)
				y += (totalMapHeight / 2);

    		if(blockRep.equals("0")){
    			//don't add a Block when the string representation is "0"
    		}else if(Utility.isInteger(blockRep)){
    			//block id formating is block{row}_{column}
                SimpleBlock block = new SimpleBlock(GameParameters.BLOCK_ID + column + '_' + row, Integer.parseInt(blockRep));
                map.put(block.getID(), block);
                block.setPosition(new Vector2f(x,y));
    			index++;
    			logger.debug("created simpleblock " + Integer.parseInt(blockRep) + " at row " + row + " and column " + column + "::"+ block.getID());
    		}else if(isRamBlock(blockRep)){

    			GameParameters.Direction d;
    			switch(blockRep.charAt(0)){
	    			case 'u': d = GameParameters.Direction.UP;
	    				break;
	    			case 'd': d = GameParameters.Direction.DOWN;
						break;
	    			case 'l': d = GameParameters.Direction.LEFT;
						break;
	    			case 'r': d = GameParameters.Direction.RIGHT;
						break;
					default: d = GameParameters.Direction.RIGHT;
						logger.error("Something went wrong during creation of RamBlock " + blockRep + " at row " + row + " and column " + column);
    			}

                RamBlock block = new RamBlock(GameParameters.BLOCK_ID + column + '_' + row, d, Integer.parseInt(blockRep.substring(1)), gameplayState.getRBMC());
    			map.put(block.getID(), block);
                block.setPosition(new Vector2f(x,y));
    			index++;
    			logger.debug("created ramblock " + d + Integer.parseInt(blockRep.substring(1)) +  " at row " + row + " and column " + column + "::"+ block.getID());
    		}else{
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
    		case SIMPLE:	return new SimpleBlockController(block.getID() + "Controller");
    		case RAM:		return new RamBlockController(block.getID() + "Controller", ((RamBlock)block).getDirection(), ((RamBlock)block).getDistance());
    		default: 		logger.error("Some error occured during the creation of the controller for block: " + block.getID());
    						return null;
    	}
    }

    /**
     * Checks whether a given string is the string-representation of a RamBlock
     *
     * @param s the string to be checked
     * @return
     */
    private boolean isRamBlock(String s){
    	if(!(s.startsWith("l")|s.startsWith("r")|s.startsWith("u")|s.startsWith("d"))) return false;
    	if(!Utility.isInteger(s.substring(1))) return false;
    	else return true;
    }

 	/**
  	 * Creates the view for a given block
  	 *
  	 * @param block the block for which a view shall be created
  	 * @return
  	 */
    private void createView(AbstractBlockModel block) { //TODO: an dieser Stelle warte ich auf den lieben paul, das hier is alles provisorisch
		if (Breakout.getDebug()) {
			return;
		}

		try {
			AbstractBlockView view;
			switch (block.getType()) {
				case SIMPLE:
					view = new SimpleBlockView(block.getInitialHits(),block.getID()+GameParameters.EXT_VIEW);
					break;
				case RAM:
					view = new RamBlockView(block.getID()+GameParameters.EXT_VIEW);
					break;
				default:
					logger.error("Some error occured during the creation of the view of the block: " + block.getID());
					view = new SimpleBlockView(1,block.getID()+GameParameters.EXT_VIEW);
					break;
			}
			block.addView(view);
			block.addComponent(view);
		} catch (SlickException e) {
			logger.error("The following error occured during the creation of block " + block.getID() + ": " + e);
		}
    }

    public int getMapId() {
        return mapId;
    }

    /**
     * Removes a given block from map
     *
     * @param block the block that should been removed
     */
    public void removeBlock(AbstractBlockModel block) {
        map.remove(block.getID());
    }

    public AbstractBlockModel getBlock(String id) {
        return map.get(id);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

	public boolean isMultiplayer() {
		return multiplayer;
	}
}
