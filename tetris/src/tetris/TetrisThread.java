package tetris;

public class TetrisThread extends Thread{
	TetrisGameView p1;
	TetrisThread(TetrisGameView P1){
		this.p1 = P1;
	}
	
	public void run() {
		while(true) {
			try {
				sleep(500);
				if(p1.limit) {
					p1.Down();
				}
				else
					break;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				return;
			}
		}
	}
}
