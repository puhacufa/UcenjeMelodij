package projetk.test.jet.Jukebox;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import projetk.test.jet.ApplicationExt;
import projetk.test.jet.R;
import projetk.test.jet.Jukebox.JukeboxPesemActivity.DownloadFileFromURL;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class AuthorActivity extends Activity{
	
	TextView textViewDescription;
	TextView textViewTitle;
	ImageView imageView;
	
	ApplicationExt app;
	
	int izbranaPesem;
	String [] description;
	String [] title;
	String [] pictureLink;
	String picLocation = Environment.getExternalStorageDirectory().toString() + "/downloadedimage.jpeg";
	 
	ArrayList <JukeboxSong> jukeboxSongList;
	
	public void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.avtor);
		
		textViewDescription = (TextView) findViewById(R.id.authorDescription);
		textViewTitle = (TextView) findViewById (R.id.authorTextViewTitle);
		imageView = (ImageView) findViewById (R.id.imageViewAuthor);
		app =  (ApplicationExt) getApplication ();
		
		izbranaPesem = app.getIzbranaJukeboxPesem();
		jukeboxSongList = app.getJukeboxSongList();
		
		try {
			getData (getHtml (new URL (jukeboxSongList.get(izbranaPesem).authorLink)));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		textViewDescription.setText (description[0]);
		textViewTitle.setText (jukeboxSongList.get(izbranaPesem).author);
		new DownloadFileFromURL().execute("http://www.cool-midi.com/" + pictureLink[0]);
		
		Bitmap bm = BitmapFactory.decodeFile(picLocation);
		imageView.setImageBitmap(bm); 
	}

	// Iz oèišèenega html-ja prebere podatke o skladbah.
	private void getData (TagNode tn)
	{
		String [] xPathExp = new String [3];
		
		xPathExp [0] = "//*[@id=\"column\"]/p[1]/text()"; // besedilo
		xPathExp [1] = "//*[@id=\"column\"]/p[2]/a/text()"; 
		xPathExp [2] = "//*[@id=\"column\"]/img/@src";
	
		
		description = findInfo  (tn, xPathExp[0]);
		title = findInfo (tn, xPathExp[1]);
		pictureLink = findInfo (tn, xPathExp[2]);
		
		//app.setJukeboxSongList(jukeboxSongList);
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
            OutputStream output = new FileOutputStream(picLocation);
 
            byte data[] = new byte[1024];
 
            long total = 0;
 
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                //publishProgress(""+(int)((total*100)/lenghtOfFile));
 
                // writing data to file
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
        picLocation = Environment.getExternalStorageDirectory().toString() + "/downloadedimage.jpeg";
    }
	}
}
