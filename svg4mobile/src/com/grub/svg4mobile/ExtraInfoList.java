package com.grub.svg4mobile;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Clase que implementa la activity que muestra una lista con la información extra.
 */
public class ExtraInfoList extends ListActivity {  

     /** Clave del atributo con el texto de la lista. */
    private static final String LABEL = "LABEL";
    /** Clave del atriburo con la ruta de la imagen. */
    private static final String ICON  = "ICON";
    private String[] images;
    private ExtraInfoParser infoparser = ExtraInfoParser.getInstance();
    
    /** 
     * Se llama al crear la actividad. 
     * @param savedInsance 
     */
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        					 WindowManager.LayoutParams.FLAG_FULLSCREEN);

        List< Map<String,Object> > drawables = buildListForSimpleAdapter();

        SimpleAdapter adapter = new SimpleAdapter(
          // Context
          this,
          // Datos a mostrar
          drawables,
          // Layout a emplear
            R.layout.activity_extrainfo_list_item,
            // Lista atribuos de los elementos a mostrar
            new String[] { LABEL, ICON },
            // Identificadores del layout
            new int[] { R.id.text, R.id.icon }
     );

        setListAdapter( adapter );

    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {      
        AlertDialog.Builder builder;
        AlertDialog alertDialog;

        Context mContext = this;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.image_dialog,
                                       (ViewGroup) findViewById(R.id.layout_root));

        ImageView image = (ImageView) layout.findViewById(R.id.image);

        image.setImageBitmap(BitmapFactory.decodeFile(images[position])); 

        builder = new AlertDialog.Builder(mContext);
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.show();
        
    }

    /**
     * Genera la lista de elementos
     * @return Lista de elementos
     */
    private List< Map<String,Object> > buildListForSimpleAdapter() {

    	List< Map<String,Object> > list = new ArrayList< Map<String,Object> > (3) ;
     
    	infoparser.First();
		images = new String[infoparser.getSize()];

		int i=0;
    	while(infoparser.hasNext()){
    		ExtraInfo info = (ExtraInfo)infoparser.next();
    		Log.v("svg4mobile", ""+info.getTitle());
    		Map<String,Object> map = new HashMap<String,Object>();
    		map.put( LABEL, info.getTitle()+"\n"+info.getDesc()+"\n"+info.getNotes()+"\n"+implode(info.getTags(), ", ") );
    		map.put( ICON, info.getImagePath() );
    		images[i++]=info.getImagePath();
    		list.add( map );
    	}     
        return list;
    }
    
    /**
     * Método que une elementos de un array con un string
     * @param arr Array a unir
     * @param st String que unirá los elementos
     * @return
     */
    private static String implode(String[] arr, String st) {
        String out = "";
        for(int i=0; i<arr.length; i++) {
            if(i!=0) { out += st; }
            out += arr[i];
        }
        return out;
    }

}
