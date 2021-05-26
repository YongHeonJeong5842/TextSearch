package editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class MyFrame extends JFrame implements ActionListener {
	static BufferedWriter bw = null;// 객체 생성
	JMenuBar menuBar = new JMenuBar();
	JMenu menu1 = new JMenu("파일");
	JMenuItem jmi1 = new JMenuItem("열기");
	JMenuItem jmi2 = new JMenuItem("출력");
	JMenu menu2 = new JMenu("편집");
	JMenuItem jmi3 = new JMenuItem("검색");
	JMenuItem jmi4 = new JMenuItem("변환");
	JMenuItem jmi5 = new JMenuItem("삽입");

	JPanel panel1 = new JPanel();
	JPanel panel1_1 = new JPanel();
	JPanel panel1_2 = new JPanel();
	JPanel panel1_3 = new JPanel();
	JPanel panel1_4 = new JPanel();
	JPanel panel1_5 = new JPanel();
	JPanel panel1_6 = new JPanel();
	JPanel panel2 = new JPanel();
	JPanel panel3 = new JPanel();
	
	JLabel openPathL = new JLabel("입력 파일: ");
	JLabel printPathL = new JLabel("출력 파일: ");
	JLabel searchWordL = new JLabel("검색할 단어: ");
	JLabel replaceWordL = new JLabel("변환할 단어: ");
	JLabel appendL = new JLabel("덧붙일 단어: ");
	//Cursor showProgress = new Cursor(100);
	JProgressBar progressBar = new JProgressBar(0,100);

	JTextField openPathP = new JTextField(20);
	JTextField printPathP = new JTextField(20);
	JTextField searchWord = new JTextField(20);
	JTextField print = new JTextField(20);
	JTextField replaceWord = new JTextField(20);
	JTextField append = new JTextField(20);
	JTextField line = new JTextField("행입력", 5);
	JTextField row = new JTextField("열입력", 5);

	JList<String> jList = new JList();
	JScrollPane scroll = new JScrollPane(jList);

	JButton openPathB = new JButton("파일오픈");
	JButton printPathB = new JButton("출력경로등록");
	JButton searchB = new JButton("검색");
	JButton printB = new JButton("출력");
	JButton replaceB = new JButton("변환");
	JButton appendB = new JButton("삽입");
	Editor editor = new Editor();
	ImprovedEditor imEditor = new ImprovedEditor();
	
	public MyFrame() {// 생성자
		JFrame frame = new JFrame("검색프로그램");
		frame.setSize(1000, 600);
		frame.setLayout(new BorderLayout());

		frame.add(menuBar, BorderLayout.NORTH);
		menuBar.add(menu1);
		menu1.add(jmi1);
		jmi1.addActionListener(this);
		menu1.add(jmi2);
		jmi2.addActionListener(this);
		menuBar.add(menu2);
		menu2.add(jmi3);
		jmi3.addActionListener(this);
		menu2.add(jmi4);
		jmi4.addActionListener(this);
		menu2.add(jmi5);
		jmi5.addActionListener(this);

		frame.add(panel1, BorderLayout.WEST);
		panel1.setLayout(new GridLayout(6, 1));
		panel1.add(panel1_1);
		panel1.add(panel1_2);
		panel1.add(panel1_3);
		panel1.add(panel1_4);
		panel1.add(panel1_5);
		panel1.add(panel1_6);

		panel1_1.add(openPathL);
		panel1_1.add(openPathP);
		panel1_1.add(openPathB);
		openPathB.addActionListener(this);

		panel1_2.add(printPathL);
		panel1_2.add(printPathP);
		panel1_2.add(printPathB);
		printPathB.addActionListener(this);

		panel1_3.add(searchWordL);
		panel1_3.add(searchWord);
		panel1_3.add(searchB);
		searchB.addActionListener(this);
		panel1_3.add(printB);
		printB.addActionListener(this);

		panel1_4.add(replaceWordL);
		panel1_4.add(replaceWord);
		panel1_4.add(replaceB);
		replaceB.addActionListener(this);

		panel1_5.add(appendL);
		panel1_5.add(append);
		panel1_5.add(line);
		panel1_5.add(row);
		panel1_5.add(appendB);
		appendB.addActionListener(this);

		// 스크롤 사용
		JScrollPane scroller = new JScrollPane(jList);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jList.setVisibleRowCount(11);
		frame.add(panel2, BorderLayout.EAST);
		panel2.add(scroller);

		frame.add(panel3,BorderLayout.SOUTH);
		panel3.add(progressBar);
		panel3.setBackground(Color.WHITE);
		progressBar.setValue(0);
		jList.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {// 액션 이벤트 처리
		String inputPath = "", outputPath = "", scWord, temp = null, replace, appendWord;
		String appendLine = "";
		String appendRow = "";
		String[] resultStr;

		if (e.getSource().equals(openPathB) || e.getSource().equals(jmi1)) {// 파일-열기
			inputPath = openPathP.getText();
			if (editor.checkFile(inputPath))// 파일오픈등록
			{
				JOptionPane.showMessageDialog(null, "입력파일 오픈 성공");// 성공메세지
			} else {
				JOptionPane.showMessageDialog(null, "입력파일 오픈 실패");
			}
		}

		else if (e.getSource().equals(printPathB) || e.getSource().equals(jmi2)) {// 파일-출력
			outputPath = printPathP.getText();
			if (!printPathP.getText().equals("")) {// 예외처리
				try {
					bw = new BufferedWriter(new BufferedWriter(new FileWriter(outputPath)));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // BufferedWrite객체 생성
				JOptionPane.showMessageDialog(null, "출력경로 등록 완료");
			} else {
				JOptionPane.showMessageDialog(null, "출력경로 등록 실패");
			}
		}

		else if (e.getSource().equals(searchB) || e.getSource().equals(jmi3)) {// 편집-검색
			inputPath = openPathP.getText();
			outputPath = printPathP.getText();
			scWord = searchWord.getText();
			if (!searchWord.getText().equals("") && !openPathP.getText().equals("")
					&& !printPathP.getText().equals("")) {// 예외처리
				try {
					MyThread thread = new MyThread(progressBar,0,editor);  //progressbar를 출력하는 스레드 생성
					thread.start();  //스레드 실행
					temp = editor.searchWord(inputPath, scWord, false, progressBar);
					progressBar.setValue(100);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				resultStr = temp.split("\r\n");
				jList.setListData(resultStr);// jList에 삽입
			} else {
				JOptionPane.showMessageDialog(null, "입력경로, 출력경로, 검색단어를 입력하세요");
			}
		}

		else if (e.getSource().equals(replaceB) || e.getSource().equals(jmi4)) {// 편집-변환
			inputPath = openPathP.getText();
			outputPath = printPathP.getText();
			replace = replaceWord.getText();
			scWord = searchWord.getText();
			if (!replaceWord.getText().equals("") && !searchWord.getText().equals("")
					&& !printPathP.getText().equals("") && !openPathP.getText().equals("")) {
				try {
					editor.replaceWord(scWord, replace, inputPath, outputPath);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "파일출력이 완료 되었습니다.");
			} else {
				JOptionPane.showMessageDialog(null, "입력경로, 출력경로, 검색단어, 변환할 단어를 입력하세요");
			}
		}

		else if (e.getSource().equals(appendB) || e.getSource().equals(jmi5)) {// 편집-삽입
			inputPath = openPathP.getText();
			outputPath = printPathP.getText();
			appendWord = append.getText();
			appendLine = line.getText();
			appendRow = row.getText();
			if (!append.getText().equals("") && !printPathP.getText().equals("") && !line.getText().equals("")
					&& !row.getText().equals("") && !openPathP.getText().equals("")) {
				try {
					System.out.println(appendWord);
					imEditor.appendStr(inputPath, outputPath, appendWord, Integer.valueOf(appendLine),
							Integer.valueOf(appendRow));// 메소드호출
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "단어삽입완료");
				// printPathP.setText("");
			} else {
				JOptionPane.showMessageDialog(null, "입력경로, 출력경로, 추가할단어, 줄과행을 입력하세요");
			}
		}
	}
}