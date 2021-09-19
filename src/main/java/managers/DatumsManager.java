package managers;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Diese Methode definiert einen DatumsManager. Dieser ist fuer das Erstellen
 * des aktuellen Datums zustaendig.
 * 
 * @author Norman Boettcher
 *
 */
public class DatumsManager {
	/**
	 * Diese Methode erstellt das aktuelle Datum. Relevant fuer das erstellen einer
	 * Einkaufsliste inkl. Datum.
	 * 
	 * @return das aktuelle, heutige Datum.
	 */
	@SuppressWarnings({ "deprecation", "static-access" })
	public static Date aktuellesDatum() {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		Calendar c = df.getCalendar();
		c.setTimeInMillis(System.currentTimeMillis());
		return new Date(c.get(c.YEAR) - 1900, c.get(c.MONTH), c.get(c.DAY_OF_MONTH));
	}
	
	/**
	 * Methode um ein String Datum in ein Date datum umwandeln zu koennen.
	 * 
	 * @param datum das Datum als String.
	 * @return das Datum als 'Date'
	 */
	@SuppressWarnings("deprecation")
	public static Date stringDatumZuDate(String datum) {
		String[] date = datum.split("\\.");
		
		int tag = Integer.parseInt(date[0]);
		int monat = Integer.parseInt(date[1]);
		int jahr = Integer.parseInt(date[2]);
		
		return new Date(jahr - 1900, monat + 1, tag);
	}
}

