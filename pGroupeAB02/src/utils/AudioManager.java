package utils;

import audio.AudioBuffer;
import audio.AudioSource;
import audio.OpenAL;
import main.Main;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;

public class AudioManager {
    private static final int MAX_SOUND_EFFECTS = 16;

    private static boolean muted = false;
    private static double musicVolume = 1.0;
    private static double effectVolume = 1.0;

    private static AudioSource musicSource = null;
    private static final LinkedList<AudioSource> effectSources = new LinkedList<>();
    private static final HashMap<String, AudioBuffer> bufferCache = new HashMap<>();

    public AudioManager() {
    }

    private static AudioSource makeNewMusicSource() {
        if (musicSource != null) {
            musicSource.close();
        }

        musicSource = new AudioSource();
        musicSource.setMuted(muted);
        musicSource.setVolume(musicVolume);

        return musicSource;
    }

    public static void initialize() {
        OpenAL.initialize();
    }

    public static void shutdown() {
        if (musicSource != null) {
            musicSource.close();
        }

        for (AudioSource effectSource : effectSources) {
            effectSource.close();
        }

        for (AudioBuffer buffer : bufferCache.values()) {
            buffer.close();
        }

        bufferCache.clear();
        OpenAL.shutdown();
    }

    public static AudioBuffer getBuffer(String name) {
        if (!bufferCache.containsKey(name)) {
            try {
                System.out.println("AUDIO: Buffer cache *miss* for '" + name + "' !");
                String bufferURI = Main.class.getResource("/" + name).toURI().toString();
                System.out.println("AUDIO: Loading Buffer from " + bufferURI);

                bufferCache.put(name, AudioBuffer.loadFrom(name));
            } catch (URISyntaxException e) {
                return null;
            }
        } else {
            System.out.println("AUDIO: Buffer cache hit for '" + name + "'");
        }

        return bufferCache.get(name);
    }

    public static double getMusicVolume() {
        return musicVolume;
    }

    public static void setMusicVolume(double musicVolume) {
        AudioManager.musicVolume = musicVolume;

        if (musicSource != null) {
            musicSource.setVolume(musicVolume);
        }
    }

    public static double getEffectVolume() {
        return effectVolume;
    }

    public static void setEffectVolume(double effectVolume) {
        AudioManager.effectVolume = effectVolume;
        for (AudioSource effectSource : effectSources) {
            effectSource.setVolume(effectVolume);
        }
    }

    public static boolean isMuted() {
        return muted;
    }

    public static void setMuted(boolean muted) {
        AudioManager.muted = muted;

        if (musicSource != null) {
            musicSource.setMuted(muted);
        }

        for (AudioSource effectSource : effectSources) {
            effectSource.setMuted(muted);
        }
    }

    public static void playNow(String name, Runnable then) {
        makeNewMusicSource().playNow(getBuffer(name), then);
    }

    public static void playLoopWithTransition(String loop, String Transition) {
        makeNewMusicSource().playLoopWithTransition(getBuffer(loop), getBuffer(Transition));
    }

    public static void playLoopNow(String name) {
        makeNewMusicSource().playLoop(getBuffer(name));
    }

    public static void playEffect(String name) {
        playEffect(name, 1);
    }

    public static void playEffect(String name, double pitch) {
        if (effectSources.size() > MAX_SOUND_EFFECTS) {
            return;
        }

        AudioSource effectSource = new AudioSource();

        effectSource.setMuted(muted);
        effectSource.setPitch(pitch);
        effectSource.setVolume(effectVolume);

        effectSources.add(effectSource);
        effectSource.playNow(getBuffer(name), () -> {
            effectSources.remove(effectSource);
            effectSource.close();
        });

    }

    public static void stop() {
        musicSource.reset();
    }
}
