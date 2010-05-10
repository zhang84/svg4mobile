package com.grub.svg4mobile;

public class ParseTransformations {
	/**
	 * Método que se encarga de parsear la cadena obtenida del atributo
	 * transform de un elemento del fichero svg para obtener la matriz de
	 * transformación a aplicarle.
	 * 
	 * @param transform
	 *            Cadena que contiene la transformación a aplicar a un elemento
	 *            del SVG
	 * @return Matriz de transformación
	 */
	public static float[] parseMatrix(String transform) {
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

	/**
	 * Método que se encarga de parsear la cadena obtenida del atributo
	 * transform de un elemento del fichero svg para obtener la traslación
	 * a aplicarle.
	 * @param transform Cadena que contiene la traslación a aplicar a un elemento
	 *            del SVG
	 * @return Valores de la traslación.
	 */
	public static float[] parseTranslate(String transform) {
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
}
