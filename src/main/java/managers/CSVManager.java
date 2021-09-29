package managers;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVManager {
	
	public static ArrayList<String[]> loadCSV(String filename) {
		File file = new File(filename);
		String thisLine;
		ArrayList<String[]> arrList = new ArrayList<String[]>();
		
		try {
			FileInputStream fis = new FileInputStream(file);
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
	
	public static void main(String[] args) {
		ArrayList<String[]> list = CSVManager.loadCSV("src/main/webapp/CSV/produkte.csv");
		
		for(int i = 0; i < list.size(); i++) {
			for(int j = 0; j < list.get(i).length; j++) {
				System.out.println(list.get(i)[j]);
			}
			System.out.println("new line");
		}
		
	}
}
