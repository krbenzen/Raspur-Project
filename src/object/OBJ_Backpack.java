package object;
import java.io.IOException;

import javax.imageio.ImageIO;

import maingame.GamePanel;
public class OBJ_Backpack extends SuperObject {
	GamePanel gp;
public OBJ_Backpack(GamePanel gp) {
	this.gp = gp;
	name = "backpack";
	try {
		image = ImageIO.read(getClass().getResourceAsStream("/object/backpack.png"));
		uTool.scaleImage(image, gp.tileSize,  gp.tileSize);
	} catch(IOException e) {
		e.printStackTrace();
	}
}
}
