package com.example.book;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book.mine.MineFragment;
import com.example.book.shoppingcart.ShoppingCartFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PuttingFragment extends AppCompatActivity {
    String TABLENAME = "iteminfo";
    String imagedata;
    Bitmap imagebm;
    //private LayoutInflater mLayoutInflater;
    private Button but1;
    private Button but2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puttings);
        getSupportActionBar().hide();

        DatabaseHelper database = new DatabaseHelper(this);
        final SQLiteDatabase db = database.getWritableDatabase();
        ListView listView = (ListView)findViewById(R.id.show_fabu);
        Map<String, Object> item;  // 列表项内容用Map存储
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(); // 列表
        String userid = LoginFragment.post_userid;
        Cursor cursor = db.query(TABLENAME,null,"userId=?",new String[]{userid},null,null,null,null); // 数据库查询
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    item = new HashMap<String, Object>();  // 为列表项赋值
                    item.put("id", cursor.getInt(0));
                    item.put("userid", cursor.getString(1));
                    item.put("title", cursor.getString(2));
                    item.put("info", cursor.getString(3));
                    item.put("price", cursor.getString(4));
                    Log.i("商品编号：",String.valueOf(cursor.getInt(0)));
                    Log.i("发布者:",cursor.getString(1));
                    imagedata = cursor.getString(5);
                    imagebm = ImageUtil.base64ToImage(imagedata);
                    item.put("image", imagebm);
                    cursor.moveToNext();
                    data.add(item); // 加入到列表中
                }
            }



            // 使用SimpleAdapter布局listview
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.activity_my_fabu, new String[]{"image", "title", "info", "price"},
                    new int[]{R.id.item_image, R.id.title, R.id.info, R.id.price});
            simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data, String textRepresentation) {
                    if (view instanceof ImageView && data instanceof Bitmap) {
                        ImageView iv = (ImageView) view;
                        iv.setImageBitmap((Bitmap) data);
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            listView.setAdapter(simpleAdapter);
/*
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    String delId = data.get(position).get("id").toString();
                    if (db.delete(TABLENAME, "id=?", new String[]{delId}) > 0) {
                        Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PuttingFragment.this, PuttingFragment.class);
                        startActivity(intent);
                        return true;
                    } else {
                        return false;
                    }
                }
            });*/

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(PuttingFragment.this, Putshop_info.class);
                    intent.putExtra("id", data.get(position).get("id").toString());
                    startActivity(intent);
                }
            });


            but1 = findViewById(R.id.but1);
            but1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PuttingFragment.this, MineFragment.class);
                    startActivity(intent);
                }
            });

            but2 = findViewById(R.id.but2);
            but2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PuttingFragment.this, PutShopFragment.class);
                    startActivity(intent);
                }
            });



    }
}
