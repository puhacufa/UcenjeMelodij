package projetk.test.jet.Jukebox;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import projetk.test.jet.ApplicationExt;
import projetk.test.jet.PesemActivity;
import projetk.test.jet.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class JukeboxActivity extends Activity {
	TextView textViewTest; 
	ListView listViewSongList;
	ApplicationExt app;
	Context context = this;
	
	final String urlHome = "http://www.cool-midi.com";
	ArrayList <JukeboxSong> jukeboxSongList;
	
	@Override
	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		app =  (ApplicationExt) getApplication ();   
		jukeboxSongList = new ArrayList <JukeboxSong>();
		//jukeboxSongList = app.getJukeboxSongList();
		
		setContentView (R.layout.pesmi);
		
		listViewSongList = (ListView) findViewById(R.id.listViewSongList);
		

		if (jukeboxSongList.isEmpty())
		{
			try {
				getData (getHtml (new URL (urlHome)));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
				

		
		listViewSongList.setOnItemClickListener(new OnItemClickListener() 
		{
			  public void onItemClick(AdapterView<?> parent, View view,
			    int position, long id) 
			  {
				  app.setIzbranaJukeboxPesem(position);
				  Intent intent = new Intent (context, JukeboxPesemActivity.class);
				  context.startActivity(intent);
			  }
			}); 
		}
	

	@Override
	public void onStart ()
	{
		super.onStart();
		ArrayAdapter<JukeboxSong> adapter  = new ArrayAdapter<JukeboxSong> (this, 					
				android.R.layout.simple_list_item_1, app.getJukeboxSongList());
		listViewSongList.setAdapter(adapter);
	}
	
	// Iz oèišèenega html-ja prebere podatke o skladbah.
	private void getData (TagNode tn)
	{
		String [] xPathExp = new String [3];
		
		
		xPathExp [0]= "//*[@id=\"songlist\"]/ul/li/div/a/text()"; // naslov
		xPathExp [1] = "//*[@id=\"songlist\"]/ul/li/div/a/@href";		//link
		
		String [] authorList = findInfo  (tn, xPathExp[0]);
		String [] urlList = findInfo (tn, xPathExp [1]);
		
		for (int i=0; i< authorList.length; i++) {
			JukeboxSong js1 = new JukeboxSong();
			js1.author = authorList[i] ;
			js1.url = urlList[i];
			jukeboxSongList.add (js1);
		}
		
		app.setJukeboxSongList(jukeboxSongList);
	}
	
	private static String [] findInfo(TagNode node, String XPathExpression) 
	{
		TagNode[] description_node = null;
		String [] podatki = null; 
		try {
			Object [] nodes =  node.evaluateXPath(XPathExpression);
			
			podatki = new String [nodes.length];
			for (int i = 0; i<nodes.length; i++) {
				podatki [i] = (nodes[i]).toString(); 
			}
			
		} 
		catch (XPatherException e) {
			e.printStackTrace();
		}
		
		return podatki;
	}
	
	// Prenese html iz spleta in ga oèisti.
	private TagNode getHtml (URL url) throws MalformedURLException{
		AsyncTask<URL, Void, TagNode> td;
		td = new RetreiveFeedTask().execute(url);

	    TagNode result = null;
		try {
			result = td.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
