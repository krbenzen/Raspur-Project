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
 * This class represents the player's MomEnd in the game. The final exit for the player's story. Unwinnable on purpose
 *
 * ISA: MomEnd IS-A Entity
 * HASA: MomEnd has-a String question
 * HASA Array of String answers
 * HASA int correctAnswer
 */

package entity;

import maingame.GamePanel;

public class MomEnd extends Entity {
    
    /** The question this NPC will ask. */
    private String question;
    
    /** 
     * Constructs a new MomEnd instance.
     *
     * @param gamePanel      GamePanel instance.
     * @param question       Question to be asked by this NPC.
     * @param answers        Possible answers to the question.
     * @param correctAnswer  Index of the correct answer.
     * @throws IllegalArgumentException if the answers array is null or the correctAnswer index is invalid.
     */
    public MomEnd(GamePanel gamePanel, String question, String[] answers, int correctAnswer) {
        super(gamePanel);
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;

    

        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }

    /**
     * Load and sets Mom images.
     */
    public void getImage() {
        up1 = setup("/MomNPC/up1");
        up2 = setup("/MomNPC/up2");
        down1 = setup("/MomNPC/down1");
        down2 = setup("/MomNPC/down2");
        left1 = setup("/MomNPC/left1");
        left2 = setup("/MomNPC/left2");
        right1 = setup("/MomNPC/right1");
        right2 = setup("/MomNPC/right2");
    }

    /**
     * Sets the dialogue lines for this NPC.
     */
    public void setDialogue() {
        dialogues[0] = "Oh son, you did it!\nYou graduated and now we\nget to live better\nlives!";
        dialogues[1] = "Now let me be your\ntrue final test";
        dialogues[2] = question; 

    }

    /**
     * Sets the random movement action.
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
     * Interaction with the player. If the puzzle is completed, it shows the final message and
     * allows the player get the pencil.
     */
    @Override
    public void speak() {
        if (puzzleCompleted) {
            // If the puzzle is completed, show a final message and return to playState.
            gamePanel.ui.showMessage("Good luck, son!");
            gamePanel.currentNPCIndex = -1;
            gamePanel.gameState = gamePanel.playState;
            return;
        }

        if (dialogueIndex == 2) {
            System.out.println("NPC is asking a question: " + question);
            gamePanel.ui.currentDialogue = question;
            gamePanel.ui.setPuzzleAnswers(answers, correctAnswer);
            gamePanel.gameState = gamePanel.puzzleState;
        } else {
            super.speak();
        }
    }
}
