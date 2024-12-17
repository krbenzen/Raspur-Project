/**
 * Lead Author(s):
 *   - Benzen Raspur
 *
 * Other contributors:
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
 * The KeyInputs class implements KeyListener and MouseListener to handle user input 
 * for the game. It detects when keys are pressed or released and updates.
 * It also handles mouse clicks on puzzle answers, updating the game state,  and UI.
 *
 * ISA: KeyInputs IS-A KeyListener and IS-A MouseListener
 * HAS-A: KeyInputs HAS-A reference to GamePanel to change game states 
 *
 * Learning Outcomes (LOs):
 * LO1. OOP design principles:
 *    - Demonstrates event-driven input handling.
 * LO3. Objects and classes in OOP:
 *    - Interacts with GamePanel, UI, NPC (Entity subclasses).
 * LO4. Inheritance and polymorphism:
 *    - Implements KeyListener and MouseListener interfaces.
 * LO6. GUI and event-driven programming:
 *    - Example of event driven input handling in a GUI environment.
 */

package maingame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import entity.Entity;

public class KeyInputs implements KeyListener, MouseListener {
    // Reference to the main GamePanel, allowing state changes and resource access. 
    GamePanel gamePanel;
    // Flags indicating whether certain movement keys are pressed. 
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    // Debug flag to check draw times 
    boolean checkDrawTime = false;

    /**
     * Constructs a KeyInputs object GamePanel
     *
     * @param gamePanel The main game panel instance used to access and modify game states
     * @return void
     */
    public KeyInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * @param e The KeyEvent triggered when a key is typed.
     * @return void
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Handles key press events to move the player, pause unpause the game
     * interact is enter, show debug info, or exit.
     *
     * @param e The KeyEvent triggered when a key is pressed.
     * @return void
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Behavior depends on the current game state...
        if (gamePanel.gameState == gamePanel.playState) {
            if (code == KeyEvent.VK_W) upPressed = true;
            if (code == KeyEvent.VK_S) downPressed = true;
            if (code == KeyEvent.VK_A) leftPressed = true;
            if (code == KeyEvent.VK_D) rightPressed = true;
            if (code == KeyEvent.VK_ESCAPE) gamePanel.gameState = gamePanel.pauseState;
            if (code == KeyEvent.VK_ENTER) enterPressed = true;
            if (code == KeyEvent.VK_T) checkDrawTime = !checkDrawTime;

        } else if (gamePanel.gameState == gamePanel.pauseState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.gameState = gamePanel.playState;
            }

        } else if (gamePanel.gameState == gamePanel.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }

        } else if (gamePanel.gameState == gamePanel.puzzleState) {
    

        } else if (gamePanel.gameState == gamePanel.gameOverState) {
            // Press Enter to exit the game
            if (code == KeyEvent.VK_ENTER) {
                System.exit(0);
            }
        }
    }

    /**
     * Handles key release events, stopping the movement when keys are lifted
     *
     * @param e The KeyEvent triggered when a key is released.
     * @return void
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) upPressed = false;
        if (code == KeyEvent.VK_S) downPressed = false;
        if (code == KeyEvent.VK_A) leftPressed = false;
        if (code == KeyEvent.VK_D) rightPressed = false;
    }

    /**
     * Handles mouse clicks, mainly used in puzzleState to determine which answer the player clicked.
     * If the clicked answer is correct, the player is rewarded and returned to the playState. If incorrect, punishment.
     *
     * @param e The MouseEvent triggered when the mouse is clicked.
     * @return void
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (gamePanel.gameState == gamePanel.puzzleState) {
            int clickedIndex = gamePanel.ui.getClickedAnswerButton(mouseX, mouseY);

            if (clickedIndex != -1) {
                Entity currentNPC = gamePanel.npc[gamePanel.currentNPCIndex];

                if (clickedIndex == currentNPC.correctAnswer) {
                    // Correct answer scenario
                    currentNPC.puzzleCompleted = true;
                    gamePanel.ui.showMessage("Correct!");
                    gamePanel.player.pencilCount++;
                    gamePanel.playSoundEffect(3);
                    gamePanel.gameState = gamePanel.playState;
                } else {
                    // Wrong answer scenario
                    gamePanel.ui.playerHealth--;
                    gamePanel.ui.showMessage("Wrong Answer!");
                    gamePanel.playSoundEffect(4);
                }
            }
        }
    }

    // 
    /**
     *MouseListener.
     *
     * @param e The MouseEvent triggered when the mouse is pressed
     * @return void
     */
    @Override public void mousePressed(MouseEvent e) {}

    /**
     *MouseListener.
     *
     * @param e The MouseEvent triggered when the mouse is released
     * @return void
     */
    @Override public void mouseReleased(MouseEvent e) {}

    /**
     * Not used, but required by MouseListener.
     *
     * @param e The MouseEvent triggered when the mouse enters a component.
     * @return void
     */
    @Override public void mouseEntered(MouseEvent e) {}

    /**
     * MouseListener.
     *
     * @param e The MouseEvent triggered when the mouse exits a component
     * @return void
     */
    @Override public void mouseExited(MouseEvent e) {}
}
