package cr.ac.cenfotec.encryption.methods;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import cr.ac.cenfotec.encryption.properties.EncryptionProperties;
import cr.ac.cenfotec.encryption.properties.EncryptionPropertiesFactory;
import cr.ac.cenfotec.encryption.types.EncryptionTypes;

public class SymmetricManager implements EncryptionManager{

	private final int KEYSIZE;
	private final String KEY_EXTENSION;
	private final String MESSAGE_ENCRYPT_EXTENSION ;
	private final String PATH;
	private final EncryptionProperties properties;
	private final Map<String, String> propertiesMap;
	
	public SymmetricManager() {
		this.properties = EncryptionPropertiesFactory.create(EncryptionTypes.SYMMETRIC);
		this.propertiesMap = properties.loadEncryptionProperties();
		
		this.KEYSIZE = Integer.parseInt(propertiesMap.get("KEYSIZE"));
		this.KEY_EXTENSION = propertiesMap.get("KEY_EXTENSION");
		this.MESSAGE_ENCRYPT_EXTENSION = propertiesMap.get("MESSAGE_ENCRYPT_EXTENSION");
		this.PATH = propertiesMap.get("PATH");
	}
	
	public void createKey(String name, String method) throws Exception {
		byte [] key = generatedSequenceOfBytes();
		writeBytesFile(name,key,KEY_EXTENSION);
	}

	public void encryptMessage(String messageName, String message, String keyName, String method) throws Exception {
		byte[] key = readKeyFile(keyName);
		Cipher cipher = Cipher.getInstance(method);
		SecretKeySpec k = new SecretKeySpec(key,method);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		byte[] encryptedData = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
	    Encoder oneEncoder = Base64.getEncoder();
	    encryptedData = oneEncoder.encode(encryptedData);
		writeBytesFile(messageName,encryptedData,MESSAGE_ENCRYPT_EXTENSION);
	}
	
	public String decryptMessage(String messageName, String keyName, String method) throws Exception {
		byte[] key = readKeyFile(keyName);
		byte[] encryptedMessage = readMessageFile(messageName);
		Cipher cipher = Cipher.getInstance(method);
		SecretKeySpec k = new SecretKeySpec(key,method);
		cipher.init(Cipher.DECRYPT_MODE, k);
		byte[] DecryptedData = cipher.doFinal(encryptedMessage);
		String message = new String(DecryptedData, StandardCharsets.UTF_8);
		return message;
	}	
	
	private void writeBytesFile(String name, byte[] content, String type) throws FileNotFoundException, IOException {
		FileOutputStream fos = new FileOutputStream(PATH + name + type);
		fos.write(content);
		fos.close();
	}
	
	private byte[] readKeyFile(String keyName) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(PATH + keyName + KEY_EXTENSION));
		String everything = "";
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        line = br.readLine();
		    }
		    everything = sb.toString();
		} finally {
		    br.close();
		}
		return everything.getBytes(StandardCharsets.UTF_8);
	}
	
	private byte[] readMessageFile(String messageName) throws Exception{//se puede cabiar
		File file = new File(PATH + messageName + MESSAGE_ENCRYPT_EXTENSION);
        int length = (int) file.length();
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[length];
        reader.read(bytes, 0, length);
        Decoder oneDecoder = Base64.getDecoder();
	    reader.close();
		return oneDecoder.decode(bytes);
	}

	private byte[] generatedSequenceOfBytes() throws Exception {//no cambiar
		StringBuilder randomkey = new StringBuilder();
		for (int i = 0;i < KEYSIZE;i++){
			randomkey.append(Integer.parseInt(Double.toString((Math.random()+0.1)*1000).substring(0,2)));
		}
		return randomkey.toString().getBytes("UTF-8");
	}

}
