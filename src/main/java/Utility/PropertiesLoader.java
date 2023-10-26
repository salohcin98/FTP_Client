package Utility;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Loads property files into the app
 */
public class PropertiesLoader {

    // cached names and properties
    private static final List<String> cachedFilenames = new ArrayList<>();
    private static final List<Properties> cachedProperties = new ArrayList<>();

    /**
     * index of property in the lists
     */
    private int current;

    /**
     * The resource file name. resources are cached, so you may make as many copies
     * of the same property as you need.
     * @param resourceFileName the filename of the resource. the extension may be omitted
     */
    public PropertiesLoader(String resourceFileName) {
        // add file extension if missing
        if (!resourceFileName.endsWith(".properties")) resourceFileName += ".properties";

        //check if property is cached
        if ((current = cachedFilenames.indexOf(resourceFileName)) == -1)
            // it is not cached
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceFileName)) {
                Properties properties = new Properties();
                properties.load(inputStream);

                //cache it
                current = cachedFilenames.size();
                cachedFilenames.add(resourceFileName);
                cachedProperties.add(properties);

            } catch (Exception e) {
                throw new RuntimeException("Failed to load: " + resourceFileName + "\nmake sure it is in the resource directory", e);
            }
        // if cached current is already set correctly
    }

    /**
     * Returns the property value
     * @param key the key of the property you are requesting
     * @return @{String} value of the property from the file
     */
    public String getProperty(String key) {
        return cachedProperties.get(current).getProperty(key);
    }
}