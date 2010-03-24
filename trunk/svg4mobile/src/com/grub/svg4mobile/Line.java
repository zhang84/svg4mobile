package com.grub.svg4mobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Line {
	
	private float x1, x2, y1, y2;
	private Paint paint;
	/**
	 * Crea un segmento entre 2 puntos
	 * @param x1 Coordenada x del punto 1
	 * @param y1 Coordenada y del punto 1
	 * @param x2 Coordenada x del punto 2
	 * @param y2 Coordenada y del punto 2
	 * @param brgb Código de color hexadecimal de la forma #FFFFFF
	 * @param bwidth Grosor del segmento.
	 */
	public Line (float x1, float y1, float x2, float y2, String brgb, float bwidth){
		this.x1 = x1;
		this.y1 = y1;
		
		this.x2 = x2;
		this.y2 = y2;
		
		this.paint = new Paint();
		
		this.paint.setColor(Color.parseColor(brgb));
		this.paint.setAntiAlias(true);
		
	}
	
	public void draw (Canvas canvas){
		canvas.drawLine(this.x1, this.y1, this.x2, this.y2, this.paint);
	}
	
}
