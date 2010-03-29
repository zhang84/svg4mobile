package com.grub.svg4mobile;

import java.util.ArrayList;
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
	private ArrayList<Figure> figures = new ArrayList<Figure>();
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
		parser.parseXML("/sdcard/test.xml");
        //parser.parseXML("http://svg4mobile.googlecode.com/svn/trunk/svg4mobile/res/drawable/svg4mobile.svg");
        
        
        camReset();
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
		
		// custom drawing code here
		// remember: y increases from top to bottom
		// x increases from left to right
		/*
		int x = 0;
		int y = 0;
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);

		// make the entire canvas white
		paint.setColor(Color.WHITE);
		canvas.drawPaint(paint);
		// another way to do this is to use:
		// canvas.drawColor(Color.WHITE);

		// draw a solid blue circle
		paint.setColor(Color.BLUE);
		canvas.drawCircle(20, 20, 15, paint);

		// draw blue circle with antialiasing turned on
		paint.setAntiAlias(true);
		paint.setColor(Color.BLUE);
		canvas.drawCircle(60, 20, 15, paint);

		// compare the above circles once drawn
		// the fist circle has a jagged perimeter
		// the second circle has a smooth perimeter

		// draw a solid red rectangle
		paint.setAntiAlias(false);
		paint.setColor(Color.RED);

		// create and draw triangles
		// use a Path object to store the 3 line segments
		// use .offset to draw in many locations
		// note: this triangle is not centered at 0,0
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2);
		paint.setColor(Color.RED);
		Path path = new Path();
		path.moveTo(0, -10);
		path.lineTo(5, 0);
		path.lineTo(-5, 0);
		path.close();
		path.offset(10, 40);
		canvas.drawPath(path, paint);
		path.offset(50, 100);
		canvas.drawPath(path, paint);
		// offset is cumlative
		// next draw displaces 50,100 from previous
		path.offset(50, 100);
		canvas.drawPath(path, paint);

		// draw some text using STROKE style
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(1);
		paint.setColor(Color.MAGENTA);
		paint.setTextSize(30);
		canvas.drawText("Style.STROKE", 75, 75, paint);

		// draw some text using FILL style
		paint.setStyle(Paint.Style.FILL);
		//turn antialiasing on
		paint.setAntiAlias(true);
		paint.setTextSize(30);
		canvas.drawText("Style.FILL", 75, 110, paint);

		// draw some rotated text
		// get text width and height
		// set desired drawing location
		x = 75;
		y = 185;
		paint.setColor(Color.GRAY);
		paint.setTextSize(25);
		String str2rotate = "Rotated!";

		// draw bounding rect before rotating text
		Rect rect = new Rect();
		paint.getTextBounds(str2rotate, 0, str2rotate.length(), rect);
		canvas.translate(x, y);
		paint.setStyle(Paint.Style.FILL);
		// draw unrotated text
		canvas.drawText("!Rotated", 0, 0, paint);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(rect, paint);
		// undo the translate
		canvas.translate(-x, -y);

		// rotate the canvas on center of the text to draw
		canvas.rotate(-45, x + rect.exactCenterX(), y + rect.exactCenterY());
		// draw the rotated text
		paint.setStyle(Paint.Style.FILL);
		canvas.drawText(str2rotate, x, y, paint);

		//undo the rotate
		canvas.restore();
		canvas.drawText("After canvas.restore()", 50, 250, paint);

		// draw a thick dashed line
		DashPathEffect dashPath = new DashPathEffect(new float[]{20,5}, 1);
		paint.setPathEffect(dashPath);
		paint.setStrokeWidth(8);
		canvas.drawLine(0, 300 , 320, 300, paint);
		*/
		
		pruebaPath = new myPath('M', 100, 10, 'L', pointsPath, true, "#0000ff", 10);
		
		doc.draw(canvas);
		prueba.draw(canvas);
		pruebatexto.draw(canvas);
		pruebaPath.draw(canvas);
		camera.restore();
	}

}
