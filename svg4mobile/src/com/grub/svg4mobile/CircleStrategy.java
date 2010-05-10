package com.grub.svg4mobile;

import org.w3c.dom.*;

/**
 * Estrategia concreta para parsear elementos de tipo Circle.
 */
public class CircleStrategy implements ParseStrategy
{
		/**
		 * Parsea un elemento de tipo Circle
		 * @param nodo Nodo con la información del elemento
		 * @return Devuelve el elemento de tipo BCircle
		 */
        public Figure parse_element(Element nodo)
        {
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
					t.setTMatrix(ParseTransformations.parseMatrix(transform));
				} else if (transform.substring(0,
						transform.indexOf("(")).equals("translate")) {
					float[] ta = ParseTransformations.parseTranslate(transform);
					t.setTranslate(ta[0], ta[1]);
				}
			}

			return new BCircle(Float.parseFloat(x), Float
					.parseFloat(y), Float.parseFloat(r), rgb,
					"#000000", 1.5f, t);
        }
}