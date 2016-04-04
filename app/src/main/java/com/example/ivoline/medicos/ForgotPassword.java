package com.example.ivoline.medicos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.Random;

/**
 * Created by Ivoline-Clarisse on 3/4/2016.
 */
public class ForgotPassword  extends ActionBarActivity {
    private final String serverUrl = "http://192.168.1.13/medic/index.php";

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);

        Button enter = (Button)findViewById(R.id.enter);

        //Action to be performed when login button is pressed
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText enteredEmail = (EditText) findViewById(R.id.enteredemail);

                String sendemail = enteredEmail.getText().toString();

                Bundle extras = getIntent().getExtras();
                int codes = extras.getInt("CODE");
                String code = Integer.toString(codes);
                System.out.println("THE CODE IN FORGOT PASSWORD IS " + code);
                if(sendemail != null || sendemail!="") {

                    //Insert code in database
                    new EnterCode().execute("http://192.168.56.1/medic/index.php?insertCode&code=" + code + "&email=" + sendemail);
                    //Send code to users email
                    new SendMail().execute(sendemail, code);
                }else{
                    Toast.makeText(ForgotPassword.this, "Please enter your email.", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private class SendMail extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           progressDialog = ProgressDialog.show(ForgotPassword.this, "Please wait", "Sending mail", true, false);
        }

        protected String doInBackground(String... params) {
            String email = params[0];
            String code = params[1];

            //System.out.println("THE EMAIL IS "+email+"AND CODE IS"+code);

            Mail m = new Mail("ingongmegasoft@gmail.com", "CANADA22");

            String[] toArr = {email};
            m.setTo(toArr);
            m.setFrom("ingongmegasoft@gmail.com");
            m.setSubject("Notification from Medico App");
            m.setBody("Enter this verification code inorder to change your password. The code is " + code);

            try {
                // m.addAttachment("/sdcard/filelocation");

                if(m.send()) {
                    Toast.makeText(ForgotPassword.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ForgotPassword.this, "Email was not sent.", Toast.LENGTH_LONG).show();
                }
            } catch(Exception e) {
                Toast.makeText(ForgotPassword.this, "Invalid Email", Toast.LENGTH_LONG).show();
                //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                Log.e("ForgotPassword", "Could not send email", e);
            }
            return email;
        }

        @Override
        protected void onPostExecute(String emails) {
            //super.onPostExecute(aVoid);
            progressDialog.dismiss();
            final String email = emails;
            Bundle extras = getIntent().getExtras();
            final int code = extras.getInt("CODE");

            AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);
            LayoutInflater inflater = ForgotPassword.this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.dialog2, null);
            builder.setView(dialogView);

            final EditText enteredCode = (EditText) dialogView.findViewById(R.id.dialogcode);
            final EditText newPassword = (EditText) dialogView.findViewById(R.id.dialognewpassword);
            final String newPass = newPassword.toString();

            builder.setTitle("Enter Code");
            // builder.setMessage("Enter login details below");

            builder.setPositiveButton(R.string.enter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
              //Toast.makeText(this, "Delete Succesful",Toast.LENGTH_SHORT).show()
              String url = "http://192.168.56.1/medic/index.php?checkCode&code=" + enteredCode + "&email=" + email;
              //Call an asynctask here to check if entered code are the same in database
              new CheckCode().execute(url, newPass,email);

              // **********Call an asynctask to get the new password and update it


              /*
                    enteredUsername = username.getText().toString();

                    String enteredPassword = password.getText().toString();
                    if (enteredUsername.equals("") || enteredPassword.equals("")) {

                        Toast.makeText(HomePage.this, "Username or password must be filled", Toast.LENGTH_LONG).show();

                        return;
                    }

                    if (enteredUsername.length() <= 1 || enteredPassword.length() <= 1) {
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
                    return; */
                        }
                    }
            );

            AlertDialog d = builder.create();
            d.setIcon(android.R.drawable.ic_menu_edit);
            d.show();

            /*Intent intents = new Intent(getApplicationContext(), ForgotPassword.class);
            intents.putExtra("EMAIL",email);
            intents.putExtra("CODE", code);
            startActivity(intents);*/
        }
    }

    //Async Task to Enter code sent to user in forgot password table
    private class EnterCode extends AsyncTask<String, Void, String> {
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
                System.out.println("SERVER CONNECTION FAILED");
                return;
            }

            JSONObject jsonResults = returnParsedJsonObject(result);
            try {
                int jsonResult = jsonResults.getInt("success");
                //int userid = jsonResults.getInt("id");

                if (jsonResult == 0) {
                    System.out.println("ERRORS FOUND");
                    return;
                }
                if (jsonResult == 1) {
                    System.out.println("SUCCESSFULLY INSERTED");
                    return;
                }
            } catch (Exception e){
                System.out.println("Json Exception Here ");
            }
        }
    }


    //Async Task to Check if the code entered and the email match with that in the database
    private class CheckCode extends AsyncTask<String, Void, String> {
        public String newPasss;
        public String emaill;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            String urll = params[0];
            newPasss = params[1];
            emaill = params[2];

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
                System.out.println("SERVER CONNECTION FAILED");
                return;
            }

            JSONObject jsonResults = returnParsedJsonObject(result);
            try {
                int jsonResult = jsonResults.getInt("success");
                //int userid = jsonResults.getInt("id");

                if (jsonResult == 0) {
                    Toast.makeText(ForgotPassword.this, "You entered an Invalid code", Toast.LENGTH_LONG).show();
                    return;
                }
                if (jsonResult == 1) {
                    String pass = newPasss;
                    String em = emaill;
                    new UpdatePassword().execute("http://192.168.56.1/medic/index.php?checkCode&code=" + pass+ "&email=" + em);
                    System.out.println("SUCCESSFUL");
                    return;
                }
            } catch (Exception e){
                System.out.println("Json Exception Here ");
            }
        }
    }

    //Asyntask to get the new password and update it
    private class UpdatePassword extends AsyncTask<String, Void, String> {
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
                System.out.println("SERVER CONNECTION FAILED");
                return;
            }

            JSONObject jsonResults = returnParsedJsonObject(result);
            try {
                int jsonResult = jsonResults.getInt("success");
                //int userid = jsonResults.getInt("id");

                if (jsonResult == 0) {
                    //Toast.makeText(ForgotPassword.this, "You enterred an Invalid code", Toast.LENGTH_LONG).show();
                    return;
                }
                if (jsonResult == 1) {
                    System.out.println("Password Successf");
                    Toast.makeText(ForgotPassword.this, "Password Successfully Changed", Toast.LENGTH_LONG).show();

                    return;
                }
            } catch (Exception e){
                System.out.println("Json Exception Here ");
            }
        }
    }


    //Method gets result from json data in php code
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
