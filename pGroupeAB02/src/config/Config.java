package config;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import utils.Serialization;

public class Config {
    private static final String CONFIG_PATH = "config.json";
    private static Config instance;

    BooleanProperty effectEnabled = new SimpleBooleanProperty();

    public static Config get() {
        if (instance == null) {
            reload();
        }

        return instance;
    }

    public static void reload() {
        instance = Serialization.readFromJsonFile(CONFIG_PATH, Config.class);

        if (instance == null) {
            instance = new Config();
            save();
        }
    }

    public static void save() {
        if (instance != null) {
            Serialization.writeToJsonFile(CONFIG_PATH, instance);
        }
    }
}