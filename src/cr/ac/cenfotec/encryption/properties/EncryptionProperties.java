package cr.ac.cenfotec.encryption.properties;

import java.util.Map;

public interface EncryptionProperties {
	
	public void writeEncryptionProperties();
	
	public Map<String, String> loadEncryptionProperties();
	
}
