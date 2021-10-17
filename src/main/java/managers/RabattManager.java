package managers;

public class RabattManager {
	
	/**
	 * Methode um den Rabatt für ein Produkt zufaellig festzulegen.
	 * 
	 * @return Eine zufaellige Zahl zwischen 0.15 und 0.5. Dies entspricht einem
	 *         Bereich von 15 bis 50 % Rabatt.
	 */
	public static double getRandomRabatt() {
		return (Math.random() * (0.5 - 0.15) + 0.15);
	}
	
	/**
	 * Mit dieser Methode wird dem Originalpreis der gegebene Rabatt abgezogen.
	 * 
	 * @param rabatt      der aktuelle zufaellige Rabatt.
	 * @param originpreis der Originalpreis des Produktes.
	 * @return double-value originalpreis abzueglich des Rabatts.
	 */
	public static double abzugRabatt(double rabatt, double originpreis) {
		return DoubleManager.round(originpreis - (originpreis * rabatt), 2);
	}

}
