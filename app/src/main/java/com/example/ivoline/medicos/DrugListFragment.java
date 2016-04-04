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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ivoline-Clarisse on 1/28/2016.
 */
public class DrugListFragment extends ListFragment {

    EditText inputSearch;
    ListView listDrugs;
    CursorLoader cursor;
    SimpleCursorAdapter adapter;
    private Cursor mCursor;
    String[] words;
    DBhelper db;
    ArrayList<String> array_sort = new ArrayList<String>();

   // SimpleCursorAdapter adapter;

    public DrugListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // getSupportLoaderManager().initLoader(LOADER_ID, null, mCallbacks);

        System.out.println("ABOUT TO START SETUP ");

        // Enable Listener
        //this.show_description(Drug.TABLE_NAME1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.drug_fragment, container, false);

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

    @Override public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        listDrugs = getListView();
        listDrugs.invalidateViews();
        listDrugs.setTextFilterEnabled(true);
        // Perform Query
        DBhelper db = new DBhelper(getActivity());
        db.createDatabase();

        System.out.println("THE TABLENAME IS " + Drug.TABLE_NAME1);
        //We will just display the list of drugs in the adult drug table.
       // this.setUpList(Drug.TABLE_NAME1);

        System.out.println("ENTERS DRUGLIST FRAGMENT");

       // DBhelper db = new DBhelper(getActivity());
        //DBhelper db = new DBhelper(this);
       // db.createDatabase();

        adapter = null;
        mCursor = db.query(Drug.TABLE_NAME1);

        System.out.println("Druglist QUERY WORKS");

        // Now create a new list adapter bound to the cursor.
        BaseAdapter adapter = new dCustomSimpleCursor(getActivity(), // Context.
                R.layout.list_items, // Specify the row template
                // to use (here, two
                // columns bound to the
                // two retrieved cursor
                // rows)
                mCursor, // Pass in the cursor to bind to.
                // Array of cursor columns to bind to.
                new String [] {Drug.DCI},
                // Parallel array of which template objects to bind to those
                // columns.
                new int[] {R.id.drug_name});

        // Bind to our new adapter.
        setListAdapter(adapter);

        inputSearch = (EditText)getView().findViewById(R.id.inputSearch);
        //Implementing textsearch
        listDrugs.setTextFilterEnabled(true);

        inputSearch.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // Abstract Method of TextWatcher Interface.
            }

            public void beforeTextChanged(CharSequence s,
                                          int start, int count, int after) {
                // Abstract Method of TextWatcher Interface.
            }

            public void onTextChanged(CharSequence s,
                                      int start, int before, int count) {
                int textlength = inputSearch.getText().length();
                array_sort.clear();
                for (int i = 0; i < words.length; i++) {
                    if (textlength <= words[i].length()) {
                        if (inputSearch.getText().toString().equalsIgnoreCase(
                                (String)
                                        words[i].subSequence(0,
                                                textlength))) {
                            array_sort.add(words[i]);
                        }
                    }
                }
                listDrugs.setAdapter(new ArrayAdapter<String>
                        (getActivity(),
                                android.R.layout.simple_list_item_activated_1, array_sort));

                /*BaseAdapter adapter = new dCustomSimpleCursor(getActivity(), // Context.
                        R.layout.list_items,
                        mCursor,
                        new String [] {Drug.DCI},
                        new int[] {R.id.drug_name});

                // Bind to our new adapter.
                setListAdapter(adapter); */
            }
        });


        /*************** IMPLEMENTING SEARCH FUNCTIONALITY ********************************
           adapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                DBhelper db = new DBhelper(getActivity());
                //System.out.printf("TABLENAME IN RUN QUERY IS %s",tableName);
                return db.searchQuery(constraint, Drug.TABLE_NAME1);
            }
        });

        EditText inputSearch;
        inputSearch = (EditText) getView().findViewById(R.id.inputSearch);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                System.out.println("Text change listener is working");
              //  DrugList.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        }); */


    }


    private void setUpList(String tableName) {

       System.out.println("ENTERS DRUGLIST FRAGMENT");

        DBhelper db = new DBhelper(getActivity());
        //DBhelper db = new DBhelper(this);
        db.createDatabase();

            adapter = null;
            mCursor = db.query(Drug.TABLE_NAME1);

            System.out.println("Druglist QUERY WORKS");

            // Now create a new list adapter bound to the cursor.
            BaseAdapter adapter = new dCustomSimpleCursor(getActivity(), // Context.
                    R.layout.list_items, // Specify the row template
                    // to use (here, two
                    // columns bound to the
                    // two retrieved cursor
                    // rows)
                    mCursor, // Pass in the cursor to bind to.
                    // Array of cursor columns to bind to.
                    new String [] {Drug.DCI},
                    // Parallel array of which template objects to bind to those
                    // columns.
                    new int[] {R.id.drug_name});

            // Bind to our new adapter.
            setListAdapter(adapter);

        }


    @Override
    public void onListItemClick(ListView list, View v, int position, long id) {
        String tableName = Drug.TABLE_NAME1;
        // Get the cursor, positioned to the corresponding row in the result set
        Cursor cursor = (Cursor) list.getItemAtPosition(position);
        //Get it from the database.
        Integer Drug_id = cursor.getInt(cursor.getColumnIndex("_id"));
        //Send the id of the recipe to Comments class inorder to get the comments
        System.out.println("THE DRUG ID HEREEE IS "+Drug_id);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("DrugID", Drug_id);
        intent.putExtra("tableName", tableName);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

    }


}
