package mySong;

import java.io.IOException;
import java.util.Random;

import projetk.test.jet.R;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kevinboone.music.MidiFile;

public class MySongActivity extends Activity {
	Button buttonPlay1;
	Button buttonPlay2;
	Button buttonPlay3;
	Button buttonPlay4;
	
	RatingBar ratingBar1;
	RatingBar ratingBar2;
	RatingBar ratingBar3;
	RatingBar ratingBar4;
	
	TextView textView1;
	TextView textView2;
	TextView textView3;
	TextView textView4;
	
	TextView textViewNapredek;
	
	int [][] songs;
	int [] ratings;
	int napredek;
	int stPonovitev;
	
	int izbiraMelodije = 0;
	final int songLength = 22;
	final int songCount = 4;
	
	final double Pm = 0.1; // Verjetnost mutacije.
	final double Pc = 0.3; // Verjetnost križanja.
	
	MediaPlayer player = new MediaPlayer();
	MidiFile mf = new MidiFile();
	
	@Override
	public void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);
		
		setContentView (R.layout.my_song);
		
		buttonPlay1 = (Button) findViewById(R.id.ButtonPlay1);
		buttonPlay2 = (Button) findViewById(R.id.ButtonPlay2);
		buttonPlay3 = (Button) findViewById(R.id.ButtonPlay3);
		buttonPlay4 = (Button) findViewById(R.id.ButtonPlay4);
		
		ratingBar1 = (RatingBar) findViewById(R.id.ratingBar1);
		ratingBar2 = (RatingBar) findViewById(R.id.ratingBar2);
		ratingBar3 = (RatingBar) findViewById(R.id.ratingBar3);
		ratingBar4 = (RatingBar) findViewById(R.id.ratingBar4);
		
		textViewNapredek = (TextView) findViewById (R.id.textViewNapredek);
				
		napredek = 1;
		stPonovitev = 5;
		songs = new int [4][];
		ratings = new int [4];
		
		initializeSequence ();
		textViewNapredek.setText(String.format("%s / %s", napredek, stPonovitev));
		
		for (int i=0; i<songCount; i++)
		{
			ratings [i] = 1;
		}
	}
	
	@Override
	public void onStop ()
	{
		super.onStop();
		
		if (player.isPlaying())
				player.stop();
		
		player.release();
	}
	
	public void EA ()
	{
		double r;
		int rNota;
		int bestRating;
		double sumRatings = 0;
		double [] verjetnostIzbire = new double [songCount];
		double [] komulaVre = new double [songCount+1]; // Komulativna vrednost;
		int [][] tempSongs = new int [songCount][songLength];  // Melodije ki jo bomo spremenili.
		int [] crossingSelIndex = new int [songCount];  // Melodije izbrane za križanje.
		
		// PROPORCIONALNA SELEKCIJA
		// Izraèunamo vsoto ocen melodij.
		for (int i=0; i<ratings.length; i++)
		{
			sumRatings += ratings [i];
		}
		
		// Za vsako melodijo izraèunamo verjetnost izbire.
		for (int i=0; i<songCount; i++)
		{
			verjetnostIzbire[i] = ratings [i] / sumRatings;
		}
		
		// Za vsako melodijo izraèunamo komulativno vrednost.
		for (int i=0; i<songCount; i++)
		{
			for (int j=0; j<i; j++)
			{
				 komulaVre[i] += verjetnostIzbire[j];
			}
			komulaVre[songCount] = 1.0;
		}
		
		Random rnd = new Random ();
		
		// Selekcija
		for (int i=0; i<songCount; i++)
		{
			// Izbira melodij ki jih bomo spremenili.
			r = rnd.nextDouble(); 
			for (int j=0; j<songCount; j++)
			{
				if (r <= komulaVre[0])
				{
					tempSongs[0] = songs [0];
				}
				
				else if (komulaVre[j] <= r && r < komulaVre[j+1])
				{
					tempSongs[i] = songs [j];
					break;
				}	
			}
		}
		
		// Križanje
		// Izbiranje pesmi za križanje.
		int x=0;
		for (int i=0; i<songCount; i++)
		{
			r = rnd.nextDouble(); 
			if (r < Pc)
			{
				crossingSelIndex[x] = i;
			}
		}	
		
		r = rnd.nextInt(songLength); // Položaj križanja. 
		if (crossingSelIndex.length > 1)
		{
			int i=0;
			while (i<r)
			{
				tempSongs[crossingSelIndex[0]][i] = tempSongs[crossingSelIndex[1]][i];
				i++;
			}
			
			while (i<songLength)
			{
				tempSongs[crossingSelIndex[1]][i] = tempSongs[crossingSelIndex[0]][i];
				i++;
			}
		}
				
		// Mutacija
		for (int i=0; i<songCount; i++)
		{
			for (int j=0; j<songLength; j++)
			{
				r = rnd.nextDouble(); 
				if (r < Pm)
				{
					if (j%2==0)
						tempSongs[i][j] = generateNote();
					else
						tempSongs[i][j] = generateLength();
				}
			}
		}
			
		songs = tempSongs;
		
	}
	
	public int generateNote ()
	{
		Random generator = new Random(); 
		int i = 60 + generator.nextInt(13);
		return i;		
	}
	
	public int generateLength ()
	{
		int values [] = {4,8,16,32};
		
		Random generator = new Random(); 
		int i = generator.nextInt(4);
		return values [i];
	}
	
	public void onButtonNaprejClick (View view)
	{
		ratings[0] = (int) ratingBar1.getRating();
		ratings[1] = (int) ratingBar2.getRating();
		ratings[2] = (int) ratingBar3.getRating();
		ratings[3] = (int) ratingBar4.getRating();
				
		try {
			if (player.isPlaying())
			{
				player.stop();
				player.prepare();
			}
		} catch (Exception ex) {}
		
		if (napredek < stPonovitev)
		{
			resetRatingBars ();
			EA ();
			napredek++;
			textViewNapredek.setText(String.format("%s / %s", napredek, stPonovitev));
		}
		
		else
		{
			if (izbiraMelodije == 0)
			{
				Toast toast = Toast.makeText(this, "Izberite najljubšo melodijo.", Toast.LENGTH_SHORT);
				toast.show();
			}
			
			else
			{
				MidiFile mf = new MidiFile();
				mf.noteSequenceFixedVelocity (songs[izbiraMelodije-1], 127);
				
				try {
					mf.writeToFile ("/mnt/sdcard/somefile.mid");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
			
	}
	
	public void onTextViewMelodijaClick (View view)
	{
		TextView tv = (TextView) view;
		
		izbiraMelodije = Integer.parseInt(tv.getContentDescription().toString());
		
		if (napredek == stPonovitev)
		{
			Intent intent = new Intent (this, MySongEndActivity.class);
			this.startActivity(intent);
		}
	}
	
	public void onButtonPlayClick (View view)
	{
		Button butt = (Button) view;
		
		MidiFile mf = new MidiFile();
		
		if (butt.getContentDescription().equals("1"))
		{
			try {
				mf.noteSequenceFixedVelocity (songs[0], 127);			
			} catch (Exception ex) {}
		}
		
		else if (butt.getContentDescription().equals("2"))
		{
			try {
				mf.noteSequenceFixedVelocity (songs[1], 127);			
			} catch (Exception ex) {}
		}
		
		else if (butt.getContentDescription().equals("3"))
		{
			try {
				mf.noteSequenceFixedVelocity (songs[2], 127);			
			} catch (Exception ex) {}
		}
		
		else if (butt.getContentDescription().equals("4"))
		{
			try {
				mf.noteSequenceFixedVelocity (songs[3], 127);			
			} catch (Exception ex) {}
		}
		
		try
		{
			if (player.isPlaying())
			{
				player.stop();
				player.reset();
			}
			
			else
			{
			
				mf.writeToFile ("/mnt/sdcard/somefile.mid");
				player.reset();
				player.setDataSource("/mnt/sdcard/somefile.mid");
				player.prepare();
				player.start();
			}
		}
		
		catch (Exception ex)
		{
		}
		
		
	}
	

	
	private void resetRatingBars ()
	{
		ratingBar1.setRating(0.0f);
		ratingBar2.setRating(0.0f);
		ratingBar3.setRating(0.0f);
		ratingBar4.setRating(0.0f);
	}
	
	public void initializeSequence ()
	{
		  songs[0] = new int []
	    	      {
	    	      72, mf.QUAVER,
	    	      72, mf.QUAVER,
	    	      72, mf.QUAVER,
	    	      72, mf.QUAVER,
	    	      72, mf.QUAVER,
	    	      72, mf.QUAVER,
	    	      72, mf.QUAVER,
	    	      72, mf.QUAVER,
	    	      72, mf.SEMIQUAVER,
	    	      72, mf.SEMIQUAVER,
	    	      72, mf.QUAVER,
	    	      };
		
	    songs[1] = new int []
	    	      {
	    	      60, mf.SEMIQUAVER,
	    	      60, mf.SEMIQUAVER,
	    	      60, mf.CROTCHET,
	    	      62, mf.SEMIQUAVER,
	    	      64, mf.QUAVER,
	    	      62, mf.QUAVER,
	    	      60, mf.QUAVER,
	    	      61, mf.QUAVER,
	    	      60, mf.QUAVER,
	    	      62, mf.SEMIQUAVER,
	    	      60, mf.QUAVER,
	    	      };
	    
	    songs[2] = new int []
	    	      {
	    	      60, mf.SEMIQUAVER,
	    	      65, mf.SEMIQUAVER,
	    	      70, mf.CROTCHET,
	    	      72, mf.QUAVER,
	    	      65, mf.QUAVER,
	    	      62, mf.QUAVER,
	    	      67, mf.CROTCHET,
	    	      72, mf.CROTCHET,
	    	      72, mf.CROTCHET,
	    	      70, mf.CROTCHET,
	    	      66, mf.MINIM,
	    	      };
	    
	    songs[3] = new int []
	    	      {
	    	      60, mf.QUAVER,
	    	      65,  mf.SEMIQUAVER,
	    	      70,  mf.CROTCHET,
	    	      69,  mf.QUAVER,
	    	      65,  mf.QUAVER,
	    	      62,  mf.QUAVER,
	    	      67,  mf.QUAVER,
	    	      72,  mf.QUAVER,
	    	      -1,  mf.SEMIQUAVER,
	    	      72,  mf.SEMIQUAVER,
	    	      76,  mf.MINIM,
	    	      };
//	    songs[0] = new int []
//	    	      {
//	    	      60, mf.SEMIQUAVER,
//	    	      65, mf.SEMIQUAVER,
//	    	      70, mf.CROTCHET,
//	    	      69, mf.QUAVER,
//	    	      65, mf.QUAVER,
//	    	      -1, mf.MINIM,
//	    	      67, mf.SEMIBREVE,
//	    	      72, mf.MINIM,
//	    	      70, mf.SEMIQUAVER,
//	    	      72, mf.SEMIQUAVER,
//	    	      76, mf.MINIM,
//	    	      };
//		
//	    songs[1] = new int []
//	    	      {
//	    	      60, mf.SEMIQUAVER,
//	    	      65, mf.SEMIQUAVER,
//	    	      70, mf.CROTCHET,
//	    	      69, mf.SEMIQUAVER,
//	    	      -1, mf.QUAVER,
//	    	      67, mf.QUAVER,
//	    	      67, mf.QUAVER,
//	    	      67, mf.QUAVER,
//	    	      67, mf.MINIM,
//	    	      70, mf.SEMIQUAVER,
//	    	      76, mf.MINIM,
//	    	      };
//	    
//	    songs[2] = new int []
//	    	      {
//	    	      60, mf.SEMIQUAVER,
//	    	      65, mf.SEMIQUAVER,
//	    	      70, mf.CROTCHET,
//	    	      72, mf.QUAVER,
//	    	      65, mf.QUAVER,
//	    	      62, mf.QUAVER,
//	    	      67, mf.CROTCHET,
//	    	      72, mf.CROTCHET,
//	    	      72, mf.CROTCHET,
//	    	      70, mf.CROTCHET,
//	    	      66, mf.MINIM,
//	    	      };
//	    
//	    songs[3] = new int []
//	    	      {
//	    	      60, mf.QUAVER,
//	    	      65,  mf.SEMIQUAVER,
//	    	      70,  mf.CROTCHET,
//	    	      69,  mf.QUAVER,
//	    	      65,  mf.QUAVER,
//	    	      62,  mf.QUAVER,
//	    	      67,  mf.QUAVER,
//	    	      72,  mf.QUAVER,
//	    	      -1,  mf.SEMIQUAVER,
//	    	      72,  mf.SEMIQUAVER,
//	    	      76,  mf.MINIM,
//	    	      };

	}
}
