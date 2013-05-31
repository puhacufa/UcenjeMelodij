package mySong;

import java.io.IOException;
import java.util.Random;

import projetk.test.jet.R;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kevinboone.music.MidiFile;

public class MySongEndActivity extends Activity {
	Button buttonPlayMySong;
	MediaPlayer player = new MediaPlayer();
	
	@Override
	public void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);
		
		setContentView (R.layout.my_song_end);
		
		buttonPlayMySong = (Button) findViewById(R.id.ButtonPlayMySong);
		try {
			player.setDataSource ("/mnt/sdcard/somefile.mid");
			player.prepare();
		} catch (Exception ex) {}
	}
	
	@Override
	public void onStop ()
	{
		super.onStop();
		
		if (player.isPlaying())
				player.stop();
		
		player.release();
	}
	
	public void onButtonPlayClick (View view)
	{
		player.start();
	}
}
