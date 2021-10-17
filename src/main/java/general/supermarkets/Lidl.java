package general.supermarkets;


import general.Supermarkt;
/**
 * Klasse Lidl, erbt von Supermarkt.
 * @author norman
 *
 */
public class Lidl extends Supermarkt {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4890042114618261052L;
	private final int URSPRUNGSID = 2;
	
	public Lidl() {
		super();
		setBezeichnung();
	}

	@Override
	protected void setBezeichnung() {
		this.bez = "Lidl";
	}
	/**
	 * Rueckgabe der festen UrpsrungsID {@code 2}.
	 * @return URSPRUNGSID
	 */
	public int getUrsprungsID() {
		return URSPRUNGSID;
	}
	

}
