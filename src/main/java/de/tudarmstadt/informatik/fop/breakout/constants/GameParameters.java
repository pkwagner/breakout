package de.tudarmstadt.informatik.fop.breakout.constants;

import org.newdawn.slick.geom.Vector2f;

/**
 * Class for holding the game parameters and de.tudarmstadt.informatik.fop.breakout.constants e.g. entity IDs or image
 * paths
 *
 * @author Tobias Otterbein, Benedikt Wartusch
 */
public interface GameParameters {

    // Extensions
    String EXT_CONTROLLER = "_controller";
    String EXT_VIEW = "_view";

    // Window Settings
    int WINDOW_WIDTH = 800;
    int WINDOW_HEIGHT = 600;
    int FRAME_RATE = 60;

    // Game States
    int MAINMENU_STATE = 0;
    int GAMEPLAY_STATE = 1;
    int HIGHSCORE_STATE = 2;
    int CREDITS_STATE = 3;
    int GAMEOVER_STATE = 4;
    int SETTINGS_STATE = 5;

    // Background
    String BACKGROUND_SPRITESHEET = "src/main/resources/images/background_spritesheet.png";

    // Borders
    enum BorderType {
        TOP, LEFT, RIGHT
    }

    int BORDER_WIDTH = 6;
    String TOP_BORDER_ID = "topBorder";
    String LEFT_BORDER_ID = "leftBorder";
    String RIGHT_BORDER_ID = "rightBorder";

    // Blocks
    String BLOCK_ID = "block";
    int MAP_INITIAL_ID = 1;
    int MAP_COUNT = 3;
    int BLOCK_DEATH_ANIMAION_LENGTH = 300;
    String SIMPLE_BLOCK_1_SPRITESHEET = "src/main/resources/images/simpleBlock1-spritesheet.png";
    String SIMPLE_BLOCK_1_DEATH_SPRITESHEET = "src/main/resources/images/simpleBlock1-death-spritesheet.png";
    String SIMPLE_BLOCK_2_SPRITESHEET = "src/main/resources/images/simpleBlock2-spritesheet.png";
    String SIMPLE_BLOCK_2_DEATH_SPRITESHEET = "src/main/resources/images/simpleBlock2-death-spritesheet.png";
    String SIMPLE_BLOCK_3_SPRITESHEET = "src/main/resources/images/simpleBlock3-spritesheet.png";
    String SIMPLE_BLOCK_3_DEATH_SPRITESHEET = "src/main/resources/images/simpleBlock3-death-spritesheet.png";

    int BLOCK_WIDTH = 50;
    int BLOCK_HEIGHT = 30;
    String RAM_BLOCK_SPRITESHEET = "src/main/resources/images/ram-block-spritesheet.png";
    String RAM_BLOCK_DEATH_SPRITESHEET = "src/main/resources/images/ram-block-death-spritesheet.png";
    float RAM_BLOCK_ACCELERATION = 0.0001F;
    int RAM_BLOCK_REST_TIME = 1500;
    float RAM_BLOCK_REBOUND_VELOCITY = 0.01F;
    int BLOCK_SCOREPOINTS = 10;

    // Timer
    String STOP_WATCH_ID = "stopWatch";
    int STOP_WATCH_WIDTH = 40;
    int STOP_WATCH_HEIGHT = 50;
    int STOP_WATCH_OFFSET = 50;

    double GAME_SLOMO_ANIMATION_SPEED = 0.0005;

    // Ball
    String BALL_ID = "ball";
    float INITIAL_BALL_SPEED = 0.35f;
    float INITIAL_BALL_SPEED_MULTIPLAYER = 0.05f;
    float SPEEDUP_VALUE = 0.0001f;
    String BALL_SPRITESHEET = "src/main/resources/images/ball-spritesheet.png";

    // StickModel
    String STICK_ID = "stick";
    String STICK_ID_PLAYER2 = "stick_2";
    float STICK_SPEED = 1f;
    String STICK_MIDDLE_IMAGE = "src/main/resources/images/stick-middle.png";
    String STICK_LEFT_IMAGE = "src/main/resources/images/stick-side-l.png";
    String STICK_RIGHT_IMAGE = "src/main/resources/images/stick-side-r.png";
    float STICK_HEIGHT = 25;
    float STICK_WIDTH = 130;
    float STICK_MIN_WIDTH = 50;
    int BALL_INITIAL_POS_Y = 500;
    int BALL_INITIAL_POS_Y_PLAYER2 = 100;
    int EMITTER_Y_OFFSET = 17;
    int STICK_MAX_BALL_THETA = 90;
    int STICK_MAX_REBOUND_ANGLE = 140;
    int STICK_MIN_REBOUND_ANGLE = 40;


