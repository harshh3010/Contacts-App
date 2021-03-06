package com.codebee.contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

public class AddContactActivity extends AppCompatActivity {

    private RevealAnimation mRevealAnimation;
    private int field_count = 1;
    private LinearLayout fieldsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        Intent intent = this.getIntent();
        ConstraintLayout constraintLayout = findViewById(R.id.add_contact_constraint_layout);
        mRevealAnimation = new RevealAnimation(constraintLayout, intent, this);

        fieldsLayout = findViewById(R.id.add_contact_fields_layout);

        saveContact();

        addMoreFields();

        exit();

    }

    private void saveContact() {
        findViewById(R.id.add_contact_save_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!((EditText) findViewById(R.id.add_contact_fname_text)).getText().toString().trim().isEmpty() ||
                        !((EditText) findViewById(R.id.add_contact_mobile_text)).getText().toString().trim().isEmpty()) {

                    Contact contact = new Contact();
                    contact.setFname(((EditText) findViewById(R.id.add_contact_fname_text)).getText().toString().trim());
                    contact.setLName(((EditText) findViewById(R.id.add_contact_lname_text)).getText().toString().trim());
                    contact.setEmail(((EditText) findViewById(R.id.add_contact_email_text)).getText().toString().trim());

                    if (!((EditText) findViewById(R.id.add_contact_mobile_text)).getText().toString().isEmpty()) {
                        contact.setMobile(Long.parseLong(((EditText) findViewById(R.id.add_contact_mobile_text)).getText().toString()));
                    }

                    if (!((EditText) findViewById(R.id.add_contact_work_text)).getText().toString().isEmpty()) {
                        contact.setWork(Long.parseLong(((EditText) findViewById(R.id.add_contact_work_text)).getText().toString()));
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

                    DatabaseHelper db = new DatabaseHelper(AddContactActivity.this);
                    if (db.addData(contact)) {
                        Toast.makeText(getApplicationContext(), "Contact added successfully!", Toast.LENGTH_SHORT).show();
                        mRevealAnimation.unRevealActivity();
                    } else {
                        Toast.makeText(getApplicationContext(), "Unable to add contact!", Toast.LENGTH_LONG).show();
                    }

                    db.close();

                } else {
                    if (((EditText) findViewById(R.id.add_contact_fname_text)).getText().toString().trim().isEmpty())
                        ((EditText) findViewById(R.id.add_contact_fname_text)).setError("This field cannot be left empty.");

                    if (((EditText) findViewById(R.id.add_contact_mobile_text)).getText().toString().trim().isEmpty())
                        ((EditText) findViewById(R.id.add_contact_mobile_text)).setError("This field cannot be left empty.");
                }
            }
        });
    }

    private void addMoreFields() {
        findViewById(R.id.add_contact_field_button).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (field_count <= 3) {
                    LinearLayout linearLayout = new LinearLayout(AddContactActivity.this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutParams.setMargins(dpToPx(8), dpToPx(16), dpToPx(8), 0);
                    linearLayout.setLayoutParams(layoutParams);
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout.setWeightSum(5);
                    linearLayout.setTag("custom_linearLayout_" + field_count);
                    fieldsLayout.addView(linearLayout);

                    TextView textView = new TextView(AddContactActivity.this);
                    textView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1
                    ));
                    textView.setText("Custom " + field_count);
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(12);
                    Typeface font = Typeface.createFromAsset(getAssets(), "fonts/circular_std_book.ttf");
                    textView.setTypeface(font, Typeface.BOLD);
                    textView.setTag("custom_textView_" + field_count);

                    EditText editText = new EditText(AddContactActivity.this);
                    editText.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            4
                    ));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        editText.setBackground(getDrawable(R.drawable.bg_edittext_white));
                    }
                    editText.setPadding(dpToPx(10), dpToPx(10), dpToPx(10), dpToPx(10));
                    editText.setHint("Enter contact here");
                    editText.setTypeface(font);
                    editText.setTextSize(16);
                    editText.setTextColor(Color.WHITE);
                    editText.setHintTextColor(Color.parseColor("#A9FFFFFF"));
                    editText.setTag("custom_editText_" + field_count);
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);

                    linearLayout.addView(textView);
                    linearLayout.addView(editText);

                    field_count++;
                } else {
                    Snackbar.make(v.getRootView(), "You cannot add more fields.", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void exit() {
        findViewById(R.id.add_contact_back_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRevealAnimation.unRevealActivity();
            }
        });
    }

    @Override
    public void onBackPressed() {
        mRevealAnimation.unRevealActivity();
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
