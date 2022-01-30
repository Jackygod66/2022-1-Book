package com.example.book;

import android.content.ContentValues;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book.shoppingcart.ShoppingCartFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shoppingcart_info extends AppCompatActivity {

    String TABLENAME = "iteminfo";
    String imagedata;
    Bitmap imagebm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppingcart_info);
        getSupportActionBar().hide();
        final DatabaseHelper dbtest = new DatabaseHelper(this);
        final Intent intent = getIntent();
        final SQLiteDatabase db = dbtest.getWritableDatabase();
        ImageView image = findViewById(R.id.imageView);
        TextView price = findViewById(R.id.item_price);
        TextView title = findViewById(R.id.item_title) ;
        TextView info = findViewById(R.id.item_info);
        TextView contact = findViewById(R.id.contact);
        Cursor cursor = db.query(TABLENAME,null,"id=?",new String[]{intent.getStringExtra("id")},null,null,null,null); // 根据接收到的id进行数据库查询
        Log.i("商品的id是",intent.getStringExtra("id"));
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                imagedata = cursor.getString(5);
                imagebm = ImageUtil.base64ToImage(imagedata);
                image.setImageBitmap(imagebm);
                title.setText(cursor.getString(2));
                price.setText(cursor.getString(4));
                info.setText(cursor.getString(3));
                contact.setText(cursor.getString(7));
                cursor.moveToNext();
            }
        }
        ListView commentList = findViewById(R.id.commentList);
        Map<String, Object> item;  // 列表项内容用Map存储
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(); // 列表
        Cursor cursor1 = db.query("comments",null,"itemId=?",new String[]{intent.getStringExtra("id")},null,null,null,null); // 数据库查询
        if (cursor1.moveToFirst()){
            while (!cursor1.isAfterLast()){
                item = new HashMap<String, Object>();  // 为列表项赋值
                item.put("userId",cursor1.getString(2));
                item.put("comment",cursor1.getString(3));
                item.put("time",cursor1.getString(4));
                cursor1.moveToNext();
                data.add(item); // 加入到列表中
            }
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.comment_item, new String[] { "userId", "comment", "time"},
                new int[] { R.id.userId, R.id.commentInfo, R.id.time });
        commentList.setAdapter(simpleAdapter);
        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText comment = findViewById(R.id.comment);
                String submit_comment = comment.getText().toString();
                if(!submit_comment.equals(null)&&!submit_comment.equals("")){
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
                    Date curDate = new Date(System.currentTimeMillis());
                    String time = formatter.format(curDate);
                    ContentValues values=new ContentValues();
                    values.put("userId",LoginFragment.post_userid);
                    values.put("itemId",intent.getStringExtra("id"));
                    values.put("comment",submit_comment);
                    values.put("time",time);
                    db.insert("comments",null,values);
                    values.clear();
                    Log.i("1","评论成功");
                    Toast.makeText(getApplicationContext(), "评论成功", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Shoppingcart_info.this, ShoppingCartFragment.class);
                    startActivity(intent);
                }
                else Toast.makeText(getApplicationContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
            }
        });
        Button del_from_cart = findViewById(R.id.del_from_cart);
        del_from_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String delId = intent.getStringExtra("cartId");
                if (db.delete("shoppingcart", "cartId=?", new String[]{delId}) > 0) {
                    Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Shoppingcart_info.this, ShoppingCartFragment.class);
                    startActivity(intent);
                }
            }
        });
    }
}
