package projekt.test.jet.database;
import java.util.ArrayList;
import java.util.List;

import projetk.test.jet.Note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "rezultatiPesmi";
 
    // Contacts table name
    private static final String TABLE_REZULTATI = "rezultati";
    private static final String TABLE_PESMI = "pesmi";
    
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NOTA = "nota";
    private static final String FKEY_ID_NASLOV = "tkIdNaslov";
    private static final String KEY_JEPRAVILEN= "jePravilen";
    private static final String KEY_NATANCNOST = "natancnost";
    private static final String KEY_NASLOV = "naslov";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REZULTATI_TABLE = "CREATE TABLE " + TABLE_REZULTATI + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NOTA + " STRING,"
                + FKEY_ID_NASLOV + " INTEGER," + KEY_JEPRAVILEN + " INTEGER," 
                + KEY_NATANCNOST + " REAL" + ")";
        db.execSQL(CREATE_REZULTATI_TABLE);
        
        String CREATE_PESMI_TABLE = "CREATE TABLE " + TABLE_PESMI + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NASLOV + " STRING" + ")";
        db.execSQL(CREATE_PESMI_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REZULTATI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PESMI);

        onCreate(db);
    }
    
    public void resetRezultati (int tkIdNaslov){
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	 String RESET_REZULTATI = "DELETE FROM " + TABLE_REZULTATI + 
    			 					" WHERE " + FKEY_ID_NASLOV + " = " + tkIdNaslov;
    	 
    	db.execSQL(RESET_REZULTATI);
 	    db.close(); 
    }
    
    public void addRezultat(RezultatPesem rezultat) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_NOTA, rezultat.getNota());
	    values.put(FKEY_ID_NASLOV, rezultat.getTkIdNaslov());
	    values.put(KEY_JEPRAVILEN, rezultat.getJePravilen());
	    values.put(KEY_NATANCNOST, rezultat.getNatancnost());
	 
	    db.insert(TABLE_REZULTATI, null, values);
	    db.close();
    }
    
    public void addPesem(int id, String naslov) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_ID, id);
	    values.put(KEY_NASLOV, naslov);
	    db.insert(TABLE_PESMI, null, values);
	    db.close(); 
    }
    
    public void addRezultatList(List <RezultatPesem> rezultat) {
	    for (RezultatPesem rp : rezultat)
	    	addRezultat (rp);
    }
   
    public List<RezultatPesem> getVsiRezultati(int id) {
	    List<RezultatPesem> rezultatiList = new ArrayList<RezultatPesem>();

	    String selectQuery = "SELECT  * FROM " + TABLE_REZULTATI +
	    					 "," + TABLE_PESMI +
	    					 " WHERE " + FKEY_ID_NASLOV + " = " + id +
	    					 " AND " + TABLE_PESMI +"." + KEY_ID + " = " + id;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    if (cursor.moveToFirst()) {
	        do {
	            RezultatPesem contact = new RezultatPesem();
	            contact.setID(Integer.parseInt(cursor.getString(0)));
	            contact.setNota(cursor.getString(1));
	            contact.setTkIdNaslov(cursor.getString(2));
	            contact.setJePravilen(Boolean.valueOf(cursor.getString(3)));
	            contact.setNatancnost(Integer.valueOf(cursor.getString(4)));
	      
	            rezultatiList.add(contact);
	        } while (cursor.moveToNext());
	    }
	    db.close(); 
 

    return rezultatiList;
}
}