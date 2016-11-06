import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import java.nio.charset.Charset;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import java.util.Arrays;



public class Decryptor {

	private static final Charset CHARSET = Charset.forName("UTF-8");
	String key, str;
	SecretKey aesKey;
	byte[] keyBytes;
	
	byte[] iv;
	IvParameterSpec ivParam;
	IvParameterSpec[] ivParams;
	
	Cipher cipher;
	String ciph;
	byte[][] ciphersBytes;
	
	String decryptedMessText = "";
	
	/*
	* Constructor
	*/
	Decryptor() throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeySpecException {
		cipher = Cipher.getInstance("AES/CBC/NoPadding");
		ciphersBytes = new byte[2][];
		ivParams = new IvParameterSpec[2];
	}

	/*
	* Setter
	*/
	void setIv(String iv) {
		this.iv = DatatypeConverter.parseHexBinary(iv);
		ivParam = new IvParameterSpec(this.iv);
	}

	/*
	* Decryption function
	* @params key A key to decrypt
	* @params id ID of iv for key
	*/
	String decrypt(String key, int id) {
		try {
			keyBytes = DatatypeConverter.parseHexBinary(key);
			aesKey = new SecretKeySpec(keyBytes, "AES");
			cipher.init(Cipher.DECRYPT_MODE, aesKey, ivParams[id]);
			decryptedMessText = new String(cipher.doFinal(ciphersBytes[id]), CHARSET);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return decryptedMessText;
	}

	void firstMessage() throws FileNotFoundException, UnsupportedEncodingException {
		
		String keySufiks = "6a79b8311802ce2bf0a280cc70d5750d4b322834fbbadeaf7f800550";
		
		// First iv for specific key
		this.setIv("da433c7b581a3a4f87af900f4309b5a2");
		ivParams[0] = ivParam;
		
		// First cipher for specific key
		ciph = "+93QgC7iG+z5S5Vh9wYMVNRkj4LmrMl/49IhYfeI0OAXMO0oqSNP/dqjR8fvdl9E69Jti9CERtTWrqZmEgj9EH3ZNEpWyZNY9zSrV1tkrywZJL2AR4x/jm/sud8x9AbceZkYCrmILiw6WWKlmQUM8y4q5WiR9kNr7srwsx/hEp4=";
		ciphersBytes[0] = DatatypeConverter.parseBase64Binary(ciph);
		
		// Second iv for specific key
		this.setIv("83841007eda445953ad2d21d2b6beefe");
		ivParams[1] = ivParam;
		
		// Second cipher for specific key
		ciph = "yCTnD9DvuSKUtL/kiPuPHD5jSRSHVWUMpXxDYRQpenYjIQzB53qmuF7vqW8OiAEsuEqYXdWYtvFY8akDSGKLSTyhU+AuHLr++dnjhnffTU1l335OZr/CTujzNhtbSgKKR+aPG3C0ss2Q1F2Gc55kbUYDFtN5pWlTYRR0a6BQC4I=";
		ciphersBytes[1] = DatatypeConverter.parseBase64Binary(ciph);
		
		String decryptedMess = null;
		String decryptedMess1 = null;
		String keyPrefiks = null;
		String testKey = null;
		
		long startedAt = System.currentTimeMillis();
		PrintWriter writer = new PrintWriter("DecryptedMessage.txt", CHARSET.toString());
		
		for (int i0 = 0; i0 < 16; i0++) {
			for (int i1 = 0; i1 < 16; i1++) {
				for (int i2 = 0; i2 < 16; i2++) {
					for (int i3 = 0; i3 < 16; i3++) {
						for (int i4 = 0; i4 < 16; i4++) {
							for (int i5 = 0; i5 < 16; i5++) {
								for (int i6 = 0; i6 < 16; i6++) {
									for (int i7 = 0; i7 < 16; i7++) {
										keyPrefiks = Integer.toHexString(i0) + Integer.toHexString(i1) + Integer.toHexString(i2) 
												+ Integer.toHexString(i3) + Integer.toHexString(i4) + Integer.toHexString(i5) 
												+ Integer.toHexString(i6) + Integer.toHexString(i7);
										
										// Generate key which needs to be checked
										testKey = keyPrefiks + keySufiks;
										
										// Decrypt message using 2 ciphers and iv
										decryptedMess = this.decrypt(testKey, 0);
										decryptedMess1 = this.decrypt(testKey, 1);
										
										// means that cipher was decrypted successfully
										if (decryptedMess.equals(decryptedMess1)) {
											System.out.println("KEY: " + testKey + "\t" + "MESSAGE: " + decryptedMess);
											System.out.println("Cipher decrypted in: " + ((System.currentTimeMillis() - startedAt) / 1000.0) + " sek");
											writer.println("KEY: " + testKey + "\t" + "MESSAGE: " + decryptedMess);
											writer.println("Cipher decrypted in: " + ((System.currentTimeMillis() - startedAt) / 1000.0) + " sek");
											writer.flush();
											writer.close();
											return;
										}
									}
								}
							}
						}
					}
				}
				System.out.println((100.0 / 16 * (i0)) + (100.0 / 256 * (i1 + 1)) + "%\t of all keys checked in: \t" + ((System.currentTimeMillis() - startedAt) / 1000.0) + " sek");
			}
		}
	}

	void secondMessage() throws FileNotFoundException, UnsupportedEncodingException {
		
		String keySufiks = "c25758434969f8bfc8a8bc8d3e1b648eb05ae52e0aa5138ceb28a2a";
		
		// First iv for specific key
		this.setIv("0b7b2145a3a1c62e13f41722465964c6");
		ivParams[0] = ivParam;
		
		// First cipher for specific key
		ciph = "18WeqhrXSYIemuzVpUBzNw+BEynOXVQukc3idWFsknCViTy4t3LZ/NFtDs0b4bN3fc8hfL0yZcxEQRdq6undaA==";
		ciphersBytes[0] = DatatypeConverter.parseBase64Binary(ciph);
		
		// Second iv for specific key
		this.setIv("a639677d7c9a7fb40f5b9ff0dce451ca");
		ivParams[1] = ivParam;
		
		// Second cipher for specific key
		ciph = "M/cJAHNYDKUIeW27YfFIlDa+HYOJTEbKO98mawkMUppxgKock0UMEv4q2CxJ1NAlDyDQOZnOK70zp7kGk6rXMw==";
		ciphersBytes[1] = DatatypeConverter.parseBase64Binary(ciph);
		
		String decryptedMess = null;
		String decryptedMess1 = null;
		String keyPrefiks = null;
		String testKey = null;
		
		long startedAt = System.currentTimeMillis();
		PrintWriter writer = new PrintWriter("DecryptedMessage2.txt", CHARSET.toString());
		
		for (int i0 = 0; i0 < 16; i0++) {
			for (int i1 = 0; i1 < 16; i1++) {
				for (int i2 = 0; i2 < 16; i2++) {
					for (int i3 = 0; i3 < 16; i3++) {
						for (int i4 = 0; i4 < 16; i4++) {
							for (int i5 = 0; i5 < 16; i5++) {
								for (int i6 = 0; i6 < 16; i6++) {
									for (int i7 = 0; i7 < 16; i7++) {
										for (int i8 = 0; i8 < 16; i8++) {
											
											keyPrefiks = Integer.toHexString(i0) + Integer.toHexString(i1) + Integer.toHexString(i2) 
												+ Integer.toHexString(i3) + Integer.toHexString(i4) + Integer.toHexString(i5) 
												+ Integer.toHexString(i6) + Integer.toHexString(i7) + Integer.toHexString(i8);

											// Generate key which needs to be checked
											testKey = keyPrefiks + keySufiks;

											// Decrypt message using 2 ciphers and iv
											decryptedMess = this.decrypt(testKey, 0);
											decryptedMess1 = this.decrypt(testKey, 1);
											
											// means that cipher was decrypted successfully
											if (decryptedMess.equals(decryptedMess1)) {
												System.out.println("KEY: " + testKey + "\t" + "MESSAGE: " + decryptedMess);
												System.out.println("Cipher decrypted in: " + ((System.currentTimeMillis() - startedAt) / 1000.0) + " sek");
												writer.println("KEY: " + testKey + "\t" + "MESSAGE: " + decryptedMess);
												writer.println("Cipher decrypted in: " + ((System.currentTimeMillis() - startedAt) / 1000.0) + " sek");
												writer.flush();
												writer.close();
												return;
											}
										}
									}
								}
							}
						}
					}
					System.out.println((100.0 / 16 * (i0)) + (100.0 / 256 * (i1 + 1)) + (100.0 / 4096 * (i2 + 1)) + "%\t of all keys checked in: \t" + ((System.currentTimeMillis() - startedAt) / 1000.0) + " sek");
				}
			}
		}
	}
	
	void thirdMessage() throws FileNotFoundException, UnsupportedEncodingException {
		
		String keySufiks = "4636a45b66826a570806ed8d8b67f2f0b00647923a73c36e526583";
		
		// First iv for specific key
		this.setIv("14433a3ed17fce7e6d04bbab6d0974f7");
		ivParams[0] = ivParam;
		
		// First cipher for specific key
		ciph = "y7mdA5hhmS+OPy1+p5v+6FVWPqxHQyV3OIJeDUl9E8R5ZyATW/Chx+AWTvwWNfzRca82iK8GSBnN6jJVsVqBvlsOb048K2jevAsoXXqoQLnT5M2eU9zaLUEzSzCGqWo/6SGQNzfTxIowIEFBhI9PIyYmis6iRKekRhr8lswY+cw=";
		ciphersBytes[0] = DatatypeConverter.parseBase64Binary(ciph);
		
		// Second iv for specific key
		this.setIv("41b14052dd8dc7f415d36aea7aed0f45");
		ivParams[1] = ivParam;
	
		// Second cipher for specific key
		ciph = "ANugfqxUxks6NnUo57AHg15vaPeymZzM83YRvAfzKDkw917LF7d+Hj5MATzC4BYAuDQdIzKEs+TkYhPDAe3MJqcEKb2vDtd8W4BmUbsp4BbqxRk7Gw4Mjl1fgR4GUFwTEubBDNzZmHhQLIyonhrOZH3L2qOQthCMBlHXblg+LkM=";
		ciphersBytes[1] = DatatypeConverter.parseBase64Binary(ciph);
		
		String decryptedMess = null;
		String decryptedMess1 = null;
		String keyPrefiks = null;
		String testKey = null;
		
		long startedAt = System.currentTimeMillis();
		PrintWriter writer = new PrintWriter("DecryptedMessage3.txt", CHARSET.toString());
		
		for (int i0 = 0; i0 < 16; i0++) {
			for (int i1 = 0; i1 < 16; i1++) {
				for (int i2 = 0; i2 < 16; i2++) {
					for (int i3 = 0; i3 < 16; i3++) {
						for (int i4 = 0; i4 < 16; i4++) {
							for (int i5 = 0; i5 < 16; i5++) {
								for (int i6 = 0; i6 < 16; i6++) {
									for (int i7 = 0; i7 < 16; i7++) {
										for (int i8 = 0; i8 < 16; i8++) {
											for (int i9 = 0; i9 < 16; i9++) {
												keyPrefiks = Integer.toHexString(i0) + Integer.toHexString(i1) + Integer.toHexString(i2) 
														+ Integer.toHexString(i3) + Integer.toHexString(i4) + Integer.toHexString(i5) 
														+ Integer.toHexString(i6) + Integer.toHexString(i7) + Integer.toHexString(i8)
														+ Integer.toHexString(i9);
										
												// Generate key which needs to be checked
												testKey = keyPrefiks + keySufiks;
												
												// Decrypt message using 2 ciphers and iv
												decryptedMess = this.decrypt(testKey, 0);
												decryptedMess1 = this.decrypt(testKey, 1);
										
												// means that cipher was decrypted successfully
												if (decryptedMess.equals(decryptedMess1)) {
													System.out.println("KEY: " + testKey + "\t" + "MESSAGE: " + decryptedMess);
													System.out.println("Cipher decrypted in: " + ((System.currentTimeMillis() - startedAt) / 1000.0) + " sek");
													writer.println("KEY: " + testKey + "\t" + "MESSAGE: " + decryptedMess);
													writer.println("Cipher decrypted in: " + ((System.currentTimeMillis() - startedAt) / 1000.0) + " sek");
													writer.flush();
													writer.close();
													return;
												}
											}
										}
									}
								}
							}
						}
						System.out.println((100.0 / 16 * (i0)) + (100.0 / 256 * (i1 + 1)) + (100.0 / 4096 * (i2 + 1)) + (100.0 / 65536 * (i3+1)) + "%\t of all keys checked in: \t" + ((System.currentTimeMillis() - startedAt) / 1000.0) + " sek");
					}
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		Decryptor decryptor = new Decryptor();
		decryptor.firstMessage();
		//decryptor.secondMessage();
		//decryptor.thirdMessage();
	}

}
