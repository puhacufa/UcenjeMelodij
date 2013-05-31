package projekt.test.jet.decisionTree;

import java.util.ArrayList;
import java.util.List;

import projekt.test.jet.database.RezultatPesem;
import projetk.test.jet.Note;

public class Pravilnost {
	List <Natancnost> vozlisca = new ArrayList <Natancnost> ();
	Note nota;
	
	public Pravilnost () {
		vozlisca.add (new Natancnost());
	}
	
	public void uciSe (RezultatPesem ucniPodatek){
		
		if (ucniPodatek.getJePravilenB()) {
			vozlisca.get(0).uciSe(ucniPodatek);
		}
		
		else {
			ListDrevesa list = new ListDrevesa();
			list.povecajNe();
		}
		
	}
}
