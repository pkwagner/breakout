package de.tudarmstadt.informatik.fop.breakout.states;

import de.tudarmstadt.informatik.fop.breakout.actions.gui.EffectsSliderAction;
import de.tudarmstadt.informatik.fop.breakout.actions.gui.KeyBindingClickAction;
import de.tudarmstadt.informatik.fop.breakout.actions.gui.MusicSliderAction;
import de.tudarmstadt.informatik.fop.breakout.actions.gui.ParticleEffectsAction;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.events.MouseClickedEvent;
import de.tudarmstadt.informatik.fop.breakout.events.MousePressedEvent;
import de.tudarmstadt.informatik.fop.breakout.models.KeyBinding;
import de.tudarmstadt.informatik.fop.breakout.models.gui.BackButton;
import de.tudarmstadt.informatik.fop.breakout.models.gui.Checkbox;
import de.tudarmstadt.informatik.fop.breakout.models.gui.Slider;
import de.tudarmstadt.informatik.fop.breakout.views.MenuTitleRenderComponent;
import de.tudarmstadt.informatik.fop.breakout.views.SettingsTitleRenderComponent;
import de.tudarmstadt.informatik.fop.breakout.views.gui.CheckboxRenderComponent;
import de.tudarmstadt.informatik.fop.breakout.views.gui.KeyBindingRenderComponent;
import de.tudarmstadt.informatik.fop.breakout.views.gui.SliderRenderComponent;
import de.tudarmstadt.informatik.fop.breakout.views.gui.SeparatorRenderComponent;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * State showing changeable user settings
 */
public class SettingsState extends BasicGameState {

    private static final int LEFT_START_X = 175;
    private static final int Y_GAP = 75;

    private static final int RIGHT_START_X = 490;

    private final int stateId;
    private final StateBasedEntityManager entityManager = StateBasedEntityManager.getInstance();

    public SettingsState(int stateId) {
        this.stateId = stateId;
    }

    @Override
    public int getID() {
        return stateId;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        Entity background = new Entity("background");
        background.setPosition(new Vector2f(container.getWidth() / 2, container.getHeight() / 2));
        background.addComponent(new ImageRenderComponent(new Image(GameParameters.BLANK_BACKGROUND_IMAGE)));
        entityManager.addEntity(stateId, background);

        Entity titleEntity = new Entity("title");
        titleEntity.addComponent(new MenuTitleRenderComponent("title" + GameParameters.EXT_VIEW, GameParameters.SETTINGS_TITLE));
        entityManager.addEntity(stateId, titleEntity);

        float previousY = addEffectsSlider(container.getSoundVolume());
        previousY = addMusicSlider(previousY, container.getMusicVolume());
        previousY = addParticleBox(previousY);

        addKeymapOptions(container, previousY);

        entityManager.addEntity(stateId, new BackButton());
    }

