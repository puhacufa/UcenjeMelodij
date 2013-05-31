package projekt.test.jet.decisionTree;

import java.util.List;

import projekt.test.jet.database.RezultatPesem;

public final class VrniPodatek {
	static int vrniVrstico = 0;
	static List<RezultatPesem> rezultati;
	
	public VrniPodatek (List<RezultatPesem> rezultati) {
		this.rezultati = rezultati;
	}
	
	public static RezultatPesem vrniPodatek () {
		return rezultati.get (vrniVrstico++);
		
	}
}
