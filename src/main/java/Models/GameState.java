package Models;

import java.util.*;

public class GameState { // kelas untuk menyimpan state dari game

    public World world; // menyimpan state dari world
    public List<GameObject> gameObjects; // menyimpan state dari game object
    public List<GameObject> playerGameObjects; // menyimpan state dari player game object

    public GameState() { // constructor
        world = new World(); // inisialisasi world
        gameObjects = new ArrayList<GameObject>(); // inisialisasi game object
        playerGameObjects = new ArrayList<GameObject>(); // inisialisasi player game object
    }

    public GameState(World world , List<GameObject> gameObjects, List<GameObject> playerGameObjects) { // constructor
        this.world = world;
        this.gameObjects = gameObjects;
        this.playerGameObjects = playerGameObjects;
    }

    public World getWorld() { // getter world
        return world;
    }

    public void setWorld(World world) { // setter world
        this.world = world;
    }

    public List<GameObject> getGameObjects() { // getter game object
        return gameObjects;
    }

    public void setGameObjects(List<GameObject> gameObjects) { // setter game object
        this.gameObjects = gameObjects;
    }

    public List<GameObject> getPlayerGameObjects() { // getter player game object
        return playerGameObjects;
    }

    public void setPlayerGameObjects(List<GameObject> playerGameObjects) { // setter player game object
        this.playerGameObjects = playerGameObjects;
    }

}
