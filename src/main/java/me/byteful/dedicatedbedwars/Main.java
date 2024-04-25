package me.byteful.dedicatedbedwars;

import me.byteful.dedicatedbedwars.app.DedicatedBedwars;

public class Main {
    public static void main(String[] args) {
        final DedicatedBedwars app = new DedicatedBedwars();
        app.start();
        Runtime.getRuntime().addShutdownHook(new Thread(app::stop));
    }
}
