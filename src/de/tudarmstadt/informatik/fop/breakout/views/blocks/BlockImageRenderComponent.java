package de.tudarmstadt.informatik.fop.breakout.views.blocks;

import eea.engine.component.RenderComponent;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public abstract class BlockImageRenderComponent extends RenderComponent {
    private Image image;

    BlockImageRenderComponent(String id, Image theImage) {
        super(id);

        this.image = theImage;
    }

    public void render(GameContainer gc, StateBasedGame sb, Graphics graphicsContext) {
        Vector2f pos = this.owner.getPosition();
        float scale = this.owner.getScale();
        this.image.draw(pos.x - (float) this.image.getWidth() / 2.0F * scale, pos.y - (float) this.image.getHeight() / 2.0F * scale, (float) this.image.getWidth() * scale, (float) this.image.getHeight() * scale);
    }

    public Vector2f getSize() {
        return new Vector2f((float) this.image.getWidth() * this.owner.getScale(), (float) this.image.getHeight() * this.owner.getScale());
    }

    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        float scale = this.owner.getScale();
        this.image.setCenterOfRotation((float) this.image.getWidth() / 2.0F * scale, (float) this.image.getHeight() / 2.0F * scale);
        this.image.rotate(this.owner.getRotation() - this.image.getRotation());
    }

    void updateImage(Image theImage) {
        this.image = theImage;
    }
}
