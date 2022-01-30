package com.example.book;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book.mine.MineFragment;

public class UserMsgFragment extends AppCompatActivity {
    private TextView userid;
    private TextView username;
    private TextView usersubject;
    private TextView userphone;
    private TextView userqq;
    private TextView useraddress;
    private Button userchange;
    private Button back;
    protected Intent intent;
    private String id,name,subject,phone,qq,address;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_msg);
        getSupportActionBar().hide();

        userid = findViewById(R.id.showUser);
        username = findViewById(R.id.name);
        usersubject = findViewById(R.id.subject);
        userphone = findViewById(R.id.phone);
        userqq = findViewById(R.id.qq);
        useraddress = findViewById(R.id.address);
        userchange = findViewById(R.id.changemsg);
        back = findViewById(R.id.back);
        id = LoginFragment.post_userid;
        userid.setText(id);
        if (id.equals("") || id == null) {
            Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT).show();
            intent = new Intent(UserMsgFragment.this, LoginFragment.class);
            startActivity(intent);
        } else {//账号userId，密码passWord，姓名name，专业subject，电话phone，QQ号qq,地址address
            DatabaseHelper dbhelper = new DatabaseHelper(this);
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            try {
                String sql = "SELECT * FROM users WHERE userId=?";
                Cursor cursor = db.rawQuery(sql, new String[]{id});
                if (cursor.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "用户不存在！", Toast.LENGTH_SHORT).show();
                } else {
                    if (cursor.moveToFirst()) {
                        name = cursor.getString(cursor.getColumnIndex("name"));
                        subject = cursor.getString(cursor.getColumnIndex("subject"));
                        phone = cursor.getString(cursor.getColumnIndex("phone"));
                        qq = cursor.getString(cursor.getColumnIndex("qq"));
                        address = cursor.getString(cursor.getColumnIndex("address"));
                    }
                    Log.i("123","1233333");
                    username.setText(name);
                    userphone.setText(phone);
                    usersubject.setText(subject);
                    userqq.setText(qq);
                    useraddress.setText(address);
                    Log.i("123","12344444");

                }
                cursor.close();
                db.close();
            } catch (SQLiteException e) {
                Toast.makeText(getApplicationContext(), "无法显示个人信息", Toast.LENGTH_SHORT).show();
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(UserMsgFragment.this, MineFragment.class);
                startActivity(intent);
            }
        });

        userchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(UserMsgFragment.this,SetMsgFragment.class);
                startActivity(intent);
            }
        });
    }

}
