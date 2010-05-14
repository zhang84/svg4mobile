package com.grub.svg4mobile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

/**
 * Figura de tipo información extra.
 */
public class ExtraInfo {

	private String title;
	private Paint paint=new Paint();
	private Paint paint2=new Paint();
	private RectF drawable_area;  
	private Bitmap cached_dummy;
	private float x;
	private float y;
	private float w;
	private float h;
	private String rgb;
	private String notes;
	private String[] tags;
	private String description;
	private String image;
	
	/**
	 * Pinta círculos con imágenes que al ser pulsados muestran información adicional
	 * @param x Posición X
	 * @param y Posición Y
	 * @param w Ancho
	 * @param h Alto
	 * @param title Título
	 * @param description Descripción
	 * @param image Imagen que se mostrará, será redimensionada a w * h
	 * @param notes Notas
	 * @param rgb Color del círculo
	 * @param tags Etiquetas
	 */
	public ExtraInfo(float x, float y, float w, float h, String title, String description, String image, String notes, String rgb, String[] tags){
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.rgb = rgb;
		this.title = title;
		this.description = description;
		this.notes = notes;
		this.tags = tags;
		this.image = image;
		
		try {
			cached_dummy = BitmapFactory.decodeFile(image);
		} catch (Exception e) {
			Log.e("svg4mobile", ""+e);
		}
		drawable_area = new RectF(0,0,w,h);
	}
	/**
	 * Pinta la figura
	 * @param gl
	 */
	public void draw(Canvas canvas) {
		//Se aplican las transformaciones a la figura		
		float radio = (float) (Math.sqrt(w*w+h*h))/2;
				
		canvas.save();
		canvas.translate(this.x, this.y);
		this.paint2.setColor(Color.parseColor(rgb));
		this.paint2.setStyle(Paint.Style.FILL);
		this.paint2.setAntiAlias(true);		
		canvas.drawCircle(this.w/2, this.h/2, radio, this.paint2);
		
		this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(Color.TRANSPARENT); 
        canvas.drawPaint(paint);  
        this.paint.setColor(Color.WHITE);  
		canvas.drawBitmap(cached_dummy,null,drawable_area,this.paint);
		canvas.restore();	
	}
	
	/**
	 * Obtiene la coordenada x de la posición.
	 * @return Devuelve la coordenada x de la posición.
	 */
	public float getX() {
		return this.x;
	}
	
	/**
	 * Obtiene la coordenada y de la posición.
	 * @return Devuelve la coordenada y de la posición.
	 */
	public float getY() {
		return this.y;
	}
	
	/**
	 * Obtiene el ancho del elemento.
	 * @return Devuelve el ancho del elemento.
	 */
	public float getWidht() {
		return this.w;
	}
	
	/**
	 * Obtiene el alto del elemento.
	 * @return Devuelve el alto del elemento.
	 */
	public float getHeight() {
		return this.h;
	}
	
	/**
	 * Obtiene el título del elemento.
	 * @return Devuelve el título del elemento.
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Obtiene la descripión del elemento.
	 * @return Devuelve la descripción del elemento.
	 */
	public String getDesc() {
		return this.description;
	}
	
	/**
	 * Obtiene las anotaciones del elemento.
	 * @return Devuelve las anotaciones del elemento.
	 */
	public String getNotes() {
		return this.notes;
	}
	
	/**
	 * Obtiene las etiquetas del elemento.
	 * @return Devuelve las etiquetas del elemento.
	 */
	public String[] getTags() {
		return this.tags;
	}
	
	/**
	 * Obtiene la ruta del elemento.
	 * @return Devuelve la ruta del elemento.
	 */
	public String getImagePath() {
		return this.image;
	}
	
	/**
	 * Obtiene el color del elemento.
	 * @return Devuelve el color del elemento.
	 */
	public String getColor() {
		return "#FF0000";
	}
}
