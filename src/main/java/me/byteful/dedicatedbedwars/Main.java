package me.byteful.dedicatedbedwars;

import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.kyori.adventure.text.format.NamedTextColor.YELLOW;

public class Main {
    public static void main(String[] args) {
        final long start = System.currentTimeMillis();
        final Logger logger = LoggerFactory.getLogger("DedicatedBedwars");
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
}
