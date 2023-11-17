package com.example.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.BaseKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;

public class LoginActivity extends AppCompatActivity {

    private View pblogin;
    private View lvform;
    private TextView tvload;

    EditText etmail, etpassword;
    Button btnlogin,btnregister;
    TextView tvreset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        pblogin=findViewById(R.id.pbLogin);
        lvform=findViewById(R.id.lvForm);
        tvload =findViewById(R.id.tvLoad);

        etmail = findViewById(R.id.etMail);
        etpassword = findViewById(R.id.etPassword);
        btnlogin = findViewById(R.id.btnlogin);
        btnregister = findViewById(R.id.btnRegister);
        tvreset = findViewById(R.id.tvreset);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etmail.getText().toString().trim().isEmpty() ||
                        etpassword.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String email = etmail.getText().toString().trim();
                    String password = etpassword.getText().toString().trim();

                    showProgress(true);

                    Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            ApplicationClass.user = response;
                            Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            LoginActivity.this.finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                            Toast.makeText(LoginActivity.this, "ERROR:"+fault.getMessage(), Toast.LENGTH_SHORT).show();
                            showProgress(false);
                        }
                    }, true);
                }
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this,RegisterActivity.class));

            }
        });

        tvreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etmail.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String mail = etmail.getText().toString().trim();
                    showProgress(true);
                    tvload.setText("Sending you a email... Please wait...");

                    Backendless.UserService.restorePassword(mail, new AsyncCallback<Void>() {
                        @Override
                        public void handleResponse(Void response) {
                            Toast.makeText(LoginActivity.this, "A reset link has been sent to you email.", Toast.LENGTH_SHORT).show();
                            showProgress(false);
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                            Toast.makeText(LoginActivity.this, "ERROR:"+fault.getMessage(), Toast.LENGTH_LONG).show();
                            showProgress(false);
                        }
                    });
                }
            }
        });

        // these lines of code will be executed in th beg. as above are performed when button is pressed.
        showProgress(true);
        tvload.setText("Checking login credentials...");
        Backendless.UserService.isValidLogin(new AsyncCallback<Boolean>() {
            @Override
            public void handleResponse(Boolean response) {

                if(response)
                {
                    tvload.setText("Logging you in!!!");
                    String userObjectID = UserIdStorageFactory.instance().getStorage().get();

                    Backendless.Data.of(BackendlessUser.class).findById(userObjectID, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {

                            ApplicationClass.user = response;
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            LoginActivity.this.finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                            Toast.makeText(LoginActivity.this, "ERROR:"+fault.getMessage(), Toast.LENGTH_SHORT).show();
                            showProgress(false);
                        }
                    });
                }
                else {
                    showProgress(false);
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {

                Toast.makeText(LoginActivity.this, "ERROR:"+fault.getMessage(), Toast.LENGTH_LONG).show();
                showProgress(false);
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

            lvform.setVisibility(show ? View.GONE : View.VISIBLE);
            lvform.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    lvform.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            pblogin.setVisibility(show ? View.VISIBLE : View.GONE);
            pblogin.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    pblogin.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            tvload.setVisibility(show ? View.VISIBLE : View.GONE);
            tvload.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvload.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            pblogin.setVisibility(show ? View.VISIBLE : View.GONE);
            tvload.setVisibility(show ? View.VISIBLE : View.GONE);
            lvform.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}