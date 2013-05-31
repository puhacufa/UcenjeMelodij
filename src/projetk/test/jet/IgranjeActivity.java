package projetk.test.jet;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

public class IgranjeActivity extends Activity{
	DrawView drawView;
	
	Bitmap bitmap = Bitmap.createBitmap(840, 480, Bitmap.Config.ARGB_8888);
	Canvas canvas = new Canvas (bitmap);
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        drawView = new DrawView(this);
        drawView.setBackgroundColor(Color.GRAY);
        drawView.setBackgroundResource(R.drawable.ozadje_crtovje);
        setContentView(drawView);
           
        Paint paint = new Paint ();
        paint.setColor (Color.RED);
        canvas.drawRect(10, 400, 100, 200, paint);
    }
}
