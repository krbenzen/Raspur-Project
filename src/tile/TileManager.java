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
 * The TileManager class is responsible for:
 * - Loading tile images and storing them in a Tile array
 * - Loading and parsing a map file (notepad based) that determines which tiles appear at each coordinate.
 * - Drawing the visible portion of the game world onto the screen based on the player's position
 *
 * ISA: TileManager is a helper/manager class that supports the main GamePanel.
 * HAS-A: 
 *   - TileManager HAS-A reference to GamePanel
 *   - TileManager HAS-A Tile array to store tile data
 *   - TileManager HAS-A 2D array (mapTileNum) holding tiles for the map
 *
 * Learning Outcomes (LOs):
 * LO2. Arrays:
 *    - Demonstrates single dimensional array (Tile[]) and two-dimensional arrays (mapTileNum[][])
 * LO3. Objects and classes in OOP:
 *    - Uses Tile objects and interacts with GamePanel, Utility classes, etc.
 * LO6. GUI and event-driven programming:
 *    - Integrated into GUI (tiles drawn in GamePanel), though not event-driven itself.
 * LO7. Exception handling:
 *    - Catches IOException when loading images and map.
 * LO8. Text file I/O:
 *    - Demonstrates reading a map from a text file and parsing the data.
 */

package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import maingame.GamePanel;
import maingame.Utility;

public class TileManager {
	 // A reference to the main GamePanel, giving access to tile sizes and player position. 
    GamePanel gp;
    // An array of Tile objects
    public Tile[] tile;
    // A 2D array holding the indices of tiles for every coordinate in the game world
    public int[][] mapTileNum;

    /**
     * Constructs the TileManager, initializes tile arrays and loads tiles and map data.
     *
     * @param gp The GamePanel instance providing context
     */
    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[50];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/map/map1.txt");
    }

    /**
     * Loads each tile image and configures its collision
     * This method sets up various tiles and their images.
     *
     * @return void
     */
    public void getTileImage() {
    	
        // Using setup to create and store tile graphics with collision info
        setup(0, "floorboard1", false);
        setup(1, "backpack", false);
        setup(2, "concrete", false);
        setup(3, "grass", false);
        setup(4, "log", true);
        setup(5, "road", false);
        setup(6, "roadbot", false);
        setup(7, "roadtop", false);
        setup(8, "tree", true);
        setup(9, "wood", false);
        setup(10, "1bed", false);
        setup(11, "2bed", false);
        setup(12, "pencil", false);
        setup(13, "rock", true);
        setup(14, "stone", false);
        setup(15, "paper", false);
        setup(16, "carpet", false);
        setup(17, "water", true);
        setup(18, "sand", false);
        setup(19, "sandrock", true);
        setup(20, "tree2", true);
        setup(21, "coconut", true);
        setup(22, "snow", false);
        setup(23, "tree3", true);
        setup(24, "ice", false);
        setup(25, "redcarpet", false);
        setup(26, "redbed1", true);
        setup(27, "redbed2", true);
        setup(28, "volcanograss", false);
        setup(29, "tree4", true);
        setup(30, "lava", true);
        setup(31, "volcano", true);
        setup(32, "volcanofloor", false);
        setup(33, "water2", false);
        setup(34, "kelp", true);
        setup(35, "coral1", true);
        setup(36, "coral2", true);
        setup(37, "bubble", false);
        setup(38, "coral4", true);
        setup(39, "coral3", true);
        setup(40, "kelp2", true);
}

    /**
     * Sets up a single tile by loading its image and applying scaling and collision.
     *
     * @param index The tile's index in the tile array
     * @param imagePath The name of the image file located in /tiles/ directory
     * @param collision Whether the tile is solid and blocks entity movement
     * @return void
     */
    public void setup(int index, String imagePath, boolean collision) {
        Utility uTool = new Utility();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a map from a text file, storing tile indices in mapTileNum array.
     *
     * @param filePath The path to the map file
     * @return void
     */
    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                if (line == null) break; // In case of shorter map files

                String[] numbers = line.split(" ");
                while (col < gp.maxWorldCol && col < numbers.length) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }

                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the portion of the map visible to the player onto the screen
     * Only tiles within the player's view range are drawn
     *
     * @param g2 The Graphics2D object used for rendering.
     * @return void
     */
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        // Go through each tile in the world
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;

            // Calculate on screen position relative to player's position
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Draw only if within visible range 
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;

            // Move to next row after finishing a row of tiles
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
