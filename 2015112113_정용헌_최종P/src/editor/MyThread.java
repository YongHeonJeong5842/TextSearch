package editor;
import java.awt.*;
import javax.swing.*;

public class MyThread extends Thread{
	JProgressBar pBar;
	
	Editor ed;
	int progress;
	
	public MyThread(JProgressBar pBar, int progress, Editor editor){
		this.ed = editor;
		this.pBar = pBar;
		this.progress = progress;
	}
	
	public void run(){
		progress = 0;
		pBar.setValue(0);
		while(progress<=100){  //progressBar가 100이 되면 스레드 종료
			try {
				pBar.setValue(progress); //progressBar의 값을 새로 바뀐 progress에 따라 변경
				progress = max((int)ed.getProgress(),progress+10); //editor로부터 progress를 받아와 progress의 상태 업데이트 
				sleep(100);  //0.1초에 한번씩 progressBar를 업데이트
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	private int max(int a, int b){
		if(a>b) return b;
		else return b;
	}
}
