package com.grub.svg4mobile;

import java.io.File;
import java.io.IOException;
import java.security.acl.Group;
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
	 * Método privado que se encarga de parsear la cadena obtenida del atributo
	 * transform de un elemento del fichero svg para obtener la matriz de
	 * transformación a aplicarle.
	 * 
	 * @param transform
	 *            Cadena que contiene la transformación a aplicar a un elemento
	 *            del SVG
	 * @return Matriz de transformación
	 */
	private float[] parseMatrix(String transform) {
		float valores_m[] = { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f };
		if (transform.length() > 1) {
			transform = transform.substring(transform.indexOf("(") + 1,
					transform.indexOf(")"));

			String a = transform.substring(0, transform.indexOf(","));
			transform = transform.substring(transform.indexOf(",") + 1,
					transform.length());
			valores_m[0] = Float.parseFloat(a);
			String b = transform.substring(0, transform.indexOf(","));
			transform = transform.substring(transform.indexOf(",") + 1,
					transform.length());
			valores_m[1] = Float.parseFloat(b);
			String c = transform.substring(0, transform.indexOf(","));
			transform = transform.substring(transform.indexOf(",") + 1,
					transform.length());
			valores_m[2] = Float.parseFloat(c);
			String d = transform.substring(0, transform.indexOf(","));
			transform = transform.substring(transform.indexOf(",") + 1,
					transform.length());
			valores_m[3] = Float.parseFloat(d);
			String e = transform.substring(0, transform.indexOf(","));
			transform = transform.substring(transform.indexOf(",") + 1,
					transform.length());
			valores_m[4] = Float.parseFloat(e);
			String f = transform.substring(0, transform.length());
			valores_m[5] = Float.parseFloat(f);
			// Log.d("svg4mobile", " trans:  " + " a: " +a + " b: " +b + " c: "
			// +c + " d: " +d + " e: " +e + " f: " +f);
		}

		return valores_m;
	}

	private float[] parseTranslate(String transform) {
		float valores_m[] = { 0.0f, 0.0f };

		transform = transform.substring(transform.indexOf("(") + 1, transform
				.indexOf(")"));

		String a = transform.substring(0, transform.indexOf(","));
		transform = transform.substring(transform.indexOf(",") + 1, transform
				.length());
		valores_m[0] = Float.parseFloat(a);
		String b = transform.substring(0, transform.length());
		valores_m[1] = Float.parseFloat(b);

		return valores_m;
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
		if (root.getAttribute("width") != null
				&& root.getAttribute("height") != null) {
			this.width = Float.parseFloat(root.getAttribute("width"));
			this.height = Float.parseFloat(root.getAttribute("height"));
			Log.d("svg4mobile", "width " + width);
			Log.d("svg4mobile", "height " + height);
		}

		this.parseElement(root,elementos);
		
				// Log.d("svg4mobile", "fin while  ");
	}
	
	private void parseElement(Element root, Vector<Figure> figuras){
		NodeList lista = root.getChildNodes();
		int i = 0;
		while (i++ < (lista.getLength() - 1)) {
			Log.d("svg4mobile", " Parser: " + i);
			if (lista.item(i).getNodeType() == 1) {
				Element nodo = (Element) lista.item(i);
				if (nodo.getTagName().compareToIgnoreCase("rect") == 0) {
					String x = nodo.getAttribute("x");
					String y = nodo.getAttribute("y");
					String w = nodo.getAttribute("width");
					String h = nodo.getAttribute("height");
					String style = nodo.getAttribute("style");
					// La cadena que contiene el atributo style tiene la forma:
					// fill:#rrggbb;fill-opacity:1;...
					String rgb = style.substring(style.indexOf(":") + 1);
					rgb = rgb.substring(0, rgb.indexOf(";"));
					// Log.d("svg4mobile", " rgb:  " + rgb + " x:  " + x+ " y: "
					// + y+ " w:  " + w+ " h:  " + h);
					String transform = nodo.getAttribute("transform");

					Transformations t = new Transformations();

					if (transform.length() > 1) {
						if (transform.substring(0, transform.indexOf("("))
								.equals("matrix")) {
							t.setTMatrix(this.parseMatrix(transform));
						} else if (transform.substring(0,
								transform.indexOf("(")).equals("translate")) {
							float[] ta = this.parseTranslate(transform);
							t.setTranslate(ta[0], ta[1]);
						}
					}

					BRect rectangulo = new BRect(Float.parseFloat(x), Float
							.parseFloat(y), Float.parseFloat(w), Float
							.parseFloat(h), rgb, "#000000", 1.5f, t);
					figuras.add(rectangulo);

				} else if (nodo.getTagName().compareToIgnoreCase("path") == 0) {

					try {

						String style = nodo.getAttribute("style");
						String rgb = style.substring(style.indexOf(":") + 1);
						rgb = rgb.substring(0, rgb.indexOf(";"));
						String transform = nodo.getAttribute("transform");
						String d = nodo.getAttribute("d");
						d = d.replaceAll(" ", ",");
						// Log.d("svg4mobile", " d:  " + d);
						String d2[] = d.split(",");
						int partes = 0;
						for (int z = 0; z < d2.length; z++) {
							if (d2[z].matches("[MmLlCcQqsStTaAhHvV]")) {
								partes++;
							}
						}

						SubPath[] mysubPath = new SubPath[partes];
						String tipo = "";
						int indice = 0;
						int iteracion = 0;

						while (indice < d2.length - 1) {

							tipo = d2[indice];
							// Log.d("svg4mobile", " d2:  " + d2[indice] +
							// " length " + indice +"/"+ d2.length);
							indice++;

							int indiceaux = indice;
							int numelem = 0;
							while (!d2[indiceaux]
									.matches("[MmLlCcQqsStTaAhHvVZz]")) {
								numelem++;
								indiceaux++;
							}

							float[] d3 = new float[numelem];

							indiceaux = indice;
							numelem = 0;
							while (!d2[indiceaux]
									.matches("[MmLlCcQqsStTaAhHvVZz]")) {
								d3[numelem] = (float) Float
										.parseFloat(d2[indiceaux]);
								// Log.d("svg4mobile", " d2:  " +
								// d2[indiceaux]);
								numelem++;
								indiceaux++;
							}

							char tip[] = tipo.toCharArray();
							mysubPath[iteracion] = new SubPath(tip[0], d3);

							iteracion++;
							indice = indiceaux;

						}

						Transformations t = new Transformations();

						if (transform.length() > 1) {
							if (transform.substring(0, transform.indexOf("("))
									.equals("matrix")) {
								t.setTMatrix(this.parseMatrix(transform));
							} else if (transform.substring(0,
									transform.indexOf("(")).equals("translate")) {
								float[] ta = this.parseTranslate(transform);
								t.setTranslate(ta[0], ta[1]);
							}
						}

						BPath mPath = new BPath(mysubPath, true, rgb,
								"#000000", 2, t);
						figuras.add(mPath);

						Log.d("svg4mobile", "  Path agregado  ");

					} catch (Exception e) {

						Log.d("svg4mobile", "  Error en Path:  " + e);
					}

				} else if (nodo.getTagName().compareToIgnoreCase("text") == 0) {
					String style = nodo.getAttribute("style");
					// tiene la siguiente forma:
					// font-size:28px;font-style:normal;font-weight:normal;...
					int size = Integer.parseInt(style.substring(style
							.indexOf(":") + 1, style.indexOf("px")));
					style = style.substring(style.indexOf("fill:"));
					String rgb = style.substring(style.indexOf(":") + 1, style
							.indexOf(";"));
					String text = "E";
					String transform = nodo.getAttribute("transform");
					float x = Float.parseFloat(nodo.getAttribute("x"));
					float y = Float.parseFloat(nodo.getAttribute("y"));
					NodeList nLista = nodo.getChildNodes();
					for (int c = 0; c < nLista.getLength(); c++) {
						if (nLista.item(c).getNodeType() == 1) {
							Element tspan = (Element) nLista.item(c);
							NodeList tLista = tspan.getChildNodes();
							text = tLista.item(0).getNodeValue(); // suponeos
																	// que hay
																	// solo un
																	// texto,
																	// sino hay
																	// que ahcer
																	// un for

							// Log.d("svg4mobile", " text:  " +
							// tLista.item(0).getNodeValue() );
						}
					}

					Transformations t = new Transformations();

					if (transform.length() > 1) {
						if (transform.substring(0, transform.indexOf("("))
								.equals("matrix")) {
							t.setTMatrix(this.parseMatrix(transform));
						} else if (transform.substring(0,
								transform.indexOf("(")).equals("translate")) {
							float[] ta = this.parseTranslate(transform);
							t.setTranslate(ta[0], ta[1]);
						}
					}

					Text texto = new Text(x, y, size, text, rgb, t);
					figuras.add(texto);
				} else if (nodo.getTagName().compareToIgnoreCase("circle") == 0) {
					String x = nodo.getAttribute("cx");
					String y = nodo.getAttribute("cy");
					String r = nodo.getAttribute("r");
					String style = nodo.getAttribute("style");
					String rgb = style.substring(style.indexOf(":") + 1);
					rgb = rgb.substring(0, rgb.indexOf(";"));

					String transform = nodo.getAttribute("transform");

					Transformations t = new Transformations();

					if (transform.length() > 1) {
						if (transform.substring(0, transform.indexOf("("))
								.equals("matrix")) {
							t.setTMatrix(this.parseMatrix(transform));
						} else if (transform.substring(0,
								transform.indexOf("(")).equals("translate")) {
							float[] ta = this.parseTranslate(transform);
							t.setTranslate(ta[0], ta[1]);
						}
					}

					BCircle circulo = new BCircle(Float.parseFloat(x), Float
							.parseFloat(y), Float.parseFloat(r), rgb,
							"#000000", 1.5f, t);
					figuras.add(circulo);
				} else if (nodo.getTagName().compareToIgnoreCase("g") == 0) {
					readGroup(nodo, figuras);
				}
				// if
			}
		}

	}

	private void readGroup(Element nodo, Vector<Figure> figuras) {
		BGroup grupo = new BGroup();
		String transform = nodo.getAttribute("transform");
		if (transform.length() > 1) {
			/*Transformations t = new Transformations();
			if (transform.substring(0, transform.indexOf("(")).equals("rotate")) {
				String degrees = transform.substring(transform.indexOf("(") + 1);
				degrees = degrees.substring(0, degrees.indexOf(" "));
				t.setRotation(Float.parseFloat(degrees));
			}*/
			//Ahora mismo se utiliza un String en transform de BGroup.
			
		}
		//Aqui añadimos el group a la lista.
		//figuras.add(grupo);
		parseElement(nodo,grupo.hijos);
		
	}

	/**
	 * Método que devuelve el puntero al primer elemento.
	 */
	public void First() {
		contador = 0;
	}

	/**
	 * MÃ©todo que indica al usuario si hay mÃ¡s elementos en la lista.
	 * ImplementaciÃ³n del patrÃ³n Iterator.
	 * 
	 * @return Devuelve true si quedan mÃ¡s elementos en el iterador.
	 */
	public Boolean hasNext() {
		if ((contador + 1) <= elementos.size())
			return true;
		else
			return false;
	}

	/**
	 * MÃ©todo que usarÃ¡ el usuario para obtener el siguiente elemento de la
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
		float w = 735.03961f;
		// Se inicializa a cero y si se lee del fichero svg se cambia el valor.
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

	/**
	 * Devuelve el tamaño de la lista de elementos.
	 * 
	 * @return Tamaño de la lista.
	 */
	public int getSize() {
		return elementos.size();
	}
}
