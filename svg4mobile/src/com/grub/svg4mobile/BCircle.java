package com.grub.svg4mobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class BCircle extends Figure{
	
	private Paint paint;
	private Paint paintBorder;
	private Transformations tr;
	private float cx;
	private float cy;
	private float r;
	
	/**
	 * Crea un círculo con borde
	 * @param cx Coordenada x del centro
	 * @param cy Coordenada y del centro
	 * @param r radio
	 * @param rgb Código de color hexadecimal de la forma #FFFFFF
	 * @param brgb Código de color hexadecimal de la forma #FFFFFF para el borde
	 * @param bwidth Grosor del borde del rectángulo. Debe ser 0 para omitir el borde.
	 * @param tr Transformaciones que se aplicarán a la figura
	 */
	public BCircle (float cx, float cy, float r, String rgb, String brgb, float bwidth, Transformations tr){
		
		this.tr = tr;
		this.cx = cx;
		this.cy = cy;
		this.r = r;
		
		this.paintBorder = new Paint();
		if (bwidth>0) {
			this.paintBorder.setStyle(Paint.Style.STROKE);
			this.paintBorder.setStrokeWidth(bwidth);
			this.paintBorder.setColor(Color.parseColor(brgb));
			this.paintBorder.setAntiAlias(true);
		}
		
		this.paint = new Paint();
		this.paint.setColor(Color.parseColor(rgb));
		this.paint.setAntiAlias(true);	
	}
	
	/**
	 * Crea un círculo con borde
	 * @param r radio
	 * @param rgb Código de color hexadecimal de la forma #FFFFFF
	 * @param brgb Código de color hexadecimal de la forma #FFFFFF para el borde
	 * @param bwidth Grosor del borde del rectángulo. Debe ser 0 para omitir el borde.
	 * @param tr Transformaciones que se aplicarán a la figura
	 */
	public BCircle (float r, String rgb, String brgb, float bwidth, Transformations tr){
		this(0,0,r,rgb,brgb,bwidth,tr);
	}
	
	/**
	 * Pinta la figura
	 * @param canvas
	 */
	public void draw (Canvas canvas){
		canvas.save();
		this.tr.applyTransformations(canvas);
		canvas.drawCircle(this.cx, this.cy, this.r, paint);
		canvas.drawCircle(this.cx, this.cy, this.r, paintBorder);
		canvas.restore();
	}
	
}