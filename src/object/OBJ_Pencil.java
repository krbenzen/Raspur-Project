package object;
import java.io.IOException;

import javax.imageio.ImageIO;
public class OBJ_Pencil extends SuperObject {
public OBJ_Pencil() {
	name = "Pencil";
	try {
		image = ImageIO.read(getClass().getResourceAsStream("/object/pencil.png"));
	} catch(IOException e) {
		e.printStackTrace();
	}
	solidArea.x = 5;
}
}
