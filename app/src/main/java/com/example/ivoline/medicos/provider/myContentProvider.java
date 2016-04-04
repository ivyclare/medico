package com.example.ivoline.medicos.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.example.ivoline.medicos.Brand;
import com.example.ivoline.medicos.DBhelper;
import com.example.ivoline.medicos.Drug;
import com.example.ivoline.medicos.Reference;

//necessary cos it returns the cursor to the cursor loader
public class myContentProvider extends ContentProvider {

	private DBhelper mydb;
	public static final String AUTHORITY = "com.example.ivoline.provider.myContentProvider";
	public static final Uri drugCONTENT_URI = Uri.parse("content://" + AUTHORITY + "/Drug");
	public static final Uri brandCONTENT_URI = Uri.parse("content://" + AUTHORITY + "/Brand");
	public static final Uri refCONTENT_URI = Uri.parse("content://" + AUTHORITY + "/References");

	//helper constants for use in the uri
	private static final int DRUG  =  1;
	private static final int DRUG_ID  =  2;
	private static final int BRAND  =  3;
	private static final int BRAND_ID  = 4;
	private static final int REFERENCE =  5;
	private static final int REFERENCE_ID  = 6;


	static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static{
		uriMatcher.addURI(AUTHORITY, "Drug", DRUG);
		uriMatcher.addURI(AUTHORITY, "Drug/#",DRUG_ID );

		uriMatcher.addURI(AUTHORITY, "Brand", BRAND);
		uriMatcher.addURI(AUTHORITY, "Brand/#", BRAND_ID);

		uriMatcher.addURI(AUTHORITY, "References_ID ", REFERENCE);
		uriMatcher.addURI(AUTHORITY, "References/#", REFERENCE_ID );
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		mydb  = new DBhelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
						String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder sqlBuilder1 = new SQLiteQueryBuilder();
		sqlBuilder1.setTables(Drug.TABLE_NAME1);

		SQLiteQueryBuilder sqlBuilder2 = new SQLiteQueryBuilder();
		sqlBuilder2.setTables(Brand.TABLE_NAME2);

		SQLiteQueryBuilder sqlBuilder3 = new SQLiteQueryBuilder();
		sqlBuilder3.setTables(Reference.TABLE_NAME3);

		SQLiteDatabase db = mydb.getWritableDatabase();
		int uriType = uriMatcher.match(uri);
		Cursor cursor = null;

		switch(uriType){
			case DRUG:
				break;
			case DRUG_ID:
				// adding the ID to the original query
				sqlBuilder1.appendWhere(Drug.DRUG_ID + "="
						+ uri.getLastPathSegment());
				cursor =  sqlBuilder1.query(db, projection, selection, selectionArgs, null,
						null, sortOrder);
				//---register to watch a content URI for changes---
				cursor.setNotificationUri(getContext().getContentResolver(), uri);
				break;

			case BRAND:
				break;
			case BRAND_ID:
				// adding the ID to the original query
				sqlBuilder2.appendWhere(Brand.BRAND_ID + "="
						+ uri.getLastPathSegment());
				cursor =  sqlBuilder2.query(db, projection, selection, selectionArgs, null,
						null, sortOrder);
				cursor.setNotificationUri(getContext().getContentResolver(), uri);
				break;

			case REFERENCE:
				break;
			case REFERENCE_ID:
				// adding the ID to the original query
				sqlBuilder3.appendWhere(Reference.REF_ID + "="
						+ uri.getLastPathSegment());
				cursor =  sqlBuilder3.query(db, projection, selection, selectionArgs, null,
						null, sortOrder);
				cursor.setNotificationUri(getContext().getContentResolver(), uri);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}

	return cursor;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}


}

