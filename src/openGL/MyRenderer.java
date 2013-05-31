package openGL;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

public class MyRenderer implements GLSurfaceView.Renderer{
	
	private Context mActivityContext = null;
	
	Kvadrat mKvadrat;
	Kvadrat2 mKvadrat2;
	Ozadje mOzadje;
	
	float mAnimMatrix [] = new float [16];
	float mProjMatrix [] = new float [16];
	float mVMatrix [] = new float [16];
	float mMVPMatrix [] = new float [16];
	float mRotationMatrix [] = new float[16];
 	float mScaleMatrix [] = new float[16];
	
	public static volatile boolean isTouch = false;
 	public static volatile int buttonPressedNum= 0;
 	
 	public MyRenderer (Context context) {
 		mActivityContext = context;
 	}
 	
	public void onSurfaceCreated (GL10 unused, EGLConfig config) {
		
		// Nastavi barvo ozadja
		GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
		
		mOzadje = new Ozadje(mActivityContext);
		mKvadrat = new Kvadrat(mActivityContext);
		mKvadrat2 = new Kvadrat2(mActivityContext);
		
	}
	
	public void onDrawFrame (GL10 unused) {
	    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
	    
	    
	    Matrix.setLookAtM(mVMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
	    Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mVMatrix, 0);
	    
	    Matrix.scaleM(mAnimMatrix, 0, mMVPMatrix, 0, 0.8f, 1.0f, 1.0f);
	    
	    mOzadje.draw(mMVPMatrix);
	    
	    if(buttonPressedNum == 2 && isTouch)
	    {
		    mKvadrat2.changeColor(mActivityContext, isTouch, buttonPressedNum);
		    mKvadrat2.draw(mAnimMatrix);
	    }
	    
	    else {
	    	mKvadrat2.draw(mMVPMatrix);
	    }
	    
	    if(buttonPressedNum == 1 && isTouch)
	    {
		    mKvadrat.changeColor(mActivityContext, isTouch, buttonPressedNum);
		    mKvadrat.draw(mAnimMatrix);
	    }
	    
	    else {
	    	mKvadrat.draw(mMVPMatrix);
	    }
	    
	}
	
	public void onSurfaceChanged (GL10 unused, int width, int height) {
		GLES20.glViewport(0,  0,  width,  height);
		
		float ratio = (float) width/height;
		
		Matrix.frustumM (mProjMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
	}
	
	public static int loadShader (int type, String shaderCode) {
		
		int shader = GLES20.glCreateShader (type);
		
		GLES20.glShaderSource (shader, shaderCode);
		GLES20.glCompileShader(shader);
		
		return shader;
	}

}