    //Particle systems
    String FLAME_PARTICLE_IMAGE = "/src/main/resources/images/flame-particle.png";
    String FLAME_LEFT_EMITTER_FILE = "/src/main/resources/emitter/flame-l.xml";
    String FLAME_RIGHT_EMITTER_FILE = "/src/main/resources/emitter/flame-r.xml";
    int THRUST_DURATION = 200;
    int EMITTER_GRAVITY_MAX = -40;
    int EMITTER_GRAVITY_MIN = -20;
    int EMITTER_LIFE_MAX = 300;
    int EMITTER_LIFE_MIN = 300;
    int EMITTER_SPEED_MAX = 800;
    int EMITTER_SPEED_MIN = 400;

    // Player
    String PLAYER_ID = "player";
    String PLAYER_ID_PLAYER2 = "player2";
    int PLAYER_DEFAULT_HEALTHPOINTS = 3;
    Vector2f PLAYER_VIEW_SCORE_OFFSET = new Vector2f(20, 550);
    Vector2f PLAYER_VIEW_SCORE_OFFSET_PLAYER2 = new Vector2f(20, 50);

    // Pause
    String PAUSE_ID = "pause";
    String PAUSE_IMAGE_ID = "pause_image";
    String PAUSE_IMAGE = "src/main/resources/images/pause.png";

    // Game start
    String GAMESTART_ENTITY_ID = "start_game";

    // Escape
    String ESCAPE_ID = "escape";

    // Highscore
    String HIGHSCORE_FILE = "highscores/highscore.hsc";
    int HIGHSCORE_MAX_ENTRIES = 10;
    //example format: "name:12345:123.0" or "name:1234:123.135"
    String HIGHSCORE_FILE_ENTRY_SCHEME = "[\\w]+:[\\d]{1,9}:[\\d]{1,9}\\.\\d{1,3}";
    String HIGHSCORE_BACKGROUND_IMAGE = "images/menu_highscore.png";
    int HIGHSCORE_ENTRY_START_Y = 200;
    int HIGHSCORE_ENTRY_GAP = 4;
    String HIGHSCORE_RESET_IMAGE = "images/reset-button.png";
    String HIGHSCORE_RESET_OVER_IMAGE = "images/reset-button-over.png";
    float HIGHSCORE_RESET_SIZE = 0.7f;

    // Game over
    String GAMEOVER_BACKGROUND_IMAGE = "images/menu_gameover.png";

    //Settings
    String SETTINGS_BACKGROUND_IMAGE = "images/menu_settings.png";

    // MapParameters
    int MAP_COLUMNS = 16;
    int MAP_ROWS = 10;
    String MAP_FILE_PATH = System.getProperty("user.dir") + "/maps/";
    String MAP_FILE_PREFIX = "level";
    String MAP_FILE_EXT = ".map";
    double MAP_REAL_HEIGHT = 50000;
    double MAP_GRAVITY = 0; // Default value: 9.81F

    // Menu
    String ENTRY_IMAGE = "src/main/resources/images/entry.png";
    String ENTRY_OVER_IMAGE = "src/main/resources/images/entry_over.png";
    String ENTRY_DOWN_IMAGE = "src/main/resources/images/entry_down.png";
    String ENTRY_IMAGE_SMALL = "src/main/resources/images/entry_small.png";
    String MENU_BACKGROUND_IMAGE = "images/menu_main.png";
    int ENTRY_Y_OFFSET = -7;
    float ENTRY_SCALE_FACTOR = 0.8F;
    int MAIN_MENU_ENTRY_Y = 190;
    int MAIN_MENU_ENTRY_DISTANCE = 75;

    // Items
    boolean ITEM_DROP_IN_SMASH_MODE = false;
    float ITEM_FALL_SPEED = 0.2f;
    float ITEM_FASTER_SPEEDUP_VALUE = 1.1f;
    float ITEM_SLOWER_SPEED_VALUE = 0.9f;
    float ITEM_BIGGER_CHANGE_VALUE = 1.3f;
    float ITEM_SMALLER_CHANGE_VALUE = 0.75f;
    float ITEM_SLOMO_SPEED_FACTOR = 0.2f;
    int ITEM_HP_HEALTHPOINTS = 1;
    float ITEM_DROP_POSSIBILITY = 0.25f;
    float ITEM_IMAGE_SIZE = 0.6f; //it's a scaling factor

    // Volume
    float DEFAULT_VOLUME = 0.5f;

    // Credits
    String CREDITS_BACKGROUND_IMAGE = "images/menu_credits.png";
    String CREDITS_CONTENT_ID = "content";

    String CREDITS_DEV1_TEXT = "Leon Chemnitz";
    String CREDITS_DEV2_TEXT = "Martin Kerscher";
    String CREDITS_DEV3_TEXT = "Alexander Siegler";
    String CREDITS_DEV4_TEXT = "Paul Wagner";
    int CREDITS_DEVS_START_X = 280;
    int CREDITS_DEVS_START_Y = 220;
    int CREDITS_DEVS_SPACE_Y = 70;

    // BackButton
    Vector2f BACK_BUTTON_POSITION = new Vector2f(60, 70);

    // icon
    String[] ICON = {"images/icon_16.png",
            "images/icon_32.png",
            "images/icon_48.png",
            "images/icon_256.png",};
}
