package projetk.test.jet;

import java.util.ArrayList;
import java.util.List;

public class Rezultati {

	private List <PesemRezultat> _pesemRezultat;
	
	Rezultati () {
		_pesemRezultat = new ArrayList ();
		
	}
	
	public void addRezultat (PesemRezultat pesemRezultat) {
		_pesemRezultat.add(pesemRezultat);
	}
	
	public List<PesemRezultat> getRezultat () {
		return _pesemRezultat;
	}
}
