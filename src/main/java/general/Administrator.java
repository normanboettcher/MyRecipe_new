package general;
/**
 * Klasse Administrator, die eigentlich nicht gebraucht wird.
 * @author norman
 *
 */
public class Administrator extends User {
	/**
	 * 
	 * @param id
	 * @param vorname
	 * @param name
	 * @param email
	 * @param adrs
	 * @param pw
	 */
	public Administrator(int id, String vorname, String name, String email, Adresse adrs, String pw) {
		super(id, vorname, name, email, adrs, pw);
	}

}
