package object;
import java.io.IOException;

import javax.imageio.ImageIO;
public class OBJ_Backpack extends SuperObject {
public OBJ_Backpack() {
	name = "backpack";
	try {
		image = ImageIO.read(getClass().getResourceAsStream("/object/backpack.png"));
	} catch(IOException e) {
		e.printStackTrace();
	}
}
}
