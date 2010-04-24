package com.grub.svg4mobile;

import android.graphics.Canvas;

import java.util.Enumeration;
import java.util.Vector;

/**
 * @see http://www.w3.org/TR/SVG/struct.html#Groups
 */
public class Group extends Figure {

	private Transformations tr;
	private Vector<Figure> f;
	
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
	 * Constructor de la clase con todos los parámetros
	 * @param tr
	 */
	public Group(Transformations tr) {
		this.tr=tr;
	}

	/**
	 * Añade una figura a una figura. Implementación del patrón Composite. 
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
