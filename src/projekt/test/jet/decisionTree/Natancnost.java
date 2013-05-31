package projekt.test.jet.decisionTree;

import java.util.ArrayList;
import java.util.List;

import projekt.test.jet.database.RezultatPesem;
import projetk.test.jet.Note;

public class Natancnost {

		public Natancnost () {
		}
		
		public void uciSe (RezultatPesem ucniPodatek){
			
			if (ucniPodatek.getNatancnostB() >= 80) {
				ListDrevesa list = new ListDrevesa ();
				list.povecajDa();
			}
			
			else {
				ListDrevesa list = new ListDrevesa();
				list.povecajNe();
			}
			
		}
}
