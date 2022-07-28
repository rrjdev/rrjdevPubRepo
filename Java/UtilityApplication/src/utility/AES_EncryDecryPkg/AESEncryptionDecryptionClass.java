package utility.AES_EncryDecryPkg;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
 
public class AESEncryptionDecryptionClass {
	
	public String AES_EncryptionMethod(String OriginalString,SecretKey secretKey,IvParameterSpec parameterSpec) 
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
    BadPaddingException, InvalidAlgorithmParameterException {
		
        String secretKeyString = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("generated key-->"+secretKeyString);
 
        //Encrypt Hello world message
        Cipher encryptionCipher = null;
		try {
			encryptionCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        try {
			encryptionCipher.init(Cipher.ENCRYPT_MODE,secretKey,parameterSpec);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        byte[] encryptedMessageBytes = null;
		try {
			encryptedMessageBytes = encryptionCipher.doFinal(OriginalString.getBytes());
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String encryptedMessage =
        Base64.getEncoder().encodeToString(encryptedMessageBytes);
        
		return encryptedMessage;
	}
	
	public String AES_DecryptionMethod(String EncryptedString,SecretKey secretKey,IvParameterSpec parameterSpec) 
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
    BadPaddingException, InvalidAlgorithmParameterException {
		
		Cipher decryptionCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        decryptionCipher.init(Cipher.DECRYPT_MODE,secretKey,parameterSpec);
        
        byte[] decryptedMessageBytes = decryptionCipher.doFinal(Base64.getDecoder().decode(EncryptedString));
        String decryptedMessage = new String(decryptedMessageBytes);
 
		return decryptedMessage;
	}
    
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException,
    InvalidKeyException, IllegalBlockSizeException,
    BadPaddingException, InvalidAlgorithmParameterException {
		
		String inputPlainText = "Hello world";
		//String inputEncryptedText = "";
		
		KeyGenerator keyGenerator = null;
		try {
			keyGenerator = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        SecretKey secretKey = keyGenerator.generateKey();
        
        byte[] InitVectorBytes = keyGenerator.generateKey().getEncoded();
        IvParameterSpec parameterSpec = new IvParameterSpec(InitVectorBytes);
		
		System.out.println("---------AES Encryption Decryption---------\n");
		System.out.println("Input Text-->" + inputPlainText);
		
		AESEncryptionDecryptionClass aedo = new AESEncryptionDecryptionClass();
		String encryptedText = aedo.AES_EncryptionMethod(inputPlainText,secretKey,parameterSpec);
		
		System.out.println("Encrypted Text-->" + encryptedText);
		
		String inputEncryptedText = encryptedText;
		String decryptedText = aedo.AES_DecryptionMethod(inputEncryptedText,secretKey,parameterSpec);
		
		System.out.println("Decrypted Text-->" + decryptedText);
    }
}