package com.belajar.dicoding.submission.fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.belajar.dicoding.submission.R;
import com.bumptech.glide.Glide;

public class FullScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        getSupportActionBar().hide();
    }
}