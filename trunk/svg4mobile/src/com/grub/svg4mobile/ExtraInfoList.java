package com.grub.svg4mobile;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;

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
            R.layout.activity_extrainfo_list_item,
            // The list item attributes to display
            new String[] { LABEL, ICON },
            // And the ids of the views where they should be displayed (same order)
            new int[] { R.id.text, R.id.icon }
     );

        setListAdapter( adapter );

    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	/*new AlertDialog.Builder(this)
        .setTitle("Título")
        .setMessage("nDescripción del elemento .. Descripción del elemento .. Descripción del elemento .. \nNotas Notas Notas Notas Notas Notas Notas \n tag1, tag 2")
        .setNeutralButton("Aceptar", new DialogInterface.OnClickListener(){
        public void onClick(DialogInterface dialog, int whichButton) {  }
        }).create().show();*/
        
        AlertDialog.Builder builder;
        AlertDialog alertDialog;

        Context mContext = this;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.image_dialog,
                                       (ViewGroup) findViewById(R.id.layout_root));

        //TextView text = (TextView) layout.findViewById(R.id.text);
        //text.setText("");
        ImageView image = (ImageView) layout.findViewById(R.id.image);
        //image.setImageResource(R.drawable.icon);
        //image.setImageURI(Uri.fromFile(new File("/sdcard/1.png")));
        image.setImageBitmap(BitmapFactory.decodeFile("/sdcard/1.jpg"));
        

        builder = new AlertDialog.Builder(mContext);
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.show();
        
    }

     /**
     * @return
     */
    private List< Map<String,Object> > buildListForSimpleAdapter() {

     List< Map<String,Object> > list = new ArrayList< Map<String,Object> > (3) ;
     Map<String,Object> map = new HashMap<String,Object>();
     map.put( LABEL, "Título\nDescripción del elemento .. Descripción del elemento .. Descripción del elemento .. \nNotas Notas Notas Notas Notas Notas Notas \n tag1, tag 2" );
     map.put( ICON, "/sdcard/1.png" );
     list.add( map );
     //map.put( LABEL, "Título" );
     //map.put( ICON, "/sdcard/1.png" );
     list.add( map );
     //map.put( LABEL, "32423fdsfsdf" );
     //map.put( ICON, "/sdcard/1.png" );
     list.add( map );
     
         return list;
    }
}
