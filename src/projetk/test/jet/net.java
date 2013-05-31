package projetk.test.jet;

public class net {
	//
	// Testni razred
//		void generirajRezultat ()
//		{
//			rezultat = new Rezultati();
//			pesemRezultat = new PesemRezultat (); 
//			
//			pesemRezultat.add(Note.C4, 44, true, 1.2, Note.Bf4);
//			pesemRezultat.add(Note.C4, 98, true, 1.0, Note.C4);
//			pesemRezultat.add(Note.D4, 55, true, 1.2, Note.C4);
//			
//			rezultat.addRezultat(pesemRezultat);
//		}
	//	
//		public boolean posljiPesem() 
//		{
//			
//			int i=0;
//			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	//
//			nameValuePairs.add(new BasicNameValuePair("natancnost", pesemRezultat.getNatancnost().get(i).toString()));
//			nameValuePairs.add(new BasicNameValuePair("jePravilen", pesemRezultat.getJePravilen().get(i).toString()));
//			nameValuePairs.add(new BasicNameValuePair("cas", pesemRezultat.getCas().get(i).toString()));
//			nameValuePairs.add(new BasicNameValuePair("imeNota", pesemRezultat.getImeNota().get(i).toString()));
//			nameValuePairs.add(new BasicNameValuePair("predhodnaNota", pesemRezultat.getPredhodna().get(i).toString()));
	//
//			String rezultat = post_and_reader(nameValuePairs, "http://ucenje-melodij.comli.com/nalozi.php");
	//
//			if (rezultat.equals("success"))
//				return true;
	//
//			return false;
//		}
	//	
//		public ArrayList <Pesem> prejmiPesmi ()
//		{
//			String result = "";
//			//the year data to send
//			//ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//			//nameValuePairs.add(new BasicNameValuePair("year","1980"));
//			
//			InputStream is = null;
//			ArrayList <Pesem> seznamPesmi = new ArrayList <Pesem> ();
//			
//			//http post
//			try
//			{
//			        HttpClient httpclient = new DefaultHttpClient();
//			        HttpPost httppost = new HttpPost("http://ucenje-melodij.comli.com/prejmi.php");
//			        //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//			        HttpResponse response = httpclient.execute(httppost);
//			        HttpEntity entity = response.getEntity();
//			        is = entity.getContent();
//			}
//			
//			catch(Exception e)
//			{
//			       e.getMessage();
//			}
//			//convert response to string
//			try
//			{
//			        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
//			        StringBuilder sb = new StringBuilder();
//			        String line = null;
//			        
//			        while ((line = reader.readLine()) != null) 
//			        {
//			                sb.append(line + "\n");
//			        }
//			        
//			        is.close();
//			 
//			        result=sb.toString();		     
//			}
//			
//			catch(Exception e)
//			{
//			        String napaka = e.getMessage();
//			}
//			 
//			//parse json data
//			try
//			{
//			        JSONArray jArray = new JSONArray(result);
//			        
//			        for(int i=0;i<jArray.length();i++)
//			        
//			        {
//			                JSONObject json_data = jArray.getJSONObject(i);
//			                
//			                Pesem tempPesem = new Pesem();
//			                tempPesem.setNaslov(json_data.getString("naslov"));
//			                tempPesem.setBesedilo(json_data.getString("besedilo"));
//			                tempPesem.setMelodija(json_data.getString("melodija"));
//			                tempPesem.setNotniZapis(json_data.getString("notniZapis"));
//			                
//			                seznamPesmi.add(tempPesem);
//			        }
//			}
	//	
//			catch(JSONException e)
//			{
//			        Log.e("log_tag", "Error parsing data "+e.toString());
//			}
//			
//			return seznamPesmi;
//		}
	//	
//		public String post_and_reader(ArrayList<NameValuePair> nameValuePairs, String url) 
//		{
	//
//			InputStream is = null;
//			String rezultat = null;
//			JSONObject json = new JSONObject();
	//
//			try 
//			{
//				for (int i=0; i<nameValuePairs.size(); i++) {
//					json.put(nameValuePairs.get(i).getName(), nameValuePairs.get(i).getValue());
//				}
//				
//				HttpClient httpclient = new DefaultHttpClient();
//				HttpPost httppost = new HttpPost(url);
//				
//				StringEntity se = new StringEntity( json.toString());  
//	            se.setContentType((Header) new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//	            httppost.setEntity(se);
	//
//				HttpResponse response = httpclient.execute(httppost);
//				HttpEntity entity = response.getEntity();
//				is = entity.getContent();
//			} 
//			
//			catch (ClientProtocolException e) 
//			{
//		        String napaka = e.getMessage();
//		    } 
//			
//			catch (IOException e) 
//		    {
//		        String napaka = e.getMessage();
//		    } catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	//
//			try 
//			{
//				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
//				StringBuilder sb = new StringBuilder();
//				sb.append(reader.readLine() + "\n");
//				String line = "0";
//				
//				while ((line = reader.readLine()) != null) 
//				{
//					sb.append(line + "\n");
//				}
//				
//				is.close();
//				rezultat = sb.toString();
	//
//			}
//			
//			catch (ClientProtocolException e) 
//			{
//				  String napaka = e.getMessage();
//		    }
//			catch (IOException e) 
//		    {
//		    	  String napaka = e.getMessage();
//		    }
	//
//			return rezultat.replaceAll("\\s+", "");
//		}
	//}
}
