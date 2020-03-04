package controller;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioController {
	private static MediaPlayer mediaPlayer;

	public AudioController() {
	}

	public static void initialize() {
		if (mediaPlayer == null) {
			String path = "src/assets/AudioBackground.mp3";
			Media media = new Media(new File(path).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.play();
		}
	}

	public static void shutdown() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer = null;
		}
	}
}
