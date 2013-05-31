package projetk.test.jet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class IzbiraActivity extends Activity {
	DrawView drawView;
	ApplicationExt app;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        app =  (ApplicationExt) getApplication ();    
        
        LinearLayout layout = new LinearLayout(this); 
        layout = (LinearLayout) View.inflate(this, R.layout.izbira, null);
        
        int i = 5;
        while (i < app.getSeznamPesmi().size())
        {
	        LinearLayout ly1 = new LinearLayout (this);
	        
	        final Button btn = new Button (this);
	        btn.setText(app.getSeznamPesmi().get(i).getNaslov());
	        btn.setGravity(Gravity.CENTER);
	        btn.setTag(new String (String.valueOf(i++)));
	        btn.setOnClickListener(new OnClickListener() 
	        {
	            
	            public void onClick(View v) 
	            {
	              onButtonClick (v);
	            }
	        });
	        
	        ly1.addView (btn);
	        layout.addView(ly1);
        }
        
        setContentView(layout);
       
    }
    
    
   public void onButtonClick (View view)
   {
	   int id = Integer.parseInt(String.valueOf(view.getTag()));
	   
	   app.setIzbranaPesem(id);
	   
	   Intent intent = new Intent (this, PesemActivity.class);
	   this.startActivity(intent);
   }
}
