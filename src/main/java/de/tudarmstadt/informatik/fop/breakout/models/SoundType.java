package de.tudarmstadt.informatik.fop.breakout.models;

public enum SoundType {

    BACKGROUND_MUSIC("src/main/resources/sounds/background_music.wav"),

    ITEM_PICKUP("src/main/resources/sounds/itemHitStick.wav"),

    STICK_HIT("src/main/resources/sounds/hitStick.wav"),

    BLOCK_HIT("src/main/resources/sounds/hitBlock.wav");

    private final String soundPath;

    SoundType(String soundPath) {
        this.soundPath = soundPath;
    }

    public String getSoundPath() {
        return soundPath;
    }
}
