package me.byteful.dedicatedbedwars.module;

public class ModuleData {
  private final String id;
  private final String mainClass;

  public ModuleData(String id, String mainClass) {
    this.id = id;
    this.mainClass = mainClass;
  }

  public String getId() {
    return id;
  }

  public String getMainClass() {
    return mainClass;
  }
}
