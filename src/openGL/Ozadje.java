package openGL;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import projetk.test.jet.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

class Ozadje {

    private final String vertexShaderCode =
        "uniform mat4 uMVPMatrix;\n" +
                "attribute vec4 aPosition;\n" +
                "attribute vec2 aTextureCoord;\n" +
                "varying vec2 vTextureCoord;\n" +
                "void main() {\n" +
                "  gl_Position = uMVPMatrix * aPosition;\n" +
                "  vTextureCoord = aTextureCoord;\n" +
                "}\n";

    private final String fragmentShaderCode =
            "precision mediump float;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "uniform sampler2D sTexture;\n" +
                    "void main() {\n" +
                    "  gl_FragColor = texture2D(sTexture, vTextureCoord);\n" +
                    "}\n";

    private final FloatBuffer vertexBuffer;
    private int mProgram = 0;
    private ShortBuffer drawListBuffer;
    private int mPositionHandle;

    static final int COORDS_PER_VERTEX = 5;
    static float squareCoords[] = { -1.667f,  1.0f, 0.0f, 1.0f, 0.0f,
                                    -1.667f, -1.0f, 0.0f, 1.0f, 1.0f,
                                     1.667f, -1.0f, 0.0f, 0.0f, 1.0f,
                                     1.667f,  1.0f, 0.0f, 0.0f, 0.0f};

    private short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; 
    
    // Texture
    int mTextureUniformHandle;
    int mTextureCoordinateHandle;
    int mTextureDataHandle;
    
    public Ozadje (Context context) {
        ByteBuffer bb = ByteBuffer.allocateDirect (squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);
        
        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
        
        int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
        int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        
        mTextureDataHandle = loadTexture(context, R.drawable.ozadje);
        
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);  
        GLES20.glAttachShader(mProgram, fragmentShader); 
        
        GLES20.glLinkProgram(mProgram);   
        
        mTextureCoordinateHandle = GLES20.glGetAttribLocation(mProgram, "aTextureCoord");
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
    }


	private final int vertexCount = squareCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // bytes per vertex
    
    public void draw(float [] mvpMatrix) {
    	int mMVPMatrixHandle;
    	
        GLES20.glUseProgram(mProgram);
        
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle);
        
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        
        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(mPositionHandle, 
                                    3,
                                    GLES20.GL_FLOAT, 
                                    false,
                                    vertexStride, 
                                    vertexBuffer);
        
        vertexBuffer.position(3);
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        
        
        GLES20.glVertexAttribPointer(mTextureCoordinateHandle, 
        							2, 
        							GLES20.GL_FLOAT, 
        							false,
        							vertexStride, 
        							vertexBuffer);
        
        GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);
      
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
    
    public static int loadTexture (Context context, final int resourceId) {
    	  int[] textures = new int[1];
         // GLES20.glGenTextures(1, textures, 0);

          int mTextureID = 21; //textures[0];
          GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID);

          GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                  GLES20.GL_NEAREST);
          GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                  GLES20.GL_TEXTURE_MAG_FILTER,
                  GLES20.GL_LINEAR);

          GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
                  GLES20.GL_REPEAT);
          GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
                  GLES20.GL_REPEAT);

          Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);

          GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
          bitmap.recycle();
	 
	     return mTextureID;
    }
}
