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
 * The Player class represents the player character in the game. It extends the Entity class, inheriting 
 * basic attributes such as position, direction, and collision. Manages input 
 * from the user through KeyInputs.
 * 
 * ISA: Player IS-A Entity
 * HAS-A: Player HAS-A reference to KeyInputs for movement and interaction.
 * 
 * Learning Outcomes (LOs):
 * LO1. Employ design principles of OOP:
 *    - Inheritance: Player extends Entity.
 * LO3. Objects and classes in OOP:
 *    - The Player class interacts with GamePanel, KeyInputs, and Entities.
 * LO4. Inheritance and polymorphism:
 *    - Demonstrated as Player extends Entity (inheritance).
 * LO5. Generic collections:
 *    - Not demonstrated here.
 * LO6. GUI and event-driven programming:
 *    - Player movement is event-driven by KeyInputs (keyboard events).
 */

package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import maingame.GamePanel;
import maingame.KeyInputs;

public class Player extends Entity {
	// Key handler for processing player's input: movement, interaction.
    private KeyInputs keyH;
    // The player's X position on-screen 
    public final int screenX;
    // The player's Y position on-screen 
    public final int screenY;
    // Pencil item number amount
    public int pencilCount = 0;

    /**
     * Constructs a Player object tied to GamePanel and input handler.
     *
     * @param gamePanel The GamePanel instance the player belongs to
     * @param keyH The KeyInputs instance for detecting user input.
     */
    public Player(GamePanel gamePanel, KeyInputs keyH) {
        super(gamePanel);
        this.keyH = keyH;
        
        // Center the player on the screen
        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        solidArea = new Rectangle(0, 0, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    /**
     * Sets default values for player's position, speed, and direction
     *
     * @return void
     */
    public void setDefaultValues() {
        // Player world position and speed initialization
        worldX = gamePanel.tileSize * 1;
        worldY = gamePanel.tileSize * 1;
        speed = 10;
        direction = "down";
    }

    /**
     * Loads and assigns the player's movement sprites for animation.
     * This method retrieves images from the resource path 
     *
     * @return void
     */
    public void getPlayerImage() {
        up1 = setup("/player/up1");
        up2 = setup("/player/up2");
        down1 = setup("/player/down");
        down2 = setup("/player/down2");
        left1 = setup("/player/left1");
        left2 = setup("/player/left2");
        right1 = setup("/player/right1");
        right2 = setup("/player/right2");
    }

    /**
     * Updates the player's state every frame:
     * - Reads input to determine movement direction
     * - Checks for collisions.
     * - Moves the player if no collision is detected.
     * - Animates the sprite.
     * - Interacts with NPCs if the player is facing them and the enter key is pressed upon.
     *
     * @return void
     */
    @Override
    public void update() {
        boolean moving = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;

        // Collision detection state
        collisionOn = false;

        // Direction if player plans to move
        if (moving) {
            if (keyH.upPressed) direction = "up";
            else if (keyH.downPressed) direction = "down";
            else if (keyH.leftPressed) direction = "left";
            else if (keyH.rightPressed) direction = "right";

            // Check collisions with tiles and objects before moving
            gamePanel.colChecker.checkTile(this);
            gamePanel.colChecker.checkObject(this, true);
            int npcIndex = gamePanel.colChecker.checkEntity(this, gamePanel.npc);

            // Move only if there were no collisions
            if (!collisionOn) {
                switch (direction) {
                    case "up":    worldY -= speed; break;
                    case "down":  worldY += speed; break;
                    case "left":  worldX -= speed; break;
                    case "right": worldX += speed; break;
                }

                // Add spriteCounter to animate walking.
                spriteCounter++;
                if (spriteCounter > 12) {
                    spriteNum = (spriteNum == 1) ? 2 : 1;
                    spriteCounter = 0;
                }
            }
        }

        // Check for NPC interaction even if not moving
        int npcIndex = gamePanel.colChecker.checkEntity(this, gamePanel.npc);
        interactNPC(npcIndex);
    }

    /**
     * Interacts with an NPC if one is found at the player's location and the player presses enter
     * This method changes the game state to triggers the NPC's speak 
     *
     * @param i The index of the NPC in the gamePanel.npc array
     * @return void
     */
    public void interactNPC(int i) {
        if (i != 999) {
            if (gamePanel.keyH.enterPressed && gamePanel.gameState == gamePanel.playState) {
                // Switch to dialogue state and have NPC speak
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.currentNPCIndex = i;
                gamePanel.npc[i].speak();
            }
            gamePanel.keyH.enterPressed = false;
        }
    }

    /**
     * Draws the player's current sprite frame
     * The frame displayed depends on the player's direction and sprite number
     *
     * @param g2 The Graphics2D object used for drawing.
     * @return void
     */
    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Choose the correct sprite based on current direction and frame
        switch(direction) {
            case "up":    image = (spriteNum == 1) ? up1 : up2; break;
            case "down":  image = (spriteNum == 1) ? down1 : down2; break;
            case "left":  image = (spriteNum == 1) ? left1 : left2; break;
            case "right": image = (spriteNum == 1) ? right1 : right2; break;
        }
        
        // Draw the player's sprite at the pre-calculated screen 
        g2.drawImage(image, screenX, screenY, null);
    }
}
