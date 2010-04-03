package com.grub.svg4mobile;

import java.io.File;
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
	
	   
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_explorer_list);
        rellenarConElRaiz();
    }
	
	private List<String>  elementos = null;
	
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        int IDFilaSeleccionada = position;
        File archivo = new File(elementos.get(IDFilaSeleccionada));
        if (IDFilaSeleccionada==0){
            rellenarConElRaiz();
        } else {
            if (archivo.isDirectory())
                rellenar(archivo.listFiles());
             else {
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
        rellenar(new File("/").listFiles());
    } 
    
    private void rellenar(File[] archivos) {
        elementos = new ArrayList<String>();
        elementos.add("[Subir a raiz]");
        for( File archivo: archivos){
        	if (archivo.isDirectory())
        		elementos.add(archivo.getPath()+"/");
        	else if (archivo.getName().matches("(?i).*svg")) {
        		elementos.add(archivo.getPath());
        	}
        }
        //Collections.sort(elementos);
        ArrayAdapter<String> listaArchivos= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elementos);
        setListAdapter(listaArchivos);
    }

}
