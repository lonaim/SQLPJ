package com.example.mymenu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class UserPage extends AppCompatActivity {
    ListView lView;
    TextView tvUser;
    Button logoutBtn;
    Intent intent;
    private static MyCustomAdapter adapter;
    private MediaPlayer mWork;
    ArrayList<String> songs;
    public static final String SHARED_PREFS = "sharedPrefs";

    private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon = new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this, MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        doBindService();

        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);

        tvUser = findViewById(R.id.tvUser);
        logoutBtn = findViewById(R.id.logoutbtn);

        Intent in = getIntent();
        if (in != null && in.getExtras() != null) {
            Bundle xtras = in.getExtras();
            String name = xtras.getString("UName");
            tvUser.setText("Hello " + name + "!");
        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear remember-me preference when logging out
                SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE).edit();
                editor.remove("rememberMe");
                editor.remove("username");
                editor.apply();

                // Redirect to FragHubActivity after logout
                Intent go = new Intent(view.getContext(), FragHubActivity.class);
                startActivity(go);
                finish(); // Close the UserPage activity
            }
        });


        lView = findViewById(R.id.lView);

        String[] dataSongs = {
                "Hey Jude",
                "Let It Be",
                "Yesterday",
                "Come Together",
                "Yellow Submarine",
                "All You Need Is Love",
                "Here Comes the Sun",};

        adapter = new MyCustomAdapter(this,dataSongs);
        lView.setAdapter(adapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String str = "Song - " + adapter.getItem(i).toString()+'\n';
                int place = i;
                switch (i+1){
                    case 1: str+="Distribution date - 1968";
                        mServ.changeSong("heyjude");
                        break;
                    case 2:str+="Distribution date - 1970";
                        mServ.changeSong("letitbe");
                        break;
                    case 3:str+="Distributed date - 1965";
                        mServ.changeSong("yesterday");
                        break;
                    case 4:str+="Distribution date - 1969";
                        mServ.changeSong("cometogether");
                        break;
                    case 5:
                        str+="Distribution date - 1966";
                        mServ.changeSong("yellowsubmarine");
                        break;
                    case 6:str+="Distribution date - 1967 ";
                        mServ.changeSong("mWork,R.raw.love");
                        break;
                    case 7:str+="Distribution date - 1969 ";
                        mServ.changeSong("thesun");
                        break;
                }

                Toast.makeText(UserPage.this,str
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
