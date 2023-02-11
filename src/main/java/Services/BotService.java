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
    private Integer tik;
    private Integer tikSupernova;   
    

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
        // greedy algorithm entelect challenge galaxio 2021
        // player action move forward
        playerAction.action = PlayerActions.FORWARD;
        // move to list food : FOOD, SUPERFOOD
        if (!gameState.getGameObjects().isEmpty()) {
            // Greedy by maximalize size of bot
            // get list food
            var food = gameState.getGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.SUPERFOOD).collect(Collectors.toList());
            var foodd = gameState.getGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.FOOD).collect(Collectors.toList());
            // get nearest food
            var nearestFood = food.stream().min(Comparator.comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
            // System.out.println("Super Food Berhasil");
            var nearestFoodd = foodd.stream().min(Comparator.comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
            // System.out.println("Food Berhasil");
            // get nearest obstacle
            var obstacle = gameState.getGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.GASCLOUD || gameObject.getGameObjectType() == ObjectTypes.ASTEROIDFIELD).collect(Collectors.toList());
            var nearestObstacle = obstacle.stream().min(Comparator.comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
            // System.out.println("Obstacle Berhasil");
            // get enemy
            // get nearest enemy
            
            var enemy = gameState.getPlayerGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.PLAYER && gameObject.getId() != bot.getId()).collect(Collectors.toList());
            var nearestEnemy = enemy.stream().min(Comparator.comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
            var smallestEnemy = enemy.stream().min(Comparator.comparing(gameObject -> gameObject.getSize()-bot.getSize())).get();
            // System.out.println("Enemy Berhasil");
            
            
            
            // get real distance between player and enemy
            // var distanceEnemy = getDistanceBetween(nearestEnemy, bot) - nearestEnemy.getSize()/2 - bot.getSize()/2;
            
            // var torpedo = gameState.getGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.TORPEDOSALVO).collect(Collectors.toList());
            // var nearestTorpedo = torpedo.stream().min(Comparator.comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
            // System.out.println("Torpedo Berhasil");
            
            // System.out.println("Torpedo Gagal");
            
            


            var radiusWorld = gameState.getWorld().getRadius();
            // eat SUPERFOOD and FOOD and avoid obstacle and enemy
            if(true){
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
                    if(tik+timee == gameState.getWorld().getCurrentTick()){
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
                    // if(bot.getSize() < 50){
                    // }else if(bot.getSize() < 100){
                    //     if(getRealDistance(bot, nearestFood) < ) {
                    //         System.out.println("SUPER FOOD");
                    //         isMakanSuper = true;
                    //         tik = gameState.getWorld().getCurrentTick();
                    //     }
                    // }
                }
                // && gameState.getWorld().getCurrentTick() > 100
                if(bot.size>50 && teleportAvailable ) {
                    playerAction.heading = getHeadingBetween(nearestEnemy);
                    playerAction.action = PlayerActions.FIRETELEPORT;
                    tik = gameState.getWorld().getCurrentTick();
                    System.out.println("TEMBAK TELEPORT");
                    System.out.println(tik);
                    isTeleport = true;
                    teleportAvailable = false;
                }
            }
            else{
                // if (getRealDistance(bot, nearestEnemy) < 250 && bot.getSize() > 25) {
                //         // set heading to enemy
                //         System.out.println("FIRE FIRE");
                //         var headingToEnemy = getHeadingBetween(nearestEnemy);
                //         playerAction.setHeading(headingToEnemy);
                //         playerAction.action = PlayerActions.FIRETORPEDOES;
                //     }
            }
            

            

            // playerAction.heading = getHeadingBetween(nearestObstacle);

            

        //     // greedy by attack enemy use FIRETORPEDOES
        //     // attack enemy if distance between bot and enemy is less than 250
            // if (getRealDistance(bot, nearestEnemy) < 250 && bot.getSize() > 25) {
            //     // set heading to enemy
            //     System.out.println("FIRE FIRE");
            //     var headingToEnemy = getHeadingBetween(nearestEnemy);
            //     playerAction.setHeading(headingToEnemy);
            //     playerAction.action = PlayerActions.FIRETORPEDOES;
            //     this.playerAction = playerAction;
            //     // teleport to enemy if size of enemy is less than size of bot
            //     // if the enemy is smaller than the bot, fire teleporter
            // }

            
            // System.out.println("ENEMY 1 :");
            // System.out.println("SIZE\t:" + enemy.get(0).getSize());
            // System.out.println("SALVO\t:" + enemy.get(0).getTorpedoSalvoCount());
            // System.out.println("ENEMY 2 :");
            // System.out.println("SIZE\t:" + enemy.get(1).getSize());
            // System.out.println("SALVO\t:" + enemy.get(1).getTorpedoSalvoCount());
            // System.out.println("BOT :");
            // System.out.println("SIZE\t:" + bot.getSize());
            // System.out.println("SALVO\t:" + bot.getTorpedoSalvoCount());

            // if(enemy.get(0).torpedoSalvoCount == 0 || enemy.get(1).torpedoSalvoCount == 0){
            //     System.out.println("SHILEDDDDDDDD");
            //     playerAction.action = PlayerActions.ACTIVATESHIELD;
            // }

            // if (getRealDistance(nearestEnemy, bot) < 400 &&  nearestEnemy.getSize() < bot.getSize()-25) {
            //     playerAction.action = PlayerActions.FIRETELEPORTER;
            //     var teleporter = gameState.getGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.TELEPORTER).collect(Collectors.toList());
            //     if (getRealDistance(teleporter.get(0), enemy.get(0)) <= 30) {
            //         playerAction.action = PlayerActions.TELEPORT;
            //     }
            // }
        }
        this.playerAction = playerAction;
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

// playerAction.action = PlayerActions.FORWARD;
// int heading = playerAction.getHeading();
// // playerAction.heading = new Random().nextInt(360);
// if (!gameState.getGameObjects().isEmpty()) {
//     var superFoodList = gameState.getGameObjects()
//     .stream().filter(item -> item.getGameObjectType() == ObjectTypes.SUPERFOOD)
//     .sorted(Comparator
//     .comparing(item -> getDistanceBetween(bot, item)))
//     .collect(Collectors.toList());

//     var obstacleList = gameState.getGameObjects()
//     .stream().filter(musuh -> musuh.getGameObjectType() == ObjectTypes.GASCLOUD ||
//     musuh.getGameObjectType() == ObjectTypes.ASTEROIDFIELD ||
//     musuh.getGameObjectType() == ObjectTypes.TELEPORTER)
//     .sorted(Comparator
//     .comparing(musuh -> getDistanceBetween(bot, musuh)))
//     .collect(Collectors.toList());

//     var enemyList = gameState.getGameObjects()
//     .stream().filter(enemy -> (enemy.getId() != bot.getId()) && (enemy.getGameObjectType() == ObjectTypes.PLAYER))
//     .sorted(Comparator
//     .comparing(enemy -> getDistanceBetween(bot, enemy)))
//     .collect(Collectors.toList());
    
//     // if(getDistanceBetween(bot, superFoodList.get(0)) <= 300){
//         // playerAction.heading = getHeadingBetween(superFoodList.get(0));
//         if(getDistanceBetween(bot, enemyList.get(0)) < 600){
//             int musuh = enemyList.get(0).getSize();
//             int aku = bot.getSize();
//             // if(musuh<aku){
//                 playerAction.heading = getHeadingBetween(enemyList.get(0));
//             // }
//         }
//     // }

// }