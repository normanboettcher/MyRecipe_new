package general.supermarkets;

import general.Supermarkt;

public class Rewe extends Supermarkt{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6763726670326940696L;
	private final int URSPRUNGSID = 1;
	
	public Rewe() {
		super();
		setBezeichnung();
	}

	@Override
	protected void setBezeichnung() {
		this.bez = "Rewe";
	}
	
	public int getUrsprungsID() {
		return URSPRUNGSID;
	}

}
