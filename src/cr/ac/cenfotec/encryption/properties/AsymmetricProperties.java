package cr.ac.cenfotec.encryption.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class AsymmetricProperties implements EncryptionProperties {
	
	@Override
	public void writeEncryptionProperties(){	
		Properties prop = new Properties();
		OutputStream output = null;
		
		try {
			
			output = new FileOutputStream("asymmetric.properties");
			
			prop.setProperty("KEY_EXTENSION", ".key");
			prop.setProperty("PUBLIC", "public");
			prop.setProperty("PRIVATE", "private");		
			prop.setProperty("MESSAGE_ENCRYPT_EXTENSION", ".encript");
			prop.setProperty("PATH", "C:/encrypt/asymetric/");
			
			prop.store(output, null);
			
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}			
	}
	
	@Override
	public Map<String,String> loadEncryptionProperties() {
		InputStream input = null;
		
		try {
			input = new FileInputStream("asymmetric.properties");
			
		}catch(FileNotFoundException e){
			writeEncryptionProperties();	
		}
		
		Properties prop = new Properties();	
		Map<String,String> propertiesMap = new TreeMap<String,String>();
		
		try {
			
			input = new FileInputStream("asymmetric.properties");
			prop.load(input);
					
			propertiesMap.put("KEY_EXTENSION", prop.getProperty("KEY_EXTENSION"));
			propertiesMap.put("PUBLIC", prop.getProperty("PUBLIC"));
			propertiesMap.put("PRIVATE", prop.getProperty("PRIVATE"));
			propertiesMap.put("MESSAGE_ENCRYPT_EXTENSION", prop.getProperty("MESSAGE_ENCRYPT_EXTENSION"));
			propertiesMap.put("PATH", prop.getProperty("PATH"));
			
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return propertiesMap;
	}

}
