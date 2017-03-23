package de.tudarmstadt.informatik.fop.breakout.views.game;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.game.StickModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import de.tudarmstadt.informatik.fop.breakout.util.Utility;

import eea.engine.component.RenderComponent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.StateBasedGame;

import java.io.File;
import java.nio.file.Paths;

/**
 * Renders the stick including the correct scaled width on bigger/small item pickup
 */
public class StickView extends RenderComponent {

    private static final Logger logger = LogManager.getLogger();

    private Image leftImage;
    private Image rightImage;
    private Image middleImage;
    private ParticleSystem flameLeftParticleSystem;
    private ParticleSystem flameRightParticleSystem;
    private ConfigurableEmitter flameLeftEmitter;
    private ConfigurableEmitter flameRightEmitter;
    private int currentThrustTime = 0;
    private boolean isSecondPlayer = false;

    public StickView() throws SlickException {
        super(GameParameters.STICK_ID + GameParameters.EXT_VIEW);

        leftImage = new Image(GameParameters.STICK_LEFT_IMAGE);
        middleImage = new Image(GameParameters.STICK_MIDDLE_IMAGE);
        rightImage = new Image(GameParameters.STICK_RIGHT_IMAGE);

        Image flameParticle = new Image(GameParameters.FLAME_PARTICLE_IMAGE);
        flameLeftParticleSystem = new ParticleSystem(flameParticle, 700);
        flameRightParticleSystem = new ParticleSystem(flameParticle, 700);

        try {
            File flameLeftEmitterFile = Paths.get("." + GameParameters.FLAME_LEFT_EMITTER_FILE).toFile();
            File flameRightEmitterFile = Paths.get("." + GameParameters.FLAME_RIGHT_EMITTER_FILE).toFile();

            flameLeftEmitter = ParticleIO.loadEmitter(flameLeftEmitterFile);
            flameRightEmitter = ParticleIO.loadEmitter(flameRightEmitterFile);

            flameLeftParticleSystem.addEmitter(flameLeftEmitter);
            flameRightParticleSystem.addEmitter(flameRightEmitter);
        } catch (Exception e) {
            logger.error("Tried loading a emitter file but following error occured: " + e);
        }

        flameLeftParticleSystem.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);
        flameRightParticleSystem.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);
    }

    @Override
    public StickModel getOwnerEntity() {
        return (StickModel) super.getOwnerEntity();
    }

    public void init() {
        // If the controller is the second player, flip the images
        if (getOwnerEntity().getOwner().isSecondPlayer()) {
            leftImage = leftImage.getFlippedCopy(false, true);
            middleImage = middleImage.getFlippedCopy(false, true);
            rightImage = rightImage.getFlippedCopy(false, true);
            isSecondPlayer = true;
        }
    }

    @Override
    public Vector2f getSize() {
        //the model size will be resized every time so rely on that object
        return getOwnerEntity().getSize();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics graphicsContext) {
        // retrieve the underlying entity's position
        Shape stickShape = owner.getShape();

        int widthOfSide = leftImage.getWidth();
        int widthOfMiddle = (int) owner.getSize().getX() - widthOfSide * 2;
        int height = middleImage.getHeight();


        leftImage.draw(stickShape.getMinX(), stickShape.getMinY());

        middleImage.draw(stickShape.getMinX() + widthOfSide, stickShape.getMinY(), widthOfMiddle, height);

        rightImage.draw(stickShape.getMinX() + widthOfSide + widthOfMiddle, stickShape.getMinY());

        GameplayState state = (GameplayState) sb.getState(GameParameters.GAMEPLAY_STATE);
        if (state.isParticleEffectsEnabled()) {
            flameLeftParticleSystem.render();
            flameRightParticleSystem.render();
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        GameplayState state = (GameplayState) sb.getState(GameParameters.GAMEPLAY_STATE);
        if (state.isParticleEffectsEnabled()) {
            Shape stickShape = owner.getShape();

            if (getOwnerEntity().isThrust()) { //thrust occurs when the ball hits the stick
                flameLeftEmitter.gravityFactor.setValue(Utility.map(currentThrustTime, 0, GameParameters.THRUST_DURATION, GameParameters.EMITTER_GRAVITY_MAX, GameParameters.EMITTER_GRAVITY_MIN));
                flameRightEmitter.gravityFactor.setValue(Utility.map(currentThrustTime, 0, GameParameters.THRUST_DURATION, GameParameters.EMITTER_GRAVITY_MAX, GameParameters.EMITTER_GRAVITY_MIN));

                flameLeftEmitter.initialLife.setMax(Utility.map(currentThrustTime, 0, GameParameters.THRUST_DURATION, GameParameters.EMITTER_LIFE_MAX, GameParameters.EMITTER_LIFE_MIN));
                flameRightEmitter.initialLife.setMax(Utility.map(currentThrustTime, 0, GameParameters.THRUST_DURATION, GameParameters.EMITTER_LIFE_MAX, GameParameters.EMITTER_LIFE_MIN));

                flameLeftEmitter.speed.setMax(Utility.map(currentThrustTime, 0, GameParameters.THRUST_DURATION, GameParameters.EMITTER_SPEED_MAX, GameParameters.EMITTER_SPEED_MIN));
                flameRightEmitter.speed.setMax(Utility.map(currentThrustTime, 0, GameParameters.THRUST_DURATION, GameParameters.EMITTER_SPEED_MAX, GameParameters.EMITTER_SPEED_MIN));
                currentThrustTime += delta;

                if (currentThrustTime > GameParameters.THRUST_DURATION) {
                    getOwnerEntity().setThrust(false);
                    currentThrustTime = 0;
                }
            }

            int x, y;

            if (isSecondPlayer) y = (int) (stickShape.getMaxY() - GameParameters.EMITTER_Y_OFFSET);
            else y = (int) (stickShape.getMinY() + GameParameters.EMITTER_Y_OFFSET);

            x = (int) stickShape.getMinX();
            flameLeftEmitter.setPosition(x, y, false);
            flameLeftParticleSystem.update(delta);


            x = (int) stickShape.getMaxX();
            flameRightEmitter.setPosition(x, y, false);
            flameRightParticleSystem.update(delta);
        }
    }

    public void resetParticleSystems() {
        Shape stickShape = owner.getShape();
        stickShape = owner.getShape();
        int x, y;

        if (isSecondPlayer)
            y = (int) (stickShape.getMaxY() - GameParameters.EMITTER_Y_OFFSET);
        else
            y = (int) (stickShape.getMinY() + GameParameters.EMITTER_Y_OFFSET);

        x = (int) stickShape.getMinX();
        flameLeftEmitter.setPosition(x, y, false);
        flameLeftParticleSystem.update(1000);


        x = (int) stickShape.getMaxX();
        flameRightEmitter.setPosition(x, y, false);
        flameRightParticleSystem.update(1000);
    }
}
