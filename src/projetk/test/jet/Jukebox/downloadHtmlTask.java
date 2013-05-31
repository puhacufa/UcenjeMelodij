package projetk.test.jet.Jukebox;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;


import android.os.AsyncTask;

class RetreiveFeedTask extends AsyncTask<URL, Void, TagNode> {

    private Exception exception;

    protected TagNode doInBackground(URL... url) {
        try {
        	TagNode td = null;
    		td = xmlCleaner (url[0]);
    		
            return td;
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(TagNode tn) {
        // TODO: check this.exception 
        // TODO: do something with the feed
    }
    
	public static TagNode xmlCleaner(URL url)
	{
		CleanerProperties props = new CleanerProperties();
		props.setTranslateSpecialEntities(true);
		props.setTransResCharsToNCR(true);
		props.setOmitComments(true);
		TagNode tagNode = null;

		try {
			tagNode = new HtmlCleaner(props).clean(new URL(url.toString()));
			
			//new PrettyXmlSerializer(props).writeToFile (tagNode, "vreme.xml", "utf-8");
			
			return tagNode;

		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
 }