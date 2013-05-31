package projetk.test.jet;

public class Pesem {
	private String naslov = "naslov";
	private String besedilo = "besedilo";
	private String melodija;
	private String notniZapis;
	private int ocena = 0;
	private int stanje = 0;
	
	public Pesem ()
	{}
	
	public Pesem(String naslov, String besedilo, int ocena) 
	{
		super();
		this.naslov = naslov;
		this.besedilo = besedilo;
		this.ocena = ocena;
	}
	
	public Pesem (String naslov, String besedilo, String melodija, int stanje)
	{
		super();
		this.naslov = naslov;
		this.besedilo = besedilo;
		this.melodija = melodija;
		this.stanje = stanje;
	}

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getBesedilo() {
		return besedilo;
	}

	public void setBesedilo(String besedilo) {
		this.besedilo = besedilo;
	}

	public int getStanje ()
	{
		return stanje;
	}
	
	public void setStanje (int novoStanje)
	{
		if (novoStanje >= 100)
		{
			stanje = 100;
		}
		
		else if (novoStanje <= 0)
		{
			stanje = 0;
		}
		
		else
		{
			stanje = novoStanje;
		}
	}
	
	public void setMelodija (String novaMelodija)
	{ 
		melodija = novaMelodija;
	}
	
	public String  getMelodija ()
	{
		return melodija;
	}
	
	public void setNotniZapis (String novNotniZapis)
	{
		notniZapis = novNotniZapis;
	}
	
	public String getNotniZapis ()
	{
		return notniZapis;
	}
	
	public Pesem(String naslov, String besedilo) {
		super();
		this.naslov = naslov;
		this.besedilo = besedilo;
	}
}