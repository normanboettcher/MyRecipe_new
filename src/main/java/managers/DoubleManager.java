package managers;

/**
 * Diese Klasse definiert einen DoubleManager.
 * 
 * @author Norman Boettcher
 *
 */
public class DoubleManager {
	
	/**
	 * Methode zum runden eines double-Wertes als double. Das Format 'x.yy' soll
	 * erreicht werden. Wichtig fuer die Berechnung der Bewertug und Ertraege bei
	 * Einkauf.
	 * 
	 * @param value          der Wert, der gerundet werden soll.
	 * @param dezimalstellen Anzahl der Dezimalstellen, auf die gerundet werden
	 *                       soll.
	 * @return der neugerundete Wert als double.
	 */
	
	 public static double round(double value, int dezimalstellen) { 
		 double d = Math.pow(10, dezimalstellen); 
		 return Math.round(value * d) / d; 
	}
}