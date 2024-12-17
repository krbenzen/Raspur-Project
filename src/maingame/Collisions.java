/**
 * Lead Author(s):
 *   - Benzen Raspur
 *
 * Other contributors:
 *   - None
 *
 * References:
 *   - Morelli, R., & Walde, R. (2016). Java, Java, Java: Object-Oriented Problem Solving.
 *     https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *   - Bechtold, S., Brannen, S., Link, J., Merdes, M., Philipp, M., Rancourt, J. D., & Stein, C. (n.d.).
 *     JUnit 5 user guide. JUnit 5.
 *     https://junit.org/junit5/docs/current/user-guide/
 *
 * Version/Date: 12/16/2024
 *
 * Description:
 * The Collisions class is responsible for detecting and handling collision logic 
 * between entities, objects, tiles, and the player
 * - Checks tile collisions to prevent entities from moving into blocked areas.
 * - Checks object collisions, allowing the player to pick up or interact with items.
 * - Checks entity collisions 
 * - Checks collisions with the player from the perspective of other entities
 *
 * ISA: Collisions is a helper class that checks for collisions in the game.
 * HAS-A: Collisions has-a reference to GamePanel, giving it access to tiles, objects, and entities.
 *
 * Learning Outcomes (LOs):
 * LO1. Employ design principles of OOP:
 *    - Separation of collision logic from other game logic
 * LO2. Arrays:
 *    - Demonstrates iteration over arrays like objects and npcs
 * LO3. Objects and Classes:
 *    - Interacts with Entity, objects, and GamePanel
 * LO4. Inheritance and Polymorphism:
 *    - Uses polymorphism when checking object types instance of.
 */

package maingame;

import entity.Entity;
import object.PaperObject;
import object.PencilObject;

public class Collisions {
   // Reference to the main GamePanel, providing game context. 
    private GamePanel gamePanel;

    /**
     * Constructs a Collisions object
     *
     * @param gamePanel The GamePanel instance that contains tiles, objects, and entities
     */
    public Collisions(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Checks tile collisions for a given entity based on direction and speed
     * If a tile is marked as collision = true the entity cannot move onto it
     *
     * @param entity The entity whose movement is being checked
     * @return void - Updates entity.collisionOn directly if a collision is checked
     */
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidAreaDefaultX;
        int entityRightWorldX = entity.worldX + entity.solidAreaDefaultX + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidAreaDefaultY;
        int entityBottomWorldY = entity.worldY + entity.solidAreaDefaultY + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        int tileNum1, tileNum2;

        // Check based on direction
        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gamePanel.tileM.tile[tileNum1].collision || gamePanel.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;

            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gamePanel.tileM.tile[tileNum1].collision || gamePanel.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;

            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gamePanel.tileM.tile[tileNum1].collision || gamePanel.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;

            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gamePanel.tileM.tile[tileNum1].collision || gamePanel.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    /**
     * Checks for collisions between an entity and objects on the map
     * If the entity is the player, it can pick up pencils and remove paper if it has a pencil
     *
     * @param entity The entity being checked for object collisions
     * @param player A boolean indicating if this entity is the player true or not false
     * @return int The index of the object collided with, or 999 if no collision occurred or no pickup
     */
    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gamePanel.obj.length; i++) {
            if (gamePanel.obj[i] == null) {
                continue; // Skip null objects
            }

            // Save original positions
            int originalEntityX = entity.solidArea.x;
            int originalEntityY = entity.solidArea.y;
            int originalObjX = gamePanel.obj[i].solidArea.x;
            int originalObjY = gamePanel.obj[i].solidArea.y;

            // Update positions for collision detection
            entity.solidArea.x = entity.worldX + entity.solidAreaDefaultX;
            entity.solidArea.y = entity.worldY + entity.solidAreaDefaultY;
            gamePanel.obj[i].solidArea.x = gamePanel.obj[i].worldX + gamePanel.obj[i].solidAreaDefaultX;
            gamePanel.obj[i].solidArea.y = gamePanel.obj[i].worldY + gamePanel.obj[i].solidAreaDefaultY;

            // Shift entity hitbox based on movement direction
            switch (entity.direction) {
                case "up":    entity.solidArea.y -= entity.speed; break;
                case "down":  entity.solidArea.y += entity.speed; break;
                case "left":  entity.solidArea.x -= entity.speed; break;
                case "right": entity.solidArea.x += entity.speed; break;
            }

            // Check if entity hit with the object's hitbox
            if (entity.solidArea.intersects(gamePanel.obj[i].solidArea)) {
                if (gamePanel.obj[i].collision) {
                    entity.collisionOn = true;
                }

                if (player) {
                    // Player-specific object interactions
                    if (gamePanel.obj[i] instanceof PencilObject) {
                        // Increase pencil count and remove the object
                        gamePanel.player.pencilCount++;

                        // Reset positions before removing the object
                        entity.solidArea.x = originalEntityX;
                        entity.solidArea.y = originalEntityY;
                        gamePanel.obj[i].solidArea.x = originalObjX;
                        gamePanel.obj[i].solidArea.y = originalObjY;

                        gamePanel.obj[i] = null;
                        gamePanel.playSoundEffect(3);
                        break; // stop checking further since we removed the object
                    } else if (gamePanel.obj[i] instanceof PaperObject) {
                        if (gamePanel.player.pencilCount > 0) {
                            // Use one pencil and remove the paper
                            gamePanel.player.pencilCount--;

                            // Reset positions before removing the object
                            entity.solidArea.x = originalEntityX;
                            entity.solidArea.y = originalEntityY;
                            gamePanel.obj[i].solidArea.x = originalObjX;
                            gamePanel.obj[i].solidArea.y = originalObjY;

                            gamePanel.obj[i] = null;
                            gamePanel.playSoundEffect(3);
                            break; // stop checking further since we removed the object
                        } else {
                            gamePanel.ui.showMessage("You need a pencil to remove this paper!");
                            // Just show message, do not remove object
                            // Reset positions after showing message
                            entity.solidArea.x = originalEntityX;
                            entity.solidArea.y = originalEntityY;
                            gamePanel.obj[i].solidArea.x = originalObjX;
                            gamePanel.obj[i].solidArea.y = originalObjY;
                        }
                    } else {
                        index = i; // For other objects (if any) return index
                        // Reset positions
                        entity.solidArea.x = originalEntityX;
                        entity.solidArea.y = originalEntityY;
                        gamePanel.obj[i].solidArea.x = originalObjX;
                        gamePanel.obj[i].solidArea.y = originalObjY;
                    }
                } else {
                    // Not the player, just reset positions
                    entity.solidArea.x = originalEntityX;
                    entity.solidArea.y = originalEntityY;
                    gamePanel.obj[i].solidArea.x = originalObjX;
                    gamePanel.obj[i].solidArea.y = originalObjY;
                }
            } else {
                // No collision for this object, reset positions
                entity.solidArea.x = originalEntityX;
                entity.solidArea.y = originalEntityY;
                gamePanel.obj[i].solidArea.x = originalObjX;
                gamePanel.obj[i].solidArea.y = originalObjY;
            }
        }

