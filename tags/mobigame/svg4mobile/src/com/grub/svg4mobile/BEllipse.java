package com.grub.svg4mobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
/**
 * @see http://www.w3.org/TR/SVG/shapes.html#EllipseElement
 */
public class BEllipse extends Figure{
	
	private Paint paint;
	private RectF rectElement;
	private Paint paintBorder;
	private Transformations tr;
	
	/**
	 * 
	 * @param cx Coordenada del centro en el eje X, si se omite se asume 0
	 * @param cy Coordenada del centro el en eje Y, si se omite se asume 0 
	 * @param rx Radio en el eje X
	 * @param ry Radio en el eje Y
	 * @param rgb Código de color hexadecimal de la forma #FFFFFF
	 * @param brgb Código de color hexadecimal de la forma #FFFFFF para el borde
	 * @param bwidth Grosor del borde del rectángulo. Debe ser 0 para omitir el borde.
	 * @param tr Transformaciones que se aplicarán a la figura
	 */
	public BEllipse (float cx, float cy, float rx, float ry, String rgb, String brgb, float bwidth, Transformations tr){
		
		this.tr = tr;
		this.rectElement = new RectF(cx-rx, cy-ry, 2*rx, 2*ry);
		
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
	 * 
	 * @param rx Radio en el eje X
	 * @param ry Radio en el eje Y
	 * @param rgb Código de color hexadecimal de la forma #FFFFFF
	 * @param brgb Código de color hexadecimal de la forma #FFFFFF para el borde
	 * @param bwidth Grosor del borde del rectángulo. Debe ser 0 para omitir el borde.
	 * @param tr Transformaciones que se aplicarán a la figura
	 */
	public BEllipse (float rx, float ry, String rgb, String brgb, float bwidth, Transformations tr) {
		this(0f,0f,rx,ry,rgb,brgb,bwidth,tr);
	}
	
	/**
	 * Pinta la figura
	 * @param canvas
	 */
	public void draw (Canvas canvas){
		canvas.save();
		this.tr.applyTransformations(canvas);
		canvas.drawOval(rectElement, paint);
		canvas.drawOval(rectElement, paintBorder);
		canvas.restore();
	}
	
}