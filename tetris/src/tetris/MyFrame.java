package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.*;

public class MyFrame extends JFrame {
	
	TetrisPanel TP = new TetrisPanel();
	JLabel la1 = new JLabel();
	TetrisThread th; //��Ʈ���� ������
	
	//���� ��
	block gameblock = new block();
	board gameboard = new board();
	
	//�� ���� ������
	int random = 0;
	int random2 = (int)(Math.random()*7);
	
	//��� ũ��, ����, ����
	int blocksize = 20;
	int wid = 100;
	int hgt = 0;
	
	//���� ���� �ɼ�
	int End = 0;
	
	boolean limit = false; // õ�忡 ����� �Ǵ� ����
	
	//��� ��ǥ, ��� ȸ��
	int curX[] = new int[4];
	int curY[] = new int[4];	
	
	int rotation = 0;
	
	MyFrame()
	{
		setTitle("ù��°");
		setLayout(null);
		setSize(720, 600);
		
		TP.setSize(720, 600);
		TP.setBackground(Color.WHITE);
		add(TP);
		
		th = new TetrisThread();
		
		TP.addKeyListener(new MyKeyListener());
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		TP.requestFocus(true);
		th.start();
	}
	
	class MyKeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()){
			case KeyEvent.VK_UP:
				la1.setText("����");
				TP.moveUp();
				
				break;
			case KeyEvent.VK_RIGHT:
				la1.setText("������");
				TP.moveRight();
				//���� ���� �̵�
				break;
			case KeyEvent.VK_LEFT:
				la1.setText("����");
				TP.moveLeft();
				//���� ���� �̵�
				break;
			case KeyEvent.VK_DOWN:
				la1.setText("�Ʒ���");
				TP.moveDown();
				//���� ���� ������.
				break;
			case KeyEvent.VK_SPACE:
				la1.setText("�����̽���");
				//���� �ٷ� ������.
				break;
			}
		}
	}

	
	class TetrisPanel extends JPanel{
		public void paintComponent(Graphics g) {
			
			int cnt = 0; // ��� x,y ��ǥ �迭�� �����ϴ� �ε��� ��
			int cnt2 = 0; //�� ��� ��� ��
			
			TP.requestFocus(true);
			super.paintComponent(g);
			
			TP.add(la1);
			g.setColor(Color.ORANGE);
			
			gameOverCheck(); //����� õ�忡 ����� �Ǵ�
			
			removeBlock(cnt, cnt2, g); // �� ���� ������� ä������ ���� (���� �ִ� ����� ������)
 			
			blockToWall(); // (�ٴ� ����) ���� ���� ��� ������
			
			lookBlock(g); //������ ���� ����
			
			makeBlock(g); //��� ����
			 
			
			if(End == 1) { //����� �� ���������� ���� ��� �����ϱ� ���� ���� �ʱ�ȭ
				random2 = (int)(Math.random()*7);
				End = 0;
			}
			
		}
		
		public void lookBlock(Graphics g) {
			for (int i = 0; i<4; i++) {
				for (int j = 0; j < 4; j++) {
					if(gameblock.blocks[random2][0][i][j] == 1) {
						g.fill3DRect(j*blocksize+120, i*blocksize, blocksize, blocksize, true);
					}
				}
			}
		}
		
		public void gameOverCheck() {
			for (int x = 1; x<11; x++) {
				if (gameboard.boards[2][x] == 1)
					limit = true;
			}
			System.out.println(limit); //�α�
		}
		
		public void removeBlock(int cnt, int cnt2, Graphics g) {
			for (int y = 0; y <20; y++) {
				for (int x = 1; x<11; x++) {
					if (gameboard.boards[y][x] == 1) {
						cnt2++; // ��� �� ����
					}
				}
				if (cnt2 == 10) { //�� ���� ����� ���� á�� ���
					for (int i = y; i>1; i--) {
						for (int j = 1; j < 11; j ++) {
							gameboard.boards[i][j] = 0; //��� ����
							gameboard.boards[i][j] = gameboard.boards[i-1][j]; //���� �ִ� �� ������.
						}
					}
				}
				else {
					blockDown(cnt, g);
				}
				cnt2 = 0; //�ش� �� ��� �� 0���� �ʱ�ȭ
			}
		}
		
		public void blockToWall() {
			try {
				for (int z = 0; z < 4; z++)
					if(gameboard.boards[curY[z]+1][curX[z]] == 1) // �� ������ �����ؾ� �ϱ� ������ curY[z]�� 1 �����ִ� ��.
						for(int j = 0; j< 4; j++) {
							
							gameboard.boards[curY[j]][curX[j]] = 1;
							wid = 100;
							hgt = 0;
							End = 1;
							rotation = 0;
							random = random2;
						}
			}
			catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("Error");
				for(int i = 0; i < 4; i++)
					System.out.print("(" + curX[i] + "," + curY[i]+ ")");
				System.out.println();
			}
		}
		
		public void makeBlock(Graphics g){
	         g.setColor(Color.GRAY); 
	          for(int y=0; y<20;y++){
	             for(int x=1; x<11; x++){
	                if(gameboard.boards[y][x]== 1){
	                   g.fill3DRect( x*blocksize, y*blocksize, blocksize, blocksize, true);
	                }
	             }
	          }
	      }
		
		public void blockDown(int cnt, Graphics g) {
			for (int j = 0; j < 4; j++) {
				for (int i = 0; i<4; i ++) {
					if(gameblock.blocks[random][rotation][j][i] == 1) {
						curX[cnt] = ((i * blocksize) + wid) / blocksize;
						curY[cnt] = ((j * blocksize) + hgt) / blocksize;
						g.draw3DRect(curX[cnt] * blocksize, curY[cnt] * blocksize, blocksize, blocksize, true);
						
						cnt++;
					}
				}
			}
		}
		
		public int collision_LEFT() { //���� �� �浹 ����
			for (int i = 0; i < 4; i++) {
				if (gameboard.boards[curY[i]][curX[i] - 1] == 1)
					return 1;
			}
			return 0;
		}
		
		public int collision_RIGHT() { //������ �� �浹 ����
			for (int i = 0; i < 4; i++) {
				if (gameboard.boards[curY[i]][curX[i] + 1] == 1)
					return 1;
			}
			return 0;
		}
		
		public void checkRotation() {
			int cnt = 0;
			
			rotation = (rotation + 1) % 4;
			for (int i = 0; i < 4; i++) {
				for(int j = 0; j < 4; j++) {
					if (gameblock.blocks[random][rotation][i][j] == 1) {
						curX[cnt] = ((j * blocksize) + wid) / blocksize;
						curY[cnt] = ((i * blocksize) + wid) / blocksize;
						cnt++;
					}
				}
			}
			
			if (gameboard.boards[curY[1]][curX[1]] == 1) {
				if (random == 1)
					wid += (blocksize * 2);
				else if (random == 2)
					wid -= blocksize;
			}
			
			else if (gameboard.boards[curY[0]][curX[0]] == 1){
				if (random == 6 && rotation == 3)
					wid -= blocksize;
				else {
					wid += blocksize;
				}
			}
			
			else if (gameboard.boards[curY[3]][curX[3]] == 1){
				wid -= blocksize;
			}
		}
		
		public void makeBorder(Graphics g){
	         g.setColor(Color.GRAY);
	         
	         g.draw3DRect(28, 70, 5, 375,true); // ���
	         g.draw3DRect(265, 70, 5, 375, true); // ���
	         g.draw3DRect(15, 445, 270, 5,true); // �ٴ�
	         g.draw3DRect(15, 65, 270, 5, true); // õ��
	      }
		
		public void down() { //�ð����� �ڵ����� �ϰ�
			hgt += blocksize;
			TP.repaint();
		}
		
		public void moveUp() { // ���� ����Ű ���� ȸ��
			//ȸ���� ����� ���� �浹�ϴ� �����ϴ� �޼ҵ� �ʿ���. 
			checkRotation();
			if(limit == false)
				TP.repaint();
		}
		
		public void moveLeft() { // ���� ����Ű ���� ���� �̵�
			int sel = collision_LEFT();
			if(sel == 0 && limit == false) {
				wid -= blocksize;
				TP.repaint();
			}
		}
		
		public void moveRight() {// ������ ����Ű ���� ���� �̵�
			int sel = collision_RIGHT();
			if(sel == 0 && limit == false) {
				wid += blocksize;
				TP.repaint();
			}
		}
		
		public void moveDown() { // �Ʒ��� ����Ű ���� ������ �ϰ�
			if (limit == false) {
				hgt += blocksize;
				TP.repaint();
			}
		}
		
		public void moveSpace() { //�����̽��� ������ ��� ������.
			
		}
	}
	
	class TetrisThread extends Thread{
		TetrisPanel TP = new TetrisPanel();
		public void run () {
			while(true) {
				try {
					sleep(500);
					if(limit == false)
						TP.down();
				}catch(InterruptedException e) {
					return;
				}
			}
		}
	}
	
}
