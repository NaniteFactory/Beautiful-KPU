package com.example.administrator.myapplication;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SpinnerAdapter;


/**
 * Created by yong on 2016-05-29.
 */
public class MusicActivity extends AppCompatActivity implements ActionBar.OnNavigationListener {
    private SeekBar seekBar;
    private Button btn_play, btn_pause, btn_stop;
    MediaPlayer mp = null;
    int selectSongNum = 0;
    AudioManager audioManager;
    ActionBar actionBar;
    SpinnerAdapter adapter;

    private int nMax;
    private int nCurrentVolumn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        btn_play = (Button) findViewById(R.id.btn_play);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_stop = (Button) findViewById(R.id.btn_stop);

        actionBar = getSupportActionBar();
        adapter = ArrayAdapter.createFromResource(this, R.array.music_list1, android.R.layout.simple_spinner_dropdown_item);
        actionBar.setListNavigationCallbacks(adapter, this);
       actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

       audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);//?
        nMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        nCurrentVolumn = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekBar.setMax(nMax);
        seekBar.setProgress(nCurrentVolumn);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( mp != null) {
                    mp.start();
                }else {
                    selectSong();
                    mp.start();
                }
            }
        });
        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.pause();
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp != null) {
                    mp.stop();
                    mp.reset();
                }
                mp = null;
            }
        });


    }
        @Override
    public boolean onNavigationItemSelected(int i, long l) {
        selectSongNum = i;
            if(mp != null)
            mp.stop();
        mp = null;

        switch (i) {
            case 0:
                mp = MediaPlayer.create(this, R.raw.timtolove);
                break;
            case 1:
                mp = MediaPlayer.create(this, R.raw.cherryblossom);
                break;
            case 2:
                mp = MediaPlayer.create(this, R.raw.romance);
                break;
            case 3:
                mp = MediaPlayer.create(this, R.raw.fallingsnow);
                break;
            case 4:
                mp = MediaPlayer.create(this, R.raw.acacia);
                break;
            case 5:
                mp = MediaPlayer.create(this, R.raw.platoniclove);
                break;
            case 6:
                mp = MediaPlayer.create(this, R.raw.recollection);
                break;
            case 7:
                mp = MediaPlayer.create(this, R.raw.withyou);
                break;
        }
        mp.start();
        return false;
    }
    public void selectSong(){
        int i = selectSongNum;
        switch (i){
            case 0:
                mp = MediaPlayer.create(this, R.raw.timtolove);
                break;
            case 1:
                mp = MediaPlayer.create(this, R.raw.cherryblossom);
                break;
            case 2:
                mp = MediaPlayer.create(this, R.raw.romance);
                break;
            case 3:
                mp = MediaPlayer.create(this, R.raw.fallingsnow);
                break;
            case 4:
                mp = MediaPlayer.create(this, R.raw.acacia);
                break;
            case 5:
                mp = MediaPlayer.create(this, R.raw.platoniclove);
                break;
            case 6:
                mp = MediaPlayer.create(this, R.raw.recollection);
                break;
            case 7:
                mp = MediaPlayer.create(this, R.raw.withyou);
                break;
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mp != null)
            mp.release();
        mp = null;
    }
    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            try {
                mp.stop();
                mp = null;
            }
            catch(IllegalStateException e){
                e.printStackTrace();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/
}


