package com.grub.svg4mobile;

import javax.microedition.khronos.opengles.GL10;

public class BRect extends Figure {
	/**
	 * Crea un rectángulo con borde utilizando las clases Rect y Line 
	 */
	private Rect shape;
	private Boolean hasWidth = false;
	private Line borderTop;
	private Line borderDown;
	private Line borderLeft;
	private Line borderRight;
	/*
	transform="matrix(0.69743725,0.71664586,-0.71664586,0.69743725,0,0)"
	private float[] tmatrix = {0.69743725f,	-0.71664586f,	0,0,
							   0.71664586f,	0.69743725f, 	0,0,
							   0,			0,				0,0,
							   0,			0,				0,1};
	es decir
	transform="matrix(a,b,c,d,e,f)" tmatrix = {a,c,e,0,
											   b,d,f,0,
											   0,0,0,0,
											   0,0,0,1};
	*/
	private float[] tmatrix = {1,0,0,0,
							   0,1,0,0,
							   0,0,1,0,
							   0,0,0,1};
	private float rotation_angle = 0;
	private float scalex = 1, scaley = 1;
	private float translatex = 0, translatey = 0;
	
	/**
	 * Crea un rectángulo con borde
	 * @param x Coordenada x
	 * @param y Coordenada y
	 * @param w Ancho
	 * @param h Alto
	 * @param rgb Código de color hexadecimal de la forma #FFFFFF
	 * @param brgb Código de color hexadecimal de la forma #FFFFFF para el borde
	 * @param bwidth Grosor del borde del rectángulo. Debe ser 0 para omitir el borde.
	 */
	public BRect(float x, float y, float w, float h, String rgb, String brgb, float bwidth) {
		// TODO: cargar parámetros transform
		
		this.shape = new Rect(x,y,w,h,rgb);
		this.hasWidth = ( bwidth > 0);
		if (this.hasWidth) {
			this.borderTop = new Line( x, y+h, x+w, y+h, brgb, bwidth );
			this.borderDown = new Line( x, y, x+w, y, brgb, bwidth );
			this.borderLeft = new Line( x, y+h, x, y, brgb, bwidth );
			this.borderRight = new Line( x+w, y, x+w, y+h, brgb, bwidth );
		}
	}
	/**
	 * Pinta la figura
	 * @param gl
	 */
	public void draw(GL10 gl) {
		//Se aplican las transformaciones a la figura
		gl.glPushMatrix();
		gl.glMultMatrixf(tmatrix, 0);
		gl.glRotatef(this.rotation_angle, 0, 0, 1.0f);
		gl.glScalef(this.scalex, this.scaley, 1.0f);
		gl.glTranslatef(this.translatex, this.translatey, 0);
		
		this.shape.draw(gl);
		if (this.hasWidth) {
			this.borderTop.draw(gl);
			this.borderDown.draw(gl);
			this.borderLeft.draw(gl);
			this.borderRight.draw(gl);
		}
		gl.glPopMatrix();
	}
	
}
