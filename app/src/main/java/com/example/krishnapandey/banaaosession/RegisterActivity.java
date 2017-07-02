package com.example.krishnapandey.banaaosession;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "Ankit";

    private EditText input_name;
    private EditText input_phone_no;
    private EditText input_usercode;
    private EditText input_email;
    private EditText input_password;
    private EditText input_re_password;

    private Button btn_register;
    private TextView link_signin;
    //Firebase authentication
    private FirebaseAuth mAuth;

    //for Database storage
    private DatabaseReference databaseReference;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        input_name= (EditText) findViewById(R.id.input_name);
        input_phone_no = (EditText) findViewById(R.id.input_phone_no);
        input_usercode = (EditText) findViewById(R.id.input_usercode);
        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        input_re_password = (EditText) findViewById(R.id.input_re_password);

        btn_register = (Button) findViewById(R.id.btn_register);
        link_signin = (TextView) findViewById(R.id.link_signin);

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = input_password.getText().toString().trim();
                String repassword = input_re_password.getText().toString().trim();
                if (!password.equals(repassword)) {
                    input_password.setText("");
                    input_re_password.setText("");
                    input_password.setError("password does not matched");
                    input_password.requestFocus();
                    return;
                }
                registerUser();
            }
        });
        link_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
    private void registerUser() {
        String name = input_name.getText().toString().trim();
        String phone = input_phone_no.getText().toString().trim();
        String usercode = input_usercode.getText().toString().trim();
        String email = input_email.getText().toString().trim();
        String password = input_password.getText().toString().trim();
        String repassword = input_re_password.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            input_name.setError("Enter your full name");
            Toast.makeText(this, "enter email", Toast.LENGTH_SHORT).show();
            input_name.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            input_phone_no.setError("Enter the contact no");
            Toast.makeText(this, "enter password", Toast.LENGTH_SHORT).show();
            input_phone_no.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(usercode)) {
            input_usercode.setError("Enter the usercode");
            Toast.makeText(this, "enter email", Toast.LENGTH_SHORT).show();
            input_usercode.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            input_email.setError("Enter the email");
            Toast.makeText(this, "enter email", Toast.LENGTH_SHORT).show();
            input_email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            input_password.setError("Enter the password");
            Toast.makeText(this, "enter password", Toast.LENGTH_SHORT).show();
            input_password.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(repassword)) {
            input_re_password.setError("Re-Enter password");
            Toast.makeText(this, "enter password", Toast.LENGTH_SHORT).show();
            input_re_password.requestFocus();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Registration failed",
                                    Toast.LENGTH_SHORT).show();
                                    progressDialog.hide();
                        } else {
                            startActivity(new Intent(RegisterActivity.this,ProfileActivity.class));
                            saveUserInformation();
                            finish();
                        }

                        // ...
                    }
                });
    }

    private void saveUserInformation() {
        String name = input_name.getText().toString().trim();
        String phone = input_phone_no.getText().toString().trim();
        String usercode = input_usercode.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name, phone, usercode);

        FirebaseUser user = mAuth.getCurrentUser();
        //databaseReference.child(user.getUid()).setValue(userInformation);
        databaseReference.child(user.getUid()).push().setValue(userInformation);
        Toast.makeText(this, "data saved", Toast.LENGTH_SHORT).show();
    }

}

