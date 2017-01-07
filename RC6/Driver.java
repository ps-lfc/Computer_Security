import java.io.FileNotFoundException;

public class Driver {
		public static void main(String[] args) throws FileNotFoundException {
			if(args.length != 2) {
				System.err.println("Usage Improper: Please provide the arguments as per the assignemnt requirement");
				System.exit(1);
			}
			FileProcessor fileProcessor = new FileProcessor(args[0]);
				fileProcessor.openFile();
				Encryption encryption = new Encryption(fileProcessor);
				Decryption decryption = new Decryption(fileProcessor);
				String lineReadFromfile = fileProcessor.readLineFromFile();
				if(lineReadFromfile.equals("Encryption")){
					encryption.encrypt(args[1]);
				}
				if(lineReadFromfile.equals("Decryption")){
						decryption.decrypt(args[1]);
				}
				
		}
}
