package object;
import java.io.IOException;
import javax.imageio.ImageIO;

import maingame.GamePanel;


public class OBJ_Apple extends SuperObject {
	GamePanel gp;
public OBJ_Apple(GamePanel gp) {
	this.gp = gp;
	name = "Apple";
	try {
		image = ImageIO.read(getClass().getResourceAsStream("/object/apple.png"));
		uTool.scaleImage(image, gp.tileSize,  gp.tileSize);
	} catch(IOException e) {
		e.printStackTrace();
	}
}
}
