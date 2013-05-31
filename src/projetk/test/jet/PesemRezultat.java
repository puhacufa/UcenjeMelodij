package projetk.test.jet;

import java.util.ArrayList;
import java.util.List;

public class PesemRezultat {
	private List <Integer> _natancnost = new ArrayList <Integer> ();		// V  koliko procentih se trajanje vnesene note ujema z dejansko noto.
	private List <Boolean> _jePravilen = new ArrayList <Boolean> ();	// Ali je uporabnik pritisnil pravo tipko.
	private List <Double> _cas = new ArrayList <Double> ();		// Èas, ki ga je uporabnik potreboval, da je našel pravo tipko.
	private List <Note> _predhodna = new ArrayList <Note> ();		// Katera je bila predhodnjo nastavljena nota.
	private List <Note> _imeNota = new ArrayList <Note> ();
	
	public PesemRezultat() {
		_natancnost = new ArrayList <Integer> ();
		_jePravilen = new ArrayList <Boolean> ();
		_cas = new ArrayList <Double> ();
		_predhodna = new ArrayList <Note> ();
		_imeNota = new ArrayList <Note> ();
	}
	
	PesemRezultat (Note imeNota, int natancnost, boolean jePravilen, double cas, Note predhodna) {
		_natancnost.add(natancnost);
		_jePravilen.add (jePravilen);
		_cas.add (cas);
		_predhodna.add(predhodna);
		_imeNota.add (imeNota);
	}
	
	public void add (Note trenutna, int natancnost, boolean jePravilen, double cas, Note predhodna) {
		_natancnost.add(natancnost);
		_jePravilen.add (jePravilen);
		_cas.add (cas);
		_predhodna.add(predhodna);
		_imeNota.add (trenutna);
	}
	
	public List<Integer> getNatancnost() {
		return _natancnost;
	}
	public void addNatancnost(Integer natancnost) {
		this._natancnost.add (natancnost);
	}
	public List<Boolean> getJePravilen() {
		return _jePravilen;
	}
	public void addJePravilen(Boolean jePravilen) {
		this._jePravilen.add (jePravilen);
	}
	public List<Double> getCas() {
		return _cas;
	}
	public void addCas(Double cas) {
		this._cas.add (cas);
	}
	public List <Note> getPredhodna() {
		return _predhodna;
	}
	public void addPredhodna(Note predhodna) {
		this._predhodna.add (predhodna);
	}
	public void addTrenutna(Note imeNota) {
		this._imeNota.add (imeNota);
	}
	
	public List <Note> getImeNota() {
		return _imeNota;
	}
	
}
