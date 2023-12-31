package com.example.mymenu;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

public class MainActivity extends AppCompatActivity {
    Intent intent;

    private boolean mIsBound = false;
    public static boolean isPlaying = true;
    public static MusicService mServ;
    private ServiceConnection Scon = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder) binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService() {
        bindService(new Intent(this, MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doBindService();

        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);

        if (menu instanceof MenuBuilder) {
            MenuBuilder mb = (MenuBuilder) menu;
            mb.setOptionalIconsVisible(true);
        }
        MenuItem menuItem = menu.findItem(R.id.Search);
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
            if (id == R.id.Home) {
                Intent go = new Intent(this, MainActivity.class);
                startActivity(go);
            }

            if (id == R.id.Search) {
                Intent go = new Intent(this, FilterActivity.class);
                startActivity(go);
            }

            if (id == R.id.About) {
                Intent go = new Intent(this, AboutMeActivity.class);
                startActivity(go);
            }

            return super.onOptionsItemSelected(item);
        }

    public void onClick (View view){
        intent = new Intent(this, FragHubActivity.class);
        startActivity(intent);
    }
}