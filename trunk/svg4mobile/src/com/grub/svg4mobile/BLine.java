package com.grub.svg4mobile;

import android.graphics.Canvas;

/**
 * @see http://www.w3.org/TR/SVG/shapes.html#LineElement
 */
public class BLine extends Figure {
	private Line l;
	private Transformations tr;

	/**
	 * Crea un segmento
	 * @param x1 Coordenada X de comienzo
	 * @param y1 Coordenada Y de comienzo
	 * @param x2 Coordenada X de fin
	 * @param y2 Coordenada Y de fin
	 * @param brgb Código de color hexadecimal de la forma #FFFFFF para el borde
	 * @param bwidth Grosor del borde del rectángulo. Debe ser 0 para omitir el borde.
	 * @param tr Transformaciones que se aplicarán a la figura
	 */
	public BLine(float x1, float y1, float x2, float y2, String brgb, float bwidth, Transformations tr) {
		this.l = new Line(x1,y1,x2,y2,brgb,bwidth);
		this.tr=tr;
	}
	
	/**
	 * Pinta la figura
	 * @param canvas
	 */
	public void draw (Canvas canvas){
		//Se aplican las transformaciones a la figura
		canvas.save();
		this.tr.applyTransformations(canvas);
		this.l.draw(canvas);
		canvas.restore();
	}

}
