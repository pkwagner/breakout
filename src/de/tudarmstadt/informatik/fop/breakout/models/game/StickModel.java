package de.tudarmstadt.informatik.fop.breakout.models.game;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.views.game.StickView;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import org.newdawn.slick.geom.Vector2f;


public class StickModel extends Entity {

    private Vector2f velocity = new Vector2f(GameParameters.STICK_SPEED, 0);
    private final PlayerModel owner;
    private StickView view;
    private boolean thrust;

    public StickModel(String stickId, PlayerModel owner) {
        super(stickId);

        this.owner = owner;
        this.setSize(new Vector2f(GameParameters.STICK_WIDTH, GameParameters.STICK_HEIGHT));
        setPassable(false);
    }

    public void setWidth(float width) {
        setSize(new Vector2f(width, GameParameters.STICK_HEIGHT));
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public PlayerModel getOwner() {
        return owner;
    }

    public boolean isThrust() {
        return thrust;
    }

    /**
     * Push more particles out of the particle system if true
     *
     * @param thrust Thrust value
     */
    public void setThrust(boolean thrust) {
        this.thrust = thrust;
    }

    @Override
    public void addComponent(Component component) {
        if (component instanceof StickView) {
            view = (StickView) component;
        }
        super.addComponent(component);
    }

    public StickView getView() {
        return view;
    }
}
