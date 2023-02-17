package Services;

import Enums.*;
import Models.*;

import java.util.*;
import java.util.stream.*;

public class BotService {
    private GameObject bot;
    private PlayerAction playerAction;
    private GameState gameState;

    private boolean isTeleport = false;
    private boolean isMakanSuper = false;
    private boolean teleportAvailable = true;
    private boolean isTembakTorpedo = false;
    private Integer tik = 0;
    private Integer tikTeleport = 0;  
    

    public BotService() {
        this.playerAction = new PlayerAction();
        this.gameState = new GameState();
    }


    public GameObject getBot() {
        return this.bot;
    }

    public void setBot(GameObject bot) {
        this.bot = bot;
    }

    public PlayerAction getPlayerAction() {
        return this.playerAction;
    }
    
    public void setPlayerAction(PlayerAction playerAction) {
        this.playerAction = playerAction;
    }

    public void computeNextPlayerAction(PlayerAction playerAction) {
        playerAction.action = PlayerActions.FORWARD;
        if (!gameState.getGameObjects().isEmpty()) {
            var food = gameState.getGameObjects()
                .stream().filter(gameObject -> gameObject
                .getGameObjectType() == ObjectTypes.SUPERFOOD)
                .collect(Collectors.toList());
            var foodd = gameState.getGameObjects()
                .stream().filter(gameObject -> gameObject
                .getGameObjectType() == ObjectTypes.FOOD || gameObject.getGameObjectType() == ObjectTypes.SUPERFOOD)
                .collect(Collectors.toList());
            var obstacle = gameState.getGameObjects()
                .stream().filter(gameObject -> gameObject
                .getGameObjectType() == ObjectTypes.GASCLOUD || gameObject.getGameObjectType() == ObjectTypes.WORMHOLE)
                .collect(Collectors.toList());
            var enemy = gameState.getPlayerGameObjects()
                .stream().filter(gameObject -> gameObject
                .getGameObjectType() == ObjectTypes.PLAYER && gameObject.getId() != bot.getId())
                .collect(Collectors.toList());
            var nearestFood = food
                .stream().min(Comparator
                .comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
            var nearestFoodd = foodd
                .stream().min(Comparator
                .comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
            var nearestObstacle = obstacle
                .stream().min(Comparator
                .comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
            var nearestEnemy = enemy
                .stream().min(Comparator
                .comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
            var smallestEnemy = enemy
                .stream().min(Comparator
                .comparing(gameObject -> gameObject.getSize()-bot.getSize())).get();
            var listTorpedo = gameState.getGameObjects()
                .stream().filter(gameObject -> gameObject
                .getGameObjectType() == ObjectTypes.TORPEDOSALVO)
                .collect(Collectors.toList());

            // Cek ketersediaan teleport
            if(tikTeleport + 100 == gameState.getWorld().getCurrentTick()) {
                teleportAvailable = true;
            }

            // Kondisi ketika ukuran bot lebih kecil dari 100s
            if(bot.getSize() < 100){
                // Kondisi setelah makan Superfood
                if(isMakanSuper) {
                    var headingToFoodd = getHeadingBetween(nearestFoodd);
                    playerAction.heading = headingToFoodd;
                    System.out.println("OTEWEE FOOD");
                    if(tik+5 == gameState.getWorld().getCurrentTick()) {
                        System.out.println("FOOD");
                        isMakanSuper = false;
                    }
                }

                // Kondisi ketika ingin teleport
                else if(isTeleport){
                    // var teleporter = gameState.getGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.TELEPORTER).collect(Collectors.toList());
                    double jarak = getRealDistance(bot, nearestEnemy);
                    int velo = 20;
                    int timee = (int)jarak/velo;
                    if(tik+timee <= gameState.getWorld().getCurrentTick()){
                        playerAction.action = PlayerActions.TELEPORT;
                        System.out.println("TELEPORT");
                        isTeleport = false;
                    }
                }

                // Kondisi belum makan Superfood
                else{
                    var headingToFood = getHeadingBetween(nearestFood);
                    playerAction.heading = headingToFood;
                    System.out.println("OTEWEE SUPER FOOD");
                    // System.out.println("DISTANCE\t:" + getRealDistance(bot, nearestFood));
                    // System.out.println("MAKAN\t\t:" + nearestFood.getSize());
                    // System.out.println("BOT\t\t:" + bot.getSize());
                    if(getRealDistance(bot, nearestFood) < 10) {
                        System.out.println("SUPER FOOD");
                        isMakanSuper = true;
                        tik = gameState.getWorld().getCurrentTick();
                    }
                }

                // Kondisi menyerang tembak
                if (getRealDistance(bot, nearestEnemy) < 100 && bot.getSize() > 25 && nearestEnemy.getSize() > bot.getSize() && !isTeleport) {
                    // set heading to enemy
                    System.out.println("FIRE FIRE");
                    var headingToEnemy = getHeadingBetween(nearestEnemy);
                    playerAction.setHeading(headingToEnemy);
                    playerAction.action = PlayerActions.FIRETORPEDOES;
                }

                // Kondiisi menembak peluru teleportasi
                if(bot.size > 50 && teleportAvailable && (bot.getSize() - 30 > nearestEnemy.getSize()) && getRealDistance(bot, nearestEnemy) > 100 && !isTeleport) {
                    var headingToEnemy = getHeadingBetween(nearestEnemy);
                    playerAction.setHeading(headingToEnemy);
                    playerAction.action = PlayerActions.FIRETELEPORT;
                    tik = gameState.getWorld().getCurrentTick();
                    tikTeleport = gameState.getWorld().getCurrentTick();
                    System.out.println("TEMBAK TELEPORT");
                    System.out.println(tik);
                    isTeleport = true;
                    teleportAvailable = false;
                    isMakanSuper = false;
                }

                // Kondisi menghindari obstacle
                if (getRealDistance(bot, nearestObstacle) < 50) {
                    int i = 0;
                    while (getRealDistance(bot, foodd.get(i)) < 30) {
                        i++;
                    }
                    playerAction.heading = getHeadingBetween(foodd.get(i));
                }

                // Kondisi ketika world mengecil
                if(distanceFromWorldCenter(bot) + (3 * bot.getSize()) > gameState.getWorld().getRadius()) {
                    playerAction.heading = (int)getDirectionPosition(bot, gameState.getWorld().getCenterPoint());
                }

                // Kondisi aktivasi shield pelindung
                if (listTorpedo.size() > 0){
                    var nearestTorpedo = listTorpedo
                    .stream().min(Comparator
                    .comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
                    var headingToTorpedo = getHeadingBetween(nearestTorpedo);
                    int headingTorpedoToMe = nearestTorpedo.getCurrentHeading();
                    // System.out.println("HEADING TO TORPEDO\t:" + headingToTorpedo);
                    // System.out.println("HEADING TORPEDO TO ME\t:" + headingTorpedoToMe);
                    if (getRealDistance(bot, nearestTorpedo) < 50 && ((headingToTorpedo > (headingTorpedoToMe+10)%360 || headingToTorpedo < (headingTorpedoToMe-10)%360)) && bot.getSize() > 60){
                        System.out.println("HEADING TO TORPEDO\t:" + headingToTorpedo);
                        System.out.println("HEADING TORPEDO TO ME\t:" + headingTorpedoToMe);
                        playerAction.action = PlayerActions.ACTIVATESHIELD;
                        System.out.println("ACTIVATE SHIELD");   
                    }
                }
            }
            else{
                // Kondisi ketika ingin teleport
                if(isTeleport){
                    double jarak = getRealDistance(bot, nearestEnemy);
                    int velo = 20;
                    int timee = (int)jarak/velo;
                    if(tik+timee <= gameState.getWorld().getCurrentTick()){
                        playerAction.action = PlayerActions.TELEPORT;
                        System.out.println("TELEPORT");
                        isTeleport = false;
                    }
                }

                // Kondisi lainnya
                else{
                    // Kondisi ketika ingin menembak
                    if (getRealDistance(bot, nearestEnemy) < 250 && bot.getSize() > 25 && nearestEnemy.getSize() > bot.getSize()) {
                        // set heading to enemy
                        System.out.println("FIRE FIRE");
                        var headingToEnemy = getHeadingBetween(nearestEnemy);
                        playerAction.setHeading(headingToEnemy);
                        playerAction.action = PlayerActions.FIRETORPEDOES;
                    }

                    // Kondisi menembak peluru teleportasi
                    else if(teleportAvailable  && (bot.getSize() - 30 > nearestEnemy.getSize()) && getRealDistance(bot, nearestEnemy)>250) {
                        var headingToEnemy = getHeadingBetween(nearestEnemy);
                        playerAction.setHeading(headingToEnemy);
                        playerAction.action = PlayerActions.FIRETELEPORT;
                        tik = gameState.getWorld().getCurrentTick();
                        tikTeleport = gameState.getWorld().getCurrentTick();
                        System.out.println("TEMBAK TELEPORT");
                        System.out.println(tik);
                        isTeleport = true;
                        teleportAvailable = false;
                    }

                    else{
                        var headingToEnemy = getHeadingBetween(nearestEnemy);
                        playerAction.setHeading(headingToEnemy);
                        
                        // Kondisi aktivasi shield pelindung
                        if (listTorpedo.size() > 0){
                            var nearestTorpedo = listTorpedo
                            .stream().min(Comparator
                            .comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
                            int headingToTorpedo = getHeadingBetween(nearestTorpedo);
                            int headingTorpedoToMe = nearestTorpedo.getCurrentHeading();
                            if (getRealDistance(bot, nearestTorpedo) < 50 && (headingToTorpedo > (headingTorpedoToMe+10)%360 || headingToTorpedo < (headingTorpedoToMe-10)%360)){
                                System.out.println("HEADING TO TORPEDO\t:" + headingToTorpedo);
                                System.out.println("HEADING TORPEDO TO ME\t:" + headingTorpedoToMe);
                                playerAction.action = PlayerActions.ACTIVATESHIELD;
                                System.out.println("ACTIVATE SHIELD");
                            }
                        }
                        
                        // Puter balik mau nabrak edge
                        if(distanceFromWorldCenter(bot) + (3 * bot.getSize()) > gameState.getWorld().getRadius()) {
                            playerAction.heading = (int)getDirectionPosition(bot, gameState.getWorld().getCenterPoint());
                        }
                    }
                }
            }
        }
        this.playerAction = playerAction;
    }

    private int getDirectionPosition(GameObject bot, Position position) {
        var cartesianDegrees = toDegrees(Math.atan2(position.y - bot.getPosition().y, position.x - bot.getPosition().x));
        return cartesianDegrees = (cartesianDegrees + 360) % 360;
    }

    private double distanceFromWorldCenter(GameObject object) {
        var triangleX = Math.abs(object.getPosition().x - 0);
        var triangleY = Math.abs(object.getPosition().y - 0);
        return Math.sqrt(triangleX * triangleX + triangleY * triangleY);
    }
    

    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        updateSelfState();
    }

    private void updateSelfState() {
        Optional<GameObject> optionalBot = gameState.getPlayerGameObjects().stream().filter(gameObject -> gameObject.id.equals(bot.id)).findAny();
        optionalBot.ifPresent(bot -> this.bot = bot);
    }

    private double getDistanceBetween(GameObject object1, GameObject object2) {
        var triangleX = Math.abs(object1.getPosition().x - object2.getPosition().x);
        var triangleY = Math.abs(object1.getPosition().y - object2.getPosition().y);
        return Math.sqrt(triangleX * triangleX + triangleY * triangleY);
    }

    private int getHeadingBetween(GameObject otherObject) {
        var direction = toDegrees(Math.atan2(otherObject.getPosition().y - bot.getPosition().y,
                otherObject.getPosition().x - bot.getPosition().x));
        return (direction + 360) % 360;
    }

    private int toDegrees(double v) {
        return (int) (v * (180 / Math.PI));
    }

    private double getRealDistance(GameObject object1, GameObject object2){
        double bot = object1.getSize();
        double enemy = object2.getSize();
        return getDistanceBetween(object1, object2) - bot - enemy;

    }
}