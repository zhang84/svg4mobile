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
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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
	private DrawView drawView;
	private Menu menu;
	private float xtemp, ytemp;
	private float prevx, prevy;
	private float x_mouse = 0, y_mouse =0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set full screen view
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                         WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //requestWindowFeature(Window.FEATURE_CONTEXT_MENU FEATURE_NO_TITLE);

        drawView = new DrawView(this);
        setContentView(drawView);
        //drawView.requestFocus();
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
        	
          break;
        }        
        case KeyEvent.KEYCODE_DPAD_LEFT: {
          // Mueve la cámara hacia la derecha
        	 break;
        }
        case KeyEvent.KEYCODE_DPAD_RIGHT: {
          // Mueve la cámara hacia la izquierda
          break;
        }
        case KeyEvent.KEYCODE_DPAD_UP: {
          // Mueve la cámara hacia abajo
          //this.renderer.zoomOut();
          break;
        }
        case KeyEvent.KEYCODE_DPAD_DOWN: {
        	// Mueve la cámara hacia arriba
        	//this.renderer.zoomIn();
        	break;
        }
        case KeyEvent.KEYCODE_Z: {
        	// Aleja la cámara
          	
            break;
          }
          case KeyEvent.KEYCODE_X: {
          	// Acerca la cámara
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
       }
       else if (item.getItemId() == 3) {
       }
       return true;
    }

    /**
     * Maneja los eventos táctiles.
     * @param event Evento
     * @return Devuelve true si se ha producido algún evento táctil.
     */
    public boolean onTouchEvent(MotionEvent event) {
    	int action = event.getAction();
    	switch (action) {
               
        case MotionEvent.ACTION_DOWN:
        	xtemp = event.getX();
        	ytemp = event.getY();
        	x_mouse = xtemp;
        	y_mouse = ytemp;
            return true;

        case MotionEvent.ACTION_MOVE:
        	prevx = x_mouse;
        	prevy = y_mouse;
        	
        	x_mouse = event.getX();
        	return true;
        }

    	return true;
    }
}