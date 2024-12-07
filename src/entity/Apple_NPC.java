package entity;

import java.util.Random;

import maingame.GamePanel;

public class Apple_NPC extends Entity {
    public Apple_NPC(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("/npc/applenpc");
        up2 = setup("/npc/applenpc");
        down1 = setup("/npc/applenpc");
        down2 = setup("/npc/applenpc");
        left1 = setup("/npc/applenpc");
        left2 = setup("/npc/applenpc");
        right1 = setup("/npc/applenpc");
        right2 = setup("/npc/applenpc");
    }

    public void setDialogue() {
        dialogues[0] = "What's up, what's 2+2?";
        dialogues[1] = "You don't know?";
        dialogues[2] = "Well, that's too bad...\nyou should've known!";
        dialogues[3] = "The answer is 4... but let's see if you remember.";
        dialogues[4] = "I will ask you again, what is 2+2?";
        dialogues[5] = "Choose the correct answer below:";
    }

    public void setAction() {
        actionLockCounter++;
        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if (i <= 25) direction = "up";
            else if(i>25 && i <=50) direction = "down";
            else if(i>50 && i <=75) direction = "left";
            else if(i>75 && i <=100) direction = "right";
            actionLockCounter=0;
        }
    }

    @Override
    public void speak() {
        if (dialogueIndex == 5) {
            gp.ui.currentDialogue = dialogues[5];
            gp.gameState = gp.puzzleState;
        } else {
            super.speak();
        }
    }
}
