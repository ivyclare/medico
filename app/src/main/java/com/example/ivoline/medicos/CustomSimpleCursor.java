package com.example.ivoline.medicos;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class CustomSimpleCursor extends SimpleCursorAdapter {

        private Cursor c;
        private Context context;

    public CustomSimpleCursor(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.c = c;
        this.context = context;
    }

    public View getView(int pos, View inView, ViewGroup parent) {
           View v = inView;
           if (v == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.list_items, null);
           }
           this.c.moveToPosition(pos);
           String name = this.c.getString(this.c.getColumnIndex(Brand.BRAND_NAME));

          /* byte[] image = this.c.getBlob(this.c.getColumnIndex(Dishes.DISH_IMAGE));
           ImageView iv = (ImageView) v.findViewById(R.id.img);
           if (image != null) {
               // If there is no image in the database "NA" is stored instead of a blob
               // test if there more than 3 chars "NA" + a terminating char if more than
               // there is an image otherwise load the default
               if(image.length > 3)
               {
                   iv.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
               }
               else
               {
                   iv.setImageResource(R.drawable.abc_btn_check_material);
               }
           } */
               TextView fname = (TextView) v.findViewById(R.id.brand);
               fname.setText(name);
           return(v);
}

}