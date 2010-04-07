package com.grub.svg4mobile;

import java.util.Vector;
import android.graphics.Canvas;
/**
 * Clase que implementa un grupo de figuras. Se emplea un patrón composite para
 * manejar la posibilidad de incluir como hijos tanto nuevos grupos como otro tipo
 * de figuras (hojas).
 * @author greendoc
 *
 */
public class BGroup extends Figure {
	private Transformations tr;
	public Vector<Figure> f = new Vector<Figure>();
	
	/**
	 * Constructor de la clase con todos los parámetros
	 * @param f
	 * @param tr
	 */
	public BGroup(Vector<Figure> f, Transformations tr) {
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
	
	@Override
	public void add(Figure figura){
		f.add(figura);
	}
	
	@Override
	public void remove(int position){
		f.remove(position);
	}
	
}
