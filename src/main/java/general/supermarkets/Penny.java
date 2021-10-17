package general.supermarkets;

import general.Supermarkt;
/**
 * Klasse Penny erbt von Supermarkt.
 * @author norman
 *
 */
public class Penny extends Supermarkt {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5624494430705880795L;
	private final int URSPRUNGSID = 4;
	
	public Penny() {
		super();
		setBezeichnung();
	}

	@Override
	protected void setBezeichnung() {
		this.bez = "Penny";
	}
	/**
	 * Rueckgabe der festen UrsprungsID {@code 4}.
	 * @return URSPRUNGSID
	 */
	public int getUrsprungsID() {
		return URSPRUNGSID;
	}

}
