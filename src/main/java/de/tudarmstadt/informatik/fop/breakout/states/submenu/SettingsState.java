package de.tudarmstadt.informatik.fop.breakout.states.submenu;

import de.tudarmstadt.informatik.fop.breakout.actions.gui.*;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.events.MouseClickedEvent;
import de.tudarmstadt.informatik.fop.breakout.events.MousePressedEvent;
import de.tudarmstadt.informatik.fop.breakout.models.KeyBinding;
import de.tudarmstadt.informatik.fop.breakout.models.gui.Checkbox;
import de.tudarmstadt.informatik.fop.breakout.models.gui.Slider;
import de.tudarmstadt.informatik.fop.breakout.views.SettingsTitleView;
import de.tudarmstadt.informatik.fop.breakout.views.gui.CheckboxView;
import de.tudarmstadt.informatik.fop.breakout.views.gui.KeyBindingView;
import de.tudarmstadt.informatik.fop.breakout.views.gui.SeparatorView;
import de.tudarmstadt.informatik.fop.breakout.views.gui.SliderView;
import eea.engine.entity.Entity;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * State showing changeable user settings
 */
public class SettingsState extends AbstractMenuState {

    private static final int LEFT_START_X = 175;
    private static final int Y_GAP = 60;

    private static final int RIGHT_START_X = 490;
    private static final int CHECKBOX_X = RIGHT_START_X - 137;

    public SettingsState(int stateId) throws SlickException {
        super(stateId, GameParameters.SETTINGS_BACKGROUND_IMAGE);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        super.init(container, game);
        if (isTesting())
            return;

        Entity effects = addSliderEntity(140, container.getSoundVolume(), "Effects", new EffectsSliderAction());
        Entity music = addSliderEntity(effects.getPosition().getY(), container.getMusicVolume(), "Music", new MusicSliderAction());
        float previousY = addParticleBox(music.getPosition().getY());

        addKeymapOptions(container, previousY);
    }

    /**
     * Add the section for the keybindings
     *
     * @param container current game container
     * @param previousY y position of the previous component
     */
    private void addKeymapOptions(GameContainer container, float previousY) {
        //start key and left move key
        Entity startTitle = createKeyMapField(previousY, LEFT_START_X, KeyBinding.START_GAME, "Start game");
        Entity leftMove = createKeyMapField(startTitle.getPosition().getY(), LEFT_START_X, KeyBinding.LEFT_MOVE, "Move left");

        Entity player2 = new Entity("player2");
        player2.setPosition(new Vector2f(LEFT_START_X, leftMove.getPosition().getY() + Y_GAP));
        player2.addComponent(new SettingsTitleView(player2.getID() + GameParameters.EXT_VIEW, "Player 2:"));
        addEntity(player2);

        createKeyMapField(player2.getPosition().getY(), LEFT_START_X, KeyBinding.LEFT_MOVE_PLAYER2, "Move left"); //p2

        //separator
        Entity separator = new Entity("separator");
        separator.setPosition(new Vector2f(container.getWidth() / 2 + 15, startTitle.getPosition().getY() + Y_GAP + 30));
        separator.setSize(new Vector2f(3, 225));
        separator.addComponent(new SeparatorView("separator" + GameParameters.EXT_VIEW));
        addEntity(separator);


        //pause key and right move key
        Entity pauseTitle = createKeyMapField(previousY, RIGHT_START_X, KeyBinding.PAUSE, "Pause");
        Entity rightMove = createKeyMapField(pauseTitle.getPosition().getY(), RIGHT_START_X, KeyBinding.RIGHT_MOVE, "Move right");
        createKeyMapField(rightMove.getPosition().getY() + Y_GAP, RIGHT_START_X, KeyBinding.RIGHT_MOVE_PLAYER2, "Move right"); //p2
    }

    /**
     * Creates a keybinding entry.
     *
     * @param previousY  y position of the component above this component
     * @param startX     Where can we start drawing this component
     * @param keyBinding Which keyBinding should be displayed/changeable
     * @param text       the text that should be displayed
     * @return only the key title entity
     */
    private Entity createKeyMapField(float previousY, float startX, KeyBinding keyBinding, String text) {
        String titleId = keyBinding.name() + "_title";
        String keyId = keyBinding.name() + "_key";

        Entity titleEntity = new Entity(titleId);
        titleEntity.setPosition(new Vector2f(startX, previousY + Y_GAP));
        titleEntity.addComponent(new SettingsTitleView(titleId + GameParameters.EXT_VIEW, text));
        addEntity(titleEntity);

        Entity keyEntity = new Entity(keyId);
        keyEntity.setPosition(new Vector2f(startX + 150, titleEntity.getPosition().getY()));
        keyEntity.setSize(new Vector2f(100, 40));
        keyEntity.addComponent(new KeyBindingView(keyId + GameParameters.EXT_VIEW, keyBinding));

        MouseClickedEvent startClickEvent = new MouseClickedEvent();
        startClickEvent.addAction(new KeyBindingClickAction(keyBinding));
        keyEntity.addComponent(startClickEvent);
        addEntity(keyEntity);

        return titleEntity;
    }

    /**
     * Add the section for particle effects
     *
     * @param previousY y position of the previous component
     * @return max y position of the current component
     */
    private float addParticleBox(float previousY) {
        Entity particleTitle = new Entity("particle_title");
        particleTitle.setPosition(new Vector2f(LEFT_START_X, previousY + Y_GAP));
        particleTitle.addComponent(new SettingsTitleView("particle_title" + GameParameters.EXT_VIEW, "Particles enabled"));
        addEntity(particleTitle);

        Entity particleCheck = new Checkbox("particle_check", true);
        particleCheck.setPosition(new Vector2f(CHECKBOX_X, particleTitle.getPosition().getY()));
        particleCheck.setSize(new Vector2f(24, 24));

        MouseClickedEvent mousePressedEvent = new MouseClickedEvent();
        mousePressedEvent.addAction(new ParticleEffectsAction());
        particleCheck.addComponent(mousePressedEvent);

        particleCheck.addComponent(new CheckboxView("particle_check" + GameParameters.EXT_VIEW));
        addEntity(particleCheck);
        return particleTitle.getPosition().getY();
    }

    /**
     * Add the section for the sliders
     *
     * @param defVal the default value where the slider should be displayed before the user interacts with it
     * @return title component of this slider
     */
    private Entity addSliderEntity(float previousCompY, float defVal, String text, SliderChangeAction sliderAction) {
        Entity musicTitle = new Entity("music_title");
        musicTitle.setPosition(new Vector2f(LEFT_START_X, previousCompY + Y_GAP));
        musicTitle.addComponent(new SettingsTitleView("music_title" + GameParameters.EXT_VIEW, text));
        addEntity(musicTitle);

        //slider
        Slider musicSlider = new Slider("music_slider", defVal);
        musicSlider.setPosition(new Vector2f(RIGHT_START_X, musicTitle.getPosition().getY()));
        musicSlider.setSize(new Vector2f(300, 50));
        musicSlider.addComponent(new SliderView());

        //slider listener
        MousePressedEvent sliderClickEvent = new MousePressedEvent();
        sliderClickEvent.addAction(sliderAction);
        musicSlider.addComponent(sliderClickEvent);

        addEntity(musicSlider);
        return musicTitle;
    }
}
