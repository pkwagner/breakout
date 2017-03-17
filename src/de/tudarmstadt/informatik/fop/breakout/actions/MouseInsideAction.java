package de.tudarmstadt.informatik.fop.breakout.actions;

import eea.engine.action.Action;
import eea.engine.component.Component;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Checks if the mouse actual clicked inside the boundingbox of this component. If not it's not relevant for us.
 */
public abstract class MouseInsideAction implements Action {

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta, Component event) {
        Input input = gc.getInput();
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        //check if the user actual clicked inside the button
        if (event.getOwnerEntity().getShape().contains(mouseX, mouseY)) {
            onUpdate(gc, sb, delta, event);
        }
    }

    /**
     * This method only trigger if the mouse clicked inside the boundingbox of this component.
     *
     * The method arguments are the same as {@link #update(GameContainer, StateBasedGame, int, Component)}. So please
     * use that method for documentation.
     *
     * @see #update(GameContainer, StateBasedGame, int, Component)
     */
    public abstract void onUpdate(GameContainer gc, StateBasedGame stateBasedGame, int delta, Component event);
}
