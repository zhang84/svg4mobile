package com.grub.svg4mobile;

import java.util.Enumeration;

import android.graphics.Canvas;

public abstract class Figure {
	
	private String id = "";
	private String transform = "";
	
	/**
	 * Inicializa el valor del identificador.
	 * @param id
	 */
	public void setId(String id){
		this.id = id;
	}
	
	/**
	 * Método que obtiene la cadena con que se identifica
	 * a la figura dentro del svg.
	 * @return Devuelve el identificador de la figura. 
	 */
	public String getId(){
		return id;
	}
	
	/**
	 * Actualizar la transformada que ha de aplicarse a la figura.
	 * @param transform Cadena con la transformada.
	 */
	public void setTransform(String transform){
		this.transform = transform;
	}
	
	/**
	 * Método que accede al valor de la transformada.
	 * @return Devuelve cadena vacía si no hay que aplicar ninguna.
	 */
	public String getTransform(){
		return this.transform;
	}

	/**
	 * Método que pinta la figura
	 * @param canvas
	 */
	public void draw(Canvas canvas) {
		
	}
	
	// Funciones del patrón Composite
	
	/**
	 * Añade una figura a una figura 
	 * @param f Figura a añadir
	 */
	public abstract void addFigure(Figure f); 
	
	/**
	 * Elimina una figura de una figura 
	 * @param f Figura a eliminar
	 */
	public abstract void removeFigure(Figure f);
	
	/**
	 * Obtiene la lista de figuras de una figura.
	 * @return Lista de figuras de una figura
	 */
	public abstract Enumeration<Figure> getFigures();
	
}
