package com.example.ivoline.medicos;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;

import com.example.ivoline.medicos.provider.myContentProvider;

public class RefDetailsActivity extends AppCompatActivity {

    private static final String[] PROJECTION1 = new String[]{Reference.REF_ID, Reference.REF_VALUE};
    private static final String[] PROJECTION2 = new String[]{RefDetails.RDETAILS_ID, RefDetails.RDETAILS_ANALYTE, RefDetails.RDETAILS_ID};
    private static final String[] PROJECTION3 = new String[]{RefDetails.RDETAILS_ID, RefDetails.RDETAILS_ANALYTE, Brand.BRAND_DCI};

    //The loaders specific id, loader's id are specific to the activity or fragment in which they reside
    private static final int LOADER_ID = 1;

    // The callbacks through which we will interact with the LoaderManager.
    private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;
    DBhelper db;
    EditText inputSearch;
    TextView tv;
    ImageView iv;
    CursorLoader cursor;
    BaseAdapter adapter;
    SimpleCursorTreeAdapter mAdapter;
    private Cursor mCursor;
    View rootView;
    ExpandableListView lv;
    private final String LOG_TAG = getClass().getSimpleName().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ref_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        lv = (ExpandableListView) findViewById(R.id.expListView2);


        // Perform Query
        final DBhelper db = new DBhelper(this);
        db.createDatabase();
        // mCallbacks = this;

        //lv = (ExpandableListView) getView().findViewById(R.id.expListView);

        Bundle extras = this.getIntent().getExtras();
        Integer ref_id = extras.getInt("REFID");
        System.out.println("THE REF ID IN REF DETAILS ACTIVITY IS  " + ref_id);

        mCursor = db.getRefDetails(ref_id);

        mAdapter = new SimpleCursorTreeAdapter(this,
                mCursor, R.layout.refrow, R.layout.refexprow,
                new String[]{RefDetails.RDETAILS_ANALYTE}, new int[]{R.id.value},
                R.layout.refexprow, R.layout.refexprow,
                new String[]{RefDetails.RDETAILS_SI, RefDetails.RDETAILS_CV},
                new int[]{R.id.siValue, R.id.cvValue}) {

            @Override
            protected Cursor getChildrenCursor(Cursor groupCursor) {
                String tempGroup = groupCursor.getString(groupCursor
                        .getColumnIndex(RefDetails.RDETAILS_ANALYTE));

                groupCursor = db.getRefChildDetails(tempGroup);

                return groupCursor;
                //System.out.println("THE TEMP GROUP IS"+tempGroup);

            }
        };
        lv.setAdapter(mAdapter);
    }

}






