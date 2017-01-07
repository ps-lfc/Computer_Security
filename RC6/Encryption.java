import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Encryption {
	String plaintext;
	String userkey;
	String localRead;
	String localReadValue[];
	byte[] byteArray_key;
	byte[] byteArray_plaintext;
	private FileProcessor fileProcessor = null;
	public Encryption(FileProcessor fileProcessor_in) {
		fileProcessor = fileProcessor_in;
	}
	StringOperations stringtobyte = new StringOperations();
	public void encrypt(String output_file) {
		localRead = fileProcessor.readLineFromFile();
		localReadValue=localRead.split(":");
		plaintext = localReadValue[1];
		plaintext = plaintext.replace(" ","");
		localRead = fileProcessor.readLineFromFile();
		localReadValue=localRead.split(":");
		userkey = localReadValue[1];
		userkey = userkey.replace(" ","");
		byteArray_key = stringtobyte.stringByte(userkey);
		byteArray_plaintext = stringtobyte.stringByte(plaintext);
		
		int[] s = stringtobyte.KeyScheduler(byteArray_key);
		
		byte[] encrypted_byte = stringtobyte.encryption(byteArray_plaintext,s);
		String encrypted_text = stringtobyte.byteString(encrypted_byte);
		
		encrypted_text = encrypted_text.replaceAll("..", "$0 ");
		
		PrintWriter file = null;
		try {
			file = new PrintWriter(output_file);
		} catch (FileNotFoundException e) {
			System.out.println("Output file not found");
			System.exit(0);
		}
		file.println("ciphertext: "+ encrypted_text);
		file.close();
	}
}
