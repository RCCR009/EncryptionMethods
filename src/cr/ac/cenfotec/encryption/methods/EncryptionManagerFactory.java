package cr.ac.cenfotec.encryption.methods;

import cr.ac.cenfotec.encryption.types.EncryptionTypes;

public class EncryptionManagerFactory {
	
	public static EncryptionManager create(EncryptionTypes encryptionTypes) {
		switch(encryptionTypes) {
		case SYMMETRIC:
			return new SymmetricManager();
		case ASYMMETRIC:
			return new AsymmetricManager();
		default:
			return null;
		}
	}

}
