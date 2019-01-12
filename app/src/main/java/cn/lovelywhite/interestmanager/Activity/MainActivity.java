package cn.lovelywhite.interestmanager.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.FrameMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import cn.lovelywhite.interestmanager.Data.StaticValues;
import cn.lovelywhite.interestmanager.Fragment.Home;
import cn.lovelywhite.interestmanager.Fragment.Manage;
import cn.lovelywhite.interestmanager.Fragment.Setting;
import cn.lovelywhite.interestmanager.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction()
                            .show(Objects.requireNonNull(StaticValues.fG.get("home")))
                            .hide(Objects.requireNonNull(StaticValues.fG.get("manage")))
                            .hide(Objects.requireNonNull(StaticValues.fG.get("setting")))
                            .commit();
                    return true;
                case R.id.navigation_manage:
                    getSupportFragmentManager().beginTransaction()
                            .show(Objects.requireNonNull(StaticValues.fG.get("manage")))
                            .hide(Objects.requireNonNull(StaticValues.fG.get("home")))
                            .hide(Objects.requireNonNull(StaticValues.fG.get("setting")))
                            .commit();
                    return true;
                case R.id.navigation_setting:
                    getSupportFragmentManager().beginTransaction()
                            .show(Objects.requireNonNull(StaticValues.fG.get("setting")))
                            .hide(Objects.requireNonNull(StaticValues.fG.get("home")))
                            .hide(Objects.requireNonNull(StaticValues.fG.get("manage")))
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StaticValues.fG.put("home", new Home());//添加
        StaticValues.fG.put("manage", new Manage());//添加
        StaticValues.fG.put("setting", new Setting());//添加
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, Objects.requireNonNull(StaticValues.fG.get("home")), "home")
                .add(R.id.frame_layout, Objects.requireNonNull(StaticValues.fG.get("manage")), "manage")
                .add(R.id.frame_layout, Objects.requireNonNull(StaticValues.fG.get("setting")), "setting")
                .hide(Objects.requireNonNull(StaticValues.fG.get("manage")))
                .hide(Objects.requireNonNull(StaticValues.fG.get("setting")))
                .commit();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次离开系统", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
