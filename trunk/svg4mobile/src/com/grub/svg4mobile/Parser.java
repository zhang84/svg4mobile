package com.grub.svg4mobile;

import java.io.IOException;
import java.util.Vector;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;

public class Parser {
	
	public static Parser instance = null;
	
	private int contador=0;
	private float width = 0;
	private float height = 0;
	private Document dom;
	private Vector<Figure> elementos;
	
	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new Parser();
	    }
	}
	
	public static Parser getInstance() {
		if (instance == null) 
			createInstance();
	    return instance;
	}

	/**
	 * Constructor de la clase genérico
	 */
	public Parser() {
		elementos = new Vector<Figure>();
	}
	
	/**
	 * Método privado encargado de ir parseando el archivo SVG e insertando
	 * los elementos en la correspondiente lista.
	 */
	private void parseXML (String path){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			//Inicializamos el documento.
			dom = db.parse(path);
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		Element root = dom.getDocumentElement();
		if (root.getAttribute("width") != null && root.getAttribute("height") != null){
			this.width = Float.parseFloat(root.getAttribute("width"));
			this.height = Float.parseFloat(root.getAttribute("height"));
			System.out.println(width);
		}
		NodeList lista = root.getChildNodes();
		int i = 0;
		while (i++ < lista.getLength()){
			Element nodo = (Element) lista.item(i);
			if (nodo.getTagName().compareToIgnoreCase("rect")==0){
				String x = nodo.getAttribute("x");
				String y = nodo.getAttribute("y");
				String w = nodo.getAttribute("width");
				String h = nodo.getAttribute("height");
				String style = nodo.getAttribute("style");
				//La cadena que contiene el atributo style tiene la forma:
				// fill:#rrggbb;fill-opacity:1;...
				String rgb = style.substring(style.indexOf(":")+1);
				rgb = rgb.substring(0,rgb.indexOf(";"));
				Rect rectangulo = new Rect(Float.parseFloat(x),Float.parseFloat(y),Float.parseFloat(w), Float.parseFloat(h),rgb,"#000000",0);
				elementos.add(rectangulo);
			}
			//else ...
			//Aquí han de utilizarse el resto de tipos para parsear.
		}
	}
	
	/**
	 * Método que indica al usuario si hay más elementos en la lista.
	 * Implementación del patrón Iterator.
	 * @return Devuelve true si quedan más elementos en el iterador.
	 */
	public Boolean hasNext() {
		if (contador>elementos.size())
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
	public float getWidth() {
		float w = 735.03961f;
		//Se inicializa a cero y si se lee del fichero svg se cambia el valor.
		if (width != 0)
			w = width;
		return w;
	}
	/**
	 * Altura del documento obtenida de parsear el SVG
	 */
	public float getHeight() {
		float h = 720.34869f;
		if (height != 0)
			h = height;
		return h;
	}
}
