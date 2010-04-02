package com.grub.svg4mobile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Camera;
import android.view.View;

public class Svg4mobileView extends View {
	private static final double ZOOMFACTOR = 10;
	private Camera camera = new Camera();
	private float zoom;
	private float xposcam;
	private float yposcam;
	private float rotcam;
	private static double SMOOTHNESS = 1.0;
	private Parser parser = Parser.getInstance();
	private ExtraInfoParser infoparser = ExtraInfoParser.getInstance();
	private BRect doc = new BRect( 0f,  0f, 100f, 100f, "#FFFF9C", "#FFFFFF", 3f, new Transformations());
	
	private float perspective = 0;
/*	
*/   
	/**
	 * Constructor
	 */
	public Svg4mobileView(Context context){
		super(context);
		
        setFocusable(true);
        setFocusableInTouchMode(true);   
        camReset();
	}
	
	/**
	 * Asigna una ruta al fichero SVG para que sea parseado
	 * @param path Ruta del SVG.
	 */
	public void setPath(String path) {
		parser.parseXML( path );
		doc = new BRect( 0f,  0f, parser.getWidth(), parser.getHeight(), "#FFFF9C", "#FFFFFF", 3f, new Transformations());
		this.camReset();
	}
	
	/**
	 * Asigna una ruta al fichero de información extra para que sea parseado
	 * @param path Debe coincidir con la ruta del svg.
	 */
	public void setInfoPath(String path) {
		infoparser.parseXML( path );
	}
	
	/**
	 * Incrementa la posición de la cámara en el eje Y 
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
		this.zoom=parser.getWidth();
		this.xposcam=0;
		this.yposcam=0;
		this.rotcam=0;
		//this.perspective = 0;

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
	 * Rota la cámara
	 * @param angle ángulo en grados
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
				
		doc.draw(canvas);
		
			
		parser.First();
		
		while(parser.hasNext()){
			Figure f = (Figure)parser.next();
		    f.draw(canvas);
		    }
		
		infoparser.First();
		
		while(infoparser.hasNext()){
			ExtraInfo info = (ExtraInfo)infoparser.next();
		    info.draw(canvas);
	    }

		/*
		mysubPath = new SubPath[3];
		mysubPath[0] = new SubPath('M', iniPoints);
		mysubPath[1] = new SubPath('C', pointsPathC);
		mysubPath[2] = new SubPath('L', pointsPathL);
		*/
		
		//pruebaPath = new myPath(mysubPath, true, "#32cd32", "#000000", 3, new Transformations());

		//pruebatexto.draw(canvas);
		//pruebaPath.draw(canvas);
		//prueba.draw(canvas);
		
				
		camera.restore();
	}
	
	public Boolean extraInfoExist() {
		return (infoparser.getSize()>0);
	}

}
