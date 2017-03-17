package de.tudarmstadt.informatik.fop.breakout.actions.gui;

import de.tudarmstadt.informatik.fop.breakout.actions.MouseInsideAction;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.KeyBinding;
import de.tudarmstadt.informatik.fop.breakout.views.gui.KeyBindingRenderComponent;
import de.tudarmstadt.informatik.fop.breakout.views.gui.WaitingKeybindingRenderComponent;

import eea.engine.component.Component;
import eea.engine.entity.Entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.InputAdapter;

import java.util.stream.Stream;

/**
 * Called if the user wants to change the keybinding and this listens to the new key for a certain keybinding.
 */
public class KeyBindingClickAction extends MouseInsideAction {

    private final Logger logger = LogManager.getLogger();
    private final KeyBinding keyBinding;

    public KeyBindingClickAction(KeyBinding keyBinding) {
        this.keyBinding = keyBinding;
    }

    @Override
    public void onUpdate(GameContainer gc, StateBasedGame stateBasedGame, int delta, Component event) {
        Entity ownerEntity = event.getOwnerEntity();

        String viewId = ownerEntity.getID() + GameParameters.EXT_VIEW;

        //remove the old view and set a new one to inform the user that we are now waiting for the new key
        ownerEntity.removeComponent(viewId);
        ownerEntity.addComponent(new WaitingKeybindingRenderComponent(viewId));

        InputAdapter inputListener = new InputAdapter() {
            @Override
            public void keyPressed(int key, char character) {
                restoreView();
                if (isDuplicate(key)) {
                    //abort if the key is already in use
                    logger.info("Duplicate key -> cancel user input");
                    return;
                }

                //update the binding
                logger.info("New keybinding {} for {}", key, keyBinding.name());
                keyBinding.setCurrentBinding(key);
            }

            @Override
            public void mouseClicked(int button, int x, int y, int clickCount) {
                //cancel listening if the user left the state or wants to change another keybinding
                logger.debug("User clicked outside -> cancel keybinding listener");
                restoreView();
            }

            /**
             * Loops through all available keybinding and check if key is already in use
             *
             * @param key the key to check
             * @return true if the key is already in use
             */
            private boolean isDuplicate(int key) {
                return Stream.of(KeyBinding.values())
                        .mapToInt(KeyBinding::getCurrentBinding)
                        .anyMatch(keyBinding -> keyBinding == key);
            }

            /**
             * Restores the previous view and remove our temporary view
             */
            private void restoreView() {
                //restore the previous render component
                ownerEntity.removeComponent(viewId);
                ownerEntity.addComponent(new KeyBindingRenderComponent(viewId, keyBinding));

                //remove this listener again to abort a keybinding change
                gc.getInput().removeKeyListener(this);
                gc.getInput().removeMouseListener(this);
            }
        };

        //add our listener for both key and mouse events
        gc.getInput().addListener(inputListener);
    }
}
