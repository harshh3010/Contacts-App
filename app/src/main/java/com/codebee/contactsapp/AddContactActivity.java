package com.codebee.contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Size;
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
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.add_contact_constraint_layout);
        mRevealAnimation = new RevealAnimation(constraintLayout, intent, this);

        fieldsLayout = (LinearLayout) findViewById(R.id.add_contact_fields_layout);

        addMoreFields();

        exit();
    }

    private void addMoreFields(){
        findViewById(R.id.add_contact_field_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(field_count <= 3){
                    LinearLayout linearLayout = new LinearLayout(AddContactActivity.this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutParams.setMargins(dpToPx(8),dpToPx(16),dpToPx(8),0);
                    linearLayout.setLayoutParams(layoutParams);
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout.setWeightSum(5);
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
                    Typeface font = Typeface.createFromAsset(getAssets(),"fonts/circular_std_book.ttf");
                    textView.setTypeface(font,Typeface.BOLD);
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
                    editText.setPadding(dpToPx(10),dpToPx(10),dpToPx(10),dpToPx(10));
                    editText.setHint("Enter custom value " + field_count);
                    editText.setTypeface(font);
                    editText.setTextSize(16);
                    editText.setTextColor(Color.WHITE);
                    editText.setHintTextColor(Color.parseColor("#A9FFFFFF"));
                    editText.setTag("custom_editText_" + field_count);

                    linearLayout.addView(textView);
                    linearLayout.addView(editText);

                    field_count++;
                }else{
                    Snackbar.make(v.getRootView(),"You cannot add more fields.", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void exit(){
        findViewById(R.id.add_contact_back_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRevealAnimation.unRevealActivity();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        mRevealAnimation.unRevealActivity();
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
