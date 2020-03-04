package controller;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import utils.Main;

public class AudioController {
	private static MediaPlayer mediaPlayer;
	private static HashMap<String, Media> mediaCache = new HashMap<>();
	private static LinkedList<MediaPlayer> effects = new LinkedList<>();

	public AudioController() {
	}

	public static Media getMedia(String name) {
		if (!mediaCache.containsKey(name)) {
			try {
				mediaCache.put(name, new Media(Main.class.getResource("/" + name).toURI().toString()));
			} catch (URISyntaxException e) {
				return null;
			}
		}

		return mediaCache.get(name);
	}

	public static void initialize() {
		playNow("assets/intro.wav", () -> {
			playLoopNow("assets/loop.wav", null);
		});
	}

	public static void playNow(String name, Runnable then) {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}
		mediaPlayer = new MediaPlayer(getMedia(name));
		mediaPlayer.setVolume(0.5);

		if (then != null) {
			mediaPlayer.setOnEndOfMedia(then);
		}
		mediaPlayer.play();
	}

	public static void playLoopNow(String name, Runnable then) {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}
		mediaPlayer = new MediaPlayer(getMedia(name));
		mediaPlayer.setVolume(0.5);
		mediaPlayer.setCycleCount(Integer.MAX_VALUE);
		if (then != null) {
			mediaPlayer.setOnEndOfMedia(then);
		}
		mediaPlayer.play();
	}

	public static void playEffect(String name) {
		MediaPlayer mediaPlayer = new MediaPlayer(getMedia(name));

		effects.add(mediaPlayer);

		mediaPlayer.setOnEndOfMedia(() -> {
			effects.remove(mediaPlayer);
		});

		mediaPlayer.play();
	}

	public static void shutdown() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer = null;
		}
	}
}
