package editor;
import java.util.Scanner;
import java.io.*;

public class Menu {
	static BufferedWriter bufferedWriter;
	public static String readFile=null, writeFile=null;

	public static void consoleMenu()throws IOException{
		ImprovedEditor improvedEditor=new ImprovedEditor();
		boolean check = false;	//검색할 파일 경로 존재하는지 여부 저장
		String input=null,temp=null, word=null, replaceWord=null ;
		//readFile:검색할 파일경로 저장, input:입력받은 메뉴 저장, writeFile:출력할 파일경로 저장, temp:파일에 출력할 내용, word:검색할 단어
		int i=0,index;//for문을 위한 i, index:메뉴 "A"에서 덧붙일 인덱스 값 저장
		int[] line = new int[100];	//메뉴 "A"에서 덧붙일 라인 저장,또는 메뉴 "L"에서 검색할 라인들 저장
		Scanner sc = new Scanner(System.in);
		boolean isExitProgram=false;

		while (!isExitProgram) {
			System.out.println("------- M E N U -------");
			System.out.println("파일읽기(O), 단어검색(S), 검색 후 결과 출력(P), 수정후 결과 출력(R), 단어 덧붙이기(A), 특정 라인에서만 검색(L), 프로그램종료(Q)");
			input = sc.nextLine();
			switch (input) {
			case "O":	case "o":
				while (true) {
					System.out.println("검색할 파일 경로를 입력하세요: ");
					sc = new Scanner(System.in);
					readFile = sc.nextLine();		//readFile에 검색할 파일 경로 저장
					check=improvedEditor.checkFile(readFile); //경로에 파일이 존재하는지 확인하기 위해 checkFile 호출
					if (check == true)		//파일이 존재하는 경우
						break;
				}
				break;

			case "S":	case "s":
				if (check == false) {		//파일 경로를 입력받지 않은 경우
					System.out.println("정확한 파일 경로를 먼저 입력해주세요");
				} 
				else {		//파일 경로를 입력받은 경우
					System.out.println("검색할 단어를 입력하세요");
					word=sc.nextLine();		//word에 검색할 단어 저장
					///////////////////////////////////////////////////////
					improvedEditor.searchWord(readFile,word, true, null);	//wordFind 호출해서 단어 검색 후 결과 출력
					///////////////////////////////////////////////////////
				}
				break;
			case "P":	case "p":
				if (check == false) {		//파일 경로를 입력받지 않은 경우
					System.out.println("정확한 파일 경로를 먼저 입력해주세요");
				} 
				else{		//파일 경로를 입력받은 경우
					System.out.println("검색할 단어를 입력하세요");
					word=sc.nextLine();		//word에 검색할 단어 저장
					/////////////////////////////////////////////////////////////
					temp=improvedEditor.searchWord(readFile, word, false, null);	//searchWord 호출해서 단어 검색 후 결과 출력한 후 검색 내용을 반환받아 temp에 저장
					////////////////////////////////////////////////////////////
					System.out.println("파일을 출력하시겠습니까?(y/n)");
					String answer = sc.nextLine();		//파일 출력 여부 저장
					if(answer.equals("y")){		//파일을 출력할 경우
						System.out.println("저장할 경로를 입력하세요");
						writeFile=sc.nextLine();		//저장할 경로를 writeFile에 저장
						check=improvedEditor.checkFile(writeFile);		//저장 경로가 존재하는지 확인하기 위해 checkFile 호출
						if(check==true)			//저장경로가 존재하는 경우
						{System.out.println("파일이 존재합니다. 덮어 쓰시겠습니까?(y/n)");
						answer=sc.nextLine();		//덮어쓰기 여부를 answer에 저장
						if(answer.equals("y")){		//파일 덮어쓰기
							improvedEditor.printToFile(writeFile, false,temp);	//loadWritingFile을 호출
						}
						else {   //파일 이어쓰기
							improvedEditor.printToFile(writeFile, true,temp); //loadWritingFile을 호출
							}
					}
					else		//파일이 존재하지 않을 경우 저장경로에 파일을 새로 생성해서 쓰기
					{
						if(improvedEditor.printToFile(writeFile, true,temp)){
							System.out.println("파일 경로로 검색결과를 출력하겠습니다.");
						}
					}
					}
				}
					break;
					
			case "R":	case "r":
				if (check == false) {		//파일 경로를 입력받지 않은 경우
					System.out.println("정확한 파일 경로를 먼저 입력해주세요");
				} 
				else{		//파일 경로를 입력받은 경우
					System.out.println("검색할 단어를 입력하세요");
					word=sc.nextLine();		//word에 검색할 단어 저장
					System.out.println("교체할 단어를 입력하세요");
					replaceWord=sc.nextLine();
					System.out.println("출력할 파일 경로를 입력하세요");
					writeFile=sc.nextLine();
					improvedEditor.replaceWord(word, replaceWord,readFile,writeFile);
				}
				break;
			case "A": case "a":
				System.out.println("덧붙일 단어를 입력하세요");
				word=sc.nextLine();
				System.out.println("덧붙일 라인을 입력하세요");
				line[0]=Integer.parseInt(sc.nextLine());
				System.out.println("덧붙일 인덱스값을 입력하세요");
				index=Integer.parseInt(sc.nextLine());
				System.out.println("출력할 파일 경로를 입력하세요");
				writeFile=sc.nextLine();
				improvedEditor.appendStr(readFile,writeFile,word, line[0],index);
				break;
				
			case "L": case "l":
				System.out.println("검색할 단어를 입력하세요");
				word=sc.nextLine();
				System.out.println("검색할 라인들을 입력하고, '0'을 입력하세요");
				while(true){	//0을 입력할 때까지 배열에 차례대로 라인 저장
					line[i]=Integer.parseInt(sc.nextLine());
					if(line[i]==0)
						break;
					i++;
				}
				System.out.println("출력할 파일 경로를 입력하세요");
				writeFile=sc.nextLine();
				improvedEditor.searchWord(word, line);
				break;
				
			case "Q":	case "q":
				System.out.println("프로그램을 종료합니다.");
				isExitProgram = true;
				break;
			}
		}
		sc.close();
	}
}
