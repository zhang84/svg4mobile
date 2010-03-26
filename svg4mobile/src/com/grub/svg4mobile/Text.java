package com.grub.svg4mobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;

public class Text extends Figure {
  
	private Paint paint;
	private Transformations tr;
	
	private float x,y;
	private String text;
    
  public Text(float x, float y, int size, String txt, String rgb, Transformations tr) {
    
	  this.text = txt;
	  this.x = x;
	  this.y = y;
	  this.tr = tr;
	  
	  this.paint = new Paint();
	  this.paint.setColor(Color.parseColor(rgb));
	  this.paint.setAntiAlias(true);		 
	  this.paint.setTextSize(size);
	  
	  //TODO
	  //this.paint.setStyle(Style.FILL);
	  //this.paint.setStyle(Paint.Style.STROKE);
	  //this.paint.setStrokeWidth(1);
	  
  }
  /**
  * Función que pinta la figura
  * @param canvas
  */
  public void draw(Canvas canvas) {  
		//Se aplican las transformaciones a la figura
		canvas.save();
		this.tr.applyTransformations(canvas);
				
		canvas.drawText(text, this.x, this.y, paint);
		canvas.restore();
  }
}
