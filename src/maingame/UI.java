package maingame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("0.00");

    // Puzzle answers and buttons
    private int buttonWidth = 100;
    private int buttonHeight = 50;
    private int spacing = 20;
    private String[] puzzleAnswers = {"2","3","4","5"};
    private int[] buttonX = new int[4];
    private int[] buttonY = new int[4];

    // Health system
    public int playerHealth = 3; 
    BufferedImage heart_full;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("arial", Font.PLAIN, 40);
        arial_80B = new Font("arial", Font.BOLD, 60);
        loadHeartImage();
    }

    public void loadHeartImage() {
        try {
            heart_full = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/npc/applenpc.png"));
            heart_full = new maingame.Utility().scaleImage(heart_full, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.black);

        drawHearts();

        if(gp.gameState == gp.playState) {
            // normal
        }
        else if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
        else if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }
        else if (gp.gameState == gp.puzzleState) {
            drawPuzzleScreen();
        }
        else if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }

        if(messageOn) {
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message, 10, 760);
            messageCounter++;
            if(messageCounter >120) {
                messageCounter = 0;
                messageOn = false;
            }
        }
    }

    public void drawHearts() {
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        for(int i = 0; i < playerHealth; i++) {
            g2.drawImage(heart_full, x + (i * (gp.tileSize + 10)), y, null);
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont (Font.BOLD,75F));
        String text = "GAME PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text,  x,  y);
    }

    public void drawDialogueScreen() {
        int x = gp.tileSize*2;
        int y = 500;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*5;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        x += gp.tileSize;
        y += gp.tileSize;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawPuzzleScreen() {
        int puzzleX = gp.tileSize*2;
        int puzzleY = 500;
        int puzzleWidth = gp.screenWidth - (gp.tileSize*4);
        int puzzleHeight = gp.tileSize*5;
        drawSubWindow(puzzleX, puzzleY, puzzleWidth, puzzleHeight);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        int textX = puzzleX + gp.tileSize;
        int textY = puzzleY + gp.tileSize;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        int answerY = textY + spacing;
        for(int i = 0; i < puzzleAnswers.length; i++) {
            int bx = textX + i * (buttonWidth + spacing);
            int by = answerY;
            drawAnswerButton(bx, by, buttonWidth, buttonHeight, puzzleAnswers[i]);
            buttonX[i] = bx;
            buttonY[i] = by;
        }
    }

    public void drawGameOverScreen() {
        String text = "GAME OVER";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64F));
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2 - gp.tileSize*2;
        g2.setColor(Color.black);
        g2.drawString(text, x, y);

        g2.setFont(arial_40);
        String instruction = "Press ENTER to Restart";
        int x2 = getXforCenteredText(instruction);
        int y2 = gp.screenHeight/2;
        g2.drawString(instruction, x2, y2);
    }

    public void drawAnswerButton(int x, int y, int w, int h, String text) {
        g2.setColor(Color.white);
        g2.fillRoundRect(x, y, w, h, 10, 10);
        g2.setColor(Color.black);
        g2.drawRoundRect(x, y, w, h, 10, 10);
        int textWidth = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int textHeight = (int)g2.getFontMetrics().getAscent();
        int textX = x + (w - textWidth)/2;
        int textY = y + (h + textHeight)/2;
        g2.drawString(text, textX, textY);
    }

    public void drawSubWindow(int x , int y , int width, int height) {
        Color c = new Color(0,0,0);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x, y, width, height, 35, 35);
    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }

    public int getClickedAnswerButton(int mouseX, int mouseY) {
        if (gp.gameState != gp.puzzleState) return -1;
        for(int i = 0; i < puzzleAnswers.length; i++) {
            int bx = buttonX[i];
            int by = buttonY[i];
            if (mouseX >= bx && mouseX <= bx+buttonWidth && mouseY >= by && mouseY <= by+buttonHeight) {
                try {
                    return Integer.parseInt(puzzleAnswers[i]);
                } catch(NumberFormatException e) {
                    return -1;
                }
            }
        }
        return -1;
    }
}
