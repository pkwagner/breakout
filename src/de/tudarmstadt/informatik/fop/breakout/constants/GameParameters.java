package de.tudarmstadt.informatik.fop.breakout.constants;

import org.newdawn.slick.geom.Vector2f;
import de.tudarmstadt.informatik.fop.breakout.actions.items.*;

/**
 * Class for holding the game parameters and constants e.g. entity IDs or image
 * paths
 *
 * @author Tobias Otterbein, Benedikt Wartusch
 *
 */
public interface GameParameters {

	public static final String EXT_CONTROLLER = "_controller";
	public static final String EXT_VIEW = "_view";

	// Window Settings
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final int FRAME_RATE = 60;

	// Game States
	public static final int MAINMENU_STATE = 0;
	public static final int GAMEPLAY_STATE = 1;
	public static final int HIGHSCORE_STATE = 2;
	public static final int CREDITS_STATE = 3;
	public static final int GAMEOVER_STATE = 4;
	public static final int SETTINGS_STATE = 5;

	// Background
	public static final String BACKGROUND_ID = "background";
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
	public static final int MAP_INITIAL_ID = 1;
	public static final int MAP_COUNT = 3;
	//public static final int MAP_COUNT_MULTIPLAYER = 3;
	public static final int BLOCK_DEATH_ANIMAION_LENGTH = 300;
	public static final String SIMPLE_BLOCK_1_SPRITESHEET = "/images/simpleBlock1-spritesheet.png";
	public static final String SIMPLE_BLOCK_1_DEATH_SPRITESHEET = "/images/simpleBlock1-death-spritesheet.png";
	public static final String SIMPLE_BLOCK_2_SPRITESHEET = "/images/simpleBlock2-spritesheet.png";
	public static final String SIMPLE_BLOCK_2_DEATH_SPRITESHEET = "/images/simpleBlock2-death-spritesheet.png";
	public static final String SIMPLE_BLOCK_3_SPRITESHEET = "/images/simpleBlock3-spritesheet.png";
	public static final String SIMPLE_BLOCK_3_DEATH_SPRITESHEET = "/images/simpleBlock3-death-spritesheet.png";

	public static final int		BLOCK_WIDTH = 50;
	public static final int		BLOCK_HEIGHT = 30;
	public static final String	RAM_BLOCK_SPRITESHEET = "/images/ram-block-spritesheet.png";
	public static final String	RAM_BLOCK_DEATH_SPRITESHEET = "/images/ram-block-death-spritesheet.png";
	public static final float	RAM_BLOCK_ACCELERATION = 0.0001F;
	public static final int		RAM_BLOCK_REST_TIME = 1500;
	public static final float	RAM_BLOCK_REBOUND_VELOCITY = 0.01F;
	public static final int BLOCK_DEFAULT_SCOREPOINTS = 10;
	public static final int BLOCK_RAM_SCOREPOINTS = 20;

	// Timer
	public static final String 	STOP_WATCH_ID		= "stopWatch";
	public static final int		STOP_WATCH_WIDTH	= 40;
	public static final int		STOP_WATCH_HEIGHT	= 50;
	public static final int		STOP_WATCH_OFFSET	= 50;


	public static final double	GAME_SLOMO_ANIMATION_SPEED =  0.0005;

	// Ball
	public static final String BALL_ID = "ball";
	public static final float INITIAL_BALL_SPEED = 0.35f;
	public static final float INITIAL_BALL_SPEED_MULTIPLAYER = 0.05f;
	public static final float SPEEDUP_VALUE = 0.0001f;
	public static final String BALL_SPRITESHEET = "/images/ball-spritesheet.png";

