package utility.AES_EncryDecryPkg;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
 
public class AESEncryptionDecryptionClass {
	
	public String AES_EncryptionMethod(String OriginalString) {
		
		KeyGenerator keyGenerator = null;
		try {
			keyGenerator = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        SecretKey secretKey = keyGenerator.generateKey();
        		
        String EncryptedString = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        
		return EncryptedString;
	}
	
	public String AES_DecryptionMethod(String EncryptedString) {
		
		return decryptedText;
		}
    
	public static void main(String[] args) throws NoSuchAlgorithmException {
		
		String inputPlainText = "Hello world";
		//String inputEncryptedText = "";
		
		System.out.println("---------Encryption Decryption---------\n");
		System.out.println("Input Text-->" + inputPlainText);
		
		AESEncryptionDecryptionClass aedo = new AESEncryptionDecryptionClass();
		String encryptedText = aedo.AES_EncryptionMethod(inputPlainText);
		
		System.out.println("Encrypted Text-->" + encryptedText);
		
		String inputEncryptedText = encryptedText;
		String decryptedText = aedo.AES_DecryptionMethod(inputEncryptedText);
		
		System.out.println("Decrypted Text-->" + decryptedText);
    }
}
