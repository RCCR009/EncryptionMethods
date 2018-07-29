package cr.ac.cenfotec.encryption.methods.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cr.ac.cenfotec.encryption.methods.AsymmetricManager;

public class RSATest {

	AsymmetricManager asymmetric;
	
	@Before
	public void init() {
		asymmetric = new AsymmetricManager();
	}
	
	@Test
	public void normalEncryptionTest() throws Exception {
		asymmetric.createKey("RSATestKey", "RSA");
		asymmetric.encryptMessage("hw", "helloworld", "RSATestKey", "RSA");
		assertEquals("helloworld", asymmetric.decryptMessage("hw","RSATestKey","RSA"));
	}
	
	@Test(expected = Exception.class)
	public void badKeyTest() throws Exception {
		asymmetric.createKey("RSATestKey2", "RSA");
		asymmetric.encryptMessage("hRSA", "helloRSA", "RSATestKey2", "RSA");
		assertEquals("helloAES", asymmetric.decryptMessage("hRSA","RSATestKey","RSA"));
	}
	
	@Test(expected = Exception.class)
	public void badMessageNameTest() throws Exception {
		asymmetric.createKey("RSATestKey3", "RSA");
		asymmetric.encryptMessage("gm", "goodmorning", "RSATestKey3", "RSA");
		assertEquals("goodmorning", asymmetric.decryptMessage("hw","RSATestKey3","RSA"));
	}

}
