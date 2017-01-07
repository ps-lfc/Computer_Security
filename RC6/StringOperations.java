public class StringOperations {
	int P = 0xB7E15163;
	int Q = 0x9E3779B9;
	int w = 32;
	int r = 20;
	public byte[] stringByte(String s) {
		int str_length = s.length();
		byte[] byte_data = new byte[str_length/2];
		for(int i=0;i<str_length;i+=2){
			byte_data[i/2] = (byte) ((Character.digit(s.charAt(i), 16)<<4) + Character.digit(s.charAt(i+1), 16));
		}
		return byte_data;
	}

	public String byteString(byte[] b) {
	    StringBuilder builder = new StringBuilder();
	    for(byte by : b) {
	        builder.append(String.format("%02x", by));
	    }
	    return builder.toString();
	}
	
	public int[] KeyScheduler(byte[] key){
		int S[] = new int[2 * r + 4];
		int A;
		int B;
		int i;
		int j;
		int k;
		int v;
		int c = key.length/(w/8);
		int[] L = byteToInt(key,  c);
		S[0] = P;
		for(i = 1;i <(2 * r + 4);i++){
			S[i] = S[i-1] + Q;
		}
		A = B = i = j = 0;
		v = 3 * Math.max(c, (2 * r + 4));
		for(k = 0; k < v;k++){
			A = S[i] = Integer.rotateLeft((S[i] + A + B),3);
			B = L[j] = Integer.rotateLeft((L[j]+A+B), A+B);
			i = (i+1)% (2 * r + 4);
			j = (j+1) % c;
		}
		return S;
	}
	public byte[] encryption(byte[] text,int[] S){
		int i;
		
		int[] temp_text = new int[text.length/4];
		temp_text = byteToInt(text, temp_text.length);
		int A=temp_text[0];
		int B=temp_text[1];
		int C=temp_text[2];
		int D=temp_text[3];

		int t;
		int u;
		B = B + S[0];
		D = D + S[1];
		for(i=1;i<=r;i++){
			t = Integer.rotateLeft(B * (2*B + 1), 5);
			u = Integer.rotateLeft(D * (2 * D + 1), 5);
			A = Integer.rotateLeft(A^t,u)+S[2*i];
			C = Integer.rotateLeft(C^u,t)+S[2*i+1];
			int temp;
			temp=A;
			A=B;
			B=C;
			C=D;
			D=temp;
		}
		A = A + S[2*r+2];
		C = C +S[2*r+3];
		temp_text[0] = A;
		temp_text[1] = B;
		temp_text[2] = C;
		temp_text[3] = D;
		byte[] encrypted_array = intToByte(temp_text, text.length);
		return encrypted_array;
	}
	public byte[] decryption(byte[] text, int[] s) {
		int i;
		int[] temp_text = new int[text.length/4];
		for(i=0;i<temp_text.length;i++){
			temp_text[i]=0;
		}
		temp_text=byteToInt(text, temp_text.length);
		int A=temp_text[0];
		int B=temp_text[1];
		int C=temp_text[2];
		int D=temp_text[3];
		int t;
		int u;
		C = C - s[2*r+3];
		A = A - s[2*r+2];
		int temp;
		for(i=r;i>=1;i--){
			temp=D;
			D=C;
			C=B;
			B=A;
			A=temp;
			t = Integer.rotateLeft(B * (2*B + 1), 5);
			u = Integer.rotateLeft(D * (2 * D + 1), 5);
			A = Integer.rotateRight(A-s[2*i],u)^t;
			C = Integer.rotateRight(C-s[2*i+1], t)^u;
		}
		D = D - s[1];
		B = B - s[0];
		temp_text[0] = A;
		temp_text[1] = B;
		temp_text[2] = C;
		temp_text[3] = D;
		byte[] decrypted_array = intToByte(temp_text, text.length);
		return decrypted_array;
	}
	public int[] byteToInt(byte[] arr,int length){
		int[]  byte_to_int=new int[length];
		for(int j=0; j<byte_to_int.length; j++)
		{
			byte_to_int[j] = 0;
		}
		
		int counter = 0;
		for(int i=0;i<byte_to_int.length;i++){
			byte_to_int[i] = ((arr[counter++]&0xff))|((arr[counter++]&0xff) << 8) |((arr[counter++]&0xff) << 16) |((arr[counter++]&0xff) << 24);
		}
		return byte_to_int;
		
	}
	public byte[] intToByte(int[] int_Array,int length){
		int i;
		byte intbyte[]=new byte[length];
		for(i = 0 ; i<length ; i++){
			intbyte[i] = (byte)((int_Array[i/4] >>>(i%4)*8) & 0xff);
		}
		
		return intbyte;
	}
	public int[] bytetoInt(byte[] userkey,int c) 
	{			int i;
				int offset;
				int byteint[] = new int[c];
				for (i = 0; i < byteint.length; i++)
					byteint[i] = 0;

				for (i = 0, offset = 0; i < c; i++)
					byteint[i] = ((userkey[offset++] & 0xFF)) | ((userkey[offset++] & 0xFF) << 8)| ((userkey[offset++] & 0xFF) << 16) | ((userkey[offset++] & 0xFF) << 24);
				
				return byteint;
			}			
}