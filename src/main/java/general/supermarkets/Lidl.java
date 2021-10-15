package general.supermarkets;


import general.Supermarkt;

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
	
	public int getUrsprungsID() {
		return URSPRUNGSID;
	}
	

}
