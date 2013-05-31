package projetk.test.jet;

import java.util.List;

import projekt.test.jet.database.DatabaseHandler;
import projekt.test.jet.database.RezultatPesem;
import projekt.test.jet.decisionTree.ListDrevesa;
import projekt.test.jet.decisionTree.Nota;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class KonecIgreActivity extends Activity
{
	final String preferencesName = "nastavitve";
	PesemRezultat pesemRezultat;
	TextView textViewNatancnost;
	TextView textViewPovprecje;
	TextView textViewSporocilo;
	Button buttonNaprej;
	
	boolean isNaprej = false;
	int stIger = 0;
	int vsotaTock = 0;
	int stTock = 0;
	
	ApplicationExt app;

	static List<RezultatPesem> rezultati;
	static int vrniVrstico = 0;

	@Override	
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);

		setContentView (R.layout.konec_igre);
		textViewNatancnost = (TextView) findViewById(R.id.textViewNatancnost);
		textViewPovprecje = (TextView) findViewById(R.id.textViewPovprecje);
		textViewSporocilo = (TextView) findViewById (R.id.textViewSporocilo);
		buttonNaprej = (Button) findViewById (R.id.buttonNaprej);
		textViewNatancnost.setText("ni rezultatov");

		app = (ApplicationExt) getApplication();
		
		SharedPreferences settings = getSharedPreferences(preferencesName, 0);
        stIger = settings.getInt("stIger", 0);
        stIger++;

		DatabaseHandler db = new DatabaseHandler(this);
		//db.onUpgrade(db.getReadableDatabase(), 1, 2);

		Log.d("Insert: ", "Inserting ..");
//			        db.addPesem(0, "Kuza pazi");
//			        db.addPesem(1, "Cuk se je ozenil");
//			        db.addPesem(2, "Na planincah");
//			        db.addPesem(3, "Barcica");
//			        db.addPesem(4, "Zima zima bela");
		
	    // Gradnja odloèitvenega drevesa.
        Nota nota = new Nota ();
		for (RezultatPesem rp : app.getSeznamRezultati()) {
			nota.uciSe(rp);
		}
		
		ListDrevesa ld = new ListDrevesa ();
		String rez = ld.getVal();
		
		stTock = (int) (ld.izracunajPovprecje()*100);
		textViewNatancnost.setText(String.valueOf(stTock));
		ld.reset();
		
		db.addRezultatList(app.getSeznamRezultati());
		Log.d("Reading: ", "Reading all contacts..");
		rezultati = db.getVsiRezultati(app.izbranaPesem);       
		
		// Izraèun za zadnje tri igre.
		Nota nota2 = new Nota ();
		for (RezultatPesem rp : rezultati) {
			nota2.uciSe(rp);
		}
		
		stTock = (int) (ld.izracunajPovprecje()*100);
		textViewPovprecje.setText(String.valueOf(stTock/3));
		
		if (stIger >= 3)
		{
			for (int i=0; i<app.note.size(); i++); // <app.note.size(); i++)
				//rezultati.remove(i);
		}
		
		db.resetRezultati(app.izbranaPesem);
		db.addRezultatList(rezultati);
		
		for (RezultatPesem cn : rezultati) {
			String log = "Id: "+cn.getID()+" ,Nota: " + cn.getNota() 
					+ " , Naslov: " + cn.getTkIdNaslov()
					+ " , Je pravilen:" + cn.getJePravilen()
					+ " , Natancnost: " + cn.getNatancnost();
			Log.d("Name: ", log);
		}

		if (stTock >= 10) {
			isNaprej = true;
		}

		if (isNaprej) {
			textViewSporocilo.setText("Èestitam, zdaj znaš zaigrati to melodijo.");
		}
		
		else {
			buttonNaprej.setText("Znova");
			textViewSporocilo.setText("Vaja dela mojstra, poskusi znova");
		}
	}

	public static RezultatPesem vrniPodatek () {		
		return rezultati.get(vrniVrstico++);
	}
	
	public void onButtonNaprejClick (View view) {
		if (!isNaprej) {
			Intent intent = new Intent (this, IgranjeActivity.class);
			this.startActivity(intent);
		}
	}
	
	@Override
	protected void onStop ()
	{
		super.onStop();
		 SharedPreferences settings = getSharedPreferences(preferencesName, 0);
	     SharedPreferences.Editor editor = settings.edit();
	     editor.putInt("stIger", stIger);
	     editor.commit();
	}
	
}

