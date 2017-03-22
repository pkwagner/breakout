package de.tudarmstadt.informatik.fop.breakout.models;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.views.StickRenderComponent;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import org.lwjgl.util.vector.Vector;
import org.newdawn.slick.geom.Vector2f;


public class StickModel extends Entity {

    private Vector velocity;
    private final PlayerModel owner;
    private StickRenderComponent view;
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

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
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
     * @param thrust
     */
    public void setThrust(boolean thrust) {
        this.thrust = thrust;
    }
    
    @Override
    public void addComponent(Component component){
    	if(component instanceof StickRenderComponent){
    		view = (StickRenderComponent) component;
    	}
    	super.addComponent(component);
    }
    
    public StickRenderComponent getView(){
    	return view;
    }
}
