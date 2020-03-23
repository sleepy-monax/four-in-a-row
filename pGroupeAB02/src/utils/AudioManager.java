package utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.Main;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;

public class AudioManager {
    private static final int MAX_SOUND_EFFECTS = 16;

    private static boolean muted = false;
    private static double musicVolume = 1.0;
    private static double effectVolume = 1.0;

    private static HashMap<String, Media> soundCache = new HashMap<>();
    private static MediaPlayer musicPlayer;
    private static LinkedList<MediaPlayer> effectPlayers = new LinkedList<>();

    public AudioManager() {
    }

    public static void initialize() {
        playNow("assets/intro.wav", () -> {
            playLoopNow("assets/loop.wav");
        });
    }

    public static Media getMedia(String name) {
        if (!soundCache.containsKey(name)) {
            try {
                System.out.println("AUDIO: Media cache *miss* for '" + name + "' !");
                soundCache.put(name, new Media(Main.class.getResource("/" + name).toURI().toString()));
            } catch (URISyntaxException e) {
                return null;
            }
        } else {
            System.out.println("AUDIO: Media cache hit for '" + name + "'");
        }

        return soundCache.get(name);
    }

    public static double getMusicVolume() {
        return musicVolume;
    }

    public static double getEffectVolume() {
        return effectVolume;
    }

    public static boolean isMuted() {
        return muted;
    }

    public static void setMusicVolume(double musicVolume) {
        AudioManager.musicVolume = musicVolume;
        musicPlayer.setVolume(musicVolume);
    }

    public static void setEffectVolume(double effectVolume) {
        AudioManager.effectVolume = effectVolume;
        for (MediaPlayer effect : effectPlayers) {
            effect.setVolume(effectVolume);
        }
    }

    public static void setMuted(boolean muted) {
        AudioManager.muted = muted;

        musicPlayer.setMute(muted);

        if (muted) {
            musicPlayer.setVolume(0);
        } else {
            musicPlayer.setVolume(musicVolume);
        }

        for (MediaPlayer effect : effectPlayers) {
            effect.setMute(muted);

            if (muted) {
                effect.setVolume(0);
            } else {
                effect.setVolume(effectVolume);
            }
        }
    }

    public static void playNow(String name, Runnable then) {
        if (musicPlayer != null) {
            musicPlayer.stop();
            musicPlayer.dispose();
        }

        musicPlayer = new MediaPlayer(getMedia(name));
        musicPlayer.setMute(muted);

        if (muted) {
            musicPlayer.setVolume(0);
        } else {
            musicPlayer.setVolume(musicVolume);
        }

        if (then != null)
            musicPlayer.setOnEndOfMedia(then);

        musicPlayer.play();
    }

    public static void playLoopNow(String name) {
        if (musicPlayer != null) {
            musicPlayer.stop();
            musicPlayer.dispose();
        }

        musicPlayer = new MediaPlayer(getMedia(name));

        musicPlayer.setCycleCount(Integer.MAX_VALUE);
        musicPlayer.setMute(muted);

        if (muted) {
            musicPlayer.setVolume(0);
        } else {
            musicPlayer.setVolume(musicVolume);
        }

        musicPlayer.play();
    }

    public static void playEffect(String name) {
        playEffect(name, 1);
    }

    public static void playEffect(String name, double pitch) {
        if (effectPlayers.size() > MAX_SOUND_EFFECTS) {
            return;
        }

        MediaPlayer effectPlayer = new MediaPlayer(getMedia(name));

        effectPlayer.setMute(muted);
        effectPlayer.setRate(pitch);


        if (muted) {
            effectPlayer.setVolume(0);
        } else {
            effectPlayer.setVolume(effectVolume);
        }

        effectPlayer.setOnEndOfMedia(() -> {
            effectPlayers.remove(effectPlayer);
            effectPlayer.dispose();
        });

        effectPlayers.add(effectPlayer);
        effectPlayer.play();
    }

    public static void stop() {
        musicPlayer.stop();
    }

    public static void shutdown() {
        if (musicPlayer != null) {
            musicPlayer.stop();
            musicPlayer.dispose();
            musicPlayer = null;
        }

        for (MediaPlayer mediaPlayer : effectPlayers) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        effectPlayers.clear();
    }
}
