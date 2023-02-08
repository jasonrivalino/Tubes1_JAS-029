package Models;

import Enums.*;
import java.util.*;

public class GameObject {
  public UUID id; // id dari objek
  public Integer size; // ukuran dari objek
  public Integer speed; // kecepatan dari objek
  public Integer currentHeading; // merupakan sudut dari arah yang dituju
  public Position position; // posisi dari objek
  public ObjectTypes gameObjectType; // tipe dari objek
  public Integer torpedoSalvoCount; // jumlah torpedo yang akan dilempar
  public Boolean supernovaAvailable; // apakah supernova sudah siap digunakan

  public GameObject(UUID id, Integer size, Integer speed, Integer currentHeading, Position position, ObjectTypes gameObjectType, Integer torpedoSalvoCount, Boolean supernovaAvailable) {
    this.id = id; // id dari objek
    this.size = size; // ukuran dari objek
    this.speed = speed; // kecepatan dari objek
    this.currentHeading = currentHeading; // merupakan sudut dari arah yang dituju
    this.position = position; // posisi dari objek
    this.gameObjectType = gameObjectType; // tipe dari objek
    this.torpedoSalvoCount = torpedoSalvoCount; // jumlah torpedo yang akan dilempar
    this.supernovaAvailable = supernovaAvailable; // apakah supernova sudah siap digunakan
  }

  public UUID getId() { // getter id
    return id;
  }

  public void setId(UUID id) { // setter id
    this.id = id;
  }

  public int getSize() { // getter size
    return size;
  }

  public void setSize(int size) { // setter size
    this.size = size;
  }

  public int getSpeed() { // getter speed
    return speed;
  }

  public void setSpeed(int speed) { // setter speed
    this.speed = speed;
  }

  public int getCurrentHeading() { // getter currentHeading
    return currentHeading;
  }

  public void setCurrentHeading(int currentHeading) { // setter currentHeading
    this.currentHeading = currentHeading;
  }

  public Position getPosition() { // getter position
    return position;
  }

  public void setPosition(Position position) { // setter position
    this.position = position;
  }

  public ObjectTypes getGameObjectType() { // getter gameObjectType
    return gameObjectType;
  }

  public void setGameObjectType(ObjectTypes gameObjectType) { // setter gameObjectType
    this.gameObjectType = gameObjectType;
  }

  public int getTorpedoSalvoCount() { // getter torpedoSalvoCount
    return this.torpedoSalvoCount;
  }

  public boolean getSupernovaAvailable() { // getter supernovaAvailable
    return this.supernovaAvailable;
  }

  public void setSupernovaAvailable(boolean supernovaAvailable) { // setter supernovaAvailable
    this.supernovaAvailable = supernovaAvailable;
  }

  public static GameObject FromStateList(UUID id, List<Integer> stateList)
  {
    Position position = new Position(stateList.get(4), stateList.get(5)); // posisi dari objek
    return new GameObject(id, stateList.get(0), stateList.get(1), stateList.get(2), position, ObjectTypes.valueOf(stateList.get(3)), stateList.get(6), false); 
  }
}
