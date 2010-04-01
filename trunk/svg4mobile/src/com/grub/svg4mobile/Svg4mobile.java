package com.grub.svg4mobile;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Daniel Lahoz
 * @author Daniel Rivera
 * @author Álvaro Tanarro
 * @author Luis Torrrico
 * @version 0.1
 */
public class Svg4mobile extends Activity {
	
	private Svg4mobileView view;
	private Menu menu;
	private float xtemp, ytemp;
	private float prevx, prevy;
	private float x_mouse = 0, y_mouse =0;
    private SensorManager sm; 
    private Sensor oriSensor; 
    private List<Sensor> sensors;
    private Boolean isPerspectiveOn = false;
    private Boolean setNorthRequest = false;
    private Boolean AutoSetNorth = false;
	private static final int OPENFILE_ID = 6;

	
    /**
     * Se ejecuta al iniciar la aplicación.
     */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        					 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.view = new Svg4mobileView(this);
		setContentView(view);
		
        // Establece Sensor y Manager 
        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE); 
        sensors = sm.getSensorList(Sensor.TYPE_ORIENTATION); 
        if(sensors.size() > 0) { 
          oriSensor = sensors.get(0); 
        } 
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
       boolean result = super.onCreateOptionsMenu(menu);
       this.menu = menu;
       
       MenuItem openFile = this.menu.add(3,OPENFILE_ID,0,R.string.load); //Abrir fichero
       openFile.setIcon(android.R.drawable.ic_menu_add);
              
       MenuItem itemZoomOut = this.menu.add(1,2,0,R.string.zoom_out); //Zoom -
       MenuItem itemZoomIn = this.menu.add(1,3,1,R.string.zoom_in); //Zoom +
       itemZoomOut.setIcon(android.R.drawable.btn_minus);
       itemZoomIn.setIcon(android.R.drawable.btn_plus);
       
       MenuItem itemChPersp = this.menu.add(0,1,0,R.string.perspective); //Cambiar Perspectiva
       itemChPersp.setIcon(android.R.drawable.ic_menu_mapmode);
       
       MenuItem compassSync = this.menu.add(2,4,0,R.string.set_north); //Nortear
       MenuItem autoCompassSync = this.menu.add(2,5,1,R.string.auto_set_north); //Autonortear
       compassSync.setIcon(android.R.drawable.ic_menu_rotate);
       autoCompassSync.setIcon(android.R.drawable.ic_menu_compass);
       
       return result;
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
        	this.view.camReset();
          break;
        }        
        case KeyEvent.KEYCODE_DPAD_LEFT: {
          // Mueve la cámara hacia la derecha
        	this.view.camRight();
          break;
        }
        case KeyEvent.KEYCODE_DPAD_RIGHT: {
          // Mueve la cámara hacia la izquierda
          this.view.camLeft();
          break;
        }
        case KeyEvent.KEYCODE_DPAD_UP: {
          // Mueve la cámara hacia abajo
          //this.renderer.zoomOut();
        	this.view.camDown();
          break;
        }
        case KeyEvent.KEYCODE_DPAD_DOWN: {
        	// Mueve la cámara hacia arriba
        	//this.renderer.zoomIn();
        	this.view.camUp();
        	break;
        }
        case KeyEvent.KEYCODE_Z: {
        	// Aleja la cámara
            this.view.zoomOut();
          	
            break;
          }
          case KeyEvent.KEYCODE_X: {
          	// Acerca la cámara
          	this.view.zoomIn();
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
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	switch ( item.getItemId() ) {
    		case 1: { //Perspectiva
    			if (this.isPerspectiveOn) {
    				this.view.setPerspective(0f);
    			} else {
    				this.view.setPerspective(64f);		
    			}
    			this.isPerspectiveOn = !this.isPerspectiveOn; 

    			break;
    		}
    		case 2: { //Zoom Out
    			this.view.zoomOut();
    			break;    			
    		}
    		case 3: { //Zoom In
    			this.view.zoomIn();
    			break;    			
    		}
    		case 4: { //Nortear
    			this.setNorthRequest = true;
    			break;    			
    		}
    		case 5: { //Autonortear
    			this.AutoSetNorth = !this.AutoSetNorth;
    			break;    			
    		}
    		case OPENFILE_ID: { // Cargar fichero
    			openFile();
    			break;    			
    		}
    	}
       return true;
    }

    /**
     * 
     */
    private void openFile() {
    	Intent i = new Intent(this, FileExplorer.class);
    	startActivityForResult(i, OPENFILE_ID);		
	}
    
    /**
     * 
     * @param requestCode
     * @param resultCode
     * @param data
     */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null)   // Cancel case
			return;
		switch(requestCode) {
		case OPENFILE_ID:
		    Bundle extras = data.getExtras();
		    String fname = extras.getString("filename");
		    Log.d("svg4mobile", fname);
		    this.view.setPath(fname);
		    this.view.setInfoPath(fname);
		    break;
		}
	}


	/**
     * Maneja los eventos táctiles.
     * @param event Evento
     * @return Devuelve true si se ha producido algún evento táctil.
     */
    @Override
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
        	if (xtemp < x_mouse && x_mouse > prevx)    	  
      		  this.view.camRight();
      	  	else
      		  this.view.camLeft();
        	y_mouse = event.getY();
        	if (ytemp < y_mouse && y_mouse > prevy)    	  
        	  this.view.camDown();
        	else
        	  this.view.camUp();
        	return true;
        case MotionEvent.ACTION_UP:
        	return true;
        }

    	return true;
    }
    
    @Override 
    protected void onResume() { 
     super.onResume(); 
     sm.registerListener(sl, oriSensor, SensorManager.SENSOR_DELAY_GAME);       
    } 
    
    @Override 
    protected void onStop() {      
     sm.unregisterListener(sl); 
     super.onStop();
    }

    private final SensorEventListener sl = new SensorEventListener() {
    	/**
    	 * Se ejecuta cada vez que recibe un cambio en algún sensor
    	 * @param event Evento
    	 */
    	public void onSensorChanged(SensorEvent event) {
    		if (setNorthRequest || AutoSetNorth) {
    			Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay(); 
    			if (display.getOrientation() == 0) { // Vertical
    				view.setNorth(event.values[0]);
    			} else { // Apaisado
    				view.setNorth(event.values[0]+90);
    			}
    			setNorthRequest = false;
    			Log.v("svg4mobile", "Display width: " + display.getWidth() );
    		}
    	}
      
    	@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) { }
    };
}
