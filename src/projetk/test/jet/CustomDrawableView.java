package projetk.test.jet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;
import android.view.animation.ScaleAnimation;

public class CustomDrawableView extends View {
    private ShapeDrawable mDrawable;

    public CustomDrawableView(Context context) {
    super(context);

    int x = 10;
    int y = 10;
    int width = 300;
    int height = 50;
    
    mDrawable = new ShapeDrawable(new OvalShape());
    mDrawable.getPaint().setColor(0xff74AC23);
    mDrawable.setBounds(x, y, x + width, y + height);
    }

    protected void onDraw(Canvas canvas) {
    mDrawable.draw(canvas);
    }
    
    public void animate ()
    {
    	 ScaleAnimation scale = new ScaleAnimation(1, 5, 1, 1);
    	    scale.setDuration(2000);
    	    startAnimation(scale);
    }
    }
  