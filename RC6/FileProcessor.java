

	import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
	import java.io.FileReader;
	import java.io.IOException;
	import java.net.URL;
	public class FileProcessor {
			private FileReader fileReader = null;
			private String fileName = null;
			private ClassLoader classLoader = null;
			private URL urlToFilePath = null;
			private BufferedReader getSingleLine = null;
			
			public FileProcessor(String Filename) {
				this.fileName = Filename;
				classLoader = Thread.currentThread().getContextClassLoader();
				urlToFilePath = classLoader.getResource(getFileName());
			}
			public void openFile(){
				try {
					fileReader = new FileReader(new File(getFileName()));
					getSingleLine = new BufferedReader(fileReader);
					
				} catch (FileNotFoundException fileNotFoundException) {
					System.err.println("File not found exception");
					System.exit(1);
				}
			}
			
			public String readLineFromFile() {
				String lineReadFromFile = null;
				try {
					lineReadFromFile = getSingleLine.readLine();
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println("File Read exception");
					System.exit(1);
				}
				return lineReadFromFile;
			}
			public String getFileName() {
				return fileName;
			}
			public URL getUrlToFilePath() {
				return urlToFilePath;
			}
}


