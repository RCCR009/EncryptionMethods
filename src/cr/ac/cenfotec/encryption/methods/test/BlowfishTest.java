package cr.ac.cenfotec.encryption.methods.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cr.ac.cenfotec.encryption.methods.SymmetricManager;

public class BlowfishTest {

SymmetricManager symmetric;
	
	@Before
	public void init() {
		symmetric = new SymmetricManager();
	}
	
	@Test
	public void normalEncryptionTest() throws Exception {
		symmetric.createKey("BlowfishTestKey", "Blowfish");
		symmetric.encryptMessage("h", "hello", "BlowfishTestKey", "Blowfish");
		assertEquals("hello", symmetric.decryptMessage("h","BlowfishTestKey","Blowfish"));
	}
	
	@Test(expected = Exception.class)
	public void badKeyTest() throws Exception {
		symmetric.createKey("BlowfishTestKey2", "Blowfish");
		symmetric.encryptMessage("hBlowfish", "helloBlowfish", "BlowfishTestKey2","Blowfish");
		assertEquals("helloBlowfish", symmetric.decryptMessage("hBlowfish","AESTestKey","Blowfish"));
	}
	
	@Test(expected = Exception.class)
	public void badMessageNameTest() throws Exception {
		symmetric.createKey("BlowfishTestKey3", "Blowfish");
		symmetric.encryptMessage("ga", "goodafternoon", "BlowfishTestKey3", "Blowfish");
		assertEquals("goodmorning", symmetric.decryptMessage("hBlowfish","BlowfishTestKey3","Blowfish"));
	}

}
