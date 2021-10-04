package tetris;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
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
	int hgt = 0;
	int wid  = 100;
	int[] curX =  new int[4];
	int[] curY =  new int[4];
	
	//���� ��Ʈ�� ����
	boolean End = false;
	boolean limit = true;
	
	TetrisGameView(){
		setBackground(Color.white);
		this.setBounds(50, 50, 400, 400);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		endJudge(); //���� ���� ����
		paintBorder(g); // �׵θ� �׸���
		drawBoard(g); //������ �׸���
		lookBlock(g); // �� �̸�����
		
		if (limit) {
			findCoord(); // ������ �� ��ǥ ���ϱ�
			drawBlock(g); // ������ �� �׸���
		
			blockToWall(); //���̳� �ٴ� ������ ������ �ٲ�.
			removeBlock();
		}
		else
			gameOver();
		
		
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
					hgt = 0;
					End = true;
				}
			}
		}
	}
	
	//������ �α� ���
	public void printlog() {
		System.out.println("----------------------------------------");
		for(int y = 0; y < 21; y ++) {
			for(int x = 0; x < 12; x++) {
				System.out.print(b2.board[y][x]);
			}
			System.out.println();
		}
		System.out.println("----------------------------------------");
	}
	
	//�� ����
	public void removeBlock() {
		int cnt;
		
		for(int y = 0; y < 20; y ++) {
			cnt = 0;
			for(int x = 1; x < 11; x++) {
				if(b2.board[y][x] == 1)
					cnt++;
			}
			if(cnt == 10) {
				for(int j = y; j > 1; j--) {
					for(int i = 1; i < 11; i++) {
						b2.board[j][i] = b2.board[j-1][i];
					}
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
	
	//��Ʈ���� �޼���
	public void gameOver() {
		TetrisDialog TD = new TetrisDialog(this);
		TD.setVisible(true);
	}
	
	//�ɼ� ����
	public void resetOption() {
		hgt = 0;
		wid  = 100;
		End = false;
		limit = true;
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
	
	
	/*��� �˻�*/
	public int checkRotateWall() {
		int temp = b.rotate;
		
		b.rotate = (b.rotate + 1) % 4;
		findCoord();
		
		for(int i = 0; i < 4; i++)
			if(b2.board[curY[i]][curX[i] + 1] == 1)
				return temp;
		
		return b.rotate;
	}
	
	public boolean checkLWall() {
		for (int i=0; i<4; i++)
			if (b2.board[0][curX[i]] == 1) 
				return false;
		return true;
	}
	
	public boolean checkRWall() {
		for (int i=0; i<4; i++)
			if (b2.board[0][curX[i] + 2] == 1) 
				return false;
		return true;
	}
	
	
	/*��� �̵� ����*/
	
	//��� �ϰ�
	public void Down() {
		hgt += b.size;
		this.repaint();
	}
	
	//��� ���� �̵�
	public void moveRight() {
		if(checkRWall()) {
			wid += b.size;
			this.repaint();
		}
	}
	//�� ���� �̵�
	public void moveLeft() {
		if(checkLWall()) {
			wid -= b.size;
			this.repaint();
		}
	}
	
	//�� ȸ��
	public void blockRotation() {
		b.rotate = checkRotateWall();
		this.repaint();
	}
	
	public void changeRandom() {
		randomB = randomA;
		randomA = (int) (Math.random() * 7);
		randomC = (int) (Math.random() * 4);
	}
	
}
