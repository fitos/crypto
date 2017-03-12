import java.io.*;
import java.math.*;
import java.security.SecureRandom;
 
public class RSA {

	public static BigInteger TWO = new BigInteger("2");

	public static BigInteger pow(BigInteger base, BigInteger power) {
		if (power.compareTo(BigInteger.ZERO) == 0) {
		    return BigInteger.ONE;
		}
		if (power.mod(TWO).compareTo(BigInteger.ONE) == 0) {
		    return pow(base, power.subtract(BigInteger.ONE)).multiply(base);
		} else {
		    BigInteger a = pow(base, power.divide(TWO));
		    return a.multiply(a);
        	}
    	}
 
  public static void main(String[] args) {
	
	BigInteger ONE = new BigInteger("1");
	BigInteger TWO = new BigInteger("2");
	BigInteger THREE = new BigInteger("3");
	SecureRandom rand = new SecureRandom();

	BigInteger d, e, n;
	BigInteger p = BigInteger.probablePrime(128, rand);
	BigInteger q = BigInteger.probablePrime(128, rand);
	
	n = p.multiply(q);
	
	BigInteger phi = (p.subtract(ONE)).multiply(q.subtract(ONE));
	
	e = new BigInteger("65537");
	d = e.modInverse(phi);

	String string = "this is a test";
	BigInteger plainText = new BigInteger(string.getBytes());
	//BigInteger cipherText = plainText.modPow(e, n);
	//BigInteger originalMessage = cipherText.modPow(d, n);
	BigInteger cipherText = pow(plainText, e).mod(n);
	BigInteger originalMessage = pow(cipherText, d).mod(n);
	String decrypted = new String(originalMessage.toByteArray());

	System.out.println("original: " + string);
	System.out.println("decrypted: " + decrypted);
	System.out.println(THREE.mod(TWO).compareTo(BigInteger.ONE) == 0);
  
  }
  
}
