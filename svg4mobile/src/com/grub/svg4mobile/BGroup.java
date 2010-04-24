package com.grub.svg4mobile;

import java.util.Enumeration;
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
	 * Constructor de la clase con todos los parámetros
	 * @param tr
	 */
	public BGroup(Transformations tr) {
		this.tr=tr;
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

	/** Añade una figura a una figura. Implementación del patrón Composite. 
	 * @param f Figura a añadir
	 */

	public void addFigure(Figure f) 
	{
		this.f.addElement(f);
	}

	/**
	 * Elimina una figura de una figura. Implementación del patrón Composite. 
	 * @param f Figura a eliminar
	 */
	public void removeFigure(Figure f) 
	{
		this.f.removeElement(f);
	}
	
	/**
	 * Obtiene la lista de figuras de una figura. Implementación del patrón Composite.
	 * @return Lista de figuras de una figura
	 */
	public Enumeration<Figure> getFigures() 
	{
		return this.f.elements();
	}
	
}
