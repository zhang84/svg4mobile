package com.grub.svg4mobile;

import java.util.Arrays;

import org.w3c.dom.*;

import android.util.Log;

/**
 * Estrategia concreta para parsear elementos de tipo Path.
 */
public class PathStrategy implements ParseStrategy
{
	/**
	 * Parsea un elemento de tipo Path
	 * @param nodo Nodo con la información del elemento
	 * @return Devuelve el elemento de tipo BPath
	 */
        public Figure parse_element(Element nodo)
        {
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
						// Avanza el puntero de puntos hasta encontrar puntos.
						j2++;
					}
					
					String pts_arr[] = pts[j2].split(",");
					//Log.d("svg4mobile", "pts: " + Arrays.toString(pts_arr));
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
					t.setTMatrix(ParseTransformations.parseMatrix(transform));
				} else if (transform.substring(0,
						transform.indexOf("(")).equals("translate")) {
					float[] ta = ParseTransformations.parseTranslate(transform);
					t.setTranslate(ta[0], ta[1]);
				}
			}

			Log.d("svg4mobile", "añadiendo sp a path");
			
			return new BPath(sp, rgb, border,
					Float.parseFloat(borderwidth), t);

        }
}