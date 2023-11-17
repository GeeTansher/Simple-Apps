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

public class NewContact extends AppCompatActivity {

    private View pblogin;
    private View lvform;
    private TextView tvload;

    EditText etname,etnumber,etmail;
    Button btnnewcontact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        pblogin=findViewById(R.id.pbLogin);
        lvform=findViewById(R.id.lvForm);
        tvload =findViewById(R.id.tvLoad);

        etname = findViewById(R.id.etname);
        etnumber = findViewById(R.id.etphone);
        etmail = findViewById(R.id.etmail);
        btnnewcontact = findViewById(R.id.btnnewcontact);

        btnnewcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etname.getText().toString().trim().isEmpty() ||
                        etnumber.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(NewContact.this, "Please enter required fields.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String name = etname.getText().toString().trim();
                    String number = etnumber.getText().toString().trim();
                    String mail;
                    if(!etmail.getText().toString().trim().isEmpty())
                    {
                        mail = etmail.getText().toString().trim();
                    }
                    else
                    {
                        mail = null;
                    }

                    Contact contact = new Contact();
                    contact.setName(name);
                    contact.setNumber(number);
                    contact.setEmail(mail);
                    contact.setUserEmail(ApplicationClass.user.getEmail());

                    showProgress(true);
                    tvload.setText("Creating new contact... Please wait...");

                    Backendless.Persistence.save(contact, new AsyncCallback<Contact>() {
                        @Override
                        public void handleResponse(Contact response) {
                            Toast.makeText(NewContact.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                            showProgress(false);
                            NewContact.this.finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(NewContact.this, "ERROR:"+fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

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