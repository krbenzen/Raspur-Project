package maingame;

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
	gp.obj[0] = new OBJ_Pencil();
	gp.obj[0].worldX = 10* gp.tileSize;
	gp.obj[0].worldY = 19*gp.tileSize;
	
	
	gp.obj[1] = new OBJ_Pencil();
	gp.obj[1].worldX = 17* gp.tileSize;
	gp.obj[1].worldY = 24*gp.tileSize;
		
	
	
	gp.obj[2] = new OBJ_Backpack();
	gp.obj[2].worldX = 15* gp.tileSize;
	gp.obj[2].worldY = 11*gp.tileSize;
	
	
	gp.obj[3] = new OBJ_Backpack();
	gp.obj[3].worldX = 17* gp.tileSize;
	gp.obj[3].worldY = 11*gp.tileSize;
	
	gp.obj[4] = new OBJ_Apple();
	gp.obj[4].worldX = 15* gp.tileSize;
	gp.obj[4].worldY = 13*gp.tileSize;
			
	
	gp.obj[5] = new OBJ_Apple();
	gp.obj[5].worldX = 17* gp.tileSize;
	gp.obj[5].worldY = 13*gp.tileSize;
	
	gp.obj[6] = new OBJ_Paper();
	gp.obj[6].worldX = 13* gp.tileSize;
	gp.obj[6].worldY = 17*gp.tileSize;
	
	gp.obj[7] = new OBJ_Paper();
	gp.obj[7].worldX = 14* gp.tileSize;
	gp.obj[7].worldY = 17*gp.tileSize;
}
}
