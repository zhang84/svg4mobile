package com.grub.svg4mobile;

import android.graphics.Canvas;
import android.util.Log;

/**
 * Pinta figuras de tipo Rect
 * @see http://www.w3.org/TR/SVG11/shapes.html#RectElement
 */
public class BRect extends Figure {
	/**
	 * Crea un rectángulo con borde utilizando las clases Rect y Line 
	 */
	private Rect shape;
	private Boolean hasWidth = false;
	private Line borderTop;
	private Line borderDown;
	private Line borderLeft;
	private Line borderRight;
	private Transformations tr;
	
	/**
	 * Crea un rectángulo con borde
	 * @param x Coordenada x
	 * @param y Coordenada y
	 * @param w Ancho
	 * @param h Alto
	 * @param rgb Código de color hexadecimal de la forma #FFFFFF
	 * @param brgb Código de color hexadecimal de la forma #FFFFFF para el borde
	 * @param bwidth Grosor del borde del rectángulo. Debe ser 0 para omitir el borde.
	 * @param tr Transformaciones que se aplicarán a la figura
	 */
	public BRect(float x, float y, float w, float h, String rgb, String brgb, float bwidth, Transformations tr) {
		this.tr = tr;
		this.shape = new Rect(x,y,(x+w),(y+h),rgb);
		this.hasWidth = ( bwidth > 0);
		if (this.hasWidth) {
			this.borderTop = new Line( x, y+h, x+w, y+h, brgb, bwidth );
			this.borderDown = new Line( x, y, x+w, y, brgb, bwidth );
			this.borderLeft = new Line( x, y+h, x, y, brgb, bwidth );
			this.borderRight = new Line( x+w, y, x+w, y+h, brgb, bwidth );
		}
	}
	/**
	 * Pinta la figura
	 * @param canvas
	 */
	public void draw(Canvas canvas) {
		//Se aplican las transformaciones a la figura
		canvas.save();
		this.tr.applyTransformations(canvas);
		this.shape.draw(canvas);
		if (this.hasWidth) {
			this.borderTop.draw(canvas);
			this.borderDown.draw(canvas);
			this.borderLeft.draw(canvas);
			this.borderRight.draw(canvas);
		}
		canvas.restore();
	}
	
}
