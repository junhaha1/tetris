package tetris;

import javax.swing.*;
import java.awt.event.*;

public class TetrisMain extends JFrame{
	TetrisGameView p1 = new TetrisGameView();
	
	//테스트
	JPanel tem1 = new JPanel();
	JButton b1 = new JButton("chage");
	//테스트
	
	TetrisMain(){
		setSize(500, 700);
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p1.repaint();
			}
		});
		
		tem1.add(b1);
		tem1.setBounds(0, 0, 100, 100);
		
		add(tem1);
		add(p1);
		setLayout(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TetrisMain();
	}

}
