package tetris;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class TetrisGameView extends JPanel{
	Color[] color = {Color.darkGray, Color.blue, Color.orange, Color.pink};
	TetrisBlock b = new TetrisBlock();
	
	int randomC = (int) (Math.random() * 4);
	int randomB = (int) (Math.random() * 7);
	
	boolean End = false;
	
	TetrisGameView(){
		setBackground(Color.GRAY);
		this.setBounds(50, 100, 220, 420);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color[randomC]);
		lookBlock(g);
		changeRandom();
	}
	
	public void lookBlock(Graphics g) {
		for (int i = 0; i<4; i++) {
			for (int j = 0; j < 4; j++) {
				if(b.block[randomB][0][i][j] == 1) {
					g.fill3DRect(j*20 + 100, i*20, 20, 20, true);
				}
			}
		}
	}
	
	public void changeRandom() {
		randomB = (int) (Math.random() * 7);
		randomC = (int) (Math.random() * 4);
	}
	
}
