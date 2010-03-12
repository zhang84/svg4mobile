package com.grub.svg4mobile;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.util.Vector;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
//import android.util.Log;


//import android.view.SurfaceView;

public class OpenGLRenderer implements Renderer {
	//private FlatColoredSquare flatSquare;
	//private SmoothColoredSquare smoothSquare;
	private float zoom;
	private float xposcam;
	private float yposcam;
	private float x2poscam;
	private float y2poscam;
	//private static double SMOOTHNESS = .0001;
	private Vector<Object> v = new Vector<Object>();
	private Parser parser = new Parser();
	private float width, height;
	
	/**
	 * Constructor
	 */
	public OpenGLRenderer() {
		// Initialize our squares.
		
		while (parser.hasNext()) {  
			v.add(parser.next());
		}
		this.width = parser.getWidth();
		this.height = parser.getHeight();
		//flatSquare = new FlatColoredSquare();
		//smoothSquare = new SmoothColoredSquare();
	}
	
	public void incX(double d) {
		this.xposcam+=d;
		this.x2poscam=this.xposcam;
	}
	
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
	public void zoomOut() { this.zoom++; }
	
	/*
	 * Mueve la cámara a la izquierda suavemente
	 */
	public void camRight() {
		/*double i;
		for (i=0; i<=1; i+=SMOOTHNESS) {
			this.xposcam+=SMOOTHNESS; this.x2poscam=this.xposcam; }
		*/
		if (this.xposcam < this.width) { this.xposcam++; this.x2poscam=this.xposcam; }
	}
	
	/*
	 * Mueve la cámara a la derecha
	 */
	public void camLeft() { 
		if (this.xposcam > 0) { this.xposcam--; this.x2poscam=this.xposcam; }	 
	}
	
	/*
	 * Mueve la cámara hacia abajo
	 */
	public void camUp() { 
		if (this.yposcam < this.height) { this.yposcam++; this.y2poscam=this.yposcam; } 
	}
	
	/*
	 * Mueve la cámara hacia arriba
	 */
	public void camDown() {
		if (this.yposcam > 0) { this.yposcam--; this.y2poscam=this.yposcam; }
		}
	
	/*
	 * Se ejecuta al crear la surface
	 * 
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
		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		this.camReset();
	}

	/*
	 * Se ejecuta cada vez que se redibuja la pantalla.
	 * 
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onDrawFrame(javax.microedition.
	 * khronos.opengles.GL10)
	 */
	public void onDrawFrame(GL10 gl) {
		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// Replace the current matrix with the identity matrix
		gl.glLoadIdentity();
		
		//Ajusta la cámara
		GLU.gluLookAt(gl, this.xposcam, this.yposcam, this.zoom, this.x2poscam, this.y2poscam, 0, 0, 1, 0);
		//Log.v("svg4mobile", "///");

		BRect doc = new BRect(0f, 0f, this.width, this.height, "#FFFF9C", "#FFFFFF", 3f); 
		doc.draw(gl);
		
		BRect prueba = new BRect(-1f, -1f, 3, 3, "#0000FF", "#FF0000", 2f); 
		prueba.draw(gl);
		
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
		// Draw our flat square.
		//flatSquare.draw(gl);
		
		// Draw our smooth square.
		//smoothSquare.draw(gl);
	}

	/*
	 * Se ejecuta cada vez que cambia la surface
	 * 
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onSurfaceChanged(javax.microedition
	 * .khronos.opengles.GL10, int, int)
	 */
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height);
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix
		gl.glLoadIdentity();
		// Calculate the aspect ratio of the window
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
				100.0f);
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// Reset the modelview matrix
		gl.glLoadIdentity();
	}
}
