package de.tudarmstadt.informatik.fop.breakout.models;

public enum SoundType {

    BACKGROUND_MUSIC("/sounds/background_music.wav"),

    BACKGROUND_MUSIC_SLOMO("/sounds/background_music.wav"),

    ITEM_PICKUP("/sounds/itemHitStick.wav"),

    STICK_HIT("/sounds/hitStick.wav"),

    BLOCK_HIT("/sounds/hitBlock.wav");

    private final String soundPath;

    SoundType(String soundPath) {
        this.soundPath = soundPath;
    }

    public String getSoundPath() {
        return soundPath;
    }
}
