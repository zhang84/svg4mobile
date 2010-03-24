package com.grub.svg4mobile;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Camera;
import android.view.View;

public class Svg4mobileView extends View{
	private Camera camera = new Camera();
	private float zoom;
	private float xposcam, x2poscam;
	private float yposcam, y2poscam;
	private float rotcam;
	private static double SMOOTHNESS = 100.0;
	private Vector<Object> v = new Vector<Object>();
	private Parser parser = new Parser();
	private float width, height;
	

	
	
	
	
	
	
	
	//private BRect doc =    new BRect( 0f,  0f, 200, 200, "#FFFF9C", "#FFFFFF", 3f, new Transformations()); 
	//private BRect prueba = new BRect(-1f, -1f,   3,   3, "#0000FF", "#FF0000", 2f, new Transformations()); 
	//private Text pruebatexto = new Text(-10, 8, 12, "#00FF00");

	private Line myLine;
    private Rect myRect;
    
	/**
	 * Constructor
	 */
	public Svg4mobileView(Context context){
		super(context);
		
        setFocusable(true);
        setFocusableInTouchMode(true);
        
        myLine = new Line(10, 20, 30, 40, Color.WHITE);
        
        myRect = new Rect(100, 100, 200, 200, Color.CYAN);
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
		camera.applyToCanvas(canvas);
		
		
		// custom drawing code here
		// remember: y increases from top to bottom
		// x increases from left to right
		/*int x = 0;
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
		canvas.rotate(-45, x + rect.exactCenterX(),
                                           y + rect.exactCenterY());
		// draw the rotated text
		paint.setStyle(Paint.Style.FILL);
		canvas.drawText(str2rotate, x, y, paint);

		//undo the rotate
		canvas.restore();
		canvas.drawText("After canvas.restore()", 50, 250, paint);

		// draw a thick dashed line
		DashPathEffect dashPath =
                        new DashPathEffect(new float[]{20,5}, 1);
		paint.setPathEffect(dashPath);
		paint.setStrokeWidth(8);
		canvas.drawLine(0, 300 , 320, 300, paint);
		
		*/
		
		
		
		
    	myLine.Draw(canvas);
    	myRect.Draw(canvas);
		

	}

}
