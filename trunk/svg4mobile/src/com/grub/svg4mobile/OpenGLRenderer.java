package com.grub.svg4mobile;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

public class OpenGLRenderer implements Renderer {
	private FlatColoredSquare flatSquare;
	private SmoothColoredSquare smoothSquare;
	private float zoom;
	private float xposcam;
	private float yposcam;
	private float x2poscam;
	private float y2poscam;
	private static double SMOOTHNESS = .0001;
	/**
	 * Constructor
	 */
	public OpenGLRenderer() {
		// Initialize our squares.
		flatSquare = new FlatColoredSquare();
		smoothSquare = new SmoothColoredSquare();
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
	public void zoomIn() { this.zoom++; }
	
	/**
	 * Aleja la cámara
	*/
	public void zoomOut() { this.zoom--; }
	
	/*
	 * Mueve la cámara a la izquierda suavemente
	 */
	public void camRight() {
		double i;
		for (i=0; i<=1; i+=SMOOTHNESS) {
			this.xposcam+=SMOOTHNESS; this.x2poscam=this.xposcam; } 
		}
	
	/*
	 * Mueve la cámara a la derecha
	 */
	public void camLeft() { this.xposcam--; this.x2poscam=this.xposcam; }
	
	/*
	 * Mueve la cámara hacia abajo
	 */
	public void camUp() { this.yposcam++; this.y2poscam=this.yposcam; }
	
	/*
	 * Mueve la cámara hacia arriba
	 */
	public void camDown() { this.yposcam--; this.y2poscam=this.yposcam; }
	
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
		// Pone el fondo de amarillo ( rgba ).
		gl.glClearColor(1.0f, 1.0f, 0.6f, 0.5f);
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
		// Translates 7 units into the screen and 1.5 units up.
		gl.glTranslatef(0, 1.5f, -7);
		// Draw our flat square.
		flatSquare.draw(gl);
		// Translate to end up under the flat square.
		gl.glTranslatef(0, -3f, 0);
		// Draw our smooth square.
		smoothSquare.draw(gl);
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
