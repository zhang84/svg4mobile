package com.grub.svg4mobile;

import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * Aplica transformaciones a las figuras
 * @see http://www.w3.org/TR/SVG11/coords.html#TransformAttribute
 */
public class Transformations {
	private float[] tmatrix = {1,0,0,
							   0,1,0,
							   0,0,1};
	
	private float skewx=0, skewy=0;
	private float rotation_angle = 0;
	private float scalex = 1, scaley = 1;
	private float translatex = 0, translatey = 0;
	
	public Transformations() {
		
	}
	
	/**
	 * Establece la matriz de transformación que se multiplicará por la matriz modelview 
	 * @param tmatrix Matriz de transformación de la forma [a b c d e f]
	 */
	public void setTMatrix(float[] tmatrix) {
		/*
		transform="matrix(a,b,c,d,e,f)" ==> tmatrix = {	a,c,e,
												   		b,d,f,
												   		0,0,1};
		*/
		if (tmatrix.length==6) {
			this.tmatrix[0] = tmatrix[0];
			this.tmatrix[1] = tmatrix[2];
			this.tmatrix[2] = tmatrix[4];
			this.tmatrix[3] = tmatrix[1];
			this.tmatrix[4] = tmatrix[3];
			this.tmatrix[5] = tmatrix[5];
		}
	}
	
	/**
	 * Establece el ángulo de rotación que se aplicará a la figura 
	 * @param rotation_angle Ángulo de rotación
	 */
	public void setRotation(float rotation_angle) {
		this.rotation_angle = rotation_angle;
	}
	
	/**
	 * Establece el escalado que se aplicará a la figura
	 * @param scalex Factor de escala sobre el eje X
	 * @param scaley Factor de escala sobre el eje Y
	 */
	public void setScale(float scalex, float scaley) {
		this.scalex = scalex;
		this.scaley = scaley;
	}
	
	/**
	 * Establece el escalado que se aplicará a la figura
	 * @param scale Factor de escala sobre los ejes X e Y
	 */
	public void setScale(float scale) {
		this.scalex = scale;
		this.scaley = scale;
	}
	
	/**
	 * Establece la trasposición que se le aplicará a la figura
	 * @param translatex Trasposición sobre el eje X
	 * @param translatey Trasposición sobre el eje Y
	 */
	public void setTranslate(float translatex, float translatey) {
		this.translatex = translatex;
		this.translatey = translatey;
	}
	
	/**
	 * Establece la trasposición que se le aplicará a la figura
	 * @param translatex Trasposición sobre el eje X
	 */
	public void setTranslate(float translatex) {
		setTranslate(translatex, 0);
	}
	
	/**
	 * 
	 * @param skew_anglex
	 */
	public void setSkewX(float skew_anglex) {
		this.skewx=skew_anglex;
	}
	/**
	 * 
	 * @param skew_angley
	 */
	public void setSkewY(float skew_angley) {
		this.skewy=skew_angley;
		}
	
	/**
	 * Aplica las transformaciones a la matriz ModelView
	 * @param gl
	 */
	public void applyTransformations(Canvas canvas) {
		float[] curmat = new float[9];
		Matrix m = new Matrix();
		canvas.getMatrix().getValues(curmat);
		
		m.setValues(multiplyAMatrix(curmat, this.tmatrix));
		canvas.setMatrix(m);
		canvas.rotate(this.rotation_angle);
		canvas.skew(this.skewx, this.skewy);
		canvas.scale(this.scalex, this.scaley);
		canvas.translate(this.translatex, this.translatey);
	}
	
	/**
	 * 
	 * @param m1
	 * @param m2
	 * @return
	 */
	private float[] multiplyAMatrix(float[] m1, float[] m2) {
		return matrix2array(multiplyMatrix(array2matrix(m1),array2matrix(m2)));
	}
	
	/**
	 * Multiplica dos matrices
	 * @param m1 Matriz M1, un array bidimensional
	 * @param m2 Matriz M2, un array bidimensional
	 * @return Devuelve un array bidimensional con la matriz resultante de M1xM2
	 */
	private float[][] multiplyMatrix(float[][] m1, float[][] m2) {
		int m1rows = m1.length;
		int m1cols = m1[0].length;
		int m2rows = m2.length;
		int m2cols = m2[0].length;
		if (m1cols != m2rows)
			throw new IllegalArgumentException("matrices don't match: " + m1cols + " != " + m2rows);
		float[][] result = new float[m1rows][m2cols];

		for (int i=0; i<m1rows; i++)
			for (int j=0; j<m2cols; j++)
				for (int k=0; k<m1cols; k++)
					result[i][j] += m1[i][k] * m2[k][j];
		    
		return result;
	}
	
	/**
	 * 
	 * @param m
	 * @return
	 */
	private float[] matrix2array(float[][] m) {
		int mrows = m.length;
		int mcols = m[0].length;
		int k=0;
		float[] result = new float[mrows*mcols];
		for (int i=0; i<mrows; i++)
			for (int j=0; j<mcols; j++) 
				result[k++]=m[i][j];

		return result;
	}
	
	/**
	 * 
	 * @param m
	 * @return
	 */
	private float[][] array2matrix(float[] m) {
		int k=0;
		float[][] result = new float[3][3];
		for (int i=0; i<3; i++)
			for (int j=0; j<3; j++) 
				result[i][j]=m[k++];

		return result;
	}
}
