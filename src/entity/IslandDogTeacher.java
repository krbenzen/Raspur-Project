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
 * This class represents a IslandDogTeacher NPC within the island portion of the map. It extends the 
 * Entity class, adding a question and multiple-choice answers. Once the player reaches a point,
 * this NPC will prompt player with a question.
 *
 * ISA: IslandDogTeacher IS-A Entity
 * HASA: IslandDogTeacher has-a String question
 * HASA Array of String answers
 * HASA int correctAnswer
 */

package entity;

import maingame.GamePanel;

public class IslandDogTeacher extends Entity {
    
    /** The question this NPC will ask. */
    private String question;
    
    
    /** 
     * Constructs a new IslandDogTeacher NPC.
     *
     * @param gamePanel      GamePanel instance.
     * @param question       Question to be asked by this NPC.
     * @param answers        Possible answers to the question.
     * @param correctAnswer  Index of the correct answer.
     * @throws IllegalArgumentException if the answers array is null or the correctAnswer index is invalid.
     */
    public IslandDogTeacher(GamePanel gamePanel, String question, String[] answers, int correctAnswer) {
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
        dialogues[0] = "Oh great, it’s you again.\nWhat are you doing\non this island?";
        dialogues[1] = "Cats don’t belong here.\nThis place is too tough\nfor your kind.";
        dialogues[2] = "See all this lava?\nOne misstep and you’re\ndone. Just like your\nchances of graduating.";
        dialogues[3] = "I don’t know why they\nlet a cat like you\ntry to make it this far.";
        dialogues[4] = "Let me be clear:\nYou won’t survive\nthis challenge.";
        dialogues[5] = "If you think you have\nwhat it takes, prove it.\nAnswer my question!";
        dialogues[6] = "I’m going to make sure\nyou never graduate,\nlittle furball.";
        dialogues[7] = "Get ready to fail.\nYou don’t stand\na chance!";
        dialogues[8] = "BARK! Let’s see if\nyou can handle\nthis question.";
        dialogues[9] = question;
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
            gamePanel.ui.showMessage("Okay then...");
            gamePanel.currentNPCIndex = -1;
            gamePanel.gameState = gamePanel.playState;
            return;
        }

        if (dialogueIndex == 9) {
            gamePanel.ui.currentDialogue = question;
            gamePanel.ui.setPuzzleAnswers(answers, correctAnswer);
            gamePanel.gameState = gamePanel.puzzleState;
        } else {
            super.speak();
        }
    }
}
