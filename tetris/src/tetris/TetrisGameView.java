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
	
	
	
	//블럭 모양 & 색 랜덤 값
	int randomA = (int) (Math.random() * 7);
	int randomB = (int) (Math.random() * 7);
	int randomC = (int) (Math.random() * 4);
	
	//블럭 높이, 좌표
	int hgt = 0;
	int wid  = 100;
	int[] curX =  new int[4];
	int[] curY =  new int[4];
	
	//게임 컨트롤 변수
	boolean End = false;
	boolean limit = true;
	
	TetrisGameView(){
		setBackground(Color.white);
		this.setBounds(50, 50, 400, 400);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		endJudge(); //게임 종료 판정
		paintBorder(g); // 테두리 그리기
		drawBoard(g); //보드판 그리기
		lookBlock(g); // 블럭 미리보기
		
		if (limit) {
			findCoord(); // 생성된 블럭 좌표 구하기
			drawBlock(g); // 생성된 블럭 그리기
		
			blockToWall(); //벽이나 바닥 만나면 벽으로 바뀜.
			removeBlock();
		}
		else
			gameOver();
		
		
		if(End) {
			changeRandom();
			End = false;
		}
	}
	
	// 블럭 미리 보기
	public void lookBlock(Graphics g) { //최종 완성
		g.setColor(Color.yellow);
		for (int i = 0; i<4; i++) {
			for (int j = 0; j < 4; j++) {
				if(b.block[randomA][0][i][j] == 1) {
					g.fill3DRect(j*b.size + 300, i*b.size + 50, b.size, b.size, true);
				}
			}
		}
	}
	
	//블럭 좌표 구하기
	public void findCoord() { //완성
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
	
	//블록 그리기
	public void drawBlock(Graphics g) { //완성
		g.setColor(color[randomC]);
		for (int i = 0; i < 4; i++) {
			g.fill3DRect(curX[i] * b.size, curY[i] * b.size, b.size, b.size, true);
		}
	}
	
	//보드 그리기
	public void drawBoard(Graphics g) { //완성
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
	
	
	//블럭이 바닥이나 벽에 닿으면 벽
	public void blockToWall() { // 1차 완성
		for (int i = 0; i < 4; i++) {
			if(b2.board[curY[i] + 1][curX[i] + 1] == 1) { //x의 범위가 1~10까지여서 1을 더해줌. 
				for (int j = 0; j < 4; j++) {
					b2.board[curY[j]][curX[j] + 1] = 1;
					wid = 100;
					hgt = 0;
					End = true;
				}
			}
		}
	}
	
	//보드판 로그 찍기
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
	
	//블럭 삭제
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
	
	//게임 종료 조건 => 두번째 줄에 1이 있으면 게임 오버
	public void endJudge() {
		for(int i = 1; i< 11; i++) 
			if (b2.board[1][i] == 1)
				limit = false;
	}
	
	//테트리스 메세지
	public void gameOver() {
		TetrisDialog TD = new TetrisDialog(this);
		TD.setVisible(true);
	}
	
	//옵션 설정
	public void resetOption() {
		hgt = 0;
		wid  = 100;
		End = false;
		limit = true;
	}
	
	//테트리스 테두리
	public void paintBorder(Graphics g) { //1차 완성
		g.setColor(Color.black);
		//블럭 미리보기
		g.drawLine(249, 0, 399, 0);
		g.drawLine(249, 149, 399, 149);
		g.drawLine(249, 0, 249, 149);
		g.drawLine(399, 0, 399, 149);
	}
	
	
	/*블록 검사*/
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
	
	
	/*블록 이동 관련*/
	
	//블록 하강
	public void Down() {
		hgt += b.size;
		this.repaint();
	}
	
	//블록 우측 이동
	public void moveRight() {
		if(checkRWall()) {
			wid += b.size;
			this.repaint();
		}
	}
	//블럭 좌측 이동
	public void moveLeft() {
		if(checkLWall()) {
			wid -= b.size;
			this.repaint();
		}
	}
	
	//블럭 회전
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
