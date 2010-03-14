/* 
 * svg4mobile - A project to use SVG files in mobile devices
 * Copyright (C) 2010  Daniel Lahoz, Daniel Rivera, Álvaro Tanarro and Luis Torrrico
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.grub.svg4mobile;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.util.Log;

/**
 * 
 * @author Daniel Lahoz
 * @author Daniel Rivera
 * @author Álvaro Tanarro
 * @author Luis Torrrico
 * @version 0.1
 */
public class Svg4mobile extends Activity {
    /**
     * Se ejecuta al iniciar la aplicación.
     */
	private OpenGLRenderer renderer;
	private Menu menu;
	private float xtemp;
	private float prevx;
	private float x_mouse = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	Log.v("svg4mobile", "prueba");
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        					 WindowManager.LayoutParams.FLAG_FULLSCREEN);
 		GLSurfaceView view = new GLSurfaceView(this);
 		this.renderer = new OpenGLRenderer();
   		view.setRenderer(this.renderer);
   		setContentView(view);
    }

    /**
     * Se ejecuta cuando se crea el menu
     * @param menu
     * @return Devuelve true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
       //call the base class to include system menus
       super.onCreateOptionsMenu(menu);
       this.menu = menu;
       MenuItem itemChPersp = this.menu.add(0,1,0,"Perspectiva"); //Cambiar Perspectiva
       itemChPersp.setIcon(android.R.drawable.ic_menu_mapmode);
       
       MenuItem itemZoomOut = this.menu.add(1,2,0,"Zoom -"); //Zoom -
       MenuItem itemZoomIn = this.menu.add(1,3,1,"Zoom +"); //Zoom +
       itemZoomOut.setIcon(android.R.drawable.btn_minus);
       itemZoomIn.setIcon(android.R.drawable.btn_plus);
       
       MenuItem compassSync = this.menu.add(2,4,0,"Nortear"); //Nortear
       MenuItem autoCompassSync = this.menu.add(2,5,1,"Autonortear"); //Autonortear
       compassSync.setIcon(android.R.drawable.ic_menu_rotate);
       autoCompassSync.setIcon(android.R.drawable.ic_menu_compass);
       return true;
    }
   /**
    * Manejador de teclas
    * @param keyCode Código de tecla
    * @param event Evento de tecla
    * @return Devuelve true
    */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
      switch (keyCode) {
        case KeyEvent.KEYCODE_DPAD_CENTER: {
          // Resetea la posición de la cámara
        	this.renderer.camReset();
          break;
        }        
        case KeyEvent.KEYCODE_DPAD_LEFT: {
          // Mueve la cámara hacia la derecha
        	this.renderer.camLeft();
          break;
        }
        case KeyEvent.KEYCODE_DPAD_RIGHT: {
          // Mueve la cámara hacia la izquierda
          this.renderer.camRight();
          break;
        }
        case KeyEvent.KEYCODE_DPAD_UP: {
          // Mueve la cámara hacia abajo
          //this.renderer.zoomOut();
        	this.renderer.camUp();
          break;
        }
        case KeyEvent.KEYCODE_DPAD_DOWN: {
        	// Mueve la cámara hacia arriba
        	//this.renderer.zoomIn();
        	this.renderer.camDown();
        	break;
        }
        case KeyEvent.KEYCODE_Z: {
        	// Aleja la cámara
            this.renderer.zoomOut();
          	
            break;
          }
          case KeyEvent.KEYCODE_X: {
          	// Acerca la cámara
          	this.renderer.zoomIn();
          	break;
          }
        case KeyEvent.KEYCODE_BACK: {
        	// Sale del programa
        	finish();
        }
        default: {
        	return super.onKeyUp(keyCode, event);
        }
      }
      return true;
    }
    
    /**
     * Manejador de los botones del menu
     * @param item Elemento del menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
       if (item.getItemId() == 1) {
    	   
       }
       else if (item.getItemId() == 2) {
    	   this.renderer.zoomOut();
       }
       else if (item.getItemId() == 3) {
    	   this.renderer.zoomIn();
       }
       return true;
    }

    /**
     * Maneja los eventos táctiles.
     * @param event Evento
     * @return Devuelve true si se ha producido algún evento táctil.
     */
    public boolean onTouchEvent(MotionEvent event)
    {
    	/*
            int startX=0;
            int startY=0;
            int endX;
            int endY;
            int resY;
            int resX;
            // float mAngle_x=0;
            // float mAngle_y=0;
            switch (event.getAction())
            {
                   
            case MotionEvent.ACTION_DOWN:
                       
                    startX= (int)event.getX();
                    startY= (int)event.getY();                         
                    return true;

            case MotionEvent.ACTION_MOVE:
                       
                    endX = (int)event.getX();
                    endY= (int)event.getY();
                    resY= endY-startY;
                    resX= endX-startX;
                    
                    /*if((endY-startY)<0)
                    {
                        resY= -(endY-startY);
                    }
                    else
                    {
                        resY= (endY-startY);
                    }
                   
                    if((endX -startX < 0))
                    {
                        resX= -(endX-startX);
                    }
                            else
                    {
                        resX = (endX-startX);
                    } * /
                   
                       
                    if(resX <  0)
                    {
                        this.renderer.camRight();
                    } else {
                    	this.renderer.camLeft();
                    }
                       
                       
                    if(resY<0)
                    {
                    	this.renderer.camUp();
                    } else {
                    	this.renderer.camDown();
                    }
                    Log.v("svg4mobile", "startX: " + startX + ", startY: " + startY);
                    Log.v("svg4mobile", "endX: " + endX + ", endY: " + endY);

                    Log.v("svg4mobile", "resX: " + resX + ", resY: " + resY);
                    //obj.rotateY(mAngle_y); 
                    //obj.rotateZ(mAngle_x);
                    //this.renderer.setY(resY/100);
                    //this.renderer.setX(resX/100);
                    return true;
                                                               
            }
                   
        return false;
        */
    	int action = event.getAction();
    	switch (action)
        {
               
        case MotionEvent.ACTION_DOWN:
        	xtemp = event.getX();
        	x_mouse = xtemp;
            return true;

        case MotionEvent.ACTION_MOVE:
        	prevx = x_mouse;
        	x_mouse = event.getX();
        	if (xtemp < x_mouse && x_mouse > prevx)    	  
      		  this.renderer.camLeft();
      	  	else
      		  this.renderer.camRight();
        	return true;
        }
        	
    	/*if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) 
    	{    	  
    	  float x_mouse = event.getX();
    	  Log.v("svg4mobile", Float.toString(x_mouse));
    	  //float width = getWidth();
    	  int progress = (int) (x_mouse / 10);

    	  if (progress < 0)    	  
    		  this.renderer.camLeft();
    	  else
    		  this.renderer.camRight();

    	  //if (listener != null)    	  
    		//  listener.onProgressChanged(this, progress);
    	}*/

    	return true;
    }

}