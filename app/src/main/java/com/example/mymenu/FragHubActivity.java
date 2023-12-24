package com.example.mymenu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FragHubActivity extends AppCompatActivity {
    Intent intent;
    ViewPager2 viewPager;
    MyViewPagerAdapter myAdapter;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraghub);

        TabLayout tabLayout = findViewById(R.id.tabLayout);


        viewPager = findViewById(R.id.viewPager2);
        myAdapter = new MyViewPagerAdapter(
                getSupportFragmentManager(),
                getLifecycle());
        myAdapter.addFragment(new SignUpFrag());
        myAdapter.addFragment(new SignInFrag());
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(myAdapter);
        String[] name = new String[]{"Sign Up", "Sign In"};

        new TabLayoutMediator(
                tabLayout,
                viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(name[position]);
                    }
                }
        ).attach();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);

        if (menu instanceof MenuBuilder) {
            MenuBuilder mb = (MenuBuilder) menu;
            mb.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnMute) {
            if (MainActivity.isPlaying) {
                MainActivity.mServ.pauseMusic();
                item.setTitle("Unmute");
            } else {
                MainActivity.mServ.resumeMusic();
                item.setTitle("Mute");
            }
            MainActivity.isPlaying = !MainActivity.isPlaying;
        }

        if (id == R.id.Home){
            Intent go = new Intent(this,MainActivity.class);
            startActivity(go);
        }

        if (id == R.id.Search) {
            Intent go = new Intent(this,FilterActivity.class);
            startActivity(go);
        }

        if(id==R.id.About){
            Intent go = new Intent(this,AboutMeActivity.class);
            startActivity(go);
        }

        return super.onOptionsItemSelected(item);
    }
}