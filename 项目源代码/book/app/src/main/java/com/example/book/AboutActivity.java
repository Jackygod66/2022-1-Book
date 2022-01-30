package com.example.book;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book.home.HomeFragment;
import com.example.book.mine.MineFragment;

public class AboutActivity extends AppCompatActivity {
    private Button cometo;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_about);
        cometo=findViewById(R.id.cometo);


        cometo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AboutActivity.this, HomeFragment.class);
                startActivity(intent);
            }
        });
    }
}
