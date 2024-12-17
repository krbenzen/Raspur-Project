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
 * The Tile class represents a single tile that can be placed in the game world. Each tile
 * can have an image and a flag showing whether it is solid or not.
 *
 * ISA: Tile is a basic data-holder class, not extending any other custom class.
 * HAS-A: Tile HAS-A BufferedImage representing its appearance, and a boolean for collision.
 *
 * Learning Outcomes (LOs):
 * LO1. Employ OOP design principles:
 *    - Encapsulation: The tile data is contained within this class.
 *    - Simple class responsibility (just holds tile data).
 * LO2. Arrays:
 *    - Instances of Tile are placed in arrays within the TileManager.
 * LO3. Utilize objects and classes:
 *    - Tile instances are used by the TileManager and GamePanel to render the game world.
 * LO6. GUI and event-driven programming:
 *    - Used as part of the GUI environment, displayed on a JPanel.

 */

package tile;

import java.awt.image.BufferedImage;

public class Tile {
    // The image representing this tile. 
    public BufferedImage image;
   // Indicates if this tile is collidable (blocks movement). 
    public boolean collision = false;
}
