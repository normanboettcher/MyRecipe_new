package managers;

import java.util.regex.Pattern;
/**
 * Realisierung eines RegEx Managers.
 * @author norman
 *
 */
public class RegExManager {
	
	/**
	 * Methode, um Syntax des Passwortes zu pruefen.
	 * 
	 * @param passwort das zu pruefende Passwort als String.
	 * @return true or false.
	 */
	public static boolean pruefePasswort(String passwort) {
		return Pattern.matches("^([A-Za-z0-9ÄÖÜäöüß]{8,50})$", passwort);
	}

	/**
	 * Methode, um Syntax der Email zu pruefen.
	 * 
	 * @param email die zu pruefende Email als String.
	 * @return true or false.
	 */
	public static boolean pruefeEmail(String email) {
		return Pattern.matches("^[A-Za-z0-9\\.\\+_-]+@[A-Za-z0-9\\._-]+\\.[a-uA-Z]+$", email);
	}

	/**
	 * Methode, um Syntax der Strasse zu pruefen.
	 * 
	 * @param strasse die zu pruefende Strasse als String.
	 * @return true or false.
	 */
	public static boolean pruefeStrasse(String strasse) {
		return Pattern.matches("[A-ZÖÄÜ]{1,1}[A-ZÖÄÜa-zöäü\s]{1,49}", strasse);
	}

	/**
	 * Methode, um Syntax der PLZ zu pruefen.
	 * 
	 * @param plz die zu pruefende plz als String.
	 * @return true or false.
	 */
	public static boolean pruefePLZ(String plz) {
		return Pattern.matches("[1-9]{1,1}[\\d]{4,4}", plz);
	}

	/**
	 * Methode, um Syntax der Hausnummer zu pruefen.
	 * 
	 * @param num die zu pruefende Hausnummer als String.
	 * @return true or false.
	 */
	public static boolean pruefeHausnummer(String num) {
		return Pattern.matches("[1-9]{1,1}[\\d|a-z]{0,1}[\\d|a-z]{0,1}[a-z]{0,1}", num);
	}

	/**
	 * Methode, um Syntax der Stadt zu pruefen.
	 * 
	 * @param stadt die zu pruefende Stadt als String.
	 * @return true or false.
	 */
	public static boolean pruefeStadt(String stadt) {
		return Pattern.matches("[\\D][\\w]{2,50}", stadt);
	}
}
