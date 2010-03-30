package com.grub.svg4mobile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Camera;
import android.util.Log;
import android.view.View;

public class Svg4mobileView extends View {
	private static final double ZOOMFACTOR = 10;
	private Camera camera = new Camera();
	private float zoom;
	private float xposcam;
	private float yposcam;
	private float rotcam;
	private static double SMOOTHNESS = 1.0;
	//private ArrayList<Figure> figures = new ArrayList<Figure>();
	public Vector<Figure> figures;
	private Parser parser = Parser.getInstance();
	private float width, height;

	
	private BRect doc =    new BRect( 0f,  0f, 735.03961f, 720.34869f, "#FFFF9C", "#FFFFFF", 3f, new Transformations()); 
	private BRect prueba = new BRect(0f, 0f,   23,   23, "#0000FF", "#FF0000", 2f, new Transformations()); 
	private Text pruebatexto = new Text(100, 8, 22, "Freedom!!!", "#FF0000", new Transformations());
	private float perspective = 0;
	
	private float[] pointsPath = {100,10,
								40,180, 
								190,60, 
								10,60, 
								160,180 };
	private myPath pruebaPath;
	
	/**
	 * Constructor
	 */
	public Svg4mobileView(Context context){
		super(context);
		
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setPath("/sdcard/test.svg");
        
        camReset();
	}
	
	public void setPath(String path) {
		parser.parseXML( path );
		
		/*while (parser.hasNext()) {  
			figures.add(parser.next());
		}*/
	}
	
	/**
	 * Incrementa la posición de la camara en el eje Y 
	 * @param d
	 * @deprecated
	 */
	public void incX(double d) {
		this.xposcam+=d;
	}
	/**
	 * Incrementa la posición de la cámara en el eje Y 
	 * @param d
	 * @deprecated
	 */
	public void incY(double d) {
		this.yposcam+=d;
	}
	
	/**
	 * Resetea los valores de posición de la cámara.
	 */
	public void camReset() {
		this.zoom=100;
		this.xposcam=0;
		this.yposcam=0;
		this.rotcam=0;
		this.perspective = 0;
		this.invalidate();
	}
	
	/**
	 * Acerca la cámara
	*/
	public void zoomIn() {
		if (this.zoom > 10) this.zoom-=ZOOMFACTOR;
		this.invalidate();
	}
	
	/**
	 * Aleja la cámara
	*/
	public void zoomOut() {
		this.zoom+=ZOOMFACTOR;
		this.invalidate();
	
	}
	
	/**
	 * Rota la camara
	 * @param angle angulo en grados
	 */
	public void setNorth(float angle) {
		this.rotcam=angle;
		this.invalidate();
	}
	
	public void setPerspective(float perspective) {
		this.perspective = perspective;
		this.invalidate();
	}
	
	/**
	 * Mueve la cámara a la derecha
	 */
	public void camRight() {
		this.xposcam+=(float) (SMOOTHNESS*this.zoom/ZOOMFACTOR);
		this.invalidate();
	}
	
	/**
	 * Mueve la cámara a la izquierda
	 */
	public void camLeft() {
		this.xposcam-=(float) (SMOOTHNESS*this.zoom/ZOOMFACTOR);
		this.invalidate();
	}
	
	/**
	 * Mueve la cámara hacia arriba
	 */
	public void camUp() {
		this.yposcam+=(float) (SMOOTHNESS*this.zoom/ZOOMFACTOR);
		this.invalidate();
	}
	
	/**
	 * Mueve la cámara hacia abajo
	 */
	public void camDown() {
		this.yposcam-=(float) (SMOOTHNESS*this.zoom/ZOOMFACTOR);	
		this.invalidate();
	}
	
	/**
	 * Se ejecuta cada vez que se redibuja la pantalla.
	 * @param canvas
	 * @see
	 * android.view.View#onDraw
	 */
	
	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		//Ajusta la cámara
		camera.save();
		camera.translate(this.xposcam, this.yposcam, this.zoom);
		camera.rotateZ(this.rotcam);
		camera.rotateX(this.perspective);
		camera.applyToCanvas(canvas);
		
		//pruebaPath = new myPath('M', 100, 10, 'L', pointsPath, true, "#0000ff", 10);
		
		doc.draw(canvas);
		//prueba.draw(canvas);
		//pruebatexto.draw(canvas);
		//pruebaPath.draw(canvas);
		
		while(parser.hasNext()){
			Figure f = (Figure)parser.next();
		    Log.d("svg4mobile", "figure " + f.toString());
		    f.draw(canvas);
		    }
		
		parser.First();
		
		Log.d("svg4mobile", "onDraw ");

		
		camera.restore();
	}

}
