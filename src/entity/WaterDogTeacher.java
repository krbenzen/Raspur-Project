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
 * This class represents a WaterDogTeacher NPC within the Water portion of the map. It extends the
 * Entity class, adding a question and multiple-choice answers. Once the player reaches a certain index,
 * this NPC will prompt player with a question.
 *
 * ISA: WaterDogTeacher IS-A Entity
 * HASA: WaterDogTeacher has-a String question
 * HASA Array of String answers
 * HASA int correctAnswer
 */

package entity;

import maingame.GamePanel;

public class WaterDogTeacher extends Entity {
    
    /** The question this NPC will ask. */
    private String question;
    

    
    /** 
     * Constructs a new WaterDogTeacher NPC.
     *
     * @param gamePanel      GamePanel instance.
     * @param question       Question to be asked by this NPC.
     * @param answers        Possible answers to the question.
     * @param correctAnswer  Index of the correct answer.
     * @throws IllegalArgumentException if the answers array is null or the correctAnswer index is invalid.
     */
    public WaterDogTeacher(GamePanel gamePanel, String question, String[] answers, int correctAnswer) {
        super(gamePanel);
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;

        // Validate the answer set and correct answer index
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
        dialogues[0] = "So, you’ve made it\nthis far, cat.";
        dialogues[1] = "I didn’t think you’d\nsurvive the tundra,\nthe volcano, or even\nthe surface waters.";
        dialogues[2] = "But here you are,\nin the deepest, darkest\npart of the ocean.";
        dialogues[3] = "Let me make this\nvery clear: This is where\nyour journey ends.";
        dialogues[4] = "No cat has ever\npassed my final test.\nAnd you won’t be\nthe first.";
        dialogues[5] = "Do you feel the\ncrushing weight of the ocean?\nThat’s nothing compared\nto the pressure to succeed.";
        dialogues[6] = "You don’t belong here.\nYou’ve never belonged.\nThis is my domain.";
        dialogues[7] = "But fine. If you want\nto humiliate yourself,\nlet’s do this.";
        dialogues[8] = "Answer this question,\nand maybe, just maybe,\nI’ll let you crawl out of\nhere.";
        dialogues[9] = "But I warn you...\nFailure isn’t just expected.\nIt’s inevitable. BARK!";
        dialogues[10] = question;
    }



    /**
     * Sets the random movement action for this NPC.
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
            gamePanel.ui.showMessage("Congratulations. Cat...");
            gamePanel.currentNPCIndex = -1;
            gamePanel.gameState = gamePanel.playState;
            return;
        }

        if (dialogueIndex == 10) {
            gamePanel.ui.currentDialogue = question;
            gamePanel.ui.setPuzzleAnswers(answers, correctAnswer);
            gamePanel.gameState = gamePanel.puzzleState;
        } else {
            super.speak();
        }
    }
}
