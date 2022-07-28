package utility.base64EncodeDecode;

import java.util.Base64;

public class Base64EncodeDecoderClass {
	
	public static String encodeContent(String plainText)
            throws Exception {
        String encodeBytes = Base64.getEncoder().encodeToString(plainText.getBytes());
        
        return encodeBytes;
    }
	
    public static String decodeContent(String encodedText)
            throws Exception {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedText.getBytes());
        return new String(decodedBytes);
    }

	public static void main(String[] args) {

		String plainText = "Hello World";
		
        System.out.println("Plain Text Before Encoding: " + plainText);
        String encodedText = null;
		try {
			encodedText = Base64EncodeDecoderClass.encodeContent(plainText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Encrypted Text After Encoding: " + encodedText);
        
        String decodedText = null;
		try {
			decodedText = Base64EncodeDecoderClass.decodeContent(encodedText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Decoded Text After Decoding: " + decodedText);

	}

}
