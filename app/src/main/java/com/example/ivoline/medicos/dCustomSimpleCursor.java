package com.example.ivoline.medicos;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class dCustomSimpleCursor extends SimpleCursorAdapter implements Filterable  {

    private Cursor c;
    private Context context;
    //ValueFilter valueFilter;
    ArrayList<String> mStringFilterList;

    public dCustomSimpleCursor(Context context, int layout, Cursor c, String[] from, int[] to) {
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
        String name = this.c.getString(this.c.getColumnIndex(Drug.DCI));

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
        TextView fname = (TextView) v.findViewById(R.id.drug_name);
        fname.setText(name);
        return (v);
    }

/*
    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }


    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<String> filterList = new ArrayList<String>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ( (mStringFilterList.get(i).getName().toUpperCase() )
                            .contains(constraint.toString().toUpperCase())) {

                        Country country = new Country(mStringFilterList.get(i)
                                .getName() ,  mStringFilterList.get(i)
                                .getIso_code() ,  mStringFilterList.get(i)
                                .getFlag());

                        filterList.add(country);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            countrylist = (ArrayList<Country>) results.values;
            notifyDataSetChanged();
        }

    } */
}