	// StickModel
	public static final String STICK_ID = "stick";
	public static final String STICK_ID_PLAYER2 = "stick_2";
	public static final float STICK_SPEED = 1f;
	public static final String STICK_MIDDLE_IMAGE = "/images/stick-middle.png";
	public static final String STICK_LEFT_IMAGE = "/images/stick-side-l.png";
	public static final String STICK_RIGHT_IMAGE = "/images/stick-side-r.png";
	public static final float STICK_HEIGHT = 25;
	public static final float STICK_WIDTH = 130;
	public static final float STICK_MIN_WIDTH = 50;
	public static final int BALL_INITIAL_POS_Y = 500;
	public static final int BALL_INITIAL_POS_Y_PLAYER2 = 100;
	public static final int EMITTER_Y_OFFSET = 17;
	public static final int STICK_MAX_BALL_THETA = 90;
	public static final int STICK_MAX_REBOUND_ANGLE = 140;
	public static final int STICK_MIN_REBOUND_ANGLE = 40;


	//Particle systems
	public static final String FLAME_PARTICLE_IMAGE = "/images/flame-particle.png";
	public static final String FLAME_LEFT_EMITTER_FILE = "/emitter/flame-l.xml";
	public static final String FLAME_RIGHT_EMITTER_FILE = "/emitter/flame-r.xml";
	public static final int THRUST_DURATION = 200;
	public static final int EMITTER_GRAVITY_MAX = -40;
	public static final int EMITTER_GRAVITY_MIN = -20;
	public static final int EMITTER_LIFE_MAX = 300;
	public static final int EMITTER_LIFE_MIN = 300;
	public static final int EMITTER_SPEED_MAX = 800;
	public static final int EMITTER_SPEED_MIN = 400;

	// Player
	public static final String PLAYER_ID = "player";
	public static final String PLAYER_ID_PLAYER2 = "player2";
	public static final String NO_LIFE_LEFT = "noLifeLeft";
	public static final int PLAYER_DEFAULT_HEALTHPOINTS = 3;
	public static final Vector2f PLAYER_VIEW_SCORE_OFFSET = new Vector2f(20, 550);
	public static final Vector2f PLAYER_VIEW_SCORE_OFFSET_PLAYER2 = new Vector2f(20, 50);
	public static final int PLAYER_VIEW_SCORE_DISTANCE_Y = 50;

	// Pause
	public static final String PAUSE_ID = "pause";
	public static final String PAUSE_IMAGE_ID = "pause_image";
	public static final String PAUSE_IMAGE = "/images/pause.png";

	// Game start
	public static final String GAMESTART_ENTITY_ID = "start_game";

	// Win
	public static final String WIN_ID = "win";

	// Escape
	public static final String ESCAPE_ID = "escape";

	public static final String MENU_TITLE_ID = "title";

	// Highscore
	public static final String HIGHSCORE_FILE = "highscores/highscore.hsc";
	public static final int HIGHSCORE_MAX_ENTRIES = 10;
	//example format: "name:12345:123.0" or "name:1234:123.135"
	public static final String HIGHSCORE_FILE_ENTRY_SCHEME = "[\\w]+:[\\d]{1,9}:[\\d]{1,9}\\.\\d{1,3}";
	public static final String HIGHSCORE_BACKGROUND_IMAGE = "images/menu_highscore.png";
	public static final int HIGHSCORE_TITLE_START_Y = 50;
	public static final int HIGHSCORE_ENTRY_START_Y = 200;
	public static final int HIGHSCORE_ENTRY_GAP = 4;
	public static final String HIGHSCORE_TITLE_TEXT = "Highscores";
	public static final String HIGHSCORE_RESET_IMAGE = "images/reset-button.png";
	public static final String HIGHSCORE_RESET_OVER_IMAGE = "images/reset-button-over.png";
	public static final float HIGHSCORE_RESET_SIZE = 0.7f;

	// Game over
	public static final String GAMEOVER_BACKGROUND_IMAGE = "images/menu_gameover.png";

	//Settings
	public static final String SETTINGS_TITLE = "Settings";
	public static final String SETTINGS_BACKGROUND_IMAGE = "images/menu_settings.png";

	// MapParameters
	public static final int MAP_COLUMNS = 16;
	public static final int MAP_ROWS	= 10;
	public static final String MAP_FILE_PATH	= System.getProperty("user.dir") + "/maps/";
	public static final String MAP_FILE_PREFIX = "level";
	//public static final String MAP_FILE_PREFIX_MULTIPLAYER = "level";
	public static final String MAP_FILE_EXT 	= ".map";
	public static final double MAP_REAL_HEIGHT = 50000;
	public static final double MAP_GRAVITY = 9.81F;

