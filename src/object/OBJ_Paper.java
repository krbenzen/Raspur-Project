package object;
import java.io.IOException;

import javax.imageio.ImageIO;

import maingame.GamePanel;
public class OBJ_Paper extends SuperObject {
	GamePanel gp;
public OBJ_Paper(GamePanel gp) {
	this.gp = gp;
	name = "Paper";
	try {
		image = ImageIO.read(getClass().getResourceAsStream("/object/paper.png"));
		uTool.scaleImage(image, gp.tileSize,  gp.tileSize);
	} catch(IOException e) {
		e.printStackTrace();
	}
	collision = true;
}

}
