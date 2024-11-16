package object;
import java.io.IOException;

import javax.imageio.ImageIO;
public class OBJ_Paper extends SuperObject {
public OBJ_Paper() {
	name = "Paper";
	try {
		image = ImageIO.read(getClass().getResourceAsStream("/object/paper.png"));
	} catch(IOException e) {
		e.printStackTrace();
	}
	collision = true;
}

}
