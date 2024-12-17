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
 * The UI class manages all on screen graphical user interface elements
 * - Drawing player health, pencil count, and messages.
 * - Displaying dialogue boxes, pause screens, puzzle screens, and game over screens
 * - Handling puzzle answers and multiple-choice buttons
 *
 * ISA: UI is a helper/manager class for drawing user interface elements on a GamePanel.
 * HAS-A: UI has-a reference to GamePanel and uses its dimensions, player state, and game state.
 *
 * Learning Outcomes (LOs):
 * LO1. Employ design principles of OOP:
 *    - Encapsulation: This class encapsulates all logic for rendering the UI.
 * LO2. Arrays:
 *    - Demonstrated by puzzleAnswers and buttonX/buttonY arrays.
 * LO3. Objects and classes in OOP:
 *    - UI interacts with GamePanel and uses Utility for image scaling.
 * LO6. GUI and event-driven programming:
 *    - GUI rendering. Responds to game state changes to show correct UI.
 * LO7. Exception handling:
 *    - Demonstrated by try catch when loading images.
 */

package maingame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class UI {
    // Reference to the GamePanel for sizes, states, etc. 
    GamePanel gamePanel;
    // Graphics2D object used for drawing UI elements. 
    Graphics2D g2;
    // Various fonts used for UI text. 
    Font arial_40, arial_80B, ComicSans_40B;
    // Flag indicating if a temporary message should be displayed. 
    public boolean messageOn = false;
    // The temporary message to display. 
    public String message = "";
    // Counter for how long the message is displayed. 
    int messageCounter = 0;
    // Flag indicating if the game is finished. 
    public boolean gameFinished = false;
    // The current dialogue text displayed in dialogueState. 
    public String currentDialogue = "";

    // Player health represented as number of hearts.
    public int playerHealth = 3;

    // Full heart image for representing player health. 
    BufferedImage heartFull;
    // Image representing a pencil
    BufferedImage pencilImage;

    // Dimensions for puzzle answer buttons. 
    private int buttonWidth = 100;
    private int buttonHeight = 50;
    private int spacing = 20;

    // Array of puzzle answers displayed during puzzleState. 
    private String[] puzzleAnswers = {"2", "3", "4", "5"};
    // X-coordinates for puzzle answer buttons. 
    private int[] buttonX = new int[puzzleAnswers.length];
    // Y-coordinates for puzzle answer buttons. 
    private int[] buttonY = new int[puzzleAnswers.length];

    /**
     * Constructs a UI object and initializes fonts and images.
     *
     * @param gamePanel The GamePanel associated with this UI.
     * @return void
     */
    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        ComicSans_40B = new Font("Comic Sans MS", Font.BOLD, 40);
        loadHeartImage();
        loadPencilImage();
    }

    /**
     * Loads the pencil image and scales it to the tile size.
     *
     * @return void
     */
    public void loadPencilImage() {
        try {
            pencilImage = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/object/pencil.png"));
            pencilImage = new Utility().scaleImage(pencilImage, gamePanel.tileSize, gamePanel.tileSize);
        } catch (Exception e) {
            System.err.println("Error loading pencil image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads the heart image and scales it to the tile size.
     *
     * @return void
     */
    public void loadHeartImage() {
        try {
            heartFull = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/object/heart.png"));
            heartFull = new Utility().scaleImage(heartFull, gamePanel.tileSize, gamePanel.tileSize);
        } catch (Exception e) {
            System.err.println("Error loading heart image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Shows a temporary message on the screen.
     *
     * @param text The message text to be displayed.
     * @return void
     */
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    /**
     * Returns the current puzzle answers array
     *
     * @return A String array of puzzle answers.
     */
    public String[] getPuzzleAnswers() {
        return puzzleAnswers;
    }

    /**
     * Sets new puzzle answers and verifies that the correct answer index is good
     *
     * @param answers The array of new puzzle answers.
     * @param correctAnswerIndex The index of the correct answer.
     * @return void
     */
    public void setPuzzleAnswers(String[] answers, int correctAnswerIndex) {
        if (answers != null && correctAnswerIndex >= 0 && correctAnswerIndex < answers.length) {
            puzzleAnswers = answers;
            System.out.println("Puzzle Answers Updated: " + Arrays.toString(puzzleAnswers));
        } else {
            System.err.println("Error: Invalid answers or correct answer index!");
        }
    }

    /**
     * Main draw method, called every frame to render UI 
     *
     * @param g2 The Graphics2D object used for drawing on the screen.
     * @return void
     */
    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        drawHearts();
        drawPencilCount();

        if (gamePanel.gameState == gamePanel.playState) {
        } else if (gamePanel.gameState == gamePanel.pauseState) {
            drawPauseScreen();
        } else if (gamePanel.gameState == gamePanel.dialogueState) {
            drawDialogueScreen();
        } else if (gamePanel.gameState == gamePanel.puzzleState) {
            drawPuzzleScreen();
        } else if (gamePanel.gameState == gamePanel.gameOverState) {
            drawGameOverScreen();
        }

        // Temporary message display
        if (messageOn) {
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message, gamePanel.tileSize / 2, gamePanel.screenHeight - gamePanel.tileSize);
            messageCounter++;
            if (messageCounter > 120) {
                messageCounter = 0;
                messageOn = false;
            }
        }
    }

    /**
     * Draws the player's current pencil count and an icon.
     *
     * @return void
     */
    public void drawPencilCount() {
        g2.drawImage(pencilImage, 30, 80, null);
        g2.setFont(ComicSans_40B);
        g2.setColor(Color.white);
        String pencilText = "x " + gamePanel.player.pencilCount;
        g2.drawString(pencilText, 90, 115);
    }

    /**
     * Draws hearts for the player's health.
     *
     * @return void
     */
    public void drawHearts() {
        int x = gamePanel.tileSize / 2;
        int y = gamePanel.tileSize / 2;
        for (int i = 0; i < playerHealth; i++) {
            g2.drawImage(heartFull, x + (i * (gamePanel.tileSize + 10)), y, null);
        }
    }

    /**
     * Draws the pause screen text
     *
     * @return void
     */
    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 75F));
        String text = "GAME PAUSED";
        int x = getXforCenteredText(text);
        int y = gamePanel.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    /**
     * Draws the dialogue box and text when the game is in dialogueState
     *
     * @return void
     */
    public void drawDialogueScreen() {
        int x = 75;
        int y = 525;
        int width = gamePanel.screenWidth - (gamePanel.tileSize * 3);
        int height = gamePanel.tileSize * 5;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;

        // Print dialogue line x line
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    /**
     * Draws the puzzle screen with question and multiple-choice answer buttons.
     *
     * @return void
     */
    public void drawPuzzleScreen() {
        int puzzleX = gamePanel.tileSize;
        int puzzleY = gamePanel.tileSize * 10;
        int puzzleWidth = gamePanel.screenWidth - (gamePanel.tileSize * 2);
        int puzzleHeight = gamePanel.tileSize * 6;
        drawSubWindow(puzzleX, puzzleY, puzzleWidth, puzzleHeight);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        g2.setColor(Color.white);

        // Center the question text
        String questionText = currentDialogue;
        int questionX = puzzleX + (puzzleWidth - g2.getFontMetrics().stringWidth(questionText)) / 2;
        int questionY = puzzleY + gamePanel.tileSize;
        g2.drawString(questionText, questionX, questionY);

        // Calculate button alignment
        int totalButtonWidth = (buttonWidth * puzzleAnswers.length) + (spacing * (puzzleAnswers.length - 1));
        int startX = puzzleX + (puzzleWidth - totalButtonWidth) / 2;
        int buttonYPosition = questionY + gamePanel.tileSize * 2;

        // Draw buttons for each answer
        for (int i = 0; i < puzzleAnswers.length; i++) {
            int bx = startX + i * (buttonWidth + spacing);
            int by = buttonYPosition;
            buttonX[i] = bx;
            buttonY[i] = by;
            drawAnswerButton(bx, by, buttonWidth, buttonHeight, puzzleAnswers[i]);
        }
    }

    /**
     * Draws the game over screen with instructions.
     *
     * @return void
     */
    public void drawGameOverScreen() {
        String text = "GAME OVER";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64F));
        int x = getXforCenteredText(text);
        int y = gamePanel.screenHeight / 2 - gamePanel.tileSize * 2;
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        g2.setFont(arial_40);
        String instruction = "Press ENTER to CLOSE!";
        int x2 = getXforCenteredText(instruction);
        int y2 = gamePanel.screenHeight / 2;
        g2.drawString(instruction, x2, y2);
    }

    /**
     * Draws a single answer button for the puzzle screen.
     *
     * @param x The X-coordinate of the button
     * @param y The Y-coordinate of the button
     * @param w The width of the button.
     * @param h The height of the button.
     * @param text The answer text to display on the button.
     * @return void
     */
    public void drawAnswerButton(int x, int y, int w, int h, String text) {
        g2.setColor(Color.white);
        g2.fillRoundRect(x, y, w, h, 10, 10);
        g2.setColor(Color.black);
        g2.drawRoundRect(x, y, w, h, 10, 10);
        int textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int textHeight = (int) g2.getFontMetrics().getAscent();
        int textX = x + (w - textWidth) / 2;
        int textY = y + (h + textHeight) / 2;
        g2.drawString(text, textX, textY);
    }

    /**
     * Draws a semi transparent dialogue window 
     *
     * @param x The X-coordinate of the window.
     * @param y The Y-coordinate of the window.
     * @param width The width of the window.
     * @param height The height of the window.
     * @return void
     */
    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new java.awt.BasicStroke(5));
        g2.drawRoundRect(x, y, width, height, 35, 35);
    }

    /**
     * Returns the X-coordinate to center the given text
     *
     * @param text The text to be centered
     * @return int The X-coordinate to start drawing the text so it is centered Horizontally
     */
    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gamePanel.screenWidth / 2 - length / 2;
    }

    /**
     * Determines which puzzle answer button (if any) was clicked.
     *
     * @param mouseX The X-coordinate of the mouse click
     * @param mouseY The Y-coordinate of the mouse click
     * @return int The index of the clicked answer button
     */
    public int getClickedAnswerButton(int mouseX, int mouseY) {
        if (gamePanel.gameState != gamePanel.puzzleState) return -1;

        for (int i = 0; i < puzzleAnswers.length; i++) {
            if (buttonX[i] == 0 || buttonY[i] == 0) continue;

            int bx = buttonX[i];
            int by = buttonY[i];

            if (mouseX >= bx && mouseX <= bx + buttonWidth &&
                mouseY >= by && mouseY <= by + buttonHeight) {
                System.out.println("Button clicked: " + i + " (" + puzzleAnswers[i] + ")");
                return i;
            }
        }

        System.out.println("No button clicked. MouseX: " + mouseX + ", MouseY: " + mouseY);
        return -1;
    }
}
