package com.grub.svg4mobile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import android.util.Log;

import javax.xml.parsers.*;

public class Parser {

	public static Parser instance = null;

	private int contador = 0;
	private float width = 0;
	private float height = 0;
	private Document dom;
	private Vector<Figure> elementos;

	/**
	 * 
	 */
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
	 * Constructor de la clase genÃ©rico
	 */
	public Parser() {
		elementos = new Vector<Figure>();
	}

	/**
	 * MÃ©todo privado encargado de ir parseando el archivo SVG e insertando los
	 * elementos en la correspondiente lista.
	 */
	public void parseXML(String path) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		elementos = new Vector<Figure>();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			// Inicializamos el documento.
			dom = db.parse(new File(path));
		} catch (ParserConfigurationException pce) {
			Log.e("svg4mobile", "pce " + pce);
		} catch (SAXException se) {
			Log.e("svg4mobile", "se " + se);
		} catch (IOException ioe) {
			Log.e("svg4mobile", "ioe " + ioe);
		}

		Element root = dom.getDocumentElement();
		this.width = 0;
		this.height = 0;
		if (root.getAttribute("width") != null
				&& root.getAttribute("height") != null) {
			try{
				this.width = Float.parseFloat(root.getAttribute("width"));
			}catch(Exception e){
				//
			}
			try{
				this.height = Float.parseFloat(root.getAttribute("height"));
			}catch(Exception e){
				//
			}
			
			Log.d("svg4mobile", "width " + width);
			Log.d("svg4mobile", "height " + height);
		}

		this.parseElement(root, elementos);

		// Log.d("svg4mobile", "fin while  ");
	}

	private void parseElement(Element root, Vector<Figure> figuras) {
		NodeList lista = root.getChildNodes();
		int i = 0;
		ParseStrategy est = null;
		//El contexto
		ParseContext context;

		
		while (i++ < (lista.getLength() - 1)) {
			Log.d("svg4mobile", " Parser: " + i);
			if (lista.item(i).getNodeType() == 1) {
				Element nodo = (Element) lista.item(i);
				Log.d("svg4mobile", " Nodo: " + nodo.getNodeName());

				if (nodo.getTagName().compareToIgnoreCase("rect") == 0) {
					est = new RectStrategy();
				} else if (nodo.getTagName().compareToIgnoreCase("path") == 0) {
					est = new PathStrategy();
				} else if (nodo.getTagName().compareToIgnoreCase("text") == 0) {
					est = new TextStrategy();
				} else if (nodo.getTagName().compareToIgnoreCase("circle") == 0) {
					est = new CircleStrategy();
				} else if (nodo.getTagName().compareToIgnoreCase("ellipse") == 0) {
					est = null; // TODO
				} else if (nodo.getTagName().compareToIgnoreCase("line") == 0) {
					est = null; // TODO
				} else if (nodo.getTagName().compareToIgnoreCase("polyline") == 0) {
					est = null; // TODO
				} else if (nodo.getTagName().compareToIgnoreCase("polygon") == 0) {
					est = null; // TODO
				} else if (nodo.getTagName().compareToIgnoreCase("g") == 0) {
					est = null; // TODO
					readGroup(nodo, figuras);
				} // endif
				
				context = new ParseContext(est, nodo);
				context.setStrategy(est);
				try {
					Figure fg = (Figure) context.runStrategy();
					figuras.add(fg);
				} catch (Exception e) {}
				
			}
		}

	}

	private void readGroup(Element nodo, Vector<Figure> figuras) {

		String transform = nodo.getAttribute("transform");
		Transformations t = new Transformations();
		if (transform.length() > 1) {

			if (transform.substring(0, transform.indexOf("(")).equals("rotate")) {
				String degrees = transform
						.substring(transform.indexOf("(") + 1);
				degrees = degrees.substring(0, degrees.indexOf(" "));
				t.setRotation(Float.parseFloat(degrees));
			}
			// Ahora mismo se utiliza un String en transform de BGroup.

		}
		Vector<Figure> f = new Vector<Figure>();
		BGroup grupo = new BGroup(f, t);
		// Aqui añadimos el group a la lista.
		figuras.add(grupo);
		parseElement(nodo, grupo.f);

	}

	/**
	 * Método que devuelve el puntero al primer elemento.
	 */
	public void First() {
		contador = 0;
	}

	/**
	 * Método que indica al usuario si hay más elementos en la lista.
	 * Implementación del patrón Iterator.
	 * 
	 * @return Devuelve true si quedan más elementos en el iterador.
	 */
	public Boolean hasNext() {
		if ((contador + 1) <= elementos.size())
			return true;
		else
			return false;
	}

	/**
	 * Método que usará el usuario para obtener el siguiente elemento de la
	 * lista e incrementar el contador.
	 * 
	 * @return Devuelve el siguiente elemento.
	 */
	public Figure next() {
		// Log.d("svg4mobile", "next  " + String.valueOf(contador) + " " +
		// String.valueOf(elementos.size()));
		return elementos.get(contador++);
		// return new BRect( 0f, 0f, 735.03961f, 720.34869f, "#FFFF9C",
		// "#FFFFFF", 3f, new Transformations());
	}

	/**
	 * Anchura del documento obtenida de parsear el SVG
	 */
	public float getWidth() {
		float w = 0;
		// Se inicializa a cero y si se lee del fichero svg se cambia el valor.
		if (width != 0)
			w = width;
		return w;
	}

	/**
	 * Altura del documento obtenida de parsear el SVG
	 */
	public float getHeight() {
		float h = 0;
		if (height != 0)
			h = height;
		return h;
	}

	/**
	 * Devuelve el tamaño de la lista de elementos.
	 * 
	 * @return Tamaño de la lista.
	 */
	public int getSize() {
		return elementos.size();
	}
}
