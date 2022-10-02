package com.example.eatingbox;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //BottomNavigationView
        BottomNavigationView navigationView = findViewById(R.id.nav_menu);
        //构建导航id  menu 与 navigation中的id要相对应
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_record,R.id.navigation_profile)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //设置ActionBar标题与当前所显示的fragment相对应
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //与navcontroller相关联
        NavigationUI.setupWithNavController(navigationView, navController);

        //设置BottomNavigationView点击事件
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //跳转相应fragment
                navController.navigate(menuItem.getItemId());
                //返回false会有一个点击悬浮效果，返回true则不会有该效果
                return false;
            }
        });



    }
}
