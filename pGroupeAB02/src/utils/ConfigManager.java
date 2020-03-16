package utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ConfigManager {
    private static final String CONFIG_PATH = "config.json";
    private static ConfigManager instance;

    BooleanProperty effectEnabled = new SimpleBooleanProperty();

    public static ConfigManager get() {
        if (instance == null) {
            reload();
        }

        return instance;
    }

    public static void reload() {
        instance = Serialization.readFromJsonFile(CONFIG_PATH, ConfigManager.class);

        if (instance == null) {
            instance = new ConfigManager();
            save();
        }
    }

    public static void save() {
        if (instance != null) {
            Serialization.writeToJsonFile(CONFIG_PATH, instance);
        }
    }
}