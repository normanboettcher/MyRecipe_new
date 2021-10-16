package general.supermarkets;

import general.Supermarkt;

public class Netto extends Supermarkt {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7999530215560135376L;
	private final int URSPRUNGSID = 3;
	
	public Netto() {
		super();
		setBezeichnung();
	}

	@Override
	protected void setBezeichnung() {
		this.bez = "Netto";
	}
	
	public int getUrsprungsID() {
		return URSPRUNGSID;
	}
}
