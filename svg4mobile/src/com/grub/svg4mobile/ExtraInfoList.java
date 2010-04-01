package com.grub.svg4mobile;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;

/**
 * 
 */
public class ExtraInfoList extends ListActivity {  

     /** Attribute key for the list item text. */
    private static final String LABEL = "LABEL";
    /** Attribute key for the list item icon's drawable resource. */
    private static final String ICON  = "ICON";

     
     ///////////////////////////////////////////////////////////////////////
     //  Public Methods
    /** Called when the activity is first created. */
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
       
        // Format the data for the SimpleListAdapter:
        // Each item in the list represents one list entry.
        // The attributes of this item are represented in a Map,
        // with the names of the attributes as the keys.
        // Our keys are LABEL and ICON.
        List< Map<String,Object> > drawables = buildListForSimpleAdapter();
       
        // Now build the list adapter
        SimpleAdapter adapter = new SimpleAdapter(
          // the Context
          this,
          // the data to display
          drawables,
          // The layout to use for each item
            android.R.layout.activity_list_item,
            // The list item attributes to display
            new String[] { LABEL, ICON },
            // And the ids of the views where they should be displayed (same order)
            new int[] { android.R.id.text1, android.R.id.icon }
     );

        setListAdapter( adapter );

    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	new AlertDialog.Builder(this)
        .setTitle("Archivo Seleccionado")
        .setMessage("asdasdjalshdljashdj")
        .setNeutralButton("Aceptar", new DialogInterface.OnClickListener(){
        public void onClick(DialogInterface dialog, int whichButton) {
        	/* No hacemos nada */
        }
        }).create().show();
    }

     /**
     * @return
     */
    private List< Map<String,Object> > buildListForSimpleAdapter() {

     List< Map<String,Object> > list = new ArrayList< Map<String,Object> > (3) ;
     Map<String,Object> map = new HashMap<String,Object>();
     map.put( LABEL, "1" );
     map.put( ICON, "/sdcard/1.png" );
     list.add( map );
     map.put( LABEL, "1" );
     map.put( ICON, "/sdcard/1.png" );
     list.add( map );
     map.put( LABEL, "1" );
     map.put( ICON, "/sdcard/1.png" );
     list.add( map );
     
         return list;
    }
}
