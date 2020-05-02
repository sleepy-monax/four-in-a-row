package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Serialization {
    public static <T> void writeToJsonFile(String path, T object) {
        try (
                Writer out = new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8)
        ) {
            new GsonBuilder()
                    .disableHtmlEscaping()
                    .setPrettyPrinting()
                    .create()
                    .toJson(object, out);
        } catch (Exception e) {
            System.out.println("SERIALIZATION: Failled to write to " + path + ": " + e.toString());
        }
    }

    public static <T> T readFromJsonFile(String path, Class<T> class_name) {
        T object = null;

        try (
                Reader in = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8);
                JsonReader reader = new JsonReader(in)
        ) {
            Gson gson = new Gson();
            object = gson.fromJson(reader, class_name);
        } catch (Exception e) {
            System.out.println("SERIALIZATION: Failled to read from " + path + ": " + e.toString());
        }

        return object;
    }

    public static <T> T readFromJsonFileInJar(String path, Class<T> class_name) {
        T object = null;

        try (
                Reader in = new InputStreamReader(class_name.getResourceAsStream(path), StandardCharsets.UTF_8);
                JsonReader reader = new JsonReader(in)
        ) {
            Gson gson = new Gson();
            object = gson.fromJson(reader, class_name);
        } catch (Exception e) {
            System.out.println("SERIALIZATION: Failled to Read from " + path + ": " + e.toString());
        }

        return object;
    }
}
