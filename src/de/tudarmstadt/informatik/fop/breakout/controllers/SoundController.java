package de.tudarmstadt.informatik.fop.breakout.controllers;

import de.tudarmstadt.informatik.fop.breakout.models.SoundType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.SoundStore;

import java.io.IOException;
import java.util.EnumMap;

/**
 * Manages sound loading, playing and caching. It loads the sound files into memory and caches. Then
 * the sound type doesn't need to be loaded every time it's accessed.
 */
public class SoundController implements AutoCloseable {

    private final Logger logger = LogManager.getLogger();
    private final SoundStore soundStore = SoundStore.get();
    private final EnumMap<SoundType, Audio> loadedSound = new EnumMap<>(SoundType.class);

    private float volume = 0.1f;

    /**
     * @return gets the current volume as a float 1.0f = 100% 0.0f = 0%
     */
    public float getVolume() {
        return volume;
    }

    /**
     * Sets the new volume which takes effect on the next sound effect
     *
     * @param volume a float value between 0.0 = 0% and 1.0 = 100%
     */
    public void setVolume(float volume) {
        this.volume = volume;
    }

    /**
     * Loads the sound file from disk and caches it into memory
     *
     * @param types a list of all sounds that should be loaded
     */
    public void load(SoundType... types) {
        //load sound system if not already done
        soundStore.init();
        if (!soundStore.soundWorks()) {
            logger.warn("Failed to connect to the sound system");
        }

        for (SoundType sound : types) {
            logger.info("Loading sound file: {}", sound.getSoundPath());
            try {
                Audio wav = soundStore.getWAV(sound.getSoundPath());
                loadedSound.put(sound, wav);
            } catch (IOException ioEx) {
                logger.error("Failed to load sound file {} - cause {}", sound.name(), ioEx);
            }
        }
    }

    /**
     * Play a sound effect without looping and with from the user specified volume
     *
     * @param type the sound type that should be played
     */
    public void playEffect(SoundType type) {
        Audio audio = loadedSound.get(type);
        if (audio == null) {
            logger.warn("Sound file {} isn't loaded and cannot be played now", type.name());
            return;
        }

        logger.info("Playing sound effect {}", type.name());
        audio.playAsSoundEffect(1.0f, volume, false);
    }

    /**
     * Play this sound type on the music channel
     *
     * @param type the sound type that should be played as a loop
     */
    public void playMusic(SoundType type) {
        Audio audio = loadedSound.get(type);
        if (audio == null) {
            logger.warn("Sound file {} isn't loaded and cannot be played now", type.name());
            return;
        }

        logger.info("Playing music {}", type.name());

        //turn it on if it was off before
        soundStore.setMusicOn(true);
        audio.playAsMusic(1.0f, volume, true);
    }

    /**
     * Stops the background music loop.
     *
     * This removes the lock in order to free the memory.
     */
    public void stopMusic() {
        soundStore.setMusicOn(false);
    }

    @Override
    public void close() {
        logger.debug("Cleaning up sound resources");

        stopMusic();

        //release allocated memory
        for (Audio audio : loadedSound.values()) {
            audio.release();
        }
    }
}
