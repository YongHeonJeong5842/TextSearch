package editor;

import java.io.*;
import java.util.*;
import java.lang.*;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class Editor {

   static BufferedWriter bufferedWriter;
   private double progress;
   
   public double getProgress(){
      return progress;
   }
   public void setProgress(double p){
      this.progress = p;
   }

   /**
    * 입력받은 경로에 파일이 존재하는지 확인하고 결과를 true or false로 반환
    * 
    * @param path
    *            입력받은 경로
    * @return 경로에 파일이 존재하면 true, 존재하지 않으면 false 반환
    */
   public static boolean checkFile(String path) {
      File textFile = new File(path);
      if (textFile.exists()) {
         return true;
      } else {
         System.out.println("없는 파일입니다.");
         return false;
      }
   }

   /**
    * 이전줄의 마지막 단어와 다음줄의 첫 번째 단어가 걸쳐있는 경우 이 메소드를 통해 찾는 단어와 같은지 확인하고 결과 반환
    * 
    * @param lastWord
    *            이전줄의 마지막 단어
    * @param firstWord
    *            다음줄의 첫번째 단어
    * @param toSearch
    *            찾는 단어
    */
   public static boolean wordCheck(String lastWord, String firstWord, String toSearch) {
      String temp = lastWord + firstWord; // 이전줄 마지막 단어와 다음줄 첫번째 단어를 이어서 생성된
                                 // 객체 temp에 저장한다.
      if (temp.equals(toSearch)) // temp와 찾는 단어(toSearch)가 같다면
         return true; // true 반환
      else // 아닌경우 false 반환
         return false;
   }

   /**
    * 파일 경로, 찾을 단어, 메인함수 콘솔메뉴에서 s,p 중 입력받은 결과를 switchCase 매개변수로 받아 단어를 검색하고
    * 콘솔창에 출력을 한 뒤 결과를 반환한다.
    * 
    * @param readFile
    *            단어를 찾을 텍스트 파일 경로
    * @param findWord
    *            찾을 단어
    * @param switchCase
    *            메인함수 콘솔메뉴에서 s인 경우 true, p인 경우 false로 전달받음
    * @return switchCase 값이 true인 경우 함수 종료, false인 경우 검색결과 내용인 temp 반환
    */
   String searchWord(String readFile, String findWord, boolean switchCase,JProgressBar pBar) throws IOException {
      setProgress(0);
      MyThread thread = new MyThread(pBar,(int)getProgress(), this);
      BufferedReader input = new BufferedReader(new FileReader(readFile));
      int i, num = 0, line = 0, index = 1, startIndex=0;
      ArrayList<CSearchItem> list = new ArrayList<CSearchItem>();
      boolean check;
      String tempWord, temp = "<" + readFile + ">파일에서 " + findWord + "의 검색결과는 다음과 같습니다." + "\r\n";
      /**
       * i: for문을 위한 i index: 열의 수(메모장 인덱스의 col에 해당)->IndexNo에 해당 line: 행의 수
       * (메모장 인덱스의 ln에 해당)->LineNo에 해당 tempWord: 단어가 줄사이에 걸치는 경우 wordCheck
       * 메소드를 호출하고 tempWord를 매개변수로 넘겨준다. temp: 출력결과 저장(기존에 저장했던 방식)
       */
      String str = input.readLine(); // iuput의 메소드인 readLine을 호출해서 텍스트파일의 글을 한
                              // 줄씩 읽어 저장한다.
      String splitWord[] = str.split(" "); // split 메소드를 호출해서 " "를 기준으로 단어를 쪼개
                                    // 배열에 저장
      while (str != null) { // str이 null이 아닐 때까지 실행
         line++; // 라인수에 해당하는라인 값을 1만큼 증가
         if (str.endsWith(" ") == true) // 스트링의 맨 끝 단어가 " "인 경우(단어가 다음줄에 걸치지 않는 경우)
         {
            for (i = startIndex; i < splitWord.length; i++) // for문을 사용해서 i는 시작점부터 splitWord의 길이까지
            {
               if (splitWord[i].equals(findWord)) { // 만약 splitWord의 i번째 단어와 찾는 단어가 같다면
                  CSearchItem csItem = new CSearchItem();
                  temp += line + "째 줄의 " + index + "번째" + "\r\n";
                  csItem.setLineNo(line);
                  csItem.setIndexNo(index);
                  list.add(csItem);
                  index+=splitWord[i].length()+1;// 인덱스 값을 i번째 단어의길이와 1(공백길이)만큼 증가
                  csItem.setIndexNo(index);
                  num++;
               } else // splitWord의 i번째 단어와 찾는 단어가 같지 않다면
               {
                  index += splitWord[i].length() + 1; // 인덱스 값을 i번째 단어의 길이와 1(공백길이)을 증가시킨다.
                  continue;
               }
            }
            setProgress(((double)line/85000)*100);
            str = input.readLine(); // if문이 끝나면 한 줄을 읽는 readLine 메소드를 호출해서 그 다음줄을 읽어와 저장한다.
            if (str != null){ // 만약 str이 null값이 아니라면
               splitWord = str.split(" "); // 단어를 쪼개는 메소드인 split을 호출해서 공백을 기준으로 쪼개서 splitWord에 저장한다.
               startIndex=0;
               index=1;
               }
            else
               break;
         } 
         else // 스트링의 맨 끝 단어가 " "이 아닌 경우 (단어가 다음줄에 걸치는 경우)
         {

            for (i = startIndex; i < splitWord.length-1; i++) // for문을 사용해서 i는 시작점부터 (splitWord의 길이-1)까지
            {
               if (splitWord[i].equals(findWord)) { // 만약 splitWord의 i번째 단어가 toSearch와 같다면
                  CSearchItem csItem = new CSearchItem();
                  temp += line + "째 줄의 " + index + "번째" + "\r\n";
                  csItem.setLineNo(line);
                  csItem.setIndexNo(index);
                  list.add(csItem);

                  index += splitWord[i].length() + 1; // 인덱스 값을 단어의 길이+1만큼
                                             // 증가. 이 때 1은 공백
                                             // 길이다.
                  num++;

               } else // 그렇지 않은 경우
                  index += splitWord[i].length() + 1;
               continue; // 인덱스 값을 단어의 길이+1만큼 증가. 이 때 1은 공백 길이다.

            }
            tempWord = splitWord[splitWord.length-1]; // 이 줄의 마지막 단어를 임시로
                                             // 저장
            str = input.readLine(); // 줄 읽어오는 메소드 호출해서 다음줄 저장
            if (str != null){ // 그 다음줄이 null이 아닌 경우
               splitWord = str.split(" "); // 단어 쪼개기
               startIndex=1;
               }
            else{
               if(tempWord.equals(findWord)){
                  CSearchItem csItem = new CSearchItem();
                  temp += line + "째 줄의 " + index + "번째" + "\r\n";
                  csItem.setLineNo(line);
                  csItem.setIndexNo(index);
                  list.add(csItem);
                  num++;
                  break;
               }
            }
         // wordCheck메소드 호출해서 걸친 단어가 찾는 단어와 일치하는지 확인
            check = wordCheck(tempWord, splitWord[0], findWord);
            
            if (check == true) // 걸친 단어가 찾는 단어와 같은 경우
            {
               CSearchItem csItem = new CSearchItem();
               temp += line + "번 째 줄의 " + index + "번 째" + "\r\n";
               csItem.setLineNo(line);
               csItem.setIndexNo(index);
               list.add(csItem);
               num++;
            }
            index=splitWord[0].length()+2;
            setProgress((((double)(line))/85000)*100);
         }

      }
      setProgress(100);
      
      // 출력하는 부분
      for (i = 0; i < list.size(); i++) {
         System.out.println(list.get(i).toString());
      }
      System.out.println("총 " + num + "번 검색되었습니다.");
      temp += "이상 검색결과로 총 " + num + "개의 검색이 완료되었습니다." + "\r\n";
      if (switchCase == true)
         return null; // s를 입력받은 경우 함수종료
      else
         return temp; // p를 입력받은 경우 temp 반환
   }

   /**
    * 파일 경로를 인자로 받아와서 경로로 파일을 열어보고 예외가 있는지 확인하고 파일에 출력하는 함수
    * 
    * @param path
    *            입력받은 경로
    * @param isAppend
    *            덮어쓰기(false)인지 이어쓰기(true)인지 여부
    * @return 예외가 있는 경우 false, 없는 경우 true 반환
    * @throws IOException
    */
   public static boolean printToFile(String path, boolean isAppend, String temp) throws IOException {
      File textFile = new File(path);
      try {
         FileWriter fileWriter = new FileWriter(textFile, isAppend);
         BufferedWriter output = new BufferedWriter(new FileWriter(path));
         output.write(temp); // 경로에 파일 출력
         output.close();
      } catch (IOException e) {
         e.printStackTrace();
         return false; // 예외가 생길 경우 false반환
      }
      return true; // 예외가 생기지 않은 경우 true반환
   }

   /**
    * 해당 word를 검색해 원하는 word로 바꿔주는 메소드
    * 
    * @param word:
    *            교체될 시 교체되는 단어
    * @param replaceWord:
    *            교체될 시 교체하는 단어
    * @param readFile:
    *            검색할 파일 경로
    * @param writeFile:
    *            출력할 파일 경로
    */
   void replaceWord(String word, String replaceWord, String readFile, String writeFile) throws IOException {
      StringBuilder str = new StringBuilder(); // str: readFile로부터 한 줄씩 읽어와서
                                       // 저장
      int index; // index: 검색단어의 인덱스값
      if (checkFile(readFile) == false)
         return; // 검색파일이 존재하지 않는 경우 리턴

      BufferedReader input = new BufferedReader(new FileReader(readFile)); // 여기서부터는
                                                            // 검색파일에서
                                                            // 임시파일로
                                                            // 수정결과
                                                            // 출력
      BufferedWriter output = new BufferedWriter(new FileWriter("temp.txt")); // temp.txt:임시파일
      str.append(input.readLine()); // iuput의 메소드인 readLine을 호출해서 텍스트파일의 글을 한
                              // 줄씩 읽어 저장한다.
      while (str != null) {
         if (str.length() <= 129) // str의 길이가 129보다 작거나 같을 경우 다음 줄 이어붙이기
            str.append(input.readLine());
         index = str.indexOf(word + " "); // 검색단어의 인덱스값 저장
         if (index >= 0) { // str에 검색단어가 존재하는 경우
            str.delete(index, index + word.length()); // 검색단어 삭제
            str.insert(index, replaceWord); // 치환단어 삽입
         }
         if (str.length() < 129) { // 마지막줄 출력할 때
            output.write(str.substring(0, str.length() - 4)); // 여기서 4는
                                                   // null을 의미함
            str.delete(0, str.length());
            break;
         } else {
            output.write(str.substring(0, 128)); // 129글자씩 출력
            output.newLine();
            output.flush(); // 버퍼 비움
            str.delete(0, 128); // 출력한 내용을 str에서 삭제
         }
      }
      input.close();
      output.close();

      input = new BufferedReader(new FileReader("temp.txt")); // 여기서부터는 출력파일로
                                                // 복사하는 과정으로 위
                                                // 과정과 같다.
      output = new BufferedWriter(new FileWriter(writeFile));
      str.append(input.readLine());
      while (str != null) {
         if (str.length() <= 129)
            str.append(input.readLine());
         if (str.length() < 129) {
            output.write(str.substring(0, str.length() - 4));
            break;
         } else {
            output.write(str.substring(0, 128));
            output.newLine();
            output.flush();
            str.delete(0, 128);
         }
      }
      System.out.println("파일이 출력되었습니다.");
      input.close();
      output.close();
   }
}