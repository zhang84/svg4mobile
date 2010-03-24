package com.grub.svg4mobile;

import android.graphics.Canvas;
import android.graphics.Matrix;

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
	public Canvas applyTransformations(Canvas canvas) {
		//Matrix m = canvas.getMatrix();
		//m.setValues(this.tmatrix);
		//canvas.setMatrix(m);
		canvas.rotate(this.rotation_angle);
		canvas.skew(this.skewx, this.skewy);
		canvas.scale(this.scalex, this.scaley);
		canvas.translate(this.translatex, this.translatey);
		return canvas;
	}
}
