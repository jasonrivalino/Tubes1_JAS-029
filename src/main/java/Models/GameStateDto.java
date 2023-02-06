package Models;

import java.util.*;

public class GameStateDto { // kelas untuk menyimpan state dari game

  private World world; // menyimpan state dari world
  private Map<String, List<Integer>> gameObjects; // menyimpan state dari game object
  private Map<String, List<Integer>> playerObjects; // menyimpan state dari player game object

  public Models.World getWorld() { // getter world
    return world;
  }

  public void setWorld(Models.World world) { // setter world
    this.world = world;
  }

  public Map<String, List<Integer>> getGameObjects() { // getter game object
    return gameObjects;
  }

  public void setGameObjects(Map<String, List<Integer>> gameObjects) { // setter game object
    this.gameObjects = gameObjects;
  }

  public Map<String, List<Integer>> getPlayerObjects() { // getter player game object
    return playerObjects;
  }

  public void setPlayerObjects(Map<String, List<Integer>> playerObjects) { // setter player game object
    this.playerObjects = playerObjects;
  }
}
