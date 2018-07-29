package cr.ac.cenfotec.encryption.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import cr.ac.cenfotec.encryption.methods.EncryptionManager;
import cr.ac.cenfotec.encryption.methods.EncryptionManagerFactory;
import cr.ac.cenfotec.encryption.types.EncryptionTypes;

public class EncryptSample {
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static  EncryptionManager encryptManager;
	private static String method;
	
	public static void main(String[] args) throws Exception {
		int option = 0;
		do {
			System.out.println("/////////////////////////////////////////////");
			System.out.println("Select the Encryption method");
			System.out.println("/////////////////////////////////////////////");
    		System.out.println("1.AES (symmetric method)");
        	System.out.println("2.Blowfish (symmetric method)");
        	System.out.println("3.RSA (asymmetric method)");
        	System.out.println("4.Exit");
        	option = Integer.parseInt(br.readLine());
        	if (option >= 1 && option <= 3){
        		setMethod(option);
        	}
    	} while (option != 4);
    }
	
	private static void setMethod(int option) throws Exception {
		switch(option) {
		case 1:
			encryptManager = EncryptionManagerFactory.create(EncryptionTypes.SYMMETRIC);
			method = "AES";
			break;
		case 2:
			encryptManager = EncryptionManagerFactory.create(EncryptionTypes.SYMMETRIC);
			method = "Blowfish";
			break;
		case 3:
			encryptManager = EncryptionManagerFactory.create(EncryptionTypes.ASYMMETRIC);
			method = "RSA";
			break;
		}
		menu();
	}

	public static void menu() throws Exception {
		int option = 0;
		do {
    		System.out.println("1.Create key");
        	System.out.println("2.Encript Message");
        	System.out.println("3.Decrypt Message");
        	System.out.println("4.Back ");
        	option = Integer.parseInt(br.readLine());
        	if (option >= 1 && option <= 3){
        		executeAction(option);
        	}
    	} while (option != 4);
	}
	
	private static void executeAction(int option) throws Exception {
		if (option == 1){ 
			System.out.println("Key name: ");
			String name = br.readLine();
			encryptManager.createKey(name,method);
		}
		if (option == 2){
			System.out.println("Key name: ");
			String name = br.readLine();
			System.out.println("Message name: ");
			String messageName = br.readLine();
			System.out.println("Message: ");
			String message = br.readLine();
			encryptManager.encryptMessage(messageName,message,name,method);		
		}
		if (option == 3){
			System.out.println("Key name: ");
			String keyName = br.readLine();
			System.out.println("Message name: ");
			String messageName = br.readLine();
			System.out.println("Message content:");
			System.out.println(encryptManager.decryptMessage(messageName, keyName,method));
			System.out.println("");
		}
	}

}
