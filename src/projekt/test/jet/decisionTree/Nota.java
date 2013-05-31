package projekt.test.jet.decisionTree;

import java.util.ArrayList;
import java.util.List;

import projekt.test.jet.database.RezultatPesem;
import projetk.test.jet.Note;

public class Nota {
	List <Pravilnost> vozlisca = new ArrayList <Pravilnost> ();
	Note nota;
	
	public Nota () {
		vozlisca.add (new Pravilnost());
	}
	
	public void uciSe (RezultatPesem ucniPodatek){
		
		switch (ucniPodatek.getNotaN())
		{
			case C4:
				vozlisca.get(0).uciSe(ucniPodatek);
				break;
			
			case D4:
				vozlisca.get(0).uciSe(ucniPodatek);
				break;
				
			case E4:
				vozlisca.get(0).uciSe(ucniPodatek);
				break;
				
			case F4:
				vozlisca.get(0).uciSe(ucniPodatek);
				break;
				
			case G4:
				vozlisca.get(0).uciSe(ucniPodatek);
				break;
				
			case A4:
				vozlisca.get(0).uciSe(ucniPodatek);
				break;
				
			case B4:
				vozlisca.get(0).uciSe(ucniPodatek);
				break;
				
			case C5:
				vozlisca.get(0).uciSe(ucniPodatek);
				break;
		}
		
	}
}



