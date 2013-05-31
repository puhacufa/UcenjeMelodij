package projekt.test.jet.decisionTree;

public class ListDrevesa {
	private static int da;
	private static int ne;
	private static double povprecje;
	
	public double izracunajPovprecje () {
		povprecje = (double)da / (double)(da + ne);
		return povprecje;
	}
	
	public void povecajDa () {
		da++;
		izracunajPovprecje ();
	}
	
	public void povecajNe () {
		ne++;
		izracunajPovprecje ();
	}
	
	public String getVal () {
		return String.format("%d , %d \n", da, ne);
	}
	
	public void reset () {
		povprecje = 0;
	}
}
