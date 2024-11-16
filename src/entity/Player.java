package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import maingame.GamePanel;
import maingame.KeyInputs;

public class Player extends Entity{
	GamePanel gp;
	KeyInputs keyH;
	
	public final int screenX;
	public final int screenY;
	int hasPencil = 0;
	int hasApplePower = 0;
	
	public Player(GamePanel gp, KeyInputs keyH) {
		this.gp = gp;
		this.keyH = keyH;
screenX = gp.screenWidth/2 - (gp.tileSize/2);
screenY = gp.screenHeight/2 - (gp.tileSize/2);

solidArea = new Rectangle();
solidArea.x = 0;
solidArea.y = 0;
solidAreaDefaultX = solidArea.x;
solidAreaDefaultY = solidArea.y;
solidArea.height = 48;
solidArea.width = 48;
		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() {
		worldX=gp.tileSize * 6;
		worldY=gp.tileSize * 8;
		speed = 4;
		direction = "down";
	}
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/up1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/up1.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/down1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/down2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void update() {
		  if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
	            // Set direction based on key press
	            if (keyH.upPressed) {
	                direction = "up";
	            } else if (keyH.downPressed) {
	                direction = "down";
	            } else if (keyH.leftPressed) {
	                direction = "left";
	            } else if (keyH.rightPressed) {
	                direction = "right";
	            }

				
			// checks tile collisions
			collisionOn = false;
			gp.colChecker.checkTile(this);
			//check obj collisions
			int objIndex = gp.colChecker.checkObject(this, true);
			pickUpObject(objIndex);
			// if false player moves
			 if (!collisionOn) {
	                switch (direction) {
	                    case "up": worldY -= speed; break;
	                    case "down": worldY += speed; break;
	                    case "left": worldX -= speed; break;
	                    case "right": worldX += speed; break;
	                }
	            
			
			
			
				spriteCounter++;
				if(spriteCounter >12) {
					if(spriteNum ==1) {
					spriteNum=2;
					}
					else if(spriteNum==2) {
						spriteNum=1;
					}
					spriteCounter = 0;
				}
		}
		}
	}
	
	public void pickUpObject(int i) {
	    if (i != 999) { // Check if there's an object to pick up
	        String objectName = gp.obj[i].name;

	        switch (objectName) {
	            case "Pencil":
	            	gp.playSoundEffect(3);
	                hasPencil++;
	                gp.obj[i] = null; // Remove the pencil object
	                System.out.println("YOU HAVE OBTAINED " + hasPencil + " PENCIL(S), YOU MAY PASS THROUGH!");
	                break;

	            case "Paper":
	                if (hasPencil > 0) {
	                    hasPencil--; // use 1 pencil
	                    gp.obj[i] = null; // remove the paper object
	                    System.out.println("YOU USED A PENCIL TO REMOVE A PAPER.");
	                    gp.playSoundEffect(2);
	                } else {
	                    System.out.println("YOU NEED A PENCIL TO REMOVE THE PAPER!");
	                }
	                break;
	                
	            case "Apple":
	            	 System.out.println("You ate "+ hasApplePower + " apple(s), it feels as if nothing happened");
	            	 gp.obj[i] = null;
	            	 gp.playSoundEffect(1);
	            	 hasApplePower++;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
//	g2.setColor(Color.white);
//	g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		BufferedImage image = null;
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
				image=up1;
			}
			if(spriteNum ==2 ) {
				image=up2;
			}
		break;
		case "down":
			if(spriteNum == 1) {
				image=down1;
			}
			if(spriteNum ==2 ) {
				image=down2;
			}
			break;
		case "left":
			if(spriteNum == 1) {
				image=left1;
			}
			if(spriteNum ==2 ) {
				image=left2;
			}
			break;
		case "right":
			if(spriteNum == 1) {
				image=right1;
			}
			if(spriteNum ==2 ) {
				image=right2;
			}
			break;
		}
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
}
