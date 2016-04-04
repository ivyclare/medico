package com.example.ivoline.medicos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Ivoline-Clarisse on 3/5/2016.
 */
public class ForgotPassword2  extends ActionBarActivity {
    private final String serverUrl = "http://192.168.43.188/medic/index.php";

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);

        Bundle extras = getIntent().getExtras();
        String email = extras.getString("EMAIL");
        int codes = extras.getInt("CODE");
        String code = Integer.toString(codes);

        System.out.println("THE EMAIL AND CODE IS FORGOT PASSWORD 2 IS"+email+"and"+code);

            AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword2.this);
            LayoutInflater inflater = ForgotPassword2.this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.dialog2, null);
            builder.setView(dialogView);

            final EditText enteredCode = (EditText) dialogView.findViewById(R.id.dialogcode);
            final EditText newPassword = (EditText) dialogView.findViewById(R.id.dialognewpassword);

            builder.setTitle("Enter Code");
            // builder.setMessage("Enter login details below");

            builder.setPositiveButton(R.string.enter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //Toast.makeText(this, "Delete Succesful",Toast.LENGTH_SHORT).show();

                            //String verifyemail = email;

                            //************Call an asynctask here to check if entered code are the same in database


                            //**********Call an asynctask to get the new password and update it
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
    }
}
