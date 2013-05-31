package projetk.test.jet.Jukebox;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import projetk.test.jet.ApplicationExt;
import projetk.test.jet.R;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class JukeboxPesemActivity extends Activity{
	
	TextView textViewBesedilo;
	TextView textViewTitle;
	TextView textViewAuthorLink;
	ApplicationExt app;
	MediaPlayer mediaPlayer;
	String myUri; // povezava do zvocne datoteke
	
	int izbranaPesem;
	int[] midiSources;
	String [] lyrics;
	String [] downloadPath;
	String [] authorLink;
	
	ArrayList <JukeboxSong> jukeboxSongList;
	
	public void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jukebox_pesem);
		
		textViewBesedilo = (TextView) findViewById(R.id.jukeboxTextViewBesedilo);
		textViewTitle = (TextView) findViewById (R.id.jukeboxTextViewTitle);
		textViewAuthorLink = (TextView) findViewById(R.id.textViewAuthorLink);
		app =  (ApplicationExt) getApplication ();
		
		izbranaPesem = app.getIzbranaJukeboxPesem();
		jukeboxSongList = app.getJukeboxSongList();
		
		try {
			getData (getHtml (new URL ("http://www.cool-midi.com/"+jukeboxSongList.get(izbranaPesem).url)));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		textViewBesedilo.setText (lyrics[0]);
		textViewTitle.setText (jukeboxSongList.get(izbranaPesem).author);
		//textViewBesedilo.setText("ala");
//		
//		midiSources = new int[]
//				{
//					R.raw.kuza_pazi,
//					R.raw.cuk_se_je_ozenil
//				};
		
//		if (izbranaPesem < 2)
//		{
		new DownloadFileFromURL().execute(downloadPath[0]);
		myUri = (Environment.getExternalStorageDirectory().toString() + "/downloadedfile.mid"); // initialize Uri here

			
//		}
	}
	
	
	public void onClickTextViewAuthorLink (View view)
	{
		Intent intent = new Intent (this, AuthorActivity.class);
		this.startActivity(intent);
	}
	
	
	public void onButtonPlayClick (View view)
	{		
		if (mediaPlayer.isPlaying())
		{
			mediaPlayer.pause();
		}
		
		else
		{
			
			mediaPlayer.start();
		}
	}
	
	public void onButtonStopClick (View view)
	{
		if (mediaPlayer.isPlaying())
		{
			mediaPlayer.stop();
			try {
				mediaPlayer.reset();
				mediaPlayer.setDataSource(myUri);
				mediaPlayer.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void onStart ()
	{
		super.onStart();
		
		mediaPlayer = new MediaPlayer();
		//mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mediaPlayer.setDataSource(myUri);
			mediaPlayer.prepare();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void onStop ()
	{
		super.onStop();
		mediaPlayer.release();
	}

	// Iz oèišèenega html-ja prebere podatke o skladbah.
	private void getData (TagNode tn)
	{
		String [] xPathExp = new String [3];
		
		
		xPathExp [0] = "//*[@id=\"lyrics\"]/text()"; // besedilo
		xPathExp [1] = "//*[@id=\"downloadbox\"]/a/@href"; 
		xPathExp [2] = "//*[@id=\"content\"]/h2[1]/a/@href";
		
		lyrics = findInfo  (tn, xPathExp[0]);
		downloadPath = findInfo (tn, xPathExp[1]);
		authorLink = findInfo (tn, xPathExp[2]);
		
		JukeboxSong tempJS = new JukeboxSong ();
		tempJS = jukeboxSongList.get(izbranaPesem);
		tempJS.authorLink = authorLink[0];
		
		jukeboxSongList.add(izbranaPesem, tempJS);
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
	
	class DownloadFileFromURL extends AsyncTask<String, String, String> {
	 
	
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();

	    }
	 
	    /**
	     * Downloading file in background thread
	     * */
	    @Override
	    protected String doInBackground(String... f_url) {
	        int count;
	        try {
	            URL url = new URL(f_url[0]);
	            //URL url = new URL ("http://www.cool-midi.com/midi/p/psy-gangnam_style_best_version.mid");
	            URLConnection conection = url.openConnection();
	            conection.connect();
	            // getting file length
	            int lenghtOfFile = conection.getContentLength();
	 
	            // input stream to read file - with 8k buffer
	            InputStream input = new BufferedInputStream(url.openStream(), 8192);
	 
	            // Output stream to write file
	            OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/downloadedfile.mid");
	 
	            byte data[] = new byte[1024];
	 
	            long total = 0;
	 
	            while ((count = input.read(data)) != -1) {
	                total += count;

	                output.write(data, 0, count);
	            }
	 
	            // flushing output
	            output.flush();
	 
	            // closing streams
	            output.close();
	            input.close();
	 
	        } catch (Exception e) {
	            Log.e("Error: ", e.getMessage());
	        }
	 
	        return null;
	    }
	 

	    @Override
	    protected void onPostExecute(String file_url) {
	        String songPath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.mid";
	    }
	 
	}

}
