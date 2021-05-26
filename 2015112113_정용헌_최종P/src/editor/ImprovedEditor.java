package editor;
import java.io.*;
public class ImprovedEditor extends Editor{
	/**
	 * 해당 라인에 str을 append해 저장
	 * @param str 해당라인에 덧붙일 str
	 * @param line 덧붙일 라인 
	 */
	void appendStr(String inputPath, String outputPath, String str, int line, int index) throws IOException{
		int ln=1;	//ln:라인
		String temp;	//temp:해당 라인의 스트링을 임시로 저장
		BufferedReader input=new BufferedReader(new FileReader(inputPath));		//여기서부터는 검색파일에서 임시파일로 수정결과 출력
		BufferedWriter output=new BufferedWriter(new FileWriter("temp.txt"));	//temp.txt:임시파일
		while(ln<=85000){
			if(ln==line){	//해당 라인까지 도달하면
				temp=input.readLine();
				temp=temp.substring(1,index)+str+temp.substring(index);//라인의 알맞은 인덱스값에 단어를 덧붙임
				output.write(temp);
				output.newLine();
				output.flush();
				ln++;
			}
			else{		//해당 라인이 아닌 경우 스트링을 그대로 출력한다.
				temp=input.readLine();
				output.write(temp);
				output.newLine();
				output.flush();
				ln++;
			}
		}
		ln=1;
		input.close(); output.close();
		input=new BufferedReader(new FileReader("temp.txt"));	//여기서부터는 출력파일로 복사하는 과정으로 위 과정과 같다.
		output=new BufferedWriter(new FileWriter(outputPath));
		while(ln<=84999){
			output.write(input.readLine());
			output.newLine();
			output.flush();
			ln++;
			}
		output.write(input.readLine());
		output.flush();
		input.close();
		output.close();
		System.out.println("파일출력이 완료되었습니다.");
	}
	
	/**
	 * 해당 라인에서만 검색
	 * @param word	찾을 단어
	 * @param line	찾을 라인
	 */
	void searchWord(String word, int[] line) throws IOException{
		BufferedReader input=new BufferedReader(new FileReader(Menu.readFile));
		File file=new File(Menu.readFile);
		MyBufferedReader mybuffer=new MyBufferedReader(file);	//MyBufferedReader의 메소드를 사용하기 위해 객체 생성
		boolean check=false;	//check:내용이 출력되었는지 확인
		int i,index,ln=0;		//i:for문에 쓰임, index:해당 라인에서 단어가 존재하는지 확인하기 위한 인덱스값, ln:라인
		String temp,message = "";
		input.mark(1);		//MyBufferedReader의 메소드인 resetToFirst를 사용하기 위해 mark를 지정한다. 이후 해당 mark로 버퍼가 리셋이 된다.
		for(i=0;line[i]!=0;i++){	//line[i]에서 0이 아닐 때까지 반복
			while(ln!=line[i]-1){	//해당 라인(line[i])의 전줄까지 라인을 읽는다.
				input.readLine();
				ln++;
			}
			temp=input.readLine(); ln++;	//해당 라인의 스트링을 temp에 저장
			if(temp.endsWith(" "))	//마지막 단어가 다음줄에 걸치지 않는 경우
				index=temp.indexOf(word+" ");	//검색단어의 인덱스값 저장
			else{		//마지막 단어가 다음줄에 걸치는 경우
				String splitWord[]=input.readLine().split(" "); //다음줄을 호출해서 단어단위로 쪼갠다.
				temp+=splitWord[0];	//temp에 다음줄의 첫번째 단어만 이어붙인다.
				index=temp.indexOf(word+" ");	//검색단어의 인덱스값 저장
			}
			if(index>=0)		//검색단어가 존재할 경우
				message+=ln+"번째 줄의 "+(index+1)+"번째 단어!"+"\r\n";
			else	//검색단어가 존재하지 않는 경우
				message+=ln+"번째 줄에는 "+word+" 를 찾을 수 없습니다"+"\r\n";
			mybuffer.resetToFirst(input);	//MyBufferedReader의 메소드인 resetToFirst를 호출해서 버퍼를 처음으로 리셋
			ln=0;
		}
		check=printToFile(Menu.writeFile, false, message);	//상속받은 클래스의 printToFile메소드를 사용해서 내용 출력
		if(check)
			System.out.println("파일출력이 완료되었습니다.");
	}

}
