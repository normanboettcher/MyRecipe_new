package managers;

import java.util.regex.Pattern;

public class RegExManager {
	
	public static boolean pruefePasswort(String passwort) {
		return Pattern.matches("^([A-Za-z0-9ÄÖÜäöüß]{8,50})$", passwort);
	}
	
	public static boolean pruefeEmail(String email) {
		return Pattern.matches("^[A-Za-z0-9\\.\\+_-]+@[A-Za-z0-9\\._-]+\\.[a-uA-Z]+$", email);
	}
	
	public static boolean pruefeStrasse(String strasse) {
		return Pattern.matches("[A-ZÖÄÜ]{1,1}[A-ZÖÄÜa-zöäü\s]{1,49}", strasse);
	}
	
	public static boolean pruefePLZ(String plz) {
		return Pattern.matches("[1-9]{1,1}[\\d]{4,4}", plz);
	}
	
	public static boolean pruefeHausnummer(String num) {
		return Pattern.matches("[1-9]{1,1}[\\d|a-z]{0,1}[\\d|a-z]{0,1}[a-z]{0,1}", num);
	}
	
	public static boolean pruefeStadt(String stadt) {
		return Pattern.matches("[\\D][\\w]{2,50}", stadt);
	}
}
