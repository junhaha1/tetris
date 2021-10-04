package tetris;

import javax.swing.*;
import java.awt.event.*;

public class TetrisMain extends JFrame{
	TetrisGameView p1 = new TetrisGameView();
	TetrisThread t1;
	
	JButton st = new JButton("게임 시작");
	
	TetrisMain(){
		setSize(500, 600);
		add(p1);
		
		p1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				
				switch(keyCode) {
					case KeyEvent.VK_UP:
						p1.blockRotation();
						break;
					case KeyEvent.VK_RIGHT:
						p1.moveRight();
						break;
					case KeyEvent.VK_LEFT:
						p1.moveLeft();
						break;
					case KeyEvent.VK_DOWN:
						p1.Down();
						break;
				}
			}
		});
		
		t1 = new TetrisThread(p1);
		
		setLayout(null);
		setVisible(true);
		
		p1.requestFocus(true);
		
		t1.run();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TetrisMain();
	}

}
