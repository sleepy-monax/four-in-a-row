package controller;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;

import javafx.animation.Interpolator;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import utils.Main;

public class AudioController {
	private static final int MAX_SOUND_EFFECTS = 8;

	private static HashMap<String, Media> soundCache = new HashMap<>();
	private static MediaPlayer musicPlayer;
	private static LinkedList<MediaPlayer> effectPlayers = new LinkedList<>();

	public AudioController() {
	}

	public static void initialize() {
		playNow("assets/intro.wav", () -> {
			playLoopNow("assets/loop.wav");
		});
	}

	public static Media getMedia(String name) {
		if (!soundCache.containsKey(name)) {
			try {
				soundCache.put(name, new Media(Main.class.getResource("/" + name).toURI().toString()));
			} catch (URISyntaxException e) {
				return null;
			}
		}

		return soundCache.get(name);
	}

	public static void playNow(String name, Runnable then) {
		if (musicPlayer != null) {
			musicPlayer.stop();
		}

		musicPlayer = new MediaPlayer(getMedia(name));
		musicPlayer.setVolume(0.5);

		if (then != null) {
			musicPlayer.setOnEndOfMedia(then);
		}
		musicPlayer.play();
	}

	public static void playLoopNow(String name) {
		if (musicPlayer != null) {
			musicPlayer.stop();
		}

		musicPlayer = new MediaPlayer(getMedia(name));
		musicPlayer.setVolume(0.5);
		musicPlayer.setCycleCount(Integer.MAX_VALUE);
		musicPlayer.play();
	}

	public static void playEffect(String name) {
		if (effectPlayers.size() > MAX_SOUND_EFFECTS) {
			return;
		}

		MediaPlayer effectPlayer = new MediaPlayer(getMedia(name));

		effectPlayers.add(effectPlayer);

		effectPlayer.setOnEndOfMedia(() -> {
			effectPlayers.remove(effectPlayer);
			effectPlayer.dispose();
		});

		effectPlayer.play();
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
