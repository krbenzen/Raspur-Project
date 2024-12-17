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
 * The Utility class provides helper methods for common operations needed through
 * the game, such as image scaling. By encapsulating operations, we avoid 
 * code duplication 
 *
 * ISA: Utility is a helper class that provides static or instance utility functions
 * HAS-A: Utility does not hold state; it provides a method for scaling images
 *
 * Learning Outcomes (LOs):
 * LO1. Employ OOP design principles:
 *    - Single Responsibility: This class focuses solely on utility operations, such as image scaling.
 * LO3. Objects and classes in OOP:
 *    - Utility interacts with BufferedImage objects to produce scaled images.
 * LO6. GUI and event-driven programming:
 *    - Supports GUI by preparing images for rendering at the appropriate size.
 */

package maingame;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Utility {

    /**
     * Scales a given BufferedImage to the width and height
     *
     * @param original The original BufferedImage to be scaled.
     * @param width    The desired width of the scaled image.
     * @param height   The desired height of the scaled image.
     * @return A new BufferedImage that is a scaled version of the original.
     */
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();
        return scaledImage;
    }
}
