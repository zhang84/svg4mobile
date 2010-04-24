package com.grub.svg4mobile;

import java.util.Enumeration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;

/**
 * Pinta figuras de tipo Text 
 * @see http://www.w3.org/TR/SVG11/text.html
 */
public class Text extends Figure {
  
	private Paint paint;
	private Transformations tr;
	
	private float x,y;
	private String text;
    
	/**
	 * Crea una figura de tipo texto 
	 * @param x Posición X
	 * @param y Posición Y
	 * @param size Tamaño de la fuente
	 * @param txt Cadena de texto 
	 * @param rgb Código de color hexadecimal de la forma #FFFFFF
	 * @param tr Transformaciones que se aplicarán a la figura
	 */
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
@Override
public void addFigure(Figure f) {
	// TODO Auto-generated method stub
	
}
@Override
public Enumeration<Figure> getFigures() {
	return null;
}
@Override
public void removeFigure(Figure f) {
}
}
