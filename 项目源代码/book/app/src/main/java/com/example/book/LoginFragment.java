package com.example.book;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book.home.HomeFragment;

import java.util.ArrayList;

public class LoginFragment extends AppCompatActivity implements View.OnClickListener{

    public static String post_userid;
    private TextView mTvLoginactivityRegister;
    //private RelativeLayout mRlLoginactivityTop;
    private EditText mEtLoginactivityUsername;
    private EditText mEtLoginactivityPassword;
    //private LinearLayout mLlLoginactivityTwo;
    private Button mBtLoginactivityLogin;
    private TextView Login_Times;
    //protected Intent intent;
    private int times=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initView();

    }

    private void initView() {
        // 初始化控件
        mBtLoginactivityLogin = findViewById(R.id.bt_loginactivity_login);
        mTvLoginactivityRegister = findViewById(R.id.tv_loginactivity_register);
        //mRlLoginactivityTop = findViewById(R.id.rl_loginactivity_top);
        mEtLoginactivityUsername = findViewById(R.id.et_loginactivity_username);
        mEtLoginactivityPassword = findViewById(R.id.et_loginactivity_password);
        //mLlLoginactivityTwo = findViewById(R.id.ll_loginactivity_two);
        Login_Times = findViewById(R.id.time);
        Login_Times.setText(String.valueOf(times));


        // 设置点击事件监听器
        mBtLoginactivityLogin.setOnClickListener(this);
        mTvLoginactivityRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 跳转到注册界面
            case R.id.tv_loginactivity_register:
                startActivity(new Intent(this, RegisterFragment.class));
                finish();
                break;

            case R.id.bt_loginactivity_login:
                String user = mEtLoginactivityUsername.getText().toString().trim();
                String password = mEtLoginactivityPassword.getText().toString().trim();

                if(user.equals("")||user==null) {
                    Toast.makeText(this, "请输入用户账号！", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (password.equals("") || password == null) {
                    Toast.makeText(this, "请输入用户密码！", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(times>0){
                    checkUser(user,password);
                    Login_Times.setText(String.valueOf(times));
                }
                else {
                    Toast.makeText(this,"无法登陆",Toast.LENGTH_SHORT).show();
                    Login_Times.setText(String.valueOf(times));
                    break;
                }


                /*if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    ArrayList<User> data = mDBOpenHelper.getAllData();
                    boolean match = false;
                    for (int i = 0; i < data.size(); i++) {
                        User user = data.get(i);
                        if (name.equals(user.getName()) && password.equals(user.getPassword())) {
                            match = true;
                            break;
                        } else {
                            match = false;
                        }
                    }
                    if(times>0){
                        if (match) {
                            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                            finish();//销毁此Activity
                        }
                        else {
                            Toast.makeText(this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                            times--;
                            Login_Times.setText(String.valueOf(times));
                        }
                    }
                    else {
                        Toast.makeText(this,"无法登陆",Toast.LENGTH_SHORT).show();
                        Login_Times.setText(String.valueOf(times));
                        break;
                    }
                }
                else {
                    Toast.makeText(this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                }
                break;*/
        }
    }

    private void checkUser(String user, String password) {
        DatabaseHelper dbhelper = new DatabaseHelper(this);
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        try{
            String sql="SELECT * FROM users WHERE userId=? and passWord=?";
            Cursor cursor=db.rawQuery(sql,new String[]{user,password});
            if(cursor.getCount()==0){
                Toast.makeText(getApplicationContext(), "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                times--;
            }
            else{
                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, HomeFragment.class);
                post_userid=user;
                startActivity(intent);
                finish();
            }
            cursor.close();
            db.close();
        }catch (SQLiteException e){
            Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
        }
    }
}
