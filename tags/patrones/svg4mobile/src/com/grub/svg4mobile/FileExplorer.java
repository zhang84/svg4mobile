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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FileExplorer extends ListActivity {
	
    private static final String MEDIA_PATH = new String("/sdcard/");
	private List<String>  elements = null;
	private File current_level = new File(MEDIA_PATH);
	   
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        					 WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_file_explorer_list);
        refill(new File(MEDIA_PATH).listFiles(new SvgFilter()));
    }
	
	
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        File file = new File(elements.get(position));
        if(position == 0){
            //No está en el raíz (hay padre)
            if(current_level.getParent() != null){
            	current_level = new File(current_level.getParent());
                refill(current_level.listFiles(new SvgFilter()));
            }
		} else {
            if (file.isDirectory()) {
            	current_level = file;
                refill(file.listFiles(new SvgFilter()));
            } else {
            	 Bundle bundle = new Bundle(); // Coje la ruta y la pone en el Bundle
	             bundle.putString("filename", file.getPath());  
	             
	             Intent intent = new Intent();
	             intent.putExtras(bundle);   	// Pone el Bundle en un Intent
	             setResult(RESULT_OK, intent);  // Devuelve el Intent
	             finish();
             }
        }
    } 
    
    private void refill(File[] filelist) {
        elements = new ArrayList<String>();
        elements.add(getResources().getString(R.string.up));
        ArrayList<String> dirs = new ArrayList<String>();
        ArrayList<String> files = new ArrayList<String>();
        
        for( File archivo: filelist){
        	
        	if (archivo.isDirectory())
        		dirs.add(archivo.getPath()+"/");
        	else
        		files.add(archivo.getPath());
        }
        Collections.sort(dirs);
        Collections.sort(files);
        
        elements.addAll(dirs);
        elements.addAll(files);

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elements));
    }
    
    class SvgFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
        	File theFile = new File(dir, name);
            return (name.endsWith(".svg")||theFile.isDirectory());
        }
    }
    
}