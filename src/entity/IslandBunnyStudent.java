/**
 * Lead Author(s):
 *   @author Benzen Raspur
 *
 * Other contributors:
 *   None
 *
 * References:
 *   - Morelli, R., & Walde, R. (2016). Java, Java, Java: Object-Oriented Problem Solving.
 *     https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *     
 *   - Bechtold, S., Brannen, S., Link, J., Merdes, M., Philipp, M., Rancourt, J. D., & Stein, C. (n.d.).
 *     JUnit 5 user guide. JUnit 5.
 *     https://junit.org/junit5/docs/current/user-guide/
 *
 * Version/Date: 12/16/2024
 *
 * Description:
 * This class represents a IslandBunnyStudent NPC within the island portion of the map. It extends the 
 * Entity class, adding a question and multiple-choice answers. Once the player reaches a point,
 * this NPC will prompt player with a question.
 *
 * ISA: IslandBunnyStudent IS-A Entity
 * HASA: IslandBunnyStudent has-a String question
 * HASA Array of String answers
 * HASA int correctAnswer
 */

package entity;

import maingame.GamePanel;

public class IslandBunnyStudent extends Entity {
    
    /** The question this NPC will ask. */
    private String question;
    
    
    /** 
     * Constructs a new IslandBunnyStudent NPC.
     *
     * @param gamePanel      GamePanel instance.
     * @param question       Question to be asked by this NPC.
     * @param answers        Possible answers to the question.
     * @param correctAnswer  Index of the correct answer 
     * @throws IllegalArgumentException if answer is wrong for debugging.
     */
    public IslandBunnyStudent(GamePanel gamePanel, String question, String[] answers, int correctAnswer) {
        super(gamePanel);
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;

        // Validate the answer set and correct answer
        if (answers == null || correctAnswer < 0 || correctAnswer >= answers.length) {
            throw new IllegalArgumentException("Invalid answers array or correct answer index");
        }

        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }

    /**
     * Loads and sets images for this NPC.
     */
    public void getImage() {
        up1 = setup("/npc/BunnyStudentNPC/1");
        up2 = setup("/npc/BunnyStudentNPC/2");
        down1 = setup("/npc/BunnyStudentNPC/1");
        down2 = setup("/npc/BunnyStudentNPC/2");
        left1 = setup("/npc/BunnyStudentNPC/1");
        left2 = setup("/npc/BunnyStudentNPC/2");
        right1 = setup("/npc/BunnyStudentNPC/1");
        right2 = setup("/npc/BunnyStudentNPC/2");
    }

    /**
     * Sets the dialogue lines.
     */
    public void setDialogue() {
        dialogues[0] = "Phew, it's hot here!\nBut I can handle it.\nNothing stops a bunny!";
        dialogues[1] = "This island is full\nof bubbling lava and\nsmoldering rocks.";
        dialogues[2] = "I love hopping around\nthe edges of the volcano.\nIt’s a thrill.";
        dialogues[3] = "But be careful—\none wrong step\nand you're toast.";
        dialogues[4] = "Think you can handle\nthe heat and\nanswer my question?";
        dialogues[5] = "If you get it right,\nI'll let you pass.\nMaybe...";
        dialogues[6] = "Let's see if you're\nsmarter than a bunny!";
        dialogues[7] = "Alright, here we go.\nHOP! HOP!";
        dialogues[8] = question;
    }


    /**
     * Sets the random movement action for this NPC. This method is called upon through RNG
     * to give them human-like movement.
     */
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            int randomValue = new java.util.Random().nextInt(100);
            if (randomValue < 25) {
                direction = "up";
            } else if (randomValue < 50) {
                direction = "down";
            } else if (randomValue < 75) {
                direction = "left";
            } else {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    /**
     * Interaction with the player. If the puzzle is completed, it shows the final message.
     */
    @Override
    public void speak() {
        if (puzzleCompleted) {
            // If the puzzle is completed, show a final message and return to playState.
            gamePanel.ui.showMessage("Don't get too cocky.");
            gamePanel.currentNPCIndex = -1;
            gamePanel.gameState = gamePanel.playState;
            return;
        }

        if (dialogueIndex == 8) {
            gamePanel.ui.currentDialogue = question; 
            gamePanel.ui.setPuzzleAnswers(answers, correctAnswer);
            gamePanel.gameState = gamePanel.puzzleState; 
        } else {
            super.speak();
        }
    }
}
