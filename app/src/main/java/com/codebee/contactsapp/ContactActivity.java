package com.codebee.contactsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {

    private static final int EDIT_CONTACT = 1;
    private Contact contact;
    public static final int REQUEST_PHONE_CALL = 1;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contact = (Contact) getIntent().getSerializableExtra("contact");
        displayData();

        (findViewById(R.id.contact_mobile_text)).setOnClickListener(onContactClick);
        (findViewById(R.id.contact_work_text)).setOnClickListener(onContactClick);
        (findViewById(R.id.contact_custom1_text)).setOnClickListener(onContactClick);
        (findViewById(R.id.contact_custom2_text)).setOnClickListener(onContactClick);
        (findViewById(R.id.contact_custom3_text)).setOnClickListener(onContactClick);

        findViewById(R.id.contact_edit_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editContact();
            }
        });

        findViewById(R.id.contact_menu_image).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(ContactActivity.this,v, Gravity.END);
                menu.getMenuInflater().inflate(R.menu.menu_contact_options, menu.getMenu());

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.contact_option_edit : editContact();
                            break;
                            case R.id.contact_option_delete : deleteContact();
                            break;
                            case R.id.contact_option_share : shareContact();
                            break;
                        }
                        return true;
                    }
                });

                menu.show();
            }
        });

        exit();
    }

    private void deleteContact(){
        DatabaseHelper db = new DatabaseHelper(ContactActivity.this);
        if(db.deleteData(contact.getId())){
            Toast.makeText(getApplicationContext(),"Contact deleted successfully!",Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(getApplicationContext(),"Unable to delete contact!",Toast.LENGTH_LONG).show();
        }
    }

    private void shareContact(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT,contact.getFname() + " " + contact.getLName());
        String message = "Name: " + contact.getFname() ;
        if(!contact.getLName().isEmpty()){
            message = message + " " + contact.getLName();
        }
        message = message + "\nMobile: " + contact.getMobile() + "\n";
        if(contact.getWork() != 0){
            message = message + "Work: " + contact.getWork() + "\n";
        }
        if(!contact.getEmail().isEmpty()){
            message = message + "Email: " + contact.getEmail() + "\n";
        }
        if(contact.getCustom1() != 0){
            message = message + "Custom1: " + contact.getCustom1() + "\n";
        }
        if(contact.getCustom2() != 0){
            message = message + "Custom2: " + contact.getCustom2() + "\n";
        }
        if(contact.getCustom3() != 0){
            message = message + "Custom3: " + contact.getCustom3() + "\n";
        }
        i.putExtra(Intent.EXTRA_TEXT,message);
        startActivity(Intent.createChooser(i,"Share contact"));

    }

    private void editContact() {
        Intent i = new Intent(ContactActivity.this, EditContactActivity.class);
        i.putExtra("contact", contact);
        startActivityForResult(i, EDIT_CONTACT);
    }

    private View.OnClickListener onContactClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            callContact(Long.parseLong(((TextView) v).getText().toString()));
        }
    };

    @SuppressLint("SetTextI18n")
    private void displayData() {

        (findViewById(R.id.contact_email_text)).setVisibility(View.GONE);
        (findViewById(R.id.contact_email_label)).setVisibility(View.GONE);
        (findViewById(R.id.contact_work_text)).setVisibility(View.GONE);
        (findViewById(R.id.contact_work_label)).setVisibility(View.GONE);
        (findViewById(R.id.contact_custom1_text)).setVisibility(View.GONE);
        (findViewById(R.id.contact_custom1_label)).setVisibility(View.GONE);
        (findViewById(R.id.contact_custom2_text)).setVisibility(View.GONE);
        (findViewById(R.id.contact_custom2_label)).setVisibility(View.GONE);
        (findViewById(R.id.contact_custom3_text)).setVisibility(View.GONE);
        (findViewById(R.id.contact_custom3_label)).setVisibility(View.GONE);

        ((TextView) findViewById(R.id.contact_name_text)).setText(contact.getFname() + " " + contact.getLName());
        ((TextView) findViewById(R.id.contact_mobile_text)).setText(String.valueOf(contact.getMobile()));

        if (!(contact.getEmail().isEmpty())) {
            (findViewById(R.id.contact_email_text)).setVisibility(View.VISIBLE);
            (findViewById(R.id.contact_email_label)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_email_text)).setText(contact.getEmail());
        }

        if (contact.getWork() != 0) {
            (findViewById(R.id.contact_work_text)).setVisibility(View.VISIBLE);
            (findViewById(R.id.contact_work_label)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_work_text)).setText(String.valueOf(contact.getWork()));
        }

        if (contact.getCustom1() != 0) {
            (findViewById(R.id.contact_custom1_text)).setVisibility(View.VISIBLE);
            (findViewById(R.id.contact_custom1_label)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_custom1_text)).setText(String.valueOf(contact.getCustom1()));
        }

        if (contact.getCustom2() != 0) {
            (findViewById(R.id.contact_custom2_text)).setVisibility(View.VISIBLE);
            (findViewById(R.id.contact_custom2_label)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.contact_custom2_text)).setText(String.valueOf(contact.getCustom2()));
        }

        if (contact.getCustom3() != 0) {
            (findViewById(R.id.contact_custom3_text)).setVisibility(View.VISIBLE);
            (findViewById(R.id.contact_custom3_label)).setVisibility(View.VISIBLE);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PHONE_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_CONTACT) {
            if (resultCode == RESULT_OK && data != null) {
                contact = (Contact) data.getSerializableExtra("contact");
                displayData();
            }
        }
    }
}
