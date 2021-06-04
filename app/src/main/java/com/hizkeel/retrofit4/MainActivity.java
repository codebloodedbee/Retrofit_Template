package com.hizkeel.retrofit4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONException;
import org.json.JSONObject;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    LoginResponse loginResponsesData;
    EditText emailId, password, name;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // init the EditText and Button

        emailId = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signUp = (Button) findViewById(R.id.signUp);
        // implement setOnClickListener event on sign up Button
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // validate the fields and call sign method to implement the api
                if (validateEmail() && validate(password)) {
                    signUp();
                }
            }
        });
    }

    private boolean validateEmail() {
        String email = emailId.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            emailId.setError("Email is not valid.");
            emailId.requestFocus();
            return false;
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validate(EditText editText) {
        // check the lenght of the enter data in EditText and give error if its empty
        if (editText.getText().toString().trim().length() > 0) {
            return true; // returns true if field is not empty
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }

    private void signUp() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        // Api is a class in which we define a method getClient() that returns the API Interface class object
        // registration is a POST request type method in which we are sending our field's data
        // enqueue is used for callback response and error
        (Api
                .getClient()
                .registration("agent_login", emailId.getText().toString().trim(), password.getText().toString().trim()))
                .enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                loginResponsesData = response.body();

                LinkedTreeMap<Object,Object> t = (LinkedTreeMap) response.body().getData();

                String name = t.get("name").toString();

                Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();


//                Toast.makeText(getApplicationContext(), response.body().getData().toString(), Toast.LENGTH_SHORT).show();

                Log.i("xx1y", response.body().getData().toString());
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("response", t.getStackTrace().toString());

                Toast.makeText(getApplicationContext(), "did't work ", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
    }

}