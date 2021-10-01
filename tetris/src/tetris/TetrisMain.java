package tetris;

import javax.swing.*;
import java.awt.event.*;

public class TetrisMain extends JFrame{
	TetrisGameView p1 = new TetrisGameView();
	TetrisThread t1;
	//테스트
	JPanel tem1 = new JPanel();
	JButton b1 = new JButton("chage");
	//테스트
	
	TetrisMain(){
		setSize(500, 600);
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p1.repaint();
			}
		});
		
		add(p1);
		
		t1 = new TetrisThread(p1);
		
		setLayout(null);
		setVisible(true);
		
		t1.run();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TetrisMain();
	}

}
