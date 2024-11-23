package entity;

import java.util.Random;

import maingame.GamePanel;


public class Apple_NPC extends Entity{
public Apple_NPC(GamePanel gp) {
	super(gp);
	direction = "down";
	speed = 1;
	getImage();
}
public void getImage() {
	
	up1 = setup("/npc/applenpc");
	up2 = setup("/npc/applenpc");
	down1 = setup("/npc/applenpc");
	down2 =  setup("/npc/applenpc");
	left1 =  setup("/npc/applenpc");
	left2 = setup("/npc/applenpc");
	right1 = setup("/npc/applenpc");
	right2 = setup("/npc/applenpc");

}

public void setAction()  {
Random random = new Random();
int i = random.nextInt(100) + 1;
if (i <= 25){
	direction = "up";
}
if(i>25 && i <=50) {
	direction = "down";
}
if(i>50 && i <=75) {
	direction = "left";
}
if(i>75 && i <=100) {
	direction = "right";
}
}
}




