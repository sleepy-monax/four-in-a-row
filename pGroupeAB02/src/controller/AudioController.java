package controller;

import java.io.File;  

import javafx.application.Application;  
import javafx.scene.Group;  
import javafx.scene.media.Media;  
import javafx.scene.media.MediaPlayer;  
import javafx.scene.media.MediaView;  
import javafx.stage.Stage;  

public class AudioController {
	
	 public AudioController() {
	    }
	 
	 public static void AudioPlay() {
		 String path = "src/assets/AudioBackground.mp3";  
	        Media media = new Media(new File(path).toURI().toString());     
	        MediaPlayer mediaPlayer = new MediaPlayer(media);  
	        mediaPlayer.play();
	 }
	

}
