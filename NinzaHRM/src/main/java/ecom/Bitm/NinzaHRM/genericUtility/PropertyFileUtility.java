package ecom.Bitm.NinzaHRM.genericUtility;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Reads application settings from DD/config1.properties. */
public final class PropertyFileUtility {
    private static final Properties PROPERTIES = new Properties();

    static {
        String configuredPath = System.getProperty("configFile");
        try (InputStream input = configuredPath != null
                ? Files.newInputStream(Path.of(configuredPath))
                : PropertyFileUtility.class.getClassLoader()
                    .getResourceAsStream("DD/config1.properties")) {
            if (input == null) {
                throw new IllegalStateException("Could not find DD/config1.properties");
            }
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private PropertyFileUtility() { }

    public static String getData(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing or blank configuration property: " + key);
        }
        return value.trim();
    }

    public static String getDataOrDefault(String key, String defaultValue) {
        String value = PROPERTIES.getProperty(key);
        return value == null || value.isBlank() ? defaultValue : value.trim();
    }
}
