package com.codebee.contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {

    private Contact contact;
    public static final int REQUEST_PHONE_CALL = 1;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contact = (Contact) getIntent().getSerializableExtra("contact");
        displayData();

        ((TextView) findViewById(R.id.contact_mobile_text)).setOnClickListener(onContactClick);
        ((TextView) findViewById(R.id.contact_work_text)).setOnClickListener(onContactClick);
        ((TextView) findViewById(R.id.contact_custom1_text)).setOnClickListener(onContactClick);
        ((TextView) findViewById(R.id.contact_custom2_text)).setOnClickListener(onContactClick);
        ((TextView) findViewById(R.id.contact_custom3_text)).setOnClickListener(onContactClick);

        exit();
    }

    private View.OnClickListener onContactClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            callContact(Long.parseLong(((TextView) v).getText().toString()));
        }
    };

    @SuppressLint("SetTextI18n")
    private void displayData() {

        ((TextView) findViewById(R.id.contact_email_text)).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.contact_email_label)).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.contact_work_text)).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.contact_work_label)).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.contact_custom1_text)).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.contact_custom1_label)).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.contact_custom2_text)).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.contact_custom2_label)).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.contact_custom3_text)).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.contact_custom3_label)).setVisibility(View.GONE);

        ((TextView) findViewById(R.id.contact_name_text)).setText(contact.getFname() + " " + contact.getLName());
        ((TextView) findViewById(R.id.contact_mobile_text)).setText(String.valueOf(contact.getMobile()));

        if (!(contact.getEmail().isEmpty())) {
            ((TextView) findViewById(R.id.contact_email_text)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_email_label)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_email_text)).setText(contact.getEmail());
        }

        if (contact.getWork() != 0) {
            ((TextView) findViewById(R.id.contact_work_text)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_work_label)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_work_text)).setText(String.valueOf(contact.getWork()));
        }

        if (contact.getCustom1() != 0) {
            ((TextView) findViewById(R.id.contact_custom1_text)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_custom1_label)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_custom1_text)).setText(String.valueOf(contact.getCustom1()));
        }

        if (contact.getCustom2() != 0) {
            ((TextView) findViewById(R.id.contact_custom2_text)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_custom2_label)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_custom2_text)).setText(String.valueOf(contact.getCustom2()));
        }

        if (contact.getCustom3() != 0) {
            ((TextView) findViewById(R.id.contact_custom3_text)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_custom3_label)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_custom3_text)).setText(String.valueOf(contact.getCustom3()));
        }
    }

    private void exit() {
        findViewById(R.id.contact_back_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void callContact(long number) {
        intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ContactActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                startActivity(intent);
            }
        } else {
            startActivity(intent);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                } else {
                }
                return;
            }
        }
    }
}
