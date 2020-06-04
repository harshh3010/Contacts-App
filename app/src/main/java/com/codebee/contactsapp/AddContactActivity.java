package com.codebee.contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddContactActivity extends AppCompatActivity {

    RevealAnimation mRevealAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        Intent intent = this.getIntent();

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.add_contact_constraint_layout);
        mRevealAnimation = new RevealAnimation(constraintLayout, intent, this);

        exit();
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
}
