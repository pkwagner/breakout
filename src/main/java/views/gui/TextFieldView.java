package views.gui;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;

/**
 * TextFieldView that can be set invisible
 */
public class TextFieldView extends org.newdawn.slick.gui.TextField {

    private boolean visible = true;

    public TextFieldView(GUIContext container, Font font, int x, int y, int width, int height) {
        super(container, font, x, y, width, height);
    }

    public TextFieldView(GUIContext container, Font font, int x, int y, int width, int height, ComponentListener componentListener) {
        super(container, font, x, y, width, height, componentListener);
    }

    @Override
    public void render(GUIContext container, Graphics graphics) {
        if (visible)
            super.render(container, graphics);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
