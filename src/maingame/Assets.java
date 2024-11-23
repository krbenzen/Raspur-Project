package maingame;

import entity.Apple_NPC;
import object.OBJ_Apple;
import object.OBJ_Backpack;
import object.OBJ_Paper;
import object.OBJ_Pencil;

public class Assets {
GamePanel gp;
public Assets(GamePanel gp) {
	this.gp = gp;
	
}
public void setObject() {
	gp.obj[0] = new OBJ_Pencil(gp);
	gp.obj[0].worldX = 10* gp.tileSize;
	gp.obj[0].worldY = 19*gp.tileSize;
	
	
	gp.obj[1] = new OBJ_Pencil(gp);
	gp.obj[1].worldX = 17* gp.tileSize;
	gp.obj[1].worldY = 24*gp.tileSize;
		
	
	
	gp.obj[2] = new OBJ_Backpack(gp);
	gp.obj[2].worldX = 15* gp.tileSize;
	gp.obj[2].worldY = 11*gp.tileSize;
	
	gp.obj[3] = new OBJ_Apple(gp);
	gp.obj[3].worldX = 15* gp.tileSize;
	gp.obj[3].worldY = 13*gp.tileSize;
			
	
//	gp.obj[4] = new OBJ_Paper(gp);
//	gp.obj[4].worldX = 13* gp.tileSize;
//	gp.obj[4].worldY = 17*gp.tileSize;
//	
//	gp.obj[5] = new OBJ_Paper(gp);
//	gp.obj[5].worldX = 14* gp.tileSize;
//	gp.obj[5].worldY = 17*gp.tileSize;
}
public void setNPC() {
	gp.npc[0] = new Apple_NPC(gp);
	gp.npc[0].worldX = gp.tileSize*15;
	gp.npc[0].worldY = gp.tileSize*15;		
}
}
