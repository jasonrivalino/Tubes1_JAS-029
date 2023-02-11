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
            var food = gameState.getGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.SUPERFOOD).collect(Collectors.toList());
            var foodd = gameState.getGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.FOOD).collect(Collectors.toList());
            var obstacle = gameState.getGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.GASCLOUD || gameObject.getGameObjectType() == ObjectTypes.ASTEROIDFIELD).collect(Collectors.toList());
            var enemy = gameState.getPlayerGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.PLAYER && gameObject.getId() != bot.getId()).collect(Collectors.toList());
            var nearestFood = food.stream().min(Comparator.comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
            var nearestFoodd = foodd.stream().min(Comparator.comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
            var nearestObstacle = obstacle.stream().min(Comparator.comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
            var nearestEnemy = enemy.stream().min(Comparator.comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
            var smallestEnemy = enemy.stream().min(Comparator.comparing(gameObject -> gameObject.getSize()-bot.getSize())).get();
            // var torpedo = gameState.getGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.TORPEDOSALVO).collect(Collectors.toList());
            // var nearestTorpedo = torpedo.stream().min(Comparator.comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();

            var radiusWorld = gameState.getWorld().getRadius();
            // eat SUPERFOOD and FOOD and avoid obstacle and enemy

            if(tikTeleport+100 == gameState.getWorld().getCurrentTick()) {
                teleportAvailable = true;
            }

            if(bot.getSize()<100){
                if(isMakanSuper) {
                    var headingToFoodd = getHeadingBetween(nearestFoodd);
                    playerAction.heading = headingToFoodd;
                    System.out.println("OTEWEE FOOD");
                    if(tik+5 == gameState.getWorld().getCurrentTick()) {
                        System.out.println("FOOD");
                        isMakanSuper = false;
                    }
                }
                else if(isTeleport){
                    // var teleporter = gameState.getGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.TELEPORTER).collect(Collectors.toList());
                    double jarak = getRealDistance(bot, nearestEnemy);
                    double velo = 20;
                    int timee = (int)jarak/(int)velo;
                    if(tik+timee <= gameState.getWorld().getCurrentTick()){
                        playerAction.action = PlayerActions.TELEPORT;
                        System.out.println("TELEPORT");
                        isTeleport = false;
                    }
                }
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

                if (getRealDistance(bot, nearestEnemy) < 100 && bot.getSize() > 25 && nearestEnemy.getSize() > bot.getSize() && !isTeleport) {
                    // set heading to enemy
                    System.out.println("FIRE FIRE");
                    var headingToEnemy = getHeadingBetween(nearestEnemy);
                    playerAction.setHeading(headingToEnemy);
                    playerAction.action = PlayerActions.FIRETORPEDOES;
                }

                if(bot.size>50 && teleportAvailable && (bot.getSize()-20 > nearestEnemy.getSize()) && getRealDistance(bot, nearestEnemy) > 100 && !isTeleport) {
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
            }
            else{
                
                if(isTeleport){
                    double jarak = getRealDistance(bot, nearestEnemy);
                    double velo = 20;
                    int timee = (int)jarak/(int)velo;
                    if(tik+timee <= gameState.getWorld().getCurrentTick()){
                        playerAction.action = PlayerActions.TELEPORT;
                        System.out.println("TELEPORT");
                        isTeleport = false;
                    }
                }
                else{
                    if (getRealDistance(bot, nearestEnemy) < 250 && bot.getSize() > 25 && nearestEnemy.getSize() > bot.getSize()) {
                        // set heading to enemy
                        System.out.println("FIRE FIRE");
                        var headingToEnemy = getHeadingBetween(nearestEnemy);
                        playerAction.setHeading(headingToEnemy);
                        playerAction.action = PlayerActions.FIRETORPEDOES;
                    }else if(teleportAvailable  && (bot.getSize()-20 > nearestEnemy.getSize()) && getRealDistance(bot, nearestEnemy)>250) {
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
                    }
                }
            }
        }
        this.playerAction = playerAction;
    } 

    private int  GetAttackerResolution(GameObject bot, GameObject attacker, GameObject closestFood){
        if (closestFood == null){
            return GetOppositeDirection(bot,attacker);
        }
        var distanceToAttacker = getDistanceBetween(attacker);
        var distanceBetweenAttackerAndFood = getDistanceBetween(attacker,closestFood);

        if (distanceToAttacker > attacker.speed && distanceBetweenAttackerAndFood > distanceToAttacker){
            System.out.println("Atk is far, going for food");
            return GetDirection(this.bot, closestFood);
        }
        else{
            System.out.println("Atk is close, running");
            return GetOppositeDirection(bot,attacker);
        }
    }

    private int GetOppositeDirection(GameObject object1, GameObject object2) {
        return toDegrees(Math.atan2(object2.getPosition().y - object1.getPosition().y, object2.getPosition().x - object1.getPosition().x));
    }

    private int GetDirection(GameObject bot, GameObject object) {
        var cartesianDegrees = toDegrees(Math.atan2(object.getPosition().y - bot.getPosition().y, object.getPosition().x - bot.getPosition().x));
        return cartesianDegrees = (cartesianDegrees + 360) % 360;
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

    private double getDistanceBetween(GameObject object){
        return  getDistanceBetween(this.bot,object);
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