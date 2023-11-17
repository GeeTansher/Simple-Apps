package com.example.contactsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class ContactInfo extends AppCompatActivity {

    TextView tvChar,tvName,tvNumber;
    ImageView ivcall,ivmail,ivedit,ivdelete;
    EditText etName,etNumber,etMail;
    Button btnsubmit;

    private View pblogin;
    private View lvform;
    private TextView tvload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        tvChar = findViewById(R.id.tvChar);
        tvName = findViewById(R.id.tvName);
        tvNumber = findViewById(R.id.tvNumber);

        ivcall = findViewById(R.id.ivcall);
        ivmail = findViewById(R.id.ivmail);
        ivedit = findViewById(R.id.ivedit);
        ivdelete = findViewById(R.id.ivdelete);

        etName = findViewById(R.id.etName);
        etMail = findViewById(R.id.etMail);
        etNumber = findViewById(R.id.etNumber);

        btnsubmit = findViewById(R.id.btnsubmit);

        pblogin=findViewById(R.id.pbLogin);
        lvform=findViewById(R.id.lvForm);
        tvload =findViewById(R.id.tvLoad);

        etNumber.setVisibility(View.GONE);
        etMail.setVisibility(View.GONE);
        etName.setVisibility(View.GONE);
        btnsubmit.setVisibility(View.GONE);

        final Boolean[] edit = {false};
        final int index = getIntent().getIntExtra("index",0);

        etName.setText(ApplicationClass.contacts.get(index).getName());
        etMail.setText(ApplicationClass.contacts.get(index).getEmail());
        etNumber.setText(ApplicationClass.contacts.get(index).getNumber());

        tvChar.setText(ApplicationClass.contacts.get(index).getName().toUpperCase().charAt(0)+"");
        tvName.setText(ApplicationClass.contacts.get(index).getName());
        tvNumber.setText(ApplicationClass.contacts.get(index).getNumber());

        ivcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uri = "tel:" + ApplicationClass.contacts.get(index).getNumber();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });

        ivmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ApplicationClass.contacts.get(index).getEmail() != null &&
                        ApplicationClass.contacts.get(index).getEmail() != "") {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/html");
                    intent.putExtra(Intent.EXTRA_EMAIL, ApplicationClass.contacts.get(index).getEmail());
                    startActivity(Intent.createChooser(intent, "Send mail to " +
                            ApplicationClass.contacts.get(index).getName()));
                }
                else
                {
                    Toast.makeText(ContactInfo.this, "Sorry but we don't have any " +
                            "email registered for this user.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if false and clicked then change to true and vice versa.
                edit[0] = !edit[0];

                if(edit[0])
                {
                    etNumber.setVisibility(View.VISIBLE);
                    etMail.setVisibility(View.VISIBLE);
                    etName.setVisibility(View.VISIBLE);
                    btnsubmit.setVisibility(View.VISIBLE);
                }
                else
                {
                    etNumber.setVisibility(View.GONE);
                    etMail.setVisibility(View.GONE);
                    etName.setVisibility(View.GONE);
                    btnsubmit.setVisibility(View.GONE);
                }

            }
        });

        ivdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(ContactInfo.this);
                dialog.setMessage("Are you sure you want to delete the contact?");
                dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showProgress(true);
                        tvload.setText("Deleting contact... Please wait...");

                        Backendless.Persistence.of(Contact.class).remove(ApplicationClass.contacts.get(index),
                                new AsyncCallback<Long>() {
                                    @Override
                                    public void handleResponse(Long response) {
                                        ApplicationClass.contacts.remove(index);
                                        Toast.makeText(ContactInfo.this, "Contact " +
                                                "successfully removed!", Toast.LENGTH_SHORT).show();
                                        setResult(RESULT_OK);
                                        ContactInfo.this.finish();
                                    }

                                    @Override
                                    public void handleFault(BackendlessFault fault) {
                                        Toast.makeText(ContactInfo.this, "ERROR:"+
                                                fault.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString().trim().isEmpty() ||
                        etNumber.getText().toString().trim().isEmpty() )
                {
                    Toast.makeText(ContactInfo.this, "Please enter required fields.",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ApplicationClass.contacts.get(index).setName(etName.getText().toString().trim());
                    ApplicationClass.contacts.get(index).setNumber(etNumber.getText().toString().trim());
                    if(etMail.getText().toString().trim().isEmpty())
                    {
                        ApplicationClass.contacts.get(index).setEmail("");
                    }
                    else {
                        ApplicationClass.contacts.get(index).setEmail(etMail.getText().toString().trim());
                    }
                    showProgress(true);
                    tvload.setText("Updating contact.... Please wait...");
                    Backendless.Persistence.save(ApplicationClass.contacts.get(index), new AsyncCallback<Contact>() {
                        @Override
                        public void handleResponse(Contact response) {
                            tvChar.setText(ApplicationClass.contacts.get(index).getName().toUpperCase().charAt(0)+"");
                            tvName.setText(ApplicationClass.contacts.get(index).getName());
                            tvNumber.setText(ApplicationClass.contacts.get(index).getNumber());
                            Toast.makeText(ContactInfo.this, "Contact successfully updated!", Toast.LENGTH_SHORT).show();
                            showProgress(false);
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(ContactInfo.this, "ERROR:" + fault.getMessage(), Toast.LENGTH_SHORT).show();
                            showProgress(false);
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