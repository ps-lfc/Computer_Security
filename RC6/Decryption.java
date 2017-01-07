import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Decryption {
	String ciphertext;
	String userkey;
	String localRead;
	String localReadValue[];
	int s[];
	byte byteArray_key[];
	byte byteArray_ciphertext[];
	private FileProcessor fileProcessor = null;
	public Decryption(FileProcessor fileProcessor_in) {
		fileProcessor = fileProcessor_in;
	}
	StringOperations stringtobyte = new StringOperations();
	public void decrypt(String output_file) {
		localRead = fileProcessor.readLineFromFile();
		localReadValue=localRead.split(":");
		ciphertext = localReadValue[1];
		ciphertext = ciphertext.replace(" ","");
		localRead = fileProcessor.readLineFromFile();
		localReadValue=localRead.split(":");
		userkey = localReadValue[1];
		userkey = userkey.replace(" ","");
		byteArray_key = stringtobyte.stringByte(userkey);
		byteArray_ciphertext = stringtobyte.stringByte(ciphertext);
		s = stringtobyte.KeyScheduler(byteArray_key);
		byte[] decrypted_byte = stringtobyte.decryption(byteArray_ciphertext,s);
		String decrypted_text = stringtobyte.byteString(decrypted_byte);
		decrypted_text = decrypted_text.replaceAll("..", "$0 ");
		
		PrintWriter file = null;
		try {
			file = new PrintWriter(output_file);
		} catch (FileNotFoundException e) {
			System.out.println("Output file not found");
			System.exit(0);
		}
		file.println("plaintext: "+ decrypted_text);
		file.close();
	}
}
