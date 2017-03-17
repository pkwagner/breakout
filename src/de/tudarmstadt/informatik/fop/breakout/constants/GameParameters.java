package de.tudarmstadt.informatik.fop.breakout.constants;

import de.tudarmstadt.informatik.fop.breakout.actions.items.BiggerItemAction;
import de.tudarmstadt.informatik.fop.breakout.actions.items.FasterItemAction;
import de.tudarmstadt.informatik.fop.breakout.actions.items.SlowerItemAction;
import de.tudarmstadt.informatik.fop.breakout.actions.items.SmallerItemAction;

/**
 * Class for holding the game parameters and constants e.g. entity IDs or image
 * paths
 *
 * @author Tobias Otterbein, Benedikt Wartusch
 *
 */
public interface GameParameters {

	// Window Settings
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final int FRAME_RATE = 60;

	// Game States
	public static final int MAINMENU_STATE = 0;
	public static final int GAMEPLAY_STATE = 1;
	public static final int HIGHSCORE_STATE = 2;
	public static final int CREDITS_STATE = 3;

	// Background
	public static final String BACKGROUND_ID = "background";
	public static final String BACKGROUND_IMAGE = "/images/background.png";
	public static final String BACKGROUND_SPRITESHEET = "/images/background_spritesheet.png";

	// Borders
	public enum BorderType {
		TOP, LEFT, RIGHT
	};

	public static final int BORDER_WIDTH = 6;
	public static final String TOP_BORDER_ID = "topBorder";
	public static final String LEFT_BORDER_ID = "leftBorder";
	public static final String RIGHT_BORDER_ID = "rightBorder";

	// Blocks
	public static final String BLOCK_ID = "block";
	public static final String MAP_FILE = "maps/level1.map";
	public static final String BLOCK_1_IMAGE = "/images/simpleBlock1.png";
	public static final String BLOCK_2_IMAGE = "/images/simpleBlock2.png";
	public static final String BLOCK_3_IMAGE = "/images/simpleBlock3.png";
	public static final int		BLOCK_WIDTH = 50;
	public static final int		BLOCK_HEIGHT = 30;
	public static final String	RAM_BLOCK_IMAGE = "/images/ram-block.png";
	public static final float	RAM_BLOCK_ACCELERATION = 0.0001F;
	public static final int		RAM_BLOCK_REST_TIME = 1500;
	public static final float	RAM_BLOCK_REBOUND_VELOCITY = 0.01F;
	
	// Timer
	public static final String 	STOP_WATCH_ID		= "stopWatch";
	public static final int		STOP_WATCH_WIDTH	= 40;
	public static final int		STOP_WATCH_HEIGHT	= 50;

	// Ball
	public static final String BALL_ID = "ball";
	public static final float INITIAL_BALL_SPEED = 0.3f;
	public static final float SPEEDUP_VALUE = 0.0001f;
	public static final String BALL_SPRITESHEET = "/images/ball-spritesheet.png";

	// StickModel
	public static final String STICK_ID = "stick";
	public static final float STICK_SPEED = 0.5f;
	public static final String STICK_IMAGE = "/images/stick.png";
	public static final float STICK_HEIGHT = 25;
	public static final float STICK_WIDTH = 130;

	// Player
	public static final String PLAYER_ID = "player";
	public static final String NO_LIFE_LEFT = "noLifeLeft";

	// Pause
	public static final String PAUSE_ID = "pause";
	public static final String PAUSE_IMAGE = "/images/pause.png";

	// Win
	public static final String WIN_ID = "win";

	// Escape
	public static final String ESCAPE_ID = "escape";

	// Highscore
	public static final String HIGHSCORE_FILE = "highscores/highscore.hsc";
	public static final int HIGHSCORE_MAX_ENTRIES = 10;
	//example format: "name:12345:123.0" or "name:1234:123.135"
	public static final String HIGHSCORE_FILE_ENTRY_SCHEME = "[\\w]+:[\\d]{1,9}:[\\d]{1,9}\\.\\d{1,3}";

	public static final int HIGHSCORE_TITLE_START_Y = 50;
	public static final int HIGHSCORE_ENTRY_START_Y = 110;
	public static final int HIGHSCORE_ENTRY_GAP = 4;
	public static final String HIGHSCORE_TITLE_TEXT = "Highscores";

	// MapParameters
	public static final int MAP_COLUMNS = 16;
	public static final int MAP_ROWS	= 10;
	public static final String MAP_FILE_PATH	= System.getProperty("user.dir") + "/maps/";
	public static final String MAP_FILE_PREFIX = "level";
	public static final String MAP_FILE_EXT 	= ".map";

	// Menu
	public static final String ENTRY_IMAGE = "/images/entry.png";
	public static final String ENTRY_DOWN_IMAGE = "/images/entry_down.png";
	public static final String MENU_BACKGROUND_IMAGE = "images/menu.png";
	
	// Items
	public static final float ITEM_FALL_SPEED = 0.2f;
	public static final float ITEM_FASTER_SPEEDUP_VALUE = 1.1f;
	public static final float ITEM_SLOWER_SPEED_VALUE = 0.9f;
	public static final float ITEM_BIGGER_CHANGE_VALUE = 1.3f;
	public static final float ITEM_SMALLER_CHANGE_VALUE = 0.75f;
	public static final float ITEM_DROP_POSSIBILITY = 0.1f;
	public static enum ItemType {
		FasterItem("/images/faster.png", 1, 0, FasterItemAction.class),
		SlowerItem("/images/slower.png", 1, 0, SlowerItemAction.class),
		BiggerItem("/images/bigger.png", 1, 0, BiggerItemAction.class),
		SmallerItem("/images/smaller.png", 1, 0, SmallerItemAction.class);

		private final String imagePath;
		private final double possibility;
		private final Class actionHandler;
		private final int duration;

		ItemType(String imagePath, double possibility, int duration, Class actionHandler) {
			this.imagePath = imagePath;
			this.possibility = possibility;
			this.duration = duration;
			this.actionHandler = actionHandler;
		}

		public String getImagePath() {
			return imagePath;
		}

		public double getPossibility() {
			return possibility;
		}

		public int getDuration() {
			return duration;
		}

		public Class getActionHandler() {
			return actionHandler;
		}
	}

	// Direction enum
	public static enum Direction {
		LEFT,
		RIGHT,
		UP,
		DOWN
	}

	// BlockType enum
	public static enum BlockType {
		SIMPLE,
		RAM
	}

	// Credits
	public static final String CREDITS_TITLE_TEXT = "Credits";
	public static final String CREDITS_DEV1_TEXT = "Leon Chemnitz";
	public static final String CREDITS_DEV2_TEXT = "Martin Kerscher";
	public static final String CREDITS_DEV3_TEXT = "Alexander Siegler";
	public static final String CREDITS_DEV4_TEXT = "Paul Wagner";
	public static final int CREDITS_TITLE_START_Y = 50;
	public static final int CREDITS_DEVS_START_X = 115;
	public static final int CREDITS_DEVS_START_Y = 150;
	public static final int CREDITS_DEVS_SPACE_Y = 110;
}
