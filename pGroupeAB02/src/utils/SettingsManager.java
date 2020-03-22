package utils;

import java.io.Serializable;

public class SettingsManager implements Serializable {
    private static final String CONFIG_PATH = "settings.json";
    private static SettingsManager instance;

    private boolean audioMuted = false;
    private double audioMusicVolume = 0.75;
    private double audioEffectVolume = 0.75;

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
}