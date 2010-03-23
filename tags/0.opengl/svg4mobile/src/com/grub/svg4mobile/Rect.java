package com.grub.svg4mobile;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.graphics.Color;
import javax.microedition.khronos.opengles.GL10;

public class Rect extends Figure{
	private float[] vertices = {0,1, 0,0,  1,0,  1,1};
	
	// Orden de conexión entre vértices
	private short[] indices = { 
			0, 1, 2, 0, 2, 3};

	
	// Buffer de vértices.
	private FloatBuffer vertexBuffer;

	// Buffer de índices.
	private ShortBuffer indexBuffer;

	private String colorString;
	
	/**
	 * Crea un rectángulo
	 * @param x Coordenada x
	 * @param y Coordenada y
	 * @param w Ancho
	 * @param h Alto
	 * @param rgb Código de color hexadecimal de la forma #FFFFFF
	 */
	public Rect(float x, float y, float w, float h, String rgb) {
		
		// 0, Arriba izqda
		this.vertices[0] = x; 	//x
		this.vertices[1] = y+h; //y
		// 1, Abajo izqda
		this.vertices[2] = x; //x
		this.vertices[3] = y; //y
		// 2, Abajo dcha
		this.vertices[4] = x+w; //x
		this.vertices[5] = y; 	//y
		// 3, Arriba dcha
		this.vertices[6] = x+w; //x
		this.vertices[7] = y+h; //y
		
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
	 * Pinta la figura
	 * @param gl
	 */
	public void draw(GL10 gl) {
		
		//Asignamos color
		int c = Color.parseColor(this.colorString);
		gl.glColor4f(Color.red(c)/255, Color.green(c)/255, Color.blue(c)/255, 1.0f);
		//Log.v("svg4mobile", "c:"+c+" red: "+Color.red(c)+" green: "+Color.green(c)+" blue: "+Color.blue(c));
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
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
		
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, 
				GL10.GL_UNSIGNED_SHORT, indexBuffer);
		
		// Desactiva el buffer de vertices.
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		// Desactiva face culling.
		gl.glDisable(GL10.GL_CULL_FACE);
	}
}
