package controller;

import java.io.File;
import java.util.HashMap;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioController {
	private static MediaPlayer mediaPlayer;
	private static HashMap<String, Media> mediaCache = new HashMap<>();

	public AudioController() {
	}

	public static Media getMedia(String name) {
		if (!mediaCache.containsKey(name)) {
			mediaCache.put(name, new Media(new File(name).toURI().toString()));
		}

		return mediaCache.get(name);
	}

	public static void initialize() {
		playNow("src/assets/intro.wav", () -> {
			playLoopNow("src/assets/loop.wav", null);
		});
	}

	public static void playNow(String name, Runnable then) {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}
		mediaPlayer = new MediaPlayer(getMedia(name));
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
		mediaPlayer.setCycleCount(Integer.MAX_VALUE);
		if (then != null) {
			mediaPlayer.setOnEndOfMedia(then);
		}
		mediaPlayer.play();
	}

	public static void shutdown() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer = null;
		}
	}
}
