package com.grub.svg4mobile;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.util.Vector;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
//import android.util.Log;
import android.util.Log;

public class OpenGLRenderer implements Renderer {
	private float zoom;
	private float xposcam;
	private float yposcam;
	private float x2poscam;
	private float y2poscam;
	private float rotcam;
	private static double SMOOTHNESS = .02;
	private Vector<Object> v = new Vector<Object>();
	private Parser parser = new Parser();
	private float width, height;
	

	
	
	
	
	
	
	
	private BRect doc =    new BRect( 0f,  0f, 200, 200, "#FFFF9C", "#FFFFFF", 3f, new Transformations()); 
	private BRect prueba = new BRect(-1f, -1f,   3,   3, "#0000FF", "#FF0000", 2f, new Transformations()); 
	private Text pruebatexto = new Text(-10, 8, 12, "#00FF00");
	/**
	 * Constructor
	 */
	public OpenGLRenderer() {
		// Initialize our squares.
		
		/*while (parser.hasNext()) {  
			v.add(parser.next());
		}*/
		this.width = parser.getWidth();
		this.height = parser.getHeight();
	}
	
	/**
	 * Incrementa la posición de la cámara en el eje Y 
	 * @param d
	 * @deprecated
	 */
	public void incX(double d) {
		this.xposcam+=d;
		this.x2poscam=this.xposcam;
	}
	/**
	 * Incrementa la posición de la cámara en el eje Y 
	 * @param d
	 * @deprecated
	 */
	public void incY(double d) {
		this.yposcam+=d;
		this.y2poscam=this.yposcam;
	}
	
	/**
	 * Resetea los valores de posición de la cámara.
	 */
	public void camReset() {
		this.zoom=8;
		this.xposcam=0;
		this.yposcam=0;
		this.x2poscam=0;
		this.y2poscam=0;
		this.rotcam=0;
	}
	
	/**
	 * Acerca la cámara
	*/
	public void zoomIn() {
		if (this.zoom > 1) this.zoom--;
	}
	
	/**
	 * Aleja la cámara
	*/
	public void zoomOut() {
		if (this.zoom < 100) this.zoom++;
	
	}
	
	/**
	 * Rota la cámara
	 * @param angle Ángulo en grados
	 */
	public void setNorth(float angle) {
		this.rotcam=angle;
	}
	
	/**
	 * Posiciona la cámara
	 * @param angle Ángulo en el que se moverá la cámara en grados.
	 */
	private void posCam(float angle) {
		angle = (float) Math.toRadians( angle - this.rotcam );
		this.xposcam+=(float) (SMOOTHNESS*this.zoom)*Math.cos(angle); this.x2poscam=this.xposcam;
		this.yposcam+=(float) (SMOOTHNESS*this.zoom)*Math.sin(angle); this.y2poscam=this.yposcam; 
	}
	
	/**
	 * Mueve la cámara a la derecha
	 */
	public void camRight() {
		posCam(0);
	}
	
	/**
	 * Mueve la cámara a la izquierda
	 */
	public void camLeft() {
		posCam(180);
	}
	
	/**
	 * Mueve la cámara hacia arriba
	 */
	public void camUp() {
		posCam(90);
	}
	
	/**
	 * Mueve la cámara hacia abajo
	 */
	public void camDown() {
		posCam(270);
	}
	
	/**
	 * Se ejecuta al crear la surface
	 * @param gl
	 * @param config
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onSurfaceCreated(javax.microedition
	 * .khronos.opengles.GL10, javax.microedition.khronos.egl.EGLConfig)
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glDisable(GL10.GL_DITHER);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		// Pone el fondo de negro ( rgba ).
		gl.glClearColor(.0f, .0f, 0f, 0f);
		// Enable Smooth Shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);
		// Depth buffer setup.
		gl.glClearDepthf(1.0f);
		// Enables depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);
		
        gl.glEnable(GL10.GL_TEXTURE_2D);

		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisable(GL10.GL_TEXTURE_2D);

		this.camReset();
	}

	/**
	 * Se ejecuta cada vez que se redibuja la pantalla.
	 * @param gl
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onDrawFrame(javax.microedition.
	 * khronos.opengles.GL10)
	 */
	public void onDrawFrame(GL10 gl) {
		//Rendimiento
        gl.glDisable(GL10.GL_DITHER);
        gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);

		// Limpia la pantalla y el buffer de profundidad.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		// Reemplaza la matriz actual con la matriz Identidad
		gl.glLoadIdentity();
		
		//Ajusta la cámara
		gl.glRotatef(this.rotcam, 0.f, 0.f, 1.f);
		GLU.gluLookAt(gl, this.xposcam, this.yposcam, this.zoom, this.x2poscam, this.y2poscam, 0, 0, 1, 0);
		//Log.v("svg4mobile", this.xposcam+"/"+this.yposcam+"/"+this.zoom);
		
		
		doc.draw(gl);
		prueba.draw(gl);
		
		
		//pruebatexto.draw(gl);
		/*
		Text pruebatexto2 = new Text(-10, 26, 12, "#00FF00");
		pruebatexto2.draw(gl);*/
			
		Line pruebaline= new Line(4.5f, -4.5f, 0.5f, 0.5f, "#00FF00", 10f);
		pruebaline.draw(gl);
		
		/*for(int i=0; i<v.size(); i++) {
			Object shape = v.elementAt(i);
			//Log.v("svg4mobile", "-"+shape.getClass().getName()+"-");

			if (shape.getClass().getName()=="com.grub.svg4mobile.FlatColoredSquare") {
				FlatColoredSquare fcs = (FlatColoredSquare) shape;
				fcs.draw(gl);	
			}
			if (shape.getClass().getName()=="com.grub.svg4mobile.SmoothColoredSquare") {
				SmoothColoredSquare scs = (SmoothColoredSquare) shape;
				scs.draw(gl);	
			}
			
        }*/
	}

	/**
	 * Se ejecuta cada vez que cambia la surface.
 	 * @param gl
 	 * @param width Ancho del Viewport
 	 * @param height Alto del Viewport
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onSurfaceChanged(javax.microedition
	 * .khronos.opengles.GL10, int, int)
	 */
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// Establece el tamaño del viewport
		gl.glViewport(0, 0, width, height);
		// Selecciona la matriz de proyección
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reinicia la matriz de proyección
		gl.glLoadIdentity();
		// Calcula la relación de aspecto de la ventanta
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
		// Selecciona la matriz del modelview
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// Reinicia la matriz del modelview
		gl.glLoadIdentity();
	}
}
