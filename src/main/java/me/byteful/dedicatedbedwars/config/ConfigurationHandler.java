package me.byteful.dedicatedbedwars.config;

import me.byteful.dedicatedbedwars.app.DedicatedBedwars;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigurationHandler {
  private static final String CONFIG_RESOURCE_ID = "server.properties";
  private final Configuration config;

  public ConfigurationHandler(Path configFile) {
    try {
      this.config = loadFromFile(configFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static Configuration loadFromFile(Path file) throws IOException {
    final Properties data = new Properties();

    try (InputStream stream = DedicatedBedwars.class.getClassLoader().getResourceAsStream(CONFIG_RESOURCE_ID)) {
      if (!Files.exists(file)) {
        Files.copy(stream, file);
      }

      data.load(stream); // Load defaults first, then replace with contents of existing file.
    }
    try (BufferedReader reader = Files.newBufferedReader(file)) {
      data.load(reader);
    }

    return new Configuration(
        data.getProperty("ip"),
        Integer.parseInt(data.getProperty("port"))
    );
  }

  public Configuration getConfig() {
    return config;
  }
}
