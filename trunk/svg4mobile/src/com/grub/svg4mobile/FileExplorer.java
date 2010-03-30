package com.grub.svg4mobile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FileExplorer extends ListActivity {
	
	   
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado);
        rellenarConElRaiz();
    }
      
    
	
	private List<String>  elementos = null;
	

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        int IDFilaSeleccionada = position;
        if (IDFilaSeleccionada==0){
            rellenarConElRaiz();
        } else {
            File archivo = new File(elementos.get(IDFilaSeleccionada));
            if (archivo.isDirectory())
                rellenar(archivo.listFiles());
             else {
            	 
            	 Bundle bundle = new Bundle();   // Get the name and put it in the Bundle
	             // Here the bundle mapping is "Name" --> the name typed by user
	             bundle.putString("filename", archivo.getPath());  
	             
	             Intent intent = new Intent();
	             intent.putExtras(bundle);   // Put the Bundle in an Intent
	             setResult(RESULT_OK, intent);   // Return the Intent
	             finish();
            	 
                 /*new AlertDialog.Builder(this)
                 .setTitle("Archivo Seleccionado")
                 .setMessage(archivo.getPath())
                 .setNeutralButton("Cancelar", new DialogInterface.OnClickListener(){
                     public void onClick(DialogInterface dialog, int whichButton) {
                         
                     }
                 }).create().show();*/
             }
        }
    }
    
    public void rellenarConElRaiz() {
        rellenar(new File("/").listFiles());
    } 
    
    private void rellenar(File[] archivos) {
        elementos = new ArrayList<String>();
        elementos.add("[Volver a raiz]");
        for( File archivo: archivos)
            elementos.add(archivo.getPath());
       
        ArrayAdapter<String> listaArchivos= new ArrayAdapter<String>(this, R.layout.fila, elementos);
        setListAdapter(listaArchivos);
    }
    
	
	

}
