package maingame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyInputs implements KeyListener, MouseListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    boolean checkDrawTime = false;

    public KeyInputs(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) upPressed = true;
            if (code == KeyEvent.VK_S) downPressed = true;
            if (code == KeyEvent.VK_A) leftPressed = true;
            if (code == KeyEvent.VK_D) rightPressed = true;
            if (code == KeyEvent.VK_ESCAPE) gp.gameState = gp.pauseState;
            if (code == KeyEvent.VK_ENTER) enterPressed = true;
            if (code == KeyEvent.VK_T) checkDrawTime = !checkDrawTime;

        } else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }

        } else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true; 
            }

        } else if (gp.gameState == gp.puzzleState) {
            // optional keyboard puzzle logic
        } else if (gp.gameState == gp.gameOverState) {
            // Press Enter to reset
            if (code == KeyEvent.VK_ENTER) {
                gp.setupGame();
                gp.gameState = gp.playState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) upPressed = false;
        if (code == KeyEvent.VK_S) downPressed = false;
        if (code == KeyEvent.VK_A) leftPressed = false;
        if (code == KeyEvent.VK_D) rightPressed = false; // corrected
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (gp.gameState == gp.puzzleState) {
            int chosenAnswer = gp.ui.getClickedAnswerButton(x, y);
            if (chosenAnswer != -1) {
                if (chosenAnswer == 4) {
                    // Correct
                    gp.gameState = gp.playState;
                    gp.currentNPCIndex = -1;
                    gp.ui.showMessage("Correct! The answer is 4!");
                    gp.ui.currentDialogue = "";
                } else {
                    // Wrong answer
                    gp.ui.showMessage("That's not correct! You lost a heart!");
                    gp.ui.playerHealth--;
                    gp.ui.currentDialogue = "";
                }
            }
        }
    }

    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
}
