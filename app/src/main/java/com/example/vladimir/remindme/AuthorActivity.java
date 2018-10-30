package com.example.vladimir.remindme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class AuthorActivity extends AppCompatActivity {

    private ImageView avatar;
    private TextView authorName;
    private TextView authorEmail;
    private TextView authorGit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        avatar = (ImageView) findViewById(R.id.avaImg);
        authorName = (TextView) findViewById(R.id.authorName);
        authorEmail = (TextView) findViewById(R.id.authorEmail);
        authorGit = (TextView) findViewById(R.id.authorGit);
    }
}
