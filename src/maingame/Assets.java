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
 * Version/Date: 12/15/2024
 *
 * Description:
 * This class, Assets is responsible for initializing and placing various objects 
 *(and non-player characters (NPCs) into the game world. It sets their 
 * world coordinates and provides them with puzzle questions and multiple choice answers.
 *
 * ISA: Assets is a helper class that sets up objects and NPCs within the game
 * HAS-A: 
 *   - Assets HAS-A reference to GamePanel, allowing it to place objects and NPCs
 *
 * Learning Outcomes (LOs):
 * LO1. OOP Design Principles:
 *    - Encapsulation by referencing GamePanel to position objects and NPCs.
 *    - Clear responsibilities: Assets class focuses on setting objects and NPCs.
 * LO2. Arrays:
 *    - Demonstrates array usage with gamePanel.obj[] and gamePanel.npc[].
 * LO3. Objects and Classes:
 *    - Creates instances of PaperObject and various NPC subclasses
 * LO4. Inheritance/Polymorphism:
 *    - NPCs are instances of classes that extend a common Entity superclass
 */

package maingame;

import entity.BeachBirdStudent;
import entity.BeachBunnyStudent;
import entity.BeachDogTeacher;
import entity.BeachFishStudent;
import entity.DogTeacherBeginning;
import entity.IslandBirdStudent;
import entity.IslandBunnyStudent;
import entity.IslandDogTeacher;
import entity.IslandFishStudent;
import entity.MomEnd;
import entity.MomNPC;
import entity.TundraBirdStudent;
import entity.TundraBunnyStudent;
import entity.TundraDogTeacher;
import entity.TundraFishStudent;
import entity.WaterBirdStudent;
import entity.WaterBunnyStudent;
import entity.WaterDogTeacher;
import entity.WaterFishStudent;
import object.PaperObject;
import object.PencilObject;

public class Assets {
    // A reference to the GamePanel, which provides context like tileSize and arrays for NPCs and objects. 
    GamePanel gamePanel;

    /**
     * Constructs an Assets object
     *
     * @param gamePanel The GamePanel that provides arrays and details for placing objects and npcs
     */
    public Assets(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Sets the positions of objects (like PaperObjects) in the game world
     * Positions are given in terms of worldX and worldY, calculated using tileSize
     *
     * @return void
     */
    public void setObject() {
        // LEVEL 1 PAPERS
        // @return void - Sets the position of objects in the gamePanel's obj array
        // @param None - Uses the gamePanel reference for positioning
        gamePanel.obj[1] = new PaperObject(gamePanel);
        gamePanel.obj[1].worldX = 102 * gamePanel.tileSize;
        gamePanel.obj[1].worldY = 11 * gamePanel.tileSize;

        gamePanel.obj[2] = new PaperObject(gamePanel);
        gamePanel.obj[2].worldX = 149 * gamePanel.tileSize;
        gamePanel.obj[2].worldY = 48 * gamePanel.tileSize;

        gamePanel.obj[3] = new PaperObject(gamePanel);
        gamePanel.obj[3].worldX = 149 * gamePanel.tileSize;
        gamePanel.obj[3].worldY = 55 * gamePanel.tileSize;

        gamePanel.obj[4] = new PaperObject(gamePanel);
        gamePanel.obj[4].worldX = 149 * gamePanel.tileSize;
        gamePanel.obj[4].worldY = 63 * gamePanel.tileSize;

        gamePanel.obj[5] = new PaperObject(gamePanel);
        gamePanel.obj[5].worldX = 149 * gamePanel.tileSize;
        gamePanel.obj[5].worldY = 72 * gamePanel.tileSize;

        gamePanel.obj[18] = new PaperObject(gamePanel);
        gamePanel.obj[18].worldX = 108 * gamePanel.tileSize;
        gamePanel.obj[18].worldY = 11 * gamePanel.tileSize;

        // LEVEL 2 PAPERS
        gamePanel.obj[6] = new PaperObject(gamePanel);
        gamePanel.obj[6].worldX = 223 * gamePanel.tileSize;
        gamePanel.obj[6].worldY = 121 * gamePanel.tileSize;

        gamePanel.obj[7] = new PaperObject(gamePanel);
        gamePanel.obj[7].worldX = 223 * gamePanel.tileSize;
        gamePanel.obj[7].worldY = 127 * gamePanel.tileSize;

        gamePanel.obj[8] = new PaperObject(gamePanel);
        gamePanel.obj[8].worldX = 223 * gamePanel.tileSize;
        gamePanel.obj[8].worldY = 133 * gamePanel.tileSize;

        gamePanel.obj[9] = new PaperObject(gamePanel);
        gamePanel.obj[9].worldX = 223 * gamePanel.tileSize;
        gamePanel.obj[9].worldY = 140 * gamePanel.tileSize;

        // LEVEL 3 PAPERS
        gamePanel.obj[10] = new PaperObject(gamePanel);
        gamePanel.obj[10].worldX = 183 * gamePanel.tileSize;
        gamePanel.obj[10].worldY = 289 * gamePanel.tileSize;

        gamePanel.obj[11] = new PaperObject(gamePanel);
        gamePanel.obj[11].worldX = 183 * gamePanel.tileSize;
        gamePanel.obj[11].worldY = 295 * gamePanel.tileSize;

        gamePanel.obj[12] = new PaperObject(gamePanel);
        gamePanel.obj[12].worldX = 183 * gamePanel.tileSize;
        gamePanel.obj[12].worldY = 301 * gamePanel.tileSize;

        gamePanel.obj[13] = new PaperObject(gamePanel);
        gamePanel.obj[13].worldX = 183 * gamePanel.tileSize;
        gamePanel.obj[13].worldY = 308 * gamePanel.tileSize;

        // LEVEL 4 PAPERS
        gamePanel.obj[14] = new PaperObject(gamePanel);
        gamePanel.obj[14].worldX = 293 * gamePanel.tileSize;
        gamePanel.obj[14].worldY = 355 * gamePanel.tileSize;

        gamePanel.obj[15] = new PaperObject(gamePanel);
        gamePanel.obj[15].worldX = 299 * gamePanel.tileSize;
        gamePanel.obj[15].worldY = 355 * gamePanel.tileSize;

        gamePanel.obj[16] = new PaperObject(gamePanel);
        gamePanel.obj[16].worldX = 305 * gamePanel.tileSize;
        gamePanel.obj[16].worldY = 355 * gamePanel.tileSize;

        gamePanel.obj[17] = new PaperObject(gamePanel);
        gamePanel.obj[17].worldX = 312 * gamePanel.tileSize;
        gamePanel.obj[17].worldY = 355 * gamePanel.tileSize;
    }

    /**
     * Sets the positions and parameters of various NPCs in the game world
     * Each NPC is assigned a puzzle and placed at a coordinate
     *
     * @return void
     */
    public void setNPC() {
        // PLAINS
        // @param None - Uses gamePanel to access the npc array and tileSize
        // @return void - Just assigns NPCs to positions and sets their dialogues and puzzles
        gamePanel.npc[0] = new MomNPC(gamePanel, "Tell me son... \nWhat is 2 + 2?",
                new String[]{"4", "2", "1", "3"}, 0);
        gamePanel.npc[0].worldX = gamePanel.tileSize * 18;
        gamePanel.npc[0].worldY = gamePanel.tileSize * 11;

        gamePanel.npc[18] = new DogTeacherBeginning(gamePanel, "What is 77 + 33?",
                new String[]{"100", "110", "99", "1"}, 1);
        gamePanel.npc[18].worldX = gamePanel.tileSize * 105;
        gamePanel.npc[18].worldY = gamePanel.tileSize * 11;

        // BEACHES
        gamePanel.npc[1] = new BeachBunnyStudent(gamePanel, "What is 22 + 17?",
                new String[]{"29", "37", "42", "39"}, 3);
        gamePanel.npc[1].worldX = gamePanel.tileSize * 101;
        gamePanel.npc[1].worldY = gamePanel.tileSize * 56;

        gamePanel.npc[2] = new BeachBirdStudent(gamePanel, "What is 6 + 6?",
                new String[]{"13", "11", "14", "12"}, 3);
        gamePanel.npc[2].worldX = gamePanel.tileSize * 116;
        gamePanel.npc[2].worldY = gamePanel.tileSize * 16;

        gamePanel.npc[3] = new BeachFishStudent(gamePanel, "Blub.. 9 + 10?",
                new String[]{"19", "91", "18", "21"}, 0);
        gamePanel.npc[3].worldX = gamePanel.tileSize * 162;
        gamePanel.npc[3].worldY = gamePanel.tileSize * 21;

        gamePanel.npc[4] = new BeachDogTeacher(gamePanel, "What is 65 + 29?",
                new String[]{"84", "94", "95", "92"}, 1);
        gamePanel.npc[4].worldX = gamePanel.tileSize * 149;
        gamePanel.npc[4].worldY = gamePanel.tileSize * 69;

        // TUNDRA
        gamePanel.npc[5] = new TundraBunnyStudent(gamePanel, "What is 42 - 15?",
                new String[]{"30", "25", "27", "31"}, 2);
        gamePanel.npc[5].worldX = gamePanel.tileSize * 269;
        gamePanel.npc[5].worldY = gamePanel.tileSize * 51;

        gamePanel.npc[6] = new TundraBirdStudent(gamePanel, "What is 89 - 56?",
                new String[]{"33", "35", "36", "34"}, 0);
        gamePanel.npc[6].worldX = gamePanel.tileSize * 223;
        gamePanel.npc[6].worldY = gamePanel.tileSize * 69;

        gamePanel.npc[7] = new TundraFishStudent(gamePanel, "Blub.. 123 - 78?",
                new String[]{"47", "44", "43", "45"}, 3);
        gamePanel.npc[7].worldX = gamePanel.tileSize * 222;
        gamePanel.npc[7].worldY = gamePanel.tileSize * 99;

        gamePanel.npc[8] = new TundraDogTeacher(gamePanel, "What is 76 - 48?",
                new String[]{"28", "27", "30", "29"}, 0);
        gamePanel.npc[8].worldX = gamePanel.tileSize * 223;
        gamePanel.npc[8].worldY = gamePanel.tileSize * 137;

        // ISLAND
        gamePanel.npc[9] = new IslandBunnyStudent(gamePanel, "What is 8 x 12?",
                new String[]{"86", "92", "96", "88"}, 2);
        gamePanel.npc[9].worldX = gamePanel.tileSize * 227;
        gamePanel.npc[9].worldY = gamePanel.tileSize * 236;

        gamePanel.npc[10] = new IslandBirdStudent(gamePanel, "What is 7 x 13?",
                new String[]{"91", "89", "93", "97"}, 0);
        gamePanel.npc[10].worldX = gamePanel.tileSize * 119;
        gamePanel.npc[10].worldY = gamePanel.tileSize * 224;

        gamePanel.npc[11] = new IslandFishStudent(gamePanel, "Blub.. 6 x 15?",
                new String[]{"80", "90", "84", "88"}, 1);
        gamePanel.npc[11].worldX = gamePanel.tileSize * 175;
        gamePanel.npc[11].worldY = gamePanel.tileSize * 205;

        gamePanel.npc[12] = new IslandDogTeacher(gamePanel, "What is 9 x 11?",
                new String[]{"97", "95", "98", "99"}, 3);
        gamePanel.npc[12].worldX = gamePanel.tileSize * 183;
        gamePanel.npc[12].worldY = gamePanel.tileSize * 305;

        // WATER
        gamePanel.npc[13] = new WaterBunnyStudent(gamePanel, "What is 96 / 8?",
                new String[]{"14", "12", "10", "16"}, 1);
        gamePanel.npc[13].worldX = gamePanel.tileSize * 250;
        gamePanel.npc[13].worldY = gamePanel.tileSize * 382;

        gamePanel.npc[14] = new WaterBirdStudent(gamePanel, "What is 144 / 12?",
                new String[]{"12", "11", "10", "13"}, 0);
        gamePanel.npc[14].worldX = gamePanel.tileSize * 175;
        gamePanel.npc[14].worldY = gamePanel.tileSize * 373;

        gamePanel.npc[15] = new WaterFishStudent(gamePanel, "Blub.. 130 / 5?",
                new String[]{"30", "24", "28", "26"}, 3);
        gamePanel.npc[15].worldX = gamePanel.tileSize * 110;
        gamePanel.npc[15].worldY = gamePanel.tileSize * 343;

        gamePanel.npc[16] = new WaterDogTeacher(gamePanel, "Derivative of (1/5)x",
                new String[]{"1/5", "x", "5/1", "x2/10"}, 1);
        gamePanel.npc[16].worldX = gamePanel.tileSize * 308;
        gamePanel.npc[16].worldY = gamePanel.tileSize * 355;

        // END
        gamePanel.npc[17] = new MomEnd(gamePanel, "What's the meaning of life?",
                new String[]{"1", "2", "3", "4"}, 10);
        gamePanel.npc[17].worldX = gamePanel.tileSize * 380;
        gamePanel.npc[17].worldY = gamePanel.tileSize * 279;
    }
}
