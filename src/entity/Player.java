package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import maingame.GamePanel;
import maingame.KeyInputs;

public class Player extends Entity {
    KeyInputs keyH;
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyInputs keyH) {
        super(gp);
        this.keyH = keyH;
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(0, 0, 48, 48);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 6;
        worldY = gp.tileSize * 8;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        up1 = setup("/player/up1");
        up2 = setup("/player/up2");
        down1 = setup("/player/down1");
        down2 = setup("/player/down2");
        left1 = setup("/player/left1");
        left2 = setup("/player/left2");
        right1 = setup("/player/right1");
        right2 = setup("/player/right2");
    }

    @Override
    public void update() {
        boolean moving = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;

        // Reset collisionOn at start of update
        collisionOn = false;

        // If player intends to move, determine direction first
        if (moving) {
            if (keyH.upPressed) direction = "up";
            else if (keyH.downPressed) direction = "down";
            else if (keyH.leftPressed) direction = "left";
            else if (keyH.rightPressed) direction = "right";

            // Check collisions BEFORE moving
            gp.colChecker.checkTile(this);
            gp.colChecker.checkObject(this, true);
            int npcIndex = gp.colChecker.checkEntity(this, gp.npc);
            // If collisionOn is set here, we won't move this frame

            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }

                spriteCounter++;
                if(spriteCounter > 12) {
                    spriteNum = (spriteNum == 1) ? 2 : 1;
                    spriteCounter = 0;
                }
            }
        }

        // Even if not moving, still check for NPC interaction
        int npcIndex = gp.colChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);
    }

    public void interactNPC(int i) {
        if (i != 999) {
            if(gp.keyH.enterPressed && gp.gameState == gp.playState) {
                gp.gameState = gp.dialogueState;
                gp.currentNPCIndex = i;
                gp.npc[i].speak();
            }
            gp.keyH.enterPressed = false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch(direction) {
            case "up": image = (spriteNum == 1) ? up1 : up2; break;
            case "down": image = (spriteNum == 1) ? down1 : down2; break;
            case "left": image = (spriteNum == 1) ? left1 : left2; break;
            case "right": image = (spriteNum == 1) ? right1 : right2; break;
        }
        g2.drawImage(image, screenX, screenY, null);
    }
}
