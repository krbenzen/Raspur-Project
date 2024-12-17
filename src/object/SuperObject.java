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
 * The SuperObject class is like a blueprint for all objects that can be placed in the game world. 
 * These objects could be items the player can pick up, obstacles, or interactive environmental 
 * features. It stores information about the object's image, name, position, collision properties.
 *
 * ISA: SuperObject is a base class for all object types 
 * HAS-A: SuperObject HAS-A Utility instance for image scaling
 * and a Rectangle for collision detection
 *
 * Learning Outcomes (LOs):
 * LO1. OOP Design Principles:
 *    - Inheritance: Certain objects extend this class
 *    - Encapsulation: Object data like collisions is contained within this class.
 * LO2. Arrays:
 *    - Instances of SuperObject subclasses are stored in arrays (e.g., gamePanel.obj[]).
 * LO3. Objects and Classes:
 *    - This class interacts with GamePanel and Utility classes.
 * LO4. Inheritance and Polymorphism:
 *    - This class is meant to be subclassed by object types to display and behave differently
 * LO6. GUI and Event-Driven Programming:
 *    - Objects are drawn on a JPanel, influences the player's event-driven interaction
 */

package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import maingame.GamePanel;
import maingame.Utility;

public class SuperObject {
    // The image representing the object. 
    public BufferedImage image;
    // The name of the object. 
    public String name;
    // Whether this object is collidable. If true the player cannot pass 
    public boolean collision = false;
    // The X position of the object in the game world 
    public int worldX;
    // The Y position of the object in the game world 
    public int worldY;
    // The collision area of the object, used to detect interactions 
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    // The default X offset of the solid area from the object's top-left corner 
    public int solidAreaDefaultX = 0;
    // The default Y offset of the solid area from the object's top-left corner 
    public int solidAreaDefaultY = 0;
    // A utility tool for image scaling and other graphical operations. 
    Utility uTool = new Utility();

    /**
     * Draws the object on the game screen if it is within the camera's view range
     *
     * @param g2        The Graphics2D object used for drawing.
     * @param gamePanel  The GamePanel that provides rendering context
     *                   and player position.
     * @return void
     */
    public void draw(Graphics2D g2, GamePanel gamePanel) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        // Only draw the object if it is within the players visiblility
        if(worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
           worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
           worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
           worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

            g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }
}
