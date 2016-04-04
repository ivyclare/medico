package com.example.ivoline.medicos;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Pregnancy_Fragment extends Fragment {

	private Cursor mCursor;

	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	  View rootView = inflater.inflate(R.layout.preg_fragment, container, false);

		Bundle extras = getActivity().getIntent().getExtras();
		Integer Drug_id = extras.getInt("DrugID");

		DBhelper db = new DBhelper(getActivity());
		mCursor = db.getDrugDetails(Drug_id);

		TextView classValue = (TextView) rootView.findViewById(R.id.pclassValue);

		mCursor.moveToFirst();
		String val = mCursor.getString(mCursor.getColumnIndex("Pregnancy"));
		System.out.println("VALUE FROM DATABASE IS "+val);

		classValue.setText(val);

	   return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

      	System.out.println("ABOUT TO START SETUP ");
		System.out.println("THE TABLENAME IS " + Drug.TABLE_NAME1);

	}
}
