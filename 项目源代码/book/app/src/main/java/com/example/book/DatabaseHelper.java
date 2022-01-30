package com.example.book;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.example.book.bean.Shop;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String dbname="mydb";
    public DatabaseHelper(Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //账号userId，密码passWord，姓名name，专业subject，电话phone，QQ号qq,地址address
        db.execSQL("create table if not exists users" +
                "(userId varchar(20) primary key," +
                "passWord varchar(20) not null," +
                "name varchar(20)," +
                "subject varchar(20)," +
                "phone varchar(15)," +
                "qq varchar(15)," +
                "address varchar(50))");
        //商品编号id，发布者账号userId，标题title，内容info，价格price，图片image
        db.execSQL("create table if not exists iteminfo(" +
                "id integer primary key AUTOINCREMENT," +
                "userId varchar(100)," +
                "title varchar(200)," +
                "info varchar(1000)," +
                "price varchar(100)," +
                "image blob," +
                "time DATETIME," +
                "contact varchar(1000))");
        //评论者账号userId，评论编号commentId，商品编号itemId，评论内容comment，评论时间time
        db.execSQL("create table if not exists comments(" +
                "commentId integer primary key AUTOINCREMENT," +
                "itemId integer," +
                "userId varchar(100)," +
                "comment varchar(1000)," +
                "time DATETIME)");
        //购物车货物编号cartId，商品编号itemId，账户名userId
        db.execSQL("create table if not exists shoppingcart(" +
                "cartId integer primary key AUTOINCREMENT," +
                "itemId integer," +
                "userId varchar(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Shop> queryAllFromDb() {

        SQLiteDatabase db = getWritableDatabase();
        List<Shop> shopList = new ArrayList<>();

        Cursor cursor = db.query("iteminfo", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                String id = cursor.getString(0);
                String userid = cursor.getString(1);
                String title = cursor.getString(2);
                String info = cursor.getString(3);
                String price = cursor.getString(4);
                String imagedata = cursor.getString(5);
                Bitmap imagebm = ImageUtil.base64ToImage(imagedata);

                Shop shop = new Shop();
                shop.setId(id);
                shop.setUserId(userid);
                shop.setTitle(title);
                shop.setInfo(info);
                shop.setPrice(price);
                shop.setImage(imagebm);
                shopList.add(shop);
                cursor.moveToNext();
            }
        }

        return shopList;

    }

    public List<Shop> queryFromDbByTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            return queryAllFromDb();
        }

        SQLiteDatabase db = getWritableDatabase();
        List<Shop> shopList = new ArrayList<>();

        Cursor cursor = db.query("iteminfo", null, "title like ?", new String[]{"%"+title+"%"}, null, null, null);

        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                String id = cursor.getString(0);
                String userid = cursor.getString(1);
                String title1 = cursor.getString(2);
                String info = cursor.getString(3);
                String price = cursor.getString(4);
                String imagedata = cursor.getString(5);
                Bitmap imagebm = ImageUtil.base64ToImage(imagedata);

                Shop shop = new Shop();
                shop.setId(id);
                shop.setUserId(userid);
                shop.setTitle(title1);
                shop.setInfo(info);
                shop.setPrice(price);
                shop.setImage(imagebm);
                shopList.add(shop);
                cursor.moveToNext();
            }
        }
        return shopList;
    }
}
