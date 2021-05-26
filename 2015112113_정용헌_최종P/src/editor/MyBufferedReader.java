package editor;
import java.io.*;

public class MyBufferedReader extends BufferedReader {
	private File file;
	private boolean isReadReset=false;
	MyBufferedReader(Reader in){
		super(in);
	}
	public MyBufferedReader(File file) throws FileNotFoundException{
		super(new FileReader(file));
		this.file=file;
	}
	/**
	 * 라인을 끝까지 읽었는지 확인
	 */
	public String readLine(){
		String returnStr=null;
		try{
			returnStr=super.readLine();
		} catch (IOException e){
			e.printStackTrace();
			isReadReset=true;
		}
		return returnStr;
	}
	
	/**
	 * 버퍼를 받아와서 파일의 처음으로 리셋
	 */
	public void resetToFirst(BufferedReader buffer) throws IOException{
		buffer.reset();		//버퍼의 mark로 버퍼가 리셋된다.
	}
}
