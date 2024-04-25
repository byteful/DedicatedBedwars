package me.byteful.dedicatedbedwars.config;

public class Configuration {
  private final String ip;
  private final int port;

  public Configuration(String ip, int port) {
    this.ip = ip;
    this.port = port;
  }

  public String getIp() {
    return ip;
  }

  public int getPort() {
    return port;
  }
}
