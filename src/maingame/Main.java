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
 * The Main class serves as the entry point for the application
 * - Creates a JFrame window.
 * - Initializes and adds a GamePanel instance to the window.
 * - Sets up the game by calling setupGame().
 * - Starts the game loop by calling startGameThread().
 *
 * ISA: Main is a launcher class for the application
 * HAS-A: Main creates and HAS-A JFrame and a GamePanel instance
 *
 */

package maingame;

import javax.swing.JFrame;

public class Main {

    /**
     * The main method is the entry of the program. It sets up a JFrame window
     * adds the GamePanel, sets the window properties, and starts the game
     *
     * @param args Command line arguments (not used).
     * @return void
     */
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Alegbraic Animals");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
