package Services;

import Enums.*;
import Models.*;

import java.util.*;
import java.util.stream.*;

public class BotService {
    private GameObject bot;
    private PlayerAction playerAction;
    private GameState gameState;

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
            // // bot is my bot    
            // // var detect enemy using getPlayerGameObject
            // // ID PLAYER != ID bot
            // var enemy = gameState.getPlayerGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.PLAYER && gameObject.getId() != bot.getId()).collect(Collectors.toList());
            // // get nearest enemy
            // var nearestEnemy = enemy.stream().min(Comparator.comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
            // // get distance between bot and enemy
            // var distanceEnemy = getDistanceBetween(nearestEnemy, bot);
            // // get size of bot
            // var sizeBot = bot.getSize();
            // // get size of enemy
            // var sizeEnemy = nearestEnemy.getSize();
            // // get heading between bot and enemy if size of bot is bigger than nearest enemy and distance between bot and enemy is less than 300
            // // var food
            // var food = gameState.getGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.SUPERFOOD).collect(Collectors.toList());
            // var foodd = gameState.getGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.FOOD).collect(Collectors.toList());
            // // get nearest food
            // var nearestFood = food.stream().min(Comparator.comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
            // var nearestFoodd = foodd.stream().min(Comparator.comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();

            // // get nearest GASCLOUD
            // var gasCloud = gameState.getGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.GASCLOUD).collect(Collectors.toList());
            // var nearestGasCloud = gasCloud.stream().min(Comparator.comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();

            // if (sizeBot > sizeEnemy && nearestEnemy != null && distanceEnemy < 250) {
            //     var headingToEnemy = getHeadingBetween(nearestEnemy);
            //     playerAction.heading = headingToEnemy;
            // }
            // // size of bot is less than nearest enemy and distance between bot and enemy is less than 250 then avoid enemy
            // else if ((sizeBot < sizeEnemy) && (nearestEnemy != null) && (distanceEnemy < 180)) {
            //     var headingToEnemy = getHeadingBetween(nearestEnemy);
            //     // setHeading() avoid enemy
            //     playerAction.setHeading(headingToEnemy + 180);
            //     // if in range of GASCLOUD in radius 150 then avoid GASCLOUD
            //     if (getDistanceBetween(bot, nearestGasCloud) < 150) {
            //         var headingToGasCloud = getHeadingBetween(nearestGasCloud);
            //         playerAction.setHeading(headingToGasCloud + 40);
            //     }
            // }
            // else {
            //     // if in range of SUPERFOOD in radius 200 then eat SUPERFOOD, else eat FOOD
            //     if (getDistanceBetween(bot, nearestFood) < 200) {
            //         var headingToFood = getHeadingBetween(nearestFood);
            //         playerAction.heading = headingToFood;
            //         if (getDistanceBetween(bot, nearestGasCloud) < 50) {
            //             var headingToGasCloud = getHeadingBetween(nearestGasCloud);
            //             playerAction.setHeading(headingToGasCloud + 45);
            //         }
            //     }
            //     // selama tidak ada SUPERFOOD maka makan FOOD
            //     else {
            //         var headingToFoodd = getHeadingBetween(nearestFoodd);
            //         playerAction.heading = headingToFoodd;
            //         if (getDistanceBetween(bot, nearestGasCloud) < 50) {
            //             var headingToGasCloud = getHeadingBetween(nearestGasCloud);
            //             playerAction.setHeading(headingToGasCloud + 45);
            //         }
            //     }
            // }
            // Greedy by maximalize size of bot
            // get list food
            var food = gameState.getGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.SUPERFOOD).collect(Collectors.toList());
            var foodd = gameState.getGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.FOOD).collect(Collectors.toList());
            // get nearest food
            var nearestFood = food.stream().min(Comparator.comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
            var nearestFoodd = foodd.stream().min(Comparator.comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
            // get nearest obstacle
            var obstacle = gameState.getGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.GASCLOUD || gameObject.getGameObjectType() == ObjectTypes.ASTEROIDFIELD).collect(Collectors.toList());
            var nearestObstacle = obstacle.stream().min(Comparator.comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
            // get enemy
            var enemy = gameState.getPlayerGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.PLAYER && gameObject.getId() != bot.getId()).collect(Collectors.toList());
            // get nearest enemy
            var nearestEnemy = enemy.stream().min(Comparator.comparing(gameObject -> getDistanceBetween(gameObject, bot))).get();
            // get real distance between player and enemy
            // var distanceEnemy = getDistanceBetween(nearestEnemy, bot) - nearestEnemy.getSize()/2 - bot.getSize()/2;

            var radiusWorld = gameState.getWorld().getRadius();
            // eat SUPERFOOD and FOOD and avoid obstacle and enemy
            if (getDistanceBetween(bot, nearestFood) < 300) {
                var headingToFood = getHeadingBetween(nearestFood);
                playerAction.heading = headingToFood;
                if (getRealDistance(bot, nearestObstacle) < 50) {
                    var headingToObstacle = getHeadingBetween(nearestObstacle);
                    playerAction.setHeading(headingToObstacle + 90);
                }
                if (getRealDistance(bot, nearestEnemy) < 50) {
                    var headingToEnemy = getHeadingBetween(nearestEnemy);
                    playerAction.setHeading(headingToEnemy + 180);
                }
            }
            else {
                var headingToFoodd = getHeadingBetween(nearestFoodd);
                playerAction.heading = headingToFoodd;
                if (getDistanceBetween(bot, nearestObstacle) < 50) {
                    var headingToObstacle = getHeadingBetween(nearestObstacle);
                    playerAction.setHeading(headingToObstacle + 45);
                }
                if (getRealDistance(bot, nearestEnemy) < 50) {
                    var headingToEnemy = getHeadingBetween(nearestEnemy);
                    playerAction.setHeading(headingToEnemy + 45);
                }
            }
            // greedy by attack enemy use FIRETORPEDOES
            // attack enemy if distance between bot and enemy is less than 250
            if (getRealDistance(bot, nearestEnemy) < 500 && bot.getSize() > 25) {
                // set heading to enemy
                var headingToEnemy = getHeadingBetween(nearestEnemy);
                playerAction.setHeading(headingToEnemy);
                playerAction.action = PlayerActions.FIRETORPEDOES;
                // teleport to enemy if size of enemy is less than size of bot
                // if the enemy is smaller than the bot, fire teleporter
                if (getRealDistance(nearestEnemy, bot) < 400 &&  nearestEnemy.getSize() < bot.getSize()-25) {
                    playerAction.action = PlayerActions.FIRETELEPORTER;
                    var teleporter = gameState.getGameObjects().stream().filter(gameObject -> gameObject.getGameObjectType() == ObjectTypes.TELEPORTER).collect(Collectors.toList());
                    if (getRealDistance(teleporter.get(0), enemy.get(0)) <= 30) {
                        playerAction.action = PlayerActions.TELEPORT;
                    }
                }
            }
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
        double bot = object1.getSize()/2;
        double enemy = object2.getSize()/2;
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