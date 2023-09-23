import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CHSearchBackendFD implements CHSearchBackendInterface{
	//constructor 
	public CHSearchBackendFD() {
		
	}
	public CHSearchBackendFD(HashtableWithDuplicateKeysInterface<String,PostInterface> hashtable, PostReaderInterface postReader) {
		
	}
	@Override
	public void loadData(String filename) throws FileNotFoundException {
		return;
	}

	@Override
	public List<String> findPostsByTitleWords(String words) {
		List<String> arr=new LinkedList<>();
		arr.add("called findPostsByTitleWords");
		return arr;
	}

	@Override
	public List<String> findPostsByBodyWords(String words) {
		List<String> arr=new LinkedList<>();
		arr.add("called findPostsByBodyWords");
		return arr;
	}

	@Override
	public List<String> findPostsByTitleOrBodyWords(String words) {
		List<String> arr=new LinkedList<>();
		arr.add("called findPostsByTitleOrBodyWords");
		return arr;
	}

	@Override
	public String getStatisticsString() {
		return "called getStatisticsString";
	}

}
