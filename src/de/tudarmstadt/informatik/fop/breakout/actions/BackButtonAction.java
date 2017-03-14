package de.tudarmstadt.informatik.fop.breakout.actions;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

import eea.engine.action.Action;
import eea.engine.component.Component;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

/**
 * If a button assigned with this action is pressed the screen goes back to the main menu
 */
public class BackButtonAction implements Action {

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta, Component component) {
        Input input = gameContainer.getInput();
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        //check if the user actual clicked inside the button
        if (component.getOwnerEntity().getShape().contains(mouseX, mouseY)) {
            stateBasedGame.enterState(GameParameters.MAINMENU_STATE);
        }
    }
}
