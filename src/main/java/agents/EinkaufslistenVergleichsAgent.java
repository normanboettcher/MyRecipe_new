package agents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import databaseConnection.DBConnection;
import general.Einkaufsliste;

public class EinkaufslistenVergleichsAgent {
	
	private HashMap<Integer, Double> einkaufslisten_geordnet_nach_preis;
	
	private static final boolean ABSTEIGEND = false;
	private static final boolean AUFSTEIGEND = true;
	
	public EinkaufslistenVergleichsAgent() {
		this.einkaufslisten_geordnet_nach_preis = new HashMap<Integer, Double>(); 
	}
	
	public HashMap<Integer, Double> getEinkaufslistenSortiertNachPreis() {
		return einkaufslisten_geordnet_nach_preis;
	}
	
	public void vergleicheEinkaufslisten(HashMap<Integer, Einkaufsliste> liste) {
		HashMap<Integer, Double> listen_mit_preisen_unsportiert = extrahierePreisVonEinkaufsliste(liste);
		this.einkaufslisten_geordnet_nach_preis = sortiereNachBilligstenFuenf(listen_mit_preisen_unsportiert, AUFSTEIGEND);
	}
	
	private static HashMap<Integer, Double> extrahierePreisVonEinkaufsliste(
			HashMap<Integer, Einkaufsliste> l) {
		
		HashMap<Integer, Double> listen_mit_preisen_und_id = new HashMap<Integer, Double>();
		
		for(int i : l.keySet()) {
			listen_mit_preisen_und_id.put(i, l.get(i).getGesamtPreis());
		}
		return listen_mit_preisen_und_id;
	}
	
	private static HashMap<Integer, Double> sortiereNachBilligstenFuenf(
			HashMap<Integer, Double> unsortiert, final boolean order) {
		
		List<Entry<Integer, Double>> liste = new LinkedList<Entry<Integer, Double>>(unsortiert.entrySet());
		
		System.out.println("Bevor sortiert: ");
		printMap(unsortiert);
		
		Collections.sort(liste, new Comparator<Entry<Integer, Double>>() {
			public int compare(
					Entry<Integer, Double> wert1, Entry<Integer, Double> wert2) {
				if(order) {
					return wert1.getValue().compareTo(wert2.getValue());
				}
				else {
					return wert2.getValue().compareTo(wert1.getValue());
				}
			}
		});
		
		HashMap<Integer, Double> sortiert = new LinkedHashMap<Integer, Double>();
		
		int key = 0;
		for(Entry<Integer, Double> entry : liste) {
			key++;
			
			//Es werden nur die top 5 Listen hinzugefuegt.
			if(key <= 5) {	
				sortiert.put(key, entry.getValue());
			}
		}
		
		System.out.println("Nach sortiert: ");
		printMap(sortiert);
		return sortiert;
	}
	
	/**
	 * Hilfsmethode zum ausgeben der Maps. Zu Testzwecken.
	 * @param map die ubergebene map, die ausgegeben werden soll jeweils mit key und value.
	 */
	private static void printMap(HashMap<Integer, Double> map) {
		for(Entry<Integer, Double> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ". " + entry.getValue() + " EUR");
		}
	}
}
