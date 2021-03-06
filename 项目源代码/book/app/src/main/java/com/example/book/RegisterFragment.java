package com.example.book;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterFragment extends AppCompatActivity implements View.OnClickListener {

    private String realCode;
    private Button mBtRegisteractivityRegister;
    private RelativeLayout mRlRegisteractivityTop;
    private ImageView mIvRegisteractivityBack;
    private LinearLayout mLlRegisteractivityBody;
    private EditText mEtRegisteractivityUsername;
    private EditText mEtRegisteractivityPassword1;
    private EditText mEtRegisteractivityPassword2;
    private EditText mEtRegisteractivityPhonecodes;
    private ImageView mIvRegisteractivityShowcode;
    private RelativeLayout mRlRegisteractivityBottom;
    String username = null;
    String password1 = null;
    String password2 = null;
    String phoneCode = null;


    private void initView(){
        mBtRegisteractivityRegister = findViewById(R.id.bt_registeractivity_register);
        mRlRegisteractivityTop = findViewById(R.id.rl_registeractivity_top);
        mIvRegisteractivityBack = findViewById(R.id.iv_registeractivity_back);
        mLlRegisteractivityBody = findViewById(R.id.ll_registeractivity_body);
        mEtRegisteractivityUsername = findViewById(R.id.et_registeractivity_username);
        mEtRegisteractivityPassword1 = findViewById(R.id.et_registeractivity_password1);
        mEtRegisteractivityPassword2 = findViewById(R.id.et_registeractivity_password2);
        mEtRegisteractivityPhonecodes = findViewById(R.id.et_registeractivity_phoneCodes);
        mIvRegisteractivityShowcode = findViewById(R.id.iv_registeractivity_showCode);
        mRlRegisteractivityBottom = findViewById(R.id.rl_registeractivity_bottom);


        mIvRegisteractivityBack.setOnClickListener(this);
        mIvRegisteractivityShowcode.setOnClickListener(this);
        mBtRegisteractivityRegister.setOnClickListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initView();

        //??????????????????????????????????????????
        mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_registeractivity_back: //??????????????????
                Intent intent1 = new Intent(this, LoginFragment.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.iv_registeractivity_showCode:    //??????????????????????????????
                mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
                break;
            case R.id.bt_registeractivity_register:    //????????????
                //???????????????????????????????????????????????????
                username = mEtRegisteractivityUsername.getText().toString().trim();
                password1 = mEtRegisteractivityPassword1.getText().toString().trim();
                password2 = mEtRegisteractivityPassword2.getText().toString().trim();
                phoneCode = mEtRegisteractivityPhonecodes.getText().toString().toLowerCase();
                //??????

                if(username==null||username.equals("")){
                    Toast.makeText(this, "?????????????????????", Toast.LENGTH_SHORT).show();
                }
                else if(password1==null||password1.equals("")){
                    Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
                }
                else if(!password1.equals(password2)){
                    Toast.makeText(this, "??????????????????????????????!", Toast.LENGTH_SHORT).show();
                }
                else if(phoneCode.equals((realCode))){
                    checkUser(username,password2);
                }
                else Toast.makeText(this, "???????????????,????????????", Toast.LENGTH_SHORT).show();

                /*if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(phoneCode) ) {
                    if (phoneCode.equals(realCode)) {
                        //??????????????????????????????????????????
                        mDBOpenHelper.add(username, password);
                        Intent intent2 = new Intent(this, MainActivity.class);
                        startActivity(intent2);
                        finish();
                        Toast.makeText(this,  "???????????????????????????", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "???????????????,????????????", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
                }*/
                break;
        }

    }


    private void checkUser(String user,String pwd){
        DatabaseHelper dbhelper = new DatabaseHelper(this);
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        try{
            String sql="SELECT * FROM users WHERE userId=?";
            Cursor cursor=db.rawQuery(sql,new String[]{user});
            if(cursor.getCount()>0){
                Toast.makeText(this, "?????????????????????", Toast.LENGTH_SHORT).show();
            }
            else{
                ContentValues values = new ContentValues();
                //???????????????????????????   //??????userId?????????passWord?????????name?????????subject?????????phone???QQ???qq,??????address
                values.put("userId",user);
                values.put("passWord",pwd);
                db.insert("users",null,values);//?????????????????????
                Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterFragment.this,LoginFragment.class);
                startActivity(intent);
            }
            cursor.close();
            db.close();
        }catch (SQLiteException e){
            Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
        }
    }

}
