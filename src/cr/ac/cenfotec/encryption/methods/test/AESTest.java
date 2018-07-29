package cr.ac.cenfotec.encryption.methods.test;

import static org.junit.Assert.*;

import org.junit.Before;

import cr.ac.cenfotec.encryption.methods.SymmetricManager;


import org.junit.Test;

public class AESTest {
	
	SymmetricManager symmetric;
	
	@Before
	public void init() {
		symmetric = new SymmetricManager();
	}
	
	@Test
	public void normalEncryptionTest() throws Exception {
		symmetric.createKey("AESTestKey", "AES");
		symmetric.encryptMessage("hw", "helloworld", "AESTestKey", "AES");
		assertEquals("helloworld", symmetric.decryptMessage("hw","AESTestKey","AES"));
	}
	
	@Test(expected = Exception.class)
	public void badKeyTest() throws Exception {
		symmetric.createKey("AESTestKey2", "AES");
		symmetric.encryptMessage("hAES", "helloAES", "AESTestKey2", "AES");
		assertEquals("helloAES", symmetric.decryptMessage("hAES","AESTestKey","AES"));
	}
	
	@Test(expected = Exception.class)
	public void badMessageNameTest() throws Exception {
		symmetric.createKey("AESTestKey3", "AES");
		symmetric.encryptMessage("gm", "goodmorning", "AESTestKey3", "AES");
		assertEquals("goodmorning", symmetric.decryptMessage("hw","AESTestKey3","AES"));
	}

}
