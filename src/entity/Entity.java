/**
 * Lead Author(s):
 *   - Benzen Raspur
 *
 * Other Contributors:
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
 * The Entity class represents a generic game entity, such as players, NPCs, or other 
 * moving characters in the game. Attributes like position, speed, 
 * direction, and collision information. Has basic method for movement
 * drawing, and dialogue
 *
 * ISA: Entity is a base class for many game characters (Player, NPCs).
 * HAS-A:
 *   - Entity has-a reference to a GamePanel for rendering 
 *   - Entity has-a Rectangle for collision detection
 *   - Entity have arrays for dialogue lines and puzzle answers
 *
 * Learning Outcomes (LOs):
 * LO2. Arrays:
 *    - Dialogue lines stored in a String array
 *    - Answers stored in a String array.
 * LO3. Utilize objects and classes in OOP:
 *    - Entity interacts with GamePanel, Utility, collision checker, etc
 * LO4. Inheritance and polymorphism:
 *    - Entity is a superclass: Player, NPCs extend
 * LO5. Generic collections:
 *    - Not demonstrated here.
 * LO6. GUI and event-driven programming:
 *    - Entities are part of the GUI, and can react to events through updates.
 * LO7. Exception handling:
 *    - Demonstrated while loading images (catching exceptions and printing stack trace).
 */

package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import maingame.GamePanel;
import maingame.Utility;

public class Entity {
    // Reference to the main game panel 
    public GamePanel gamePanel;
    // The X-coordinate of this entity 
    public int worldX;
    // The Y-coordinate of this entity 
    public int worldY;
    // The movement speed of this entity 
    public int speed;

    // Directional sprites for animation
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;

    // The current facing direction of this entity 
    public String direction;
    // A counter used to manage sprite animation timing
    public int spriteCounter = 0;
    // The current sprite number 
    public int spriteNum = 1;

    // A rectangle used for collision detection 
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    // The default X position of the solid area.
    public int solidAreaDefaultX, solidAreaDefaultY;
    // Whether this entity is currently colliding with something. 
    public boolean collisionOn = false;
    // A counter used to manage how long an action is locked. 
    public int actionLockCounter = 0;

    // An array of dialogue strings for conversation with the player
    public String dialogues[] = new String[20];
    // The current index in the dialogues array. 
    public int dialogueIndex = 0;
    // An array of possible answers for a puzzle this entity has
    public String[] answers;
    // The index of the correct answer for the puzzle. 
    public int correctAnswer;
    // Indicating whether a puzzle given by this entity has been finished. 
    public boolean puzzleCompleted = false;

    /**
     * Constructs a new Entity object.
     *
     * @param gamePanel The GamePanel instance to which this entity belongs.
     */
    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Sets the action of this entity
     *
     * @return void
     */
    public void setAction() {
        // Default do nothing.
        // NPCs and other entities might override this.
    }

    /**
     * Handles the entity speaking, showing dialogue lines to the player.
     * If no more dialogue lines, it resets the dialogue and returns to play state.
     *
     * @return void
     */
    public void speak() {
        if (dialogueIndex < dialogues.length && dialogues[dialogueIndex] != null) {
            gamePanel.ui.currentDialogue = dialogues[dialogueIndex];
            dialogueIndex++;
        } else {
            // No more dialogue
            dialogueIndex = 0;
            gamePanel.ui.currentDialogue = "";
            gamePanel.gameState = gamePanel.playState;
            gamePanel.currentNPCIndex = -1;
        }
    }

    /**
     * Updates the entitys state every frame
     * - Calls setAction() for any entity logic.
     * - Checks collisions with tiles, objects, and the player
     * - Moves the entity if no collision is detected
     * - Animates the entity's sprite
     *
     * @return void
     */
    public void update() {
        setAction();
        collisionOn = false;
        gamePanel.colChecker.checkTile(this);
        gamePanel.colChecker.checkObject(this, false);
        gamePanel.colChecker.checkPlayer(this);

        // Move only if not colliding
        if (!collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }
    }

    /**
     * Draws the entity on the screen if visible
     *
     * @param g2 The Graphics2D object used for drawing.
     * @return void
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Calculate the on-screen position to the player
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        // Check if the entity is in the camera's view
        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
            worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
            worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
            worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

            // Choose the correct sprite based on direction and current frame
            switch (direction) {
                case "up":
                    image = (spriteNum == 1) ? up1 : up2;
                    break;
                case "down":
                    image = (spriteNum == 1) ? down1 : down2;
                    break;
                case "left":
                    image = (spriteNum == 1) ? left1 : left2;
                    break;
                case "right":
                    image = (spriteNum == 1) ? right1 : right2;
                    break;
            }

            // Draw the entity sprite
            g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }

    /**
     * Loads, scales, and returns a BufferedImage sprite based on image path
     *
     * @param imagePath The path to the sprite image file .
     * @return A BufferedImage representing the loaded and scaled sprite, or null if loading fails
     */
    public BufferedImage setup(String imagePath) {
        Utility uTool = new Utility();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}
