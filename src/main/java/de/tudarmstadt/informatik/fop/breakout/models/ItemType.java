package de.tudarmstadt.informatik.fop.breakout.models;

import de.tudarmstadt.informatik.fop.breakout.actions.game.items.*;

public enum ItemType {

    FasterItem("src/main/resources/images/faster-spritesheet.png", 1, 0, FasterItemAction.class),
    SlowerItem("src/main/resources/images/slower-spritesheet.png", 1, 0, SlowerItemAction.class),
    BiggerItem("src/main/resources/images/bigger-spritesheet.png", 1, 0, BiggerItemAction.class),
    SmallerItem("src/main/resources/images/smaller-spritesheet.png", 1, 0, SmallerItemAction.class),
    SmashBallItem("src/main/resources/images/smash-ball-spritesheet.png", 1, 3, SmashBallItemAction.class),
    SloMoItem("src/main/resources/images/slo-mo-spritesheet.png", 1, 1.5f, SloMoItemAction.class),
    HealthPointItem("src/main/resources/images/health-point-spritesheet.png", 1, 0, HealthPointItemAction.class),
    AdditionalBallItem("src/main/resources/images/additional-ball-spritesheet.png", 1, 0, AdditionalBallItemAction.class);

    private final String imagePath;
    private final double possibility;
    private final Class actionHandler;
    private final float duration;

    ItemType(String imagePath, double possibility, float duration, Class actionHandler) {
        this.imagePath = imagePath;
        this.possibility = possibility;
        this.duration = duration;
        this.actionHandler = actionHandler;
    }

    public String getImagePath() {
        return imagePath;
    }

    public double getPossibility() {
        return possibility;
    }

    public float getDuration() {
        return duration;
    }

    public Class getActionHandler() {
        return actionHandler;
    }
}