	// Menu
	public static final String 	ENTRY_IMAGE = "/images/entry.png";
	public static final String 	ENTRY_OVER_IMAGE = "/images/entry_over.png";
	public static final String 	ENTRY_DOWN_IMAGE = "/images/entry_down.png";
	public static final String ENTRY_IMAGE_SMALL = "/images/entry_small.png";
	public static final String 	MENU_BACKGROUND_IMAGE = "images/menu_main.png";
	public static final int 	ENTRY_Y_OFFSET = -7;
	public static final float	ENTRY_SCALE_FACTOR = 0.8F;
	public static final int 	MAIN_MENU_ENTRY_Y = 190;
	public static final int		MAIN_MENU_ENTRY_DISTANCE = 75;

	// Items
	public static final boolean ITEM_DROP_IN_SMASH_MODE = false;
	public static final float ITEM_FALL_SPEED = 0.2f;
	public static final float ITEM_FASTER_SPEEDUP_VALUE = 1.1f;
	public static final float ITEM_SLOWER_SPEED_VALUE = 0.9f;
	public static final float ITEM_BIGGER_CHANGE_VALUE = 1.3f;
	public static final float ITEM_SMALLER_CHANGE_VALUE = 0.75f;
	public static final float ITEM_SLOMO_SPEED_FACTOR = 0.2f;
	public static final int ITEM_HP_HEALTHPOINTS = 1;
	public static final float ITEM_DROP_POSSIBILITY = 0.25f;
	public static final float ITEM_IMAGE_SIZE = 0.6f; //it's a scaling factor
	public static enum ItemType {
		FasterItem("/images/faster-spritesheet.png", 1, 0, FasterItemAction.class),
		SlowerItem("/images/slower-spritesheet.png", 1, 0, SlowerItemAction.class),
		BiggerItem("/images/bigger-spritesheet.png", 1, 0, BiggerItemAction.class),
		SmallerItem("/images/smaller-spritesheet.png", 1, 0, SmallerItemAction.class),
		SmashBallItem("/images/faster.png", 1, 3, SmashBallItemAction.class),
		SloMoItem("/images/slower.png", 1, 1.5f, SloMoItemAction.class),
		HealthPointItem("/images/health-point-spritesheet.png", 1, 0, HealthPointItemAction.class),
		AdditionalBallItem("/images/additional-ball-spritesheet.png", 1, 0, AdditionalBallItemAction.class);

		private final String imagePath;
		private final double possibility;
		private final Class actionHandler;
		private final float duration;

		ItemType(String imagePath, double possibility, float duration, Class actionHandler) {
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

		public float getDuration() {
			return duration;
		}

		public Class getActionHandler() {
			return actionHandler;
		}
	}

	public static final float DEFAULT_VOLUME = 0.5f;

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
	public static final String CREDITS_TITLE_ID = "title";
	public static final String CREDITS_BACKGROUND_IMAGE = "images/menu_credits.png";
	public static final String CREDITS_CONTENT_ID = "content";
	public static final String CREDITS_TITLE_TEXT = "Credits";

	public static final String CREDITS_DEV1_TEXT = "Leon Chemnitz";
	public static final String CREDITS_DEV2_TEXT = "Martin Kerscher";
	public static final String CREDITS_DEV3_TEXT = "Alexander Siegler";
	public static final String CREDITS_DEV4_TEXT = "Paul Wagner";
	public static final int CREDITS_TITLE_START_Y = 100;
	public static final int CREDITS_DEVS_START_X = 280;
	public static final int CREDITS_DEVS_START_Y = 220;
	public static final int CREDITS_DEVS_SPACE_Y = 70;

	// BackButton
	public static final Vector2f BACK_BUTTON_POSITION = new Vector2f(60,70);

	// icon
	public static final String[] ICON ={ 	"images/icon_16.png",
											"images/icon_32.png",
											"images/icon_48.png",
											"images/icon_256.png",};
}
