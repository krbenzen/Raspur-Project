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
 * This class represents a WaterFishStudent NPC within the Water portion of the map. It extends the
 * Entity class, adding a question and multiple-choice answers. Once the player reaches a certain index,
 * this NPC will prompt player with a question.
 *
 * ISA: WaterFishStudent IS-A Entity
 * HASA: WaterFishStudent has-a String question
 * HASA Array of String answers
 * HASA int correctAnswer
 */

package entity;

import maingame.GamePanel;

public class WaterFishStudent extends Entity {
    
    /** The question this NPC will ask. */
    private String question;
    
    
    /** 
     * Constructs a new WaterFishStudent NPC.
     *
     * @param gamePanel      GamePanel instance.
     * @param question       Question to be asked by this NPC.
     * @param answers        Possible answers to the question.
     * @param correctAnswer  Index of the correct answer.
     * @throws IllegalArgumentException if the answers array is null or the correctAnswer index is invalid.
     */
    public WaterFishStudent(GamePanel gamePanel, String question, String[] answers, int correctAnswer) {
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
        up1 = setup("/npc/FishStudentNPC/1");
        up2 = setup("/npc/FishStudentNPC/2");
        down1 = setup("/npc/FishStudentNPC/1");
        down2 = setup("/npc/FishStudentNPC/2");
        left1 = setup("/npc/FishStudentNPC/1");
        left2 = setup("/npc/FishStudentNPC/2");
        right1 = setup("/npc/FishStudentNPC/1");
        right2 = setup("/npc/FishStudentNPC/2");
    }

    /**
     * Sets the dialogue lines.
     */
    public void setDialogue() {
        dialogues[0] = "Oh, so you’ve found me.\nBet you didn’t expect\nme fish to talk,\ndid you?";
        dialogues[1] = "Blub this, blub that...\nI’ve been pretending all\nalong.";
        dialogues[2] = "Down here in the deep,\nit’s survive or be eaten.\nI’ve seen things that\nwould break you.";
        dialogues[3] = "You think you’re special?\nYou’re just another visitor\nabout to become chum.";
        dialogues[4] = "The pressure here is real,\nand I don’t just mean\nthe water around you.";
        dialogues[5] = "But hey, I’ll give you a shot.\nAnswer my question, and\nmaybe I won’t laugh at you.";
        dialogues[6] = "Be warned, though:\nI don’t do easy. If you mess up,\nI’ll make sure this ocean\nswallows you whole.";
        dialogues[7] = "Alright, let’s get this\n over with.\nTime to see if you can\nkeep up with the smartest\nfish in the sea.";
        dialogues[8] = "Oh, and don’t call me\n‘Blub’.\nIt’s Sir Fin to you.\nAre you ready kids!";
        dialogues[9] = question;
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
            gamePanel.ui.showMessage("Fish noise.");
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
