package de.tudarmstadt.informatik.fop.breakout.actions.gui;

import de.tudarmstadt.informatik.fop.breakout.actions.MouseInsideAction;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

import eea.engine.component.Component;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

/**
 * If a button assigned with this action is pressed the screen goes back to the main menu
 */
public class BackButtonAction extends MouseInsideAction {

    @Override
    public void onUpdate(GameContainer gc, StateBasedGame stateBasedGame, int delta, Component event) {
        stateBasedGame.enterState(GameParameters.MAINMENU_STATE);
    }
}
