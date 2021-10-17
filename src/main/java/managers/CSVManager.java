package managers;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import databaseConnection.DBConnection;

/**
 * Realisierung eines CSV- MAnagers. Wurde benoetigt, um produkte aus CSV in die
 * Datenbank zu laden.
 * 
 * @author norman
 *
 */
public class CSVManager {
	/**
	 * Methode, um eine CSV zu laden und als Liste wiederzugeben.
	 * 
	 * @param filename Der Name der CSV datei.
	 * @return arrList eine Liste mit String Arrays.
	 */
	@SuppressWarnings("deprecation")
	public static ArrayList<String[]> loadCSV(String filename) {
		File file = new File(filename);
		String thisLine;
		ArrayList<String[]> arrList = new ArrayList<String[]>();
		
		try {
			FileInputStream fis = new FileInputStream(file);
			@SuppressWarnings("resource")
			DataInputStream dis = new DataInputStream(fis);
			
			while((thisLine = dis.readLine()) != null) {
				arrList.add(thisLine.split(","));
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch (NullPointerException e1) {
			e1.getStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arrList;
	}

	/**
	 * Methode, um die Produkte von der CSV in die DB zu transferieren.
	 * 
	 * @param laden der Laden, fuer den die Produkte sind.
	 * @param list  die Liste, aus der geladenen CSV- Datei.
	 */
	@SuppressWarnings("unused")
	private static void transferProductsFromCSVToDB(String laden, ArrayList<String[]> list) {
		Connection con = DBConnection.getConnection();
		
		String query = "INSERT INTO " + laden +"_sortiment "
				+ "VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?)";
		try {
			
			for(int i = 1; i < list.size(); i++) {
				PreparedStatement stmt = con.prepareStatement(query);

				stmt.setInt(1, Integer.parseInt(list.get(i)[0]));
				stmt.setString(2, list.get(i)[1]);
				stmt.setDouble(3, Double.parseDouble(list.get(i)[2]));
				stmt.setInt(4, Integer.parseInt(list.get(i)[5]));
				stmt.setInt(5, Integer.parseInt(list.get(i)[6]));
				stmt.setInt(6, Integer.parseInt(list.get(i)[7]));
				stmt.setInt(7, Integer.parseInt(list.get(i)[8]));
				stmt.setString(8, list.get(i)[3]);
				stmt.setString(9, list.get(i)[4]);
				stmt.setInt(10, Integer.parseInt(list.get(i)[9]));
				stmt.executeUpdate();
				stmt.close();
			}
			con.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
