package se.jayway.opengl.tutorial;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
/**
* @author Luis Torrrico
* @author Daniel Rivera
* @author Álvaro Tanarro
* @author Daniel Lahoz
* @version 0.1
*/
public class Svg4mobile extends Activity {
    /**
     * Called when the activity is first created.
     */
	private OpenGLRenderer renderer;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        					 WindowManager.LayoutParams.FLAG_FULLSCREEN);
 		GLSurfaceView view = new GLSurfaceView(this);
 		this.renderer = new OpenGLRenderer();
   		view.setRenderer(this.renderer);
   		setContentView(view);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
      //CharSequence dialogString;
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
        case KeyEvent.KEYCODE_BACK: {
        	// Sale del programa
        	finish();
        }
      }
      return true;
    }

}