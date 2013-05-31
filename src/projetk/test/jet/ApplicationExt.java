package projetk.test.jet;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import projekt.test.jet.database.RezultatPesem;
import projetk.test.jet.Jukebox.JukeboxSong;


import android.app.Application;
;

public class ApplicationExt extends Application{
	
	ArrayList <Pesem> seznamPesmi;
	ArrayList <JukeboxSong> jukeboxSongList;
	List<RezultatPesem> seznamRezultati;
	Vector <Note> note;
	
	int izbranaPesem;
	int izbranaJukeboxPesem;
	

	public void onCreate ()
	{
		super.onCreate();
		
		seznamPesmi = new ArrayList <Pesem> ();
		jukeboxSongList  = new ArrayList<JukeboxSong>();
		seznamRezultati =  new ArrayList <RezultatPesem>();
		note = new  Vector <Note>();
		
		fillInitData();
	}
	
	public void setNote ( Vector <Note> note)
	{
		this.note = note;
	}
	public List <RezultatPesem> getSeznamRezultati ()
	{
		return seznamRezultati;
	}
	
	public void setSeznamRezultati ( List <RezultatPesem> seznamRezultati)
	{
		this.seznamRezultati = seznamRezultati;
	}
	
	public void resetSeznamRezultati ()
	{
		this.seznamRezultati.clear();
	}
	
	public void addRezultatPesem (RezultatPesem rezultat)
	{
		seznamRezultati.add (rezultat);
	}
	
	public ArrayList <Pesem> getSeznamPesmi ()
	{
		return seznamPesmi;
	}
	
	public void addPesem (Pesem novaPesem)
	{
		seznamPesmi.add(novaPesem);
	}
	
	public ArrayList<JukeboxSong> getJukeboxSongList() 
	{
		return jukeboxSongList;
	}

	public void setJukeboxSongList(ArrayList<JukeboxSong> jukeboxSongList) 
	{
		this.jukeboxSongList = jukeboxSongList;
	}
	
	public int getIzbranaJukeboxPesem() 
	{
		return izbranaJukeboxPesem;
	}

	public void setIzbranaJukeboxPesem(int izbranaJukeboxPesem) 
	{
		this.izbranaJukeboxPesem = izbranaJukeboxPesem;
	}


	
	public void setIzbranaPesem (int st)
	{
		izbranaPesem = st;
	}
	
	public int getIzbranaPesem ()
	{
		return izbranaPesem;
	}
	
	private void fillInitData ()
	{
		seznamPesmi.add(new Pesem ("Kuža pazi", 
									getResources().getString ( R.string.KuzaPazi),
									new String ("kuza_pazi"),
									0));		
		seznamPesmi.add(new Pesem ("Èuk se je oženil", 
				getResources().getString ( R.string.CukSeJeOzenil),
				new String ("cuk_se_je_ozenil"),
				0));
		seznamPesmi.add(new Pesem ("Na planincah", 
				getResources().getString ( R.string.NaPlanincah),
				new String ("na_planincah"),
				0));
		seznamPesmi.add(new Pesem ("Barèica", 
				getResources().getString ( R.string.Barcica),
				new String ("barcica"),
				0));
		seznamPesmi.add(new Pesem ("Zima zima bela", 
				getResources().getString ( R.string.ZimaZimaBela),
				
				new String ("zima_zima_bela"),
				0));
//		seznamPesmi.add(new Pesem ("Kuža pazi", 
//				getResources().getString ( R.string.KuzaPazi),
//				new String ("kuza_pazi"),
//				0));
	}
}
