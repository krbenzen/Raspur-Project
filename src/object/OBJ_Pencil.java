package object;
import java.io.IOException;

import javax.imageio.ImageIO;

import maingame.GamePanel;
public class OBJ_Pencil extends SuperObject {
	GamePanel gp;
public OBJ_Pencil(GamePanel gp) {
	this.gp = gp;
	name = "Pencil";
	try {
		image = ImageIO.read(getClass().getResourceAsStream("/object/pencil.png"));
		uTool.scaleImage(image, gp.tileSize,  gp.tileSize);
	} catch(IOException e) {
		e.printStackTrace();
	}
	solidArea.x = 5;
}
}
