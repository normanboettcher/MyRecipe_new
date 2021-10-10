package agents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import general.Food;
import managers.DatumsManager;

public class EinkaufslistenVergleichsAgent {
	
	private HashMap<Integer, Einkaufsliste> einkaufslisten_geordnet_nach_preis;
	
	private static final boolean ABSTEIGEND = false;
	private static final boolean AUFSTEIGEND = true;
	
	public EinkaufslistenVergleichsAgent() {
		this.einkaufslisten_geordnet_nach_preis = new HashMap<Integer, Einkaufsliste>(); 
	}
	
	public HashMap<Integer, Einkaufsliste> getEinkaufslistenSortiertNachPreis() {
		return einkaufslisten_geordnet_nach_preis;
	}
	
	private HashMap<Integer, Einkaufsliste> zusammenfuehren(HashMap<Integer, Double> l, HashMap<Integer, Einkaufsliste> l2) {
		HashMap<Integer, Einkaufsliste> liste = new HashMap<Integer, Einkaufsliste>();
		
		for(int entry : l.keySet()) {
			System.out.println("key: " + entry + " " + l2.get(entry));
			
			liste.put(entry, l2.get(entry));
		}
		return liste;
	}
	
	public void vergleicheEinkaufslisten(HashMap<Integer, Einkaufsliste> liste) {
		HashMap<Integer, Double> listen_mit_preisen_unsportiert = extrahierePreisVonEinkaufsliste(liste);
		this.einkaufslisten_geordnet_nach_preis = zusammenfuehren(sortiereNachBilligstenFuenf(listen_mit_preisen_unsportiert, AUFSTEIGEND), liste);
		
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
			for(Entry<Integer, Double> entry: liste) {
				key = entry.getKey();
				sortiert.put(key, entry.getValue());
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
	
	public HashMap<Integer, Einkaufsliste> erstelleEinkaufslistenFuerAlleLaeden(int rezept_id) {
		Connection con = DBConnection.getConnection();
		
		HashMap<Integer, Einkaufsliste> results = new HashMap<Integer, Einkaufsliste>();
		
		int[] laeden = {1, 3, 4};
		int laden_id = 0;
		
		for(int i = 0; i < laeden.length; i++) {
			laden_id = laeden[i];
			System.out.println(laden_id);
			
			Einkaufsliste l = new Einkaufsliste(laden_id, DatumsManager.aktuellesDatum());
			
			try {
				String laden = "";
				
				PreparedStatement get_laden_bez = con.prepareStatement("select bez from laeden where laden_id = ?");
				get_laden_bez.setInt(1, laden_id);
				
				ResultSet laden_bez = get_laden_bez.executeQuery();
				while(laden_bez.next()) {
					laden = laden_bez.getString("bez");
				}
				System.out.println("-----" +laden);
				
				l.addLaden(laden);
				PreparedStatement stmt = con.prepareStatement("select * from " + laden + "_sortiment"
						+ " full outer join "
						+ " zutaten_aus_rezepte"
						+ " on " + laden + "_sortiment.artikelnr = zutaten_aus_rezepte.artikelnr"
								+ " where zutaten_aus_rezepte.rezept_id = ?");
				stmt.setInt(1, rezept_id);
				
				System.out.println(stmt);
				
				ResultSet produkte = stmt.executeQuery();
				
				while(produkte.next()) {
					Food f = new Food(produkte.getString("artikelbez"), produkte.getDouble("artikelpreis"),
							produkte.getString("hersteller"), "", produkte.getInt("vegan"), produkte.getInt("vegetarisch"), produkte.getInt("lokal"),
							produkte.getInt("bio"), produkte.getString("kategorie"));
					f.setArtikelNr(produkte.getInt("artikelnr"));
					l.addProduktZuListe(f,  produkte.getInt("anzahl"));
					//System.out.println(produkte.getString("artikelbez"));
				}
				//System.out.println(l.getProduktliste().size());
				l.berechneGesamtpreis();
				System.out.println(l.getGesamtPreis());
				results.put(l.getEinkaufslisteID(), l);
				
				stmt.close();
				get_laden_bez.close();
				laden_bez.close();
				produkte.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return results;
	}
}
