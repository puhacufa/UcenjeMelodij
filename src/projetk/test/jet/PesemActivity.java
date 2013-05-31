package projetk.test.jet;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PesemActivity extends Activity{
	
	TextView textViewBesedilo;
	ApplicationExt app;
	MediaPlayer mediaPlayer;
	
	int izbranaPesem;
	int[] midiSources;
	
	public void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pesem);
		
		textViewBesedilo = (TextView) findViewById(R.id.textViewBesedilo);
		
		app =  (ApplicationExt) getApplication ();
		
		izbranaPesem = app.getIzbranaPesem();
		
		String besedilo = app.getSeznamPesmi().get(izbranaPesem).getBesedilo();
		textViewBesedilo.setText(besedilo);
		//textViewBesedilo.setText("ala");
		
		midiSources = new int[]
				{
					R.raw.kuza_pazi,
					R.raw.cuk_se_je_ozenil
				};
		
		if (izbranaPesem < 2)
		{
			mediaPlayer = MediaPlayer.create (this, midiSources[izbranaPesem]);
		}
	}
	
	@Override
	public void onStop ()
	{
		super.onStop();
		mediaPlayer.release();
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
		}
	}
	
	public void onButtonStartGameClick (View view)
	{
		Intent intent = new Intent (this, IgranjeActivity.class);
		this.startActivity(intent);
	}
}
