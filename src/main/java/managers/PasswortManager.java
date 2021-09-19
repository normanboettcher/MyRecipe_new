package managers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Diese Klasse defineirt einen PasswortManager, der fuer das Verschlusseln der
 * Passwoerter eines Users zustaendig ist.
 * 
 * @author Norman B�ttcher
 *
 */
public class PasswortManager {
	/**
	 * 
	 */
	private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

	/**
	 * Mit dieser MEthode wird aus einem uebergebenen String-Passwort ein Hash
	 * erzeugt.
	 * 
	 * @param pw das uebergebene Paswort
	 * @return {@link #bytesToStringHex(byte[])}
	 * @throws NoSuchAlgorithmException eine Exception die geworfen wird.
	 */
	public static String generateHash(String pw) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		byte[] hash = digest.digest(pw.getBytes());
		return bytesToStringHex(hash);
	}

	/**
	 * Diese Methode wandelt ein uebergebenes byte-Array in einen String um.
	 * Grundlage fuer den output string bildet das hexArray.
	 * 
	 * @param bytes das uebergebene byte-array.
	 * @return der neue String eines char arrays auf Basis eines byte Arrays.
	 */
	private static String bytesToStringHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		
		for(int i = 0; i < bytes.length; i++) {
			int v = bytes[i] & 0xFF;
			hexChars[i * 2] = hexArray[v >>> 4];
			hexChars[i * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
}
