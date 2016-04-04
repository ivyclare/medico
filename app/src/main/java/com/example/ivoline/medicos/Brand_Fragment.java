package com.example.ivoline.medicos;

import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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
import android.content.Intent;

import com.example.ivoline.medicos.provider.myContentProvider;

import java.util.HashMap;


public class Brand_Fragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private static final String[] PROJECTION1 = new String[]{Drug.DRUG_ID, Drug.DCI};
	private static final String[] PROJECTION2 = new String[]{Brand.BRAND_ID, Brand.BRAND_NAME,  Brand.BRAND_DCI};
	private static final String[] PROJECTION3 = new String[]{Brand.BRAND_ID, Brand.BRAND_NAME, Brand.BRAND_DCI};
	//private View rootView;
	//C:\Users\Ivoline-Clarisse\AppData\Local\Android\sdk\tools\emulator.exe -scale 0.25 -netdelay none -netspeed full -avd Galaxy_Nexus_API_19


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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Perform Query
		final DBhelper db = new DBhelper(getActivity());
		db.createDatabase();
		mCallbacks = this;

		//lv = (ExpandableListView) getView().findViewById(R.id.expListView);

		Loader<Cursor> loader = getLoaderManager().getLoader(-1);
		if (loader != null && !loader.isReset()) {
			getLoaderManager().restartLoader(-1, null, this);
		} else {
			getLoaderManager().initLoader(-1, null, this);
		}

		/*mAdapter = new MySimpleCursorTree(getActivity(),
				android.R.layout.simple_expandable_list_item_1,
				android.R.layout.simple_expandable_list_item_1,
				new String[] {Brand.BRAND_NAME },
				new int[] { android.R.id.text1 },
				new String[] {Brand.BRAND_DCI },
				new int[] { android.R.id.text1 }); */
		Bundle extras = getActivity().getIntent().getExtras();
		Integer Drug_id = extras.getInt("DrugID");
		System.out.println("THE DRUG ID IN BRAND FRAGMENT IS  "+Drug_id);

		mCursor = db.getBrandDetails(Drug_id);

			mAdapter = new SimpleCursorTreeAdapter(getActivity(),
				mCursor, R.layout.row, R.layout.exprows,
						new String[] { Brand.BRAND_NAME }, new int[] { R.id.brand  },
						R.layout.exprows, R.layout.exprows,
						new String[] {Brand.DOSAGE,Brand.PRESENTATION, Brand.COST},
					new int[] { R.id.doseValue, R.id.presentaValue,R.id.costValue }) {

			@Override
			protected Cursor getChildrenCursor(Cursor groupCursor) {
				String tempGroup = groupCursor.getString(groupCursor
						.getColumnIndex(Brand.BRAND_NAME));

				//System.out.println("THE TEMP GROUP IS"+tempGroup);

				groupCursor = db.getBrandChildDetails(tempGroup);

				//System.out.println("THE GROUP CURSOR IS"+groupCursor);
				return groupCursor;
			}
		};

		//System.out.println("ADAPTER CONTENT IS "+mAdapter);

		//lv.setAdapter(mAdapter);


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.brand_fragment, container, false);

		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//System.out.println("VIEW IS CREATED HERE");
		lv = (ExpandableListView) view.findViewById(R.id.expListView);
	    lv.setAdapter(mAdapter);

	}


	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle arg1) {

		Log.d(LOG_TAG, "onCreateLoader FOR LOADER_ID IS " + id);
		CursorLoader cl;
		if (id != -1) {
			// child cursor
			String selection = "(" + Brand.BRAND_ID + "  )";
			String sortOrder = Brand.BRAND_NAME + " ASC";
			System.out.println("THE SELECTION IS "+selection);
			//String[] selectionArgs = new String[] { String.valueOf(id) };

			cl = new CursorLoader(getActivity(), myContentProvider.brandCONTENT_URI, PROJECTION1,
					null, null, sortOrder);

			// Log.d(LOG_TAG,"THE CURSOR IS "+ cl);
		} else {
			// group cursor

			String selection = "((" +  Brand.BRAND_ID
					+ " NOTNULL) AND ("
					+  Brand.BRAND_DCI + "=1) AND ("
					+ Brand.BRAND_NAME  + " != '' ))";
			//Log.d(LOG_TAG, "THE SELECTION WHEN THE id = -1 IS"+selection);
			String sortOrder = Brand.BRAND_NAME + " ASC";

			cl = new CursorLoader(getActivity(),  myContentProvider.brandCONTENT_URI,PROJECTION2,
					selection, null, sortOrder);
		}
		return cl;
	}


	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		Bundle extras = getActivity().getIntent().getExtras();
		Integer Drug_id = extras.getInt("DrugID");

		DBhelper db = new DBhelper(getActivity());
		db.createDatabase();
		data = db.getBrandDetails(Drug_id);

	}


	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		//((SimpleCursorAdapter) adapter).swapCursor(null);
		// Called just before the cursor is about to be closed.
		int id = loader.getId();
		//Log.d(LOG_TAG, "onLoaderReset() for loader_id " + id);
		if (id != -1) {
			// child cursor
			try {
				mAdapter.setChildrenCursor(id, null);
			} catch (NullPointerException e) {
				//Log.w(LOG_TAG, "Adapter expired, try again on the next query: + e.getMessage());
			}
		} else {
			mAdapter.setGroupCursor(null);
		}
	}
	}



