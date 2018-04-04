import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;


public class Decrypt {
    private static Cipher dcipher;
    private static byte[] salt = {
	(byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
	(byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
    };
    private static int iterationCount = 19;
    
    public static void main(String[] args) throws Exception {
	System.out.println(decrypt("NS2+kczxjbE="));
	System.out.println(decrypt("pvgQGnkAKdvxCqWNi1Bm1fFAPmVd6bG+"));
	System.out.println(decrypt("	"));
	System.out.println(decrypt("0s5/HRsihTgZyYrNQYxilA=="));
	System.out.println(decrypt("n9a8VYF43gKmf9l3qzcAyQ=="));
	
    }
    
    public static String decrypt(String encryptedText) throws Exception
	{
		if(encryptedText.indexOf("\\n") != -1)
		{
			StringBuffer sb = new StringBuffer();
			sb.append(encryptedText.substring(0, encryptedText.indexOf("\\n")));
			sb.append("\n");
			sb.append(encryptedText.substring(encryptedText.indexOf("\\n")+2));
			encryptedText = sb.toString();
		}
		String secretKey = "Manhattan";
		KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
		SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);        
		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
		dcipher=Cipher.getInstance(key.getAlgorithm());
		dcipher.init(Cipher.DECRYPT_MODE, key,paramSpec);
		byte[] enc = new sun.misc.BASE64Decoder().decodeBuffer(encryptedText);
		byte[] utf8 = dcipher.doFinal(enc);
		String charSet="UTF-8";     
		String plainStr = new String(utf8, charSet);
		return plainStr;
	} 
}
