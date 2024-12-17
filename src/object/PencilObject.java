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
 * The PencilObject class represents a collectible pencil item placed in the game world.
 * When picked up by the player, it increases their pencil count, allowing them
 * to interact with paper
 *
 * ISA: PencilObject IS-A SuperObject (has its properties...)
 * HAS-A: PencilObject HAS-A reference to GamePanel for tile size and context.
 *
 * Learning Outcomes (LOs):
 * LO1. Employ OOP principles:
 *    - Inheritance: PencilObject extends SuperObject.
 *    - Encapsulation: Fields are managed through superclass.
 * LO2. Arrays:
 *    - PencilObject instances can be stored in the gamePanel.obj[] array.
 * LO3. Objects and classes in OOP:
 *    - The PencilObject interacts with GamePanel context for sizing.
 * LO4. Inheritance and polymorphism:
 *    - Demonstrates inheritance from SuperObject.
 * LO6. GUI and event-driven programming:
 *    - The player picks up the pencil on collision, driven by user input.
 * LO7. Exception handling:
 *    - IOException is caught if image loading fails
 */

package object;

import java.io.IOException;
import javax.imageio.ImageIO;

import maingame.GamePanel;

public class PencilObject extends SuperObject {
    // Reference to the GamePanel providing tile size and game context. 
    GamePanel gamePanel;

    /**
     * Constructs a PencilObject and loads its image, scaling it to fit the game's tile size.
     *
     * @param gamePanel The GamePanel used to determine tile size and for game context
     * @return void
     */
    public PencilObject(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        name = "Pencil";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/pencil.png"));
            image = uTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            System.err.println("Error loading pencil object image: " + e.getMessage());
            e.printStackTrace();
        }

        // Adjust solidArea if needed 
        solidArea.x = 5;
    }
}
