package com.grub.svg4mobile;

import java.io.IOException;
import java.util.Vector;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;

public class Parser {
	private int contador=0;
	private double width = 0;
	private double height = 0;
	private Document dom;
	private Vector<Rect> rects;
	
	/**
	 * Constructor de la clase genérico
	 */
	public Parser() {
		rects = new Vector<Rect>();
	}
	
	/**
	 * Constructor con el que se inicializa directamente la
	 * ruta del archivo svg a parsear.
	 * @param path
	 */
	public Parser (String path){
		rects = new Vector<Rect>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			//Inicializamos el documento.
			dom = db.parse(path);
			this.parseXML();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Método privado encargado de ir parseando el archivo SVG e insertando
	 * los elementos en la correspondiente lista.
	 */
	private void parseXML (){
		Element root = dom.getDocumentElement();
		if (root.getAttribute("width") != null && root.getAttribute("height") != null){
			width = Double.parseDouble(root.getAttribute("width"));
			height = Double.parseDouble(root.getAttribute("height"));
			System.out.println(width);
		}
		NodeList lista = root.getElementsByTagName("rect");
		int i = 0;
		while (i++ < lista.getLength()){
			Element nodoRectangular = (Element) lista.item(i);
			String x = nodoRectangular.getAttribute("x");
			String y = nodoRectangular.getAttribute("y");
			String w = nodoRectangular.getAttribute("width");
			String h = nodoRectangular.getAttribute("height");
			String style = nodoRectangular.getAttribute("style");
			//La cadena que contiene el atributo style tiene la forma:
			// fill:#rrggbb;fill-opacity:1;...
			String rgb = style.substring(style.indexOf(":")+1);
			rgb = rgb.substring(0,rgb.indexOf(";"));
			Rect rectangulo = new Rect(Float.parseFloat(x),Float.parseFloat(y),Float.parseFloat(w), Float.parseFloat(h),rgb);
			rects.add(rectangulo);
		}
	}
	
	/**
	 * Método que indica al usuario si hay más elementos en la lista.
	 * Implementación del patrón Iterator.
	 * @return Devuelve true si quedan más elementos en el iterador.
	 */
	public Boolean hasNext() {
		if (contador>rects.size())
			return false;
		else return true;
	}
	
	/**
	 * Método que usará el usuario para obtener el siguiente elemento
	 * de la lista e incrementar el contador.
	 * @return Devuelve el siguiente elemento.
	 */
	public Object next() {
		if (contador++ == 0)
			return new FlatColoredSquare();
		else return new SmoothColoredSquare();
	}
	/**
	 * Anchura del documento obtenida de parsear el SVG
	 */
	public double getWidth() {
		double w = 735.03961;
		//Se inicializa a cero y si se lee del fichero svg se cambia el valor.
		if (width != 0)
			w = width;
		return w;
	}
	/**
	 * Altura del documento obtenida de parsear el SVG
	 */
	public double getHeight() {
		double h = 720.34869;
		if (height != 0)
			h = height;
		return h;
	}
	
		
}
