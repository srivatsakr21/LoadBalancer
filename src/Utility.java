import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
public class Utility {
	private static final FileSystem fs=FileSystems.getDefault();
	private static final String fileName="data.txt";
	private static final Path file=fs.getPath(fileName);
	private static BufferedReader reader;
	static {
		reader=null;
	}
	private static void open() {
		try { reader=Files.newBufferedReader(file); } catch(IOException ioex){}
	}
	private static void close() {
		if(reader!=null)
			try {
				reader.close();
			} catch(IOException ioex) {
			} finally {
				reader=null;
			}
	}
	
	public static String getDataSet() {
		String dataSet=null;
		if(reader==null) open();
		try {
			dataSet=reader.readLine();
			if(dataSet==null) {
				close();
				open();
				dataSet=getDataSet();
			} 
		} catch(IOException ioex) {
		} finally {
			return dataSet;
		}
	}
}