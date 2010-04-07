package com.grub.svg4mobile;

import android.graphics.Canvas;

/**
 * @see http://www.w3.org/TR/SVG/struct.html#Groups
 */
public class Group extends Figure {

	private Transformations tr;
	private Figure[] f;

	/**
	 * 
	 * @param f
	 * @param tr
	 */
	public Group(Figure[] f, Transformations tr) {
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
		for (int i=0; i<f.length; i++)
			this.f[i].draw(canvas);
		canvas.restore();
	}	
}
