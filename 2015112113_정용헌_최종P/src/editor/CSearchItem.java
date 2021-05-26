package editor;
import edu.dongguk.cse.pl.reportwritter.ISearchItem;
public class CSearchItem implements ISearchItem{

	private int lineNo, indexNo;
	@Override
	public int getIndexNo() {
		// TODO Auto-generated method stub
		return indexNo;
	}

	@Override
	public int getLineNo() {
		// TODO Auto-generated method stub
		return lineNo;
	}

	@Override
	public void setIndexNo(int arg0) {
		// TODO Auto-generated method stub
		indexNo=arg0;
	}

	@Override
	public void setLineNo(int arg0) {
		// TODO Auto-generated method stub
		lineNo=arg0;
	}

	public String toString(){
		return lineNo+"번째 줄의 "+indexNo+"번 째";
	}
}
