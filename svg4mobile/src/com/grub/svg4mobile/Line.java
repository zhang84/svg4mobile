package com.grub.svg4mobile;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Line {
	
	private float line[] = {
			-0.5f, -0.5f, //point A
			0.5f, -0.5f //point B
	};

	private FloatBuffer vertexBuffer;
	
	public Line(){
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(line.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuf.asFloatBuffer();
		vertexBuffer.put(line);
		vertexBuffer.position(0);
	}
	
	public void draw(GL10 gl) {
		
		gl.glColor4f(0.0f,1.0f,0.0f,1.0f); //line color
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glLineWidth(10f);
		//gl.glPointSize(100f);

		gl.glDrawArrays(GL10.GL_LINES, 0, 2);
		//gl.glDrawArrays(GL10.GL_POINTS, 0, 1);
	}
}
