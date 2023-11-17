package com.example.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class RegisterActivity extends AppCompatActivity {

    private View pbLogin;
    private View lvForm;
    private TextView tvLoad;

    EditText etName, etPassword, etMail, etRepassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        pbLogin=findViewById(R.id.pbLogin);
        lvForm =findViewById(R.id.lvForm);
        tvLoad =findViewById(R.id.tvLoad);

        etName = findViewById(R.id.etName);
        etMail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);
        etRepassword = findViewById(R.id.etRepassword);
        btnRegister = findViewById(R.id.btnRegister);

        tvLoad.setText("Registering user... Please wait...");
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString().trim().isEmpty() ||
                        etPassword.getText().toString().trim().isEmpty()||
                        etRepassword.getText().toString().trim().isEmpty()||
                        etMail.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(etPassword.getText().toString().trim().equals(etRepassword.getText().toString().trim()))
                    {
                        String name= etName.getText().toString().trim();
                        String email = etMail.getText().toString().trim();
                        String password = etPassword.getText().toString().trim();

                        BackendlessUser user = new BackendlessUser();
                        user.setEmail(email);
                        user.setPassword(password);
                        user.setProperty("name",name);

                        showProgress(true);

                        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse(BackendlessUser response) {
                                showProgress(false);
                                // as we coming in register activity form login activity
                                // so when we will finish this we will go back to login activity
                                Toast.makeText(RegisterActivity.this, "User Successfully Register", Toast.LENGTH_SHORT).show();
                                RegisterActivity.this.finish();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(RegisterActivity.this, "ERROR: "+fault.getMessage(), Toast.LENGTH_SHORT).show();
                                showProgress(false);
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Please make sure that your password and retype password in same.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            lvForm.setVisibility(show ? View.GONE : View.VISIBLE);
            lvForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    lvForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            pbLogin.setVisibility(show ? View.VISIBLE : View.GONE);
            pbLogin.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    pbLogin.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            pbLogin.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            lvForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}