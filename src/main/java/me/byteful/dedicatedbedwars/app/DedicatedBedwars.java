package me.byteful.dedicatedbedwars.app;

import me.byteful.dedicatedbedwars.config.ConfigurationHandler;
import me.byteful.dedicatedbedwars.module.ModuleManager;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static net.kyori.adventure.text.format.NamedTextColor.YELLOW;

public class DedicatedBedwars {
  private static DedicatedBedwars instance;
  private final Logger logger = LoggerFactory.getLogger("DedicatedBedwars");
  private final File baseDirectory = new File(".");
  private final ConfigurationHandler configurationHandler = new ConfigurationHandler(baseDirectory.toPath().resolve("server.properties"));
  private final ModuleManager moduleManager = new ModuleManager(logger);

  public static DedicatedBedwars getInstance() {
    return instance != null ? instance : (instance = new DedicatedBedwars());
  }

  public void start() {
    final long start = System.currentTimeMillis();
    logger.info("Starting DedicatedBedwars server on port 25565...");
    final MinecraftServer server = MinecraftServer.init();
    final InstanceContainer instance = MinecraftServer.getInstanceManager().createInstanceContainer();
    instance.setGenerator(gen -> gen.modifier().fillHeight(39, 40, Block.GRASS_BLOCK));
    MinecraftServer.getGlobalEventHandler().addListener(PlayerLoginEvent.class, e -> {
      e.setSpawningInstance(instance);
      e.getPlayer().setRespawnPoint(new Pos(0, 42, 0));
      logger.info(e.getPlayer().getName() + " has joined the server.");
      e.getPlayer().sendMessage(Component.text("Welcome!", YELLOW));
    });
    server.start("0.0.0.0", 25565);
    logger.info("Done! Took " + (System.currentTimeMillis() - start) + "ms.");
  }

  public void stop() {

  }

  public File getBaseDirectory() {
    return baseDirectory;
  }

  public ConfigurationHandler getConfigurationHandler() {
    return configurationHandler;
  }

  public ModuleManager getModuleManager() {
    return moduleManager;
  }
}