    /**
     * Add the section for the keybindings
     *
     * @param container current game container
     * @param previousY y position of the previous component
     */
    private void addKeymapOptions(GameContainer container, float previousY) {
        //start key
        Entity startTitle = new Entity("start_title");
        startTitle.setPosition(new Vector2f(LEFT_START_X, previousY + Y_GAP));
        startTitle.addComponent(new SettingsTitleRenderComponent("start_title" + GameParameters.EXT_VIEW, "Start game"));
        entityManager.addEntity(stateId, startTitle);

        Entity startKey = new Entity("start_key");
        startKey.setPosition(new Vector2f(LEFT_START_X + 150, startTitle.getPosition().getY()));
        startKey.setSize(new Vector2f(100, 40));
        startKey.addComponent(new KeyBindingRenderComponent("start_key" + GameParameters.EXT_VIEW, KeyBinding.START_GAME));

        MouseClickedEvent startClickEvent = new MouseClickedEvent();
        startClickEvent.addAction(new KeyBindingClickAction(KeyBinding.START_GAME));
        startKey.addComponent(startClickEvent);
        entityManager.addEntity(stateId, startKey);

        //left move key
        Entity leftTitle = new Entity("left_title");
        leftTitle.setPosition(new Vector2f(LEFT_START_X, startTitle.getPosition().getY() + Y_GAP));
        leftTitle.addComponent(new SettingsTitleRenderComponent("left_title" + GameParameters.EXT_VIEW, "Move left"));
        entityManager.addEntity(stateId, leftTitle);

        Entity leftKey = new Entity("start_key");
        leftKey.setPosition(new Vector2f(LEFT_START_X + 150, leftTitle.getPosition().getY()));
        leftKey.setSize(new Vector2f(100, 40));
        leftKey.addComponent(new KeyBindingRenderComponent("start_key" + GameParameters.EXT_VIEW, KeyBinding.LEFT_MOVE));

        MouseClickedEvent leftClickAction = new MouseClickedEvent();
        leftClickAction.addAction(new KeyBindingClickAction(KeyBinding.LEFT_MOVE));
        leftKey.addComponent(leftClickAction);
        entityManager.addEntity(stateId, leftKey);

        //separator
        Entity splitter = new Entity("splitter");
        splitter.setPosition(new Vector2f(container.getWidth() / 2, startTitle.getPosition().getY() + Y_GAP / 2));
        splitter.setSize(new Vector2f(5, 175));
        splitter.addComponent(new SeparatorRenderComponent("splitter" + GameParameters.EXT_VIEW));
        entityManager.addEntity(stateId, splitter);

        //pause key
        Entity pauseTitle = new Entity("pause_title");
        pauseTitle.setPosition(new Vector2f(RIGHT_START_X, previousY + Y_GAP));
        pauseTitle.addComponent(new SettingsTitleRenderComponent("pause_title" + GameParameters.EXT_VIEW, "Pause"));
        entityManager.addEntity(stateId, pauseTitle);

        Entity pauseKey = new Entity("pause_key");
        pauseKey.setPosition(new Vector2f(RIGHT_START_X + 150, pauseTitle.getPosition().getY()));
        pauseKey.setSize(new Vector2f(100, 40));
        pauseKey.addComponent(new KeyBindingRenderComponent("pause_key" + GameParameters.EXT_VIEW, KeyBinding.PAUSE));

        MouseClickedEvent pauseClickAction = new MouseClickedEvent();
        pauseClickAction.addAction(new KeyBindingClickAction(KeyBinding.PAUSE));
        pauseKey.addComponent(pauseClickAction);
        entityManager.addEntity(stateId, pauseKey);

        //right move key
        Entity rightTitle = new Entity("right_title");
        rightTitle.setPosition(new Vector2f(RIGHT_START_X, pauseTitle.getPosition().getY() + Y_GAP));
        rightTitle.addComponent(new SettingsTitleRenderComponent("right_title" + GameParameters.EXT_VIEW, "Move Right"));
        entityManager.addEntity(stateId, rightTitle);

        Entity rightKey = new Entity("start_key");
        rightKey.setPosition(new Vector2f(RIGHT_START_X + 150, rightTitle.getPosition().getY()));
        rightKey.setSize(new Vector2f(100, 40));
        rightKey.addComponent(new KeyBindingRenderComponent("start_key" + GameParameters.EXT_VIEW, KeyBinding.RIGHT_MOVE));

        MouseClickedEvent rightClickAction = new MouseClickedEvent();
        rightClickAction.addAction(new KeyBindingClickAction(KeyBinding.RIGHT_MOVE));
        rightKey.addComponent(rightClickAction);
        entityManager.addEntity(stateId, rightKey);
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
        particleTitle.addComponent(new SettingsTitleRenderComponent("particle_title" + GameParameters.EXT_VIEW, "Particle"));
        entityManager.addEntity(stateId, particleTitle);

        Entity particleCheck = new Checkbox("particle_check", true);
        particleCheck.setPosition(new Vector2f(RIGHT_START_X, particleTitle.getPosition().getY()));
        particleCheck.setSize(new Vector2f(25, 25));

        MouseClickedEvent mousePressedEvent = new MouseClickedEvent();
        mousePressedEvent.addAction(new ParticleEffectsAction());
        particleCheck.addComponent(mousePressedEvent);

        particleCheck.addComponent(new CheckboxRenderComponent("particle_check" + GameParameters.EXT_VIEW));
        entityManager.addEntity(stateId, particleCheck);
        return particleTitle.getPosition().getY();
    }

    /**
     * Add the section for the effects sound volume
     *
     * @param effectsVolume current effects volume value
     * @return max y position of the current component
     */
    private float addEffectsSlider(float effectsVolume) {
        //slider type text
        Entity effectsTitle = new Entity("effects_title");
        effectsTitle.setPosition(new Vector2f(LEFT_START_X, 150));
        effectsTitle.addComponent(new SettingsTitleRenderComponent("effects_title" + GameParameters.EXT_VIEW, "Effects"));
        entityManager.addEntity(stateId, effectsTitle);

        Slider effectsSlider = new Slider("effects_slider", effectsVolume);
        effectsSlider.setPosition(new Vector2f(RIGHT_START_X, effectsTitle.getPosition().getY()));
        effectsSlider.setSize(new Vector2f(300, 50));
        effectsSlider.addComponent(new SliderRenderComponent());

        //slider listener
        MousePressedEvent sliderClickEvent = new MousePressedEvent();
        sliderClickEvent.addAction(new EffectsSliderAction());
        effectsSlider.addComponent(sliderClickEvent);

        entityManager.addEntity(stateId, effectsSlider);
        return effectsTitle.getPosition().getY();
    }

    /**
     * Add the section for the music sound volume
     *
     * @param previousCompY y position of the previous component
     * @param musicVolume current music volume value
     * @return max y position of the current component
     */
    private float addMusicSlider(float previousCompY, float musicVolume) {
        //slider type text
        Entity musicTitle = new Entity("music_title");
        musicTitle.setPosition(new Vector2f(LEFT_START_X, previousCompY + Y_GAP));
        musicTitle.addComponent(new SettingsTitleRenderComponent("music_title" + GameParameters.EXT_VIEW, "Music"));
        entityManager.addEntity(stateId, musicTitle);

        //slider
        Slider musicSlider = new Slider("music_slider", musicVolume);
        musicSlider.setPosition(new Vector2f(RIGHT_START_X, musicTitle.getPosition().getY()));
        musicSlider.setSize(new Vector2f(300, 50));
        musicSlider.addComponent(new SliderRenderComponent());

        //slider listener
        MousePressedEvent sliderClickEvent = new MousePressedEvent();
        sliderClickEvent.addAction(new MusicSliderAction());
        musicSlider.addComponent(sliderClickEvent);

        entityManager.addEntity(stateId, musicSlider);
        return musicTitle.getPosition().getY();
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        entityManager.renderEntities(container, game, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        entityManager.updateEntities(container, game, delta);
    }
}
