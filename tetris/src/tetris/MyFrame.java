package tetris;

import java.awt.event.*;

import javax.swing.*;

public class MyFrame extends JFrame {
	
	
	JPanel contentPane = new JPanel();
	JLabel a = new JLabel();
	
	
	MyFrame()
	{
		setTitle("ù��°");
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
				a.setText("����");
				//���� ȸ�� ����������
				break;
			case KeyEvent.VK_RIGHT:
				a.setText("������");
				//���� ���� �̵�
				break;
			case KeyEvent.VK_LEFT:
				a.setText("����");
				//���� ���� �̵�
				break;
			case KeyEvent.VK_DOWN:
				a.setText("�Ʒ���");
				//���� ���� ������.
				break;
			case KeyEvent.VK_SPACE:
				a.setText("�����̽���");
				//���� �ٷ� ������.
				break;
			}
		}
	}
}
