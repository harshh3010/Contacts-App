package com.codebee.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {

    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contact = (Contact) getIntent().getSerializableExtra("contact");
        displayData();

        exit();
    }

    @SuppressLint("SetTextI18n")
    private void displayData(){

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

        if(!(contact.getEmail().isEmpty())){
            ((TextView) findViewById(R.id.contact_email_text)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_email_label)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_email_text)).setText(contact.getEmail());
        }

        if(contact.getWork() != 0){
            ((TextView) findViewById(R.id.contact_work_text)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_work_label)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_work_text)).setText(String.valueOf(contact.getWork()));
        }

        if(contact.getCustom1() != 0){
            ((TextView) findViewById(R.id.contact_custom1_text)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_custom1_label)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_custom1_text)).setText(String.valueOf(contact.getCustom1()));
        }

        if(contact.getCustom2() != 0){
            ((TextView) findViewById(R.id.contact_custom2_text)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_custom2_label)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_custom2_text)).setText(String.valueOf(contact.getCustom2()));
        }

        if(contact.getCustom3() != 0){
            ((TextView) findViewById(R.id.contact_custom3_text)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_custom3_label)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_custom3_text)).setText(String.valueOf(contact.getCustom3()));
        }
    }

    private void exit(){
        findViewById(R.id.contact_back_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
