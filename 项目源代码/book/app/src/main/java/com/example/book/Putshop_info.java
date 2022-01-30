package com.example.book;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Putshop_info extends AppCompatActivity {
    String TABLENAME = "iteminfo";
    String imagedata;
    Bitmap imagebm;
    private Button but_tofabu;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.putshop_info);
        getSupportActionBar().hide();
        final DatabaseHelper dbtest = new DatabaseHelper(this);
        final Intent intent = getIntent();
        final SQLiteDatabase db = dbtest.getWritableDatabase();
        ImageView image = findViewById(R.id.m1_image);
        TextView title1=findViewById(R.id.m1_title);
        TextView price1=findViewById(R.id.m1_price);
        TextView phone1=findViewById(R.id.m1_phone);
        TextView nr1=findViewById(R.id.m1_nr);
        Cursor cursor = db.query(TABLENAME,null,"id=?",new String[]{intent.getStringExtra("id")},null,null,null,null); // 根据接收到的id进行数据库查询
        Log.i("商品的id是",intent.getStringExtra("id"));
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                imagedata = cursor.getString(5);
                imagebm = ImageUtil.base64ToImage(imagedata);
                image.setImageBitmap(imagebm);
                title1.setText(cursor.getString(2));
                price1.setText(cursor.getString(4));
                nr1.setText(cursor.getString(3));
                phone1.setText(cursor.getString(7));
                cursor.moveToNext();
            }
        }

        but_tofabu = findViewById(R.id.but_tofabu);
        but_tofabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Putshop_info.this, PuttingFragment.class);
                startActivity(intent);
            }
        });

        Button del_from_fabu = findViewById(R.id.del_from_fabu);
        del_from_fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String delId = intent.getStringExtra("id");
                if (db.delete(TABLENAME, "id=?", new String[]{delId}) > 0) {
                    Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Putshop_info.this, PuttingFragment.class);
                    startActivity(intent);
                }
            }
        });





    }
}
