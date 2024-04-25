package me.byteful.dedicatedbedwars.module;

import me.byteful.dedicatedbedwars.api.module.Module;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModuleManager {
  private final List<Module> modules = new ArrayList<>();
  private final Logger logger;

  public ModuleManager(Logger logger) {
    this.logger = logger;
  }

  private ModuleData getData(InputStream stream) throws IOException {
    final Properties prop = new Properties();
    prop.load(stream);

    return new ModuleData(prop.getProperty("id"), prop.getProperty("main"));
  }

  public void loadFromDirectory(File directory) {
    for (File file : directory.listFiles((dir, name) -> name.endsWith(".jar"))) {
      InputStream stream = null;
      JarFile jar = null;

      try {
        final URLClassLoader loader = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()});
        jar = new JarFile(file);
        final JarEntry propertiesFile = jar.getJarEntry("module.properties");
        if (propertiesFile == null) {
          logger.warn("Failed to find module.properties in '" + file.getName() + "'.");
          continue;
        }

        stream = jar.getInputStream(propertiesFile);
        final ModuleData data = getData(stream);
        final Class<?> mainClass = Class.forName(data.getMainClass(), true, loader);
        final Class<? extends Module> moduleClass;
        try {
          moduleClass = mainClass.asSubclass(Module.class);
        } catch (ClassCastException e) {
          logger.warn("Module main class is not of Module type!", e);
          continue;
        }

        modules.add(moduleClass.getDeclaredConstructor().newInstance());
      } catch (IOException | ClassNotFoundException | NoSuchMethodException | InstantiationException |
               IllegalAccessException | InvocationTargetException e) {
        logger.warn("Failed to load module: " + file.getName(), e);
      } finally {
        if (jar != null) {
          try {
            jar.close();
          } catch (IOException ignored) {
          }
        }
        if (stream != null) {
          try {
            stream.close();
          } catch (IOException ignored) {
          }
        }
      }
    }
  }
}
