package com.grub.svg4mobile;

import android.graphics.Canvas;
import java.util.Vector;

/**
 * @see http://www.w3.org/TR/SVG/struct.html#Groups
 */
public class Group extends Figure {

	private Transformations tr;
	public Vector<Figure> f;
	
	/**
	 * Constructor de la clase con todos los parámetros
	 * @param f
	 * @param tr
	 */
	public Group(Vector<Figure> f, Transformations tr) {
		this.tr=tr;
		this.f =f;
	}
	
	/**
	 * Función que pinta la figura
	 * @param canvas 
	 */
	public void draw(Canvas canvas) {
		canvas.save();
		this.tr.applyTransformations(canvas);
		for (int i=0; i<f.size(); i++)
			this.f.elementAt(i).draw(canvas);
		canvas.restore();
	}	
}
