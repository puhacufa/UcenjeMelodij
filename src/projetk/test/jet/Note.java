package projetk.test.jet;

public enum Note {
    C4 (60),
    D4 (62),
    E4 (64),
    F4 (65),
    G4 (67),
    A4 (69),
    B4 (71),
    C5 (72),
    Cs4 (61),
    Ef4 (63),
    Fs4 (66),
    Af4 (68),
    Bf4 (70),
    Cs5 (72);
    
    private final int vrednost; // in meters
    
    Note(int vrednost) {
        this.vrednost = vrednost;
    }
    
    Note(String str) {
        this.vrednost = Integer.parseInt(str);
    }
    
    public int vrednost() { return vrednost; }
    
    public static Note fromInt(int vr) {
        for (Note n : values() ){
            if (n.vrednost == vr) return n;
        }
        return null;
    }
    
    @Override
    public String toString ()
    {
		return String.valueOf(vrednost);
    	
    }
}