        return index;
    }

    /**
     * Checks collisions between an entity and an array of target entities.
     * If a collision is detected, returns the index entity collided with.
     *
     * @param entity The entity checking for collisions.
     * @param target The array of target entities to check against.
     * @return int The index of the colliding target entity, or 999 if no collision.
     */
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;
        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                int originalEntityX = entity.solidArea.x;
                int originalEntityY = entity.solidArea.y;
                int originalTargetX = target[i].solidArea.x;
                int originalTargetY = target[i].solidArea.y;

                // Update positions
                entity.solidArea.x = entity.worldX + entity.solidAreaDefaultX;
                entity.solidArea.y = entity.worldY + entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].worldX + target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].worldY + target[i].solidAreaDefaultY;

                // Shift based on movement
                switch (entity.direction) {
                    case "up":    entity.solidArea.y -= entity.speed; break;
                    case "down":  entity.solidArea.y += entity.speed; break;
                    case "left":  entity.solidArea.x -= entity.speed; break;
                    case "right": entity.solidArea.x += entity.speed; break;
                }

                if (entity.solidArea.intersects(target[i].solidArea)) {
                    entity.collisionOn = true;
                    index = i;
                }

                // Reset positions
                entity.solidArea.x = originalEntityX;
                entity.solidArea.y = originalEntityY;
                target[i].solidArea.x = originalTargetX;
                target[i].solidArea.y = originalTargetY;
            }
        }
        return index;
    }

    /**
     * Checks if a given entity collides with the player.
     * Used by NPCs or enemies to see if they run into the player.
     *
     * @param entity The entity checking for collision with the player
     * @return void - Updates entity.collisionOn if player collision occurs
     */
    public void checkPlayer(Entity entity) {
        int originalEntityX = entity.solidArea.x;
        int originalEntityY = entity.solidArea.y;
        int originalPlayerX = gamePanel.player.solidArea.x;
        int originalPlayerY = gamePanel.player.solidArea.y;

        // Update positions
        entity.solidArea.x = entity.worldX + entity.solidAreaDefaultX;
        entity.solidArea.y = entity.worldY + entity.solidAreaDefaultY;
        gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidAreaDefaultY;

        // Shift based on movement
        switch (entity.direction) {
            case "up":    entity.solidArea.y -= entity.speed; break;
            case "down":  entity.solidArea.y += entity.speed; break;
            case "left":  entity.solidArea.x -= entity.speed; break;
            case "right": entity.solidArea.x += entity.speed; break;
        }

        if (entity.solidArea.intersects(gamePanel.player.solidArea)) {
            entity.collisionOn = true;
        }

        // Reset positions
        entity.solidArea.x = originalEntityX;
        entity.solidArea.y = originalEntityY;
        gamePanel.player.solidArea.x = originalPlayerX;
        gamePanel.player.solidArea.y = originalPlayerY;
    }
}
