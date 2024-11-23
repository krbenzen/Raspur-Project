package maingame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Pencil;

public class UI {
GamePanel gp;
Graphics2D g2;
Font arial_40, arial_80B;
//BufferedImage pencilImage;
public boolean messageOn = false;
public String message = "";
int messageCounter = 0;
public boolean gameFinished = false;
double playTime;
DecimalFormat dFormat = new DecimalFormat("0.00");

public UI(GamePanel gp) {
	this.gp = gp;
	arial_40 = new Font("arial", Font.PLAIN, 40);
	arial_80B = new Font("arial", Font.BOLD, 60);
//	OBJ_Pencil pencil = new OBJ_Pencil(gp);
//	pencilImage = pencil.image;
}
public void showMessage(String text) {
	message = text;
	messageOn = true;
}
public void draw(Graphics2D g2) {
	this.g2 = g2;
	g2.setFont(arial_40);
	g2.setColor(Color.black);
	if(gp.gameState == gp.playState) {
		
	}
	if (gp.gameState == gp.pauseState) {
		drawPauseScreen();
	}

		
	}
	
public void drawPauseScreen() {
	g2.setFont(g2.getFont().deriveFont (Font.BOLD,75F));
	String text = "GAME PAUSED";
			int x = getXforCenteredText(text);
			
			int y = gp.screenHeight/2;
	g2.drawString(text,  x,  y);
	
}
public int getXforCenteredText(String text) {
	int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
	int x = gp.screenWidth/2 - length/2;
	return x;
}
//	if(gameFinished == true) {
//		g2.setFont(arial_40);
//		
//		g2.setColor(Color.black);
//		
//		String text;
//		int textLength;
//		int x;
//		int y;
//		
//		 g2.setFont(arial_40);
//			g2.setColor(Color.black);
//			text = "Your Time is:" + dFormat.format(playTime);
//			textLength = (int)g2.getFontMetrics().getStringBounds(text,  g2).getWidth();
//			 x = gp.screenWidth/2 - textLength/2;
//			 y = gp.screenHeight/2 - (gp.tileSize*5);
//			 g2.drawString(text,  x , y);
//		 
//		 g2.setFont(arial_80B);
//			g2.setColor(Color.black);
//			text = "YOU WON!!";
//			textLength = (int)g2.getFontMetrics().getStringBounds(text,  g2).getWidth();
//			
//			 x = gp.screenWidth/2 - textLength/2;
//			 y = gp.screenHeight/2 - (gp.tileSize*6);
//			 g2.drawString(text,  x , y);
//			 
//			 gp.gameThread=null;
//	}
//	
//	else {
//		g2.setFont(arial_40);
//		g2.setColor(Color.black);
//		g2.drawImage(pencilImage,  gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
//		g2.drawString("" + gp.player.hasPencil, 70, 60);
//		
//		//playtime
//		playTime +=(double)1/60;
//		g2.drawString("" + dFormat.format(playTime),490,60);
//		// pop up message
//		if(messageOn == true) {
//			g2.setFont(g2.getFont().deriveFont(30F));
//			g2.drawString(message,  10, 760);
//			messageCounter++;
//			
//			if(messageCounter >120) {
//				messageCounter = 0;
//				messageOn = false;
//	}
//	
//		}
		
//	}


	

}
