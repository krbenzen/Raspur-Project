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
 * This class represents a DogTeacherBeginning NPC within the beach portion of the map. It extends the 
 * Entity class, adding a question and multiple-choice answers and will instruct the player for the beginning tutorial
 *
 * ISA: DogTeacherBeginning IS-A Entity
 * HASA: DogTeacherBeginning has-a String question
 * HASA Array of String answers
 * HASA int correctAnswer
 */

package entity;

import maingame.GamePanel;

public class DogTeacherBeginning extends Entity {
    
    /** The question this NPC will ask. */
    private String question;
    
    
    /** 
     * Constructs a new DogTeacherBeginning NPC.
     *
     * @param gamePanel      GamePanel instance.
     * @param question       Question to be asked by this NPC.
     * @param answers        Possible answers to the question.
     * @param correctAnswer  Index of the correct answer.
     * @throws IllegalArgumentException if the answers array is null or the correctAnswer index is invalid.
     */
    public DogTeacherBeginning(GamePanel gamePanel, String question, String[] answers, int correctAnswer) {
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
        up1 = setup("/npc/DogTeacherNPC/1");
        up2 = setup("/npc/DogTeacherNPC/2");
        down1 = setup("/npc/DogTeacherNPC/1");
        down2 = setup("/npc/DogTeacherNPC/2");
        left1 = setup("/npc/DogTeacherNPC/1");
        left2 = setup("/npc/DogTeacherNPC/2");
        right1 = setup("/npc/DogTeacherNPC/1");
        right2 = setup("/npc/DogTeacherNPC/2");
    }

    /**
     * Sets the dialogue lines.
     */
    public void setDialogue() {
        dialogues[0] = "Welcome to the school. Cat.";
        dialogues[1] = "I'll keep it short and\nsimple due to your lack\nof intelligence,being that\nyou're a cat.";
        dialogues[2] = "I'm your teacher\n and around this map,\nyou'll have to fight.";
        dialogues[3] = "Thats right,\nother kids are all\nscattered and you have";
        dialogues[4] = "to find them in order\nto proceed.";
        dialogues[5] = "You'll have to\nchallenge them by\nanswering math questions.";
        dialogues[6] = "Pass me and you'll\nunderstand the gist...";
        dialogues[7] = "BARK BARK!";
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
            gamePanel.ui.showMessage("Don't expect easier...");
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
