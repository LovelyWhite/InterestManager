package cn.lovelywhite.interestmanager.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;

import java.util.Objects;

import cn.lovelywhite.interestmanager.Data.StaticValues;
import cn.lovelywhite.interestmanager.Fragment.Home;
import cn.lovelywhite.interestmanager.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(!StaticValues.fG.containsKey("home"))
                        StaticValues.fG.put("home",new Home());
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, Objects.requireNonNull(StaticValues.fG.get("home"))).commit();
                    return true;
                case R.id.navigation_manage:

                    return true;
                case R.id.navigation_setting:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StaticValues.fG.put("home",new Home());//添加
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, Objects.requireNonNull(StaticValues.fG.get("home"))).commit();
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
