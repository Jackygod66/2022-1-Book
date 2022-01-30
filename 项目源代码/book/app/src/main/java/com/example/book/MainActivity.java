package com.example.book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.book.home.HomeFragment;
import com.example.book.mine.MineFragment;
import com.example.book.shoppingcart.ShoppingCartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{

}
/*
public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Fragment[] fragments;
    private int lastFragmentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Book);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        BottomNavigationView bottomNavigationView =
                find(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        fragments = new Fragment[]{
                new HomeFragment(),
                new ShoppingCartFragment(),
                new MineFragment()
        };

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_frame,fragments[0])
                .commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        switch(item.getItemId()){
            case R.id.bottom_home:
                //switchFragment(0);
                replaceFragment(new HomeFragment());
                //Intent intent = new Intent(this,UserMsgFragment.class);
                //startActivity(intent);
                break;
            case R.id.bottom_shopping_cart:
                //switchFragment(1);
                replaceFragment(new ShoppingCartFragment());
                break;
            case R.id.bottom_mine:
                //switchFragment(2);
                replaceFragment(new MineFragment());
                break;
        }

        return false;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayout,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void switchFragment(int to){
        if(lastFragmentIndex == to) return;
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        if(!fragments[to].isAdded()){
            fragmentTransaction.add(R.id.main_frame,fragments[to]);
        }
        else {
            fragmentTransaction.show(fragments[to]);
        }
        fragmentTransaction.hide(fragments[lastFragmentIndex])
                .commitAllowingStateLoss();
        lastFragmentIndex = to;
    }
}*/