package com.example.book.mine;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.book.AboutActivity;
import com.example.book.LoginFragment;
import com.example.book.PuttingFragment;
import com.example.book.R;
import com.example.book.UserMsgFragment;
import com.example.book.changepwdActivity;
import com.example.book.home.HomeFragment;
import com.example.book.shoppingcart.ShoppingCartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.zip.Inflater;

public class MineFragment extends AppCompatActivity {

    private Button myself;
    private Button myshow;
    private Button changepwd;
    private Button about;
    private Button login;
    private TextView myId;
    protected Intent intent;
    private String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mine);
        getSupportActionBar().hide();

        myself = findViewById(R.id.myself);
        myshow=findViewById(R.id.myShow);
        changepwd=findViewById(R.id.changepwd);
        about=findViewById(R.id.about);
        login=findViewById(R.id.login) ;
        myId = findViewById(R.id.myId);
        a = LoginFragment.post_userid;
        myId.setText(a);

        Log.i("123",a);
        if(a.equals("")||a==null){
            login.setText("登 录");
        }else{
            login.setText("退出登录");
        }

        myself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MineFragment.this,UserMsgFragment.class);
                startActivity(intent);
            }
        });

        myshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a.equals("")||a==null){
                    Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT).show();
                    intent = new Intent(MineFragment.this,LoginFragment.class);
                    startActivity(intent);
                }
                intent = new Intent(MineFragment.this, PuttingFragment.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a.equals("")||a==null){
                    intent = new Intent(MineFragment.this,LoginFragment.class);
                    finish();
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "退出成功", Toast.LENGTH_SHORT).show();
                    finish();
                    LoginFragment.post_userid="";
                    intent = new Intent(MineFragment.this,LoginFragment.class);
                    startActivity(intent);
                }

            }
        });

        changepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a.equals("")||a==null){
                    Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT).show();
                    intent = new Intent(MineFragment.this,LoginFragment.class);
                    startActivity(intent);
                }
                intent = new Intent(MineFragment.this, changepwdActivity.class);
                startActivity(intent);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MineFragment.this, AboutActivity.class);
                startActivity(intent);
            }
        });





        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.bottom_mine);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_home:
                        startActivity(new Intent(getApplicationContext(), HomeFragment.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bottom_shopping_cart:
                        startActivity(new Intent(getApplicationContext(), ShoppingCartFragment.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bottom_mine:
                        return true;
                }
                return false;
            }
        });
    }


/*
    @Override
    protected void initViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }*/
}
