package com.grub.svg4mobile;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
    
    private static final int PERSPECTIVE_ID = 1;
    private static final int ZOOMIN_ID = 3;
    private static final int ZOOMOUT_ID = 2;
    private static final int SETNORTH_ID = 4;
	private static final int AUTOSETNORTH_ID = 5;
	private static final int OPENFILE_ID = 6;
	private static final int EXTRAINFO_ID = 7;

	private static final int ZOOMGROUP_ID = 1;
	private static final int NORTHGROUP_ID = 2;
	private static final int PERSPGROUP_ID = 4;
	private static final int INFOGROUP_ID = 0;
	private static final int LOADGROUP_ID = 3;
	
	
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
		this.view.setPath("/sdcard/test.svg");
		this.view.setInfoPath("/sdcard/test.svg");
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
       
       MenuItem openFile = this.menu.add(LOADGROUP_ID,OPENFILE_ID,0,R.string.load); //Abrir fichero
       openFile.setIcon(android.R.drawable.ic_menu_add);
              
       MenuItem itemZoomOut = this.menu.add(ZOOMGROUP_ID,ZOOMOUT_ID,0,R.string.zoom_out); //Zoom -
       MenuItem itemZoomIn = this.menu.add(ZOOMGROUP_ID,ZOOMIN_ID,1,R.string.zoom_in); //Zoom +
       itemZoomOut.setIcon(android.R.drawable.btn_minus);
       itemZoomIn.setIcon(android.R.drawable.btn_plus);
       
       MenuItem itemChPersp = this.menu.add(PERSPGROUP_ID,PERSPECTIVE_ID,0,R.string.perspective); //Cambiar Perspectiva
       itemChPersp.setIcon(android.R.drawable.ic_menu_mapmode);
       MenuItem itemExtraInfo = this.menu.add(INFOGROUP_ID,EXTRAINFO_ID,0,R.string.extra_info); //Mostrar información extra
       itemExtraInfo.setIcon(android.R.drawable.btn_star_big_on);
       
       MenuItem compassSync = this.menu.add(NORTHGROUP_ID,SETNORTH_ID,0,R.string.set_north); //Nortear
       MenuItem autoCompassSync = this.menu.add(NORTHGROUP_ID,AUTOSETNORTH_ID,1,R.string.auto_set_north); //Autonortear
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
        	this.view.camDown();
          break;
        }
        case KeyEvent.KEYCODE_DPAD_DOWN: {
        	// Mueve la cámara hacia arriba
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
    		case PERSPECTIVE_ID: { //Perspectiva
    			if (this.isPerspectiveOn) {
    				this.view.setPerspective(0f);
    			} else {
    				this.view.setNorth(0);
    				this.view.setPerspective(64f);
    			}
    			this.isPerspectiveOn = !this.isPerspectiveOn; 
    			this.menu.setGroupVisible(NORTHGROUP_ID, !this.isPerspectiveOn);
    			break;
    		}
    		case ZOOMOUT_ID: { //Zoom Out
    			this.view.zoomOut();
    			break;    			
    		}
    		case ZOOMIN_ID: { //Zoom In
    			this.view.zoomIn();
    			break;    			
    		}
    		case SETNORTH_ID: { //Nortear
    			this.setNorthRequest = true;
    			break;    			
    		}
    		case AUTOSETNORTH_ID: { //Autonortear
    			this.AutoSetNorth = !this.AutoSetNorth;
    			break;    			
    		}
    		case OPENFILE_ID: { // Cargar fichero
    			openFile();
    			
    			break;    			
    		}
    		case EXTRAINFO_ID: { //ExtraInfo
    			showExtraInfo();
    			break;
    		}
    	}
       return true;
    }

    /**
     * Llama a la activity FileExplorer
     */
    private void openFile() {
    	Intent i = new Intent(this, FileExplorer.class);
    	startActivityForResult(i, OPENFILE_ID);
	}
    
    /**
     * Llama a la activity ExtraInfoList
     */
    private void showExtraInfo() {
    	Intent i = new Intent(this, ExtraInfoList.class);
    	Log.v("svg4mobile", "show extra info");
    	startActivity(i);
	}
    
    /**
     * Captura las respuestas de las subactivity
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
		    this.menu.setGroupVisible(INFOGROUP_ID, this.view.extraInfoExist());
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
