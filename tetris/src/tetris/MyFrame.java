package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.*;

public class MyFrame extends JFrame {
	
	TetrisPanel TP = new TetrisPanel();
	JLabel la1 = new JLabel();
	TetrisThread th; //테트리스 스레드
	
	//게임 블럭
	block gameblock = new block();
	board gameboard = new board();
	
	//블럭 섞기 랜덤값
	int random = 0;
	int random2 = (int)(Math.random()*7);
	
	//블록 크기, 넓이, 높이
	int blocksize = 20;
	int wid = 100;
	int hgt = 0;
	
	//게임 설정 옵션
	int End = 0;
	
	boolean limit = false; // 천장에 닿는지 판단 변수
	
	//블록 좌표, 블록 회전
	int curX[] = new int[4];
	int curY[] = new int[4];	
	
	int rotation = 0;
	
	MyFrame()
	{
		setTitle("첫번째");
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
				la1.setText("위쪽");
				TP.moveUp();
				
				break;
			case KeyEvent.VK_RIGHT:
				la1.setText("오른쪽");
				TP.moveRight();
				//도형 우측 이동
				break;
			case KeyEvent.VK_LEFT:
				la1.setText("왼쪽");
				TP.moveLeft();
				//도형 좌측 이동
				break;
			case KeyEvent.VK_DOWN:
				la1.setText("아래쪽");
				TP.moveDown();
				//도형 빨리 내려옴.
				break;
			case KeyEvent.VK_SPACE:
				la1.setText("스페이스바");
				//도형 바로 내려옴.
				break;
			}
		}
	}

	
	class TetrisPanel extends JPanel{
		public void paintComponent(Graphics g) {
			
			int cnt = 0; // 블록 x,y 좌표 배열에 접근하는 인덱스 값
			int cnt2 = 0; //한 행당 블록 수
			
			TP.requestFocus(true);
			super.paintComponent(g);
			
			TP.add(la1);
			g.setColor(Color.ORANGE);
			
			gameOverCheck(); //블록이 천장에 닿는지 판단
			
			removeBlock(cnt, cnt2, g); // 한 행이 블록으로 채워지면 삭제 (위에 있는 블록은 떨어짐)
 			
			blockToWall(); // (바닥 포함) 벽에 닿은 블록 벽으로
			
			lookBlock(g); //다음에 나올 도형
			
			makeBlock(g); //블록 생성
			 
			
			if(End == 1) { //블록이 다 떨어졌으면 랜덤 블록 생성하기 위한 변수 초기화
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
			System.out.println(limit); //로그
		}
		
		public void removeBlock(int cnt, int cnt2, Graphics g) {
			for (int y = 0; y <20; y++) {
				for (int x = 1; x<11; x++) {
					if (gameboard.boards[y][x] == 1) {
						cnt2++; // 블록 수 증가
					}
				}
				if (cnt2 == 10) { //한 행의 블록이 가득 찼을 경우
					for (int i = y; i>1; i--) {
						for (int j = 1; j < 11; j ++) {
							gameboard.boards[i][j] = 0; //블록 없앰
							gameboard.boards[i][j] = gameboard.boards[i-1][j]; //위에 있는 행 내려옴.
						}
					}
				}
				else {
					blockDown(cnt, g);
				}
				cnt2 = 0; //해당 행 블록 수 0으로 초기화
			}
		}
		
		public void blockToWall() {
			try {
				for (int z = 0; z < 4; z++)
					if(gameboard.boards[curY[z]+1][curX[z]] == 1) // 맨 윗줄을 제외해야 하기 때문에 curY[z]에 1 더해주는 것.
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
		
		public int collision_LEFT() { //왼쪽 벽 충돌 판정
			for (int i = 0; i < 4; i++) {
				if (gameboard.boards[curY[i]][curX[i] - 1] == 1)
					return 1;
			}
			return 0;
		}
		
		public int collision_RIGHT() { //오른쪽 벽 충돌 판정
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
	         
	         g.draw3DRect(28, 70, 5, 375,true); // 기둥
	         g.draw3DRect(265, 70, 5, 375, true); // 기둥
	         g.draw3DRect(15, 445, 270, 5,true); // 바닥
	         g.draw3DRect(15, 65, 270, 5, true); // 천장
	      }
		
		public void down() { //시간마다 자동으로 하강
			hgt += blocksize;
			TP.repaint();
		}
		
		public void moveUp() { // 위쪽 방향키 도형 회전
			//회전시 블록이 벽에 충돌하는 판정하는 메소드 필요함. 
			checkRotation();
			if(limit == false)
				TP.repaint();
		}
		
		public void moveLeft() { // 왼쪽 방향키 도형 좌측 이동
			int sel = collision_LEFT();
			if(sel == 0 && limit == false) {
				wid -= blocksize;
				TP.repaint();
			}
		}
		
		public void moveRight() {// 오른쪽 방향키 도형 우측 이동
			int sel = collision_RIGHT();
			if(sel == 0 && limit == false) {
				wid += blocksize;
				TP.repaint();
			}
		}
		
		public void moveDown() { // 아래쪽 방향키 도형 빠르게 하강
			if (limit == false) {
				hgt += blocksize;
				TP.repaint();
			}
		}
		
		public void moveSpace() { //스페이스바 누르면 즉시 내려옴.
			
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
