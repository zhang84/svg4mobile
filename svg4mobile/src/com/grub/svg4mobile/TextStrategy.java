package com.grub.svg4mobile;

import org.w3c.dom.*;

/**
 * Estrategia concreta para parsear elementos de tipo Text.
 */
public class TextStrategy implements ParseStrategy
{
	/**
	 * Parsea un elemento de tipo Text
	 * @param nodo Nodo con la información del elemento
	 * @return Devuelve el elemento de tipo Text
	 */
        public Figure parse_element(Element nodo)
        {

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
              t.setTMatrix(ParseTransformations.parseMatrix(transform));
            } else if (transform.substring(0,
              transform.indexOf("(")).equals("translate")) {
              float[] ta = ParseTransformations.parseTranslate(transform);
            t.setTranslate(ta[0], ta[1]);
            }
          }
          return new Text(x, y, size, text, rgb, t);
        }
}