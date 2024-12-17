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
 * This class represents the main game panel and is responsible for:
 * - Rendering the game world and its components: tiles, objects, NPCs, player
 * - Running the main game loop: updating and repainting the game state
 * - Managing various game states: play, pause, dialogue, puzzle, game over
 * - Music and sound effects
 * - Positioning the player, NPCs, and objects in the world
 *
 * ISA: GamePanel IS-A JPanel and IS-A Runnable (to run the game loop)
 * HAS-A:
 *   - TileManager to manage and draw tiles
 *   - KeyInputs to handle keyboard and mouse inputs
 *   - Music objects for background music and sound effects
 *   - Collisions to handle collision detection
 *   - Assets to set up NPCs and objects in the world
 *   - UI to display user interface elements 
 *   - Player object representing the player character
 *   - Arrays of SuperObject and Entity 
 *
 * Learning Outcomes (LOs):
 * LO1. Employ design principles of OOP:
 *    - Shows "has-a" and "is-a" relationships.
 * LO2. Single and multidimensional arrays:
 *    - Demonstrated by arrays such as the SuperObject[] obj and Entity[] npc.
 * LO3. Utilize objects and classes in OOP:
 *    - Multiple classes like player or tile interact within GamePanel.
 * LO4. Utilize inheritance and polymorphism:
 *    - GamePanel extends JPanel (inheritance).
 *    - NPCs extend Entity (polymorphism).
 * LO6. GUI and event-driven programming:
 *    - GamePanel is a GUI component (JPanel). KeyInputs handle events.
 * LO7. Exception handling:
 *    - Exceptions are caught and logged on console
 */

package maingame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // Screen settings
	// The original tile size before scaling. 
	public final int originalTileSize = 16;
    // The scale factor for resizing tiles
    public final int scale = 3;
    // The final tile size after scaling. 
    public final int tileSize = originalTileSize * scale; 
    
    // The maximum number of columns displayed on the screen. 
    public final int maxScreenCol = 12;
    // The maximum number of rows displayed on the screen. 
    public final int maxScreenRow = 16;
    // The width of the screen in pixels. 
    public final int screenWidth = tileSize * maxScreenCol; 
    // The height of the screen in pixels. 
    public final int screenHeight = tileSize * maxScreenRow; 

    // The maximum number of columns in the game world. 
    public final int maxWorldCol = 400;
    // The maximum number of rows in the game world. 
    public final int maxWorldRow = 400;
    // The total width of the world in pixels. 
    public final int worldWidth = tileSize * maxWorldCol;
    // The total height of the world in pixels. 
    public final int worldHeight = tileSize * maxWorldRow;

    // The target frames-per-second for the game loop. 
    public int FPS = 60;
    
    // Manages and draws all tiles in the game world. 
    public TileManager tileM = new TileManager(this);
    // Handles keyboard and mouse input events. 
    public KeyInputs keyH = new KeyInputs(this);
    // Plays background music. 
    public Music Music = new Music();
    // Plays sound effects. 
    public Music soundeffect = new Music();
    // The main game thread for the game loop. 
    public Thread gameThread;
    // Handles collision detection between entities and objects. 
    public Collisions colChecker = new Collisions(this);
    // Sets up objects and NPCs in the world. 
    public Assets aSetter = new Assets(this);
    // Manages and displays user interface elements. 
    public UI ui = new UI(this);
    // Represents the player character. 
    public Player player = new Player(this,keyH);
    // An array of objects (items) in the game world. 
    public SuperObject obj[] = new SuperObject[100];
    // An array of non-player characters also known as the (NPCs) in the game.
    public Entity npc[] = new Entity[100];

    // Game States
    // The current state of the game.
    public int gameState;
    // State representing 'play' mode. 
    public final int playState = 1;
    // State representing 'pause' mode. 
    public final int pauseState = 2;
    // State representing 'dialogue' mode. 
    public final int dialogueState = 3;
    // State representing 'puzzle' mode. 
    public final int puzzleState = 4;
    // State representing 'game over' mode. 
    public final int gameOverState = 5;

    // Tracks the current NPC index that the player is interacting with 
    public int currentNPCIndex = -1;

    /**
     * Constructs the GamePanel and sets panel properties such as size and
     * the background color
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.addMouseListener(keyH);
    }

    /**
     * Sets up the game by placing objects, NPCs, and player. 
     * Also starts background music and UI elements.
     */
    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        
        // Initialize player's position in the world 
        player.worldX = 11 * tileSize; 
        player.worldY = 10 * tileSize; 
        
        playMusic(0);
        
        gameState = playState;
        ui.playerHealth = 3; // Reset health
        ui.currentDialogue = "";
    }

    /**
     * Starts the main game thread which runs the game loop.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * The main game loop method. It updates the game state and repaints the screen 
     * at the target FPS. 
     *
     *  This uses a timing mechanism to ensure consistent updates 
     *  if FPS varies.
     */
    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            // Update and render once per frame interval
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            // Print FPS every second for debugging
            if (timer >= 1000000000) {
                System.out.println(drawCount + "FPS");
                drawCount = 0;
                timer = 0;
            }
        }
    }

    /**
     * Updates the game logic depending on the current game state.
     * In playState, the player and NPCs are updated.
     * In dialogueState, checks for interaction progression
     * Also checks if the player's health reaches zero and sets gameOverState 
     */
    public void update() {
        if (gameState == playState) {
            player.update();
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
        } else if (gameState == dialogueState) {
            if (keyH.enterPressed) {
                if (currentNPCIndex != -1 && npc[currentNPCIndex] != null) {
                    npc[currentNPCIndex].speak();
                }
                keyH.enterPressed = false;
            }
        } else if (gameState == puzzleState) {
            // Puzzle interactions may occur through mouse events in KeyInputs
        } else if (gameState == gameOverState) {
            // Waiting for user input to reset or exit
        } else if (gameState == pauseState) {
            // Paused state: no updates occur
        }

        // Check if player is out of health and set game over
        if (ui.playerHealth <= 0 && gameState != gameOverState) {
            gameState = gameOverState;
        }
    }

    /**
     * Paints all game elements onto the screen, including tiles, objects, NPCs, player, and UI.
     * This method is called automatically by the JVM and Swing
     *
     * @param g The Graphics object provided by the system to draw on the panel.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw tiles first as a background
        tileM.draw(g2);

        // Draw objects
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        // Draw NPCs
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].draw(g2);
            }
        }

        // Draw player
        player.draw(g2);

        // Draw UI on top
        ui.draw(g2);

        g2.dispose();
    }

    /**
     * Plays background music based on the given index. 
     * This sets the audio file, starts playback, and loops it continuously.
     *
     * @param i The index of the music track to play.
     */
    public void playMusic(int i) {
        try {
            Music.setFile(i);
            Music.play();
            Music.loop();
        } catch (Exception e) {
            System.err.println("Error playing music file index " + i + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Stops the currently playing background music.
     */
    public void stopMusic() {
        try {
            Music.stop();
        } catch (Exception e) {
            System.err.println("Error stopping music: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Plays a one-time sound effect based on the provided index.
     *
     * @param i The index of the sound effect to play.
     */
    public void playSoundEffect(int i) {
        try {
            soundeffect.setFile(i);
            soundeffect.play();
        } catch (Exception e) {
            System.err.println("Error playing sound effect file index " + i + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
