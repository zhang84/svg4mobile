package com.grub.svg4mobile;

import java.util.Vector;
/**
 * Clase que implementa un grupo de figuras. Se emplea un patrón composite para
 * manejar la posibilidad de incluir como hijos tanto nuevos grupos como otro tipo
 * de figuras (hojas).
 * @author greendoc
 *
 */
public class BGroup extends Figure {
	
	public Vector<Figure> hijos = new Vector<Figure>();
	
	@Override
	public void add(Figure figura){
		hijos.add(figura);
	}
	
	@Override
	public void remove(int position){
		hijos.remove(position);
	}
	
}
