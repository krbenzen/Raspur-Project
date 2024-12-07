package maingame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // Screen settings
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; 
    public final int maxScreenCol = 12;
    public final int maxScreenRow = 16;
    public final int screenWidth = tileSize * maxScreenCol; 
    public final int screenHeight = tileSize * maxScreenRow; 

    public final int maxWorldCol = 31;
    public final int maxWorldRow = 43;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    int FPS = 60;
    TileManager tileM = new TileManager(this);
    public KeyInputs keyH = new KeyInputs(this);
    music music = new music();
    music soundeffect = new music();
    Thread gameThread;
    public collisions colChecker = new collisions(this);
    public Assets aSetter = new Assets(this);
    public UI ui = new UI(this);
    public Player player = new Player(this,keyH);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[100];

    // Game States
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int puzzleState = 4;
    public final int gameOverState = 5;

    public int currentNPCIndex = -1;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.addMouseListener(keyH);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        playMusic(0);
        gameState = playState;
        ui.playerHealth = 3; // Reset hearts
        ui.currentDialogue = "";
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000) {
                System.out.println(drawCount + "FPS");
                drawCount = 0;
                timer=0;
            }
        }
    }

    public void update() {
        if(gameState == playState) {
            player.update();
            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null) npc[i].update();
            }
        }
        else if(gameState == dialogueState) {
            if(keyH.enterPressed) {
                if (currentNPCIndex != -1 && npc[currentNPCIndex] != null) {
                    npc[currentNPCIndex].speak();
                }
                keyH.enterPressed = false;
            }
        }
        else if(gameState == puzzleState) {
            // handled by mouse clicks in KeyInputs
        }
        else if (gameState == gameOverState) {
            // Wait for enter key in KeyInputs to reset
        }
        else if(gameState == pauseState) {
            // paused
        }

        // Check if player died (no hearts)
        if (ui.playerHealth <= 0 && gameState != gameOverState) {
            gameState = gameOverState;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2);
        for(int i = 0; i < obj.length; i++) {
            if(obj[i] != null) obj[i].draw(g2, this);
        }
        for(int i = 0; i < npc.length; i++) {
            if(npc[i] != null) npc[i].draw(g2);
        }
        player.draw(g2);
        ui.draw(g2);
        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundEffect(int i) {
        soundeffect.setFile(i);
        soundeffect.play();
    }
}
