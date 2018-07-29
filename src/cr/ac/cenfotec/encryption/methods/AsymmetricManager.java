package cr.ac.cenfotec.encryption.methods;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Map;

import javax.crypto.Cipher;

import cr.ac.cenfotec.encryption.properties.EncryptionProperties;
import cr.ac.cenfotec.encryption.properties.EncryptionPropertiesFactory;
import cr.ac.cenfotec.encryption.types.EncryptionTypes;

public class AsymmetricManager implements EncryptionManager {

	private final String KEY_EXTENSION;
	private final String PUBLIC;
	private final String PRIVATE;
	private final String MESSAGE_ENCRYPT_EXTENSION;
	private final String PATH;
	private final EncryptionProperties properties;
	private final Map<String, String> propertiesMap;
	
	public AsymmetricManager() {
		properties = EncryptionPropertiesFactory.create(EncryptionTypes.ASYMMETRIC);
		propertiesMap = properties.loadEncryptionProperties();
		
		this.KEY_EXTENSION = propertiesMap.get("KEY_EXTENSION");
		this.PUBLIC = propertiesMap.get("PUBLIC");
		this.PRIVATE = propertiesMap.get("PRIVATE");
		this.MESSAGE_ENCRYPT_EXTENSION = propertiesMap.get("MESSAGE_ENCRYPT_EXTENSION");
		this.PATH = propertiesMap.get("PATH");
	}
		
	public void createKey(String name,String method) throws Exception {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance(method);
		KeyFactory fact = KeyFactory.getInstance(method);
		kpg.initialize(2048);
		KeyPair kp = kpg.genKeyPair();
		RSAPublicKeySpec pub = fact.getKeySpec(kp.getPublic(),
		  RSAPublicKeySpec.class);
		RSAPrivateKeySpec priv = fact.getKeySpec(kp.getPrivate(),
		  RSAPrivateKeySpec.class);

		saveToFile(PATH + name+"public.key", pub.getModulus(),
		  pub.getPublicExponent());
		saveToFile(PATH + name+"private.key", priv.getModulus(),
		  priv.getPrivateExponent());
	}
	
	public void saveToFile(String fileName,BigInteger mod, BigInteger exp) throws IOException {
		ObjectOutputStream oout = new ObjectOutputStream(
			    new BufferedOutputStream(new FileOutputStream(fileName)));
		try {
			oout.writeObject(mod);
			oout.writeObject(exp);
		} catch (Exception e) {
			throw new IOException("Unexpected error", e);
		} finally {
		    oout.close();
		}
	}

	public void encryptMessage(String messageName, String message, String keyName, String method) throws Exception {
		PublicKey pubKey = (PublicKey)readKeyFromFile(keyName, PUBLIC,method);
		Cipher cipher = Cipher.getInstance(method);
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		byte[] encryptedData = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
	    Encoder oneEncoder = Base64.getEncoder();
	    encryptedData = oneEncoder.encode(encryptedData);
		writeBytesFile(messageName,encryptedData,MESSAGE_ENCRYPT_EXTENSION);
	}
	
	public String decryptMessage(String messageName, String keyName, String method) throws Exception {
		PrivateKey privKey = (PrivateKey)readKeyFromFile(keyName, PRIVATE, method);
		Cipher cipher = Cipher.getInstance(method);
		cipher.init(Cipher.DECRYPT_MODE, privKey);
		byte[] encryptedMessage = readMessageFile(messageName);
		byte[] decryptedData = cipher.doFinal(encryptedMessage);
	    String message = new String(decryptedData,StandardCharsets.UTF_8);
		return message;
	}
	
	
	private void writeBytesFile(String name, byte[] content, String type) throws FileNotFoundException, IOException {
		FileOutputStream fos = new FileOutputStream(PATH + name + type);
		fos.write(content);
		fos.close();
	}
	
	private Key readKeyFromFile(String keyFileName, String type, String method) throws IOException {
		  InputStream in = new FileInputStream (PATH + keyFileName+ type + KEY_EXTENSION);
		  ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(in));
		  try {
		    BigInteger m = (BigInteger) oin.readObject();
		    BigInteger e = (BigInteger) oin.readObject();
		    if (type.equals("public")) {
			    RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
			    KeyFactory fact = KeyFactory.getInstance(method);
			    PublicKey pubKey = fact.generatePublic(keySpec);
			    return pubKey;		    	
		    } else {
		    	RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m, e);
			    KeyFactory fact = KeyFactory.getInstance(method);
			    PrivateKey pubKey = fact.generatePrivate(keySpec);
			    return pubKey;		    	
		    }
		  } catch (Exception e) {
		    throw new RuntimeException("Spurious serialisation error", e);
		  } finally {
		    oin.close();
		  }
		}
	
	private byte[] readMessageFile(String messageName) throws Exception{
		File file = new File(PATH + messageName + MESSAGE_ENCRYPT_EXTENSION);
        int length = (int) file.length();
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[length];
        reader.read(bytes, 0, length);
        reader.close();
        Decoder oneDecoder = Base64.getDecoder();
		return oneDecoder.decode(bytes);
		
	}

}
