package com.example.ivoline.medicos;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ivoline-Clarisse on 1/29/2016.
 */
public class AccountInfoFragment extends Fragment {

    protected EditText editUsername;

    private EditText editEmail;

    private EditText editSpeciality;

    private EditText editCountry;

    private EditText editRegion;

    private EditText editStructure;

    private int userid;

    private final String serverUrl = "http://192.168.1.13/medic/index.php";

    public AccountInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.accountinfo, container, false);

        editUsername = (EditText) rootView.findViewById(R.id.editusername);

        editEmail = (EditText)rootView.findViewById(R.id.editemail);

        editSpeciality = (EditText)rootView.findViewById(R.id.editspec);

        editCountry = (EditText) rootView.findViewById(R.id.editctry);

        editRegion = (EditText)rootView.findViewById(R.id.editreg);

        editStructure = (EditText)rootView.findViewById(R.id.editstruct);

        Bundle extras = getActivity().getIntent().getExtras();
        String username = extras.getString("USERNAME");
        int userid = extras.getInt("USERID");
        System.out.println("THE USERNAME FOR ACCOUNT INFO IS "+username);
        System.out.println("THE USER ID IS " + userid);
        new LoadUserInfo().execute("http://192.168.1.13/medic/index.php?getData&userid=" + userid);

        //Edit Button is clicked
        ImageButton editButton = (ImageButton) rootView.findViewById(R.id.edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button updateButton = (Button) getView().findViewById(R.id.update);
                updateButton.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "You can Edit Now", Toast.LENGTH_LONG).show();

                editEmail.setEnabled(true);
                editEmail.setFocusableInTouchMode(true);
                editEmail.setClickable(true);
                final String em = editEmail.getText().toString();

                editSpeciality.setEnabled(true);
                editSpeciality.setFocusableInTouchMode(true);
                editSpeciality.setClickable(true);
                final String sp = editSpeciality.getText().toString();

                editRegion.setEnabled(true);
                editRegion.setFocusableInTouchMode(true);
                editRegion.setClickable(true);
                final String reg = editRegion.getText().toString();

                editCountry.setEnabled(true);
                editCountry.setFocusableInTouchMode(true);
                editCountry.setClickable(true);
                final String ctry = editCountry.getText().toString();

                editStructure.setEnabled(true);
                editStructure.setFocusableInTouchMode(true);
                editStructure.setClickable(true);
                final String struc = editStructure.getText().toString();
            }
        });

        //Update Button Clicked
        Button updateButton = (Button) rootView.findViewById(R.id.update);
        //updateButton.setVisibility(View.VISIBLE);

        updateButton.setOnClickListener(new View.OnClickListener() {
            //Integer recipe_id = getIntent().getExtras().getInt("recipeID");
            @Override
            public void onClick(View arg0) {
                //Setting values to clickable so that they can be edited
                editEmail.setEnabled(true);
                editEmail.setFocusableInTouchMode(true);
                editEmail.setClickable(true);
                final String em = editEmail.getText().toString();

                editSpeciality.setEnabled(true);
                editSpeciality.setFocusableInTouchMode(true);
                editSpeciality.setClickable(true);
                final String sp = editSpeciality.getText().toString();

                editRegion.setEnabled(true);
                editRegion.setFocusableInTouchMode(true);
                editRegion.setClickable(true);
                final String reg = editRegion.getText().toString();

                editCountry.setEnabled(true);
                editCountry.setFocusableInTouchMode(true);
                editCountry.setClickable(true);
                final String ctry = editCountry.getText().toString();

                editStructure.setEnabled(true);
                editStructure.setFocusableInTouchMode(true);
                editStructure.setClickable(true);
                final String struc = editStructure.getText().toString();



                Bundle extras = getActivity().getIntent().getExtras();
                String username = extras.getString("USERNAME");
                int userid = extras.getInt("USERID");
                System.out.println("THE ID IN EDIT IS "+userid);
                int id_To_Update = userid;

                String url = "http://192.168.1.13/medic/index.php?updateData&userid="+userid+"&email="+em+"&speciality="+sp+"&country="+ctry+"&region="+reg+"&structure="+struc;
                String urlWithoutSpaces = url.replaceAll("\\s+","");
                // AsyncUpdateClass asyncRequestObject = new AsyncUpdateClass();
                // asyncRequestObject.execute(serverUrl, em, sp, ctry, reg, struc);
                new AsyncUpdateClass().execute(urlWithoutSpaces);

            }
        } );

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

    //Code needed to edit a users info
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Intent i = new Intent(Description.this, MainActivity.class);
        //	startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    } */



    //Private class to get all  from the database
    private class LoadUserInfo extends AsyncTask<String, Void,String> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        //Here,the makeServiceCall() method is called to get the json from url,
        //parse the JSON and add to HashMap to show the results in List View.
        @Override
        protected String doInBackground(String... params) {
            //creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            String urll = params[0];
            System.out.println("THE UUUUURRRRRRRRRRLL IS " + urll);

            // Get the string from the intent
            String jsonStr = sh.makeServiceCall(urll, ServiceHandler.GET);
            System.out.println(jsonStr);
            Log.d("Response: ", "> " + jsonStr);
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String jsonStr = result;
            if(jsonStr != null){
                try{
                    JSONArray jsonarr = new JSONArray(jsonStr);

                    //looping thro all comments
                    for(int i=0; i<jsonarr.length(); i++){

                        JSONObject c = jsonarr.getJSONObject(i);
                        editUsername.setText(c.getString("username") );
                        editEmail.setText(c.getString("email") );
                        editSpeciality.setText(c.getString("speciality") );
                        editCountry.setText(c.getString("country") );
                        editRegion.setText(c.getString("region") );
                        editStructure.setText(c.getString("structure") );
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }else{
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

        }
    }

    //Async Task to update User information in database
    private class AsyncUpdateClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
            }
            //Here,the makeServiceCall() method is called to get the json from url,
            //parse the JSON and add to HashMap to show the results in List View.
            @Override
            protected String doInBackground(String... params) {
                //creating service handler class instance
                ServiceHandler sh = new ServiceHandler();
                String urll = params[0];
                System.out.println("THE UUUUURRRRRRRRRRLL IS " + urll);

                // Get the string from the intent
                String jsonStr = sh.makeServiceCall(urll, ServiceHandler.GET);
                System.out.println(jsonStr);
                Log.d("Response: ", "> " + jsonStr);
                return jsonStr;
            }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("Resulted Value: " + result);
            if(result.equals("") || result == null){
                Toast.makeText(getActivity(), "Server connection failed", Toast.LENGTH_LONG).show();
                return;
            }

            JSONObject jsonResults = returnParsedJsonObject(result);
            try {
                int jsonResult = jsonResults.getInt("success");
                //int userid = jsonResults.getInt("id");

                if (jsonResult == 0) {
                    Toast.makeText(getActivity(), "Errors Found", Toast.LENGTH_LONG).show();
                    return;
                }
                if (jsonResult == 1) {
                    Toast.makeText(getActivity(), "You have successfully updated your information", Toast.LENGTH_LONG).show();
                    return;
                }
            }catch (Exception e){
                System.out.println("Json Exception Here ");
            }
        }
        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            try {
                while ((rLine = br.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return answer;
        }
    }
    private JSONObject returnParsedJsonObject(String result){
        JSONObject resultObject = null;
        int returnedResult = 0;
        try {
            resultObject = new JSONObject(result);
            returnedResult = resultObject.getInt("success");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultObject;
    }

}
