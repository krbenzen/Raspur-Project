package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import maingame.GamePanel;
import maingame.KeyInputs;
import maingame.Utility;

public class Player extends Entity{
	
	KeyInputs keyH;
	
	public final int screenX;
	public final int screenY;
//	public int hasPencil = 0;
	int hasApplePower = 0;
	
	public Player(GamePanel gp, KeyInputs keyH) {
		super(gp);

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
	
			up1 = setup("/player/up1");
			up2 = setup("/player/up2");
			down1 = setup("/player/down1");
			down2 =  setup("/player/down2");
			left1 =  setup("/player/left1");
			left2 = setup("/player/left2");
			right1 = setup("/player/right1");
			right2 = setup("/player/right2");
	
	
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
//	        String objectName = gp.obj[i].name;
//
//	        switch (objectName) {
//	            case "Pencil":
//	            	gp.playSoundEffect(3);
//	                hasPencil++;
//	                gp.obj[i] = null; // Remove the pencil object
//	                gp.ui.showMessage("You have obtained a Pencil!");
//	                break;
//
//	            case "Paper":
//	                if (hasPencil > 0) {
//	                    hasPencil--; // use 1 pencil
//	                    gp.obj[i] = null; // remove the paper object
//	                    gp.ui.showMessage("YOU USED A PENCIL");
//	                    gp.playSoundEffect(2);
//	                } else {
//	                    gp.ui.showMessage("YOU NEED A PENCIL");
//	                    gp.playSoundEffect(4);
//	                }
//	                break;
//	                
//	            case "Apple":
//	            	gp.obj[i] = null;
//	            	gp.ui.showMessage("You ate an apple.");
//	            	gp.ui.gameFinished=true;
//	            	gp.stopMusic();
//	            
//	           
//	            	 gp.playSoundEffect(2);
//	            	 hasApplePower++;
//	            	 break;
//			}
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
		g2.drawImage(image, screenX, screenY, null);
	}
}
