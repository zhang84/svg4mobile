package com.grub.svg4mobile;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.graphics.Color;
import android.util.Log;

import javax.microedition.khronos.opengles.GL10;

public class Rect {
	private float[] vertices;
	
	// Orden de conexión entre vértices
	private short[] indices = { 
			0, 1, 2, 0, 2, 3};
	private float x,y;
	// Our vertex buffer.
	private FloatBuffer vertexBuffer;

	// Our index buffer.
	private ShortBuffer indexBuffer;

	private String colorString;
	
	public Rect(float x, float y, float w, float h, String rgb) {
		// 0, Arriba izqda
		this.vertices[0] = 0; //x
		this.vertices[1] = h; //y
		this.vertices[2] = 0; //z
		// 1, Abajo izqda
		this.vertices[3] = 0; //x
		this.vertices[4] = 0; //y
		this.vertices[5] = 0; //z
		// 2, Abajo dcha
		this.vertices[6] = w; //x
		this.vertices[7] = 0; //y
		this.vertices[8] = 0; //z
		// 3, Arriba dcha
		this.vertices[ 9] = w; //x
		this.vertices[10] = h; //y
		this.vertices[11] = 0; //z
		
		this.x = x;
		this.y = y;
		this.colorString = rgb;
		// un float son 4 bytes, por tanto se multiplica el número de 
		// vertices por 4.
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		
		// un short son 2 bytes, por tanto se multiplica el número de 
		// vertices por 2.
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indexBuffer = ibb.asShortBuffer();
		indexBuffer.put(indices);
		indexBuffer.position(0);
	}

	/**
	 * This function draws our square on screen.
	 * @param gl
	 */
	public void draw(GL10 gl) {
		// Posicionamos el rect figura.
		Log.v("svg4mobile", "draw");
		gl.glTranslatef(this.x, this.y, 0);

		//Asignamos color
		//int c = Color.parseColor(this.colorString);
		//gl.glColor4f(Color.red(c), Color.green(c), Color.blue(c), 1.0f);
		gl.glColor4f(0,0,0, 1.0f);
		// Counter-clockwise winding.
		gl.glFrontFace(GL10.GL_CCW);
		// Activa face culling.
		gl.glEnable(GL10.GL_CULL_FACE);
		// Que caras eliminará el face culling.
		gl.glCullFace(GL10.GL_BACK);
		
		// Activa el buffer de vértices para escritura y para ser usado 
		// durante el renderizado.
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// Especifica el lugar y el formato de los datos de un  array de 
		// coordenadas de vertices para usarlo cuando renderice.
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, 
				GL10.GL_UNSIGNED_SHORT, indexBuffer);
		
		// Desactiva el buffer de vertices.
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		// Desactiva face culling.
		gl.glDisable(GL10.GL_CULL_FACE);
		
		//Volvemos a la posicion inicial
		gl.glTranslatef(-this.x, -this.y, 0);
	}
}
