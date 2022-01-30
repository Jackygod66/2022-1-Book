package com.example.book.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObservable;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.book.DatabaseHelper;
import com.example.book.ImageUtil;
import com.example.book.R;
import com.example.book.adapter.MyAdapter;
import com.example.book.bean.Shop;
import com.example.book.item_info;
import com.example.book.mine.MineFragment;
import com.example.book.shoppingcart.ShoppingCartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HomeFragment extends AppCompatActivity implements View.OnClickListener{

    String TABLENAME = "iteminfo";
    Intent intent;
    String imagedata;
    Bitmap imagebm;
    private RecyclerView mRecyclerView;
    private List<Shop> shops;
    private DatabaseHelper databaseHelper;
    private MyAdapter mMyAdapter;
    //private List<Map<String, Object>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        getSupportActionBar().hide();

        mRecyclerView = findViewById(R.id.rlv);
        shops = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);
        mMyAdapter = new MyAdapter(this, shops);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mMyAdapter);


/*
        DatabaseHelper database = new DatabaseHelper(this);
        final SQLiteDatabase db = database.getWritableDatabase();
        ListView listView = findViewById(R.id.show_all);
        Map<String, Object> item;  // 列表项内容用Map存储
        //List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(); // 列表
        //Map<String, Object> item;  // 列表项内容用Map存储
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(); // 列表
        Cursor cursor = db.query(TABLENAME,null,null,null,null,null,null,null); // 数据库查询
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                item = new HashMap<String, Object>();  // 为列表项赋值
                item.put("id",cursor.getInt(0));
                item.put("userid",cursor.getString(1));
                item.put("title",cursor.getString(2));
                item.put("info",cursor.getString(3));
                item.put("price",cursor.getString(4));
                imagedata = cursor.getString(5);
                imagebm = ImageUtil.base64ToImage(imagedata);
                item.put("image",imagebm);
                cursor.moveToNext();
                data.add(item); // 加入到列表中
            }
        }


        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("1","进入首页");
                //data = new ArrayList<Map<String, Object>>();
                //data = queryFromDbByTitle(newText);

                return true;
            }
        });

        // 使用SimpleAdapter布局listview
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.activity_show_all, new String[] { "image", "title", "info", "price" },
                new int[] { R.id.item_image, R.id.title, R.id.info, R.id.price });
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if(view instanceof ImageView && data instanceof Bitmap){
                    ImageView iv = (ImageView)view;
                    iv.setImageBitmap( (Bitmap)data );
                    return true;
                }else{
                    return false;
                }
            }
        });
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(HomeFragment.this, item_info.class);
                intent.putExtra("id", data.get(position).get("id").toString()); // 获取该列表项的key为id的键值，即商品的id，将其储存在Bundle传递给打开的页面
                startActivity(intent);
            }
        });
*/

        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("1","进入首页");
                //data = new ArrayList<Map<String, Object>>();
                //data = queryFromDbByTitle(newText);

                shops = databaseHelper.queryFromDbByTitle(newText);
                mMyAdapter.refreshData(shops);
                return true;
            }
        });





        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_home:
                        return true;
                    case R.id.bottom_shopping_cart:
                        startActivity(new Intent(getApplicationContext(), ShoppingCartFragment.class));
                        overridePendingTransition(0,0);
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

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshDataFromDb();
    }

    private void refreshDataFromDb() {
        shops = getDataFromDB();
        mMyAdapter.refreshData(shops);
    }

    private List<Shop> getDataFromDB() {
        return databaseHelper.queryAllFromDb();
    }

    /*
    public List<Shop> queryAllFromDb() {
        DatabaseHelper database = new DatabaseHelper(this);
        SQLiteDatabase db = database.getWritableDatabase();
        List<Shop> data = new ArrayList<>();
        Cursor cursor = db.query(TABLENAME,null,null,null,null,null,null,null); // 数据库查询
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                String userid = cursor.getString(cursor.getColumnIndex("userid"));
                item.put("id",cursor.getInt(0));
                item.put("userid",cursor.getString(1));
                item.put("title",cursor.getString(2));
                item.put("info",cursor.getString(3));
                item.put("price",cursor.getString(4));
                imagedata = cursor.getString(5);
                imagebm = ImageUtil.base64ToImage(imagedata);
                item.put("image",imagebm);
                cursor.moveToNext();
                data.add(item); // 加入到列表中
            }
        }
        Log.i("data","全部");
        return data;
    }

    public List<Shop> queryFromDbByTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            return queryAllFromDb();
        }
        DatabaseHelper database = new DatabaseHelper(this);
        final SQLiteDatabase db = database.getWritableDatabase();
        Map<String, Object> item;  // 列表项内容用Map存储
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(); // 列表
        Cursor cursor = db.query(TABLENAME, null, "title like ?", new String[]{"%"+title+"%"}, null, null, null, null); // 数据库查询
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                item = new HashMap<String, Object>();  // 为列表项赋值
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
        Log.i("data","部分");
        return data;
    }*/


}
