package projetk.test.jet;

import java.util.ArrayList;

import mySong.MySongActivity;

import openGL.MeniOpenGLActivity;

import projetk.test.jet.Jukebox.JukeboxActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ProjektJetActivity extends Activity {
	View drawView;
	Button but;
	Button buttonPesmi;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        but = (Button) findViewById (R.id.buttonOK);
        buttonPesmi = (Button) findViewById (R.id.ButtonPesmi);
    }
    
    public void onButtonIgranjeClick (View view){
    	Intent myIntent = new Intent(this, IzbiraActivity.class);
    	this.startActivity(myIntent);
    }
    
    public void onButtonRezultatiClick (View view)
    {
        Intent konecIgre = new Intent (this, KonecIgreActivity.class);
        this.startActivity (konecIgre);
    }
    
    public void onButtonPesmiClick (View view)
    {
    	Intent myIntent = new Intent (this, JukeboxActivity.class);
    	this.startActivity(myIntent);
    }
    
    public void onButtonMySongClick (View view)
    {
    	Intent myIntent = new Intent (this, MySongActivity.class);
    	this.startActivity(myIntent);
    }
    
    public void onButtonPrenosClick (View view)
    {
    	ApplicationExt app;
    	app = (ApplicationExt) getApplication();
    	
    	ArrayList <Pesem> obstojecePesmi = app.getSeznamPesmi();
    	
    	int stNovihPesmi = 0;
    	
        PrenosPesmi pp = new PrenosPesmi();
        //pp.insert_data();
        ArrayList <Pesem> novePesmi = pp.prejmiPesmi();
        
    	for (int j=0; j<novePesmi.size(); j++)
    	{
    		if (!zeObstaja (novePesmi.get(j).getNaslov(), obstojecePesmi))
    		{
    			// Dodaj pesem.
    			app.addPesem(novePesmi.get(j));
    			stNovihPesmi++;
    		}
    	}        
    	
        String sporocilo = "Prenesenih je bilo " + stNovihPesmi + " novih pesmi.";
    	 
    	Toast ts = Toast.makeText(this, sporocilo, Toast.LENGTH_SHORT);
        ts.show();
        
        int test;
    }
    
    boolean zeObstaja (String naslov, ArrayList <Pesem> a1)
    {
    	for (int i=0; i<a1.size(); i++)
        {
        		if (a1.get(i).getNaslov().compareTo(naslov) == 0)
        		{
        			return true;
        		}
        }
    	
    	return false;
    }
}