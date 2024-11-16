package maingame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;
public class GamePanel extends JPanel implements Runnable{
	// Screen Stuff
	final int originalTileSize = 16; // Ill use a 16x16 pixel tile size
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; //48x48 tile
	public final int maxScreenCol = 12;
	public final int maxScreenRow = 16;
	public final int screenWidth = tileSize * maxScreenCol; //768 pixel
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixel
	
	public final int maxWorldCol = 31;
	public final int maxWorldRow = 43;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	int FPS = 60;
	TileManager tileM = new TileManager(this);
	KeyInputs keyH = new KeyInputs();
	music music = new music();
	Thread gameThread;
	public collisions colChecker = new collisions(this);
	public Assets aSetter = new Assets(this);
	public UI ui = new UI(this);
	
	
	public Player player = new Player(this,keyH);
	// objects the number below is the amount of inventory you have right now it is [10] slots
	public SuperObject obj[] = new SuperObject[10];
	
	
	
	
	
	//player default pos
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 3;
	
	
	public GamePanel () {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
public void setupGame() {
	aSetter.setObject();
	playMusic(0);
}
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
		
	}
//	@Override
//	public void run() {
//		while (gameThread != null) {
//
//			double drawInterval = 1000000000/FPS;
//			double nextDrawTime = System.nanoTime()+drawInterval;
//			
//			update();
//			//draws screen
//			repaint();
//			
//			
//			try {
//				double remainingTime = nextDrawTime - System.nanoTime();
//				remainingTime = remainingTime/1000000;
//				if(remainingTime < 0) {
//					remainingTime = 0;
//				}
//				Thread.sleep((long) remainingTime);
//				
//				nextDrawTime += drawInterval;
//	
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			
//		}
//		
//	}
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
				timer= 0;
			}
		}
	}
	public void update() {
			player.update();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		//tile initialization
		tileM.draw(g2);
		//objects initialization'
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		//player  initialization
		player.draw(g2);
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
		music.setFile(i);
		music.play();
	}
}
