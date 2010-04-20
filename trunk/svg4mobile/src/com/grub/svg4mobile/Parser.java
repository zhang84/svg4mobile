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
		while (i++ < (lista.getLength() - 1)) {
			Log.d("svg4mobile", " Parser: " + i);
			if (lista.item(i).getNodeType() == 1) {
				Element nodo = (Element) lista.item(i);
				Log.d("svg4mobile", " Nodo: " + nodo.getNodeName());

				if (nodo.getTagName().compareToIgnoreCase("rect") == 0) {
					String x = nodo.getAttribute("x");
					String y = nodo.getAttribute("y");
					String w = nodo.getAttribute("width");
					String h = nodo.getAttribute("height");
					String style = nodo.getAttribute("style");
					String borderwidth = "0";
					String rgb = "";
					String border = "";
					try {
						borderwidth = style.substring(style
								.indexOf("stroke-width:") + 13);
						borderwidth = borderwidth.substring(0, borderwidth
								.indexOf(";"));
					} catch (Exception e) {
						//
					}
					try {
						rgb = style.substring(style.indexOf("fill:") + 5);
						rgb = rgb.substring(0, rgb.indexOf(";"));
					} catch (Exception e) {
						//
					}
					try {
						border = style
								.substring(style.indexOf("stroke:") + 7);
						border = border.substring(0, border.indexOf(";"));
					} catch (Exception e) {
						//
					}

					if (border.compareToIgnoreCase("none") == 0
							|| border.compareTo("") == 0
							|| borderwidth.compareTo("") == 0) {
						borderwidth = "0.0";
					}

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
					Log.d("svg4mobile", "rect");
					BRect rectangulo = new BRect(Float.parseFloat(x), Float
							.parseFloat(y), Float.parseFloat(w), Float
							.parseFloat(h), rgb, border, Float
							.parseFloat(borderwidth), t);
					figuras.add(rectangulo);

				} else if (nodo.getTagName().compareToIgnoreCase("path") == 0) {

					try {
						String style = nodo.getAttribute("style");
						String borderwidth = "0";
						String rgb = "";
						String border = "";
						try {
							borderwidth = style.substring(style
									.indexOf("stroke-width:") + 13);
							borderwidth = borderwidth.substring(0, borderwidth
									.indexOf(";"));
						} catch (Exception e) {
							//
						}
						try {
							rgb = style.substring(style.indexOf("fill:") + 5);
							rgb = rgb.substring(0, rgb.indexOf(";"));
						} catch (Exception e) {
							//
						}
						try {
							border = style
									.substring(style.indexOf("stroke:") + 7);
							border = border.substring(0, border.indexOf(";"));
						} catch (Exception e) {
							//
						}
						if (border.compareToIgnoreCase("none") == 0
								|| border.compareTo("") == 0
								|| borderwidth.compareTo("") == 0) {
							borderwidth = "0.0";
						}
						// Log.d("svg4mobile", " rgb:  " + rgb + " x:  " + x+
						// " y: "
						// + y+ " w:  " + w+ " h:  " + h);
						String transform = nodo.getAttribute("transform");
						String d = nodo.getAttribute("d");
						Log.d("svg4mobile", d);
						//d = d+" e 0 0";
						d = d.replaceAll(" ", ",");
						
						String pts[] = d.split("[MmLlCcQqsStTaAhHvVzZ]");
						//Log.d("svg4mobile", "pts: "+Arrays.toString(pts));
						String letras[] = d.split("[-0-9,. ]+");
						//Log.d("svg4mobile", "letras: "+Arrays.toString(letras));
						SubPath sp[] = new SubPath[letras.length];
						int j2 =0;
						for (int j=0; j<letras.length; j++) {
							//Log.d("svg4mobile","Antes de split " + letras[j]);
							char tipo = letras[j].charAt(0);
							//Log.d("svg4mobile", "despues del split");
							float[] puntos = new float[0];
							if (tipo!='z' && tipo != 'Z') {
								j2++;
								while (pts[j2].compareTo(",")==0) {
									Log.v("svg4mobile", "-"+pts[j2]+"-");
									j2++;
								}
								
								String pts_arr[] = pts[j2].split(",");
								Log.d("svg4mobile", "pts: " + Arrays.toString(pts_arr));
								puntos = new float[pts_arr.length-1]; //!
								for (int k=1; k<pts_arr.length; k++)
									puntos[k-1]=Float.parseFloat(pts_arr[k]);
							}
							Log.d("svg4mobile", "tipo: "+ tipo + ", puntos: " + Arrays.toString(puntos));
							sp[j] = new SubPath(tipo, puntos);
						}
						Log.d("svg4mobile","For Terminado");

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

						Log.d("svg4mobile", "añadiendo sp a path");
						
						BPath mPath = new BPath(sp, rgb, border,
								Float.parseFloat(borderwidth), t);
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
					String borderwidth = "0";
					String rgb = "";
					String border = "";
					try {
						borderwidth = style.substring(style
								.indexOf("stroke-width:") + 13);
						borderwidth = borderwidth.substring(0, borderwidth
								.indexOf(";"));
					} catch (Exception e) {
						//
					}
					try {
						rgb = style.substring(style.indexOf("fill:") + 5);
						rgb = rgb.substring(0, rgb.indexOf(";"));
					} catch (Exception e) {
						//
					}
					try {
						border = style
								.substring(style.indexOf("stroke:") + 7);
						border = border.substring(0, border.indexOf(";"));
					} catch (Exception e) {
						//
					}

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
				} else if (nodo.getTagName().compareToIgnoreCase("ellipse") == 0) {
					// TODO
				} else if (nodo.getTagName().compareToIgnoreCase("line") == 0) {
					// TODO
				} else if (nodo.getTagName().compareToIgnoreCase("polyline") == 0) {
					// TODO
				} else if (nodo.getTagName().compareToIgnoreCase("polygon") == 0) {
					// TODO
				} else if (nodo.getTagName().compareToIgnoreCase("g") == 0) {
					readGroup(nodo, figuras);
				} // endif
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
