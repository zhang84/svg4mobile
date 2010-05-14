package com.grub.svg4mobile;

import org.w3c.dom.*;

/**
 * Estrategia concreta para parsear elementos de tipo Rect.
 */
public class RectStrategy implements ParseStrategy
{
	
	/**
	 * Parsea un elemento de tipo Rect
	 * @param nodo Nodo con la información del elemento
	 * @return Devuelve el elemento de tipo BRect
	 */
        public Figure parse_element(Element nodo)
        {
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
					t.setTMatrix(ParseTransformations.parseMatrix(transform));
				} else if (transform.substring(0,
						transform.indexOf("(")).equals("translate")) {
					float[] ta = ParseTransformations.parseTranslate(transform);
					t.setTranslate(ta[0], ta[1]);
				}
			}
            return new BRect(Float.parseFloat(x), Float
					.parseFloat(y), Float.parseFloat(w), Float
					.parseFloat(h), rgb, border, Float
					.parseFloat(borderwidth), t);

        }
}