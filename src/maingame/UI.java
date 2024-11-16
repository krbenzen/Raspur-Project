package maingame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {
GamePanel gp;
Font georgia_40;
public UI(GamePanel gp) {
	this.gp = gp;
	georgia_40 = new Font("Georgia", Font.BOLD, 40);
}
public void draw(Graphics2D g2) {
	g2.setFont(georgia_40);
	g2.setColor(Color.black);
	g2.drawString("Pencil = " + gp.player.hasPencil, 20, 20);
}
}
