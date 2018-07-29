package cr.ac.cenfotec.encryption.methods;

public interface EncryptionManager {
	
	public void createKey(String name, String method) throws Exception;

	public void encryptMessage(String messageName, String message, String keyName, String method) throws Exception;
	
	public String decryptMessage(String messageName, String keyName, String method) throws Exception;

}
