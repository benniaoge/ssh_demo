package test;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSATest {
	
	public static void main(String[] args) throws Exception {
		String fsd = "我是中国人";
		
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnKBr+9lyBUU7/kK35zYq9Uo2m83YM6GBiGqt9oX8t9JdjNmE3sFdD34eSIMMm9cTsZQob69IoTToNdDH7P1TpAfu3XYmnet0KdMdn80SaR+QDRD9EKqwgpcWD9PdICe3XkbgadPLWnPGIZhprps2Rv75xbobq/3b70JilAYPXmQIDAQAB";
		String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKcoGv72XIFRTv+QrfnNir1SjabzdgzoYGIaq32hfy30l2M2YTewV0Pfh5Igwyb1xOxlChvr0ihNOg10Mfs/VOkB+7ddiad63Qp0x2fzRJpH5ANEP0QqrCClxYP090gJ7deRuBp08tac8YhmGmumzZG/vnFuhur/dvvQmKUBg9eZAgMBAAECgYBQGxEs5IWaV4vjP8OQNidp6VkHDB+jue0Otrc+YpmfPVsbaaEXXgPD/ChtKBQ95IP1wcohbVbuh2vtrKSTQlt+9B0pt0gbryMeH++wwrShRo3x2FAJr0NsSWZxtapBHGdhrPY205g8AGqrx1AHnt1jFNlLelDM5Bm/rM8oxFI58QJBANt5nHI9zMC/iDoF2ypt7rMFaKe9PPTZaLFluMDfkWIAj4Ckk8mgZEzaAndgCtOaSYvTqEhb+bT3eOJzIjJRxRUCQQDC+Y1QwxJpuRwvo7g1yER6Nrzs2HpsQURK6oP8pC6u1b+wTtOf9YDfnC4OY2YfwUHohFD09zsIy6C1q1SILPF1AkAVtDwNbCEgepBtNIEM/Bwb5hAIboVlrU5WJLoVkMZey7FlRmE7Ejp5AyI1TfEkJeDaDA9bQIU8KTDyK/KYyU9VAkEAsgXnyQkEQ/IX0Uu8g6bjF2/pWpxiU3vYXkr4znIeidZzGwkuY0xnKkTRLKQHeBEeG67MuI8QoPxENfxEx725jQJBAKS4jkOkEkUswwOKah8eFPyuUWnkr3eok19cLAJD5cpkbqKlTIu2Z/HFHYzLyDa5WoQCr/K8AA7RrP4bmZv/fzE=";
		
		String encryptKey = encryptAESKey(getPublicKey(publicKey),generateAESKey());
		System.out.println("encrypt aes key: " + encryptKey);
		
		String result = encrypt(encryptKey,fsd);
		System.out.println(result);
		
		String decryptKey = decryptAESKey(getPrivateKey(privateKey), encryptKey);
		System.out.println("decrypt aes key: " + decryptKey);
		
	}
	
	public static void generateRSA() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");      
	    keyPairGen.initialize(1024); // 密钥位数
	    KeyPair keyPair = keyPairGen.generateKeyPair(); // 密钥对
	    PublicKey publicKey = (RSAPublicKey) keyPair.getPublic(); // 公钥
	    PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate(); // 私钥
	    
	    String publicKeyString = getKeyString(publicKey);  
	    System.out.println("public:\n" + publicKeyString);
	    String privateKeyString = getKeyString(privateKey);
	    System.out.println("private:\n" + privateKeyString);
	}
	
	public static PublicKey getPublicKey(String key) throws Exception {
		 byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);   
         KeyFactory keyFactory = KeyFactory.getInstance("RSA");   
         return keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));   
	}
	
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);   
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");   
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));   
	}
	
	public static String getKeyString(Key key) throws Exception {
		byte[] keyBytes = key.getEncoded();
        return (new BASE64Encoder()).encode(keyBytes);   
	}
	
	public static String encryptAESKey(PublicKey publicKey, String aesKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");//Cipher.getInstance("RSA/ECB/PKCS1Padding");   
        cipher.init(Cipher.ENCRYPT_MODE, publicKey); 
        byte[] enBytes = cipher.doFinal(aesKey.getBytes());
        return parseByte2HexStr(enBytes);
	}
	
	public static String decryptAESKey(PrivateKey privateKey, String aesKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");//Cipher.getInstance("RSA/ECB/PKCS1Padding");   
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[]deBytes = cipher.doFinal(parseHexStr2Byte(aesKey));
        return new String(deBytes);
	}
	
	public static String generateAESKey() throws Exception {
		KeyGenerator generator = KeyGenerator.getInstance ( "AES" );  
        generator.init(256, new SecureRandom());
        SecretKey secretKey = generator.generateKey();
        String secKey = (new BASE64Encoder()).encode(secretKey.getEncoded());
        System.out.println("aes key: " + secKey);
        return secKey;
	}
	
	public static String encrypt(String encryptKey, String content) throws Exception {
		String s = encryptKey;
		System.out.println("sss: " + s);
		
        SecretKeySpec key = new SecretKeySpec(parseHexStr2Byte(encryptKey), "AES");  
        Cipher cipher = Cipher.getInstance("AES");
        byte[] byteContent = content.getBytes("utf-8");
        
        System.out.println((new BASE64Encoder()).encode(key.getEncoded()));
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(byteContent);  
        return new String(result);
	}
	
	public static String parseByte2HexStr(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
                String hex = Integer.toHexString(buf[i] & 0xFF);  
                if (hex.length() == 1) {  
                        hex = '0' + hex;  
                }  
                sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
	}
	
	public static byte[] parseHexStr2Byte(String hexStr) {  
        if (hexStr.length() < 1)  
                return null;
        byte[] result = new byte[hexStr.length()/2];  
        for (int i = 0;i< hexStr.length()/2; i++) {  
                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                result[i] = (byte) (high * 16 + low);  
        }
        return result;  
	}

}
