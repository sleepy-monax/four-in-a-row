package utils;

import java.io.Serializable;

public class SettingsManager implements Serializable {
    private static final String CONFIG_PATH = "settings.json";
    private static SettingsManager instance;

    private boolean audioMuted = false;
    private double audioMusicVolume = 0.75;
    private double audioEffectVolume = 0.75;

    private String playerName = "LocalPlayer";

    private boolean graphicFullscreen = false;
    private double graphicParallax = 1;

    public static SettingsManager get() {
        if (instance == null) {
            load();
        }

        return instance;
    }

    public static void load() {
        instance = Serialization.readFromJsonFile(CONFIG_PATH, SettingsManager.class);

        if (instance == null) {
            instance = new SettingsManager();
            save();
        }
    }

    public static void save() {
        if (instance != null) {
            Serialization.writeToJsonFile(CONFIG_PATH, instance);
        }
    }

    public static void apply()
    {
        AudioManager.setMuted(get().audioMuted);
        AudioManager.setMusicVolume(get().audioMusicVolume);
        AudioManager.setEffectVolume(get().audioEffectVolume);

        StageManager.setFullscreen(get().graphicFullscreen);
        StageManager.background().setParallax(get().graphicParallax);
    }

    public boolean getAudioMuted() {
        return this.audioMuted;
    }

    public void setAudioMuted(boolean audioMuted) {
        this.audioMuted = audioMuted;

        apply();
    }

    public void toggleAudioMuted()
    {
        setAudioMuted(!getAudioMuted());
    }

    public double getAudioMusicVolume() {
        return this.audioMusicVolume;
    }

    public void setAudioMusicVolume(double audioMusicVolume) {
        this.audioMusicVolume = audioMusicVolume;

        apply();
    }

    public double getAudioEffectVolume() {
        return this.audioEffectVolume;
    }

    public void setAudioEffectVolume(double audioEffectVolume) {
        this.audioEffectVolume = audioEffectVolume;

        apply();
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;

        apply();
    }

    public boolean isGraphicFullscreen() {
        return graphicFullscreen;
    }

    public void setGraphicFullscreen(boolean graphicFullscreen) {
        this.graphicFullscreen = graphicFullscreen;
        apply();
    }

    public void toggleGraphicFullscreen()
    {
        setGraphicFullscreen(!isGraphicFullscreen());
    }

    public double getGraphicParallax() {
        return graphicParallax;
    }

    public void setGraphicParallax(double graphicParallax) {
        this.graphicParallax = graphicParallax;
        apply();
    }
}