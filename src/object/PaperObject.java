/**
 * Lead Author(s):
 *   - Benzen Raspur
 *
 * Other Contributors:
 *   - None
 *
 * References:
 *   - Morelli, R., & Walde, R. (2016). Java, Java, Java: Object-Oriented Problem Solving.
 *     https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *   - Bechtold, S., Brannen, S., Link, J., Merdes, M., Philipp, M., Rancourt, J. D., & Stein, C. (n.d.).
 *     JUnit 5 user guide. JUnit 5.
 *     https://junit.org/junit5/docs/current/user-guide/
 *
 * Version/Date: 12/16/2024
 *
 * Description:
 * The PaperObject class represents a paper item placed in the game world. 
 * This object can be interacted with by the player if they have a pencil to remove it
 *
 * ISA: PaperObject IS-A SuperObject (has its properties...)
 * HAS-A: PaperObject HAS-A reference to GamePanel for accessing tile sizes and game context.
 *
 * Learning Outcomes (LOs):
 * LO1. Employ OOP principles:
 *    - Inheritance: PaperObject extends SuperObject.
 *    - Encapsulation: Fields inherited from SuperObject.
 * LO2. Arrays:
 *    - PaperObject instances are placed in the gamePanel.obj[] array.
 * LO3. Objects and classes in OOP:
 *    - Interacts with GamePanel for config.
 * LO4. Inheritance and polymorphism:
 *    - Demonstrates inheritance from SuperObject.
 * LO6. GUI and event-driven programming:
 *    - The player interacts with this object when colliding and having a pencil.
 * LO7. Exception handling:
 *    - IOException is caught and printed if image loading fails.
 */

package object;

import java.io.IOException;
import javax.imageio.ImageIO;
import maingame.GamePanel;

public class PaperObject extends SuperObject {
    // Reference to the GamePanel providing tile size and game context. 
    GamePanel gamePanel;

    /**
     * Constructs a PaperObject and loads its image.
     *
     * @param gamePanel The game panel from which tile size and context are derived.
     * @return void
     */
    public PaperObject(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        name = "Paper";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/paper.png"));
            image = uTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            System.err.println("Error loading paper object image: " + e.getMessage());
            e.printStackTrace();
        }
        collision = true;
    }
}
