package com.example.vladimir.remindme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AuthorActivity extends AppCompatActivity {

    private TextView authorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        authorText = (TextView) findViewById(R.id.authorText);
    }
}
