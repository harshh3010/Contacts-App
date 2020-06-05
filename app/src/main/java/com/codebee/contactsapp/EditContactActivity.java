package com.codebee.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class EditContactActivity extends AppCompatActivity {

    private Contact contact;
    private int field_count = 0;
    private LinearLayout fieldsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        fieldsLayout = findViewById(R.id.edit_contact_fields_layout);

        contact = (Contact) getIntent().getSerializableExtra("contact");

        ((EditText) findViewById(R.id.edit_contact_fname_text)).setText(contact.getFname());
        ((EditText) findViewById(R.id.edit_contact_lname_text)).setText(contact.getLName());
        ((EditText) findViewById(R.id.edit_contact_mobile_text)).setText(String.valueOf(contact.getMobile()));
        ((EditText) findViewById(R.id.edit_contact_work_text)).setText(String.valueOf(contact.getWork()));
        ((EditText) findViewById(R.id.edit_contact_email_text)).setText(contact.getEmail());

        if (contact.getCustom1() != 0){
            field_count = 1;
            addFields(1);

            ((EditText) fieldsLayout
                    .findViewWithTag("custom_linearLayout_1")
                    .findViewWithTag("custom_editText_1"))
                    .setText(String.valueOf(contact.getCustom1()));
        }

        if (contact.getCustom2() != 0){
            field_count = 2;
            addFields(2);

            ((EditText) fieldsLayout
                    .findViewWithTag("custom_linearLayout_2")
                    .findViewWithTag("custom_editText_2"))
                    .setText(String.valueOf(contact.getCustom2()));
        }

        if (contact.getCustom3() != 0){
            field_count = 3;
            addFields(3);

            ((EditText) fieldsLayout
                    .findViewWithTag("custom_linearLayout_3")
                    .findViewWithTag("custom_editText_3"))
                    .setText(String.valueOf(contact.getCustom3()));
        }

        findViewById(R.id.edit_contact_field_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(field_count < 3){
                    addFields(++field_count);
                }else{
                    Snackbar.make(v.getRootView(), "You cannot add more fields.", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.edit_contact_back_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.edit_contact_save_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContact();
            }
        });

    }

    private void updateContact(){

        if (!((EditText) findViewById(R.id.edit_contact_fname_text)).getText().toString().trim().isEmpty() ||
                !((EditText) findViewById(R.id.edit_contact_mobile_text)).getText().toString().trim().isEmpty()) {

            contact.setFname(((EditText) findViewById(R.id.edit_contact_fname_text)).getText().toString().trim());
            contact.setLName(((EditText) findViewById(R.id.edit_contact_lname_text)).getText().toString().trim());
            contact.setEmail(((EditText) findViewById(R.id.edit_contact_email_text)).getText().toString().trim());

            if (!((EditText) findViewById(R.id.edit_contact_mobile_text)).getText().toString().isEmpty()) {
                contact.setMobile(Long.parseLong(((EditText) findViewById(R.id.edit_contact_mobile_text)).getText().toString()));
            }

            if (!((EditText) findViewById(R.id.edit_contact_work_text)).getText().toString().isEmpty()) {
                contact.setWork(Long.parseLong(((EditText) findViewById(R.id.edit_contact_work_text)).getText().toString()));
            }

            if (fieldsLayout.findViewWithTag("custom_linearLayout_1") != null) {
                if (!((EditText) fieldsLayout
                        .findViewWithTag("custom_linearLayout_1")
                        .findViewWithTag("custom_editText_1"))
                        .getText().toString().isEmpty()) {
                    contact.setCustom1(Long.parseLong(
                            ((EditText) fieldsLayout
                                    .findViewWithTag("custom_linearLayout_1")
                                    .findViewWithTag("custom_editText_1"))
                                    .getText().toString()
                    ));
                }
            }

            if (fieldsLayout.findViewWithTag("custom_linearLayout_2") != null) {
                if (!((EditText) fieldsLayout
                        .findViewWithTag("custom_linearLayout_2")
                        .findViewWithTag("custom_editText_2"))
                        .getText().toString().isEmpty()) {
                    contact.setCustom1(Long.parseLong(
                            ((EditText) fieldsLayout
                                    .findViewWithTag("custom_linearLayout_2")
                                    .findViewWithTag("custom_editText_2"))
                                    .getText().toString()
                    ));
                }
            }

            if (fieldsLayout.findViewWithTag("custom_linearLayout_3") != null) {
                if (!((EditText) fieldsLayout
                        .findViewWithTag("custom_linearLayout_3")
                        .findViewWithTag("custom_editText_3"))
                        .getText().toString().isEmpty()) {
                    contact.setCustom1(Long.parseLong(
                            ((EditText) fieldsLayout
                                    .findViewWithTag("custom_linearLayout_3")
                                    .findViewWithTag("custom_editText_3"))
                                    .getText().toString()
                    ));
                }
            }

            DatabaseHelper db = new DatabaseHelper(EditContactActivity.this);
            if(db.updateData(contact)){
                Toast.makeText(getApplicationContext(),"Contact updated successfully!",Toast.LENGTH_SHORT).show();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("contact",contact);
                setResult(RESULT_OK,returnIntent);
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"Unable to update contact!",Toast.LENGTH_SHORT).show();
            }


            db.close();

        } else {
            if (((EditText) findViewById(R.id.edit_contact_fname_text)).getText().toString().trim().isEmpty())
                ((EditText) findViewById(R.id.edit_contact_fname_text)).setError("This field cannot be left empty.");

            if (((EditText) findViewById(R.id.edit_contact_mobile_text)).getText().toString().trim().isEmpty())
                ((EditText) findViewById(R.id.edit_contact_mobile_text)).setError("This field cannot be left empty.");
        }

    }

    @SuppressLint("SetTextI18n")
    private void addFields(int i) {

        LinearLayout linearLayout = new LinearLayout(EditContactActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(dpToPx(8), dpToPx(16), dpToPx(8), 0);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setWeightSum(5);
        linearLayout.setTag("custom_linearLayout_" + i);
        fieldsLayout.addView(linearLayout);

        TextView textView = new TextView(EditContactActivity.this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));
        textView.setText("Custom " + i);
        textView.setTextColor(Color.parseColor("#007BFF"));
        textView.setTextSize(12);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/circular_std_book.ttf");
        textView.setTypeface(font, Typeface.BOLD);
        textView.setTag("custom_textView_" + i);

        EditText editText = new EditText(EditContactActivity.this);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                4
        ));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setBackground(getDrawable(R.drawable.bg_edittext_blue));
        }
        editText.setPadding(dpToPx(10), dpToPx(10), dpToPx(10), dpToPx(10));
        editText.setHint("Enter contact here");
        editText.setTypeface(font);
        editText.setTextSize(16);
        editText.setTextColor(Color.parseColor("#007BFF"));
        editText.setHintTextColor(Color.parseColor("#97007BFF"));
        editText.setTag("custom_editText_" + i);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        linearLayout.addView(textView);
        linearLayout.addView(editText);

    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
