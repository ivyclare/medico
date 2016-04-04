package com.example.ivoline.medicos;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Ivoline-Clarisse on 1/28/2016.
 */
public class ReferenceFragments extends ListFragment {

    EditText inputSearch;
    ListView listDrugs;
    CursorLoader cursor;
    BaseAdapter adapter;
    private Cursor mCursor;
    DBhelper db;
    // SimpleCursorAdapter adapter;

    public ReferenceFragments() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* listDrugs = (ListView) getView().findViewById(R.id.drug_list);
        listDrugs.invalidateViews();
        listDrugs.setTextFilterEnabled(true);
        // Perform Query
        DBhelper db = new DBhelper(getActivity());
        db.createDatabase(); |*/

        // getSupportLoaderManager().initLoader(LOADER_ID, null, mCallbacks);

        System.out.println("ABOUT TO START SETUP ");
        System.out.println("THE TABLENAME IS " + Reference.TABLE_NAME3);
        //We will just display the list of drugs in the adult drug table.
        this.setUpList(Reference.TABLE_NAME3);
        // Enable Listener
        //this.show_description(Drug.TABLE_NAME1);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.references, container, false);


        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void setUpList(String tableName) {

        System.out.println("ENTERS reference FRAMENT");

        DBhelper db = new DBhelper(getActivity());
        //DBhelper db = new DBhelper(this);
        db.createDatabase();

        adapter = null;
        mCursor = db.query(Reference.TABLE_NAME3);

        System.out.println("Reference QUERY WORKS");

        // Now create a new list adapter bound to the cursor.
        BaseAdapter adapter = new rCustomSimpleCursor(getActivity(), // Context.
                R.layout.ref_items, // Specify the row template
                // to use (here, two
                // columns bound to the
                // two retrieved cursor
                // rows)
                mCursor, // Pass in the cursor to bind to.
                // Array of cursor columns to bind to.
                new String [] {Reference.REF_VALUE},
                // Parallel array of which template objects to bind to those
                // columns.
                new int[] {R.id.ref_name});

        // Bind to our new adapter.
        setListAdapter(adapter);
    }


    @Override
    public void onListItemClick(ListView list, View v, int position, long id) {
        String tableName = Drug.TABLE_NAME1;
        // Get the cursor, positioned to the corresponding row in the result set
        Cursor cursor = (Cursor) list.getItemAtPosition(position);
        //Get it from the database.
        Integer ref_id = cursor.getInt(cursor.getColumnIndex("_id"));
        //Send the id of the recipe to Comments class inorder to get the comments
        System.out.println("THE REF ID HEREEE IS "+ref_id);
        Intent intent = new Intent(getActivity(), RefDetailsActivity.class);
        intent.putExtra("REFID", ref_id);
        intent.putExtra("tableName", tableName);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

    }
}
