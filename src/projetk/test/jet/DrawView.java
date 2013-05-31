package projetk.test.jet;

import java.util.Vector;

import projekt.test.jet.database.RezultatPesem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.JetPlayer;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {
	Paint paintWhite = new Paint ();
	Paint paintBlack = new Paint ();
	Paint paintGreen = new Paint ();
	JetPlayer jetPlayer = JetPlayer.getJetPlayer();
	JetPlayer jetPlayer2 = JetPlayer.getJetPlayer();
	byte segmentId = 0;
	
	 Vector <Note> note = new Vector<Note> ();
     Vector <Integer> trajanja = new Vector<Integer>();
     
     ApplicationExt app;
    
     
     int leviOdmik = 0;
     int dolzina=0;
	 int skupnaDolzina = 0;
	 float desniOdmik = (float)0.0;
	
	 int prikazanaDolzina = 0;
	 int pozicijaIgranja = 0;
	
	 long zacetekPritiska;  // Zaèetek merjenja èasa od pritisku na tipko.
	 long pretecenCas;  // Kako dolgo je bila tipka pritisnjena.
	 
	 int stPritiskov = 0; // Kolikokrat je uporabnik pritisnil na klaviaturo;
	 
	 Bitmap bitmap = Bitmap.createBitmap (840, 480, Bitmap.Config.ARGB_8888);
	 Canvas canvas = new Canvas (bitmap);
	
	 int tipkaDol = -1;
	 int tipkaGor = -1;
	
	 public DrawView (Context context) {
		super(context);
		
		app = (ApplicationExt) context.getApplicationContext();
		 app.setNote (note);
		 
		app.resetSeznamRezultati ();
		 
	    jetPlayer.loadJetFile(getResources().openRawResourceFd(R.raw.c4_c5));
	    this.setBackgroundResource(R.drawable.ozadje);
	  
	    // Doloèi melodijo.
	    note.add (Note.C4);
	    trajanja.add (4);
	    note.add (Note.D4);
	    trajanja.add (4);
	    note.add (Note.E4);
	    trajanja.add (4);
	    note.add (Note.F4);
	    trajanja.add (4);
	    note.add (Note.G4);
	    trajanja.add (8);
	    note.add (Note.G4);
	    trajanja.add (8);
	    note.add (Note.A4);
	    trajanja.add (8);
	    note.add (Note.A4);
	    trajanja.add (8);
	    note.add (Note.G4);
	    trajanja.add (8);
	    
	    note.add (Note.A4);
	    trajanja.add (8);
	    note.add (Note.A4);
	    trajanja.add (8);
	    note.add (Note.G4);
	    trajanja.add (8);
	    
	    note.add (Note.F4);
	    trajanja.add (4);
	    note.add (Note.F4);
	    trajanja.add (4);
	    note.add (Note.F4);
	    trajanja.add (4);
	    note.add (Note.F4);
	    trajanja.add (4);
	    
	    note.add (Note.E4);
	    trajanja.add (8);
	    note.add (Note.E4);
	    trajanja.add (8);
	    note.add (Note.D4);
	    trajanja.add (8);
	    note.add (Note.D4);
	    trajanja.add (8);
	    note.add (Note.G4);
	    trajanja.add (8);
	    
	    note.add (Note.F4);
	    trajanja.add (4);
	    note.add (Note.F4);
	    trajanja.add (4);
	    note.add (Note.F4);
	    trajanja.add (4);
	    note.add (Note.F4);
	    trajanja.add (4);
	    
	    note.add (Note.E4);
	    trajanja.add (8);
	    note.add (Note.E4);
	    trajanja.add (8);
	    note.add (Note.D4);
	    trajanja.add (8);
	    note.add (Note.D4);
	    trajanja.add (8);
	    note.add (Note.C4);
	    trajanja.add (16);
	    
	    narisiNote (canvas, note, trajanja, pozicijaIgranja);
	}

	@Override
	public void onDraw (Canvas c) {
		//canvas.drawColor(Color.GRAY);
		narisiKlaviaturo (canvas);
        
        
        
        //draw the bitmap to the real canvas c
        c.drawBitmap(bitmap, 
            new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()), 
            new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()), null);        
	}
	
	private void narisiKlaviaturo (Canvas canvas)
	{
		paintWhite.setColor(Color.WHITE);
        paintWhite.setStrokeWidth(3);
        
        paintBlack.setColor(Color.BLACK);
        paintBlack.setStrokeWidth(0);
        
        paintGreen.setColor(Color.GREEN);
        paintGreen.setStrokeWidth(0);
        
        // Nariši bele tipke.
        canvas.drawRect(10, 470, 100, 250, paintWhite);
        canvas.drawRect(110, 470, 200, 250, paintWhite);
        canvas.drawRect(210, 470, 300, 250, paintWhite);
        canvas.drawRect(310, 470, 400, 250, paintWhite); 
        canvas.drawRect(410, 470, 500, 250, paintWhite);
        canvas.drawRect(510, 470, 600, 250, paintWhite);
        canvas.drawRect(610, 470, 700, 250, paintWhite);
        canvas.drawRect(710, 470, 800, 250, paintWhite);
        
        // Nariši èrne tikpe.
        canvas.drawRect(80, 370, 130, 250, paintBlack);
        canvas.drawRect(180, 370, 230, 250, paintBlack);
        canvas.drawRect(380, 370, 430, 250, paintBlack);
        canvas.drawRect(480, 370, 530, 250, paintBlack);
        canvas.drawRect(580, 370, 630, 250, paintBlack);
        canvas.drawRect(780, 370, 830, 250, paintBlack);
	}
	
	private void narisiNote (Canvas canvas, Vector<Note> note, Vector<Integer> trajanja, int zacetek)
	{
		int leviOdmik=0;
		int dolzina=0;
		int skupnaDolzina = 0;
		int desniOdmik = 0;
		
		final int stZaigranihNot = 0;
		
		for (int i=zacetek; i<note.size(); i++)
		{
			leviOdmik = skupnaDolzina;
			dolzina = trajanja.elementAt(i)*10;
			skupnaDolzina+=dolzina;
			desniOdmik = leviOdmik + dolzina - 5; 
					
			switch (note.elementAt(i))
			{
				case C4:
				{
					canvas.drawRect(leviOdmik, 98, desniOdmik, 108, paintGreen);
					break;
				}
				
				case D4:
				{
					canvas.drawRect(leviOdmik, 86, desniOdmik, 96, paintGreen);
					break;
				}
				
				case E4:
				{
					canvas.drawRect(leviOdmik, 78, desniOdmik, 88, paintGreen);
					break;
				}
				
				case F4:
				{
					canvas.drawRect(leviOdmik, 70, desniOdmik, 80, paintGreen);
					break;
				}
				
				case G4:
				{
					canvas.drawRect(leviOdmik, 62, desniOdmik, 72, paintGreen);
					break;
				}
				
				case A4:
				{
					canvas.drawRect(leviOdmik, 52, desniOdmik, 62, paintGreen);
					break;
				}
				
				case B4:
				{
					canvas.drawRect(leviOdmik, 46, desniOdmik, 56, paintGreen);
					break;
				}
				
				case C5:
				{
					canvas.drawRect(leviOdmik, 30, desniOdmik, 40, paintGreen);
					break;
				}
			}
		}
			
	}
	
	private void narisiNoto (Canvas canvas, Note nota, float trajanje)
	{
		//int leviOdmik=0;
		//int dolzina=0;
		//int skupnaDolzina = 0;
		//int desniOdmik = 0;
		
		leviOdmik = skupnaDolzina;
		dolzina = trajanja.elementAt(pozicijaIgranja)*10;// trajanje*10;
		skupnaDolzina+= dolzina;
		desniOdmik = leviOdmik + trajanje*10 - 5; 	
		
		pozicijaIgranja++;
		
		if (prikazanaDolzina >= 76)
		{
			Paint paint = new Paint();
    		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    		canvas.drawPaint(paint);
    		
    		narisiNote (canvas, note, trajanja, pozicijaIgranja);
    		
			skupnaDolzina = 0;
			prikazanaDolzina = 0;
			
		}
		
		prikazanaDolzina += trajanja.elementAt(pozicijaIgranja);
		
			switch (nota)
			{
				case C4:
				{
					canvas.drawRect(leviOdmik, 215, desniOdmik, 225, paintGreen);
					break;
				}
				
				case D4:
				{
					canvas.drawRect(leviOdmik, 203, desniOdmik, 213, paintGreen);
					break;
				}
				
				case E4:
				{
					canvas.drawRect(leviOdmik, 195, desniOdmik, 205, paintGreen);
					break;
				}
				
				case F4:
				{
					canvas.drawRect(leviOdmik, 187, desniOdmik, 197, paintGreen);
					break;
				}
				
				case G4:
				{
					canvas.drawRect(leviOdmik, 179, desniOdmik, 189, paintGreen);
					break;
				}
				
				case A4:
				{
					canvas.drawRect(leviOdmik, 171, desniOdmik, 181, paintGreen);
					break;
				}
				
				case B4:
				{
					canvas.drawRect(leviOdmik, 163, desniOdmik, 173, paintGreen);
					break;
				}
				
				case C5:
				{
					canvas.drawRect(leviOdmik, 153, desniOdmik, 163, paintGreen);
					break;
				}
			}			
	}
	
	private void shraniPritisk (Note nota, float trajanje)
	{
		boolean jePravilno = false;
		float fNatancnost;
		
		if (trajanja.get(stPritiskov-1) < trajanje)
			fNatancnost = (trajanja.get(stPritiskov-1) / trajanje)*100;
		
		else 
			fNatancnost = (trajanje / trajanja.get(stPritiskov-1) )*100;
		
		if (note.get(stPritiskov-1) == nota)
			jePravilno = true;
		
		app.addRezultatPesem(new RezultatPesem (nota, app.getIzbranaPesem(), jePravilno, (int) (fNatancnost)));
	}
	
	 @Override
	    public boolean onTouchEvent(MotionEvent event) {
	        int x = (int)event.getX();
	        int y = (int)event.getY();
	        switch (event.getAction()) 
	        {
	            case MotionEvent.ACTION_DOWN:
	            {
	            	zacetekPritiska = System.nanoTime(); 
	            	
	            	if (x>10 && x<101)
	            	{
	            		tipkaDol = 0;
	            		jetPlayer.queueJetSegment(0, -1, 0, 0, 0, segmentId);
	            		jetPlayer.play();
	            		
	            		this.invalidate();
	            	}
	            	
	            	else if (x>110 && x<200)
	            	{
	            		jetPlayer.clearQueue();
	            		jetPlayer.queueJetSegment(1, -1, 0, 0, 0, segmentId);
	            		jetPlayer.play();
	            		
	            		this.invalidate();
	            	}
	            	
	            	else if (x>210 && x<300)
	            	{
	            		jetPlayer.clearQueue();
	            		jetPlayer.queueJetSegment(2, -1, 0, 0, 0, segmentId++);
	            		jetPlayer.play();
	            		
	            		this.invalidate();
	            	}
	            	
	            	else if (x>310 && x<400)
	            	{
	            		jetPlayer.clearQueue();
	            		jetPlayer.queueJetSegment(3, -1, 0, 0, 0, segmentId++);
	            		jetPlayer.play();

	            		this.invalidate();
	            	}
	            	
	            	else if (x>410 && x<500)
	            	{
	            		jetPlayer.clearQueue();
	            		jetPlayer.queueJetSegment(4, -1, 0, 0, 0, segmentId++);
	            		jetPlayer.play();
	            		
	            		this.invalidate();
	            	}
	            	
	            	else if (x>510 && x<600)
	            	{
	            		jetPlayer.clearQueue();
	            		jetPlayer.queueJetSegment(5, -1, 0, 0, 0, segmentId++);
	            		jetPlayer.play();
	            		
	            		this.invalidate();
	            	}
	            	
	            	else if (x>610 && x<700)
	            	{
	            		jetPlayer.clearQueue();
	            		jetPlayer.queueJetSegment(6, -1, 0, 0, 0, segmentId++);
	            		jetPlayer.play();
	            		
	            		this.invalidate();
	            	}
	            	
	            	else if (x>710 && x<800)
	            	{
	            		jetPlayer.clearQueue();
	            		jetPlayer.queueJetSegment(7, -1, 0, 0, 0, segmentId++);
	            		jetPlayer.play();
	            		
	            		this.invalidate();
	            	}
	            	break;
	            }
	            
	            case MotionEvent.ACTION_MOVE:
	            {
	            	break;
	            }
	            
	            case MotionEvent.ACTION_UP:
	            {
	            	pretecenCas = System.nanoTime() - zacetekPritiska;
	            	double pt = (pretecenCas / 1000000000.0 * 120.0)/15.0;
	            	
	            	stPritiskov++;
	            	if (stPritiskov == note.size())
	            	{
	            		Intent intentKonecIgre = new Intent (getContext (), KonecIgreActivity.class);
	            		super.getContext().startActivity (intentKonecIgre);
	            		break; // Zaèasna rešitev za znebitev napake IndexOutOfbounds v "NarisiNoto".
	            	}
	            	
	            	if (x>10 && x<100)
	            	{
	              		jetPlayer.clearQueue();	              		
	              		narisiNoto (this.canvas, Note.C4, (float)pt);
	              		shraniPritisk(Note.C4, (float)pt);
	              	}
	            	
	            	else if (x>110 && x<200)
	            	{
	            		jetPlayer.clearQueue();
	              		narisiNoto (this.canvas, Note.D4, (float)pt);
	              		shraniPritisk(Note.D4, (float)pt);
	            	}
	            	
	            	else if (x>210 && x<300)
	            	{
	            		jetPlayer.clearQueue();
	            		narisiNoto (this.canvas, Note.E4, (float)pt);
	            		shraniPritisk(Note.E4, (float)pt);
	            	}
	            	
	            	else if (x>310 && x<400)
	            	{
	            		jetPlayer.clearQueue();
	            		narisiNoto (this.canvas, Note.F4, (float)pt);
	            		shraniPritisk(Note.F4, (float)pt);
	            	}
	            	
	            	else if (x>410 && x<500)
	            	{
	            		jetPlayer.clearQueue();
	            		narisiNoto (this.canvas, Note.G4, (float)pt);
	            		shraniPritisk(Note.G4, (float)pt);
	            	}
	            	
	            	else if (x>510 && x<600)
	            	{
	            		jetPlayer.clearQueue();
	            		narisiNoto (this.canvas, Note.A4, (float)pt);
	            		shraniPritisk(Note.A4, (float)pt);
	            	}
	            	
	            	else if (x>610 && x<700)
	            	{
	            		jetPlayer.clearQueue();
	            		narisiNoto (this.canvas, Note.B4, (float)pt);
	            		shraniPritisk(Note.B4, (float)pt);
	            	}
	            	
	            	else if (x>710 && x<800)
	            	{
	            		jetPlayer.clearQueue();
	            		narisiNoto (this.canvas, Note.C5, (float)pt);
	            		shraniPritisk(Note.C5, (float)pt);
	            	}
	            	
            		this.invalidate();
	            	break;
	            }
	        }
	    return true;
	    }
}

