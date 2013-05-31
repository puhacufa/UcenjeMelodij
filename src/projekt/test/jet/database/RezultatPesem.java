package projekt.test.jet.database;
import projetk.test.jet.*;

public class RezultatPesem {
 
    //private variables
	int id;
    Note nota;
    int tkIdNaslov;
	boolean jePravilen;
    int natancnost;
    
    public String getNota() {
		return nota.toString();
	}
    public Note getNotaN() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = Note.fromInt(Integer.parseInt(nota));
	}
	public void setNota(Note nota) {
		this.nota = nota;
	}
	public String getTkIdNaslov() {
		return String.valueOf(tkIdNaslov);
	}
	public void setTkIdNaslov(int tkIdNaslov) {
		this.tkIdNaslov = tkIdNaslov;
	}
	public void setTkIdNaslov(String predhodna) {
		this.tkIdNaslov = Integer.parseInt(predhodna);
	}
	public String getJePravilen() {
		return String.valueOf(jePravilen);
	}
	public boolean getJePravilenB() {
		return jePravilen;
	}
	public void setJePravilen(boolean jePravilen) {
		this.jePravilen = jePravilen;
	}
	public String getNatancnost() {
		return String.valueOf(natancnost);
	}
	public double getNatancnostB() {
		return  natancnost;
	}
	public void setNatancnost(int natancnost) {
		this.natancnost = natancnost;
	}
 
    public RezultatPesem(Note nota, int predhodna, boolean jePravilen,
			int natancnost) {
		super();
		this.nota = nota;
		this.tkIdNaslov = predhodna;
		this.jePravilen = jePravilen;
		this.natancnost = natancnost;
	}
    
	public RezultatPesem(){}
	public void setID(int id) {
		this.id = id;
		
	}
	
	public int getID() {
		return id;
		
	}
}