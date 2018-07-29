package cr.ac.cenfotec.encryption.properties;

import cr.ac.cenfotec.encryption.types.EncryptionTypes;

public class EncryptionPropertiesFactory {
	
	public static EncryptionProperties create(EncryptionTypes encrytionType) {		
		switch(encrytionType) {
		case SYMMETRIC:
			return new SymmetricProperties();
		case ASYMMETRIC:
			return new AsymmetricProperties();
		default:
			return null;	
		}
		
	}

}
