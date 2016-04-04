package com.example.ivoline.medicos;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLOutput;

public class DBhelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "Medico.sqlite";
	private static final int DB_VERSION = 1;
	private final Context myContext;
	private SQLiteDatabase mydb;


     //Constructor takes and keeps a reference of the passed context in order to access
     // to the application assets and resources.
     //@param context
	public DBhelper(Context context) {
		super(context, DB_NAME , null, DB_VERSION);
		this.myContext = context;
	}

	private String getDatabasePath() {
	    // The Android's default system path of your application database.
	    // /data/data/<package name>/databases/<databasename>
	    return myContext.getFilesDir().getParentFile().getAbsolutePath()
	            + "/databases/" + DB_NAME;
	}

	//copies database from assets_folder which will overwrite the empty database that will be created
	//done using byte stream
	public void copyDatabase() throws IOException {
		System.out.println("ENTERED COPY DATABASE METHOD ");
		//open local db as input stream
		InputStream myinput = myContext.getAssets().open(DB_NAME);
		//path to the empty db that will be created
		String dbFile = getDatabasePath();
		//open the empty db as outputstream
		OutputStream myoutput = new FileOutputStream(dbFile);
		//transfer bytes from myinput to myoutput
		byte[] buffer = new byte[1024];
		int length;
		while((length = myinput.read(buffer))>0){
			System.out.println("ENTERED WHILE LOOP");
			myoutput.write(buffer, 0, length);
		}
		//close the streams
		myoutput.flush();
		myoutput.close();
		myinput.close();
	}
	//check if database exist to avoid recopying the database everytime the application is launched
	//and if it does, close it, if it doesn't call the getDBpath()
	private boolean checkDatabase(){
		System.out.println("ENTERS CHECK DATABASE");
		SQLiteDatabase checkDB = null;
		try{
			String myPath = getDatabasePath();
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
			System.out.println("DATABASE EXIST");
		}catch(SQLiteException e){
			System.out.println("database doesn't exist");
		}
		if(checkDB != null){
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	public void createDatabase(){
		System.out.println("ENTERS CREATE DATABASE METHOD");
		boolean dbExist = checkDatabase();
		if(dbExist){
			//do nothing, db already exist
		}else{
			//create and overwrite empty database
			this.getReadableDatabase();
			try {
				copyDatabase();
			} catch (IOException e) {
				//throw new Error("Error copying database");
				System.out.println("Error copying database");
				e.printStackTrace();
			}
		}

	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		try {
			this.copyDatabase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DBhelper openDatabase(){
		try{
			String myPath = getDatabasePath();
			mydb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		}catch(SQLiteException e){
			System.out.println("database doesn't exist");
	}
		return this;
	}

	  @Override
		public synchronized void close() {
	    	    if(mydb != null)
	    		    mydb.close();
	    	    super.close();
		}

	  public int numOfRows(){
			SQLiteDatabase db = this.getReadableDatabase();
			int numRows = (int) DatabaseUtils.queryNumEntries(db, Drug.TABLE_NAME1);
			return numRows;
		}

	  public Cursor getDrugDetails(Integer drug_id) {
			SQLiteDatabase db = this.getReadableDatabase();
				this.openDatabase();
				Cursor cursor = db.rawQuery("select * from Drug where _id="+drug_id+"",null);
				return cursor;
			}

	  public Cursor getBrandDetails(Integer id) {
			SQLiteDatabase db = this.getReadableDatabase();
				this.openDatabase();
				Cursor cursor = db.rawQuery("select * from Brand where drug_id="+id+" ",null);
				return cursor;
			}

	public Cursor getBrandChildDetails(String name) {
		SQLiteDatabase db = this.getReadableDatabase();
		this.openDatabase();
		Cursor cursor = db.rawQuery("select * from Brand where name='"+name+"' ",null);
		return cursor;
	}

	public Cursor getRefDetails(Integer id) {
		SQLiteDatabase db = this.getReadableDatabase();
		this.openDatabase();
		Cursor cursor = db.rawQuery("select * from RefDetail where ref_id="+id+" ",null);
		System.out.println("REF DETAIL CURSOR IS "+cursor);
		return cursor;
	}

	public Cursor getRefChildDetails(String name) {
		SQLiteDatabase db = this.getReadableDatabase();
		this.openDatabase();
		Cursor cursor = db.rawQuery("select * from RefDetail where Analyte='"+name+"' ",null);
		return cursor;
	}


	public Cursor query(String tableName) {
		this.openDatabase();
		SQLiteDatabase mydb = this.getReadableDatabase();
		Cursor res = mydb.rawQuery("select * from " +tableName, null);
		System.out.println("CURSOR CONTAINS: " +res);
		//res.moveToFirst();
		return res;
	}

	 //Method to be used in filter query
	public Cursor searchQuery(CharSequence input,String tableName){
		SQLiteDatabase mydb = this.getReadableDatabase();
		this.openDatabase();
		Cursor res = null;
		if (input == null  ||  input.length () == 0){
			res = mydb.rawQuery("select * from "+tableName, null);
		}else{
			if(tableName.equals(Drug.TABLE_NAME1)){
				res = mydb.rawQuery("select * from Drug where DCI like '%"+input.toString()+"%'", null);
			}else if(tableName.equals(Brand.TABLE_NAME2)){
				res = mydb.rawQuery("select * from Brand where brandName like '%"+input.toString()+"%'", null);
			}
	    }
		return res;
	}
}
