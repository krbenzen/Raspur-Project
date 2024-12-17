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
 * Version/Date: 12/15/2024
 *
 * Description:
 * This class represents a Beach Bird Student NPC within the beach portion of the map. It extends the 
 * Entity class, adding a question and multiple-choice answers. Once the player reaches a point,
 * this NPC will prompt player with a question.
 *
 * ISA: BeachBirdStudent IS-A Entity
 * HASA: BeachBirdStudent has-a String question
 * HASA Array of String answers
 * HASA int correctAnswer
 */

package entity;

import maingame.GamePanel;

public class BeachBirdStudent extends Entity {
    
    /** The question this NPC will ask. */
    private String question;
    
    /** The set of possible answers to the question. */
   
    
    /** 
     * Constructs a new BeachBirdStudent NPC.
     *
     * @param gamePanel      GamePanel instance.
     * @param question       Question to be asked by this NPC.
     * @param answers        Possible answers to the question.
     * @param correctAnswer  Index of the correct answer 
     * @throws IllegalArgumentException if answer is wrong for debugging.
     */
    public BeachBirdStudent(GamePanel gamePanel, String question, String[] answers, int correctAnswer) {
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
        up1 = setup("/npc/BirdStudentNPC/1");
        up2 = setup("/npc/BirdStudentNPC/2");
        down1 = setup("/npc/BirdStudentNPC/1");
        down2 = setup("/npc/BirdStudentNPC/2");
        left1 = setup("/npc/BirdStudentNPC/1");
        left2 = setup("/npc/BirdStudentNPC/2");
        right1 = setup("/npc/BirdStudentNPC/1");
        right2 = setup("/npc/BirdStudentNPC/2");
    }

    /**
     * Sets the dialogue lines.
     */
    public void setDialogue() {
        dialogues[0] = "Oh hey man!\nThe name's Bill...";
        dialogues[1] = "Well as you can\ntell, I'm kind of\ntrapped here.";
        dialogues[2] = "It's fine though,\nit's relaxing in this\nconfined space.";
        dialogues[3] = "The weather's nice,\nthere's shade here,\nand delicious coconuts!";
        dialogues[4] = "Anyways, if you weren't\nlistening to the\nteacher...";
        dialogues[5] = "I'm supposed to fight\nyou apparently?";
        dialogues[6] = "I was given one\npencil and I'm\nsupposed to protect it.";
        dialogues[7] = "So here I come! HONK!";
        dialogues[8] = question; 
    }

    /**
     * Sets the random movement action for this NPC. This method is called upon through RNG
     * to give them human-like movement
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
     * Interaction with the player. If the puzzle is completed, it shows the final message
     */
    @Override
    public void speak() {
        if (puzzleCompleted) {
            // If the puzzle is completed, show a final message and return to playState
            gamePanel.ui.showMessage("Well played, HONK!");
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
