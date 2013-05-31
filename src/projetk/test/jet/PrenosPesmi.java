package projetk.test.jet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class PrenosPesmi 
{
	public boolean posljiPesem() 
	{
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("naslov", "naslovPesmi2"));
		nameValuePairs.add(new BasicNameValuePair("besedilo", "besediloPesmi"));
		nameValuePairs.add(new BasicNameValuePair("melodija", "melodijaPesmi"));
		nameValuePairs.add(new BasicNameValuePair("notniZapis", "notniPesmi"));

		String rezultat = post_and_reader(nameValuePairs, "http://ucenje-melodij.comli.com/nalozi.php");

		if (rezultat.equals("success"))
			return true;

		return false;
	}
	
	public ArrayList <Pesem> prejmiPesmi ()
	{
		String result = "";
		//the year data to send
		//ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		//nameValuePairs.add(new BasicNameValuePair("year","1980"));
		
		InputStream is = null;
		ArrayList <Pesem> seznamPesmi = new ArrayList <Pesem> ();
		
		//http post
		try
		{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://ucenje-melodij.comli.com/prejmi.php");
		        //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		}
		
		catch(Exception e)
		{
		       e.getMessage();
		}
		//convert response to string
		try
		{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        
		        while ((line = reader.readLine()) != null) 
		        {
		                sb.append(line + "\n");
		        }
		        
		        is.close();
		 
		        result=sb.toString();		     
		}
		
		catch(Exception e)
		{
		        String napaka = e.getMessage();
		}
		 
		//parse json data
		try
		{
		        JSONArray jArray = new JSONArray(result);
		        
		        for(int i=0;i<jArray.length();i++)
		        
		        {
		                JSONObject json_data = jArray.getJSONObject(i);
		                
		                Pesem tempPesem = new Pesem();
		                tempPesem.setNaslov(json_data.getString("naslov"));
		                tempPesem.setBesedilo(json_data.getString("besedilo"));
		                tempPesem.setMelodija(json_data.getString("melodija"));
		                tempPesem.setNotniZapis(json_data.getString("notniZapis"));
		                
		                seznamPesmi.add(tempPesem);
		        }
		}
	
		catch(JSONException e)
		{
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
		return seznamPesmi;
	}
	
	public String post_and_reader(ArrayList<NameValuePair> nameValuePairs, String url) 
	{

		InputStream is = null;
		String rezultat = null;

		try 
		{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} 
		
		catch (ClientProtocolException e) 
		{
	        String napaka = e.getMessage();
	    } 
		
		catch (IOException e) 
	    {
	        String napaka = e.getMessage();
	    }

		try 
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			sb.append(reader.readLine() + "\n");
			String line = "0";
			
			while ((line = reader.readLine()) != null) 
			{
				sb.append(line + "\n");
			}
			
			is.close();
			rezultat = sb.toString();

		}
		
		catch (ClientProtocolException e) 
		{
			  String napaka = e.getMessage();
	    }
		catch (IOException e) 
	    {
	    	  String napaka = e.getMessage();
	    }

		return rezultat.replaceAll("\\s+", "");
	}
}
