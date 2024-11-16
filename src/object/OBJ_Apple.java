package object;
import java.io.IOException;

import javax.imageio.ImageIO;
public class OBJ_Apple extends SuperObject {
public OBJ_Apple() {
	name = "Apple";
	try {
		image = ImageIO.read(getClass().getResourceAsStream("/object/apple.png"));
	} catch(IOException e) {
		e.printStackTrace();
	}
}
}
