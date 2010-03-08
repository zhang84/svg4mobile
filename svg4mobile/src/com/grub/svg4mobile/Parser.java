package com.grub.svg4mobile;

public class Parser {
	private int contador=0;
	public Parser() {
		
	}
	
	public Object next() {
		if (contador++ == 0)
			return new FlatColoredSquare();
		else return new SmoothColoredSquare();
	}
	/*
	 * Anchura del documento obtenida de parsear el SVG
	 */
	public double getWidth() {
		return 735.03961;
	}
	/*
	 * Altura del documento obtenida de parsear el SVG
	 */
	public double getHeight() {
		return 720.34869;
	}
	
	public Boolean hasNext() {
		if (contador>1)
			return false;
		else return true;
	}	
}
