package openGL;

import projetk.test.jet.IzbiraActivity;
import projetk.test.jet.ProjektJetActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MeniOpenGLActivity extends Activity{
	private GLSurfaceView mGLView;
	
	float x;
	float y;
	
	@Override
	public void onCreate (Bundle savedInstanceBundle) {
		super.onCreate(savedInstanceBundle);
		
//		mGLView = new MyGLSurfaceView (this);
//		setContentView (mGLView);
	}
	
	class MyGLSurfaceView extends GLSurfaceView {
		
		public MyGLSurfaceView (Context context) {
			super (context);
			
			setEGLContextClientVersion(2);
			//setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
			setRenderer (new MyRenderer (this.getContext()));
		}
		
		@Override
		public boolean onTouchEvent (MotionEvent e) {
			x = e.getX();
			y = e.getY();
			
			switch (e.getAction()) {
			case MotionEvent.ACTION_DOWN:
				
				MyRenderer.isTouch = true;
				
				if ((y >168) && (y < 310))
				{				
					MyRenderer.buttonPressedNum = 2;
				}
				
				else if (y < 168){
					MyRenderer.buttonPressedNum = 1;
				}
				
				else {
					MyRenderer.buttonPressedNum = 0;
				}
					
				//requestRender();
				break;
			
			case MotionEvent.ACTION_UP:
				
				MyRenderer.isTouch = false;

				if (MyRenderer.buttonPressedNum == 1) {
					startGame();
				}
				
				else if (MyRenderer.buttonPressedNum == 2) {
					quitApp();
				}
			
				break;
			}
			
			
			return true;
		}
	}
	
	void startGame () {
	 	Intent myIntent = new Intent(this, IzbiraActivity.class);
    	this.startActivity(myIntent);
	}
	
	void quitApp () {
		finish();
	}
}
