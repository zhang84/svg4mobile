package com.grub.svg4mobile;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FileExplorer extends ListActivity {
	
    private static final String MEDIA_PATH = new String("/sdcard/");
	private List<String>  elementos = null;
	private File nivelActual = new File(MEDIA_PATH);
	   
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_explorer_list);
        rellenar(new File(MEDIA_PATH).listFiles(new SvgFilter()));
    }
	
	
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        File archivo = new File(elementos.get(position));
        if(position == 0){
            //No está en el raíz (hay padre)
            if(nivelActual.getParent() != null){
            	nivelActual = new File(nivelActual.getParent());
                rellenar(nivelActual.listFiles(new SvgFilter()));
            }
		} else {
            if (archivo.isDirectory()) {
            	nivelActual = archivo;
                rellenar(archivo.listFiles(new SvgFilter()));
            } else {
            	 Bundle bundle = new Bundle(); // Coje la ruta y la pone en el Bundle
	             bundle.putString("filename", archivo.getPath());  
	             
	             Intent intent = new Intent();
	             intent.putExtras(bundle);   	// Pone el Bundle en un Intent
	             setResult(RESULT_OK, intent);  // Devuelve el Intent
	             finish();
             }
        }
    }
    
    public void rellenarConElRaiz() {
        rellenar(new File("/").listFiles(new SvgFilter()));
    } 
    
    private void rellenar(File[] archivos) {
        elementos = new ArrayList<String>();
        elementos.add("..");
        ArrayList<String> dirs = new ArrayList<String>();
        ArrayList<String> files = new ArrayList<String>();
        
        for( File archivo: archivos){
        	
        	if (archivo.isDirectory())
        		dirs.add(archivo.getPath()+"/");
        	else
        		files.add(archivo.getPath());
        }
        Collections.sort(dirs);
        Collections.sort(files);
        
        elementos.addAll(dirs);
        elementos.addAll(files);

        ArrayAdapter<String> listaArchivos= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elementos);
        setListAdapter(listaArchivos);
    }
    
    class SvgFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
        	File theFile = new File(dir, name);
            return (name.endsWith(".svg")||theFile.isDirectory());
        }
    }
    
}