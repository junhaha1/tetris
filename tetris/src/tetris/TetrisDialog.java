package tetris;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class TetrisDialog extends JDialog{
	JLabel lb1 = new JLabel("Game Over!");
	JButton btn1 = new JButton("ReStart");
	
	public TetrisDialog(TetrisGameView JGV) {
		
		this.setLayout(new FlowLayout());
		this.setSize(200, 100);
		this.add(lb1);
		this.add(btn1);
		
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JGV.b2.resetBoard();
				JGV.resetOption();
				setVisible(false);
			}
		});
	}
}
