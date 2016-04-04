
package com.example.ivoline.medicos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

import android.os.AsyncTask;

import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;

import android.view.Menu;

import android.view.MenuItem;

import android.view.View;

import android.widget.Button;

import android.widget.EditText;

import android.widget.TextView;
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
import java.util.Random;


public class HomePage extends ActionBarActivity {
	Button blogin;
	Button bsignup;
	protected EditText username;
	private EditText password;
	private TextView fpassword;
	protected String enteredUsername;
	private final String serverUrl = "http://192.168.1.13/medic/index.php";

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

		username = (EditText)findViewById(R.id.dialogusername);
		password = (EditText)findViewById(R.id.dialogpassword);
		Button loginButton = (Button)findViewById(R.id.blogin);
		Button registerButton = (Button)findViewById(R.id.bsignup);

		//Action to be performed when login button is pressed
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);

				LayoutInflater inflater = HomePage.this.getLayoutInflater();
				final View dialogView = inflater.inflate(R.layout.dialog, null);
				builder.setView(dialogView);

				final EditText username = (EditText) dialogView.findViewById(R.id.dialogusername);
				final EditText password = (EditText) dialogView.findViewById(R.id.dialogpassword);

				String BLUE = "#4527a0";
				builder.setTitle("Login");
				builder.setMessage("Enter login details below");

				builder.setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						//Toast.makeText(this, "Delete Succesful",Toast.LENGTH_SHORT).show();
						enteredUsername = username.getText().toString();

						String enteredPassword = password.getText().toString();

						if (enteredUsername.equals("") || enteredPassword.equals("")) {

							Toast.makeText(HomePage.this, "Username or password must be filled", Toast.LENGTH_LONG).show();

							return;
						}

						if(enteredUsername.length() <= 1 || enteredPassword.length() <= 1){
							Toast.makeText(HomePage.this, "Username or password length must be greater than one", Toast.LENGTH_LONG).show();
							return;
						}
					// request authentication with remote server4
						AsyncDataClass asyncRequestObject = new AsyncDataClass();
						asyncRequestObject.execute(serverUrl, enteredUsername, enteredPassword);

							//Intent intent = new Intent(getApplicationContext(), LandPage.class);
							//startActivity(intent);
						}
					}

					)
					.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									//Nothing happens as user cancels dialog
									return;
								}
							}

					);

					AlertDialog d = builder.create();
					d.setTitle("Login");
					d.setIcon(android.R.drawable.ic_dialog_alert);
					d.show();
				}
			});


		//Action to be performed when sign up button is pressed
		registerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				System.out.println("SIGNUP BUTTON CLICKED");
				Intent intent = new Intent(HomePage.this, SignUpDetails.class);
				startActivity(intent);

			}
		});

		//Implementation of forgot password
		fpassword = (TextView) findViewById(R.id.fpassword);
		fpassword .setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
		fpassword.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Generating Code
				Random randomGenerator =  new Random();
				int code =  randomGenerator.nextInt(1000);
				System.out.print("CODE IS "+code);
				Intent pintent = new Intent(getApplicationContext(), ForgotPassword.class);
				pintent.putExtra("CODE",code);
				startActivity(pintent);
			}
		});

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
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("username", params[1]));
				nameValuePairs.add(new BasicNameValuePair("password", params[2]));
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpClient.execute(httpPost);
				jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
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
				Toast.makeText(HomePage.this, "Server connection failed", Toast.LENGTH_LONG).show();
				return;
			}

			JSONObject jsonResults = returnParsedJsonObject(result);
			try {
				int jsonResult = jsonResults.getInt("success");
				int userid = jsonResults.getInt("id");

				if (jsonResult == 0) {
					Toast.makeText(HomePage.this, "Invalid username or password", Toast.LENGTH_LONG).show();
					return;
				}
				if (jsonResult == 1) {
					Intent intent = new Intent(HomePage.this, LandPage.class);
					intent.putExtra("USERNAME", enteredUsername);
					intent.putExtra("USERID",userid);
					intent.putExtra("MESSAGE", "You have been successfully login");
					startActivity(intent);
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

