package tetris;

import java.awt.event.*;

import javax.swing.*;

public class MyFrame extends JFrame {
	
	
	JPanel contentPane = new JPanel();
	JLabel a = new JLabel();
	
	
	MyFrame()
	{
		setTitle("첫번째");
		setSize(300, 400);
		 
		setContentPane(contentPane);
		contentPane.addKeyListener(new MyKeyListener());
		contentPane.add(a);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane.setFocusable(true);

	}
	
	class MyKeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()){
			case KeyEvent.VK_UP:
				a.setText("위쪽");
				//도형 회전 오른쪽으로
				break;
			case KeyEvent.VK_RIGHT:
				a.setText("오른쪽");
				//도형 우측 이동
				break;
			case KeyEvent.VK_LEFT:
				a.setText("왼쪽");
				//도형 좌측 이동
				break;
			case KeyEvent.VK_DOWN:
				a.setText("아래쪽");
				//도형 빨리 내려옴.
				break;
			case KeyEvent.VK_SPACE:
				a.setText("스페이스바");
				//도형 바로 내려옴.
				break;
			}
		}
	}
}
