package com.example.ivoline.medicos;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.support.v4.widget.DrawerLayout;


import com.example.ivoline.medicos.provider.myContentProvider;

public class DrugList extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static final String[] PROJECTION1 = new String[]{Drug.DRUG_ID, Drug.DCI};
 
        String tableName = "";
        //The loaders specific id, loader's id are specific to the activity or fragment in which they reside
        private static final int LOADER_ID = 1;
        private Toolbar mToolbar;
        private FragmentDrawer drawerFragment;
        // The callbacks through which we will interact with the LoaderManager.
        private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

        DBhelper db;
        EditText inputSearch;
        ListView listDrugs;
       SimpleCursorAdapter adapter;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @SuppressLint("NewApi")
        @Override
        protected void onCreate(Bundle SavedInstantState) {
            super.onCreate(SavedInstantState);
            setContentView(R.layout.druglist);
            setTitle(R.string.list);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                  getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        String displayValue = getIntent().getExtras().getString("DISPLAY");

        if(displayValue.equalsIgnoreCase("0")){
            // display the first navigation drawer view on app launch
            displayView(0);
        }else{

            // display the first navigation drawer view on app launch
            displayView(1);
        }
    }
        //}

     @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu , menu);
            return true;
        }



/*	   //If the Add Menu is clicked
        @Override
 public boolean onOptionsItemSelected(MenuItem item) {

      if(item.getItemId() == R.id.add){
          Bundle extras = getIntent().getExtras();
            String category = extras.getString("key");
            Bundle dataBundle = new Bundle();
            dataBundle.putInt("id", 0);
            dataBundle.putString("tableName", category);
            Intent intent = new Intent(getApplicationContext(),AddDrug.class);
            intent.putExtras(dataBundle);
            startActivity(intent);
            return true;
       //Takes the activity to the previous activity
      }else if(item.getItemId() == android.R.id.home){
           Intent i = new Intent(this, MainActivity.class);
           i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           startActivity(i);
           return true;
     //Connecting to links to more Drugs
      }else if(item.getItemId() == R.id.more){
         /* Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=cameroonian+dishes+and+juices+"));
           startActivity(browserIntent);
          Bundle extras = getIntent().getExtras();
            String category = extras.getString("key");
            Bundle dataBundle = new Bundle();
            dataBundle.putInt("id", 0);
            dataBundle.putString("tableName", category);
            Intent browserIntent = new Intent(DrugList.this, ShareActivity.class);
            browserIntent.putExtras(dataBundle);
            startActivity(browserIntent);
           return true;
      }else{
        return super.onOptionsItemSelected(item);
       }
     } */

        //Provides the slide style
        @Override
        public void onBackPressed() {
            super.onBackPressed();
            Intent i = new Intent(DrugList.this, LandPage.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }

    @Override
    public void onDrawerItemSelected(View view, int position) {

        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new DrugListFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new ReferenceFragments();
                title = getString(R.string.title_friends);
                break;
            case 2:
                fragment = new AccountInfoFragment();
                title = getString(R.string.title_messages);
                break;
            case 3:
                fragment = new AccountInfoFragment();
                title = getString(R.string.title_out);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}

