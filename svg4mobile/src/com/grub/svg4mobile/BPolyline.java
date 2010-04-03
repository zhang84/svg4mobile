package com.grub.svg4mobile;

import android.graphics.Canvas;
/**
 * @see http://www.w3.org/TR/SVG/shapes.html#PolylineElement
 */
public class BPolyline extends Figure{
	
	private BPath p;
	
	/**
	 * Crea una polyline con borde
	 * @param points Puntos
	 * @param brgb Código de color hexadecimal de la forma #FFFFFF para el borde
	 * @param bwidth Grosor del borde del rectángulo. Debe ser 0 para omitir el borde.
	 * @param tr Transformaciones que se aplicarán a la figura
	 */
	public BPolyline (float[] points, String brgb, float bwidth, Transformations tr){
		SubPath[] s = new SubPath[2];
		float[] pointsM = new float[2];
		float[] pointsL = new float[points.length-2];
		pointsM[0] = points[0];
		pointsM[1] = points[1];
		for(int i=2; i<points.length; i++)
			pointsL[i-2] = points[i];
		s[0] = new SubPath('M', pointsM);
		s[1] = new SubPath('L', pointsL);
		this.p = new BPath(s, false, "", brgb, bwidth, tr);
	}
	
	/**
	 * Pinta la figura
	 * @param canvas
	 */
	public void draw (Canvas canvas){
		p.draw(canvas);
	}
	
}
