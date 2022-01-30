package com.example.book.shoppingcart;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book.DatabaseHelper;
import com.example.book.ImageUtil;
import com.example.book.LoginFragment;
import com.example.book.PuttingFragment;
import com.example.book.R;
import com.example.book.Shoppingcart_info;
import com.example.book.home.HomeFragment;
import com.example.book.item_info;
import com.example.book.mine.MineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartFragment extends AppCompatActivity {
    String TABLENAME = "shoppingcart";
    String itemid;
    String imagedata;
    Bitmap imagebm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_shoppingcart);
        getSupportActionBar().hide();


        DatabaseHelper database = new DatabaseHelper(this);
        final SQLiteDatabase db = database.getWritableDatabase();
        ListView listView = (ListView)findViewById(R.id.show_cart);
        Map<String, Object> item;  // 列表项内容用Map存储
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(); // 列表
        String userid = LoginFragment.post_userid;
        Cursor cursor1 = db.query(TABLENAME,null,"userId=?",new String[]{userid},null,null,null,null); // 购物车数据库查询
        Cursor cursor;//商品查询列表
        if(cursor1.moveToFirst()){
            while(!cursor1.isAfterLast()){
                itemid = String.valueOf(cursor1.getInt(1));
                cursor = db.query("iteminfo",null,"id=?",new String[]{itemid},null,null,null,null);//商品数据库查询
                cursor.moveToFirst();
                if(cursor.moveToFirst()){
                    while (!cursor.isAfterLast()){
                        item = new HashMap<String, Object>();  // 为列表项赋值
                        item.put("cartId", cursor1.getInt(0));
                        item.put("id", cursor.getInt(0));
                        item.put("userid", cursor.getString(1));
                        item.put("title", cursor.getString(2));
                        item.put("info", cursor.getString(3));
                        item.put("price", cursor.getString(4));
                        imagedata = cursor.getString(5);
                        imagebm = ImageUtil.base64ToImage(imagedata);
                        item.put("image", imagebm);
                        cursor.moveToNext();
                        data.add(item); // 加入到列表中
                    }
                }
                cursor1.moveToNext();
            }
        }

        // 使用SimpleAdapter布局listview
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.activity_my_shoppingcart, new String[]{"image", "title", "info", "price"},
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
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long cartId) {

                String delId = data.get(position).get("cartId").toString();
                if (db.delete(TABLENAME, "cartId=?", new String[]{delId}) > 0) {
                    Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ShoppingCartFragment.this, ShoppingCartFragment.class);
                    startActivity(intent);
                    return true;
                } else {
                    return false;
                }
            }
        });*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long cartId) {
                Intent intent = new Intent(ShoppingCartFragment.this, Shoppingcart_info.class);
                intent.putExtra("cartId", data.get(position).get("cartId").toString()); // 获取该列表项的key为cartId的键值，即购物车Id，将其储存在Bundle传递给打开的页面
                intent.putExtra("id", data.get(position).get("id").toString());
                startActivity(intent);
            }
        });




        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.bottom_shopping_cart);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_home:
                        startActivity(new Intent(getApplicationContext(), HomeFragment.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bottom_shopping_cart:
                        return true;
                    case R.id.bottom_mine:
                        startActivity(new Intent(getApplicationContext(), MineFragment.class));
                        overridePendingTransition(0,0);
                        return true;
                }


                return false;
            }
        });
    }
}
