package tetris;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class TetrisGameView extends JPanel{
	Color[] color = {Color.darkGray, Color.blue, Color.orange, Color.pink};
	
	TetrisBlock b = new TetrisBlock();
	TetrisBoard b2 = new TetrisBoard();
	
	//�� ��� & �� ���� ��
	int randomA = (int) (Math.random() * 7);
	int randomB = (int) (Math.random() * 7);
	int randomC = (int) (Math.random() * 4);
	
	//�� ����, ��ǥ
	int hgt = -20;
	int wid  = 100;
	int[] curX =  new int[4];
	int[] curY =  new int[4];
	
	
	
	//��Ʈ���� ��Ʈ�� ����
	boolean End = false;
	boolean limit = true;
	
	TetrisGameView(){
		setBackground(Color.white);
		this.setBounds(50, 50, 400, 400);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		paintBorder(g); // �׵θ� �׸���
		drawBoard(g); //������ �׸���
		lookBlock(g); // �� �̸�����
		
		endJudge(); //���� ���� ����
		findCoord(); // ������ �� ��ǥ ���ϱ�
		drawBlock(g); // ������ �� �׸���
		
		blockToWall(); //���̳� �ٴ� ������ ������ �ٲ�. 
		if(End) {
			changeRandom();
			End = false;
		}
	}
	
	// �� �̸� ����
	public void lookBlock(Graphics g) { //���� �ϼ�
		g.setColor(Color.yellow);
		for (int i = 0; i<4; i++) {
			for (int j = 0; j < 4; j++) {
				if(b.block[randomA][0][i][j] == 1) {
					g.fill3DRect(j*b.size + 300, i*b.size + 50, b.size, b.size, true);
				}
			}
		}
	}
	
	//�� ��ǥ ���ϱ�
	public void findCoord() { //�ϼ�
		int cnt = 0;
		for (int i = 0; i<4; i++) {
			for (int j = 0; j < 4; j++) {
				if(b.block[randomB][b.rotate][i][j] == 1) {
					curX[cnt] = (j*b.size + wid) / b.size;
					curY[cnt] = (i*b.size + hgt) / b.size;
					cnt++;
				}
			}
		}
	}
	
	//��� �׸���
	public void drawBlock(Graphics g) { //�ϼ�
		g.setColor(color[randomC]);
		for (int i = 0; i < 4; i++) {
			g.fill3DRect(curX[i] * b.size, curY[i] * b.size, b.size, b.size, true);
		}
	}
	
	//���� �׸���
	public void drawBoard(Graphics g) { //�ϼ�
		for (int j = 0; j < 20; j++) {
			for(int i = 1; i < 11; i++) {
				if(b2.board[j][i] == 1)
					g.setColor(Color.gray);
				else
					g.setColor(Color.white);
				g.fill3DRect((i-1) * b.size, j * b.size, b.size, b.size, true);
			}
		}
	}
	
	
	//���� �ٴ��̳� ���� ������ ��
	public void blockToWall() { // 1�� �ϼ�
		for (int i = 0; i < 4; i++) {
			if(b2.board[curY[i] + 1][curX[i] + 1] == 1) { //x�� ������ 1~10�������� 1�� ������. 
				for (int j = 0; j < 4; j++) {
					b2.board[curY[j]][curX[j] + 1] = 1;
					wid = 100;
					hgt = -20;
					End = true;
				}
			}
		}
	}
	
	//���� ���� ���� => �ι�° �ٿ� 1�� ������ ���� ����
	public void endJudge() {
		for(int i = 1; i< 11; i++) 
			if (b2.board[1][i] == 1)
				limit = false;
	}
	
	//��Ʈ���� �׵θ�
	public void paintBorder(Graphics g) { //1�� �ϼ�
		g.setColor(Color.black);
		//�� �̸�����
		g.drawLine(249, 0, 399, 0);
		g.drawLine(249, 149, 399, 149);
		g.drawLine(249, 0, 249, 149);
		g.drawLine(399, 0, 399, 149);
	}
	
	public void Down() {
		hgt += b.size;
		this.repaint();
	}
	
	public void changeRandom() {
		randomB = randomA;
		randomA = (int) (Math.random() * 7);
		randomC = (int) (Math.random() * 4);
	}
	
}
