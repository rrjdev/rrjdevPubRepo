package utility.RSA_EncryDecryPkg;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAEncryptionDecryptionClass {

	public String RSA_EncryptionMethod(String OriginalString) {
		
		KeyPairGenerator keyPairGenerator = null;
		try {
			keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SecureRandom secureRandom = new SecureRandom();

		keyPairGenerator.initialize(2048,secureRandom);

		KeyPair pair = keyPairGenerator.generateKeyPair();

		PublicKey publicKey = pair.getPublic();

		String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());

		System.out.println("public key = "+ publicKeyString);

		PrivateKey privateKey = pair.getPrivate();

		String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());

		System.out.println("private key = "+ privateKeyString);

		//Encrypt Hello world message
		Cipher encryptionCipher = null;
		try {
			encryptionCipher = Cipher.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			encryptionCipher.init(Cipher.ENCRYPT_MODE,privateKey);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] encryptedMessage = null;
		try {
			encryptedMessage = encryptionCipher.doFinal(OriginalString.getBytes());
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String encryptedText = Base64.getEncoder().encodeToString(encryptedMessage);
		
		return encryptedText;
}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RSAEncryptionDecryptionClass redo = new RSAEncryptionDecryptionClass();
		String OriginalString = "Hello World";
		String encryptedString = redo.RSA_EncryptionMethod(OriginalString);
		
		System.out.println("encrypted message = "+ encryptedString);

	}

}
