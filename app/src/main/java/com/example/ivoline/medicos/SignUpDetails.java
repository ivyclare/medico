package com.example.ivoline.medicos;

    import android.content.Intent;

    import android.os.AsyncTask;

    import android.support.v7.app.ActionBarActivity;

    import android.os.Bundle;

    import android.view.Menu;

    import android.view.MenuItem;

    import android.view.View;

    import android.widget.Button;

    import android.widget.EditText;

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

    import org.json.JSONException;

    import org.json.JSONObject;

    import java.io.BufferedReader;

    import java.io.IOException;

    import java.io.InputStream;

    import java.io.InputStreamReader;

    import java.util.ArrayList;

    import java.util.List;

    public class SignUpDetails extends ActionBarActivity {

        protected EditText username;

        private EditText password;

        private EditText email;

        private EditText speciality;

        private EditText country;

        private EditText region;

        private EditText structure;

        protected String enteredUsername;

        private final String serverUrl = "http://192.168.1.13/medic/index.php";

        @Override

        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            setContentView(R.layout.signup);

            username = (EditText)findViewById(R.id.username_field);

            password = (EditText)findViewById(R.id.password_field);

            email = (EditText)findViewById(R.id.email_field);

            speciality = (EditText)findViewById(R.id.speciality);

            country = (EditText)findViewById(R.id.country);

            region = (EditText)findViewById(R.id.region);

            structure = (EditText)findViewById(R.id.structure);

            Button signUpButton = (Button)findViewById(R.id.sign_up);

            signUpButton.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View v) {

                    enteredUsername = username.getText().toString();

                    String enteredPassword = password.getText().toString();

                    String enteredEmail = email.getText().toString();

                    String enteredSpeciality = speciality.getText().toString();

                    String enteredCountry = country.getText().toString();

                    String enteredRegion = region.getText().toString();

                    String enteredStructure = structure.getText().toString();

                    if(enteredUsername.equals("") || enteredPassword.equals("") || enteredEmail.equals("")){

                        Toast.makeText(SignUpDetails.this, "Username or password or email must be filled", Toast.LENGTH_LONG).show();

                        return;

                    }

                    if(enteredUsername.length() <= 1 || enteredPassword.length() <= 1){

                        Toast.makeText(SignUpDetails.this, "Username or password length must be greater than one", Toast.LENGTH_LONG).show();

                        return;

                    }

                // request authentication with remote server4

                    AsyncDataClass asyncRequestObject = new AsyncDataClass();

                    asyncRequestObject.execute(serverUrl, enteredUsername, enteredPassword, enteredEmail,enteredSpeciality, enteredCountry, enteredRegion,enteredStructure);

                }

            });

        }

        @Override

        public boolean onCreateOptionsMenu(Menu menu) {

            // Inflate the menu; this adds items to the action bar if it is present.

            getMenuInflater().inflate(R.menu.menu_main, menu);

            return true;

        }

        @Override

        public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will

        // automatically handle clicks on the Home/Up button, so long

        // as you specify a parent activity in AndroidManifest.xml.

            int id = item.getItemId();

        //noinspection SimplifiableIfStatement

            if (id == R.id.action_settings) {

                return true;

            }

            return super.onOptionsItemSelected(item);

        }

        private class AsyncDataClass extends AsyncTask<String, Void, String> {

            @Override

            protected String doInBackground(String... params) {

                HttpParams httpParameters = new BasicHttpParams();

                HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);

                HttpConnectionParams.setSoTimeout(httpParameters, 5000);

                HttpClient httpClient = new DefaultHttpClient(httpParameters);

                HttpPost httpPost = new HttpPost(params[0]);

                String jsonResult = "";

                try {

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);

                    nameValuePairs.add(new BasicNameValuePair("username", params[1]));

                    nameValuePairs.add(new BasicNameValuePair("password", params[2]));

                    nameValuePairs.add(new BasicNameValuePair("email", params[3]));

                    nameValuePairs.add(new BasicNameValuePair("speciality", params[4]));

                    nameValuePairs.add(new BasicNameValuePair("country", params[5]));

                    nameValuePairs.add(new BasicNameValuePair("region", params[6]));

                    nameValuePairs.add(new BasicNameValuePair("structure", params[7]));

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    jsonResult = inputStreamToString(response.getEntity().getContent()).toString();

                    System.out.println("THE JSON RESULT IS"+jsonResult);

                    System.out.println("Returned Json object " + jsonResult.toString());

                } catch (ClientProtocolException e) {

                    e.printStackTrace();

                } catch (IOException e) {

                    e.printStackTrace();

                }

                return jsonResult;

            }

            @Override

            protected void onPreExecute() {

                super.onPreExecute();

            }

            @Override

            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                System.out.println("Resulted Value: " + result);

                if(result.equals("") || result == null){

                    Toast.makeText(SignUpDetails.this, "Server connection failed", Toast.LENGTH_LONG).show();

                    return;

                }

                int jsonResult = returnParsedJsonObject(result);

                if(jsonResult == 0){

                    Toast.makeText(SignUpDetails.this, "Invalid Value Entered", Toast.LENGTH_LONG).show();

                    return;

                }

                if(jsonResult == 1){

                    Intent intent = new Intent(SignUpDetails.this, LandPage.class);

                    intent.putExtra("USERNAME", enteredUsername);

                    intent.putExtra("MESSAGE", "You have been successfully Registered");

                    startActivity(intent);

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

        private int returnParsedJsonObject(String result){

            JSONObject resultObject = null;

            int returnedResult = 0;

            try {
                System.out.println("EntersTry");

                resultObject = new JSONObject(result);

                returnedResult = resultObject.getInt("success");

                System.out.println("THE VALUE FROM JSON IS "+returnedResult);

            } catch (JSONException e) {

                e.printStackTrace();

            }

            return returnedResult;

        }

    }
